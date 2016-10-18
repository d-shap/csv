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
        CharBuffer charBuffer = new CharBuffer();
        Assert.assertEquals(0, charBuffer.getLength());
        Assert.assertEquals("", charBuffer.toString());
    }

    /**
     * {@link CharBuffer} class test.
     */
    @Test
    public void appendCharTest() {
        CharBuffer charBuffer = new CharBuffer();

        charBuffer.append('a');
        Assert.assertEquals(1, charBuffer.getLength());
        Assert.assertEquals("a", charBuffer.toString());

        charBuffer.append('b');
        Assert.assertEquals(2, charBuffer.getLength());
        Assert.assertEquals("ab", charBuffer.toString());

        charBuffer.append('c');
        Assert.assertEquals(3, charBuffer.getLength());
        Assert.assertEquals("abc", charBuffer.toString());
    }

    /**
     * {@link CharBuffer} class test.
     */
    @Test
    public void charBufferExtensionTest() {
        CharBuffer charBuffer = new CharBuffer();
        charBuffer.append('1');
        charBuffer.append('2');
        charBuffer.append('3');
        charBuffer.append('4');
        charBuffer.append('5');
        charBuffer.append('6');
        charBuffer.append('7');
        charBuffer.append('8');
        charBuffer.append('9');
        charBuffer.append('0');
        charBuffer.append('1');
        charBuffer.append('2');
        charBuffer.append('3');
        charBuffer.append('4');
        charBuffer.append('5');
        charBuffer.append('6');
        charBuffer.append('7');
        charBuffer.append('8');
        charBuffer.append('9');
        charBuffer.append('0');
        charBuffer.append('1');
        charBuffer.append('2');
        charBuffer.append('3');
        charBuffer.append('4');
        charBuffer.append('5');
        Assert.assertEquals(25, charBuffer.getLength());
        Assert.assertEquals("1234567890123456789012345", charBuffer.toString());
    }

    /**
     * {@link CharBuffer} class test.
     */
    @Test
    public void clearTest() {
        CharBuffer charBuffer = new CharBuffer();
        charBuffer.append('a');
        charBuffer.append('b');
        charBuffer.append('c');
        Assert.assertEquals(3, charBuffer.getLength());
        Assert.assertEquals("abc", charBuffer.toString());

        charBuffer.clear();
        Assert.assertEquals(0, charBuffer.getLength());
        Assert.assertEquals("", charBuffer.toString());
    }

}
