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
    void processEndOfInput(final StateHandler parserEventHandler) {
        processPushColumnAndRow(parserEventHandler);
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
        if (parserEventHandler.isCrLfSeparator()) {
            return State4.INSTANCE;
        } else if (parserEventHandler.isCrSeparator()) {
            processPushColumnAndRow(parserEventHandler);
            return State1.INSTANCE;
        } else {
            return processPushUnquotedSymbol(CR, parserEventHandler);
        }
    }

    @Override
    State processLf(final StateHandler parserEventHandler) {
        if (parserEventHandler.isLfSeparator()) {
            processPushColumnAndRow(parserEventHandler);
            return State1.INSTANCE;
        } else {
            return processPushUnquotedSymbol(LF, parserEventHandler);
        }
    }

    @Override
    State processQuot(final StateHandler parserEventHandler) {
        throw new CsvParseException(QUOT, parserEventHandler.getLastSymbols());
    }

    @Override
    State processSymbol(final int symbol, final StateHandler parserEventHandler) {
        return processPushUnquotedSymbol(symbol, parserEventHandler);
    }

}
