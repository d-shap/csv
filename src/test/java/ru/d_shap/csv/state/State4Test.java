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
import ru.d_shap.csv.CsvParseException;
import ru.d_shap.csv.CsvTest;

/**
 * Tests for {@link State4}.
 *
 * @author Dmitry Shapovalov
 */
public final class State4Test extends CsvTest {

    /**
     * Test class constructor.
     */
    public State4Test() {
        super();
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processEndOfInputCrAsSeparatorTest() {
        String csv = ",a\r";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "a");
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processEndOfInputCrAsTextTest() {
        String csv = ",a\r";
        List<List<String>> list = createCsvParser(true, true, false, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "a\r");
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processCommaAsSeparatorCrAsSeparatorTest() {
        String csv = ",a\r,";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "a");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("", "");
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processCommaAsSeparatorCrAsTextTest() {
        String csv = ",a\r,";
        List<List<String>> list = createCsvParser(true, true, false, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "a\r", "");
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processCommaAsTextCrAsSeparatorTest() {
        String csv = ",a\r,";
        List<List<String>> list = createCsvParser(false, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder(",a");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder(",");
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processCommaAsTextCrAsTextTest() {
        String csv = ",a\r,";
        List<List<String>> list = createCsvParser(false, true, false, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder(",a\r,");
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processSemicolonAsSeparatorCrAsSeparatorTest() {
        String csv = ";a\r;";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "a");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("", "");
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processSemicolonAsSeparatorCrAsTextTest() {
        String csv = ";a\r;";
        List<List<String>> list = createCsvParser(true, true, false, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "a\r", "");
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processSemicolonAsTextCrAsSeparatorTest() {
        String csv = ";a\r;";
        List<List<String>> list = createCsvParser(true, false, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder(";a");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder(";");
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processSemicolonAsTextCrAsTextTest() {
        String csv = ";a\r;";
        List<List<String>> list = createCsvParser(true, false, false, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder(";a\r;");
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processCrAsSeparatorTest() {
        String csv = ",a\r\rb";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "a");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder();
        Assertions.assertThat(list.get(2)).containsExactlyInOrder("b");
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processCrAsTextTest() {
        String csv = ",a\r\rb";
        List<List<String>> list = createCsvParser(true, true, false, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "a\r\rb");
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processLfTest() {
        String csv = ",a\r\n";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "a");
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processQuotCrAsSeparatorTest() {
        String csv = ",a\r\"b\"";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "a");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("b");
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processQuotCrAsTextTest() {
        try {
            String csv = ",a\r\"b\"";
            createCsvParser(true, true, false, true, true).parse(csv);
            Assertions.fail("State4 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: '\"' (34). Last characters: \",a\\r\"\".");
        }
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processDefaultCrAsSeparatorTest() {
        String csv = ",a\rb";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "a");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("b");
    }

    /**
     * {@link State4} class test.
     */
    @Test
    public void processDefaultCrAsTextTest() {
        String csv = ",a\rb";
        List<List<String>> list = createCsvParser(true, true, false, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "a\rb");
    }

}
