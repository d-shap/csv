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
 * Exception in thrown when column length exceeds max column length.
 *
 * @author Dmitry Shapovalov
 */
public class WrongColumnLengthException extends CsvException {

    private static final long serialVersionUID = 1L;

    /**
     * Create new object.
     *
     * @param lastSymbols last symbols processed by CSV parser.
     */
    public WrongColumnLengthException(final String lastSymbols) {
        super("Maximum column length exceeded.", lastSymbols);
    }

}