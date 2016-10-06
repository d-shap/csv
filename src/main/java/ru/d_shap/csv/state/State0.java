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
final class State0 extends AbstractState {

    /*
     * Init state.
     */

    static final AbstractState INSTANCE = new State0();

    private State0() {
        super();
    }

    @Override
    public AbstractState processInput(final int symbol, final Result result) {
        switch (symbol) {
            case END_OF_INPUT:
                result.pushRows();
                return null;
            case COMMA:
                result.pushColumn();
                return State1.INSTANCE;
            case SEMICOLON:
                result.pushColumn();
                return State1.INSTANCE;
            case CR:
                return State3.INSTANCE;
            case LF:
                result.pushRow();
                return State2.INSTANCE;
            case QUOT:
                return State5.INSTANCE;
            default:
                result.pushSymbol(symbol);
                return State7.INSTANCE;
        }
    }

}
