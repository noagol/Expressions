import java.util.Map;

/**
 * A class to describe Neg num.
 */

public class Neg extends UnaryExpression implements Expression {
    /**
     * A contractor for a new Neg expression.
     *
     * @param newExpression the new expression
     */
    public Neg(Expression newExpression) {
        super(newExpression);
    }

    /**
     * A contractor for a new Neg expression.
     *
     * @param newNum the num of the neg
     */
    public Neg(double newNum) {
        super(new Num(newNum));
    }

    /**
     * A contractor for a new Neg expression.
     *
     * @param newString the new string of the neg
     */
    public Neg(String newString) {
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
        return getExpr().evaluate(assignment) * (-1);
    }

    /**
     * Gets operator.
     *
     * @return the operator of the expression
     */
    @Override
    String getOperator() {
        return "-";
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
        return new Neg(getExpr().assign(var, expression));
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
        return new Neg(getExpr().differentiate(var));
    }

    /**
     * Simplify expression.
     *
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        try {
            // Check if there isn't variables.
            if (getExpr().simplify().getVariables().size() == 0) {
                return new Num(new Neg(getExpr().simplify()).evaluate());
            }
            // Simplify the Neg num.
            return new Neg(getExpr().simplify());
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
            // Return -(-x) = x
            return ((Neg) expression).getExpr().simplify();
        }
        // Return the simplify the expression.
        return new Neg(expression).simplify();
    }
}
