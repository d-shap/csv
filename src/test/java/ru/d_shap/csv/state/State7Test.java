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

import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processCommaAsSeparatorTest() {
        String csv = "\"a\",";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.COMMA);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processCommaAsTextTest() {
        try {
            String csv = "\"a\",";
            CsvParser.parse(csv, ColumnSeparators.SEMICOLON);
            Assert.fail("Parse csv fail");
        } catch (CsvParseException ex) {
            Assert.assertEquals("Wrong symbol obtained: ',' (44). Last symbols: \"\"a\",\".", ex.getMessage());
        }
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processSemicolonAsSeparatorTest() {
        String csv = "\"\";";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.SEMICOLON);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processSemicolonAsTextTest() {
        try {
            String csv = "\"\";";
            CsvParser.parse(csv, ColumnSeparators.COMMA);
            Assert.fail("Parse csv fail");
        } catch (CsvParseException ex) {
            Assert.assertEquals("Wrong symbol obtained: ';' (59). Last symbols: \"\"\";\".", ex.getMessage());
        }
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processCrAsSeparatorPartTest() {
        String csv = "\"\"\r\na";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.CRLF);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("a", list.get(1).get(0));
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processCrAsSeparatorTest() {
        String csv = "\"\"\ra";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.CR);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("a", list.get(1).get(0));
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processCrAsTextTest() {
        try {
            String csv = "\"\"\ra";
            CsvParser.parse(csv, RowSeparators.LF);
            Assert.fail("Parse csv fail");
        } catch (CsvParseException ex) {
            Assert.assertEquals("Wrong symbol obtained: '\r' (13). Last symbols: \"\"\"\\r\".", ex.getMessage());
        }
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processLfAsSeparatorTest() {
        String csv = "\"\"\na";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.LF);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("a", list.get(1).get(0));
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processLfAsTextTest() {
        try {
            String csv = "\"\"\na";
            CsvParser.parse(csv, RowSeparators.CR);
            Assert.fail("Parse csv fail");
        } catch (CsvParseException ex) {
            Assert.assertEquals("Wrong symbol obtained: '\n' (10). Last symbols: \"\"\"\\n\".", ex.getMessage());
        }
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processQuotTest() {
        String csv = "\"\"\"\"";
        List<List<String>> list = CsvParser.parse(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("\"", list.get(0).get(0));
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processDefaultFailTest() {
        try {
            String csv = "\"\"a\"";
            CsvParser.parse(csv);
            Assert.fail("Parse csv fail");
        } catch (CsvParseException ex) {
            Assert.assertEquals("Wrong symbol obtained: 'a' (97). Last symbols: \"\"\"a\".", ex.getMessage());
        }
    }

}
