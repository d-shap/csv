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
 * Tests for {@link State3}.
 *
 * @author Dmitry Shapovalov
 */
public final class State3Test {

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
    public void processEndOfInputCrSeparatorTest() {
        String csv = "\r\n\r";
        List<List<String>> list = CsvParser.parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder();
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processEndOfInputCrTextTest() {
        String csv = "\r\n\r";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.CRLF);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("\r");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processCommaAsSeparatorCrSeparatorTest() {
        String csv = "\r\n\r,";
        List<List<String>> list = CsvParser.parse(csv);
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
    public void processCommaAsSeparatorCrTextTest() {
        String csv = "\r\n\r,";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.CRLF);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("\r", "");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processCommaAsTextCrSeparatorTest() {
        String csv = "\r\n\r,";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.SEMICOLON);
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
    public void processCommaAsTextCrTextTest() {
        String csv = "\r\n\r,";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.SEMICOLON, RowSeparators.CRLF);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("\r,");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processSemicolonAsSeparatorCrSeparatorTest() {
        String csv = "\r\n\r;";
        List<List<String>> list = CsvParser.parse(csv);
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
    public void processSemicolonAsSeparatorCrTextTest() {
        String csv = "\r\n\r;";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.CRLF);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("\r", "");
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processSemicolonAsTextCrSeparatorTest() {
        String csv = "\r\n\r;";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.COMMA);
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
    public void processSemicolonAsTextCrTextTest() {
        String csv = "\r\n\r;";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.COMMA, RowSeparators.CRLF);
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
        List<List<String>> list = CsvParser.parse(csv);
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
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.CRLF);
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
        List<List<String>> list = CsvParser.parse(csv);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder();
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processQuotCrSeparatorTest() {
        String csv = "\r\n\r\"a\"";
        List<List<String>> list = CsvParser.parse(csv);
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
    public void processQuotCrTextTest() {
        try {
            String csv = "\r\n\r\"a\"";
            CsvParser.parse(csv, RowSeparators.CRLF);
            Assertions.fail("State3 test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong symbol obtained: '\"' (34). Last symbols: \"\\r\\n\\r\"\".");
        }
    }

    /**
     * {@link State3} class test.
     */
    @Test
    public void processDefaultCrSeparatorTest() {
        String csv = "\r\n\ra";
        List<List<String>> list = CsvParser.parse(csv);
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
    public void processDefaultCrTextTest() {
        String csv = "\r\n\ra";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.CRLF);
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("\ra");
    }

}
