///////////////////////////////////////////////////////////////////////////////////////////////////
// CSV parser converts source stream to rows and columns and vice versa.
// Copyright (C) 2016 Dmitry Shapovalov.
//
// This file is part of CSV parser.
//
// CSV parser is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// CSV parser is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.csv;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ru.d_shap.csv.state.AbstractState;

/**
 * Class to create CSV from rows and columns. Object can be used to create multiple CSVs.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvBuilder {

    private static final List<String> SPECIAL;

    static {
        SPECIAL = new LinkedList<String>();
        SPECIAL.add(String.valueOf((char) AbstractState.QUOT));
        SPECIAL.add(ColumnSeparators.COMMA.getValue());
        SPECIAL.add(ColumnSeparators.SEMICOLON.getValue());
        SPECIAL.add(RowSeparators.CR.getValue());
        SPECIAL.add(RowSeparators.LF.getValue());
    }

    private final String _columnSeparator;

    private final String _rowSeparator;

    private final boolean _checkRectangular;

    private List<List<String>> _rows;

    private List<String> _currentRow;

    private int _columnCount;

    /**
     * Create new object.
     */
    public CsvBuilder() {
        this(ColumnSeparators.COMMA, RowSeparators.CRLF, false);
    }

    /**
     * Create new object.
     *
     * @param checkRectangular check if all rows should have the same column count.
     */
    public CsvBuilder(final boolean checkRectangular) {
        this(ColumnSeparators.COMMA, RowSeparators.CRLF, checkRectangular);
    }

    /**
     * Create new object.
     *
     * @param columnSeparator separator between columns.
     */
    public CsvBuilder(final ColumnSeparators columnSeparator) {
        this(columnSeparator, RowSeparators.CRLF, false);
    }

    /**
     * Create new object.
     *
     * @param columnSeparator  separator between columns.
     * @param checkRectangular check if all rows should have the same column count.
     */
    public CsvBuilder(final ColumnSeparators columnSeparator, final boolean checkRectangular) {
        this(columnSeparator, RowSeparators.CRLF, checkRectangular);
    }

    /**
     * Create new object.
     *
     * @param rowSeparator separator between rows.
     */
    public CsvBuilder(final RowSeparators rowSeparator) {
        this(ColumnSeparators.COMMA, rowSeparator, false);
    }

    /**
     * Create new object.
     *
     * @param rowSeparator     separator between rows.
     * @param checkRectangular check if all rows should have the same column count.
     */
    public CsvBuilder(final RowSeparators rowSeparator, final boolean checkRectangular) {
        this(ColumnSeparators.COMMA, rowSeparator, checkRectangular);
    }

    /**
     * Create new object.
     *
     * @param columnSeparator separator between columns.
     * @param rowSeparator    separator between rows.
     */
    public CsvBuilder(final ColumnSeparators columnSeparator, final RowSeparators rowSeparator) {
        this(columnSeparator, rowSeparator, false);
    }

    /**
     * Create new object.
     *
     * @param columnSeparator  separator between columns.
     * @param rowSeparator     separator between rows.
     * @param checkRectangular check if all rows should have the same column count.
     */
    public CsvBuilder(final ColumnSeparators columnSeparator, final RowSeparators rowSeparator, final boolean checkRectangular) {
        super();
        _columnSeparator = columnSeparator.getValue();
        _rowSeparator = rowSeparator.getValue();
        _checkRectangular = checkRectangular;
        _rows = null;
        _currentRow = null;
        _columnCount = -1;
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
        if (_columnCount < 0) {
            _columnCount = _currentRow.size();
        }
        if (_checkRectangular && _columnCount != _currentRow.size()) {
            throw new NotRectangularException();
        }
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
        StringWriter writer = new StringWriter();
        writeTo(writer);
        return writer.getBuffer().toString();
    }

    /**
     * Writes CSV to the writer. After this call builder can be used to create new CSV.
     *
     * @param writer writer to write CSV.
     */
    public void writeTo(final Writer writer) {
        setRows();
        convertToCsv(writer);
        _rows = null;
        _currentRow = null;
        _columnCount = -1;
    }

    private void convertToCsv(final Writer writer) {
        try {
            int rowCount = _rows.size();
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                List<String> row = _rows.get(rowIndex);
                int columnCount = row.size();
                int columnCountM1 = columnCount - 1;
                for (int columnIndex = 0; columnIndex <= columnCount; columnIndex++) {
                    if (columnIndex == columnCount) {
                        writer.write(_rowSeparator);
                    } else {
                        String column = row.get(columnIndex);
                        writer.write(column);
                        if (columnIndex != columnCountM1) {
                            writer.write(_columnSeparator);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            throw new CsvIOException(ex);
        }
    }

}
