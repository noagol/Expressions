import java.util.Map;

/**
 * A class to describe Log.
 */
public class Log extends BinaryExpression implements Expression {
    /**
     * A contractor for Log.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression
     */
    public Log(Expression firstExpression, Expression secondExpression) {
        super(firstExpression, secondExpression);
    }

    /**
     * A contractor for Log, uses super contractor.
     *
     * @param firstExpression  the first expression as a string
     * @param secondExpression the first expression as a string
     */
    public Log(String firstExpression, String secondExpression) {
        super(new Var(firstExpression), new Var(secondExpression));
    }

    /**
     * A contractor for Log, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression as a num
     */
    public Log(double firstExpression, double secondExpression) {
        super(new Num(firstExpression), new Num(secondExpression));
    }

    /**
     * A contractor for Log, uses super contractor.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression as a num
     */
    public Log(Expression firstExpression, double secondExpression) {
        super(firstExpression, new Num(secondExpression));
    }

    /**
     * A contractor for Log, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression
     */
    public Log(double firstExpression, Expression secondExpression) {
        super(new Num(firstExpression), secondExpression);
    }

    /**
     * A contractor for Log, uses super contractor.
     *
     * @param firstExpression  the first expression as a var
     * @param secondExpression the second expression
     */
    public Log(String firstExpression, Expression secondExpression) {
        super(new Var(firstExpression), secondExpression);
    }

    /**
     * A contractor for Log, uses super contractor.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression as a var
     */
    public Log(Expression firstExpression, String secondExpression) {
        super(firstExpression, new Var(secondExpression));
    }

    /**
     * A contractor for Log, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression as a var
     */
    public Log(double firstExpression, String secondExpression) {
        super(new Num(firstExpression), new Var(secondExpression));
    }

    /**
     * A contractor for Log, uses super contractor.
     *
     * @param firstExpression  the first expression as a var
     * @param secondExpression the second expression as a num
     */
    public Log(String firstExpression, double secondExpression) {
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
        // Check log conditions and throw exception.
        if (Math.log(getFirstExpression().evaluate(assignment)) == 0) {
            throw new Exception("Log Problem");
        } else if (getSecondExpression().evaluate(assignment) <= 0) {
            throw new Exception("Log Problem");
        } else if ((getFirstExpression().evaluate(assignment) <= 0)
                || (getFirstExpression().evaluate(assignment) == 1)) {
            throw new Exception("Log Problem");
        }
        // Evaluate the log.
        return (Math.log(getSecondExpression().evaluate(assignment))
                / Math.log(getFirstExpression().evaluate(assignment)));
    }

    /**
     * Gets operator.
     *
     * @return the operator of the expression
     */
    @Override
    protected String getOperator() {
        return ("log");
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
        // create a new log withe the new var.
        Log newLog = new Log(firstExpressionNew, secondExpressionNew);
        return newLog;
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
        // the up differentiate
        Expression up = getSecondExpression().differentiate(var);
        // the down differentiate
        Expression down = new Mult(getSecondExpression(), new Log("e", getFirstExpression()));
        return (new Div(up, down));
    }

    /**
     * Simplify expression.
     *
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        try {
            // if there is no variables calculate the log
            if ((getFirstExpression().simplify().getVariables().size() == 0
                    && getSecondExpression().simplify().getVariables().size() == 0)) {
                return new Num(new Log(getFirstExpression().simplify(),
                        getSecondExpression().simplify()).evaluate());
            }
            // if the base of the log and expression equals return 1
            if (getFirstExpression().simplify().toString()
                    .equals(getSecondExpression().simplify().toString())) {
                return new Num(1);
            }
            // Simplify log
            return new Log(getFirstExpression().simplify(), getSecondExpression().simplify());

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
        // log(x,1) = 0
        if (second.toString().equals("1.0")) {
            return new Num(0);
        }
        if (isPow(second)) {
            return (new Mult(((Pow) second).getSecondExpression().simplify(),
                    new Log(first, ((Pow) second).getFirstExpression().simplify())));
        }
        // Return the simplify the expression.
        return new Log(first, second).simplify();
    }

}

