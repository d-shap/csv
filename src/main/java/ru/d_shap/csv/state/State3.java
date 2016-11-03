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
 *
 * @author Dmitry Shapovalov
 */
final class State3 extends AbstractState {

    /*
     * State after CR after init state.
     * State after CR after row separator.
     */

    static final State3 INSTANCE = new State3();

    private State3() {
        super();
    }

    @Override
    public AbstractState processInput(final int symbol, final ParserEventHandler parserEventHandler) {
        parserEventHandler.addLastSymbol(symbol);
        switch (symbol) {
            case END_OF_INPUT:
                if (parserEventHandler.isCrSeparator()) {
                    parserEventHandler.pushRow();
                    return null;
                } else {
                    parserEventHandler.pushSymbol(CR);
                    parserEventHandler.pushColumn();
                    parserEventHandler.pushRow();
                    return null;
                }
            case COMMA:
                if (parserEventHandler.isCrSeparator()) {
                    parserEventHandler.pushRow();
                    if (parserEventHandler.isCommaSeparator()) {
                        parserEventHandler.pushColumn();
                        return State1.INSTANCE;
                    } else {
                        parserEventHandler.pushSymbol(symbol);
                        return State7.INSTANCE;
                    }
                } else {
                    parserEventHandler.pushSymbol(CR);
                    if (parserEventHandler.isCommaSeparator()) {
                        parserEventHandler.pushColumn();
                        return State1.INSTANCE;
                    } else {
                        parserEventHandler.pushSymbol(symbol);
                        return State7.INSTANCE;
                    }
                }
            case SEMICOLON:
                if (parserEventHandler.isCrSeparator()) {
                    parserEventHandler.pushRow();
                    if (parserEventHandler.isSemicolonSeparator()) {
                        parserEventHandler.pushColumn();
                        return State1.INSTANCE;
                    } else {
                        parserEventHandler.pushSymbol(symbol);
                        return State7.INSTANCE;
                    }
                } else {
                    parserEventHandler.pushSymbol(CR);
                    if (parserEventHandler.isSemicolonSeparator()) {
                        parserEventHandler.pushColumn();
                        return State1.INSTANCE;
                    } else {
                        parserEventHandler.pushSymbol(symbol);
                        return State7.INSTANCE;
                    }
                }
            case CR:
                if (parserEventHandler.isCrSeparator()) {
                    parserEventHandler.pushRow();
                    return State3.INSTANCE;
                } else {
                    parserEventHandler.pushSymbol(CR);
                    return State4.INSTANCE;
                }
            case LF:
                parserEventHandler.pushRow();
                return State2.INSTANCE;
            case QUOT:
                parserEventHandler.pushRow();
                return State5.INSTANCE;
            default:
                parserEventHandler.pushRow();
                parserEventHandler.pushSymbol(symbol);
                return State7.INSTANCE;
        }
    }

}
