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
package ru.d_shap.csv.state;

import ru.d_shap.csv.NotRectangularException;
import ru.d_shap.csv.handler.IParserEventHandler;

/**
 * Class obtains events from parser state machine and delegates them to {@link ru.d_shap.csv.handler.IParserEventHandler} object.
 *
 * @author Dmitry Shapovalov
 */
public final class ParserEventHandler {

    private final IParserEventHandler _parserEventHandler;

    private final boolean _checkRectangular;

    private final CharStack _lastSymbols;

    private final CharBuffer _currentColumn;

    private int _firstRowColumnCount;

    private int _currentColumnCount;

    /**
     * Creates new object.
     *
     * @param parserEventHandler event handler to delegate event calls.
     * @param checkRectangular   check if all rows should have the same column count.
     */
    public ParserEventHandler(final IParserEventHandler parserEventHandler, final boolean checkRectangular) {
        super();
        _parserEventHandler = parserEventHandler;
        _checkRectangular = checkRectangular;
        _lastSymbols = new CharStack();
        _currentColumn = new CharBuffer();
        _firstRowColumnCount = -1;
        _currentColumnCount = 0;
    }

    void addLastSymbol(final int symbol) {
        _lastSymbols.append((char) symbol);
    }

    String getLastSymbols() {
        return _lastSymbols.toString();
    }

    void pushSymbol(final int symbol) {
        _currentColumn.append((char) symbol);
    }

    void pushColumn() {
        String column = _currentColumn.toString();
        _parserEventHandler.pushColumn(column);
        _currentColumn.clear();
        _currentColumnCount++;
    }

    void pushRow() {
        if (_firstRowColumnCount < 0) {
            _firstRowColumnCount = _currentColumnCount;
        }
        if (_checkRectangular && _firstRowColumnCount != _currentColumnCount) {
            throw new NotRectangularException(_lastSymbols.toString());
        }
        _parserEventHandler.pushRow();
        _currentColumn.clear();
        _currentColumnCount = 0;
    }

}
