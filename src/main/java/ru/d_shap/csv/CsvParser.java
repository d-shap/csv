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
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.csv;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import ru.d_shap.csv.handler.IParserEventHandler;
import ru.d_shap.csv.handler.ListParserEventHandler;
import ru.d_shap.csv.state.AbstractState;
import ru.d_shap.csv.state.ParserEventHandler;

/**
 * Class to parse CSV from source.
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
    public static List<List<String>> parseCsv(final CharSequence charSequence) {
        ListParserEventHandler listParserEventHandler = new ListParserEventHandler();
        parseCsv(charSequence, listParserEventHandler, false);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence     CSV to parse.
     * @param checkRectangular check if all rows should have the same column count.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parseCsv(final CharSequence charSequence, final boolean checkRectangular) {
        ListParserEventHandler listParserEventHandler = new ListParserEventHandler();
        parseCsv(charSequence, listParserEventHandler, checkRectangular);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence       CSV to parse.
     * @param parserEventHandler event handler to process parser events.
     */
    public static void parseCsv(final CharSequence charSequence, final IParserEventHandler parserEventHandler) {
        parseCsv(charSequence, parserEventHandler, false);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence       CSV to parse.
     * @param parserEventHandler event handler to process parser events.
     * @param checkRectangular   check if all rows should have the same column count.
     */
    public static void parseCsv(final CharSequence charSequence, final IParserEventHandler parserEventHandler, final boolean checkRectangular) {
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
        parseCsv(reader, parserEventHandler, checkRectangular);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader CSV to parse.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parseCsv(final Reader reader) {
        ListParserEventHandler listParserEventHandler = new ListParserEventHandler();
        parseCsv(reader, listParserEventHandler, false);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader           CSV to parse.
     * @param checkRectangular check if all rows should have the same column count.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parseCsv(final Reader reader, final boolean checkRectangular) {
        ListParserEventHandler listParserEventHandler = new ListParserEventHandler();
        parseCsv(reader, listParserEventHandler, checkRectangular);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader             CSV to parse.
     * @param parserEventHandler event handler to process parser events.
     */
    public static void parseCsv(final Reader reader, final IParserEventHandler parserEventHandler) {
        parseCsv(reader, parserEventHandler, false);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader             CSV to parse.
     * @param parserEventHandler event handler to process parser events.
     * @param checkRectangular   check if all rows should have the same column count.
     */
    public static void parseCsv(final Reader reader, final IParserEventHandler parserEventHandler, final boolean checkRectangular) {
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
