/**
 * Exception class for cycles in a graph.
 * @author Jeffrey Sham JHED: jsham2
 *
 */
public class CycleFoundException extends RuntimeException {

    /** The serial version id for the exception.
     */
    private static final long serialVersionUID = 1L;

    /** Create a default exception object.
     */
    public CycleFoundException() {
        super("ERROR: Graph has a cycle. Invalid operation.");
    }

    /** Create a specific exception object.
     *  @param err the error message
     */
    public CycleFoundException(String err) {
        super(err);
    }
}
