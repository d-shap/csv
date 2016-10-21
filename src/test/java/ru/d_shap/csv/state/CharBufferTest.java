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
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.csv.state;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link CharBuffer}.
 *
 * @author Dmitry Shapovalov
 */
public final class CharBufferTest {

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
        Assert.assertEquals(0, charBuffer1.getActualLength());
        Assert.assertEquals("", charBuffer1.toString());

        CharBuffer charBuffer2 = new CharBuffer(-1, true);
        Assert.assertEquals(0, charBuffer2.getActualLength());
        Assert.assertEquals("", charBuffer2.toString());

        CharBuffer charBuffer3 = new CharBuffer(0, false);
        Assert.assertEquals(0, charBuffer3.getActualLength());
        Assert.assertEquals("", charBuffer3.toString());

        CharBuffer charBuffer4 = new CharBuffer(0, true);
        Assert.assertEquals(0, charBuffer4.getActualLength());
        Assert.assertEquals("", charBuffer4.toString());

        CharBuffer charBuffer5 = new CharBuffer(3, false);
        Assert.assertEquals(0, charBuffer5.getActualLength());
        Assert.assertEquals("", charBuffer5.toString());

        CharBuffer charBuffer6 = new CharBuffer(3, true);
        Assert.assertEquals(0, charBuffer6.getActualLength());
        Assert.assertEquals("", charBuffer6.toString());
    }

    /**
     * {@link CharBuffer} class test.
     */
    @Test
    public void canAppendTest() {
        CharBuffer charBuffer1 = new CharBuffer(-1, false);
        Assert.assertTrue(charBuffer1.canAppend());
        charBuffer1.append('a');
        charBuffer1.append('b');
        charBuffer1.append('c');
        Assert.assertTrue(charBuffer1.canAppend());
        charBuffer1.append('d');
        Assert.assertTrue(charBuffer1.canAppend());

        CharBuffer charBuffer2 = new CharBuffer(-1, true);
        Assert.assertTrue(charBuffer2.canAppend());
        charBuffer2.append('a');
        charBuffer2.append('b');
        charBuffer2.append('c');
        Assert.assertTrue(charBuffer2.canAppend());
        charBuffer2.append('d');
        Assert.assertTrue(charBuffer2.canAppend());

        CharBuffer charBuffer3 = new CharBuffer(0, false);
        Assert.assertTrue(charBuffer3.canAppend());
        charBuffer3.append('a');
        charBuffer3.append('b');
        charBuffer3.append('c');
        Assert.assertTrue(charBuffer3.canAppend());
        charBuffer3.append('d');
        Assert.assertTrue(charBuffer3.canAppend());

        CharBuffer charBuffer4 = new CharBuffer(0, true);
        Assert.assertFalse(charBuffer4.canAppend());

        CharBuffer charBuffer5 = new CharBuffer(3, false);
        Assert.assertTrue(charBuffer5.canAppend());
        charBuffer5.append('a');
        charBuffer5.append('b');
        charBuffer5.append('c');
        Assert.assertTrue(charBuffer5.canAppend());
        charBuffer5.append('d');
        Assert.assertTrue(charBuffer5.canAppend());

        CharBuffer charBuffer6 = new CharBuffer(3, true);
        Assert.assertTrue(charBuffer6.canAppend());
        charBuffer6.append('a');
        charBuffer6.append('b');
        charBuffer6.append('c');
        Assert.assertFalse(charBuffer6.canAppend());
    }

    /**
     * {@link CharBuffer} class test.
     */
    @Test
    public void appendTest() {
        CharBuffer charBuffer1 = new CharBuffer(-1, false);
        charBuffer1.append('a');
        Assert.assertEquals(1, charBuffer1.getActualLength());
        Assert.assertEquals("a", charBuffer1.toString());
        charBuffer1.append('b');
        Assert.assertEquals(2, charBuffer1.getActualLength());
        Assert.assertEquals("ab", charBuffer1.toString());
        charBuffer1.append('c');
        Assert.assertEquals(3, charBuffer1.getActualLength());
        Assert.assertEquals("abc", charBuffer1.toString());
        charBuffer1.append('d');
        Assert.assertEquals(4, charBuffer1.getActualLength());
        Assert.assertEquals("abcd", charBuffer1.toString());

        CharBuffer charBuffer2 = new CharBuffer(-1, true);
        charBuffer2.append('a');
        Assert.assertEquals(1, charBuffer2.getActualLength());
        Assert.assertEquals("a", charBuffer2.toString());
        charBuffer2.append('b');
        Assert.assertEquals(2, charBuffer2.getActualLength());
        Assert.assertEquals("ab", charBuffer2.toString());
        charBuffer2.append('c');
        Assert.assertEquals(3, charBuffer2.getActualLength());
        Assert.assertEquals("abc", charBuffer2.toString());
        charBuffer2.append('d');
        Assert.assertEquals(4, charBuffer2.getActualLength());
        Assert.assertEquals("abcd", charBuffer2.toString());

        CharBuffer charBuffer3 = new CharBuffer(0, false);
        charBuffer3.append('a');
        Assert.assertEquals(1, charBuffer3.getActualLength());
        Assert.assertEquals("", charBuffer3.toString());
        charBuffer3.append('b');
        Assert.assertEquals(2, charBuffer3.getActualLength());
        Assert.assertEquals("", charBuffer3.toString());
        charBuffer3.append('c');
        Assert.assertEquals(3, charBuffer3.getActualLength());
        Assert.assertEquals("", charBuffer3.toString());
        charBuffer3.append('d');
        Assert.assertEquals(4, charBuffer3.getActualLength());
        Assert.assertEquals("", charBuffer3.toString());

        CharBuffer charBuffer4 = new CharBuffer(0, true);
        charBuffer4.append('a');
        Assert.assertEquals(1, charBuffer4.getActualLength());
        Assert.assertEquals("", charBuffer4.toString());
        charBuffer4.append('b');
        Assert.assertEquals(2, charBuffer4.getActualLength());
        Assert.assertEquals("", charBuffer4.toString());
        charBuffer4.append('c');
        Assert.assertEquals(3, charBuffer4.getActualLength());
        Assert.assertEquals("", charBuffer4.toString());
        charBuffer4.append('d');
        Assert.assertEquals(4, charBuffer4.getActualLength());
        Assert.assertEquals("", charBuffer4.toString());

        CharBuffer charBuffer5 = new CharBuffer(3, false);
        charBuffer5.append('a');
        Assert.assertEquals(1, charBuffer5.getActualLength());
        Assert.assertEquals("a", charBuffer5.toString());
        charBuffer5.append('b');
        Assert.assertEquals(2, charBuffer5.getActualLength());
        Assert.assertEquals("ab", charBuffer5.toString());
        charBuffer5.append('c');
        Assert.assertEquals(3, charBuffer5.getActualLength());
        Assert.assertEquals("abc", charBuffer5.toString());
        charBuffer5.append('d');
        Assert.assertEquals(4, charBuffer5.getActualLength());
        Assert.assertEquals("abc", charBuffer5.toString());

        CharBuffer charBuffer6 = new CharBuffer(3, true);
        charBuffer6.append('a');
        Assert.assertEquals(1, charBuffer6.getActualLength());
        Assert.assertEquals("a", charBuffer6.toString());
        charBuffer6.append('b');
        Assert.assertEquals(2, charBuffer6.getActualLength());
        Assert.assertEquals("ab", charBuffer6.toString());
        charBuffer6.append('c');
        Assert.assertEquals(3, charBuffer6.getActualLength());
        Assert.assertEquals("abc", charBuffer6.toString());
        charBuffer6.append('d');
        Assert.assertEquals(4, charBuffer6.getActualLength());
        Assert.assertEquals("abc", charBuffer6.toString());
    }

    /**
     * {@link CharBuffer} class test.
     */
    @Test
    public void charBufferExtensionTest() {
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
        Assert.assertEquals(25, charBuffer1.getActualLength());
        Assert.assertEquals("1234567890123456789012345", charBuffer1.toString());

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
        Assert.assertEquals(25, charBuffer2.getActualLength());
        Assert.assertEquals("1234567890123456789012345", charBuffer2.toString());
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
        Assert.assertEquals(4, charBuffer1.getActualLength());
        Assert.assertEquals("abcd", charBuffer1.toString());
        charBuffer1.clear();
        Assert.assertEquals(0, charBuffer1.getActualLength());
        Assert.assertEquals("", charBuffer1.toString());

        CharBuffer charBuffer2 = new CharBuffer(-1, true);
        charBuffer2.append('a');
        charBuffer2.append('b');
        charBuffer2.append('c');
        charBuffer2.append('d');
        Assert.assertEquals(4, charBuffer2.getActualLength());
        Assert.assertEquals("abcd", charBuffer2.toString());
        charBuffer2.clear();
        Assert.assertEquals(0, charBuffer2.getActualLength());
        Assert.assertEquals("", charBuffer2.toString());

        CharBuffer charBuffer3 = new CharBuffer(0, false);
        charBuffer3.append('a');
        charBuffer3.append('b');
        charBuffer3.append('c');
        charBuffer3.append('d');
        Assert.assertEquals(4, charBuffer3.getActualLength());
        Assert.assertEquals("", charBuffer3.toString());
        charBuffer3.clear();
        Assert.assertEquals(0, charBuffer3.getActualLength());
        Assert.assertEquals("", charBuffer3.toString());

        CharBuffer charBuffer4 = new CharBuffer(0, true);
        charBuffer4.append('a');
        charBuffer4.append('b');
        charBuffer4.append('c');
        charBuffer4.append('d');
        Assert.assertEquals(4, charBuffer4.getActualLength());
        Assert.assertEquals("", charBuffer4.toString());
        charBuffer4.clear();
        Assert.assertEquals(0, charBuffer4.getActualLength());
        Assert.assertEquals("", charBuffer4.toString());

        CharBuffer charBuffer5 = new CharBuffer(3, false);
        charBuffer5.append('a');
        charBuffer5.append('b');
        charBuffer5.append('c');
        charBuffer5.append('d');
        Assert.assertEquals(4, charBuffer5.getActualLength());
        Assert.assertEquals("abc", charBuffer5.toString());
        charBuffer5.clear();
        Assert.assertEquals(0, charBuffer5.getActualLength());
        Assert.assertEquals("", charBuffer5.toString());

        CharBuffer charBuffer6 = new CharBuffer(3, true);
        charBuffer6.append('a');
        charBuffer6.append('b');
        charBuffer6.append('c');
        charBuffer6.append('d');
        Assert.assertEquals(4, charBuffer6.getActualLength());
        Assert.assertEquals("abc", charBuffer6.toString());
        charBuffer6.clear();
        Assert.assertEquals(0, charBuffer6.getActualLength());
        Assert.assertEquals("", charBuffer6.toString());
    }

}
