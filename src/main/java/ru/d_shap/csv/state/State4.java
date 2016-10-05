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
    public AbstractState processInput(final char ch, final Result result) {
        switch (ch) {
            case END_OF_INPUT:
                result.pushColumn();
                result.pushRow();
                return null;
            case COMMA:
                result.pushColumn();
                result.pushRow();
                result.pushColumn();
                return State1.INSTANCE;
            case SEMICOLON:
                result.pushColumn();
                result.pushRow();
                result.pushColumn();
                return State1.INSTANCE;
            case CR:
                result.pushColumn();
                result.pushRow();
                return State3.INSTANCE;
            case LF:
                result.pushColumn();
                result.pushRow();
                return State2.INSTANCE;
            case QUOT:
                result.pushColumn();
                result.pushRow();
                return State5.INSTANCE;
            default:
                result.pushColumn();
                result.pushRow();
                result.pushChar(ch);
                return State7.INSTANCE;
        }
    }

}
