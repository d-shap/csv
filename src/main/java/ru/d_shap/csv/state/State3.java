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
                parserEventHandler.pushRow();
                return null;
            case COMMA:
                parserEventHandler.pushRow();
                parserEventHandler.pushColumn();
                return State1.INSTANCE;
            case SEMICOLON:
                parserEventHandler.pushRow();
                parserEventHandler.pushColumn();
                return State1.INSTANCE;
            case CR:
                parserEventHandler.pushRow();
                return State3.INSTANCE;
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
