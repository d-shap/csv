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

/**
 * Base class for all test classes.
 *
 * @author Dmitry Shapovalov
 */
public class CsvTest {

    /**
     * Create a new object.
     */
    protected CsvTest() {
        super();
    }

    /**
     * Create {@link CsvParserConfiguration} object.
     *
     * @return {@link CsvParserConfiguration} object.
     */
    public final CsvParserConfiguration createCsvParserConfiguration() {
        CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
        csvParserConfiguration.setCommaSeparator(true);
        csvParserConfiguration.setSemicolonSeparator(true);
        csvParserConfiguration.setCrSeparator(true);
        csvParserConfiguration.setLfSeparator(true);
        csvParserConfiguration.setCrLfSeparator(true);
        csvParserConfiguration.setColumnCountCheckEnabled(false);
        csvParserConfiguration.setSkipEmptyRowsEnabled(false);
        csvParserConfiguration.setMaxColumnLength(-1);
        csvParserConfiguration.setMaxColumnLengthCheckEnabled(false);
        return csvParserConfiguration;
    }

    /**
     * Create {@link CsvParser} object.
     *
     * @param comma     true if comma is a column separator.
     * @param semicolon true if semicolon is a column separator.
     * @param cr        true if CR is a row separator.
     * @param lf        true if LF is a row separator.
     * @param crlf      true if CRLF is a row separator.
     *
     * @return {@link CsvParser} object.
     */
    public final CsvParser createCsvParser(final boolean comma, final boolean semicolon, final boolean cr, final boolean lf, final boolean crlf) {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance();
        csvParserBuilder.setCommaSeparator(comma);
        csvParserBuilder.setSemicolonSeparator(semicolon);
        csvParserBuilder.setCrSeparator(cr);
        csvParserBuilder.setLfSeparator(lf);
        csvParserBuilder.setCrLfSeparator(crlf);
        csvParserBuilder.setColumnCountCheckEnabled(false);
        csvParserBuilder.setSkipEmptyRowsEnabled(false);
        csvParserBuilder.setMaxColumnLength(-1);
        csvParserBuilder.setMaxColumnLengthCheckEnabled(false);
        return csvParserBuilder.build();
    }

    /**
     * Create {@link CsvParser} object with column count check.
     *
     * @param comma     true if comma is a column separator.
     * @param semicolon true if semicolon is a column separator.
     * @param cr        true if CR is a row separator.
     * @param lf        true if LF is a row separator.
     * @param crlf      true if CRLF is a row separator.
     *
     * @return {@link CsvParser} object.
     */
    public final CsvParser createCsvParserWithColumnCountCheck(final boolean comma, final boolean semicolon, final boolean cr, final boolean lf, final boolean crlf) {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance();
        csvParserBuilder.setCommaSeparator(comma);
        csvParserBuilder.setSemicolonSeparator(semicolon);
        csvParserBuilder.setCrSeparator(cr);
        csvParserBuilder.setLfSeparator(lf);
        csvParserBuilder.setCrLfSeparator(crlf);
        csvParserBuilder.setColumnCountCheckEnabled(true);
        csvParserBuilder.setSkipEmptyRowsEnabled(false);
        csvParserBuilder.setMaxColumnLength(-1);
        csvParserBuilder.setMaxColumnLengthCheckEnabled(false);
        return csvParserBuilder.build();
    }

    /**
     * Create {@link CsvParser} object with skip empty rows enabled.
     *
     * @param comma     true if comma is a column separator.
     * @param semicolon true if semicolon is a column separator.
     * @param cr        true if CR is a row separator.
     * @param lf        true if LF is a row separator.
     * @param crlf      true if CRLF is a row separator.
     *
     * @return {@link CsvParser} object.
     */
    public final CsvParser createCsvParserWithSkipEmptyRows(final boolean comma, final boolean semicolon, final boolean cr, final boolean lf, final boolean crlf) {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance();
        csvParserBuilder.setCommaSeparator(comma);
        csvParserBuilder.setSemicolonSeparator(semicolon);
        csvParserBuilder.setCrSeparator(cr);
        csvParserBuilder.setLfSeparator(lf);
        csvParserBuilder.setCrLfSeparator(crlf);
        csvParserBuilder.setColumnCountCheckEnabled(false);
        csvParserBuilder.setSkipEmptyRowsEnabled(true);
        csvParserBuilder.setMaxColumnLength(-1);
        csvParserBuilder.setMaxColumnLengthCheckEnabled(false);
        return csvParserBuilder.build();
    }

    /**
     * Create {@link CsvParser} object with column count check and skip empty rows enabled.
     *
     * @param comma     true if comma is a column separator.
     * @param semicolon true if semicolon is a column separator.
     * @param cr        true if CR is a row separator.
     * @param lf        true if LF is a row separator.
     * @param crlf      true if CRLF is a row separator.
     *
     * @return {@link CsvParser} object.
     */
    public final CsvParser createCsvParserWithColumnCountCheckAndSkipEmptyRows(final boolean comma, final boolean semicolon, final boolean cr, final boolean lf, final boolean crlf) {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance();
        csvParserBuilder.setCommaSeparator(comma);
        csvParserBuilder.setSemicolonSeparator(semicolon);
        csvParserBuilder.setCrSeparator(cr);
        csvParserBuilder.setLfSeparator(lf);
        csvParserBuilder.setCrLfSeparator(crlf);
        csvParserBuilder.setColumnCountCheckEnabled(true);
        csvParserBuilder.setSkipEmptyRowsEnabled(true);
        csvParserBuilder.setMaxColumnLength(-1);
        csvParserBuilder.setMaxColumnLengthCheckEnabled(false);
        return csvParserBuilder.build();
    }

}
