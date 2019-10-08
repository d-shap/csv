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
 * State of the CSV parser state machine.
 * State to process unquoted column.
 *
 * @author Dmitry Shapovalov
 */
final class State8 extends State {

    static final State INSTANCE = new State8();

    private State8() {
        super();
    }

    @Override
    void processEndOfInput(final StateHandler stateHandler) {
        pushColumnAndRow(stateHandler);
    }

    @Override
    State processComma(final StateHandler stateHandler) {
        return pushAllowedComma(stateHandler);
    }

    @Override
    State processSemicolon(final StateHandler stateHandler) {
        return pushAllowedSemicolon(stateHandler);
    }

    @Override
    State processCr(final StateHandler stateHandler) {
        if (stateHandler.isCrLfSeparator()) {
            return State4.INSTANCE;
        } else if (stateHandler.isCrSeparator()) {
            pushColumnAndRow(stateHandler);
            return State1.INSTANCE;
        } else {
            return pushUnquotedCharacter(SpecialCharacter.CR, stateHandler);
        }
    }

    @Override
    State processLf(final StateHandler stateHandler) {
        if (stateHandler.isLfSeparator()) {
            pushColumnAndRow(stateHandler);
            return State1.INSTANCE;
        } else {
            return pushUnquotedCharacter(SpecialCharacter.LF, stateHandler);
        }
    }

    @Override
    State processQuot(final StateHandler stateHandler) {
        throw stateHandler.createCsvParseException(SpecialCharacter.QUOT);
    }

    @Override
    State processDefault(final int character, final StateHandler stateHandler) {
        return pushUnquotedCharacter(character, stateHandler);
    }

}
