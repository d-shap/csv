// CSV-parser converts source stream to rows and columns and vice versa.
// Copyright (C) 2016 Dmitry Shapovalov.
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
package ru.d_shap.csv.state;

import ru.d_shap.csv.CsvParseException;

/**
 * State of CSV parser state machine.
 *
 * @author Dmitry Shapovalov
 */
final class State5 extends AbstractState {

    /*
     * State to process quoted column.
     */

    static final State5 INSTANCE = new State5();

    private State5() {
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
                return State5.INSTANCE;
            case SEMICOLON:
                parserEventHandler.pushSymbol(symbol);
                return State5.INSTANCE;
            case CR:
                parserEventHandler.pushSymbol(symbol);
                return State5.INSTANCE;
            case LF:
                parserEventHandler.pushSymbol(symbol);
                return State5.INSTANCE;
            case QUOT:
                return State6.INSTANCE;
            default:
                parserEventHandler.pushSymbol(symbol);
                return State5.INSTANCE;
        }
    }

}
