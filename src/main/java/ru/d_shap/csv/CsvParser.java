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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.d_shap.csv.handler.ColumnCountEventHandler;
import ru.d_shap.csv.handler.CsvEventHandler;
import ru.d_shap.csv.handler.DimensionEventHandler;
import ru.d_shap.csv.handler.ListEventHandler;
import ru.d_shap.csv.state.SpecialCharacter;
import ru.d_shap.csv.state.State;
import ru.d_shap.csv.state.StateHandler;

/**
 * Class to parse CSV from a source. CSV parser is a push parser. CSV parser reads a source symbol by symbol and
 * pushes events to the {@link ru.d_shap.csv.handler.CsvEventHandler} object. This object defines,
 * what to do with columns and rows. Some default implementations of {@link ru.d_shap.csv.handler.CsvEventHandler}
 * can be used. For example, {@link DimensionEventHandler} can define row and column count
 * for CSV, if CSV has the same number of rows and columns. {@link ListEventHandler} stores
 * the whole CSV in memory as list of rows, each row is a list of columns. {@link ColumnCountEventHandler}
 * can define column count for each row. {@link ListEventHandler} is used in methods without
 * {@link ru.d_shap.csv.handler.CsvEventHandler} parameter.
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
        Reader reader = createReader(charSequence);
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, false);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence    CSV to parse.
     * @param columnSeparator separator between columns in one row.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final CharSequence charSequence, final ColumnSeparators columnSeparator) {
        Reader reader = createReader(charSequence);
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, false, columnSeparator);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence CSV to parse.
     * @param rowSeparator separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final CharSequence charSequence, final RowSeparators rowSeparator) {
        Reader reader = createReader(charSequence);
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, false, rowSeparator);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence  CSV to parse.
     * @param rowSeparator1 separator between rows.
     * @param rowSeparator2 separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final CharSequence charSequence, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        Reader reader = createReader(charSequence);
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, false, rowSeparator1, rowSeparator2);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence    CSV to parse.
     * @param columnSeparator separator between columns in one row.
     * @param rowSeparator    separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final CharSequence charSequence, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator) {
        Reader reader = createReader(charSequence);
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, false, columnSeparator, rowSeparator);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence    CSV to parse.
     * @param columnSeparator separator between columns in one row.
     * @param rowSeparator1   separator between rows.
     * @param rowSeparator2   separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final CharSequence charSequence, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        Reader reader = createReader(charSequence);
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, false, columnSeparator, rowSeparator1, rowSeparator2);
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
        Reader reader = createReader(charSequence);
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, checkRectangular);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence     CSV to parse.
     * @param checkRectangular check if all rows should have the same column count.
     * @param columnSeparator  separator between columns in one row.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final CharSequence charSequence, final boolean checkRectangular, final ColumnSeparators columnSeparator) {
        Reader reader = createReader(charSequence);
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, checkRectangular, columnSeparator);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence     CSV to parse.
     * @param checkRectangular check if all rows should have the same column count.
     * @param rowSeparator     separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final CharSequence charSequence, final boolean checkRectangular, final RowSeparators rowSeparator) {
        Reader reader = createReader(charSequence);
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, checkRectangular, rowSeparator);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence     CSV to parse.
     * @param checkRectangular check if all rows should have the same column count.
     * @param rowSeparator1    separator between rows.
     * @param rowSeparator2    separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final CharSequence charSequence, final boolean checkRectangular, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        Reader reader = createReader(charSequence);
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, checkRectangular, rowSeparator1, rowSeparator2);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence     CSV to parse.
     * @param checkRectangular check if all rows should have the same column count.
     * @param columnSeparator  separator between columns in one row.
     * @param rowSeparator     separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final CharSequence charSequence, final boolean checkRectangular, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator) {
        Reader reader = createReader(charSequence);
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, checkRectangular, columnSeparator, rowSeparator);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence     CSV to parse.
     * @param checkRectangular check if all rows should have the same column count.
     * @param columnSeparator  separator between columns in one row.
     * @param rowSeparator1    separator between rows.
     * @param rowSeparator2    separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final CharSequence charSequence, final boolean checkRectangular, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        Reader reader = createReader(charSequence);
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, checkRectangular, columnSeparator, rowSeparator1, rowSeparator2);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence    CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     */
    public static void parse(final CharSequence charSequence, final CsvEventHandler csvEventHandler) {
        Reader reader = createReader(charSequence);
        doParse(reader, csvEventHandler, false);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence    CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     * @param columnSeparator separator between columns in one row.
     */
    public static void parse(final CharSequence charSequence, final CsvEventHandler csvEventHandler, final ColumnSeparators columnSeparator) {
        Reader reader = createReader(charSequence);
        doParse(reader, csvEventHandler, false, columnSeparator);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence    CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     * @param rowSeparator    separator between rows.
     */
    public static void parse(final CharSequence charSequence, final CsvEventHandler csvEventHandler, final RowSeparators rowSeparator) {
        Reader reader = createReader(charSequence);
        doParse(reader, csvEventHandler, false, rowSeparator);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence    CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     * @param rowSeparator1   separator between rows.
     * @param rowSeparator2   separator between rows.
     */
    public static void parse(final CharSequence charSequence, final CsvEventHandler csvEventHandler, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        Reader reader = createReader(charSequence);
        doParse(reader, csvEventHandler, false, rowSeparator1, rowSeparator2);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence    CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     * @param columnSeparator separator between columns in one row.
     * @param rowSeparator    separator between rows.
     */
    public static void parse(final CharSequence charSequence, final CsvEventHandler csvEventHandler, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator) {
        Reader reader = createReader(charSequence);
        doParse(reader, csvEventHandler, false, columnSeparator, rowSeparator);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence    CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     * @param columnSeparator separator between columns in one row.
     * @param rowSeparator1   separator between rows.
     * @param rowSeparator2   separator between rows.
     */
    public static void parse(final CharSequence charSequence, final CsvEventHandler csvEventHandler, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        Reader reader = createReader(charSequence);
        doParse(reader, csvEventHandler, false, columnSeparator, rowSeparator1, rowSeparator2);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence     CSV to parse.
     * @param csvEventHandler  event handler to process parser events.
     * @param checkRectangular check if all rows should have the same column count.
     */
    public static void parse(final CharSequence charSequence, final CsvEventHandler csvEventHandler, final boolean checkRectangular) {
        Reader reader = createReader(charSequence);
        doParse(reader, csvEventHandler, checkRectangular);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence     CSV to parse.
     * @param csvEventHandler  event handler to process parser events.
     * @param checkRectangular check if all rows should have the same column count.
     * @param columnSeparator  separator between columns in one row.
     */
    public static void parse(final CharSequence charSequence, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final ColumnSeparators columnSeparator) {
        Reader reader = createReader(charSequence);
        doParse(reader, csvEventHandler, checkRectangular, columnSeparator);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence     CSV to parse.
     * @param csvEventHandler  event handler to process parser events.
     * @param checkRectangular check if all rows should have the same column count.
     * @param rowSeparator     separator between rows.
     */
    public static void parse(final CharSequence charSequence, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final RowSeparators rowSeparator) {
        Reader reader = createReader(charSequence);
        doParse(reader, csvEventHandler, checkRectangular, rowSeparator);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence     CSV to parse.
     * @param csvEventHandler  event handler to process parser events.
     * @param checkRectangular check if all rows should have the same column count.
     * @param rowSeparator1    separator between rows.
     * @param rowSeparator2    separator between rows.
     */
    public static void parse(final CharSequence charSequence, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        Reader reader = createReader(charSequence);
        doParse(reader, csvEventHandler, checkRectangular, rowSeparator1, rowSeparator2);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence     CSV to parse.
     * @param csvEventHandler  event handler to process parser events.
     * @param checkRectangular check if all rows should have the same column count.
     * @param columnSeparator  separator between columns in one row.
     * @param rowSeparator     separator between rows.
     */
    public static void parse(final CharSequence charSequence, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator) {
        Reader reader = createReader(charSequence);
        doParse(reader, csvEventHandler, checkRectangular, columnSeparator, rowSeparator);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param charSequence     CSV to parse.
     * @param csvEventHandler  event handler to process parser events.
     * @param checkRectangular check if all rows should have the same column count.
     * @param columnSeparator  separator between columns in one row.
     * @param rowSeparator1    separator between rows.
     * @param rowSeparator2    separator between rows.
     */
    public static void parse(final CharSequence charSequence, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        Reader reader = createReader(charSequence);
        doParse(reader, csvEventHandler, checkRectangular, columnSeparator, rowSeparator1, rowSeparator2);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader CSV to parse.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final Reader reader) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, false);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader          CSV to parse.
     * @param columnSeparator separator between columns in one row.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final Reader reader, final ColumnSeparators columnSeparator) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, false, columnSeparator);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader       CSV to parse.
     * @param rowSeparator separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final Reader reader, final RowSeparators rowSeparator) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, false, rowSeparator);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader        CSV to parse.
     * @param rowSeparator1 separator between rows.
     * @param rowSeparator2 separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final Reader reader, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, false, rowSeparator1, rowSeparator2);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader          CSV to parse.
     * @param columnSeparator separator between columns in one row.
     * @param rowSeparator    separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final Reader reader, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, false, columnSeparator, rowSeparator);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader          CSV to parse.
     * @param columnSeparator separator between columns in one row.
     * @param rowSeparator1   separator between rows.
     * @param rowSeparator2   separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final Reader reader, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, false, columnSeparator, rowSeparator1, rowSeparator2);
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
        doParse(reader, listParserEventHandler, checkRectangular);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader           CSV to parse.
     * @param checkRectangular check if all rows should have the same column count.
     * @param columnSeparator  separator between columns in one row.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final Reader reader, final boolean checkRectangular, final ColumnSeparators columnSeparator) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, checkRectangular, columnSeparator);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader           CSV to parse.
     * @param checkRectangular check if all rows should have the same column count.
     * @param rowSeparator     separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final Reader reader, final boolean checkRectangular, final RowSeparators rowSeparator) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, checkRectangular, rowSeparator);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader           CSV to parse.
     * @param checkRectangular check if all rows should have the same column count.
     * @param rowSeparator1    separator between rows.
     * @param rowSeparator2    separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final Reader reader, final boolean checkRectangular, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, checkRectangular, rowSeparator1, rowSeparator2);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader           CSV to parse.
     * @param checkRectangular check if all rows should have the same column count.
     * @param columnSeparator  separator between columns in one row.
     * @param rowSeparator     separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final Reader reader, final boolean checkRectangular, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, checkRectangular, columnSeparator, rowSeparator);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader           CSV to parse.
     * @param checkRectangular check if all rows should have the same column count.
     * @param columnSeparator  separator between columns in one row.
     * @param rowSeparator1    separator between rows.
     * @param rowSeparator2    separator between rows.
     * @return list of rows, each row is a list of columns.
     */
    public static List<List<String>> parse(final Reader reader, final boolean checkRectangular, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        ListEventHandler listParserEventHandler = new ListEventHandler();
        doParse(reader, listParserEventHandler, checkRectangular, columnSeparator, rowSeparator1, rowSeparator2);
        return listParserEventHandler.getCsv();
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader          CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     */
    public static void parse(final Reader reader, final CsvEventHandler csvEventHandler) {
        doParse(reader, csvEventHandler, false);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader          CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     * @param columnSeparator separator between columns in one row.
     */
    public static void parse(final Reader reader, final CsvEventHandler csvEventHandler, final ColumnSeparators columnSeparator) {
        doParse(reader, csvEventHandler, false, columnSeparator);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader          CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     * @param rowSeparator    separator between rows.
     */
    public static void parse(final Reader reader, final CsvEventHandler csvEventHandler, final RowSeparators rowSeparator) {
        doParse(reader, csvEventHandler, false, rowSeparator);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader          CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     * @param rowSeparator1   separator between rows.
     * @param rowSeparator2   separator between rows.
     */
    public static void parse(final Reader reader, final CsvEventHandler csvEventHandler, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        doParse(reader, csvEventHandler, false, rowSeparator1, rowSeparator2);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader          CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     * @param columnSeparator separator between columns in one row.
     * @param rowSeparator    separator between rows.
     */
    public static void parse(final Reader reader, final CsvEventHandler csvEventHandler, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator) {
        doParse(reader, csvEventHandler, false, columnSeparator, rowSeparator);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader          CSV to parse.
     * @param csvEventHandler event handler to process parser events.
     * @param columnSeparator separator between columns in one row.
     * @param rowSeparator1   separator between rows.
     * @param rowSeparator2   separator between rows.
     */
    public static void parse(final Reader reader, final CsvEventHandler csvEventHandler, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        doParse(reader, csvEventHandler, false, columnSeparator, rowSeparator1, rowSeparator2);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader           CSV to parse.
     * @param csvEventHandler  event handler to process parser events.
     * @param checkRectangular check if all rows should have the same column count.
     */
    public static void parse(final Reader reader, final CsvEventHandler csvEventHandler, final boolean checkRectangular) {
        doParse(reader, csvEventHandler, checkRectangular);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader           CSV to parse.
     * @param csvEventHandler  event handler to process parser events.
     * @param checkRectangular check if all rows should have the same column count.
     * @param columnSeparator  separator between columns in one row.
     */
    public static void parse(final Reader reader, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final ColumnSeparators columnSeparator) {
        doParse(reader, csvEventHandler, checkRectangular, columnSeparator);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader           CSV to parse.
     * @param csvEventHandler  event handler to process parser events.
     * @param checkRectangular check if all rows should have the same column count.
     * @param rowSeparator     separator between rows.
     */
    public static void parse(final Reader reader, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final RowSeparators rowSeparator) {
        doParse(reader, csvEventHandler, checkRectangular, rowSeparator);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader           CSV to parse.
     * @param csvEventHandler  event handler to process parser events.
     * @param checkRectangular check if all rows should have the same column count.
     * @param rowSeparator1    separator between rows.
     * @param rowSeparator2    separator between rows.
     */
    public static void parse(final Reader reader, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        doParse(reader, csvEventHandler, checkRectangular, rowSeparator1, rowSeparator2);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader           CSV to parse.
     * @param csvEventHandler  event handler to process parser events.
     * @param checkRectangular check if all rows should have the same column count.
     * @param columnSeparator  separator between columns in one row.
     * @param rowSeparator     separator between rows.
     */
    public static void parse(final Reader reader, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator) {
        doParse(reader, csvEventHandler, checkRectangular, columnSeparator, rowSeparator);
    }

    /**
     * Parse CSV and define rows and columns.
     *
     * @param reader           CSV to parse.
     * @param csvEventHandler  event handler to process parser events.
     * @param checkRectangular check if all rows should have the same column count.
     * @param columnSeparator  separator between columns in one row.
     * @param rowSeparator1    separator between rows.
     * @param rowSeparator2    separator between rows.
     */
    public static void parse(final Reader reader, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        doParse(reader, csvEventHandler, checkRectangular, columnSeparator, rowSeparator1, rowSeparator2);
    }

    private static void doParse(final Reader reader, final CsvEventHandler csvEventHandler, final boolean checkRectangular) {
        Set<ColumnSeparators> columnSeparators = createColumnSeparators();
        Set<RowSeparators> rowSeparators = createRowSeparators();
        doParse(reader, csvEventHandler, checkRectangular, columnSeparators, rowSeparators);
    }

    private static void doParse(final Reader reader, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final ColumnSeparators columnSeparator) {
        Set<ColumnSeparators> columnSeparators = createColumnSeparators(columnSeparator);
        Set<RowSeparators> rowSeparators = createRowSeparators();
        doParse(reader, csvEventHandler, checkRectangular, columnSeparators, rowSeparators);
    }

    private static void doParse(final Reader reader, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final RowSeparators rowSeparator) {
        Set<ColumnSeparators> columnSeparators = createColumnSeparators();
        Set<RowSeparators> rowSeparators = createRowSeparators(rowSeparator);
        doParse(reader, csvEventHandler, checkRectangular, columnSeparators, rowSeparators);
    }

    private static void doParse(final Reader reader, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        Set<ColumnSeparators> columnSeparators = createColumnSeparators();
        Set<RowSeparators> rowSeparators = createRowSeparators(rowSeparator1, rowSeparator2);
        doParse(reader, csvEventHandler, checkRectangular, columnSeparators, rowSeparators);
    }

    private static void doParse(final Reader reader, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator) {
        Set<ColumnSeparators> columnSeparators = createColumnSeparators(columnSeparator);
        Set<RowSeparators> rowSeparators = createRowSeparators(rowSeparator);
        doParse(reader, csvEventHandler, checkRectangular, columnSeparators, rowSeparators);
    }

    private static void doParse(final Reader reader, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final ColumnSeparators columnSeparator, final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        Set<ColumnSeparators> columnSeparators = createColumnSeparators(columnSeparator);
        Set<RowSeparators> rowSeparators = createRowSeparators(rowSeparator1, rowSeparator2);
        doParse(reader, csvEventHandler, checkRectangular, columnSeparators, rowSeparators);
    }

    private static void doParse(final Reader reader, final CsvEventHandler csvEventHandler, final boolean checkRectangular, final Set<ColumnSeparators> columnSeparators, final Set<RowSeparators> rowSeparators) {
        if (reader == null) {
            return;
        }
        if (csvEventHandler == null) {
            return;
        }

        try {
            StateHandler eventHandler = new StateHandler(csvEventHandler, checkRectangular, columnSeparators, rowSeparators);
            State state = State.getInitState();
            int symbol;
            while (true) {
                symbol = reader.read();
                if (symbol < 0) {
                    break;
                }
                state = state.processCharacter(symbol, eventHandler);
            }
            state.processCharacter(SpecialCharacter.END_OF_INPUT, eventHandler);
        } catch (IOException ex) {
            throw new CsvIOException(ex);
        }
    }

    private static Reader createReader(final CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        return new StringReader(charSequence.toString());
    }

    private static Set<ColumnSeparators> createColumnSeparators() {
        Set<ColumnSeparators> columnSeparators = new HashSet<>();
        columnSeparators.add(ColumnSeparators.COMMA);
        columnSeparators.add(ColumnSeparators.SEMICOLON);
        return columnSeparators;
    }

    private static Set<ColumnSeparators> createColumnSeparators(final ColumnSeparators columnSeparator) {
        Set<ColumnSeparators> columnSeparators = new HashSet<>();
        columnSeparators.add(columnSeparator);
        return columnSeparators;
    }

    private static Set<RowSeparators> createRowSeparators() {
        Set<RowSeparators> rowSeparators = new HashSet<>();
        rowSeparators.add(RowSeparators.CR);
        rowSeparators.add(RowSeparators.LF);
        rowSeparators.add(RowSeparators.CRLF);
        return rowSeparators;
    }

    private static Set<RowSeparators> createRowSeparators(final RowSeparators rowSeparator) {
        Set<RowSeparators> rowSeparators = new HashSet<>();
        rowSeparators.add(rowSeparator);
        return rowSeparators;
    }

    private static Set<RowSeparators> createRowSeparators(final RowSeparators rowSeparator1, final RowSeparators rowSeparator2) {
        Set<RowSeparators> rowSeparators = new HashSet<>();
        rowSeparators.add(rowSeparator1);
        rowSeparators.add(rowSeparator2);
        return rowSeparators;
    }

}
