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
 * State after CR after double quote in quoted column.
 *
 * @author Dmitry Shapovalov
 */
final class State5 extends AbstractState {

    static final AbstractState INSTANCE = new State5();

    private State5() {
        super();
    }

    @Override
    void processEndOfInput(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            processPushColumnAndRow(parserEventHandler);
        } else {
            throw new CsvParseException(END_OF_INPUT, parserEventHandler.getLastSymbols());
        }
    }

    @Override
    AbstractState processComma(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            processPushColumnAndRow(parserEventHandler);
            return processAllowedComma(parserEventHandler);
        } else {
            throw new CsvParseException(COMMA, parserEventHandler.getLastSymbols());
        }
    }

    @Override
    AbstractState processSemicolon(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            processPushColumnAndRow(parserEventHandler);
            return processAllowedSemicolon(parserEventHandler);
        } else {
            throw new CsvParseException(SEMICOLON, parserEventHandler.getLastSymbols());
        }
    }

    @Override
    AbstractState processCr(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            processPushColumnAndRow(parserEventHandler);
            return State3.INSTANCE;
        } else {
            throw new CsvParseException(CR, parserEventHandler.getLastSymbols());
        }
    }

    @Override
    AbstractState processLf(final ParserEventHandler parserEventHandler) {
        processPushColumnAndRow(parserEventHandler);
        return State1.INSTANCE;
    }

    @Override
    AbstractState processQuot(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            processPushColumnAndRow(parserEventHandler);
            return State6.INSTANCE;
        } else {
            throw new CsvParseException(QUOT, parserEventHandler.getLastSymbols());
        }
    }

    @Override
    AbstractState processSymbol(final int symbol, final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            processPushColumnAndRow(parserEventHandler);
            return processPushUnquotedSymbol(symbol, parserEventHandler);
        } else {
            throw new CsvParseException(symbol, parserEventHandler.getLastSymbols());
        }
    }

}
