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
 * CSV parser event handler, that skips all callback calls.
 * This handler can be used for CSV validation. For example, whether CSV source has valid format or not,
 * whether CSV contains the same column count in all rows or not, whether any column value length exceeds
 * the specified maximum column value length or not.
 *
 * @author Dmitry Shapovalov
 */
public final class NoopEventHandler implements CsvEventHandler {

    /**
     * Create a new object.
     */
    public NoopEventHandler() {
        super();
    }

    @Override
    public void pushColumn(final String column, final int actualLength) {
        // Ignore
    }

    @Override
    public void pushRow() {
        // Ignore
    }

}
