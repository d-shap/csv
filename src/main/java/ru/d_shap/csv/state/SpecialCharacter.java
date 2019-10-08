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
package ru.d_shap.csv.state;

/**
 * Constants for the special characters.
 *
 * @author Dmitry Shapovalov
 */
public final class SpecialCharacter {

    public static final int END_OF_INPUT = -1;

    public static final int COMMA = ',';

    public static final int SEMICOLON = ';';

    public static final int CR = '\r';

    public static final int LF = '\n';

    public static final int QUOT = '"';

    private SpecialCharacter() {
        super();
    }

}
