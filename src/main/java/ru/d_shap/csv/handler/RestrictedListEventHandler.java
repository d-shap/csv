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
 * CSV parser event handler, that accumulates columns and rows in memory. The length of column value has the maximum
 * value. If column value exceeds the specified maximum value, then column value is trimmed.
 *
 * @author Dmitry Shapovalov
 */
public final class RestrictedListEventHandler extends AbstractListEventHandler {

    private final int _maxColumnLength;

    private final String _moreSymbolsMark;

    private final int _trimToIndex;

    /**
     * Create new object.
     *
     * @param maxColumnLength maximum number of symbols in column.
     */
    public RestrictedListEventHandler(final int maxColumnLength) {
        this(maxColumnLength, null);
    }

    /**
     * Create new object.
     *
     * @param maxColumnLength maximum number of symbols in column.
     * @param moreSymbolsMark text, added to the end of the trimmed column value.
     */
    public RestrictedListEventHandler(final int maxColumnLength, final String moreSymbolsMark) {
        super();
        _maxColumnLength = maxColumnLength;
        _moreSymbolsMark = moreSymbolsMark;
        if (_moreSymbolsMark == null) {
            _trimToIndex = _maxColumnLength;
        } else {
            _trimToIndex = Math.max(_maxColumnLength - _moreSymbolsMark.length(), 0);
        }
    }

    @Override
    public int getMaxColumnLength() {
        return _maxColumnLength;
    }

    @Override
    public boolean checkMaxColumnLength() {
        return false;
    }

    @Override
    public void doPushColumn(final String column, final int actualLength) {
        if (actualLength <= _maxColumnLength || _moreSymbolsMark == null) {
            addColumnToCurrentRow(column);
        } else {
            addColumnToCurrentRow(column.substring(0, _trimToIndex) + _moreSymbolsMark);
        }
    }

}
