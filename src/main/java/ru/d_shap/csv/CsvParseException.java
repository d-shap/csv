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

/**
 * Exception in thrown when CSV source is in a wrong format.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvParseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates new object.
     *
     * @param symbol      wrong symbol.
     * @param lastSymbols last symbols before wrong symbol.
     */
    public CsvParseException(final int symbol, final String lastSymbols) {
        super(getErrorMessage(symbol, lastSymbols));
    }

    private static String getErrorMessage(final int symbol, final String lastSymbols) {
        StringBuilder builder = new StringBuilder(80);
        builder.append("Wrong symbol obtained: '");
        builder.append((char) symbol);
        builder.append("' (");
        builder.append(symbol);
        builder.append("). ");
        builder.append("Last symbols: \"");
        builder.append(lastSymbols);
        builder.append("\".");
        return builder.toString();
    }

}
