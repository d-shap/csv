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
package ru.d_shap.csv.handler;

import java.util.ArrayList;
import java.util.List;

/**
 * CSV parser event handler, that defines row count and column count for each row of CSV. CSV can NOT
 * be rectangular - each row can have different number of columns.
 *
 * @author Dmitry Shapovalov
 */
public final class SizeParserEventHandler implements IParserEventHandler {

    private final List<Integer> _columnCounts;

    private int _currentColumnCount;

    /**
     * Create new object.
     */
    public SizeParserEventHandler() {
        super();
        _columnCounts = new ArrayList<Integer>();
        _currentColumnCount = 0;
    }

    @Override
    public int getMaxColumnLength() {
        return 0;
    }

    @Override
    public boolean checkMaxColumnLength() {
        return false;
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
     * Return parse result as list of rows, each row contains count of columns in this row.
     *
     * @return parse result.
     */
    public List<Integer> getColumnCounts() {
        return _columnCounts;
    }

}
