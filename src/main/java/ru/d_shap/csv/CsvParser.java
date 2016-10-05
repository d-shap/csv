// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

import java.util.List;

import ru.d_shap.csv.state.AbstractState;
import ru.d_shap.csv.state.Result;

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

        Result result = new Result();
        AbstractState state = AbstractState.getInitState();
        for (int i = 0; i < csv.length(); i++) {
            state = state.processInput(csv.charAt(i), result);
        }
        state.processInput(AbstractState.END_OF_INPUT, result);
        return result.getResult();
    }

}
