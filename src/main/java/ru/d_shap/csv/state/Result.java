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

    private final CharBuffer _currentColumn;

    /**
     * Creates new object.
     */
    public Result() {
        super();
        _rows = null;
        _currentRow = null;
        _currentColumn = new CharBuffer();
    }

    void pushSymbol(final int symbol) {
        _currentColumn.append((char) symbol);
    }

    void pushColumn() {
        createCurrentRow();
        _currentRow.add(_currentColumn.toString());
        _currentColumn.clear();
    }

    void pushRow() {
        createRows();
        createCurrentRow();
        _rows.add(_currentRow);
        _currentRow = null;
        _currentColumn.clear();
    }

    void pushRows() {
        createRows();
        _currentRow = null;
        _currentColumn.clear();
    }

    private void createRows() {
        if (_rows == null) {
            _rows = new ArrayList<>();
        }
    }

    private void createCurrentRow() {
        if (_currentRow == null) {
            _currentRow = new ArrayList<>();
        }
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
