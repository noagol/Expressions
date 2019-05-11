import java.util.Map;

/**
 * A class to describe Mult.
 */
public class Mult extends BinaryExpression implements Expression {

    /**
     * A contractor for Mult.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression
     */
    public Mult(Expression firstExpression, Expression secondExpression) {
        super(firstExpression, secondExpression);
    }

    /**
     * A contractor for Mult, uses super contractor.
     *
     * @param firstExpression  the first expression as a string
     * @param secondExpression the first expression as a string
     */
    public Mult(String firstExpression, String secondExpression) {
        super(new Var(firstExpression), new Var(secondExpression));
    }

    /**
     * A contractor for Mult, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression as a num
     */
    public Mult(double firstExpression, double secondExpression) {
        super(new Num(firstExpression), new Num(secondExpression));
    }

    /**
     * A contractor for Mult, uses super contractor.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression as a num
     */
    public Mult(Expression firstExpression, double secondExpression) {
        super(firstExpression, new Num(secondExpression));
    }

    /**
     * A contractor for Mult, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression
     */
    public Mult(double firstExpression, Expression secondExpression) {
        super(new Num(firstExpression), secondExpression);
    }

    /**
     * A contractor for Mult, uses super contractor.
     *
     * @param firstExpression  the first expression as a var
     * @param secondExpression the second expression
     */
    public Mult(String firstExpression, Expression secondExpression) {
        super(new Var(firstExpression), secondExpression);
    }

    /**
     * A contractor for Mult, uses super contractor.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression as a var
     */
    public Mult(Expression firstExpression, String secondExpression) {
        super(firstExpression, new Var(secondExpression));
    }

    /**
     * A contractor for Mult, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression as a var
     */
    public Mult(double firstExpression, String secondExpression) {
        super(new Num(firstExpression), new Var(secondExpression));
    }

    /**
     * A contractor for Mult, uses super contractor.
     *
     * @param firstExpression  the first expression as a var
     * @param secondExpression the second expression as a num
     */
    public Mult(String firstExpression, double secondExpression) {
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
        return (getFirstExpression().evaluate(assignment) * getSecondExpression().evaluate(assignment));
    }

    /**
     * Gets operator.
     *
     * @return the operator of the expression
     */
    @Override
    protected String getOperator() {
        return " * ";
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
        Expression first = getFirstExpression().assign(var, expression);
        // assign the second expression.
        Expression second = getSecondExpression().assign(var, expression);
        return new Mult(first, second);
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
        // f*g = f*g' + g*f'
        Expression first = new Mult(getSecondExpression(), getFirstExpression().differentiate(var));
        Expression second = new Mult(getFirstExpression(), getSecondExpression().differentiate(var));
        return (new Plus(first, second));
    }

    /**
     * Simplify expression.
     *
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        try {
            // if the first expression have no variables
            if (getFirstExpression().simplify().getVariables().size() == 0) {
                // if the first expression is 0 return 0
                if (getFirstExpression().simplify().evaluate() == 0) {
                    return new Num(0);
                }
                // if the first expression is 1 return second expression
                if (getFirstExpression().simplify().evaluate() == 1) {
                    return getSecondExpression().simplify();
                }
            }
            // if the second expression have no variables
            if (getSecondExpression().simplify().getVariables().size() == 0) {
                // if the second expression is 0 return 0
                if (getSecondExpression().simplify().evaluate() == 0) {
                    return new Num(0);
                }
                // if the second expression is 1 return first expression
                if (getSecondExpression().simplify().evaluate() == 1) {
                    return getFirstExpression().simplify();
                }
            }
            // if there is no variables calculate the num
            if (getFirstExpression().simplify().getVariables().size() == 0
                    && getSecondExpression().simplify().getVariables().size() == 0) {
                return new Num(new Mult(getFirstExpression(), getSecondExpression()).evaluate());
            }
            // Simplify mult
            return new Mult(getFirstExpression().simplify(), getSecondExpression().simplify());
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
        //
        if (first.getVariables().size() == 0) {
            // if the second expression is plus.
            if (isPlus(second)) {
                return new Plus(new Mult(first, ((Plus) second).getFirstExpression())
                        , new Mult(first, ((Plus) second).getSecondExpression()));
            }
        }
        // Return the simplify the expression.
        return new Mult(first, second).simplify();
    }
}
