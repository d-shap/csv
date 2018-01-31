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
 * Last processed characters for correct exception messages.
 *
 * @author Dmitry Shapovalov
 */
final class CharStack {

    private final char[] _buffer;

    private final int _stringBuilderSize;

    private int _index;

    private boolean _overflow;

    CharStack(final int size) {
        super();
        _buffer = new char[size];
        _stringBuilderSize = size * 2;
        _index = 0;
        _overflow = false;
    }

    void append(final char ch) {
        if (_buffer.length > 0) {
            _buffer[_index] = ch;
            _index++;
            if (_index >= _buffer.length) {
                _index = 0;
                _overflow = true;
            }
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
        StringBuilder builder = new StringBuilder(_stringBuilderSize);
        int lng = str.length();
        for (int i = 0; i < lng; i++) {
            char ch = str.charAt(i);
            switch (ch) {
                case SpecialCharacter.CR:
                    builder.append("\\r");
                    break;
                case SpecialCharacter.LF:
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
