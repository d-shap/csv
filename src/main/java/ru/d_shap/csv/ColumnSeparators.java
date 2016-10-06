// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

import ru.d_shap.csv.state.AbstractState;

/**
 * Available separators between columns in one row.
 *
 * @author Dmitry Shapovalov
 */
public enum ColumnSeparators {

    COMMA(String.valueOf((char) AbstractState.COMMA)),

    SEMICOLON(String.valueOf((char) AbstractState.SEMICOLON));

    private final String _value;

    ColumnSeparators(final String value) {
        _value = value;
    }

    String getValue() {
        return _value;
    }

}
