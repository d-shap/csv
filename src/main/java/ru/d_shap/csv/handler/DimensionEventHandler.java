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
 * CSV parser event handler, that defines row and column count of rectangular CSV. If CSV is not rectangular,
 * then column count is defined for the first row. Other rows could have another column count. That is why this
 * handler should be used with rectangular check on.
 *
 * @author Dmitry Shapovalov
 */
public final class DimensionEventHandler implements IParserEventHandler {

    private boolean _firstRow;

    private int _columnCount;

    private int _rowCount;

    /**
     * Create new object.
     */
    public DimensionEventHandler() {
        super();
        _firstRow = true;
        _columnCount = 0;
        _rowCount = 0;
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
    public void pushColumn(final String column, final int length) {
        if (_firstRow) {
            _columnCount++;
        }
    }

    @Override
    public void pushRow() {
        _firstRow = false;
        _rowCount++;
    }

    /**
     * Return CSV column count.
     *
     * @return column count.
     */
    public int getColumnCount() {
        return _columnCount;
    }

    /**
     * Return CSV row count.
     *
     * @return row count.
     */
    public int getRowCount() {
        return _rowCount;
    }

}
