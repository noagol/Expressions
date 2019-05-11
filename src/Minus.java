import java.util.Map;

/**
 * A class to describe Minus.
 */
public class Minus extends BinaryExpression implements Expression {
    /**
     * A contractor for Minus.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression
     */
    public Minus(Expression firstExpression, Expression secondExpression) {
        super(firstExpression, secondExpression);
    }

    /**
     * A contractor for Minus, uses super contractor.
     *
     * @param firstExpression  the first expression as a string
     * @param secondExpression the first expression as a string
     */
    public Minus(String firstExpression, String secondExpression) {
        super(new Var(firstExpression), new Var(secondExpression));
    }

    /**
     * A contractor for Minus, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression as a num
     */
    public Minus(double firstExpression, double secondExpression) {
        super(new Num(firstExpression), new Num(secondExpression));
    }

    /**
     * A contractor for Minus, uses super contractor.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression as a num
     */
    public Minus(Expression firstExpression, double secondExpression) {
        super(firstExpression, new Num(secondExpression));
    }

    /**
     * A contractor for Minus, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression
     */
    public Minus(double firstExpression, Expression secondExpression) {
        super(new Num(firstExpression), secondExpression);
    }

    /**
     * A contractor for Minus, uses super contractor.
     *
     * @param firstExpression  the first expression as a var
     * @param secondExpression the second expression
     */
    public Minus(String firstExpression, Expression secondExpression) {
        super(new Var(firstExpression), secondExpression);
    }

    /**
     * A contractor for Minus, uses super contractor.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression as a var
     */
    public Minus(Expression firstExpression, String secondExpression) {
        super(firstExpression, new Var(secondExpression));
    }

    /**
     * A contractor for Minus, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression as a var
     */
    public Minus(double firstExpression, String secondExpression) {
        super(new Num(firstExpression), new Var(secondExpression));
    }

    /**
     * A contractor for Minus, uses super contractor.
     *
     * @param firstExpression  the first expression as a var
     * @param secondExpression the second expression as a num
     */
    public Minus(String firstExpression, double secondExpression) {
        super(new Var(firstExpression), new Num(secondExpression));
    }

    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.  If the expression
     * contains a variable which is not in the assignment, an exception
     * is thrown.
     *
     * @param assignment the assignment map
     * @return a double number
     * @throws Exception in case there isn't variable in the assignment
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return (getFirstExpression().evaluate(assignment) - getSecondExpression().evaluate(assignment));
    }

    /**
     * Gets operator.
     *
     * @return the operator of the expression
     */
    @Override
    protected String getOperator() {
        return " - ";
    }

    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     *
     * @param var        the var to replace
     * @param expression the expression
     * @return the expression with the new var
     */

    @Override
    public Expression assign(String var, Expression expression) {
        // assign the first expression.
        Expression firstExpressionNew = getFirstExpression().assign(var, expression);
        // assign the second expression.
        Expression secondExpressionNew = getSecondExpression().assign(var, expression);
        Minus newMinus = new Minus(firstExpressionNew, secondExpressionNew);
        return newMinus;
    }

    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     *
     * @param var the var to differentiate by
     * @return expression differentiated expression
     */
    @Override
    public Expression differentiate(String var) {
        return (new Minus(getFirstExpression().differentiate(var), getSecondExpression().differentiate(var)));
    }

    /**
     * Simplify expression.
     *
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        try {
            // If the first and second equals.
            if (getFirstExpression().simplify().toString()
                    .equals(getSecondExpression().simplify().toString())) {
                return new Num(0);
            }
            // if there is no variables in the second expression
            if (getSecondExpression().simplify().getVariables().size() == 0
                    && getFirstExpression().simplify().getVariables().size() != 0) {
                if (getSecondExpression().simplify().evaluate() == 0) {
                    return getFirstExpression().simplify();
                }
            }
            // if there is no variables in the first expression
            if (getFirstExpression().simplify().getVariables().size() == 0
                    && getSecondExpression().simplify().getVariables().size() != 0) {
                if (getFirstExpression().simplify().evaluate() == 0) {
                    return new Neg(getSecondExpression().simplify());
                }
            }
            // if there is no variables calculate the num
            if (getFirstExpression().simplify().getVariables().size() == 0
                    && getSecondExpression().simplify().getVariables().size() == 0) {
                return new Num(new Minus(getFirstExpression().simplify(),
                        getSecondExpression().simplify()).evaluate());
            }
            // Simplify minus
            return new Minus(getFirstExpression().simplify(), getSecondExpression().simplify());
        } catch (Exception e) {
            System.out.println(e);
            return this;
        }
    }

    /**
     * Advanced simplification expression.
     *
     * @return the simplified expression
     */
    @Override
    public Expression advancedSimplification() {
        // Simplify the first expression.
        Expression first = getFirstExpression().simplify().advancedSimplification();
        // Simplify the second expression.
        Expression second = getSecondExpression().simplify().advancedSimplification();
        // if the first and second expression is neg
        if (isNeg(first) && isNeg(second)) {
            // -x-(-y) = y-x
            return new Minus(((Neg) second).getExpr(), ((Neg) first).getExpr()).simplify();
            // ((-x) - y) = (-(x + y))
        } else if (isNeg(first) && !isNeg(second)) {
            return new Neg(new Plus(((Neg) first).getExpr(), second)).simplify();
            //(x - (-y)) -> (x + y)
        } else if (!isNeg(first) && isNeg(second)) {
            return new Plus(first, ((Neg) second).getExpr()).simplify();
        }
        // Return the simplify the expression.
        return new Minus(first, second).simplify();
    }
}

