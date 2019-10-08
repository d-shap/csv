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

import ru.d_shap.csv.CsvParseException;
import ru.d_shap.csv.CsvParserConfiguration;
import ru.d_shap.csv.WrongColumnCountException;
import ru.d_shap.csv.WrongColumnLengthException;
import ru.d_shap.csv.handler.CsvEventHandler;

/**
 * Class to process events from the CSV parser state machine and delegate them to a {@link CsvEventHandler} object.
 *
 * @author Dmitry Shapovalov
 */
public final class StateHandler {

    private static final int LAST_CHARACTERS_COUNT = 25;

    private static final int INITIAL_ROW_COLUMN_COUNT = -1;

    private final CsvEventHandler _csvEventHandler;

    private final CsvParserConfiguration _csvParserConfiguration;

    private final CharStack _lastProcessedCharacters;

    private final CharBuffer _currentColumnCharacters;

    private int _firstRowColumnCount;

    private int _currentColumnCount;

    /**
     * Create a new object.
     *
     * @param csvEventHandler        event handler to process CSV parser events.
     * @param csvParserConfiguration CSV parser configuration object.
     */
    public StateHandler(final CsvEventHandler csvEventHandler, final CsvParserConfiguration csvParserConfiguration) {
        super();
        _csvEventHandler = csvEventHandler;
        csvParserConfiguration.validate();
        _csvParserConfiguration = csvParserConfiguration;
        _lastProcessedCharacters = new CharStack(LAST_CHARACTERS_COUNT);
        _currentColumnCharacters = new CharBuffer(_csvParserConfiguration.getMaxColumnLength(), _csvParserConfiguration.isMaxColumnLengthCheckEnabled());
        _firstRowColumnCount = INITIAL_ROW_COLUMN_COUNT;
        _currentColumnCount = 0;
    }

    boolean isCommaSeparator() {
        return _csvParserConfiguration.isCommaSeparator();
    }

    boolean isSemicolonSeparator() {
        return _csvParserConfiguration.isSemicolonSeparator();
    }

    boolean isCrSeparator() {
        return _csvParserConfiguration.isCrSeparator();
    }

    boolean isLfSeparator() {
        return _csvParserConfiguration.isLfSeparator();
    }

    boolean isCrLfSeparator() {
        return _csvParserConfiguration.isCrLfSeparator();
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
        if (_csvParserConfiguration.isColumnCountCheckEnabled() && _firstRowColumnCount != INITIAL_ROW_COLUMN_COUNT && _currentColumnCount >= _firstRowColumnCount) {
            throw new WrongColumnCountException(getLastProcessedCharacters());
        }

        String column = _currentColumnCharacters.toString();
        int actualLength = _currentColumnCharacters.getActualLength();
        _csvEventHandler.pushColumn(column, actualLength);
        _currentColumnCharacters.clear();
        _currentColumnCount++;
    }

    void pushRow() {
        if (_csvParserConfiguration.isSkipEmptyRowsEnabled() && _currentColumnCount == 0) {
            return;
        }

        if (_firstRowColumnCount == INITIAL_ROW_COLUMN_COUNT) {
            _firstRowColumnCount = _currentColumnCount;
        } else if (_csvParserConfiguration.isColumnCountCheckEnabled() && _firstRowColumnCount != _currentColumnCount) {
            throw new WrongColumnCountException(getLastProcessedCharacters());
        }

        _csvEventHandler.pushRow();
        _currentColumnCharacters.clear();
        _currentColumnCount = 0;
    }

}
