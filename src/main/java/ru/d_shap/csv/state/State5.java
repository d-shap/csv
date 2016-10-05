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
    public AbstractState processInput(final char ch, final Result result) {
        switch (ch) {
            case END_OF_INPUT:
                throw new CsvParseException("Wrong symbol obtained: " + ch);
            case COMMA:
                result.pushChar(ch);
                return State5.INSTANCE;
            case SEMICOLON:
                result.pushChar(ch);
                return State5.INSTANCE;
            case CR:
                result.pushChar(ch);
                return State5.INSTANCE;
            case LF:
                result.pushChar(ch);
                return State5.INSTANCE;
            case QUOT:
                return State6.INSTANCE;
            default:
                result.pushChar(ch);
                return State5.INSTANCE;
        }
    }

}
