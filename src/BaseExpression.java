import java.util.Map;
import java.util.TreeMap;

/**
 * A Class for Base expression.
 */
public abstract class BaseExpression {

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
    abstract double evaluate(Map<String, Double> assignment) throws Exception;

    /**
     * Evaluate the expression and return the value.
     * Uses evaluate method with empty map
     *
     * @return the value of the expression
     * @throws Exception in case there is variable with no value
     */
    public double evaluate() throws Exception {
        Map<String, Double> map = new TreeMap<String, Double>();
        return evaluate(map);
    }


    /**
     * Gets operator.
     *
     * @return the operator of the expression
     */
    abstract String getOperator();

    /**
     * Check if the expression is Mult.
     *
     * @param e expression to check
     * @return true if the expression is Mult else return false
     */
    protected boolean isMult(Expression e) {
        return (e instanceof Mult);
    }

    /**
     * Check if the expression is Pow.
     *
     * @param e expression to check
     * @return true if the expression is Pow else return false
     */
    protected boolean isPow(Expression e) {
        return (e instanceof Pow);
    }

    /**
     * Check if the expression is Log.
     *
     * @param e expression to check
     * @return true if the expression is Log else return false
     */
    protected boolean isLog(Expression e) {
        return (e instanceof Log);
    }

    /**
     * Check if the expression is Sin.
     *
     * @param e expression to check
     * @return true if the expression is Sin else return false
     */
    protected boolean isSin(Expression e) {
        return (e instanceof Sin);
    }

    /**
     * Check if the expression is Cos.
     *
     * @param e expression to check
     * @return true if the expression is Cos else return false
     */
    protected boolean isCos(Expression e) {
        return (e instanceof Cos);
    }

    /**
     * Check if the expression is Neg.
     *
     * @param e expression to check
     * @return true if the expression is Neg else return false
     */
    protected boolean isNeg(Expression e) {
        return (e instanceof Neg);
    }

    /**
     * Check if the expression is Num.
     *
     * @param e expression to check
     * @return true if the expression is Num else return false
     */
    protected boolean isNum(Expression e) {
        return (e instanceof Num);
    }

    /**
     * Check if the expression is Plus.
     *
     * @param e expression to check
     * @return true if the expression is Num else return false
     */
    protected boolean isPlus(Expression e) {
        return (e instanceof Plus);
    }
}
