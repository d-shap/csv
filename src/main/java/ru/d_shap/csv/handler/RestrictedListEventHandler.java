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

import ru.d_shap.csv.CsvParserConfiguration;

/**
 * CSV parser event handler, that accumulates columns and rows in memory. The length of the actual column
 * value is restricted. If the actual column value exceeds the specified maximum value, then the actual
 * column value is trimmed.
 *
 * @author Dmitry Shapovalov
 */
public final class RestrictedListEventHandler extends AbstractListEventHandler implements CsvConfigurable {

    private final int _maxColumnLength;

    private final String _moreCharactersMark;

    private final int _trimToIndex;

    /**
     * Create a new object.
     *
     * @param maxColumnLength the maximum length of the actual column value.
     */
    public RestrictedListEventHandler(final int maxColumnLength) {
        this(maxColumnLength, null);
    }

    /**
     * Create a new object.
     *
     * @param maxColumnLength    the maximum length of the actual column value.
     * @param moreCharactersMark text, added to the end of the trimmed column value.
     */
    public RestrictedListEventHandler(final int maxColumnLength, final String moreCharactersMark) {
        super();
        _maxColumnLength = maxColumnLength;
        _moreCharactersMark = moreCharactersMark;
        if (_moreCharactersMark == null) {
            _trimToIndex = _maxColumnLength;
        } else {
            _trimToIndex = Math.max(_maxColumnLength - _moreCharactersMark.length(), 0);
        }
    }

    @Override
    public void configure(final CsvParserConfiguration csvParserConfiguration) {
        csvParserConfiguration.setMaxColumnLength(_maxColumnLength);
        csvParserConfiguration.setMaxColumnLengthCheckEnabled(false);
    }

    @Override
    protected void doPushColumn(final String column, final int actualLength) {
        if (actualLength <= _maxColumnLength || _moreCharactersMark == null) {
            addColumnToCurrentRow(column);
        } else {
            addColumnToCurrentRow(column.substring(0, _trimToIndex) + _moreCharactersMark);
        }
    }

}
