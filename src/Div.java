import java.util.Map;

/**
 * A class to describe Div.
 */
public class Div extends BinaryExpression implements Expression {
    /**
     * A contractor for Div.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression
     */
    public Div(Expression firstExpression, Expression secondExpression) {
        super(firstExpression, secondExpression);
    }

    /**
     * A contractor for Div, uses super contractor.
     *
     * @param firstExpression  the first expression as a string
     * @param secondExpression the first expression as a string
     */
    public Div(String firstExpression, String secondExpression) {
        super(new Var(firstExpression), new Var(secondExpression));
    }

    /**
     * A contractor for Div, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression as a num
     */
    public Div(double firstExpression, double secondExpression) {
        super(new Num(firstExpression), new Num(secondExpression));
    }

    /**
     * A contractor for Div, uses super contractor.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression as a num
     */
    public Div(Expression firstExpression, double secondExpression) {
        super(firstExpression, new Num(secondExpression));
    }

    /**
     * A contractor for Div, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression
     */
    public Div(double firstExpression, Expression secondExpression) {
        super(new Num(firstExpression), secondExpression);
    }

    /**
     * A contractor for Div, uses super contractor.
     *
     * @param firstExpression  the first expression as a var
     * @param secondExpression the second expression
     */
    public Div(String firstExpression, Expression secondExpression) {
        super(new Var(firstExpression), secondExpression);
    }

    /**
     * A contractor for Div, uses super contractor.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression as a var
     */
    public Div(Expression firstExpression, String secondExpression) {
        super(firstExpression, new Var(secondExpression));
    }

    /**
     * A contractor for Div, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression as a var
     */
    public Div(double firstExpression, String secondExpression) {
        super(new Num(firstExpression), new Var(secondExpression));
    }

    /**
     * A contractor for Div, uses super contractor.
     *
     * @param firstExpression  the first expression as a var
     * @param secondExpression the second expression as a num
     */
    public Div(String firstExpression, double secondExpression) {
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
        // If the expression divided by 0.
        if (getSecondExpression().evaluate(assignment) == 0) {
            // throw exception
            throw new Exception("Divided by 0");
        }
        // Evaluate the expression.
        return (getFirstExpression().evaluate(assignment) / getSecondExpression().evaluate(assignment));
    }

    /**
     * Gets operator.
     *
     * @return the operator of the expression
     */
    @Override
    protected String getOperator() {
        return " / ";
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
        // create a new div withe the new var.
        Div newDiv = new Div(firstExpressionNew, secondExpressionNew);
        return newDiv;
    }

    @Override
    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     *
     * @param var the var to differentiate by
     * @return expression differentiated expression
     */
    public Expression differentiate(String var) {
        // the up differentiate
        Expression up = new Minus((new Mult(getFirstExpression().differentiate(var)
                , getSecondExpression())), new Mult(getSecondExpression().differentiate(var),
                getFirstExpression()));
        // the down differentiate
        Expression down = new Pow(getSecondExpression(), 2);
        // Return the new div differentiate.
        return new Div(up, down);
    }

    /**
     * Simplify expression.
     *
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        try {
            // If the first and second expressions equals.
            if (getFirstExpression().simplify().toString().equals(getSecondExpression().simplify().toString())) {
                // Return 1
                return new Num(1);
            }
            // if the number divided by 1
            if (getSecondExpression().simplify().getVariables().size() == 0
                    && getFirstExpression().simplify().getVariables().size() != 0) {
                if (getSecondExpression().simplify().evaluate() == 1) {
                    // return the first expression.
                    return getFirstExpression().simplify();
                }
            }
            // if there is no variables calculate the num
            if (getFirstExpression().simplify().getVariables().size() == 0
                    && getSecondExpression().simplify().getVariables().size() == 0) {
                return new Num(new Div(getFirstExpression().simplify(),
                        getSecondExpression().simplify()).evaluate());
            }
            // Simplify div
            return new Div(getFirstExpression().simplify(), getSecondExpression().simplify());
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
        // if the first expression is 0
        if (getFirstExpression().simplify().getVariables().size() == 0
                && getSecondExpression().simplify().getVariables().size() != 0) {
            if (getFirstExpression().simplify().toString().equals("0.0")) {
                // return 0
                return new Num(0);
            }
        }
        // if the first and second expression is negative.
        if (isNeg(first) && isNeg(second)) {
            // return -(x+y)/-(x+y) = (x+y)/(x+y)
            return new Div(new Neg(first).advancedSimplification(),
                    new Neg(second).advancedSimplification()).simplify();
        }
        // Return the simplify the expression.
        return new Div(first, second).simplify();
    }
}
