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
 * State to process quoted column.
 *
 * @author Dmitry Shapovalov
 */
final class State6 extends State {

    static final State INSTANCE = new State6();

    private State6() {
        super();
    }

    @Override
    void processEndOfInput(final StateHandler stateHandler) {
        throw stateHandler.createCsvParseException(SpecialCharacter.END_OF_INPUT);
    }

    @Override
    State processComma(final StateHandler stateHandler) {
        return pushQuotedCharacter(SpecialCharacter.COMMA, stateHandler);
    }

    @Override
    State processSemicolon(final StateHandler stateHandler) {
        return pushQuotedCharacter(SpecialCharacter.SEMICOLON, stateHandler);
    }

    @Override
    State processCr(final StateHandler stateHandler) {
        return pushQuotedCharacter(SpecialCharacter.CR, stateHandler);
    }

    @Override
    State processLf(final StateHandler stateHandler) {
        return pushQuotedCharacter(SpecialCharacter.LF, stateHandler);
    }

    @Override
    State processQuot(final StateHandler stateHandler) {
        return State7.INSTANCE;
    }

    @Override
    State processDefault(final int character, final StateHandler stateHandler) {
        return pushQuotedCharacter(character, stateHandler);
    }

}
