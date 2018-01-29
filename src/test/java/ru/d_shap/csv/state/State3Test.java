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
 * Tests for {@link State3}.
 *
 * @author Dmitry Shapovalov
 */
public final class State3Test extends CsvTest {

    /**
     * Test class constructor.
     */
    public State3Test() {
        super();
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processEndOfInputCrAsSeparatorTest() {
        String csv = "\r\n\r";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder();
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processEndOfInputCrAsTextTest() {
        String csv = "\r\n\r";
        List<List<String>> list = createCsvParser(true, true, false, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("\r");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processCommaAsSeparatorCrAsSeparatorTest() {
        String csv = "\r\n\r,";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder();
        Assertions.assertThat(list.get(2)).containsExactlyInOrder("", "");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processCommaAsSeparatorCrAsTextTest() {
        String csv = "\r\n\r,";
        List<List<String>> list = createCsvParser(true, true, false, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("\r", "");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processCommaAsTextCrAsSeparatorTest() {
        String csv = "\r\n\r,";
        List<List<String>> list = createCsvParser(false, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder();
        Assertions.assertThat(list.get(2)).containsExactlyInOrder(",");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processCommaAsTextCrAsTextTest() {
        String csv = "\r\n\r,";
        List<List<String>> list = createCsvParser(false, true, false, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("\r,");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processSemicolonAsSeparatorCrAsSeparatorTest() {
        String csv = "\r\n\r;";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder();
        Assertions.assertThat(list.get(2)).containsExactlyInOrder("", "");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processSemicolonAsSeparatorCrAsTextTest() {
        String csv = "\r\n\r;";
        List<List<String>> list = createCsvParser(true, true, false, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("\r", "");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processSemicolonAsTextCrAsSeparatorTest() {
        String csv = "\r\n\r;";
        List<List<String>> list = createCsvParser(true, false, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder();
        Assertions.assertThat(list.get(2)).containsExactlyInOrder(";");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processSemicolonAsTextCrAsTextTest() {
        String csv = "\r\n\r;";
        List<List<String>> list = createCsvParser(true, false, false, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("\r;");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processCrAsSeparatorTest() {
        String csv = "\r\n\r\ra";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(4);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder();
        Assertions.assertThat(list.get(2)).containsExactlyInOrder();
        Assertions.assertThat(list.get(3)).containsExactlyInOrder("a");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processCrAsTextTest() {
        String csv = "\r\n\r\ra";
        List<List<String>> list = createCsvParser(true, true, false, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("\r\ra");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processLfTest() {
        String csv = "\r\n\r\n";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder();
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processQuotCrAsSeparatorTest() {
        String csv = "\r\n\r\"a\"";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder();
        Assertions.assertThat(list.get(2)).containsExactlyInOrder("a");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processQuotCrAsTextTest() {
        try {
            String csv = "\r\n\r\"a\"";
            createCsvParser(true, true, false, true, true).parse(csv);
            Assertions.fail("State3 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: '\"' (34). Last characters: \"\\r\\n\\r\"\".");
        }
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processDefaultCrAsSeparatorTest() {
        String csv = "\r\n\ra";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder();
        Assertions.assertThat(list.get(2)).containsExactlyInOrder("a");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processDefaultCrAsTextTest() {
        String csv = "\r\n\ra";
        List<List<String>> list = createCsvParser(true, true, false, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("\ra");
    }

}
