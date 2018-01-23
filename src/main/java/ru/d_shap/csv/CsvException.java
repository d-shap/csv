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
package ru.d_shap.csv;

/**
 * Base class for all exceptions.
 *
 * @author Dmitry Shapovalov
 */
public class CsvException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new object.
     *
     * @param message exception message.
     */
    protected CsvException(final String message) {
        super(message);
    }

    /**
     * Create a new object.
     *
     * @param message exception message.
     * @param ex      root cause of the exception.
     */
    protected CsvException(final String message, final Throwable ex) {
        super(message, ex);
    }

    /**
     * Create a new object.
     *
     * @param message                 exception message.
     * @param lastProcessedCharacters last characters processed by CSV parser.
     */
    protected CsvException(final String message, final String lastProcessedCharacters) {
        super(getErrorMessage(message, lastProcessedCharacters));
    }

    private static String getErrorMessage(final String message, final String lastProcessedCharacters) {
        StringBuilder builder = new StringBuilder(80);
        builder.append(message);
        builder.append(" Last characters: \"");
        builder.append(lastProcessedCharacters);
        builder.append("\".");
        return builder.toString();
    }

}
