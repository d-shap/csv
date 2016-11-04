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
final class State6 extends AbstractState {

    /*
     * State to process quoted column.
     */

    static final State6 INSTANCE = new State6();

    private State6() {
        super();
    }

    @Override
    public AbstractState processInput(final int symbol, final ParserEventHandler parserEventHandler) {
        parserEventHandler.addLastSymbol(symbol);
        switch (symbol) {
            case END_OF_INPUT:
                throw new CsvParseException(symbol, parserEventHandler.getLastSymbols());
            case COMMA:
                parserEventHandler.pushSymbol(symbol);
                return State6.INSTANCE;
            case SEMICOLON:
                parserEventHandler.pushSymbol(symbol);
                return State6.INSTANCE;
            case CR:
                parserEventHandler.pushSymbol(symbol);
                return State6.INSTANCE;
            case LF:
                parserEventHandler.pushSymbol(symbol);
                return State6.INSTANCE;
            case QUOT:
                return State7.INSTANCE;
            default:
                parserEventHandler.pushSymbol(symbol);
                return State6.INSTANCE;
        }
    }

}
