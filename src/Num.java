import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A class to describe a number.
 */
public class Num implements Expression {
    private double number;

    /**
     * Instantiates a new Num.
     *
     * @param number the number
     */
    public Num(double number) {
        this.number = number;
    }

    /**
     * Evaluate a number.
     *
     * @param assignment the assignment map
     * @return a double number
     * @throws Exception in case there isn't variable in the assignment
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return number;
    }

    /**
     * Evaluate a number.
     *
     * @return a list of the variables in the expression.
     * @throws Exception in case there is variable with no value
     */
    @Override
    public double evaluate() throws Exception {
        return number;
    }

    /**
     * Returns a list of the variables in the expression,
     * in this case return empty list.
     *
     * @return a list of the variables in the expression.
     */
    @Override
    public List<String> getVariables() {
        return new LinkedList<>();
    }

    /**
     * @return a nice string representation of the expression.
     */
    @Override
    public String toString() {
        return (Double.toString(number));
    }

    /**
     * Returns the number.
     * (we can't assign a number)
     *
     * @param var        the var to replace
     * @param expression the expression
     * @return the expression with the new var
     */
    @Override
    public Expression assign(String var, Expression expression) {
        return new Num(number);
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
        // the differentiate of a number is 0
        return new Num(0);
    }

    /**
     * Simplify expression.
     *
     * @return a simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        return this;
    }

    /**
     * Advanced simplification expression.
     *
     * @return the simplified expression
     */
    @Override
    public Expression advancedSimplification() {
        return this;
    }
}
