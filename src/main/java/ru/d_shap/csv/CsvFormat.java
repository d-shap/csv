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
 * Predefined CSV formats.
 * This formats can be used in the {@link CsvParserBuilder} object to configure {@link CsvParser} object
 * and in the {@link CsvPrinterBuilder} object to configure {@link CsvPrinter} object.
 *
 * @author Dmitry Shapovalov
 */
public enum CsvFormat {

    /**
     * The default CSV format. This format is used when an instance of the {@link CsvParserBuilder}
     * object or the {@link CsvPrinterBuilder} object is created.
     */
    DEFAULT {
        @Override
        void configure(final CsvParserBuilder csvParserBuilder) {
            csvParserBuilder.setCommaSeparator(true);
            csvParserBuilder.setSemicolonSeparator(true);
            csvParserBuilder.setCrSeparator(false);
            csvParserBuilder.setLfSeparator(true);
            csvParserBuilder.setCrLfSeparator(true);
            csvParserBuilder.setColumnCountCheckEnabled(false);
            csvParserBuilder.setSkipEmptyRowsEnabled(false);
            csvParserBuilder.setMaxColumnLength(-1);
            csvParserBuilder.setMaxColumnLengthCheckEnabled(false);
        }

        @Override
        void configure(final CsvPrinterBuilder csvPrinterBuilder) {
            csvPrinterBuilder.setCommaSeparator();
            csvPrinterBuilder.setCrLfSeparator();
            csvPrinterBuilder.setColumnCountCheckEnabled(false);
            csvPrinterBuilder.setSkipEmptyRowsEnabled(false);
            csvPrinterBuilder.setEscapeAllSeparatorsEnabled(true);
        }
    },

    /**
     * CSV format according to RFC 4180.
     */
    RFC4180 {
        @Override
        void configure(final CsvParserBuilder csvParserBuilder) {
            csvParserBuilder.setCommaSeparator(true);
            csvParserBuilder.setSemicolonSeparator(false);
            csvParserBuilder.setCrSeparator(false);
            csvParserBuilder.setLfSeparator(false);
            csvParserBuilder.setCrLfSeparator(true);
            csvParserBuilder.setColumnCountCheckEnabled(true);
            csvParserBuilder.setSkipEmptyRowsEnabled(false);
            csvParserBuilder.setMaxColumnLength(-1);
            csvParserBuilder.setMaxColumnLengthCheckEnabled(false);
        }

        @Override
        void configure(final CsvPrinterBuilder csvPrinterBuilder) {
            csvPrinterBuilder.setCommaSeparator();
            csvPrinterBuilder.setCrLfSeparator();
            csvPrinterBuilder.setColumnCountCheckEnabled(true);
            csvPrinterBuilder.setSkipEmptyRowsEnabled(false);
            csvPrinterBuilder.setEscapeAllSeparatorsEnabled(false);
        }
    },

    /**
     * CSV format used by Excel. Column separator is locale dependent. In this format comma is used.
     * If semicolon is used as a column separator, then this should be altered manually.
     */
    EXCEL {
        @Override
        void configure(final CsvParserBuilder csvParserBuilder) {
            csvParserBuilder.setCommaSeparator(true);
            csvParserBuilder.setSemicolonSeparator(false);
            csvParserBuilder.setCrSeparator(false);
            csvParserBuilder.setLfSeparator(false);
            csvParserBuilder.setCrLfSeparator(true);
            csvParserBuilder.setColumnCountCheckEnabled(true);
            csvParserBuilder.setSkipEmptyRowsEnabled(false);
            csvParserBuilder.setMaxColumnLength(-1);
            csvParserBuilder.setMaxColumnLengthCheckEnabled(false);
        }

        @Override
        void configure(final CsvPrinterBuilder csvPrinterBuilder) {
            csvPrinterBuilder.setCommaSeparator();
            csvPrinterBuilder.setCrLfSeparator();
            csvPrinterBuilder.setColumnCountCheckEnabled(true);
            csvPrinterBuilder.setSkipEmptyRowsEnabled(false);
            csvPrinterBuilder.setEscapeAllSeparatorsEnabled(false);
        }
    };

    abstract void configure(CsvParserBuilder csvParserBuilder);

    abstract void configure(CsvPrinterBuilder csvPrinterBuilder);

}
