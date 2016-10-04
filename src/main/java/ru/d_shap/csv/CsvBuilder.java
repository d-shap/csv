// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to create CSV from rows and columns. Object can be used to create multiple CSVs.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvBuilder {

    private static final List<String> SPECIAL;

    static {
        SPECIAL = new LinkedList<>();
        SPECIAL.add("\"");
        SPECIAL.add(ColumnSeparators.COMMA.getValue());
        SPECIAL.add(ColumnSeparators.SEMICOLON.getValue());
        SPECIAL.add(RowSeparators.CR.getValue());
        SPECIAL.add(RowSeparators.LF.getValue());
    }

    private final String _columnSeparator;

    private final String _rowSeparator;

    private List<List<String>> _rows;

    private List<String> _currentRow;

    /**
     * Creates new object.
     */
    public CsvBuilder() {
        this(ColumnSeparators.SEMICOLON, RowSeparators.CRLF);
    }

    /**
     * Creates new object.
     *
     * @param columnSeparator separator between columns.
     * @param rowSeparator    separator between rows.
     */
    public CsvBuilder(final ColumnSeparators columnSeparator, final RowSeparators rowSeparator) {
        super();
        _columnSeparator = columnSeparator.getValue();
        _rowSeparator = rowSeparator.getValue();
        _rows = null;
        _currentRow = null;
    }

    private void setRows() {
        if (_rows == null) {
            _rows = new ArrayList<>();
        }
    }

    private void setCurrentRow() {
        if (_currentRow == null) {
            _currentRow = new ArrayList<>();
        }
    }

    /**
     * Add column to the current row and move cursor to the next column.
     *
     * @param column column.
     * @return current object for chaining.
     */
    public CsvBuilder addColumn(final int column) {
        setCurrentRow();
        _currentRow.add(String.valueOf(column));
        return this;
    }

    /**
     * Add column to the current row and move cursor to the next column.
     *
     * @param column column.
     * @return current object for chaining.
     */
    public CsvBuilder addColumn(final long column) {
        setCurrentRow();
        _currentRow.add(String.valueOf(column));
        return this;
    }

    /**
     * Add column to the current row and move cursor to the next column.
     *
     * @param column column.
     * @return current object for chaining.
     */
    public CsvBuilder addColumn(final float column) {
        setCurrentRow();
        _currentRow.add(String.valueOf(column));
        return this;
    }

    /**
     * Add column to the current row and move cursor to the next column.
     *
     * @param column column.
     * @return current object for chaining.
     */
    public CsvBuilder addColumn(final double column) {
        setCurrentRow();
        _currentRow.add(String.valueOf(column));
        return this;
    }

    /**
     * Add column to the current row and move cursor to the next column.
     *
     * @param column column.
     * @return current object for chaining.
     */
    public CsvBuilder addColumn(final boolean column) {
        setCurrentRow();
        _currentRow.add(String.valueOf(column));
        return this;
    }

    /**
     * Add column to the current row and move cursor to the next column.
     *
     * @param column column.
     * @return current object for chaining.
     */
    public CsvBuilder addColumn(final String column) {
        setCurrentRow();
        _currentRow.add(getColumnForCsv(column));
        return this;
    }

    private String getColumnForCsv(final String column) {
        if (column == null) {
            return "";
        }
        boolean hasSpecial = false;
        for (int i = 0; i < SPECIAL.size(); i++) {
            if (column.contains(SPECIAL.get(i))) {
                hasSpecial = true;
            }
        }

        if (hasSpecial) {
            return "\"" + column.replaceAll("\"", "\"\"") + "\"";
        } else {
            return column;
        }
    }

    /**
     * Complete current row and move cursor to the next row.
     *
     * @return current object for chaining.
     */
    public CsvBuilder addRow() {
        setRows();
        setCurrentRow();
        _rows.add(_currentRow);
        _currentRow = null;
        return this;
    }

    /**
     * Convert rows and columns to CSV and clear internal buffers. After this call builder can be used to create new CSV.
     *
     * @return created CSV.
     */
    public String getCsv() {
        setRows();
        String csv = convertToScv(_rows);
        _rows = null;
        _currentRow = null;
        return csv;
    }

    private String convertToScv(final List<List<String>> rows) {
        StringBuilder result = new StringBuilder();

        for (List<String> row : rows) {
            int size = row.size();
            int sizeM1 = size - 1;
            for (int i = 0; i <= size; i++) {
                if (i == size) {
                    result.append(_rowSeparator);
                } else {
                    String column = row.get(i);
                    result.append(column);
                    if (i != sizeM1) {
                        result.append(_columnSeparator);
                    }
                }
            }
        }

        return result.toString();
    }

}
