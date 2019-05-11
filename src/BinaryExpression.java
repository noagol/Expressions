import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Binary expression.
 */
public abstract class BinaryExpression extends BaseExpression {
    private Expression firstExpression;
    private Expression secondExpression;

    /**
     * Instantiates a new Binary expression.
     *
     * @param first  the first
     * @param second the second
     */
    public BinaryExpression(Expression first, Expression second) {
        this.firstExpression = first;
        this.secondExpression = second;
    }

    /**
     * Gets first expression.
     *
     * @return the first expression
     */
    public Expression getFirstExpression() {
        return this.firstExpression;
    }

    /**
     * Gets second expression.
     *
     * @return the second expression
     */
    public Expression getSecondExpression() {
        return this.secondExpression;
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
        set.addAll(this.firstExpression.getVariables());
        set.addAll(this.secondExpression.getVariables());
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
        // Check if the operator is log.
        if (operator.equals("log")) {
            // Return the string.
            return (operator + "(" + getFirstExpression().toString()
                    + ", " + getSecondExpression().toString() + ")");
        }
        // Return the string.
        return ("(" + getFirstExpression().toString() + operator + getSecondExpression().toString() + ")");
    }
}
