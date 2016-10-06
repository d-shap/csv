// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
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
