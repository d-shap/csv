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

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for CSV parser event handlers, that accumulate columns and rows in memory.
 *
 * @author Dmitry Shapovalov
 */
abstract class AbstractListEventHandler implements CsvEventHandler {

    private final List<List<String>> _rows;

    private List<String> _currentRow;

    AbstractListEventHandler() {
        super();
        _rows = new ArrayList<>();
        _currentRow = null;
    }

    @Override
    public final void pushColumn(final String column, final int actualLength) {
        setCurrentRow();
        doPushColumn(column, actualLength);
    }

    abstract void doPushColumn(String column, int actualLength);

    final void addColumnToCurrentRow(final String column) {
        _currentRow.add(column);
    }

    @Override
    public final void pushRow() {
        setCurrentRow();
        _rows.add(_currentRow);
        _currentRow = null;
    }

    private void setCurrentRow() {
        if (_currentRow == null) {
            _currentRow = new ArrayList<>();
        }
    }

    /**
     * Return parse result as list of rows, each row is a list of columns.
     *
     * @return parse result.
     */
    public final List<List<String>> getCsv() {
        return _rows;
    }

}
