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
 * CSV parser state abstraction.
 *
 * @author Dmitry Shapovalov
 */
public abstract class State {

    State() {
        super();
    }

    /**
     * Get an init state of the CSV parser state machine.
     *
     * @return an init state.
     */
    public static State getInitState() {
        return State1.INSTANCE;
    }

    /**
     * Process an input character and define next CSV parser state.
     *
     * @param character    an input character.
     * @param stateHandler handler to process CSV parser events.
     *
     * @return next CSV parser state.
     */
    public final State processCharacter(final int character, final StateHandler stateHandler) {
        stateHandler.pushLastProcessedCharacter(character);
        switch (character) {
            case SpecialCharacter.END_OF_INPUT:
                processEndOfInput(stateHandler);
                return null;
            case SpecialCharacter.COMMA:
                return processComma(stateHandler);
            case SpecialCharacter.SEMICOLON:
                return processSemicolon(stateHandler);
            case SpecialCharacter.CR:
                return processCr(stateHandler);
            case SpecialCharacter.LF:
                return processLf(stateHandler);
            case SpecialCharacter.QUOT:
                return processQuot(stateHandler);
            default:
                return processDefault(character, stateHandler);
        }
    }

    abstract void processEndOfInput(StateHandler stateHandler);

    abstract State processComma(StateHandler stateHandler);

    final State pushAllowedComma(final StateHandler stateHandler) {
        if (stateHandler.isCommaSeparator()) {
            stateHandler.pushColumn();
            return State2.INSTANCE;
        } else {
            return pushUnquotedCharacter(SpecialCharacter.COMMA, stateHandler);
        }
    }

    final State pushDisallowedComma(final StateHandler stateHandler) {
        if (stateHandler.isCommaSeparator()) {
            stateHandler.pushColumn();
            return State2.INSTANCE;
        } else {
            throw stateHandler.createCsvParseException(SpecialCharacter.COMMA);
        }
    }

    abstract State processSemicolon(StateHandler stateHandler);

    final State pushAllowedSemicolon(final StateHandler stateHandler) {
        if (stateHandler.isSemicolonSeparator()) {
            stateHandler.pushColumn();
            return State2.INSTANCE;
        } else {
            return pushUnquotedCharacter(SpecialCharacter.SEMICOLON, stateHandler);
        }
    }

    final State pushDisallowedSemicolon(final StateHandler stateHandler) {
        if (stateHandler.isSemicolonSeparator()) {
            stateHandler.pushColumn();
            return State2.INSTANCE;
        } else {
            throw stateHandler.createCsvParseException(SpecialCharacter.SEMICOLON);
        }
    }

    abstract State processCr(StateHandler stateHandler);

    final void pushCr(final StateHandler stateHandler) {
        stateHandler.pushCharacter(SpecialCharacter.CR);
    }

    abstract State processLf(StateHandler stateHandler);

    final void pushColumnAndRow(final StateHandler stateHandler) {
        stateHandler.pushColumn();
        stateHandler.pushRow();
    }

    final void pushRow(final StateHandler stateHandler) {
        stateHandler.pushRow();
    }

    abstract State processQuot(StateHandler stateHandler);

    final State pushQuotedCharacter(final int character, final StateHandler stateHandler) {
        stateHandler.pushCharacter(character);
        return State6.INSTANCE;
    }

    abstract State processDefault(int character, StateHandler stateHandler);

    final State pushUnquotedCharacter(final int character, final StateHandler stateHandler) {
        stateHandler.pushCharacter(character);
        return State8.INSTANCE;
    }

}
