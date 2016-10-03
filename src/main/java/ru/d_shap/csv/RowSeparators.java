// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

public enum RowSeparators {

    CR("\r"),

    LF("\n"),

    CRLF("\r\n");

    private final String _value;

    RowSeparators(final String value) {
        _value = value;
    }

    String getValue() {
        return _value;
    }

}
