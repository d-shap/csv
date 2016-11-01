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
import ru.d_shap.csv.handler.DimensionEventHandler;
import ru.d_shap.csv.handler.IParserEventHandler;
import ru.d_shap.csv.handler.ListEventHandler;
import ru.d_shap.csv.state.AbstractState;
import ru.d_shap.csv.state.ParserEventHandler;

/**
 * Class to parse CSV from a source. CSV parser is a push parser. CSV parser reads a source symbol by symbol and
 * pushes events to the {@link ru.d_shap.csv.handler.IParserEventHandler} object. This object defines,
 * what to do with columns and rows. Some default implementations of {@link ru.d_shap.csv.handler.IParserEventHandler}
 * can be used. For example, {@link DimensionEventHandler} can define row and column count
 * for CSV, if CSV has the same number of rows and columns. {@link ListEventHandler} stores
 * the whole CSV in memory as list of rows, each row is a list of columns. {@link ColumnCountEventHandler}
 * can define column count for each row. {@link ListEventHandler} is used in methods without
 * {@link ru.d_shap.csv.handler.IParserEventHandler} parameter.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvParser {

    private CsvParser() {
        super();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence CSV to parse.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final CharSequence charSequence) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        parse(charSequence, listParserEventHandler, false);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence     CSV to parse.
     * @param checkRectangular check if all rows should have the same column count.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final CharSequence charSequence, final boolean checkRectangular) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        parse(charSequence, listParserEventHandler, checkRectangular);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence       CSV to parse.
     * @param parserEventHandler event handler to process parser events.
     */
    public static void parse(final CharSequence charSequence, final IParserEventHandler parserEventHandler) {
        parse(charSequence, parserEventHandler, false);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence       CSV to parse.
     * @param parserEventHandler event handler to process parser events.
     * @param checkRectangular   check if all rows should have the same column count.
     */
    public static void parse(final CharSequence charSequence, final IParserEventHandler parserEventHandler, final boolean checkRectangular) {
        if (charSequence == null) {
            return;
        }

        String str;
        if (charSequence instanceof String) {
            str = (String) charSequence;
        } else {
            str = charSequence.toString();
        }
        StringReader reader = new StringReader(str);
        parse(reader, parserEventHandler, checkRectangular);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader CSV to parse.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final Reader reader) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        parse(reader, listParserEventHandler, false);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader           CSV to parse.
     * @param checkRectangular check if all rows should have the same column count.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final Reader reader, final boolean checkRectangular) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        parse(reader, listParserEventHandler, checkRectangular);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader             CSV to parse.
     * @param parserEventHandler event handler to process parser events.
     */
    public static void parse(final Reader reader, final IParserEventHandler parserEventHandler) {
        parse(reader, parserEventHandler, false);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader             CSV to parse.
     * @param parserEventHandler event handler to process parser events.
     * @param checkRectangular   check if all rows should have the same column count.
     */
    public static void parse(final Reader reader, final IParserEventHandler parserEventHandler, final boolean checkRectangular) {
        if (reader == null) {
            return;
        }
        if (parserEventHandler == null) {
            return;
        }

        try {
            ParserEventHandler eventHandler = new ParserEventHandler(parserEventHandler, checkRectangular);
            AbstractState state = AbstractState.getInitState();
            int symbol;
            while (true) {
                symbol = reader.read();
                if (symbol < 0) {
                    break;
                }
                state = state.processInput(symbol, eventHandler);
            }
            state.processInput(AbstractState.END_OF_INPUT, eventHandler);
        } catch (IOException ex) {
            throw new CsvIOException(ex);
        }
    }

}
