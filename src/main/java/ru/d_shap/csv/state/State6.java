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
    public AbstractState processInput(final char ch, final Result result) {
        switch (ch) {
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
                result.pushChar(ch);
                return State5.INSTANCE;
            default:
                throw new CsvParseException("Wrong symbol obtained: " + ch);
        }
    }

}
