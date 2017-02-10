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
import ru.d_shap.csv.ColumnSeparators;
import ru.d_shap.csv.CsvParseException;
import ru.d_shap.csv.CsvParser;
import ru.d_shap.csv.RowSeparators;

/**
 * Tests for {@link State7}.
 *
 * @author Dmitry Shapovalov
 */
public final class State7Test {

    /**
     * Test class constructor.
     */
    public State7Test() {
        super();
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processEndOfInputTest() {
        String csv = "\"a\"";
        List<List<String>> list = CsvParser.parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("a");
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processCommaAsSeparatorTest() {
        String csv = "\"a\",";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.COMMA);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("a", "");
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processCommaAsTextTest() {
        try {
            String csv = "\"a\",";
            CsvParser.parse(csv, ColumnSeparators.SEMICOLON);
            Assertions.fail("State7 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: ',' (44). Last symbols: \"\"a\",\".");
        }
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processSemicolonAsSeparatorTest() {
        String csv = "\"\";";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.SEMICOLON);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "");
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processSemicolonAsTextTest() {
        try {
            String csv = "\"\";";
            CsvParser.parse(csv, ColumnSeparators.COMMA);
            Assertions.fail("State7 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: ';' (59). Last symbols: \"\"\";\".");
        }
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processCrAsSeparatorPartTest() {
        String csv = "\"\"\r\na";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.CRLF);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("a");
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processCrAsSeparatorTest() {
        String csv = "\"\"\ra";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.CR);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("a");
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processCrAsTextTest() {
        try {
            String csv = "\"\"\ra";
            CsvParser.parse(csv, RowSeparators.LF);
            Assertions.fail("State7 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: '\r' (13). Last symbols: \"\"\"\\r\".");
        }
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processLfAsSeparatorTest() {
        String csv = "\"\"\na";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.LF);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("a");
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processLfAsTextTest() {
        try {
            String csv = "\"\"\na";
            CsvParser.parse(csv, RowSeparators.CR);
            Assertions.fail("State7 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: '\n' (10). Last symbols: \"\"\"\\n\".");
        }
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processQuotTest() {
        String csv = "\"\"\"\"";
        List<List<String>> list = CsvParser.parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("\"");
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processDefaultFailTest() {
        try {
            String csv = "\"\"a\"";
            CsvParser.parse(csv);
            Assertions.fail("State7 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: 'a' (97). Last symbols: \"\"\"a\".");
        }
    }

}
