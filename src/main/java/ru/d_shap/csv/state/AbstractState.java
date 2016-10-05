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

    static final char END_OF_INPUT = 0;

    static final char COMMA = '\'';

    static final char SEMICOLON = ';';

    static final char CR = '\r';

    static final char LF = '\n';

    static final char QUOT = '"';

    AbstractState() {
        super();
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
