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
final class State1 extends AbstractState {

    /*
     * State after column separator.
     */

    static final State1 INSTANCE = new State1();

    private State1() {
        super();
    }

    @Override
    public AbstractState processInput(final int symbol, final Result result) {
        switch (symbol) {
            case END_OF_INPUT:
                result.pushColumn();
                result.pushRow();
                return null;
            case COMMA:
                result.pushColumn();
                return State1.INSTANCE;
            case SEMICOLON:
                result.pushColumn();
                return State1.INSTANCE;
            case CR:
                return State4.INSTANCE;
            case LF:
                result.pushColumn();
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
