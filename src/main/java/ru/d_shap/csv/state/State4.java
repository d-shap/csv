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
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.csv.state;

/**
 * State of CSV parser state machine.
 *
 * @author Dmitry Shapovalov
 */
final class State4 extends AbstractState {

    /*
     * State after CR after column separator.
     */

    static final State4 INSTANCE = new State4();

    private State4() {
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
                parserEventHandler.pushColumn();
                parserEventHandler.pushRow();
                parserEventHandler.pushColumn();
                return State1.INSTANCE;
            case SEMICOLON:
                parserEventHandler.pushColumn();
                parserEventHandler.pushRow();
                parserEventHandler.pushColumn();
                return State1.INSTANCE;
            case CR:
                parserEventHandler.pushColumn();
                parserEventHandler.pushRow();
                return State3.INSTANCE;
            case LF:
                parserEventHandler.pushColumn();
                parserEventHandler.pushRow();
                return State2.INSTANCE;
            case QUOT:
                parserEventHandler.pushColumn();
                parserEventHandler.pushRow();
                return State5.INSTANCE;
            default:
                parserEventHandler.pushColumn();
                parserEventHandler.pushRow();
                parserEventHandler.pushSymbol(symbol);
                return State7.INSTANCE;
        }
    }

}
