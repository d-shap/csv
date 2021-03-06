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
import ru.d_shap.csv.CsvTest;
import ru.d_shap.csv.WrongColumnCountException;

/**
 * Tests for {@link State2}.
 *
 * @author Dmitry Shapovalov
 */
public final class State2Test extends CsvTest {

    /**
     * Test class constructor.
     */
    public State2Test() {
        super();
    }

    /**
     * {@link State2} class test.
     */
    @Test
    public void processEndOfInputTest() {
        String csv = ";";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "");
    }

    /**
     * {@link State2} class test.
     */
    @Test
    public void processCommaAsSeparatorTest() {
        String csv = ";,";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "", "");
    }

    /**
     * {@link State2} class test.
     */
    @Test
    public void processCommaAsTextTest() {
        String csv = ";,";
        List<List<String>> list = createCsvParser(false, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", ",");
    }

    /**
     * {@link State2} class test.
     */
    @Test
    public void processSemicolonAsSeparatorTest() {
        String csv = ",;";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "", "");
    }

    /**
     * {@link State2} class test.
     */
    @Test
    public void processSemicoloAsTextTest() {
        String csv = ",;";
        List<List<String>> list = createCsvParser(true, false, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", ";");
    }

    /**
     * {@link State2} class test.
     */
    @Test
    public void processCrAsSeparatorPartTest() {
        String csv = ";\r\na";
        List<List<String>> list = createCsvParser(true, true, false, false, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("a");
    }

    /**
     * {@link State2} class test.
     */
    @Test
    public void processCrAsSeparatorPartWithColumnCountCheckTest() {
        try {
            String csv = "1,2,3\r\n,\r\n6,7,8\r\n";
            createCsvParserWithColumnCountCheck(true, true, false, false, true).parse(csv);
            Assertions.fail("State2 test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\n,\\r\\n\".");
        }
    }

    /**
     * {@link State2} class test.
     */
    @Test
    public void processCrAsSeparatorTest() {
        String csv = ";\ra";
        List<List<String>> list = createCsvParser(true, true, true, false, false).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("a");
    }

    /**
     * {@link State2} class test.
     */
    @Test
    public void processCrAsSeparatorWithColumnCountCheckTest() {
        try {
            String csv = "1,2,3\r\n,\r\n6,7,8\r\n";
            createCsvParserWithColumnCountCheck(true, true, true, false, false).parse(csv);
            Assertions.fail("State2 test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\n,\\r\".");
        }
    }

    /**
     * {@link State2} class test.
     */
    @Test
    public void processCrAsTextTest() {
        String csv = ";\ra";
        List<List<String>> list = createCsvParser(true, true, false, true, false).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "\ra");
    }

    /**
     * {@link State2} class test.
     */
    @Test
    public void processLfAsSeparatorTest() {
        String csv = ";\na";
        List<List<String>> list = createCsvParser(true, true, false, true, false).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("a");
    }

    /**
     * {@link State2} class test.
     */
    @Test
    public void processLfAsTextTest() {
        String csv = ";\na";
        List<List<String>> list = createCsvParser(true, true, true, false, false).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "\na");
    }

    /**
     * {@link State2} class test.
     */
    @Test
    public void processQuotTest() {
        String csv = ";\"a\"";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "a");
    }

    /**
     * {@link State2} class test.
     */
    @Test
    public void processDefaultTest() {
        String csv = ";a";
        List<List<String>> list = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "a");
    }

}
