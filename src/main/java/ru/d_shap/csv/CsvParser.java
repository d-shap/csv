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
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import ru.d_shap.csv.handler.ColumnCountEventHandler;
import ru.d_shap.csv.handler.CsvConfigurable;
import ru.d_shap.csv.handler.CsvEventHandler;
import ru.d_shap.csv.handler.DimensionEventHandler;
import ru.d_shap.csv.handler.ListEventHandler;
import ru.d_shap.csv.state.SpecialCharacter;
import ru.d_shap.csv.state.State;
import ru.d_shap.csv.state.StateHandler;
import ru.d_shap.csv.state.StateHandlerConfiguration;

/**
 * Class to parse CSV source.
 * CSV parser is a push parser. CSV parser reads characters one by one and pushs events (columns and
 * rows) to the {@link CsvEventHandler} object. The {@link CsvEventHandler} object defines, what to
 * do with pushed columns and rows, for example to count them, or to store them in memory, etc.
 * Some default implementations of {@link CsvEventHandler} can be used.
 * For example, {@link DimensionEventHandler} can define row and column count of CSV, if CSV has
 * the same number of columns in each row. {@link ListEventHandler} stores the whole CSV in memory as
 * list of rows, each row is a list of columns. {@link ColumnCountEventHandler} can define column
 * count for each row.
 * {@link ListEventHandler} is a default {@link CsvEventHandler} object.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvParser {

    private final StateHandlerConfiguration _stateHandlerConfiguration;

    private CsvParser(final StateHandlerConfiguration stateHandlerConfiguration) {
        super();
        _stateHandlerConfiguration = stateHandlerConfiguration;
    }

    /**
     * Get new builder instance to create CSV parser.
     *
     * @return new builder instance.
     */
    public static Builder createBuilder() {
        return new Builder();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence CSV to parse.
     * @return list of rows, each row is a list of columns.
     */
    public List<List<String>> parse(final CharSequence charSequence) {
        Reader reader = createReader(charSequence);
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and push events to the specified event handler.
     *
     * @param charSequence    CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     */
    public void parse(final CharSequence charSequence, final CsvEventHandler csvEventHandler) {
        Reader reader = createReader(charSequence);
        doParse(reader, csvEventHandler);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader CSV to parse.
     * @return list of rows, each row is a list of columns.
     */
    public List<List<String>> parse(final Reader reader) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and push events to the specified event handler.
     *
     * @param reader          CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     */
    public void parse(final Reader reader, final CsvEventHandler csvEventHandler) {
        doParse(reader, csvEventHandler);
    }

    private Reader createReader(final CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        } else {
            return new StringReader(charSequence.toString());
        }
    }

    private void doParse(final Reader reader, final CsvEventHandler csvEventHandler) {
        try {
            StateHandlerConfiguration stateHandlerConfiguration;
            if (csvEventHandler instanceof CsvConfigurable) {
                stateHandlerConfiguration = _stateHandlerConfiguration.copyOf();
                ((CsvConfigurable) csvEventHandler).configure(stateHandlerConfiguration);
            } else {
                stateHandlerConfiguration = _stateHandlerConfiguration;
            }
            StateHandler eventHandler = new StateHandler(csvEventHandler, stateHandlerConfiguration);
            State state = State.getInitState();
            int character;
            while (true) {
                character = reader.read();
                if (character < 0) {
                    break;
                }
                state = state.processCharacter(character, eventHandler);
            }
            state.processCharacter(SpecialCharacter.END_OF_INPUT, eventHandler);
        } catch (IOException ex) {
            throw new CsvIOException(ex);
        }
    }

    /**
     * Builder class to create CSV parser instance.
     *
     * @author Dmitry Shapovalov
     */
    public static final class Builder {

        private final StateHandlerConfiguration _stateHandlerConfiguration;

        private Builder() {
            super();
            _stateHandlerConfiguration = new StateHandlerConfiguration();
        }

        /**
         * Set comma as a column separator.
         *
         * @param commaSeparator true if comma is a column separator.
         * @return current object for the method chaining.
         */
        public Builder setCommaSeparator(final boolean commaSeparator) {
            _stateHandlerConfiguration.setCommaSeparator(commaSeparator);
            return this;
        }

        /**
         * Set semicolon as a column separator.
         *
         * @param semicolonSeparator true if semicolon is a column separator.
         * @return current object for the method chaining.
         */
        public Builder setSemicolonSeparator(final boolean semicolonSeparator) {
            _stateHandlerConfiguration.setSemicolonSeparator(semicolonSeparator);
            return this;
        }

        /**
         * Set CR as a row separator.
         *
         * @param crSeparator true if CR is a row separator.
         * @return current object for the method chaining.
         */
        public Builder setCrSeparator(final boolean crSeparator) {
            _stateHandlerConfiguration.setCrSeparator(crSeparator);
            return this;
        }

        /**
         * Set LF as a row separator.
         *
         * @param lfSeparator true if LF is a row separator.
         * @return current object for the method chaining.
         */
        public Builder setLfSeparator(final boolean lfSeparator) {
            _stateHandlerConfiguration.setLfSeparator(lfSeparator);
            return this;
        }

        /**
         * Set CRLF as a row separator.
         *
         * @param crLfSeparator true if CRLF is a row separator.
         * @return current object for the method chaining.
         */
        public Builder setCrLfSeparator(final boolean crLfSeparator) {
            _stateHandlerConfiguration.setCrLfSeparator(crLfSeparator);
            return this;
        }

        /**
         * Specify whether all rows should have the same column count or not.
         *
         * @param columnCountCheckEnabled true if all rows should have the same column count.
         * @return current object for the method chaining.
         */
        public Builder setColumnCountCheckEnabled(final boolean columnCountCheckEnabled) {
            _stateHandlerConfiguration.setColumnCountCheckEnabled(columnCountCheckEnabled);
            return this;
        }

        /**
         * Specify whether all empty rows should be skipped or not.
         *
         * @param skipEmptyRowsEnabled true if all empty rows should be skipped.
         * @return current object for the method chaining.
         */
        public Builder setSkipEmptyRowsEnabled(final boolean skipEmptyRowsEnabled) {
            _stateHandlerConfiguration.setSkipEmptyRowsEnabled(skipEmptyRowsEnabled);
            return this;
        }

        /**
         * Set the maximum length of a column value. If a column value length is greater then the maximum
         * column value length, then either the rest of a column value is skipped, or an exception is thrown.
         *
         * @param maxColumnLength the maximum length of a column value, or negative number for no column value length restriction.
         * @return current object for the method chaining.
         */
        public Builder setMaxColumnLength(final int maxColumnLength) {
            _stateHandlerConfiguration.setMaxColumnLength(maxColumnLength);
            return this;
        }

        /**
         * Specify whether an excepton should be thrown if a column value length exceeds the maximum column value length or not.
         *
         * @param maxColumnLengthCheckEnabled true if an excepton should be thrown.
         * @return current object for the method chaining.
         */
        public Builder setMaxColumnLengthCheckEnabled(final boolean maxColumnLengthCheckEnabled) {
            _stateHandlerConfiguration.setMaxColumnLengthCheckEnabled(maxColumnLengthCheckEnabled);
            return this;
        }

        /**
         * Create CSV parser.
         *
         * @return CSV parser.
         */
        public CsvParser build() {
            StateHandlerConfiguration stateHandlerConfiguration = _stateHandlerConfiguration.copyOf();
            return new CsvParser(stateHandlerConfiguration);
        }

    }

}
