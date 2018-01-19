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
// along with this program. If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.csv;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import ru.d_shap.csv.state.SpecialCharacter;

/**
 * Class to create CSV from rows and columns. If object is created with writer parameter, then CSV is writen
 * directly to the writer. If object is created without writer parameter, then StringWriter is used. In this case
 * everithing is stored in memory. Method {@link #getCsv()} is a convenient method when StringWriter is used.
 * This method returns generated CSV as String. For all other writer objects this method returns null.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvPrinter {

    private static final List<String> SPECIAL;

    static {
        SPECIAL = new LinkedList<>();
        SPECIAL.add(String.valueOf((char) SpecialCharacter.QUOT));
        SPECIAL.add(ColumnSeparators.COMMA.getValue());
        SPECIAL.add(ColumnSeparators.SEMICOLON.getValue());
        SPECIAL.add(RowSeparators.CR.getValue());
        SPECIAL.add(RowSeparators.LF.getValue());
    }

    private final Writer _writer;

    private final String _columnSeparator;

    private final String _rowSeparator;

    private final boolean _checkRectangular;

    private boolean _firstRow;

    private int _firstRowColumnCount;

    private int _currentColumnCount;

    /**
     * Create new object.
     */
    public CsvPrinter() {
        this(new StringWriter(), ColumnSeparators.COMMA, RowSeparators.CRLF, false);
    }

    /**
     * Create new object.
     *
     * @param checkRectangular check if all rows should have the same column count.
     */
    public CsvPrinter(final boolean checkRectangular) {
        this(new StringWriter(), ColumnSeparators.COMMA, RowSeparators.CRLF, checkRectangular);
    }

    /**
     * Create new object.
     *
     * @param columnSeparator separator between columns.
     */
    public CsvPrinter(final ColumnSeparators columnSeparator) {
        this(new StringWriter(), columnSeparator, RowSeparators.CRLF, false);
    }

    /**
     * Create new object.
     *
     * @param columnSeparator  separator between columns.
     * @param checkRectangular check if all rows should have the same column count.
     */
    public CsvPrinter(final ColumnSeparators columnSeparator, final boolean checkRectangular) {
        this(new StringWriter(), columnSeparator, RowSeparators.CRLF, checkRectangular);
    }

    /**
     * Create new object.
     *
     * @param rowSeparator separator between rows.
     */
    public CsvPrinter(final RowSeparators rowSeparator) {
        this(new StringWriter(), ColumnSeparators.COMMA, rowSeparator, false);
    }

    /**
     * Create new object.
     *
     * @param rowSeparator     separator between rows.
     * @param checkRectangular check if all rows should have the same column count.
     */
    public CsvPrinter(final RowSeparators rowSeparator, final boolean checkRectangular) {
        this(new StringWriter(), ColumnSeparators.COMMA, rowSeparator, checkRectangular);
    }

    /**
     * Create new object.
     *
     * @param columnSeparator separator between columns.
     * @param rowSeparator    separator between rows.
     */
    public CsvPrinter(final ColumnSeparators columnSeparator, final RowSeparators rowSeparator) {
        this(new StringWriter(), columnSeparator, rowSeparator, false);
    }

    /**
     * Create new object.
     *
     * @param columnSeparator  separator between columns.
     * @param rowSeparator     separator between rows.
     * @param checkRectangular check if all rows should have the same column count.
     */
    public CsvPrinter(final ColumnSeparators columnSeparator, final RowSeparators rowSeparator, final boolean checkRectangular) {
        this(new StringWriter(), columnSeparator, rowSeparator, checkRectangular);
    }

    /**
     * Create new object.
     *
     * @param writer writer to write CSV.
     */
    public CsvPrinter(final Writer writer) {
        this(writer, ColumnSeparators.COMMA, RowSeparators.CRLF, false);
    }

    /**
     * Create new object.
     *
     * @param writer           writer to write CSV.
     * @param checkRectangular check if all rows should have the same column count.
     */
    public CsvPrinter(final Writer writer, final boolean checkRectangular) {
        this(writer, ColumnSeparators.COMMA, RowSeparators.CRLF, checkRectangular);
    }

    /**
     * Create new object.
     *
     * @param writer          writer to write CSV.
     * @param columnSeparator separator between columns.
     */
    public CsvPrinter(final Writer writer, final ColumnSeparators columnSeparator) {
        this(writer, columnSeparator, RowSeparators.CRLF, false);
    }

    /**
     * Create new object.
     *
     * @param writer           writer to write CSV.
     * @param columnSeparator  separator between columns.
     * @param checkRectangular check if all rows should have the same column count.
     */
    public CsvPrinter(final Writer writer, final ColumnSeparators columnSeparator, final boolean checkRectangular) {
        this(writer, columnSeparator, RowSeparators.CRLF, checkRectangular);
    }

    /**
     * Create new object.
     *
     * @param writer       writer to write CSV.
     * @param rowSeparator separator between rows.
     */
    public CsvPrinter(final Writer writer, final RowSeparators rowSeparator) {
        this(writer, ColumnSeparators.COMMA, rowSeparator, false);
    }

    /**
     * Create new object.
     *
     * @param writer           writer to write CSV.
     * @param rowSeparator     separator between rows.
     * @param checkRectangular check if all rows should have the same column count.
     */
    public CsvPrinter(final Writer writer, final RowSeparators rowSeparator, final boolean checkRectangular) {
        this(writer, ColumnSeparators.COMMA, rowSeparator, checkRectangular);
    }

    /**
     * Create new object.
     *
     * @param writer          writer to write CSV.
     * @param columnSeparator separator between columns.
     * @param rowSeparator    separator between rows.
     */
    public CsvPrinter(final Writer writer, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator) {
        this(writer, columnSeparator, rowSeparator, false);
    }

    /**
     * Create new object.
     *
     * @param writer           writer to write CSV.
     * @param columnSeparator  separator between columns.
     * @param rowSeparator     separator between rows.
     * @param checkRectangular check if all rows should have the same column count.
     */
    public CsvPrinter(final Writer writer, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator, final boolean checkRectangular) {
        super();
        _writer = writer;
        _columnSeparator = columnSeparator.getValue();
        _rowSeparator = rowSeparator.getValue();
        _checkRectangular = checkRectangular;
        _firstRow = true;
        _firstRowColumnCount = 0;
        _currentColumnCount = 0;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
     * @return current object for chaining.
     */
    public CsvPrinter addColumn(final int column) {
        doAddColumn(String.valueOf(column));
        return this;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
     * @return current object for chaining.
     */
    public CsvPrinter addColumn(final long column) {
        doAddColumn(String.valueOf(column));
        return this;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
     * @return current object for chaining.
     */
    public CsvPrinter addColumn(final float column) {
        doAddColumn(String.valueOf(column));
        return this;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
     * @return current object for chaining.
     */
    public CsvPrinter addColumn(final double column) {
        doAddColumn(String.valueOf(column));
        return this;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
     * @return current object for chaining.
     */
    public CsvPrinter addColumn(final boolean column) {
        doAddColumn(String.valueOf(column));
        return this;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
     * @return current object for chaining.
     */
    public CsvPrinter addColumn(final String column) {
        doAddColumn(getColumnForCsv(column));
        return this;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
     * @return current object for chaining.
     */
    public CsvPrinter addColumn(final Object column) {
        doAddColumn(getColumnForCsv(column));
        return this;
    }

    private String getColumnForCsv(final Object column) {
        if (column == null) {
            return "";
        }
        return getColumnForCsv(column.toString());
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

    private void doAddColumn(final String column) {
        try {
            if (_checkRectangular && !_firstRow && _currentColumnCount >= _firstRowColumnCount) {
                throw new NotRectangularException();
            }

            if (_currentColumnCount > 0) {
                _writer.write(_columnSeparator);
            }
            _writer.write(column);
            _currentColumnCount++;
        } catch (IOException ex) {
            throw new CsvIOException(ex);
        }
    }

    /**
     * Add row separator and start a new row.
     *
     * @return current object for chaining.
     */
    public CsvPrinter addRow() {
        try {
            if (_firstRow) {
                _firstRowColumnCount = _currentColumnCount;
                _firstRow = false;
            }
            if (_checkRectangular && _firstRowColumnCount != _currentColumnCount) {
                throw new NotRectangularException();
            }

            _writer.write(_rowSeparator);
            _currentColumnCount = 0;
            return this;
        } catch (IOException ex) {
            throw new CsvIOException(ex);
        }
    }

    /**
     * Return created CSV, if StringWriter was used to create this object, implicitly or explicitly.
     * Otherwise return null.
     *
     * @return created CSV or null, if NOT StringWriter was used to create this object.
     */
    public String getCsv() {
        if (_writer instanceof StringWriter) {
            return ((StringWriter) _writer).getBuffer().toString();
        } else {
            return null;
        }
    }

}