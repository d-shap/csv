// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv.state;

/**
 * Buffer for readed characters.
 *
 * @author Dmitry Shapovalov
 */
final class CharBuffer {

    private char[] _buffer;

    private int _currentIndex;

    CharBuffer() {
        super();
        _buffer = new char[20];
        _currentIndex = 0;
    }

    void append(final char ch) {
        if (_currentIndex >= _buffer.length) {
            char[] newBuffer = new char[_buffer.length * 2];
            System.arraycopy(_buffer, 0, newBuffer, 0, _buffer.length);
            _buffer = newBuffer;
        }
        _buffer[_currentIndex] = ch;
        _currentIndex++;
    }

    int getLength() {
        return _currentIndex;
    }

    void clear() {
        _currentIndex = 0;
    }

    @Override
    public String toString() {
        return new String(_buffer, 0, _currentIndex);
    }

}
