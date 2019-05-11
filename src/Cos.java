import java.util.Map;

/**
 * A class to describe Cos.
 */
public class Cos extends UnaryExpression implements Expression {

    /**
     * A contractor for a new Cos.
     *
     * @param newExpression the new expression
     */
    public Cos(Expression newExpression) {
        super(newExpression);
    }

    /**
     * A contractor for a new Cos.
     *
     * @param newNum the num of the cos
     */
    public Cos(double newNum) {
        super(new Num(newNum));
    }

    /**
     * A contractor for a new Cos.
     *
     * @param newString the new string of the cos
     */
    public Cos(String newString) {
        super(new Var(newString));
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
        if (((getExpr().evaluate(assignment) / 90) + 1) % 2 == 0) {
            return 0;
        }
        return Math.cos(Math.toRadians(getExpr().evaluate(assignment)));
    }

    /**
     * Gets operator.
     *
     * @return the operator of the expression
     */
    @Override
    String getOperator() {
        return "cos";
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
        return new Cos(getExpr().assign(var, expression));
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
        return new Mult(new Neg(new Sin(getExpr())), getExpr().differentiate(var));
    }

    /**
     * Simplify expression.
     *
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        try {
            //Check if there isn't variables.
            if (getExpr().simplify().getVariables().size() == 0) {
                // Calculate the cos.
                return new Num(new Cos(getExpr().simplify()).evaluate());
            }
            // Simplify cos
            return new Cos(getExpr().simplify());
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
        // Simplify the expression.
        Expression expression = getExpr().simplify().advancedSimplification();
        // If the expression is neg
        if (isNeg(expression)) {
            // Return cos(-x) = cos(x).
            return new Cos(((Neg) expression).getExpr()).advancedSimplification();
        }
        // Return the simplify the expression.
        return new Cos(expression).simplify();
    }

}
