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
final class State7 extends AbstractState {

    /*
     * State after double quote in quoted column.
     */

    static final AbstractState INSTANCE = new State7();

    private State7() {
        super();
    }

    @Override
    void processEndOfInput(final ParserEventHandler parserEventHandler) {
        processPushColumnAndRow(parserEventHandler);
    }

    @Override
    AbstractState processComma(final ParserEventHandler parserEventHandler) {
        return processDisallowedComma(parserEventHandler);
    }

    @Override
    AbstractState processSemicolon(final ParserEventHandler parserEventHandler) {
        return processDisallowedSemicolon(parserEventHandler);
    }

    @Override
    AbstractState processCr(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrLfSeparator()) {
            return State5.INSTANCE;
        } else if (parserEventHandler.isCrSeparator()) {
            processPushColumnAndRow(parserEventHandler);
            return State1.INSTANCE;
        } else {
            throw new CsvParseException(CR, parserEventHandler.getLastSymbols());
        }
    }

    @Override
    AbstractState processLf(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isLfSeparator()) {
            processPushColumnAndRow(parserEventHandler);
            return State1.INSTANCE;
        } else {
            throw new CsvParseException(LF, parserEventHandler.getLastSymbols());
        }
    }

    @Override
    AbstractState processQuot(final ParserEventHandler parserEventHandler) {
        return processPushQuotedSymbol(QUOT, parserEventHandler);
    }

    @Override
    AbstractState processSymbol(final int symbol, final ParserEventHandler parserEventHandler) {
        throw new CsvParseException(symbol, parserEventHandler.getLastSymbols());
    }

}
