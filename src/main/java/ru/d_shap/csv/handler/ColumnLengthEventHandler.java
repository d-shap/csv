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

import ru.d_shap.csv.CsvParserConfiguration;

/**
 * CSV parser event handler, that accumulates the length of each column value in each row.
 *
 * @author Dmitry Shapovalov
 */
public final class ColumnLengthEventHandler implements CsvConfigurable, CsvEventHandler {

    private final List<List<Integer>> _rows;

    private List<Integer> _currentRow;

    /**
     * Create a new object.
     */
    public ColumnLengthEventHandler() {
        super();
        _rows = new ArrayList<>();
        _currentRow = null;
    }

    @Override
    public void configure(final CsvParserConfiguration stateHandlerConfiguration) {
        stateHandlerConfiguration.setMaxColumnLength(0);
        stateHandlerConfiguration.setMaxColumnLengthCheckEnabled(false);
    }

    @Override
    public void pushColumn(final String column, final int actualLength) {
        setCurrentRow();
        _currentRow.add(actualLength);
    }

    private void setCurrentRow() {
        if (_currentRow == null) {
            _currentRow = new ArrayList<>();
        }
    }

    @Override
    public void pushRow() {
        setCurrentRow();
        _rows.add(_currentRow);
        _currentRow = null;
    }

    /**
     * Get list of column value lengths of each row.
     *
     * @return list of column value lengths of each row.
     */
    public List<List<Integer>> getColumnLengths() {
        return _rows;
    }

}
