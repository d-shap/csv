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
        CharStack charStack1 = new CharStack(0);
        Assert.assertEquals("", charStack1.toString());

        CharStack charStack2 = new CharStack(3);
        Assert.assertEquals("", charStack2.toString());

        CharStack charStack3 = new CharStack(10);
        Assert.assertEquals("", charStack3.toString());
    }

    /**
     * {@link CharStack} class test.
     */
    @Test
    public void appendTest() {
        CharStack charStack1 = new CharStack(0);
        charStack1.append('a');
        Assert.assertEquals("", charStack1.toString());
        charStack1.append('b');
        Assert.assertEquals("", charStack1.toString());
        charStack1.append('1');
        Assert.assertEquals("", charStack1.toString());
        charStack1.append('2');
        Assert.assertEquals("", charStack1.toString());
        charStack1.append('a');
        Assert.assertEquals("", charStack1.toString());
        charStack1.append('b');
        Assert.assertEquals("", charStack1.toString());

        CharStack charStack2 = new CharStack(3);
        charStack2.append('a');
        Assert.assertEquals("a", charStack2.toString());
        charStack2.append('b');
        Assert.assertEquals("ab", charStack2.toString());
        charStack2.append('c');
        Assert.assertEquals("abc", charStack2.toString());
        charStack2.append('1');
        Assert.assertEquals("bc1", charStack2.toString());
        charStack2.append('2');
        Assert.assertEquals("c12", charStack2.toString());
        charStack2.append('3');
        Assert.assertEquals("123", charStack2.toString());
        charStack2.append('a');
        Assert.assertEquals("23a", charStack2.toString());
        charStack2.append('b');
        Assert.assertEquals("3ab", charStack2.toString());

        CharStack charStack3 = new CharStack(10);
        charStack3.append('a');
        Assert.assertEquals("a", charStack3.toString());
        charStack3.append('b');
        Assert.assertEquals("ab", charStack3.toString());
        charStack3.append('c');
        Assert.assertEquals("abc", charStack3.toString());
        charStack3.append('d');
        Assert.assertEquals("abcd", charStack3.toString());
        charStack3.append('e');
        Assert.assertEquals("abcde", charStack3.toString());
        charStack3.append('f');
        Assert.assertEquals("abcdef", charStack3.toString());
        charStack3.append('g');
        Assert.assertEquals("abcdefg", charStack3.toString());
        charStack3.append('h');
        Assert.assertEquals("abcdefgh", charStack3.toString());
        charStack3.append('i');
        Assert.assertEquals("abcdefghi", charStack3.toString());
        charStack3.append('j');
        Assert.assertEquals("abcdefghij", charStack3.toString());
        charStack3.append('1');
        Assert.assertEquals("bcdefghij1", charStack3.toString());
        charStack3.append('2');
        Assert.assertEquals("cdefghij12", charStack3.toString());
        charStack3.append('3');
        Assert.assertEquals("defghij123", charStack3.toString());
        charStack3.append('4');
        Assert.assertEquals("efghij1234", charStack3.toString());
        charStack3.append('5');
        Assert.assertEquals("fghij12345", charStack3.toString());
        charStack3.append('6');
        Assert.assertEquals("ghij123456", charStack3.toString());
        charStack3.append('7');
        Assert.assertEquals("hij1234567", charStack3.toString());
        charStack3.append('8');
        Assert.assertEquals("ij12345678", charStack3.toString());
        charStack3.append('9');
        Assert.assertEquals("j123456789", charStack3.toString());
        charStack3.append('0');
        Assert.assertEquals("1234567890", charStack3.toString());
        charStack3.append('a');
        Assert.assertEquals("234567890a", charStack3.toString());
        charStack3.append('b');
        Assert.assertEquals("34567890ab", charStack3.toString());
    }

    /**
     * {@link CharStack} class test.
     */
    @Test
    public void replaceCrLfTest() {
        CharStack charStack = new CharStack(10);
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
