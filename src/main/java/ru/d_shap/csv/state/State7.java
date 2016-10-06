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
final class State7 extends AbstractState {

    /*
     * State to process unquoted column.
     */

    static final State7 INSTANCE = new State7();

    private State7() {
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
                throw new CsvParseException(symbol, parserEventHandler.getLastSymbols());
            default:
                parserEventHandler.pushSymbol(symbol);
                return State7.INSTANCE;
        }
    }

}
