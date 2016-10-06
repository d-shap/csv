// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import ru.d_shap.csv.state.AbstractState;
import ru.d_shap.csv.state.ParserEventHandler;

/**
 * Class to parse CSV from source.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvParser {

    private CsvParser() {
        super();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param csv CSV to parse.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parseCsv(final String csv) {
        if (csv == null) {
            return null;
        }
        Reader reader = new StringReader(csv);
        try {
            return parseCsv(reader);
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader CSV to parse.
     * @return list of rows, each row is a list of columns.
     * @throws IOException IO Exception.
     */
    public static List<List<String>> parseCsv(final Reader reader) throws IOException {
        if (reader == null) {
            return null;
        }

        ParserEventHandler parserEventHandler = new ParserEventHandler();
        AbstractState state = AbstractState.getInitState();
        int read;
        while (true) {
            read = reader.read();
            if (read < 0) {
                break;
            }
            state = state.processInput(read, parserEventHandler);
        }
        state.processInput(AbstractState.END_OF_INPUT, parserEventHandler);
        return parserEventHandler.getResult();
    }

}
