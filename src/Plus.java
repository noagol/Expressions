import java.util.Map;

/**
 * A class to describe Plus.
 */
public class Plus extends BinaryExpression implements Expression {
    /**
     * A contractor for Plus.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression
     */
    public Plus(Expression firstExpression, Expression secondExpression) {
        super(firstExpression, secondExpression);
    }

    /**
     * A contractor for Plus, uses super contractor.
     *
     * @param firstExpression  the first expression as a string
     * @param secondExpression the first expression as a string
     */
    public Plus(String firstExpression, String secondExpression) {
        super(new Var(firstExpression), new Var(secondExpression));
    }

    /**
     * A contractor for Plus, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression as a num
     */
    public Plus(double firstExpression, double secondExpression) {
        super(new Num(firstExpression), new Num(secondExpression));
    }

    /**
     * A contractor for Plus, uses super contractor.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression as a num
     */
    public Plus(Expression firstExpression, double secondExpression) {
        super(firstExpression, new Num(secondExpression));
    }

    /**
     * A contractor for Plus, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression
     */
    public Plus(double firstExpression, Expression secondExpression) {
        super(new Num(firstExpression), secondExpression);
    }

    /**
     * A contractor for Plus, uses super contractor.
     *
     * @param firstExpression  the first expression as a var
     * @param secondExpression the second expression
     */
    public Plus(String firstExpression, Expression secondExpression) {
        super(new Var(firstExpression), secondExpression);
    }

    /**
     * A contractor for Plus, uses super contractor.
     *
     * @param firstExpression  the first expression
     * @param secondExpression the second expression as a var
     */
    public Plus(Expression firstExpression, String secondExpression) {
        super(firstExpression, new Var(secondExpression));
    }

    /**
     * A contractor for Plus, uses super contractor.
     *
     * @param firstExpression  the first expression as a num
     * @param secondExpression the second expression as a var
     */
    public Plus(double firstExpression, String secondExpression) {
        super(new Num(firstExpression), new Var(secondExpression));
    }

    /**
     * A contractor for Plus, uses super contractor.
     *
     * @param firstExpression  the first expression as a var
     * @param secondExpression the second expression as a num
     */
    public Plus(String firstExpression, double secondExpression) {
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
        return (getFirstExpression().evaluate(assignment)
                + getSecondExpression().evaluate(assignment));
    }

    /**
     * Gets operator.
     *
     * @return the operator of the expression
     */
    @Override
    protected String getOperator() {
        return " + ";
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
        return new Plus(getFirstExpression().assign(var, expression),
                getSecondExpression().assign(var, expression));
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
        return (new Plus(getFirstExpression().differentiate(var),
                getSecondExpression().differentiate(var)));
    }

    /**
     * Simplify expression.
     *
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        try {
            //if the first expression is 0
            if (getFirstExpression().simplify().getVariables().size() == 0
                    && getSecondExpression().simplify().getVariables().size() != 0) {
                if (getFirstExpression().simplify().evaluate() == 0) {
                    // return the second expression
                    return getSecondExpression().simplify();
                }
            }
            //if the second expression is 0
            if (getSecondExpression().simplify().getVariables().size() == 0
                    && getFirstExpression().simplify().getVariables().size() != 0) {
                if (getSecondExpression().simplify().evaluate() == 0) {
                    // return the first expression
                    return getFirstExpression().simplify();
                }
            }
            // if there is no variables calculate the num
            if (getSecondExpression().simplify().getVariables().size() == 0
                    && getFirstExpression().simplify()
                    .getVariables().size() == 0) {
                return (new Num(new Plus(getFirstExpression().simplify(),
                        getSecondExpression().simplify()).evaluate()));
            }
            // Simplify plus
            return new Plus(getFirstExpression().simplify(), getSecondExpression().simplify());
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
        // if the first and second expression is mult.
        //if the first and second expressions equal
        if (getFirstExpression().simplify().toString().
                equals(getSecondExpression().simplify().toString())) {
            // return x+x=2x
            return new Mult(new Num(2), getFirstExpression().simplify()).simplify();
        }
        if (isMult(first) && isMult(second)) {
            // check 2x+4x = 6x
            if (((Mult) getFirstExpression()).getSecondExpression().toString()
                    .equals(((Mult) getSecondExpression()).getSecondExpression().toString())) {
                return new Mult(new Plus(((Mult) first).getFirstExpression()
                        , ((Mult) second).getFirstExpression()),
                        ((Mult) second).getSecondExpression()).simplify();
                // check x2+x4 = 6x
            } else if (((Mult) getFirstExpression()).getFirstExpression().toString()
                    .equals(((Mult) getSecondExpression()).getFirstExpression().toString())) {
                return new Mult(new Plus(((Mult) first).getSecondExpression()
                        , ((Mult) second).getSecondExpression()),
                        ((Mult) second).getFirstExpression()).simplify();
                // check 2x+x4 = 6x
            } else if (((Mult) getFirstExpression()).getSecondExpression().toString()
                    .equals(((Mult) getSecondExpression()).getFirstExpression().toString())) {
                return new Mult(new Plus(((Mult) first).getFirstExpression()
                        , ((Mult) second).getSecondExpression()),
                        ((Mult) second).getFirstExpression()).simplify();
                // check x2+4x = 6x
            } else if (((Mult) getFirstExpression()).getFirstExpression().toString()
                    .equals(((Mult) getSecondExpression()).getSecondExpression().toString())) {
                return new Mult(new Plus(((Mult) first).getSecondExpression()
                        , ((Mult) second).getFirstExpression()),
                        ((Mult) second).getSecondExpression()).simplify();
            }
        }
        // if the first and second expression is pow.
        if (isPow(first) && isPow(second)) {
            // check sin^2(x)+cos^2(x)=1
            if ((isSin(((Pow) first).getFirstExpression()) && isCos(((Pow) second).getFirstExpression()))
                    && isNum(((Pow) first).getSecondExpression()) && isNum(((Pow) second).getSecondExpression())) {
                if (((((Pow) first).getSecondExpression().toString().equals("2.0"))
                        && (((Pow) second).getSecondExpression().toString().equals("2.0")))) {
                    return new Num(1);
                }
            }
        }
        // if the first and second expression is neg.
        if (isNeg(second)) {
            Expression n = new Neg(second).advancedSimplification();
            // check (x+y)-(x+y)=0
            if (first.toString().equals(n.toString())) {
                return new Num(0);
            }
        }
        // Return the simplify the expression.
        return new Plus(first, second).simplify();
    }
}
