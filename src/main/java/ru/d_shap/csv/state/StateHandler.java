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
import ru.d_shap.csv.CsvParseException;
import ru.d_shap.csv.NotRectangularException;
import ru.d_shap.csv.RowSeparators;
import ru.d_shap.csv.WrongColumnLengthException;
import ru.d_shap.csv.handler.IParserEventHandler;

/**
 * Class obtains events from the parser state machine and delegates them to a {@link ru.d_shap.csv.handler.IParserEventHandler} object.
 *
 * @author Dmitry Shapovalov
 */
public final class StateHandler {

    private static final int LAST_CHARACTERS_COUNT = 25;

    private final IParserEventHandler _parserEventHandler;

    private final boolean _checkRectangular;

    private final boolean _commaSeparator;

    private final boolean _semicolonSeparator;

    private final boolean _crSeparator;

    private final boolean _lfSeparator;

    private final boolean _crLfSeparator;

    private final CharStack _lastProcessedCharacters;

    private final CharBuffer _currentColumnCharacters;

    private boolean _firstRow;

    private int _firstRowColumnCount;

    private int _currentColumnCount;

    /**
     * Create a new object.
     *
     * @param parserEventHandler an event handler to delegate event calls.
     * @param checkRectangular   check if all rows should have the same column count.
     * @param columnSeparators   column separators, used by the parser.
     * @param rowSeparators      row separators, used by the parser.
     */
    public StateHandler(final IParserEventHandler parserEventHandler, final boolean checkRectangular, final Set<ColumnSeparators> columnSeparators, final Set<RowSeparators> rowSeparators) {
        super();
        _parserEventHandler = parserEventHandler;
        _checkRectangular = checkRectangular;

        _commaSeparator = columnSeparators.contains(ColumnSeparators.COMMA);
        _semicolonSeparator = columnSeparators.contains(ColumnSeparators.SEMICOLON);

        _crSeparator = rowSeparators.contains(RowSeparators.CR);
        _lfSeparator = rowSeparators.contains(RowSeparators.LF);
        _crLfSeparator = rowSeparators.contains(RowSeparators.CRLF);

        _lastProcessedCharacters = new CharStack(LAST_CHARACTERS_COUNT);
        _currentColumnCharacters = new CharBuffer(_parserEventHandler.getMaxColumnLength(), _parserEventHandler.checkMaxColumnLength());
        _firstRow = true;
        _firstRowColumnCount = 0;
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

    void pushLastProcessedCharacter(final int character) {
        if (character != SpecialCharacter.END_OF_INPUT) {
            _lastProcessedCharacters.append((char) character);
        }
    }

    String getLastProcessedCharacters() {
        return _lastProcessedCharacters.toString();
    }

    CsvParseException createCsvParseException(final int character) {
        return new CsvParseException(character, getLastProcessedCharacters());
    }

    void pushCharacter(final int character) {
        if (_currentColumnCharacters.canAppend()) {
            _currentColumnCharacters.append((char) character);
        } else {
            throw new WrongColumnLengthException(getLastProcessedCharacters());
        }
    }

    void pushColumn() {
        if (_checkRectangular && !_firstRow && _currentColumnCount >= _firstRowColumnCount) {
            throw new NotRectangularException(getLastProcessedCharacters());
        }

        String column = _currentColumnCharacters.toString();
        int actualLength = _currentColumnCharacters.getActualLength();
        _parserEventHandler.pushColumn(column, actualLength);
        _currentColumnCharacters.clear();
        _currentColumnCount++;
    }

    void pushRow() {
        if (_firstRow) {
            _firstRowColumnCount = _currentColumnCount;
            _firstRow = false;
        }
        if (_checkRectangular && _firstRowColumnCount != _currentColumnCount) {
            throw new NotRectangularException(getLastProcessedCharacters());
        }

        _parserEventHandler.pushRow();
        _currentColumnCharacters.clear();
        _currentColumnCount = 0;
    }

}
