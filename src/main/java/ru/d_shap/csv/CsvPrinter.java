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
import java.util.List;

/**
 * Class to create CSV from rows and columns.
 * Objects of this class are NOT reusable. After CSV is created, a new object should be used.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvPrinter implements AutoCloseable {

    private static final int INITIAL_ROW_COLUMN_COUNT = -1;

    private final Writer _writer;

    private final CsvPrinterConfiguration _csvPrinterConfiguration;

    private int _firstRowColumnCount;

    private int _currentColumnCount;

    CsvPrinter(final Writer writer, final CsvPrinterConfiguration csvPrinterConfiguration) {
        super();
        _writer = writer;
        csvPrinterConfiguration.validate();
        _csvPrinterConfiguration = csvPrinterConfiguration;
        _firstRowColumnCount = INITIAL_ROW_COLUMN_COUNT;
        _currentColumnCount = 0;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinter addColumn(final char column) {
        doAddColumn(String.valueOf(column));
        return this;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinter addColumn(final int column) {
        doAddColumn(String.valueOf(column));
        return this;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinter addColumn(final long column) {
        doAddColumn(String.valueOf(column));
        return this;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinter addColumn(final float column) {
        doAddColumn(String.valueOf(column));
        return this;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinter addColumn(final double column) {
        doAddColumn(String.valueOf(column));
        return this;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinter addColumn(final boolean column) {
        doAddColumn(String.valueOf(column));
        return this;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinter addColumn(final String column) {
        doAddColumn(getColumnForCsv(column));
        return this;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinter addColumn(final Object column) {
        doAddColumn(getColumnForCsv(column));
        return this;
    }

    private String getColumnForCsv(final Object column) {
        if (column == null) {
            return "";
        } else {
            return getColumnForCsv(column.toString());
        }
    }

    private String getColumnForCsv(final String column) {
        if (column == null) {
            return "";
        }
        boolean hasSpecialCharacters = _csvPrinterConfiguration.hasSpecialCharacters(column);
        if (hasSpecialCharacters) {
            return "\"" + column.replaceAll("\"", "\"\"") + "\"";
        } else {
            return column;
        }
    }

    private void doAddColumn(final String column) {
        try {
            if (_csvPrinterConfiguration.isColumnCountCheckEnabled() && _firstRowColumnCount != INITIAL_ROW_COLUMN_COUNT && _currentColumnCount >= _firstRowColumnCount) {
                throw new WrongColumnCountException();
            }

            if (_currentColumnCount > 0) {
                _writer.write(_csvPrinterConfiguration.getColumnSeparator());
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
     * @return current object for the method chaining.
     */
    public CsvPrinter addRow() {
        try {
            if (_csvPrinterConfiguration.isSkipEmptyRowsEnabled() && _currentColumnCount == 0) {
                return this;
            }

            if (_firstRowColumnCount == INITIAL_ROW_COLUMN_COUNT) {
                _firstRowColumnCount = _currentColumnCount;
            } else if (_csvPrinterConfiguration.isColumnCountCheckEnabled() && _firstRowColumnCount != _currentColumnCount) {
                throw new WrongColumnCountException();
            }

            _writer.write(_csvPrinterConfiguration.getRowSeparator());
            _currentColumnCount = 0;
            return this;
        } catch (IOException ex) {
            throw new CsvIOException(ex);
        }
    }

    /**
     * Add column values from the specified list and then add row separator and start a new row.
     *
     * @param columns the specified list of column values.
     * @param <T>     generic type of column value.
     *
     * @return current object for the method chaining.
     */
    public <T> CsvPrinter addRow(final List<T> columns) {
        for (Object column : columns) {
            addColumn(column);
        }
        addRow();
        return this;
    }

    /**
     * Add all rows in the specified list of rows, where each row is a list of columns.
     *
     * @param rows the specified list of rows, where each row is a list of columns.
     * @param <T>  generic type of column value.
     *
     * @return current object for the method chaining.
     */
    public <T> CsvPrinter addRows(final List<List<T>> rows) {
        for (List<?> row : rows) {
            addRow(row);
        }
        return this;
    }

    /**
     * Get created CSV, if {@link StringWriter} was used to create this object. Otherwise return null.
     *
     * @return created CSV or null, if NOT {@link StringWriter} was used to create this object.
     */
    public String getCsv() {
        if (_writer instanceof StringWriter) {
            return ((StringWriter) _writer).getBuffer().toString();
        } else {
            return null;
        }
    }

    @Override
    public void close() {
        try {
            _writer.close();
        } catch (IOException ex) {
            throw new CsvIOException(ex);
        }
    }

}
