// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

public final class CsvParseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CsvParseException() {
        super();
    }

    public CsvParseException(final String message) {
        super(message);
    }

    public CsvParseException(final Exception ex) {
        super(ex);
    }

    public CsvParseException(final String message, final Exception ex) {
        super(message, ex);
    }

}
