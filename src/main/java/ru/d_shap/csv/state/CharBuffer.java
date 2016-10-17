// CSV-parser converts source stream to rows and columns and vice versa.
// Copyright (C) 2016 Dmitry Shapovalov.
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
