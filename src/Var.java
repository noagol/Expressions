import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A calls to describe Var.
 */
public class Var implements Expression {
    private String variable;

    /**
     * A Constructor.
     * Initializes a new Var.
     *
     * @param var the var
     */
    public Var(String var) {
        this.variable = var;
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
        // if the map contain the var
        if (assignment.containsKey(variable)) {
            // return the var value
            return assignment.get(variable);
            // the var isn't exist
        } else {
            throw new Exception("No variable" + variable);
        }
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     *
     * @return a double number
     * @throws Exception in case there isn't variable in the assignment
     */
    @Override
    public double evaluate() throws Exception {
        Map<String, Double> map = new TreeMap<String, Double>();
        return evaluate(map);
    }

    /**
     * Returns a list of the variables in the expression.
     *
     * @return a list of the variables in the expression.
     */
    @Override
    public List<String> getVariables() {
        // create a new list
        List<String> variables = new LinkedList<>();
        // add the variable to the list.
        variables.add(variable);
        return (variables);
    }

    /**
     * @return a nice string representation of the expression.
     */
    @Override
    public String toString() {
        return variable;
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
        Expression newVar = new Var(variable);
        // if the var equals to the variable
        if (var.equals(variable)) {
            //change to the expression
            newVar = expression;
        }
        return newVar;
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
        // if we differentiate by var hid differentiated 1
        if (variable.equals(var)) {
            return (new Num(1));
            // the differentiate is 0
        } else {
            return (new Num(0));
        }
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
