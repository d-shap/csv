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
 * Builder class to create a {@link CsvPrinter} object.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvPrinterBuilder {

    private final CsvPrinterConfiguration _csvPrinterConfiguration;

    private CsvPrinterBuilder() {
        super();
        _csvPrinterConfiguration = new CsvPrinterConfiguration();
    }

    /**
     * Get a new builder instance.
     *
     * @return a new builder instance.
     */
    public static CsvPrinterBuilder getInstance() {
        return new CsvPrinterBuilder().setFormat(CsvFormat.DEFAULT);
    }

    /**
     * Set current settings as defined in the specified format.
     *
     * @param format the specified format.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setFormat(final CsvFormat format) {
        format.configure(this);
        return this;
    }

    /**
     * Set comma as a column separator.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setCommaSeparator() {
        _csvPrinterConfiguration.setCommaSeparator();
        return this;
    }

    /**
     * Set semicolon as a column separator.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setSemicolonSeparator() {
        _csvPrinterConfiguration.setSemicolonSeparator();
        return this;
    }

    /**
     * Set CR as a row separator.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setCrSeparator() {
        _csvPrinterConfiguration.setCrSeparator();
        return this;
    }

    /**
     * Set LF as a row separator.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setLfSeparator() {
        _csvPrinterConfiguration.setLfSeparator();
        return this;
    }

    /**
     * Set CRLF as a row separator.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setCrLfSeparator() {
        _csvPrinterConfiguration.setCrLfSeparator();
        return this;
    }

    /**
     * Specify whether all rows should have the same column count or not.
     *
     * @param columnCountCheckEnabled true if all rows should have the same column count.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setColumnCountCheckEnabled(final boolean columnCountCheckEnabled) {
        _csvPrinterConfiguration.setColumnCountCheckEnabled(columnCountCheckEnabled);
        return this;
    }

    /**
     * Specify whether all empty rows should be skipped or not.
     *
     * @param skipEmptyRowsEnabled true if all empty rows should be skipped.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setSkipEmptyRowsEnabled(final boolean skipEmptyRowsEnabled) {
        _csvPrinterConfiguration.setSkipEmptyRowsEnabled(skipEmptyRowsEnabled);
        return this;
    }

    /**
     * Specify whether all column and row separators should be escaped, or only separators specified in this object.
     *
     * @param escapeAllSeparatorsEnabled true if all column and row separators should be escaped, false if only separators specified in this object.
     *
     * @return current object for the method chaining.
     */
    public CsvPrinterBuilder setEscapeAllSeparatorsEnabled(final boolean escapeAllSeparatorsEnabled) {
        _csvPrinterConfiguration.setEscapeAllSeparatorsEnabled(escapeAllSeparatorsEnabled);
        return this;
    }

    /**
     * Create a {@link CsvPrinter} object.
     *
     * @return a {@link CsvPrinter} object.
     */
    public CsvPrinter build() {
        return build(new StringWriter());
    }

    /**
     * Create a {@link CsvPrinter} object.
     *
     * @param writer writer to write CSV.
     *
     * @return a {@link CsvPrinter} object.
     */
    public CsvPrinter build(final Writer writer) {
        CsvPrinterConfiguration csvPrinterConfiguration = _csvPrinterConfiguration.copyOf();
        return new CsvPrinter(writer, csvPrinterConfiguration);
    }

}
