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
package ru.d_shap.csv.handler;

/**
 * Interface to configure CSV parser behaviour and to process events, pushed from CSV parser.
 * Interface defines, how CSV parser process column values, with methods {@link #getMaxColumnLength()}
 * and {@link #checkMaxColumnLength()}. This methods define, whether CSV parser stores column value and passes this
 * value to {@link #pushColumn(String, int)} method, or just skips it. Skipping column value could be useful
 * to check, if CSV is rectangular, or to define column and row count, without actual processing of CSV.
 * Parser events are handled by {@link #pushColumn(String, int)} and {@link #pushRow()} methods.
 *
 * @author Dmitry Shapovalov
 */
public interface CsvEventHandler {

    /**
     * Define maximum length of column value. If column value length is greater then defined by this method, then
     * either the rest of column value is skipped, or an exception is thrown.
     *
     * @return maximum length of column value, or negative number for no column value length restriction.
     */
    int getMaxColumnLength();

    /**
     * Define, should parser throw an excepton if column value length exceeds maximum length or not.
     *
     * @return true if parser should throw an exception if column value length exceeds maximum length.
     */
    boolean checkMaxColumnLength();

    /**
     * Process column, pushed from the parser. If maximum length is specified and actual column value length
     * exceeds maximum length, then column value would be trimmed.
     *
     * @param column       column value.
     * @param actualLength actual column value length.
     */
    void pushColumn(String column, int actualLength);

    /**
     * Process row, pushed from the parser.
     */
    void pushRow();

}
