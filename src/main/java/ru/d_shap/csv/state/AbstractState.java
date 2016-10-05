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

    public static final char END_OF_INPUT = 0;

    public static final char COMMA = ',';

    public static final char SEMICOLON = ';';

    public static final char CR = '\r';

    public static final char LF = '\n';

    public static final char QUOT = '"';

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
     * @param ch     input symbol.
     * @param result parse result object.
     * @return next parser state.
     */
    public abstract AbstractState processInput(char ch, Result result);

}
