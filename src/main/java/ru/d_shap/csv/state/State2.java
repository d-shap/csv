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
final class State2 extends AbstractState {

    /*
     * State after row separator.
     */

    static final State2 INSTANCE = new State2();

    private State2() {
        super();
    }

    @Override
    public AbstractState processInput(final int symbol, final Result result) {
        switch (symbol) {
            case END_OF_INPUT:
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
