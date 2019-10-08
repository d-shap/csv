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

/**
 * <p>
 * Class to parse CSV source.
 * </p>
 * <p>
 * CSV parser is a push parser. CSV parser reads characters one by one and pushs events (columns and
 * rows) to the {@link CsvEventHandler} object. The {@link CsvEventHandler} object defines, what to
 * do with pushed columns and rows - to count them, to store them in memory, and so on.
 * </p>
 * <p>
 * Some default implementations of {@link CsvEventHandler} can be used.
 * For example, {@link ListEventHandler} stores the whole CSV in memory as list of rows, each row
 * is a list of columns. {@link DimensionEventHandler} can define row and column count of CSV, if CSV
 * has the same number of columns in each row. {@link ColumnCountEventHandler} can define column count
 * for each row.
 * </p>
 * <p>
 * {@link ListEventHandler} is a default {@link CsvEventHandler} object.
 * </p>
 * <p>
 * Objects of this class are reusable.
 * </p>
 *
 * @author Dmitry Shapovalov
 */
public final class CsvParser {

    private final CsvParserConfiguration _csvParserConfiguration;

    CsvParser(final CsvParserConfiguration csvParserConfiguration) {
        super();
        csvParserConfiguration.validate();
        _csvParserConfiguration = csvParserConfiguration;
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence CSV to parse.
     *
     * @return list of rows, each row is a list of columns.
     */
    public List<List<String>> parse(final CharSequence charSequence) {
        Reader reader = createReader(charSequence);
        ListEventHandler listParserEventHandler = new ListEventHandler();
        parse(reader, listParserEventHandler);
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
        parse(reader, csvEventHandler);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader CSV to parse.
     *
     * @return list of rows, each row is a list of columns.
     */
    public List<List<String>> parse(final Reader reader) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        parse(reader, listParserEventHandler);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and push events to the specified event handler.
     *
     * @param reader          CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     */
    public void parse(final Reader reader, final CsvEventHandler csvEventHandler) {
        try {
            CsvParserConfiguration csvParserConfiguration;
            if (csvEventHandler instanceof CsvConfigurable) {
                csvParserConfiguration = _csvParserConfiguration.copyOf();
                ((CsvConfigurable) csvEventHandler).configure(csvParserConfiguration);
            } else {
                csvParserConfiguration = _csvParserConfiguration;
            }
            StateHandler eventHandler = new StateHandler(csvEventHandler, csvParserConfiguration);
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
            reader.close();
        } catch (IOException ex) {
            throw new CsvIOException(ex);
        }
    }

    private Reader createReader(final CharSequence charSequence) {
        return new StringReader(charSequence.toString());
    }

}
