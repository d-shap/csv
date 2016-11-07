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
final class State4 extends AbstractState {

    /*
     * State after CR after column separator.
     * State after CR after symbol.
     */

    static final State4 INSTANCE = new State4();

    private State4() {
        super();
    }

    @Override
    void processEndOfInput(final int symbol, final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            parserEventHandler.pushColumn();
            parserEventHandler.pushRow();
        } else {
            parserEventHandler.pushSymbol(CR);
            parserEventHandler.pushColumn();
            parserEventHandler.pushRow();
        }
    }

    @Override
    AbstractState processComma(final int symbol, final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            parserEventHandler.pushColumn();
            parserEventHandler.pushRow();
            if (parserEventHandler.isCommaSeparator()) {
                parserEventHandler.pushColumn();
                return State1.INSTANCE;
            } else {
                parserEventHandler.pushSymbol(symbol);
                return State8.INSTANCE;
            }
        } else {
            parserEventHandler.pushSymbol(CR);
            if (parserEventHandler.isCommaSeparator()) {
                parserEventHandler.pushColumn();
                return State1.INSTANCE;
            } else {
                parserEventHandler.pushSymbol(symbol);
                return State8.INSTANCE;
            }
        }
    }

    @Override
    AbstractState processSemicolon(final int symbol, final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            parserEventHandler.pushColumn();
            parserEventHandler.pushRow();
            if (parserEventHandler.isSemicolonSeparator()) {
                parserEventHandler.pushColumn();
                return State1.INSTANCE;
            } else {
                parserEventHandler.pushSymbol(symbol);
                return State8.INSTANCE;
            }
        } else {
            parserEventHandler.pushSymbol(CR);
            if (parserEventHandler.isSemicolonSeparator()) {
                parserEventHandler.pushColumn();
                return State1.INSTANCE;
            } else {
                parserEventHandler.pushSymbol(symbol);
                return State8.INSTANCE;
            }
        }
    }

    @Override
    AbstractState processCr(final int symbol, final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            parserEventHandler.pushColumn();
            parserEventHandler.pushRow();
            return State3.INSTANCE;
        } else {
            parserEventHandler.pushSymbol(CR);
            return State4.INSTANCE;
        }
    }

    @Override
    AbstractState processLf(final int symbol, final ParserEventHandler parserEventHandler) {
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        return State2.INSTANCE;
    }

    @Override
    AbstractState processQuot(final int symbol, final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            parserEventHandler.pushColumn();
            parserEventHandler.pushRow();
            return State6.INSTANCE;
        } else {
            throw new CsvParseException(symbol, parserEventHandler.getLastSymbols());
        }
    }

    @Override
    AbstractState processDefault(final int symbol, final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCrSeparator()) {
            parserEventHandler.pushColumn();
            parserEventHandler.pushRow();
            parserEventHandler.pushSymbol(symbol);
            return State8.INSTANCE;
        } else {
            parserEventHandler.pushSymbol(CR);
            parserEventHandler.pushSymbol(symbol);
            return State8.INSTANCE;
        }
    }

}
