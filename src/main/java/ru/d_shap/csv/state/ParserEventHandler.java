// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv.state;

import java.util.ArrayList;
import java.util.List;

import ru.d_shap.csv.NotRectangularException;

/**
 * Class obtains events from state machine and creates result CSV.
 *
 * @author Dmitry Shapovalov
 */
public final class ParserEventHandler {

    private final boolean _checkRectangular;

    private final CharStack _lastSymbols;

    private List<List<String>> _rows;

    private List<String> _currentRow;

    private final CharBuffer _currentColumn;

    private int _columnCount;

    /**
     * Creates new object.
     *
     * @param checkRectangular check if all rows should have the same column count.
     */
    public ParserEventHandler(final boolean checkRectangular) {
        super();
        _checkRectangular = checkRectangular;
        _lastSymbols = new CharStack();
        _rows = null;
        _currentRow = null;
        _currentColumn = new CharBuffer();
        _columnCount = -1;
    }

    void addLastSymbol(final int symbol) {
        _lastSymbols.append((char) symbol);
    }

    String getLastSymbols() {
        return _lastSymbols.toString();
    }

    void pushSymbol(final int symbol) {
        _currentColumn.append((char) symbol);
    }

    void pushColumn() {
        setCurrentRow();
        _currentRow.add(_currentColumn.toString());
        _currentColumn.clear();
    }

    void pushRow() {
        setRows();
        setCurrentRow();
        if (_columnCount < 0) {
            _columnCount = _currentRow.size();
        }
        if (_checkRectangular && _columnCount != _currentRow.size()) {
            throw new NotRectangularException(_lastSymbols.toString());
        }
        _rows.add(_currentRow);
        _currentRow = null;
        _currentColumn.clear();
    }

    void pushRows() {
        setRows();
        _currentRow = null;
        _currentColumn.clear();
    }

    private void setRows() {
        if (_rows == null) {
            _rows = new ArrayList<List<String>>();
        }
    }

    private void setCurrentRow() {
        if (_currentRow == null) {
            _currentRow = new ArrayList<String>();
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
        _currentRow = null;
        _currentColumn.clear();
        _columnCount = -1;
        return result;
    }

}
