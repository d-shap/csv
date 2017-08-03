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
 * State after column separator.
 *
 * @author Dmitry Shapovalov
 */
final class State2 extends AbstractState {

    static final AbstractState INSTANCE = new State2();

    private State2() {
        super();
    }

    @Override
    void processEndOfInput(final ParserEventHandler parserEventHandler) {
        processPushColumnAndRow(parserEventHandler);
    }

    @Override
    AbstractState processComma(final ParserEventHandler parserEventHandler) {
        return processAllowedComma(parserEventHandler);
    }

    @Override
    AbstractState processSemicolon(final ParserEventHandler parserEventHandler) {
        return processAllowedSemicolon(parserEventHandler);
    }

    @Override
    AbstractState processCr(final ParserEventHandler parserEventHandler) {
        return State4.INSTANCE;
    }

    @Override
    AbstractState processLf(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isLfSeparator()) {
            processPushColumnAndRow(parserEventHandler);
            return State1.INSTANCE;
        } else {
            return processPushUnquotedSymbol(LF, parserEventHandler);
        }
    }

    @Override
    AbstractState processQuot(final ParserEventHandler parserEventHandler) {
        return State6.INSTANCE;
    }

    @Override
    AbstractState processSymbol(final int symbol, final ParserEventHandler parserEventHandler) {
        return processPushUnquotedSymbol(symbol, parserEventHandler);
    }

}
