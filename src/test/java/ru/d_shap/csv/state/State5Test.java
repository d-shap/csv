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

import java.util.List;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.csv.Builder;
import ru.d_shap.csv.CsvParseException;

/**
 * Tests for {@link State5}.
 *
 * @author Dmitry Shapovalov
 */
public final class State5Test {

    /**
     * Test class constructor.
     */
    public State5Test() {
        super();
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processEndOfInputCrSeparatorTest() {
        String csv = "\"a\"\r";
        List<List<String>> list = Builder.createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("a");
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processEndOfInputCrTextTest() {
        try {
            String csv = "\"a\"\r";
            Builder.createCsvParser(true, true, false, false, true).parse(csv);
            Assertions.fail("State5 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("End of input obtained. Last characters: \"\"a\"\\r\".");
        }
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processCommaAsSeparatorCrSeparatorTest() {
        String csv = "\"a\"\r,";
        List<List<String>> list = Builder.createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("", "");
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processCommaAsSeparatorCrTextTest() {
        try {
            String csv = "\"a\"\r,";
            Builder.createCsvParser(true, true, false, false, true).parse(csv);
            Assertions.fail("State5 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: ',' (44). Last characters: \"\"a\"\\r,\".");
        }
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processCommaAsTextCrSeparatorTest() {
        String csv = "\"a\"\r,";
        List<List<String>> list = Builder.createCsvParser(false, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder(",");
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processCommaAsTextCrTextTest() {
        try {
            String csv = "\"a\"\r,";
            Builder.createCsvParser(false, true, false, false, true).parse(csv);
            Assertions.fail("State5 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: ',' (44). Last characters: \"\"a\"\\r,\".");
        }
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processSemicolonAsSeparatorCrSeparatorTest() {
        String csv = "\"a\"\r;";
        List<List<String>> list = Builder.createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("", "");
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processSemicolonAsSeparatorCrTextTest() {
        try {
            String csv = "\"a\"\r;";
            Builder.createCsvParser(true, true, false, false, true).parse(csv);
            Assertions.fail("State5 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: ';' (59). Last characters: \"\"a\"\\r;\".");
        }
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processSemicolonAsTextCrSeparatorTest() {
        String csv = "\"a\"\r;";
        List<List<String>> list = Builder.createCsvParser(true, false, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder(";");
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processSemicolonAsTextCrTextTest() {
        try {
            String csv = "\"a\"\r;";
            Builder.createCsvParser(true, false, false, false, true).parse(csv);
            Assertions.fail("State5 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: ';' (59). Last characters: \"\"a\"\\r;\".");
        }
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processCrAsSeparatorTest() {
        String csv = "\"a\"\r\rb";
        List<List<String>> list = Builder.createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder();
        Assertions.assertThat(list.get(2)).containsExactlyInOrder("b");
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processCrAsTextTest() {
        try {
            String csv = "\"a\"\r\rb";
            Builder.createCsvParser(true, true, false, false, true).parse(csv);
            Assertions.fail("State5 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: '\r' (13). Last characters: \"\"a\"\\r\\r\".");
        }
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processLfTest() {
        String csv = "\"a\"\r\n";
        List<List<String>> list = Builder.createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("a");
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processQuotCrSeparatorTest() {
        String csv = "\"a\"\r\"b\"";
        List<List<String>> list = Builder.createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("b");
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processQuotCrTextTest() {
        try {
            String csv = "\"a\"\r\"b\"";
            Builder.createCsvParser(true, true, false, false, true).parse(csv);
            Assertions.fail("State5 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: '\"' (34). Last characters: \"\"a\"\\r\"\".");
        }
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processDefaultCrSeparatorTest() {
        String csv = "\"a\"\rb";
        List<List<String>> list = Builder.createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("b");
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processDefaultCrTextTest() {
        try {
            String csv = "\"a\"\rb";
            Builder.createCsvParser(true, true, false, false, true).parse(csv);
            Assertions.fail("State5 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: 'b' (98). Last characters: \"\"a\"\\rb\".");
        }
    }

}
