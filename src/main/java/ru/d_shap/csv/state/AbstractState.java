// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv.state;

/**
 * Base class for all state classes.
 *
 * @author Dmitry Shapovalov
 */
public abstract class AbstractState {

    public static final int END_OF_INPUT = -1;

    public static final int COMMA = ',';

    public static final int SEMICOLON = ';';

    public static final int CR = '\r';

    public static final int LF = '\n';

    public static final int QUOT = '"';

    AbstractState() {
        super();
    }

    /**
     * Return init state of CSV parser state machine.
     *
     * @return init state.
     */
    public static AbstractState getInitState() {
        return State0.INSTANCE;
    }

    /**
     * Process input char and define next parser state.
     *
     * @param symbol input symbol.
     * @param result parse result object.
     * @return next parser state.
     */
    public abstract AbstractState processInput(int symbol, Result result);

}
