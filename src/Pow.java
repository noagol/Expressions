import java.util.Map;

/**
 * A class to describe Pow.
 */
public class Pow extends BinaryExpression implements Expression {
    /**
     * A contractor for Pow.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression
     */
    public Pow(Expression firstExpression, Expression secondExpression) {
        super(firstExpression, secondExpression);
    }

    /**
     * A contractor for Pow, uses super contractor.
     *
     * @param firstExpression  the first expression as a string
     * @param secondExpression the first expression as a string
     */
    public Pow(String firstExpression, String secondExpression) {
        super(new Var(firstExpression), new Var(secondExpression));
    }

    /**
     * A contractor for Pow, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression as a num
     */
    public Pow(double firstExpression, double secondExpression) {
        super(new Num(firstExpression), new Num(secondExpression));
    }

    /**
     * A contractor for Pow, uses super contractor.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression as a num
     */
    public Pow(Expression firstExpression, double secondExpression) {
        super(firstExpression, new Num(secondExpression));
    }

    /**
     * A contractor for Pow, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression
     */
    public Pow(double firstExpression, Expression secondExpression) {
        super(new Num(firstExpression), secondExpression);
    }

    /**
     * A contractor for Pow, uses super contractor.
     *
     * @param firstExpression  the first expression as a var
     * @param secondExpression the second expression
     */
    public Pow(String firstExpression, Expression secondExpression) {
        super(new Var(firstExpression), secondExpression);
    }

    /**
     * A contractor for Pow, uses super contractor.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression as a var
     */
    public Pow(Expression firstExpression, String secondExpression) {
        super(firstExpression, new Var(secondExpression));
    }

    /**
     * A contractor for Pow, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression as a var
     */
    public Pow(double firstExpression, String secondExpression) {
        super(new Num(firstExpression), new Var(secondExpression));
    }

    /**
     * A contractor for Pow, uses super contractor.
     *
     * @param firstExpression  the first expression as a var
     * @param secondExpression the second expression as a num
     */
    public Pow(String firstExpression, double secondExpression) {
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
        // if the power is 0^ -x we get infinity
        if (getFirstExpression().evaluate(assignment) == 0 && getSecondExpression().evaluate(assignment) < 0) {
            throw new Exception("no power");
        }
        // Evaluate the expression.
        return Math.pow(getFirstExpression().evaluate(assignment), getSecondExpression().evaluate(assignment));
    }

    /**
     * Gets operator.
     *
     * @return the operator of the expression
     */
    @Override
    protected String getOperator() {
        return "^";
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
        Pow newPow = new Pow(firstExpressionNew, secondExpressionNew);
        return newPow;
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
        //f^g = f^g * (f'*(g/f) + g'*log(e,f))
        Expression first = new Pow(getFirstExpression(), getSecondExpression());
        Expression second = new Mult(getFirstExpression().differentiate(var),
                new Div(getSecondExpression(), getFirstExpression()));
        Expression third = new Mult(getSecondExpression().differentiate(var), new Log("e", getFirstExpression()));
        return (new Mult(first, new Plus(second, third)));
    }

    /**
     * Simplify expression.
     *
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        try {
            // if there is no variables calculate the num
            if (getFirstExpression().simplify().getVariables().size() == 0
                    && getSecondExpression().simplify().getVariables().size() == 0) {
                return new Num(new Pow(getFirstExpression(), getSecondExpression()).evaluate());
            }
            // Simplify pow
            return new Pow(getFirstExpression().simplify(), getSecondExpression().simplify());
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
        // if the first expression is pow.
        // check 0^x = 0
        if (getFirstExpression().simplify().getVariables().size() == 0
                && getSecondExpression().simplify().getVariables().size() != 0) {
            if (getFirstExpression().simplify().toString().equals("0.0")) {
                return new Num(0);
            }
        }
        // check x^0=1
        if (getSecondExpression().simplify().getVariables().size() == 0
                && getFirstExpression().simplify().getVariables().size() != 0) {
            if (getSecondExpression().simplify().toString().equals("0.0")) {
                return new Num(1);
            }
            if (getSecondExpression().simplify().toString().equals("1.0")) {
                return getFirstExpression().simplify().advancedSimplification();
            }
        }
        if (isPow(getFirstExpression())) {
            // check (a^b)^c = a^(b*c)
            return new Pow(((Pow) first).getFirstExpression(),
                    new Mult(((Pow) first).getSecondExpression(), second)).simplify();
            // if the second expression is log.
        } else if (isLog(second)) {
            //check a^log(a,b)=b
            if (first.toString().equals(((Log) second).getFirstExpression().toString())) {
                return ((Log) second).getSecondExpression().simplify();
            }
            // if the first expression is number
        } else if (isNum(first)) {
            // check 1^(x+y) = 1
            if (first.toString().equals("1.0")) {
                return new Num(1);
            }
        }
        // Return the simplify the expression.
        return new Pow(first, second).simplify();
    }


}



