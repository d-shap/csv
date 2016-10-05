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
                throw new CsvParseException("Wrong symbol obtained: " + ch);
            default:
                result.pushChar(ch);
                return State7.INSTANCE;
        }
    }

}
