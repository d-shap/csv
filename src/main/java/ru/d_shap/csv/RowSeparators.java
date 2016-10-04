// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

/**
 * Available separators between rows.
 *
 * @author Dmitry Shapovalov
 */
public enum RowSeparators {

    CR("\r"),

    LF("\n"),

    CRLF("\r\n");

    private final String _value;

    RowSeparators(final String value) {
        _value = value;
    }

    /**
     * Character value of object.
     *
     * @return character value.
     */
    public String getValue() {
        return _value;
    }

}
