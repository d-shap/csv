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

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link CharStack}.
 *
 * @author Dmitry Shapovalov
 */
public final class CharStackTest {

    /**
     * Test class constructor.
     */
    public CharStackTest() {
        super();
    }

    /**
     * {@link CharStack} class test.
     */
    @Test
    public void emptyCharStackTest() {
        CharStack charStack = new CharStack();
        Assert.assertEquals("", charStack.toString());
    }

    /**
     * {@link CharStack} class test.
     */
    @Test
    public void appendTest() {
        CharStack charStack = new CharStack();

        charStack.append('a');
        Assert.assertEquals("a", charStack.toString());

        charStack.append('b');
        Assert.assertEquals("ab", charStack.toString());

        charStack.append('c');
        Assert.assertEquals("abc", charStack.toString());

        charStack.append('d');
        Assert.assertEquals("abcd", charStack.toString());

        charStack.append('e');
        Assert.assertEquals("abcde", charStack.toString());

        charStack.append('f');
        Assert.assertEquals("abcdef", charStack.toString());

        charStack.append('g');
        Assert.assertEquals("abcdefg", charStack.toString());

        charStack.append('h');
        Assert.assertEquals("abcdefgh", charStack.toString());

        charStack.append('i');
        Assert.assertEquals("abcdefghi", charStack.toString());

        charStack.append('j');
        Assert.assertEquals("abcdefghij", charStack.toString());

        charStack.append('1');
        Assert.assertEquals("bcdefghij1", charStack.toString());

        charStack.append('2');
        Assert.assertEquals("cdefghij12", charStack.toString());

        charStack.append('3');
        Assert.assertEquals("defghij123", charStack.toString());

        charStack.append('4');
        Assert.assertEquals("efghij1234", charStack.toString());

        charStack.append('5');
        Assert.assertEquals("fghij12345", charStack.toString());

        charStack.append('6');
        Assert.assertEquals("ghij123456", charStack.toString());

        charStack.append('7');
        Assert.assertEquals("hij1234567", charStack.toString());

        charStack.append('8');
        Assert.assertEquals("ij12345678", charStack.toString());

        charStack.append('9');
        Assert.assertEquals("j123456789", charStack.toString());

        charStack.append('0');
        Assert.assertEquals("1234567890", charStack.toString());

        charStack.append('a');
        Assert.assertEquals("234567890a", charStack.toString());

        charStack.append('b');
        Assert.assertEquals("34567890ab", charStack.toString());
    }

    /**
     * {@link CharStack} class test.
     */
    @Test
    public void replaceCrLfTest() {
        CharStack charStack = new CharStack();
        charStack.append('a');
        charStack.append('\r');
        charStack.append('b');
        charStack.append('\n');
        charStack.append('c');
        charStack.append('\r');
        charStack.append('\n');
        charStack.append('d');
        Assert.assertEquals("a\\rb\\nc\\r\\nd", charStack.toString());
    }

}
