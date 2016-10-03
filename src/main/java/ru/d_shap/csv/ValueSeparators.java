// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

public enum ValueSeparators {

    COMMA(","),

    SEMICOLON(";");

    private final String _value;

    ValueSeparators(final String value) {
        _value = value;
    }

    String getValue() {
        return _value;
    }

}
