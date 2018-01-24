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

import ru.d_shap.csv.state.SpecialCharacter;

/**
 * Class to create CSV from rows and columns.
 * Objects of this class are NOT reusable. After CSV is created, a new object should be used.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvPrinter implements AutoCloseable {

    static final String COMMA = String.valueOf((char) SpecialCharacter.COMMA);

    static final String SEMICOLON = String.valueOf((char) SpecialCharacter.SEMICOLON);

    static final String CR = String.valueOf((char) SpecialCharacter.CR);

    static final String LF = String.valueOf((char) SpecialCharacter.LF);

    static final String CRLF = CR + LF;

    private final Writer _writer;

    private final String _columnSeparator;

    private final String _rowSeparator;

    private final boolean _columnCountCheckEnabled;

    private final boolean _skipEmptyRowsEnabled;

    private final boolean _commaSeparator;

    private final boolean _semicolonSeparator;

    private final boolean _crSeparator;

    private final boolean _lfSeparator;

    private final boolean _crLfSeparator;

    private boolean _firstRow;

    private int _firstRowColumnCount;

    private int _currentColumnCount;

    CsvPrinter(final Writer writer, final String columnSeparator, final String rowSeparator, final boolean columnCountCheckEnabled, final boolean skipEmptyRowsEnabled, final boolean escapeAllSpecialCharactersEnabled) {
        super();
        _writer = writer;
        _columnSeparator = columnSeparator;
        _rowSeparator = rowSeparator;
        _columnCountCheckEnabled = columnCountCheckEnabled;
        _skipEmptyRowsEnabled = skipEmptyRowsEnabled;
        if (escapeAllSpecialCharactersEnabled) {
            _commaSeparator = true;
            _semicolonSeparator = true;
            _crSeparator = true;
            _lfSeparator = true;
            _crLfSeparator = true;
        } else {
            _commaSeparator = _columnSeparator.equals(COMMA);
            _semicolonSeparator = _columnSeparator.equals(SEMICOLON);
            _crSeparator = _rowSeparator.equals(CR);
            _lfSeparator = _rowSeparator.equals(LF);
            _crLfSeparator = _rowSeparator.equals(CRLF);
        }
        _firstRow = true;
        _firstRowColumnCount = 0;
        _currentColumnCount = 0;
    }

    /**
     * Add column value to the current row.
     *
     * @param column column value.
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
        boolean hasSpecialCharacters = hasSpecialCharacters(column);
        if (hasSpecialCharacters) {
            return "\"" + column.replaceAll("\"", "\"\"") + "\"";
        } else {
            return column;
        }
    }

    private boolean hasSpecialCharacters(final String column) {
        if (_commaSeparator && column.indexOf(SpecialCharacter.COMMA) >= 0) {
            return true;
        }
        if (_semicolonSeparator && column.indexOf(SpecialCharacter.SEMICOLON) >= 0) {
            return true;
        }
        if (_crSeparator && column.indexOf(SpecialCharacter.CR) >= 0) {
            return true;
        }
        if (_lfSeparator && column.indexOf(SpecialCharacter.LF) >= 0) {
            return true;
        }
        if (_crLfSeparator && column.contains(CRLF)) {
            return true;
        }
        return column.indexOf(SpecialCharacter.QUOT) >= 0;
    }

    private void doAddColumn(final String column) {
        try {
            if (_columnCountCheckEnabled && !_firstRow && _currentColumnCount >= _firstRowColumnCount) {
                throw new WrongColumnCountException();
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
            if (_skipEmptyRowsEnabled && _currentColumnCount == 0) {
                return this;
            }

            if (_firstRow) {
                _firstRowColumnCount = _currentColumnCount;
                _firstRow = false;
            } else if (_columnCountCheckEnabled && _firstRowColumnCount != _currentColumnCount) {
                throw new WrongColumnCountException();
            }

            _writer.write(_rowSeparator);
            _currentColumnCount = 0;
            return this;
        } catch (IOException ex) {
            throw new CsvIOException(ex);
        }
    }

    /**
     * Get created CSV, if StringWriter was used to create this object. Otherwise return null.
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

    @Override
    public void close() {
        try {
            _writer.close();
        } catch (IOException ex) {
            throw new CsvIOException(ex);
        }
    }

}
