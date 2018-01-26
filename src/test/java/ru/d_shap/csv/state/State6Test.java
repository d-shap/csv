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
 * Tests for {@link State6}.
 *
 * @author Dmitry Shapovalov
 */
public final class State6Test {

    /**
     * Test class constructor.
     */
    public State6Test() {
        super();
    }

    /**
     * {@link State6} class test.
     */
    @Test
    public void processEndOfInputFailTest() {
        try {
            String csv = "\"";
            List<List<String>> list = CsvTest.createCsvParser(true, true, true, true, true).parse(csv);
            Assertions.fail("State6 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("End of input obtained. Last characters: \"\"\".");
        }
    }

    /**
     * {@link State6} class test.
     */
    @Test
    public void processCommaTest() {
        String csv = "\",\"";
        List<List<String>> list = CsvTest.createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder(",");
    }

    /**
     * {@link State6} class test.
     */
    @Test
    public void processSemicolonTest() {
        String csv = "\";\"";
        List<List<String>> list = CsvTest.createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder(";");
    }

    /**
     * {@link State6} class test.
     */
    @Test
    public void processCrTest() {
        String csv = "\"\r\"";
        List<List<String>> list = CsvTest.createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("\r");
    }

    /**
     * {@link State6} class test.
     */
    @Test
    public void processLfTest() {
        String csv = "\"\n\"";
        List<List<String>> list = CsvTest.createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("\n");
    }

    /**
     * {@link State6} class test.
     */
    @Test
    public void processQuotTest() {
        String csv = "\"\"\"\"";
        List<List<String>> list = CsvTest.createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("\"");
    }

    /**
     * {@link State6} class test.
     */
    @Test
    public void processDefaultTest() {
        String csv = "\"a\"";
        List<List<String>> list = CsvTest.createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("a");
    }

}
