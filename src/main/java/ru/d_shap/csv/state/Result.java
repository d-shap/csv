// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv.state;

import java.util.ArrayList;
import java.util.List;

/**
 * Class obtains events from state machine and creates result CSV.
 *
 * @author Dmitry Shapovalov
 */
public final class Result {

    private List<List<String>> _rows;

    private List<String> _currentRow;

    private final StringBuilder _currentColumn;

    /**
     * Creates new object.
     */
    public Result() {
        super();
        _currentColumn = new StringBuilder(50);
    }

    void pushChar(final char ch) {
        _currentColumn.append(ch);
    }

    void pushColumn() {
        if (_currentRow == null) {
            _currentRow = new ArrayList<>();
        }
        _currentRow.add(_currentColumn.toString());
        _currentColumn.setLength(0);
    }

    void pushRow() {
        if (_rows == null) {
            _rows = new ArrayList<>();
        }
        _rows.add(_currentRow);
        _currentRow = null;
    }

    /**
     * Return parse result.
     *
     * @return parse result.
     */
    public List<List<String>> getResult() {
        List<List<String>> result = _rows;
        _rows = null;
        return result;
    }

}
