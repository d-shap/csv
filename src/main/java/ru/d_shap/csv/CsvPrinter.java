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
 * Method {@link #getCsv()} is a convenient method when StringWriter is used. This method returns
 * generated CSV as String. For all other writer objects this method returns null.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvPrinter {

    private static final String COMMA = String.valueOf((char) SpecialCharacter.COMMA);

    private static final String SEMICOLON = String.valueOf((char) SpecialCharacter.SEMICOLON);

    private static final String CR = String.valueOf((char) SpecialCharacter.CR);

    private static final String LF = String.valueOf((char) SpecialCharacter.LF);

    private static final String CRLF = CR + LF;

    private final Writer _writer;

    private final String _columnSeparator;

    private final String _rowSeparator;

    private final boolean _columnCountCheckEnabled;

    private final boolean _commaSeparator;

    private final boolean _semicolonSeparator;

    private final boolean _crSeparator;

    private final boolean _lfSeparator;

    private final boolean _crLfSeparator;

    private boolean _firstRow;

    private int _firstRowColumnCount;

    private int _currentColumnCount;

    private CsvPrinter(final Writer writer, final String columnSeparator, final String rowSeparator, final boolean columnCountCheckEnabled, final boolean escapeAllSpecialCharactersEnabled) {
        super();
        _writer = writer;
        _columnSeparator = columnSeparator;
        _rowSeparator = rowSeparator;
        _columnCountCheckEnabled = columnCountCheckEnabled;
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
     * Get new builder instance to create CSV printer.
     *
     * @return new builder instance.
     */
    public static Builder createBuilder() {
        return new Builder();
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

    /**
     * Builder class to create CSV printer instance.
     *
     * @author Dmitry Shapovalov
     */
    public static final class Builder {

        private String _columnSeparator;

        private String _rowSeparator;

        private boolean _columnCountCheckEnabled;

        private boolean _escapeAllSpecialCharactersEnabled;

        private Builder() {
            super();
            _columnSeparator = COMMA;
            _rowSeparator = CRLF;
            _columnCountCheckEnabled = true;
            _escapeAllSpecialCharactersEnabled = true;
        }

        /**
         * Set comma as a column separator.
         *
         * @return current object for the method chaining.
         */
        public Builder setCommaSeparator() {
            _columnSeparator = COMMA;
            return this;
        }

        /**
         * Set semicolon as a column separator.
         *
         * @return current object for the method chaining.
         */
        public Builder setSemicolonSeparator() {
            _columnSeparator = SEMICOLON;
            return this;
        }

        /**
         * Set CR as a row separator.
         *
         * @return current object for the method chaining.
         */
        public Builder setCrSeparator() {
            _rowSeparator = CR;
            return this;
        }

        /**
         * Set LF as a row separator.
         *
         * @return current object for the method chaining.
         */
        public Builder setLfSeparator() {
            _rowSeparator = LF;
            return this;
        }

        /**
         * Set CRLF as a row separator.
         *
         * @return current object for the method chaining.
         */
        public Builder setCrLfSeparator() {
            _rowSeparator = CRLF;
            return this;
        }

        /**
         * Specify whether all rows should have the same column count or not.
         *
         * @param columnCountCheckEnabled true if all rows should have the same column count.
         * @return current object for the method chaining.
         */
        public Builder setColumnCountCheckEnabled(final boolean columnCountCheckEnabled) {
            _columnCountCheckEnabled = columnCountCheckEnabled;
            return this;
        }

        /**
         * Specify whether all column and row separators should be escaped, or only specified in this builder object.
         *
         * @param escapeAllSpecialCharactersEnabled true if all column and row separators should be escaped (COMMA,
         *                                          SEMICOLON, CR, LF, CRLF), false if only specified in this builder object.
         * @return current object for the method chaining.
         */
        public Builder setEscapeAllSpecialCharactersEnabled(final boolean escapeAllSpecialCharactersEnabled) {
            _escapeAllSpecialCharactersEnabled = escapeAllSpecialCharactersEnabled;
            return this;
        }

        /**
         * Create CSV printer.
         *
         * @return CSV printer.
         */
        public CsvPrinter build() {
            return build(new StringWriter());
        }

        /**
         * Create CSV printer.
         *
         * @param writer writer to write CSV.
         * @return CSV printer.
         */
        public CsvPrinter build(final Writer writer) {
            return new CsvPrinter(writer, _columnSeparator, _rowSeparator, _columnCountCheckEnabled, _escapeAllSpecialCharactersEnabled);
        }

    }

}
