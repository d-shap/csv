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
     *
     * @param symbol      wrong symbol.
     * @param lastSymbols last symbols before wrong symbol.
     */
    public CsvParseException(final int symbol, final String lastSymbols) {
        super(getErrorMessage(symbol, lastSymbols));
    }

    private static String getErrorMessage(final int symbol, final String lastSymbols) {
        StringBuilder builder = new StringBuilder(80);
        builder.append("Wrong symbol obtained: '");
        builder.append((char) symbol);
        builder.append("' (");
        builder.append(symbol);
        builder.append("). ");
        builder.append("Last symbols: \"");
        builder.append(lastSymbols);
        builder.append("\".");
        return builder.toString();
    }

}
