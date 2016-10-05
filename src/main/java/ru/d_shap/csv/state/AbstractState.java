// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv.state;

import ru.d_shap.csv.ColumnSeparators;
import ru.d_shap.csv.RowSeparators;

/**
 * Base class for all state classes.
 *
 * @author Dmitry Shapovalov
 */
public abstract class AbstractState {

    static final char END_OF_INPUT = 0;

    static final char COMMA = ColumnSeparators.COMMA.getValue().charAt(0);

    static final char SEMICOLON = ColumnSeparators.SEMICOLON.getValue().charAt(0);

    static final char CR = RowSeparators.CR.getValue().charAt(0);

    static final char LF = RowSeparators.LF.getValue().charAt(0);

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
