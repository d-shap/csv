// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

import ru.d_shap.csv.state.AbstractState;

/**
 * Available separators between rows.
 *
 * @author Dmitry Shapovalov
 */
public enum RowSeparators {

    CR(String.valueOf((char) AbstractState.CR)),

    LF(String.valueOf((char) AbstractState.LF)),

    CRLF(CR.getValue() + LF.getValue());

    private final String _value;

    RowSeparators(final String value) {
        _value = value;
    }

    String getValue() {
        return _value;
    }

}
