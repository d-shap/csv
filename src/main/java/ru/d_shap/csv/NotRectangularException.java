// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

/**
 * Exception in thrown when CSV is not rectangular.
 *
 * @author Dmitry Shapovalov
 */
public final class NotRectangularException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates new object.
     */
    public NotRectangularException() {
        super(getErrorMessage(null));
    }

    /**
     * Creates new object.
     *
     * @param lastSymbols last symbols before wrong symbol.
     */
    public NotRectangularException(final String lastSymbols) {
        super(getErrorMessage(lastSymbols));
    }

    private static String getErrorMessage(final String lastSymbols) {
        StringBuilder builder = new StringBuilder(80);
        builder.append("CSV is not rectangular.");
        if (lastSymbols != null) {
            builder.append(" Last symbols: \"");
            builder.append(lastSymbols);
            builder.append("\".");
        }
        return builder.toString();
    }

}
