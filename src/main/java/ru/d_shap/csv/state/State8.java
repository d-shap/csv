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
final class State8 extends AbstractState {

    /*
     * State to process unquoted column.
     */

    static final State8 INSTANCE = new State8();

    private State8() {
        super();
    }

    @Override
    public AbstractState processInput(final int symbol, final ParserEventHandler parserEventHandler) {
        parserEventHandler.addLastSymbol(symbol);
        switch (symbol) {
            case END_OF_INPUT:
                parserEventHandler.pushColumn();
                parserEventHandler.pushRow();
                return null;
            case COMMA:
                if (parserEventHandler.isCommaSeparator()) {
                    parserEventHandler.pushColumn();
                    return State1.INSTANCE;
                } else {
                    parserEventHandler.pushSymbol(symbol);
                    return State8.INSTANCE;
                }
            case SEMICOLON:
                if (parserEventHandler.isSemicolonSeparator()) {
                    parserEventHandler.pushColumn();
                    return State1.INSTANCE;
                } else {
                    parserEventHandler.pushSymbol(symbol);
                    return State8.INSTANCE;
                }
            case CR:
                if (parserEventHandler.isCrLfSeparator()) {
                    return State4.INSTANCE;
                } else if (parserEventHandler.isCrSeparator()) {
                    parserEventHandler.pushColumn();
                    parserEventHandler.pushRow();
                    return State2.INSTANCE;
                } else {
                    parserEventHandler.pushSymbol(symbol);
                    return State8.INSTANCE;
                }
            case LF:
                if (parserEventHandler.isLfSeparator()) {
                    parserEventHandler.pushColumn();
                    parserEventHandler.pushRow();
                    return State2.INSTANCE;
                } else {
                    parserEventHandler.pushSymbol(symbol);
                    return State8.INSTANCE;
                }
            case QUOT:
                throw new CsvParseException(symbol, parserEventHandler.getLastSymbols());
            default:
                parserEventHandler.pushSymbol(symbol);
                return State8.INSTANCE;
        }
    }

}