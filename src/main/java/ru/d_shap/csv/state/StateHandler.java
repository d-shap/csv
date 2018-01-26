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

    private final CsvEventHandler _csvEventHandler;

    private final boolean _commaSeparator;

    private final boolean _semicolonSeparator;

    private final boolean _crSeparator;

    private final boolean _lfSeparator;

    private final boolean _crLfSeparator;

    private final boolean _columnCountCheckEnabled;

    private final boolean _skipEmptyRowsEnabled;

    private final CharStack _lastProcessedCharacters;

    private final CharBuffer _currentColumnCharacters;

    private boolean _firstRow;

    private int _firstRowColumnCount;

    private int _currentColumnCount;

    /**
     * Create a new object.
     *
     * @param csvEventHandler    event handler to process CSV parser events.
     * @param stateHandlerConfig CSV parser configuration.
     */
    public StateHandler(final CsvEventHandler csvEventHandler, final StateHandlerConfiguration stateHandlerConfig) {
        super();
        _csvEventHandler = csvEventHandler;
        _commaSeparator = stateHandlerConfig.isCommaSeparator();
        _semicolonSeparator = stateHandlerConfig.isSemicolonSeparator();
        _crSeparator = stateHandlerConfig.isCrSeparator();
        _lfSeparator = stateHandlerConfig.isLfSeparator();
        _crLfSeparator = stateHandlerConfig.isCrLfSeparator();
        _columnCountCheckEnabled = stateHandlerConfig.isColumnCountCheckEnabled();
        _skipEmptyRowsEnabled = stateHandlerConfig.isSkipEmptyRowsEnabled();

        _lastProcessedCharacters = new CharStack(LAST_CHARACTERS_COUNT);
        _currentColumnCharacters = new CharBuffer(stateHandlerConfig.getMaxColumnLength(), stateHandlerConfig.isMaxColumnLengthCheckEnabled());

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
        if (_columnCountCheckEnabled && !_firstRow && _currentColumnCount >= _firstRowColumnCount) {
            throw new WrongColumnCountException(getLastProcessedCharacters());
        }

        String column = _currentColumnCharacters.toString();
        int actualLength = _currentColumnCharacters.getActualLength();
        _csvEventHandler.pushColumn(column, actualLength);
        _currentColumnCharacters.clear();
        _currentColumnCount++;
    }

    void pushRow() {
        if (_skipEmptyRowsEnabled && _currentColumnCount == 0) {
            return;
        }

        if (_firstRow) {
            _firstRowColumnCount = _currentColumnCount;
            _firstRow = false;
        } else if (_columnCountCheckEnabled && _firstRowColumnCount != _currentColumnCount) {
            throw new WrongColumnCountException(getLastProcessedCharacters());
        }

        _csvEventHandler.pushRow();
        _currentColumnCharacters.clear();
        _currentColumnCount = 0;
    }

}
