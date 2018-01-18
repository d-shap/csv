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
public abstract class State {

    public static final int END_OF_INPUT = -1;

    public static final int COMMA = ',';

    public static final int SEMICOLON = ';';

    public static final int CR = '\r';

    public static final int LF = '\n';

    public static final int QUOT = '"';

    State() {
        super();
    }

    /**
     * Return init state of CSV parser state machine.
     *
     * @return init state.
     */
    public static State getInitState() {
        return State1.INSTANCE;
    }

    /**
     * Process input symbol and define next parser state.
     *
     * @param symbol             input symbol.
     * @param parserEventHandler event handler to process parser events.
     * @return next parser state.
     */
    public final State processInput(final int symbol, final StateHandler parserEventHandler) {
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

    abstract void processEndOfInput(StateHandler parserEventHandler);

    abstract State processComma(StateHandler parserEventHandler);

    final State processAllowedComma(final StateHandler parserEventHandler) {
        if (parserEventHandler.isCommaSeparator()) {
            parserEventHandler.pushColumn();
            return State2.INSTANCE;
        } else {
            return processPushUnquotedSymbol(COMMA, parserEventHandler);
        }
    }

    final State processDisallowedComma(final StateHandler parserEventHandler) {
        if (parserEventHandler.isCommaSeparator()) {
            parserEventHandler.pushColumn();
            return State2.INSTANCE;
        } else {
            throw new CsvParseException(COMMA, parserEventHandler.getLastSymbols());
        }
    }

    abstract State processSemicolon(StateHandler parserEventHandler);

    final State processAllowedSemicolon(final StateHandler parserEventHandler) {
        if (parserEventHandler.isSemicolonSeparator()) {
            parserEventHandler.pushColumn();
            return State2.INSTANCE;
        } else {
            return processPushUnquotedSymbol(SEMICOLON, parserEventHandler);
        }
    }

    final State processDisallowedSemicolon(final StateHandler parserEventHandler) {
        if (parserEventHandler.isSemicolonSeparator()) {
            parserEventHandler.pushColumn();
            return State2.INSTANCE;
        } else {
            throw new CsvParseException(SEMICOLON, parserEventHandler.getLastSymbols());
        }
    }

    abstract State processCr(StateHandler parserEventHandler);

    final void processPushCr(final StateHandler parserEventHandler) {
        parserEventHandler.pushSymbol(CR);
    }

    abstract State processLf(StateHandler parserEventHandler);

    final void processPushColumnAndRow(final StateHandler parserEventHandler) {
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
    }

    final void processPushRow(final StateHandler parserEventHandler) {
        parserEventHandler.pushRow();
    }

    abstract State processQuot(StateHandler parserEventHandler);

    final State processPushQuotedSymbol(final int symbol, final StateHandler parserEventHandler) {
        parserEventHandler.pushSymbol(symbol);
        return State6.INSTANCE;
    }

    abstract State processSymbol(int symbol, StateHandler parserEventHandler);

    final State processPushUnquotedSymbol(final int symbol, final StateHandler parserEventHandler) {
        parserEventHandler.pushSymbol(symbol);
        return State8.INSTANCE;
    }

}
