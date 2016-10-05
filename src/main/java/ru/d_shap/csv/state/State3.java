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
    public AbstractState processInput(final char ch, final Result result) {
        switch (ch) {
            case END_OF_INPUT:
                result.pushRow();
                return null;
            case COMMA:
                result.pushRow();
                result.pushColumn();
                return State1.INSTANCE;
            case SEMICOLON:
                result.pushRow();
                result.pushColumn();
                return State1.INSTANCE;
            case CR:
                result.pushRow();
                return State3.INSTANCE;
            case LF:
                result.pushRow();
                return State2.INSTANCE;
            case QUOT:
                result.pushRow();
                return State5.INSTANCE;
            default:
                result.pushRow();
                result.pushChar(ch);
                return State7.INSTANCE;
        }
    }

}
