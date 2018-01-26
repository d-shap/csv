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
import ru.d_shap.csv.CsvTest;

/**
 * Tests for {@link CharStack}.
 *
 * @author Dmitry Shapovalov
 */
public final class CharStackTest extends CsvTest {

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
        Assertions.assertThat(charStack1).isToStringEqualTo("");

        CharStack charStack2 = new CharStack(3);
        Assertions.assertThat(charStack2).isToStringEqualTo("");

        CharStack charStack3 = new CharStack(10);
        Assertions.assertThat(charStack3).isToStringEqualTo("");
    }

    /**
     * {@link CharStack} class test.
     */
    @Test
    public void appendTest() {
        CharStack charStack1 = new CharStack(0);
        charStack1.append('a');
        Assertions.assertThat(charStack1).isToStringEqualTo("");
        charStack1.append('b');
        Assertions.assertThat(charStack1).isToStringEqualTo("");
        charStack1.append('1');
        Assertions.assertThat(charStack1).isToStringEqualTo("");
        charStack1.append('2');
        Assertions.assertThat(charStack1).isToStringEqualTo("");
        charStack1.append('a');
        Assertions.assertThat(charStack1).isToStringEqualTo("");
        charStack1.append('b');
        Assertions.assertThat(charStack1).isToStringEqualTo("");

        CharStack charStack2 = new CharStack(3);
        charStack2.append('a');
        Assertions.assertThat(charStack2).isToStringEqualTo("a");
        charStack2.append('b');
        Assertions.assertThat(charStack2).isToStringEqualTo("ab");
        charStack2.append('c');
        Assertions.assertThat(charStack2).isToStringEqualTo("abc");
        charStack2.append('1');
        Assertions.assertThat(charStack2).isToStringEqualTo("bc1");
        charStack2.append('2');
        Assertions.assertThat(charStack2).isToStringEqualTo("c12");
        charStack2.append('3');
        Assertions.assertThat(charStack2).isToStringEqualTo("123");
        charStack2.append('a');
        Assertions.assertThat(charStack2).isToStringEqualTo("23a");
        charStack2.append('b');
        Assertions.assertThat(charStack2).isToStringEqualTo("3ab");

        CharStack charStack3 = new CharStack(10);
        charStack3.append('a');
        Assertions.assertThat(charStack3).isToStringEqualTo("a");
        charStack3.append('b');
        Assertions.assertThat(charStack3).isToStringEqualTo("ab");
        charStack3.append('c');
        Assertions.assertThat(charStack3).isToStringEqualTo("abc");
        charStack3.append('d');
        Assertions.assertThat(charStack3).isToStringEqualTo("abcd");
        charStack3.append('e');
        Assertions.assertThat(charStack3).isToStringEqualTo("abcde");
        charStack3.append('f');
        Assertions.assertThat(charStack3).isToStringEqualTo("abcdef");
        charStack3.append('g');
        Assertions.assertThat(charStack3).isToStringEqualTo("abcdefg");
        charStack3.append('h');
        Assertions.assertThat(charStack3).isToStringEqualTo("abcdefgh");
        charStack3.append('i');
        Assertions.assertThat(charStack3).isToStringEqualTo("abcdefghi");
        charStack3.append('j');
        Assertions.assertThat(charStack3).isToStringEqualTo("abcdefghij");
        charStack3.append('1');
        Assertions.assertThat(charStack3).isToStringEqualTo("bcdefghij1");
        charStack3.append('2');
        Assertions.assertThat(charStack3).isToStringEqualTo("cdefghij12");
        charStack3.append('3');
        Assertions.assertThat(charStack3).isToStringEqualTo("defghij123");
        charStack3.append('4');
        Assertions.assertThat(charStack3).isToStringEqualTo("efghij1234");
        charStack3.append('5');
        Assertions.assertThat(charStack3).isToStringEqualTo("fghij12345");
        charStack3.append('6');
        Assertions.assertThat(charStack3).isToStringEqualTo("ghij123456");
        charStack3.append('7');
        Assertions.assertThat(charStack3).isToStringEqualTo("hij1234567");
        charStack3.append('8');
        Assertions.assertThat(charStack3).isToStringEqualTo("ij12345678");
        charStack3.append('9');
        Assertions.assertThat(charStack3).isToStringEqualTo("j123456789");
        charStack3.append('0');
        Assertions.assertThat(charStack3).isToStringEqualTo("1234567890");
        charStack3.append('a');
        Assertions.assertThat(charStack3).isToStringEqualTo("234567890a");
        charStack3.append('b');
        Assertions.assertThat(charStack3).isToStringEqualTo("34567890ab");
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
        Assertions.assertThat(charStack).isToStringEqualTo("a\\rb\\nc\\r\\nd");
    }

}
