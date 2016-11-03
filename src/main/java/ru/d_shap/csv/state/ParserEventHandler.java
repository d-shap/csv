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

import java.util.Set;

import ru.d_shap.csv.ColumnSeparators;
import ru.d_shap.csv.NotRectangularException;
import ru.d_shap.csv.RowSeparators;
import ru.d_shap.csv.WrongColumnLengthException;
import ru.d_shap.csv.handler.IParserEventHandler;

/**
 * Class obtains events from parser state machine and delegates them to {@link ru.d_shap.csv.handler.IParserEventHandler} object.
 *
 * @author Dmitry Shapovalov
 */
public final class ParserEventHandler {

    private static final int LAST_SYMBOLS_COUNT = 25;

    private final IParserEventHandler _parserEventHandler;

    private final boolean _checkRectangular;

    private final boolean _commaSeparator;

    private final boolean _semicolonSeparator;

    private final boolean _crSeparator;

    private final boolean _lfSeparator;

    private final boolean _crLfSeparator;

    private final CharStack _lastSymbols;

    private final CharBuffer _currentColumn;

    private int _firstRowColumnCount;

    private int _currentColumnCount;

    /**
     * Create new object.
     *
     * @param parserEventHandler event handler to delegate event calls.
     * @param checkRectangular   check if all rows should have the same column count.
     * @param columnSeparators   column separators, used by parser.
     * @param rowSeparators      row separators, used by parser.
     */
    public ParserEventHandler(final IParserEventHandler parserEventHandler, final boolean checkRectangular, final Set<ColumnSeparators> columnSeparators, final Set<RowSeparators> rowSeparators) {
        super();
        _parserEventHandler = parserEventHandler;
        _checkRectangular = checkRectangular;

        _commaSeparator = columnSeparators.contains(ColumnSeparators.COMMA);
        _semicolonSeparator = columnSeparators.contains(ColumnSeparators.SEMICOLON);

        _crSeparator = rowSeparators.contains(RowSeparators.CR);
        _lfSeparator = rowSeparators.contains(RowSeparators.LF);
        _crLfSeparator = rowSeparators.contains(RowSeparators.CRLF);

        _lastSymbols = new CharStack(LAST_SYMBOLS_COUNT);
        _currentColumn = new CharBuffer(_parserEventHandler.getMaxColumnLength(), _parserEventHandler.checkMaxColumnLength());
        _firstRowColumnCount = -1;
        _currentColumnCount = 0;
    }

    boolean isCommaSeparator() {
        return _commaSeparator;
    }

    boolean isSemicolonSeparator() {
        return _semicolonSeparator;
    }

    boolean isCrSeparator() {
        return _crSeparator;
    }

    boolean isLfSeparator() {
        return _lfSeparator;
    }

    boolean isCrLfSeparator() {
        return _crLfSeparator;
    }

    void addLastSymbol(final int symbol) {
        if (symbol != AbstractState.END_OF_INPUT) {
            _lastSymbols.append((char) symbol);
        }
    }

    String getLastSymbols() {
        return _lastSymbols.toString();
    }

    void pushSymbol(final int symbol) {
        if (_currentColumn.canAppend()) {
            _currentColumn.append((char) symbol);
        } else {
            throw new WrongColumnLengthException(_lastSymbols.toString());
        }
    }

    void pushColumn() {
        if (_checkRectangular && _firstRowColumnCount >= 0 && _currentColumnCount >= _firstRowColumnCount) {
            throw new NotRectangularException(_lastSymbols.toString());
        }

        String column = _currentColumn.toString();
        int actualLength = _currentColumn.getActualLength();
        _parserEventHandler.pushColumn(column, actualLength);
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
