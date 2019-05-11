import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Unary expression.
 */
public abstract class UnaryExpression extends BaseExpression {
    private Expression expr;

    /**
     * Instantiates a new Unary expression.
     *
     * @param newExpression the new expression
     */
    public UnaryExpression(Expression newExpression) {
        this.expr = newExpression;
    }


    /**
     * Gets expr.
     *
     * @return the expr
     */
    public Expression getExpr() {
        return expr;
    }

    /**
     * Returns a list of the variables in the expression.
     *
     * @return the variables list
     */
    public List<String> getVariables() {
        // Create a new list.
        List<String> list = new ArrayList<String>();
        // Create new set.
        Set<String> set = new TreeSet<String>();
        // Set all the variables.
        set.addAll(this.expr.getVariables());
        // Add the variables to the list
        list.addAll(set);
        // Return the new list.
        return list;
    }

    /**
     * @return a nice string representation of the expression.
     */
    @Override
    public String toString() {
        // Get the operator of the expression.
        String operator = this.getOperator();
       // Check if the operator is neg.
        if (operator.equals("-")) {
           // Return the string.
            return ("(" + operator + getExpr().toString() + ")");
        }
        // Return the string.
        return (String.format(operator + "(%s)", getExpr().toString()));
    }

}
