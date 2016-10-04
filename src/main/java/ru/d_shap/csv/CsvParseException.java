// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

/**
 * Exception in thrown when CSV source is in a wrong format.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvParseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates new object.
     */
    public CsvParseException() {
        super();
    }

    /**
     * Creates new object.
     *
     * @param message exception message.
     */
    public CsvParseException(final String message) {
        super(message);
    }

}
