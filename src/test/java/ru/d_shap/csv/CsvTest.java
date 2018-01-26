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
 * Class to create {@link CsvParser} objects and {@link CsvPrinter} objects.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvTest {

    private CsvTest() {
        super();
    }

    /**
     * Create {@link CsvParser} object.
     *
     * @param comma     true if comma is a column separator.
     * @param semicolon true if semicolon is a column separator.
     * @param cr        true if CR is a row separator.
     * @param lf        true if LF is a row separator.
     * @param crlf      true if CRLF is a row separator.
     * @return {@link CsvParser} object.
     */
    public static CsvParser createCsvParser(final boolean comma, final boolean semicolon, final boolean cr, final boolean lf, final boolean crlf) {
        return createCsvParser(comma, semicolon, cr, lf, crlf, false);
    }

    /**
     * Create {@link CsvParser} object.
     *
     * @param comma                   true if comma is a column separator.
     * @param semicolon               true if semicolon is a column separator.
     * @param cr                      true if CR is a row separator.
     * @param lf                      true if LF is a row separator.
     * @param crlf                    true if CRLF is a row separator.
     * @param columnCountCheckEnabled true if all rows should have the same column count.
     * @return {@link CsvParser} object.
     */
    public static CsvParser createCsvParser(final boolean comma, final boolean semicolon, final boolean cr, final boolean lf, final boolean crlf, final boolean columnCountCheckEnabled) {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance();
        csvParserBuilder.setCommaSeparator(comma);
        csvParserBuilder.setSemicolonSeparator(semicolon);
        csvParserBuilder.setCrSeparator(cr);
        csvParserBuilder.setLfSeparator(lf);
        csvParserBuilder.setCrLfSeparator(crlf);
        csvParserBuilder.setColumnCountCheckEnabled(columnCountCheckEnabled);
        csvParserBuilder.setSkipEmptyRowsEnabled(false);
        csvParserBuilder.setMaxColumnLength(-1);
        csvParserBuilder.setMaxColumnLengthCheckEnabled(false);
        return csvParserBuilder.build();
    }

}
