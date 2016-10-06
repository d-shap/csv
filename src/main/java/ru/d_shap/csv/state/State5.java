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
