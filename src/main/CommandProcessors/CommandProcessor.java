package CommandProcessors;

/**
 * Created by arundudani on 16/04/2015.
 *
 * This defines contract for processing commands available in application
 *
 */
public interface CommandProcessor<T> {
    public T process(String user, String commandArgument);
}
