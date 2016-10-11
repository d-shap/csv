// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv.state;

/**
 * Last processed symbols for correct exception messages.
 *
 * @author Dmitry Shapovalov
 */
final class CharStack {

    private final char[] _buffer;

    private int _index;

    private boolean _overflow;

    CharStack() {
        super();
        _buffer = new char[10];
        _index = 0;
        _overflow = false;
    }

    void append(final char ch) {
        _buffer[_index] = ch;
        _index++;
        if (_index >= _buffer.length) {
            _index = 0;
            _overflow = true;
        }
    }

    @Override
    public String toString() {
        String result;
        if (_overflow) {
            result = new String(_buffer, _index, _buffer.length - _index) + new String(_buffer, 0, _index);
        } else {
            result = new String(_buffer, 0, _index);
        }
        return replaceCrLf(result);
    }

    private String replaceCrLf(final String str) {
        StringBuilder builder = new StringBuilder(str.length() * 2);
        int lng = str.length();
        for (int i = 0; i < lng; i++) {
            char ch = str.charAt(i);
            switch (ch) {
                case AbstractState.CR:
                    builder.append("\\r");
                    break;
                case AbstractState.LF:
                    builder.append("\\n");
                    break;
                default:
                    builder.append(ch);
                    break;
            }
        }
        return builder.toString();
    }

}
