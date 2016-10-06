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
        if (_overflow) {
            return new String(_buffer, _index, _buffer.length - _index) + new String(_buffer, 0, _index);
        } else {
            return new String(_buffer, 0, _index);
        }
    }

}
