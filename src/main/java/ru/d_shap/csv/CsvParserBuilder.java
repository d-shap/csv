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

import java.io.Reader;
import java.util.List;

import ru.d_shap.csv.handler.CsvEventHandler;

/**
 * Builder class to create a {@link CsvParser} object.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvParserBuilder {

    private final CsvParserConfiguration _csvParserConfiguration;

    private CsvParserBuilder() {
        super();
        _csvParserConfiguration = new CsvParserConfiguration();
    }

    /**
     * Get a new builder instance.
     *
     * @return a new builder instance.
     */
    public static CsvParserBuilder getInstance() {
        return new CsvParserBuilder().setFormat(CsvFormat.DEFAULT);
    }

    /**
     * Set current settings as defined in the specified format.
     *
     * @param format the specified format.
     *
     * @return current object for the method chaining.
     */
    public CsvParserBuilder setFormat(final CsvFormat format) {
        format.configure(this);
        return this;
    }

    /**
     * Set comma as a column separator.
     *
     * @param commaSeparator true if comma is a column separator.
     *
     * @return current object for the method chaining.
     */
    public CsvParserBuilder setCommaSeparator(final boolean commaSeparator) {
        _csvParserConfiguration.setCommaSeparator(commaSeparator);
        return this;
    }

    /**
     * Set semicolon as a column separator.
     *
     * @param semicolonSeparator true if semicolon is a column separator.
     *
     * @return current object for the method chaining.
     */
    public CsvParserBuilder setSemicolonSeparator(final boolean semicolonSeparator) {
        _csvParserConfiguration.setSemicolonSeparator(semicolonSeparator);
        return this;
    }

    /**
     * Set CR as a row separator.
     *
     * @param crSeparator true if CR is a row separator.
     *
     * @return current object for the method chaining.
     */
    public CsvParserBuilder setCrSeparator(final boolean crSeparator) {
        _csvParserConfiguration.setCrSeparator(crSeparator);
        return this;
    }

    /**
     * Set LF as a row separator.
     *
     * @param lfSeparator true if LF is a row separator.
     *
     * @return current object for the method chaining.
     */
    public CsvParserBuilder setLfSeparator(final boolean lfSeparator) {
        _csvParserConfiguration.setLfSeparator(lfSeparator);
        return this;
    }

    /**
     * Set CRLF as a row separator.
     *
     * @param crLfSeparator true if CRLF is a row separator.
     *
     * @return current object for the method chaining.
     */
    public CsvParserBuilder setCrLfSeparator(final boolean crLfSeparator) {
        _csvParserConfiguration.setCrLfSeparator(crLfSeparator);
        return this;
    }

    /**
     * Specify whether all rows should have the same column count or not.
     *
     * @param columnCountCheckEnabled true if all rows should have the same column count.
     *
     * @return current object for the method chaining.
     */
    public CsvParserBuilder setColumnCountCheckEnabled(final boolean columnCountCheckEnabled) {
        _csvParserConfiguration.setColumnCountCheckEnabled(columnCountCheckEnabled);
        return this;
    }

    /**
     * Specify whether all empty rows should be skipped or not.
     *
     * @param skipEmptyRowsEnabled true if all empty rows should be skipped.
     *
     * @return current object for the method chaining.
     */
    public CsvParserBuilder setSkipEmptyRowsEnabled(final boolean skipEmptyRowsEnabled) {
        _csvParserConfiguration.setSkipEmptyRowsEnabled(skipEmptyRowsEnabled);
        return this;
    }

    /**
     * Set the maximum length of a column value. If a column value length is greater then the maximum
     * column value length, then either the rest of a column value is skipped, or an exception is thrown.
     *
     * @param maxColumnLength the maximum length of a column value, or negative number for no column value length restriction.
     *
     * @return current object for the method chaining.
     */
    public CsvParserBuilder setMaxColumnLength(final int maxColumnLength) {
        _csvParserConfiguration.setMaxColumnLength(maxColumnLength);
        return this;
    }

    /**
     * Specify whether an excepton should be thrown if a column value length exceeds the maximum column value length or not.
     *
     * @param maxColumnLengthCheckEnabled true if an excepton should be thrown.
     *
     * @return current object for the method chaining.
     */
    public CsvParserBuilder setMaxColumnLengthCheckEnabled(final boolean maxColumnLengthCheckEnabled) {
        _csvParserConfiguration.setMaxColumnLengthCheckEnabled(maxColumnLengthCheckEnabled);
        return this;
    }

    /**
     * Create a {@link CsvParser} object.
     *
     * @return a {@link CsvParser} object.
     */
    public CsvParser build() {
        CsvParserConfiguration csvParserConfiguration = _csvParserConfiguration.copyOf();
        return new CsvParser(csvParserConfiguration);
    }

    /**
     * Create a {@link CsvParser} object, then parse CSV and define rows and columns.
     *
     * @param charSequence CSV to parse.
     *
     * @return list of rows, each row is a list of columns.
     */
    public List<List<String>> parse(final CharSequence charSequence) {
        return build().parse(charSequence);
    }

    /**
     * Create a {@link CsvParser} object, then parse CSV and push events to the specified event handler.
     *
     * @param charSequence    CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     */
    public void parse(final CharSequence charSequence, final CsvEventHandler csvEventHandler) {
        build().parse(charSequence, csvEventHandler);
    }

    /**
     * Create a {@link CsvParser} object, then parse CSV and define rows and columns.
     *
     * @param reader CSV to parse.
     *
     * @return list of rows, each row is a list of columns.
     */
    public List<List<String>> parse(final Reader reader) {
        return build().parse(reader);
    }

    /**
     * Create a {@link CsvParser} object, then parse CSV and push events to the specified event handler.
     *
     * @param reader          CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     */
    public void parse(final Reader reader, final CsvEventHandler csvEventHandler) {
        build().parse(reader, csvEventHandler);
    }

}
