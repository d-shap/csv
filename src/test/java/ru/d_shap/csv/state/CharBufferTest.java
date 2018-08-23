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

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.assertions.Raw;
import ru.d_shap.csv.CsvTest;

/**
 * Tests for {@link CharBuffer}.
 *
 * @author Dmitry Shapovalov
 */
public final class CharBufferTest extends CsvTest {

    /**
     * Test class constructor.
     */
    public CharBufferTest() {
        super();
    }

    /**
     * {@link CharBuffer} class test.
     */
    @Test
    public void emptyCharBufferTest() {
        CharBuffer charBuffer1 = new CharBuffer(-1, false);
        Assertions.assertThat(charBuffer1.getActualLength()).isEqualTo(0);
        Assertions.assertThat(charBuffer1).hasToString("");

        CharBuffer charBuffer2 = new CharBuffer(-1, true);
        Assertions.assertThat(charBuffer2.getActualLength()).isEqualTo(0);
        Assertions.assertThat(charBuffer2).hasToString("");

        CharBuffer charBuffer3 = new CharBuffer(0, false);
        Assertions.assertThat(charBuffer3.getActualLength()).isEqualTo(0);
        Assertions.assertThat(charBuffer3).hasToString("");

        CharBuffer charBuffer4 = new CharBuffer(0, true);
        Assertions.assertThat(charBuffer4.getActualLength()).isEqualTo(0);
        Assertions.assertThat(charBuffer4).hasToString("");

        CharBuffer charBuffer5 = new CharBuffer(3, false);
        Assertions.assertThat(charBuffer5.getActualLength()).isEqualTo(0);
        Assertions.assertThat(charBuffer5).hasToString("");

        CharBuffer charBuffer6 = new CharBuffer(3, true);
        Assertions.assertThat(charBuffer6.getActualLength()).isEqualTo(0);
        Assertions.assertThat(charBuffer6).hasToString("");
    }

    /**
     * {@link CharBuffer} class test.
     */
    @Test
    public void canAppendTest() {
        CharBuffer charBuffer1 = new CharBuffer(-1, false);
        Assertions.assertThat(charBuffer1.canAppend()).isTrue();
        charBuffer1.append('a');
        charBuffer1.append('b');
        charBuffer1.append('c');
        Assertions.assertThat(charBuffer1.canAppend()).isTrue();
        charBuffer1.append('d');
        Assertions.assertThat(charBuffer1.canAppend()).isTrue();

        CharBuffer charBuffer2 = new CharBuffer(-1, true);
        Assertions.assertThat(charBuffer2.canAppend()).isTrue();
        charBuffer2.append('a');
        charBuffer2.append('b');
        charBuffer2.append('c');
        Assertions.assertThat(charBuffer2.canAppend()).isTrue();
        charBuffer2.append('d');
        Assertions.assertThat(charBuffer2.canAppend()).isTrue();

        CharBuffer charBuffer3 = new CharBuffer(0, false);
        Assertions.assertThat(charBuffer3.canAppend()).isTrue();
        charBuffer3.append('a');
        charBuffer3.append('b');
        charBuffer3.append('c');
        Assertions.assertThat(charBuffer3.canAppend()).isTrue();
        charBuffer3.append('d');
        Assertions.assertThat(charBuffer3.canAppend()).isTrue();

        CharBuffer charBuffer4 = new CharBuffer(0, true);
        Assertions.assertThat(charBuffer4.canAppend()).isFalse();
        charBuffer4.append('a');
        charBuffer4.append('b');
        charBuffer4.append('c');
        Assertions.assertThat(charBuffer4.canAppend()).isFalse();
        charBuffer4.append('d');
        Assertions.assertThat(charBuffer4.canAppend()).isFalse();

        CharBuffer charBuffer5 = new CharBuffer(3, false);
        Assertions.assertThat(charBuffer5.canAppend()).isTrue();
        charBuffer5.append('a');
        charBuffer5.append('b');
        charBuffer5.append('c');
        Assertions.assertThat(charBuffer5.canAppend()).isTrue();
        charBuffer5.append('d');
        Assertions.assertThat(charBuffer5.canAppend()).isTrue();

        CharBuffer charBuffer6 = new CharBuffer(3, true);
        Assertions.assertThat(charBuffer6.canAppend()).isTrue();
        charBuffer6.append('a');
        charBuffer6.append('b');
        charBuffer6.append('c');
        Assertions.assertThat(charBuffer6.canAppend()).isFalse();
        charBuffer6.append('d');
        Assertions.assertThat(charBuffer6.canAppend()).isFalse();
    }

    /**
     * {@link CharBuffer} class test.
     */
    @Test
    public void appendTest() {
        CharBuffer charBuffer1 = new CharBuffer(-1, false);
        charBuffer1.append('a');
        Assertions.assertThat(charBuffer1.getActualLength()).isEqualTo(1);
        Assertions.assertThat(charBuffer1).hasToString("a");
        charBuffer1.append('b');
        Assertions.assertThat(charBuffer1.getActualLength()).isEqualTo(2);
        Assertions.assertThat(charBuffer1).hasToString("ab");
        charBuffer1.append('c');
        Assertions.assertThat(charBuffer1.getActualLength()).isEqualTo(3);
        Assertions.assertThat(charBuffer1).hasToString("abc");
        charBuffer1.append('d');
        Assertions.assertThat(charBuffer1.getActualLength()).isEqualTo(4);
        Assertions.assertThat(charBuffer1).hasToString("abcd");

        CharBuffer charBuffer2 = new CharBuffer(-1, true);
        charBuffer2.append('a');
        Assertions.assertThat(charBuffer2.getActualLength()).isEqualTo(1);
        Assertions.assertThat(charBuffer2).hasToString("a");
        charBuffer2.append('b');
        Assertions.assertThat(charBuffer2.getActualLength()).isEqualTo(2);
        Assertions.assertThat(charBuffer2).hasToString("ab");
        charBuffer2.append('c');
        Assertions.assertThat(charBuffer2.getActualLength()).isEqualTo(3);
        Assertions.assertThat(charBuffer2).hasToString("abc");
        charBuffer2.append('d');
        Assertions.assertThat(charBuffer2.getActualLength()).isEqualTo(4);
        Assertions.assertThat(charBuffer2).hasToString("abcd");

        CharBuffer charBuffer3 = new CharBuffer(0, false);
        charBuffer3.append('a');
        Assertions.assertThat(charBuffer3.getActualLength()).isEqualTo(1);
        Assertions.assertThat(charBuffer3).hasToString("");
        charBuffer3.append('b');
        Assertions.assertThat(charBuffer3.getActualLength()).isEqualTo(2);
        Assertions.assertThat(charBuffer3).hasToString("");
        charBuffer3.append('c');
        Assertions.assertThat(charBuffer3.getActualLength()).isEqualTo(3);
        Assertions.assertThat(charBuffer3).hasToString("");
        charBuffer3.append('d');
        Assertions.assertThat(charBuffer3.getActualLength()).isEqualTo(4);
        Assertions.assertThat(charBuffer3).hasToString("");

        CharBuffer charBuffer4 = new CharBuffer(0, true);
        charBuffer4.append('a');
        Assertions.assertThat(charBuffer4.getActualLength()).isEqualTo(1);
        Assertions.assertThat(charBuffer4).hasToString("");
        charBuffer4.append('b');
        Assertions.assertThat(charBuffer4.getActualLength()).isEqualTo(2);
        Assertions.assertThat(charBuffer4).hasToString("");
        charBuffer4.append('c');
        Assertions.assertThat(charBuffer4.getActualLength()).isEqualTo(3);
        Assertions.assertThat(charBuffer4).hasToString("");
        charBuffer4.append('d');
        Assertions.assertThat(charBuffer4.getActualLength()).isEqualTo(4);
        Assertions.assertThat(charBuffer4).hasToString("");

        CharBuffer charBuffer5 = new CharBuffer(3, false);
        charBuffer5.append('a');
        Assertions.assertThat(charBuffer5.getActualLength()).isEqualTo(1);
        Assertions.assertThat(charBuffer5).hasToString("a");
        charBuffer5.append('b');
        Assertions.assertThat(charBuffer5.getActualLength()).isEqualTo(2);
        Assertions.assertThat(charBuffer5).hasToString("ab");
        charBuffer5.append('c');
        Assertions.assertThat(charBuffer5.getActualLength()).isEqualTo(3);
        Assertions.assertThat(charBuffer5).hasToString("abc");
        charBuffer5.append('d');
        Assertions.assertThat(charBuffer5.getActualLength()).isEqualTo(4);
        Assertions.assertThat(charBuffer5).hasToString("abc");

        CharBuffer charBuffer6 = new CharBuffer(3, true);
        charBuffer6.append('a');
        Assertions.assertThat(charBuffer6.getActualLength()).isEqualTo(1);
        Assertions.assertThat(charBuffer6).hasToString("a");
        charBuffer6.append('b');
        Assertions.assertThat(charBuffer6.getActualLength()).isEqualTo(2);
        Assertions.assertThat(charBuffer6).hasToString("ab");
        charBuffer6.append('c');
        Assertions.assertThat(charBuffer6.getActualLength()).isEqualTo(3);
        Assertions.assertThat(charBuffer6).hasToString("abc");
        charBuffer6.append('d');
        Assertions.assertThat(charBuffer6.getActualLength()).isEqualTo(4);
        Assertions.assertThat(charBuffer6).hasToString("abc");
    }

    /**
     * {@link CharBuffer} class test.
     */
    @Test
    public void appendWithExtensionTest() {
        CharBuffer charBuffer1 = new CharBuffer(-1, false);
        charBuffer1.append('1');
        charBuffer1.append('2');
        charBuffer1.append('3');
        charBuffer1.append('4');
        charBuffer1.append('5');
        charBuffer1.append('6');
        charBuffer1.append('7');
        charBuffer1.append('8');
        charBuffer1.append('9');
        charBuffer1.append('0');
        charBuffer1.append('1');
        charBuffer1.append('2');
        charBuffer1.append('3');
        charBuffer1.append('4');
        charBuffer1.append('5');
        charBuffer1.append('6');
        charBuffer1.append('7');
        charBuffer1.append('8');
        charBuffer1.append('9');
        charBuffer1.append('0');
        charBuffer1.append('1');
        charBuffer1.append('2');
        charBuffer1.append('3');
        charBuffer1.append('4');
        charBuffer1.append('5');
        Assertions.assertThat(charBuffer1.getActualLength()).isEqualTo(25);
        Assertions.assertThat(charBuffer1).hasToString("1234567890123456789012345");

        CharBuffer charBuffer2 = new CharBuffer(-1, true);
        charBuffer2.append('1');
        charBuffer2.append('2');
        charBuffer2.append('3');
        charBuffer2.append('4');
        charBuffer2.append('5');
        charBuffer2.append('6');
        charBuffer2.append('7');
        charBuffer2.append('8');
        charBuffer2.append('9');
        charBuffer2.append('0');
        charBuffer2.append('1');
        charBuffer2.append('2');
        charBuffer2.append('3');
        charBuffer2.append('4');
        charBuffer2.append('5');
        charBuffer2.append('6');
        charBuffer2.append('7');
        charBuffer2.append('8');
        charBuffer2.append('9');
        charBuffer2.append('0');
        charBuffer2.append('1');
        charBuffer2.append('2');
        charBuffer2.append('3');
        charBuffer2.append('4');
        charBuffer2.append('5');
        Assertions.assertThat(charBuffer2.getActualLength()).isEqualTo(25);
        Assertions.assertThat(charBuffer2).hasToString("1234567890123456789012345");

        CharBuffer charBuffer3 = new CharBuffer(0, false);
        charBuffer3.append('1');
        charBuffer3.append('2');
        charBuffer3.append('3');
        charBuffer3.append('4');
        charBuffer3.append('5');
        charBuffer3.append('6');
        charBuffer3.append('7');
        charBuffer3.append('8');
        charBuffer3.append('9');
        charBuffer3.append('0');
        charBuffer3.append('1');
        charBuffer3.append('2');
        charBuffer3.append('3');
        charBuffer3.append('4');
        charBuffer3.append('5');
        charBuffer3.append('6');
        charBuffer3.append('7');
        charBuffer3.append('8');
        charBuffer3.append('9');
        charBuffer3.append('0');
        charBuffer3.append('1');
        charBuffer3.append('2');
        charBuffer3.append('3');
        charBuffer3.append('4');
        charBuffer3.append('5');
        Assertions.assertThat(charBuffer3.getActualLength()).isEqualTo(25);
        Assertions.assertThat(charBuffer3).hasToString("");

        CharBuffer charBuffer4 = new CharBuffer(0, true);
        charBuffer4.append('1');
        charBuffer4.append('2');
        charBuffer4.append('3');
        charBuffer4.append('4');
        charBuffer4.append('5');
        charBuffer4.append('6');
        charBuffer4.append('7');
        charBuffer4.append('8');
        charBuffer4.append('9');
        charBuffer4.append('0');
        charBuffer4.append('1');
        charBuffer4.append('2');
        charBuffer4.append('3');
        charBuffer4.append('4');
        charBuffer4.append('5');
        charBuffer4.append('6');
        charBuffer4.append('7');
        charBuffer4.append('8');
        charBuffer4.append('9');
        charBuffer4.append('0');
        charBuffer4.append('1');
        charBuffer4.append('2');
        charBuffer4.append('3');
        charBuffer4.append('4');
        charBuffer4.append('5');
        Assertions.assertThat(charBuffer4.getActualLength()).isEqualTo(25);
        Assertions.assertThat(charBuffer4).hasToString("");

        CharBuffer charBuffer5 = new CharBuffer(3, false);
        charBuffer5.append('1');
        charBuffer5.append('2');
        charBuffer5.append('3');
        charBuffer5.append('4');
        charBuffer5.append('5');
        charBuffer5.append('6');
        charBuffer5.append('7');
        charBuffer5.append('8');
        charBuffer5.append('9');
        charBuffer5.append('0');
        charBuffer5.append('1');
        charBuffer5.append('2');
        charBuffer5.append('3');
        charBuffer5.append('4');
        charBuffer5.append('5');
        charBuffer5.append('6');
        charBuffer5.append('7');
        charBuffer5.append('8');
        charBuffer5.append('9');
        charBuffer5.append('0');
        charBuffer5.append('1');
        charBuffer5.append('2');
        charBuffer5.append('3');
        charBuffer5.append('4');
        charBuffer5.append('5');
        Assertions.assertThat(charBuffer5.getActualLength()).isEqualTo(25);
        Assertions.assertThat(charBuffer5).hasToString("123");

        CharBuffer charBuffer6 = new CharBuffer(3, true);
        charBuffer6.append('1');
        charBuffer6.append('2');
        charBuffer6.append('3');
        charBuffer6.append('4');
        charBuffer6.append('5');
        charBuffer6.append('6');
        charBuffer6.append('7');
        charBuffer6.append('8');
        charBuffer6.append('9');
        charBuffer6.append('0');
        charBuffer6.append('1');
        charBuffer6.append('2');
        charBuffer6.append('3');
        charBuffer6.append('4');
        charBuffer6.append('5');
        charBuffer6.append('6');
        charBuffer6.append('7');
        charBuffer6.append('8');
        charBuffer6.append('9');
        charBuffer6.append('0');
        charBuffer6.append('1');
        charBuffer6.append('2');
        charBuffer6.append('3');
        charBuffer6.append('4');
        charBuffer6.append('5');
        Assertions.assertThat(charBuffer6.getActualLength()).isEqualTo(25);
        Assertions.assertThat(charBuffer6).hasToString("123");
    }

    /**
     * {@link CharBuffer} class test.
     */
    @Test
    public void clearTest() {
        CharBuffer charBuffer1 = new CharBuffer(-1, false);
        charBuffer1.append('a');
        charBuffer1.append('b');
        charBuffer1.append('c');
        charBuffer1.append('d');
        Assertions.assertThat(charBuffer1.getActualLength()).isEqualTo(4);
        Assertions.assertThat(charBuffer1).hasToString("abcd");
        charBuffer1.clear();
        Assertions.assertThat(charBuffer1.getActualLength()).isEqualTo(0);
        Assertions.assertThat(charBuffer1).hasToString("");

        CharBuffer charBuffer2 = new CharBuffer(-1, true);
        charBuffer2.append('a');
        charBuffer2.append('b');
        charBuffer2.append('c');
        charBuffer2.append('d');
        Assertions.assertThat(charBuffer2.getActualLength()).isEqualTo(4);
        Assertions.assertThat(charBuffer2).hasToString("abcd");
        charBuffer2.clear();
        Assertions.assertThat(charBuffer2.getActualLength()).isEqualTo(0);
        Assertions.assertThat(charBuffer2).hasToString("");

        CharBuffer charBuffer3 = new CharBuffer(0, false);
        charBuffer3.append('a');
        charBuffer3.append('b');
        charBuffer3.append('c');
        charBuffer3.append('d');
        Assertions.assertThat(charBuffer3.getActualLength()).isEqualTo(4);
        Assertions.assertThat(charBuffer3).hasToString("");
        charBuffer3.clear();
        Assertions.assertThat(charBuffer3.getActualLength()).isEqualTo(0);
        Assertions.assertThat(charBuffer3).hasToString("");

        CharBuffer charBuffer4 = new CharBuffer(0, true);
        charBuffer4.append('a');
        charBuffer4.append('b');
        charBuffer4.append('c');
        charBuffer4.append('d');
        Assertions.assertThat(charBuffer4.getActualLength()).isEqualTo(4);
        Assertions.assertThat(charBuffer4).hasToString("");
        charBuffer4.clear();
        Assertions.assertThat(charBuffer4.getActualLength()).isEqualTo(0);
        Assertions.assertThat(charBuffer4).hasToString("");

        CharBuffer charBuffer5 = new CharBuffer(3, false);
        charBuffer5.append('a');
        charBuffer5.append('b');
        charBuffer5.append('c');
        charBuffer5.append('d');
        Assertions.assertThat(charBuffer5.getActualLength()).isEqualTo(4);
        Assertions.assertThat(charBuffer5).hasToString("abc");
        charBuffer5.clear();
        Assertions.assertThat(charBuffer5.getActualLength()).isEqualTo(0);
        Assertions.assertThat(charBuffer5).hasToString("");

        CharBuffer charBuffer6 = new CharBuffer(3, true);
        charBuffer6.append('a');
        charBuffer6.append('b');
        charBuffer6.append('c');
        charBuffer6.append('d');
        Assertions.assertThat(charBuffer6.getActualLength()).isEqualTo(4);
        Assertions.assertThat(charBuffer6).hasToString("abc");
        charBuffer6.clear();
        Assertions.assertThat(charBuffer6.getActualLength()).isEqualTo(0);
        Assertions.assertThat(charBuffer6).hasToString("");
    }

    /**
     * {@link CharBuffer} class test.
     */
    @Test
    public void bufferLengthTest() {
        CharBuffer charBuffer1 = new CharBuffer(-1, false);
        Assertions.assertThat(charBuffer1, "_buffer", Raw.charArrayAssertion()).hasLength(20);
        charBuffer1.append('1');
        charBuffer1.append('2');
        charBuffer1.append('3');
        charBuffer1.append('4');
        charBuffer1.append('5');
        charBuffer1.append('6');
        charBuffer1.append('7');
        charBuffer1.append('8');
        charBuffer1.append('9');
        charBuffer1.append('0');
        charBuffer1.append('1');
        charBuffer1.append('2');
        charBuffer1.append('3');
        charBuffer1.append('4');
        charBuffer1.append('5');
        charBuffer1.append('6');
        charBuffer1.append('7');
        charBuffer1.append('8');
        charBuffer1.append('9');
        Assertions.assertThat(charBuffer1, "_buffer", Raw.charArrayAssertion()).hasLength(20);
        charBuffer1.append('0');
        Assertions.assertThat(charBuffer1, "_buffer", Raw.charArrayAssertion()).hasLength(20);
        charBuffer1.append('1');
        Assertions.assertThat(charBuffer1, "_buffer", Raw.charArrayAssertion()).hasLength(41);

        CharBuffer charBuffer2 = new CharBuffer(10, false);
        Assertions.assertThat(charBuffer2, "_buffer", Raw.charArrayAssertion()).hasLength(10);
        charBuffer2.append('1');
        charBuffer2.append('2');
        charBuffer2.append('3');
        charBuffer2.append('4');
        charBuffer2.append('5');
        charBuffer2.append('6');
        charBuffer2.append('7');
        charBuffer2.append('8');
        charBuffer2.append('9');
        Assertions.assertThat(charBuffer2, "_buffer", Raw.charArrayAssertion()).hasLength(10);
        charBuffer2.append('0');
        Assertions.assertThat(charBuffer2, "_buffer", Raw.charArrayAssertion()).hasLength(10);
        charBuffer2.append('1');
        Assertions.assertThat(charBuffer2, "_buffer", Raw.charArrayAssertion()).hasLength(10);

        CharBuffer charBuffer3 = new CharBuffer(0, false);
        Assertions.assertThat(charBuffer3, "_buffer", Raw.charArrayAssertion()).hasLength(0);
        charBuffer3.append('1');
        Assertions.assertThat(charBuffer3, "_buffer", Raw.charArrayAssertion()).hasLength(0);
        charBuffer3.append('2');
        Assertions.assertThat(charBuffer3, "_buffer", Raw.charArrayAssertion()).hasLength(0);
        charBuffer3.append('3');
        Assertions.assertThat(charBuffer3, "_buffer", Raw.charArrayAssertion()).hasLength(0);
    }

}
