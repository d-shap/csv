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
 * State of CSV parser state machine.
 * Init state.
 * State after row separator.
 *
 * @author Dmitry Shapovalov
 */
final class State1 extends State {

    static final State INSTANCE = new State1();

    private State1() {
        super();
    }

    @Override
    void processEndOfInput(final StateHandler parserEventHandler) {
        // Ignore
    }

    @Override
    State processComma(final StateHandler parserEventHandler) {
        return processAllowedComma(parserEventHandler);
    }

    @Override
    State processSemicolon(final StateHandler parserEventHandler) {
        return processAllowedSemicolon(parserEventHandler);
    }

    @Override
    State processCr(final StateHandler parserEventHandler) {
        return State3.INSTANCE;
    }

    @Override
    State processLf(final StateHandler parserEventHandler) {
        if (parserEventHandler.isLfSeparator()) {
            processPushRow(parserEventHandler);
            return State1.INSTANCE;
        } else {
            return processPushUnquotedSymbol(LF, parserEventHandler);
        }
    }

    @Override
    State processQuot(final StateHandler parserEventHandler) {
        return State6.INSTANCE;
    }

    @Override
    State processSymbol(final int symbol, final StateHandler parserEventHandler) {
        return processPushUnquotedSymbol(symbol, parserEventHandler);
    }

}
