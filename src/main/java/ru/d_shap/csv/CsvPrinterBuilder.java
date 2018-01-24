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

import java.io.StringWriter;
import java.io.Writer;

/**
 * Builder class to create {@link CsvPrinter} object.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvPrinterBuilder {

    private String _columnSeparator;

    private String _rowSeparator;

    private boolean _columnCountCheckEnabled;

    private boolean _skipEmptyRowsEnabled;

    private boolean _escapeAllSpecialCharactersEnabled;

    private CsvPrinterBuilder() {
        super();
        _columnSeparator = CsvPrinter.COMMA;
        _rowSeparator = CsvPrinter.CRLF;
        _columnCountCheckEnabled = true;
        _skipEmptyRowsEnabled = false;
        _escapeAllSpecialCharactersEnabled = true;
    }

    /**
     * Get new builder instance.
     *
     * @return new builder instance.
     */
    public static CsvPrinterBuilder getInstance() {
        return new CsvPrinterBuilder();
    }

    /**
     * Set comma as a column separator.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setCommaSeparator() {
        _columnSeparator = CsvPrinter.COMMA;
        return this;
    }

    /**
     * Set semicolon as a column separator.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setSemicolonSeparator() {
        _columnSeparator = CsvPrinter.SEMICOLON;
        return this;
    }

    /**
     * Set CR as a row separator.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setCrSeparator() {
        _rowSeparator = CsvPrinter.CR;
        return this;
    }

    /**
     * Set LF as a row separator.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setLfSeparator() {
        _rowSeparator = CsvPrinter.LF;
        return this;
    }

    /**
     * Set CRLF as a row separator.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setCrLfSeparator() {
        _rowSeparator = CsvPrinter.CRLF;
        return this;
    }

    /**
     * Specify whether all rows should have the same column count or not.
     *
     * @param columnCountCheckEnabled true if all rows should have the same column count.
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setColumnCountCheckEnabled(final boolean columnCountCheckEnabled) {
        _columnCountCheckEnabled = columnCountCheckEnabled;
        return this;
    }

    /**
     * Specify whether all empty rows should be skipped or not.
     *
     * @param skipEmptyRowsEnabled true if all empty rows should be skipped.
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setSkipEmptyRowsEnabled(final boolean skipEmptyRowsEnabled) {
        _skipEmptyRowsEnabled = skipEmptyRowsEnabled;
        return this;
    }

    /**
     * Specify whether all column and row separators should be escaped, or only specified in this builder object.
     *
     * @param escapeAllSpecialCharactersEnabled true if all column and row separators should be escaped (Comma,
     *                                          Semicolon, CR, LF, CRLF), false if only specified in this builder object.
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setEscapeAllSpecialCharactersEnabled(final boolean escapeAllSpecialCharactersEnabled) {
        _escapeAllSpecialCharactersEnabled = escapeAllSpecialCharactersEnabled;
        return this;
    }

    /**
     * Create {@link CsvPrinter} object.
     *
     * @return {@link CsvPrinter} object.
     */
    public CsvPrinter build() {
        return build(new StringWriter());
    }

    /**
     * Create {@link CsvPrinter} object.
     *
     * @param writer writer to write CSV.
     * @return {@link CsvPrinter} object.
     */
    public CsvPrinter build(final Writer writer) {
        return new CsvPrinter(writer, _columnSeparator, _rowSeparator, _columnCountCheckEnabled, _skipEmptyRowsEnabled, _escapeAllSpecialCharactersEnabled);
    }

}
