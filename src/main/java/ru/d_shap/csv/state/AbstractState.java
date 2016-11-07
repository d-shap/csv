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
 * Base class for all state classes.
 *
 * @author Dmitry Shapovalov
 */
public abstract class AbstractState {

    public static final int END_OF_INPUT = -1;

    public static final int COMMA = ',';

    public static final int SEMICOLON = ';';

    public static final int CR = '\r';

    public static final int LF = '\n';

    public static final int QUOT = '"';

    AbstractState() {
        super();
    }

    /**
     * Return init state of CSV parser state machine.
     *
     * @return init state.
     */
    public static AbstractState getInitState() {
        return State0.INSTANCE;
    }

    /**
     * Process input symbol and define next parser state.
     *
     * @param symbol             input symbol.
     * @param parserEventHandler event handler to process parser events.
     * @return next parser state.
     */
    public final AbstractState processInput(final int symbol, final ParserEventHandler parserEventHandler) {
        parserEventHandler.addLastSymbol(symbol);
        switch (symbol) {
            case END_OF_INPUT:
                return processEndOfInput(symbol, parserEventHandler);
            case COMMA:
                return processComma(symbol, parserEventHandler);
            case SEMICOLON:
                return processSemicolon(symbol, parserEventHandler);
            case CR:
                return processCr(symbol, parserEventHandler);
            case LF:
                return processLf(symbol, parserEventHandler);
            case QUOT:
                return processQuot(symbol, parserEventHandler);
            default:
                return processDefault(symbol, parserEventHandler);
        }
    }

    abstract AbstractState processEndOfInput(int symbol, ParserEventHandler parserEventHandler);

    abstract AbstractState processComma(int symbol, ParserEventHandler parserEventHandler);

    abstract AbstractState processSemicolon(int symbol, ParserEventHandler parserEventHandler);

    abstract AbstractState processCr(int symbol, ParserEventHandler parserEventHandler);

    abstract AbstractState processLf(int symbol, ParserEventHandler parserEventHandler);

    abstract AbstractState processQuot(int symbol, ParserEventHandler parserEventHandler);

    abstract AbstractState processDefault(int symbol, ParserEventHandler parserEventHandler);

}
