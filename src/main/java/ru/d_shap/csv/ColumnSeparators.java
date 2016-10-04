// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

/**
 * Available separators between columns in one row.
 *
 * @author Dmitry Shapovalov
 */
public enum ColumnSeparators {

    COMMA(","),

    SEMICOLON(";");

    private final String _value;

    ColumnSeparators(final String value) {
        _value = value;
    }

    public String getValue() {
        return _value;
    }

}
