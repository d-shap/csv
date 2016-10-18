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
package ru.d_shap.csv;

import ru.d_shap.csv.state.AbstractState;

/**
 * Available separators between columns in one row.
 *
 * @author Dmitry Shapovalov
 */
public enum ColumnSeparators {

    COMMA(String.valueOf((char) AbstractState.COMMA)),

    SEMICOLON(String.valueOf((char) AbstractState.SEMICOLON));

    private final String _value;

    ColumnSeparators(final String value) {
        _value = value;
    }

    String getValue() {
        return _value;
    }

}
