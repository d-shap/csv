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
        return State1.INSTANCE;
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
                processEndOfInput(parserEventHandler);
                return null;
            case COMMA:
                return processComma(parserEventHandler);
            case SEMICOLON:
                return processSemicolon(parserEventHandler);
            case CR:
                return processCr(parserEventHandler);
            case LF:
                return processLf(parserEventHandler);
            case QUOT:
                return processQuot(parserEventHandler);
            default:
                return processSymbol(symbol, parserEventHandler);
        }
    }

    abstract void processEndOfInput(ParserEventHandler parserEventHandler);

    abstract AbstractState processComma(ParserEventHandler parserEventHandler);

    final AbstractState processAllowedComma(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCommaSeparator()) {
            parserEventHandler.pushColumn();
            return State2.INSTANCE;
        } else {
            return processPushUnquotedSymbol(COMMA, parserEventHandler);
        }
    }

    final AbstractState processDisallowedComma(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isCommaSeparator()) {
            parserEventHandler.pushColumn();
            return State2.INSTANCE;
        } else {
            throw new CsvParseException(COMMA, parserEventHandler.getLastSymbols());
        }
    }

    abstract AbstractState processSemicolon(ParserEventHandler parserEventHandler);

    final AbstractState processAllowedSemicolon(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isSemicolonSeparator()) {
            parserEventHandler.pushColumn();
            return State2.INSTANCE;
        } else {
            return processPushUnquotedSymbol(SEMICOLON, parserEventHandler);
        }
    }

    final AbstractState processDisallowedSemicolon(final ParserEventHandler parserEventHandler) {
        if (parserEventHandler.isSemicolonSeparator()) {
            parserEventHandler.pushColumn();
            return State2.INSTANCE;
        } else {
            throw new CsvParseException(SEMICOLON, parserEventHandler.getLastSymbols());
        }
    }

    abstract AbstractState processCr(ParserEventHandler parserEventHandler);

    final void processPushCr(final ParserEventHandler parserEventHandler) {
        parserEventHandler.pushSymbol(CR);
    }

    abstract AbstractState processLf(ParserEventHandler parserEventHandler);

    final void processPushColumnAndRow(final ParserEventHandler parserEventHandler) {
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
    }

    final void processPushRow(final ParserEventHandler parserEventHandler) {
        parserEventHandler.pushRow();
    }

    abstract AbstractState processQuot(ParserEventHandler parserEventHandler);

    final AbstractState processPushQuotedSymbol(final int symbol, final ParserEventHandler parserEventHandler) {
        parserEventHandler.pushSymbol(symbol);
        return State6.INSTANCE;
    }

    abstract AbstractState processSymbol(int symbol, ParserEventHandler parserEventHandler);

    final AbstractState processPushUnquotedSymbol(final int symbol, final ParserEventHandler parserEventHandler) {
        parserEventHandler.pushSymbol(symbol);
        return State8.INSTANCE;
    }

}
