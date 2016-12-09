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

/**
 * State of CSV parser state machine.
 *
 * @author Dmitry Shapovalov
 */
final class State3 extends AbstractState {

    /*
     * State after CR after init state.
     * State after CR after row separator.
     */

    static final AbstractState INSTANCE = new State3();

    private State3() {
        super();
    }

    @Override
    void processEndOfInput(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            processPushRow(parserEventHandler);
        } else {
            processPushCr(parserEventHandler);
            processPushColumnAndRow(parserEventHandler);
        }
    }

    @Override
    AbstractState processComma(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            processPushRow(parserEventHandler);
            return processAllowedComma(parserEventHandler);
        } else {
            processPushCr(parserEventHandler);
            return processAllowedComma(parserEventHandler);
        }
    }

    @Override
    AbstractState processSemicolon(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            processPushRow(parserEventHandler);
            return processAllowedSemicolon(parserEventHandler);
        } else {
            processPushCr(parserEventHandler);
            return processAllowedSemicolon(parserEventHandler);
        }
    }

    @Override
    AbstractState processCr(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            processPushRow(parserEventHandler);
            return State3.INSTANCE;
        } else {
            processPushCr(parserEventHandler);
            return State4.INSTANCE;
        }
    }

    @Override
    AbstractState processLf(final ParserEventHandler parserEventHandler) {
        processPushRow(parserEventHandler);
        return State1.INSTANCE;
    }

    @Override
    AbstractState processQuot(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            processPushRow(parserEventHandler);
            return State6.INSTANCE;
        } else {
            throw new CsvParseException(QUOT, parserEventHandler.getLastSymbols());
        }
    }

    @Override
    AbstractState processSymbol(final int symbol, final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            processPushRow(parserEventHandler);
            return processPushUnquotedSymbol(symbol, parserEventHandler);
        } else {
            processPushCr(parserEventHandler);
            return processPushUnquotedSymbol(symbol, parserEventHandler);
        }
    }

}
