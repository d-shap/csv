///////////////////////////////////////////////////////////////////////////////////////////////////
// CSV parser converts source stream to rows and columns and vice versa.
// Copyright (C) 2016 Dmitry Shapovalov.
//
// This file is part of CSV parser.
//
// CSV parser is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// CSV parser is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.csv.state;

/**
 * Buffer for characters.
 *
 * @author Dmitry Shapovalov
 */
final class CharBuffer {

    private static final int INITIAL_BUFFER_SIZE = 20;

    private final int _maxLength;

    private final boolean _checkMaxLength;

    private char[] _buffer;

    private int _currentIndex;

    private int _actualLength;

    CharBuffer(final int maxLength, final boolean checkMaxLength) {
        super();
        _maxLength = maxLength;
        if (_maxLength >= 0) {
            _checkMaxLength = checkMaxLength;
            _buffer = new char[_maxLength];
        } else {
            _checkMaxLength = false;
            _buffer = new char[INITIAL_BUFFER_SIZE];
        }
        _currentIndex = 0;
        _actualLength = 0;
    }

    boolean canAppend() {
        return !_checkMaxLength || _currentIndex < _maxLength;
    }

    void append(final char ch) {
        if (_maxLength < 0 && _currentIndex >= _buffer.length) {
            char[] newBuffer = new char[_buffer.length * 2 + 1];
            System.arraycopy(_buffer, 0, newBuffer, 0, _buffer.length);
            _buffer = newBuffer;
        }
        if (_maxLength < 0 || _currentIndex < _maxLength) {
            _buffer[_currentIndex] = ch;
            _currentIndex++;
        }
        _actualLength++;
    }

    int getActualLength() {
        return _actualLength;
    }

    void clear() {
        _currentIndex = 0;
        _actualLength = 0;
    }

    @Override
    public String toString() {
        return new String(_buffer, 0, _currentIndex);
    }

}
