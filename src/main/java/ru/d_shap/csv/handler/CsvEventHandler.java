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
 * Interface to process events, pushed from CSV parser. If this object also implements {@link CsvConfigurable}
 * interface, then this object can modify CSV parser behaviour.
 *
 * @author Dmitry Shapovalov
 */
public interface CsvEventHandler {

    /**
     * Process column value, pushed from CSV parser. If the maximum column value length is specified
     * and the actual column value length exceeds the maximum column value length, then the actual
     * column value would be trimmed. The actual column value length is not affected by the maximum
     * column value length configuration.
     *
     * @param column       the actual column value.
     * @param actualLength the actual column value length.
     */
    void pushColumn(String column, int actualLength);

    /**
     * Process row, pushed from CSV parser. If empty rows are skipped, then this method is not
     * inviked for empty rows.
     */
    void pushRow();

}
