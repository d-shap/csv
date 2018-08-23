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
        Assertions.assertThat(charStack1).hasToString("");

        CharStack charStack2 = new CharStack(3);
        Assertions.assertThat(charStack2).hasToString("");

        CharStack charStack3 = new CharStack(10);
        Assertions.assertThat(charStack3).hasToString("");
    }

    /**
     * {@link CharStack} class test.
     */
    @Test
    public void appendTest() {
        CharStack charStack1 = new CharStack(0);
        charStack1.append('a');
        Assertions.assertThat(charStack1).hasToString("");
        charStack1.append('b');
        Assertions.assertThat(charStack1).hasToString("");
        charStack1.append('1');
        Assertions.assertThat(charStack1).hasToString("");
        charStack1.append('2');
        Assertions.assertThat(charStack1).hasToString("");
        charStack1.append('a');
        Assertions.assertThat(charStack1).hasToString("");
        charStack1.append('b');
        Assertions.assertThat(charStack1).hasToString("");

        CharStack charStack2 = new CharStack(3);
        charStack2.append('a');
        Assertions.assertThat(charStack2).hasToString("a");
        charStack2.append('b');
        Assertions.assertThat(charStack2).hasToString("ab");
        charStack2.append('c');
        Assertions.assertThat(charStack2).hasToString("abc");
        charStack2.append('1');
        Assertions.assertThat(charStack2).hasToString("bc1");
        charStack2.append('2');
        Assertions.assertThat(charStack2).hasToString("c12");
        charStack2.append('3');
        Assertions.assertThat(charStack2).hasToString("123");
        charStack2.append('a');
        Assertions.assertThat(charStack2).hasToString("23a");
        charStack2.append('b');
        Assertions.assertThat(charStack2).hasToString("3ab");

        CharStack charStack3 = new CharStack(10);
        charStack3.append('a');
        Assertions.assertThat(charStack3).hasToString("a");
        charStack3.append('b');
        Assertions.assertThat(charStack3).hasToString("ab");
        charStack3.append('c');
        Assertions.assertThat(charStack3).hasToString("abc");
        charStack3.append('d');
        Assertions.assertThat(charStack3).hasToString("abcd");
        charStack3.append('e');
        Assertions.assertThat(charStack3).hasToString("abcde");
        charStack3.append('f');
        Assertions.assertThat(charStack3).hasToString("abcdef");
        charStack3.append('g');
        Assertions.assertThat(charStack3).hasToString("abcdefg");
        charStack3.append('h');
        Assertions.assertThat(charStack3).hasToString("abcdefgh");
        charStack3.append('i');
        Assertions.assertThat(charStack3).hasToString("abcdefghi");
        charStack3.append('j');
        Assertions.assertThat(charStack3).hasToString("abcdefghij");
        charStack3.append('1');
        Assertions.assertThat(charStack3).hasToString("bcdefghij1");
        charStack3.append('2');
        Assertions.assertThat(charStack3).hasToString("cdefghij12");
        charStack3.append('3');
        Assertions.assertThat(charStack3).hasToString("defghij123");
        charStack3.append('4');
        Assertions.assertThat(charStack3).hasToString("efghij1234");
        charStack3.append('5');
        Assertions.assertThat(charStack3).hasToString("fghij12345");
        charStack3.append('6');
        Assertions.assertThat(charStack3).hasToString("ghij123456");
        charStack3.append('7');
        Assertions.assertThat(charStack3).hasToString("hij1234567");
        charStack3.append('8');
        Assertions.assertThat(charStack3).hasToString("ij12345678");
        charStack3.append('9');
        Assertions.assertThat(charStack3).hasToString("j123456789");
        charStack3.append('0');
        Assertions.assertThat(charStack3).hasToString("1234567890");
        charStack3.append('a');
        Assertions.assertThat(charStack3).hasToString("234567890a");
        charStack3.append('b');
        Assertions.assertThat(charStack3).hasToString("34567890ab");
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
        Assertions.assertThat(charStack).hasToString("a\\rb\\nc\\r\\nd");
    }

    /**
     * {@link CharStack} class test.
     */
    @Test
    public void stringBuilderSizeTest() {
        Assertions.assertThat(new CharStack(10), "_stringBuilderSize", Raw.intAssertion()).isEqualTo(20);
        Assertions.assertThat(new CharStack(5), "_stringBuilderSize", Raw.intAssertion()).isEqualTo(10);
        Assertions.assertThat(new CharStack(25), "_stringBuilderSize", Raw.intAssertion()).isEqualTo(50);
    }

}
