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

import ru.d_shap.csv.state.StateHandlerConfiguration;

/**
 * CSV parser event handler, that defines row count and column count for each row of CSV. CSV may have
 * different column count in each row.
 *
 * @author Dmitry Shapovalov
 */
public final class ColumnCountEventHandler implements CsvConfigurable, CsvEventHandler {

    private final List<Integer> _columnCounts;

    private int _currentColumnCount;

    /**
     * Create a new object.
     */
    public ColumnCountEventHandler() {
        super();
        _columnCounts = new ArrayList<>();
        _currentColumnCount = 0;
    }

    @Override
    public void configure(final StateHandlerConfiguration stateHandlerConfiguration) {
        stateHandlerConfiguration.setMaxColumnLength(0);
        stateHandlerConfiguration.setMaxColumnLengthCheckEnabled(false);
    }

    @Override
    public void pushColumn(final String column, final int actualLength) {
        _currentColumnCount++;
    }

    @Override
    public void pushRow() {
        _columnCounts.add(_currentColumnCount);
        _currentColumnCount = 0;
    }

    /**
     * Get list of column counts of CSV. List size is a row count of CSV. Each list element is a column
     * count for corresponding row.
     *
     * @return list of column counts of CSV.
     */
    public List<Integer> getColumnCounts() {
        return _columnCounts;
    }

}
