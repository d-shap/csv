// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv.state;

import ru.d_shap.csv.CsvParseException;

/**
 * State of CSV parser state machine.
 *
 * @author Dmitry Shapovalov
 */
final class State6 extends AbstractState {

    /*
     * State after quot in quoted column.
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
                parserEventHandler.pushColumn();
                parserEventHandler.pushRow();
                return null;
            case COMMA:
                parserEventHandler.pushColumn();
                return State1.INSTANCE;
            case SEMICOLON:
                parserEventHandler.pushColumn();
                return State1.INSTANCE;
            case CR:
                return State4.INSTANCE;
            case LF:
                parserEventHandler.pushColumn();
                parserEventHandler.pushRow();
                return State2.INSTANCE;
            case QUOT:
                parserEventHandler.pushSymbol(symbol);
                return State5.INSTANCE;
            default:
                throw new CsvParseException(symbol, parserEventHandler.getLastSymbols());
        }
    }

}
