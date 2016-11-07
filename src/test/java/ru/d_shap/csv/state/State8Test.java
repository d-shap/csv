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
 * Tests for {@link State8}.
 *
 * @author Dmitry Shapovalov
 */
public final class State8Test {

    /**
     * Test class constructor.
     */
    public State8Test() {
        super();
    }

    /**
     * {@link State8} class test.
     */
    @Test
    public void processEndOfInputTest() {
        String csv = "a";
        List<List<String>> list = CsvParser.parse(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
    }

    /**
     * {@link State8} class test.
     */
    @Test
    public void processCommaAsSeparatorTest() {
        String csv = "a,";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.COMMA);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
    }

    /**
     * {@link State8} class test.
     */
    @Test
    public void processCommaAsTextTest() {
        String csv = "a,";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.SEMICOLON);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a,", list.get(0).get(0));
    }

    /**
     * {@link State8} class test.
     */
    @Test
    public void processSemicolonAsSeparatorTest() {
        String csv = "a;";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.SEMICOLON);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
    }

    /**
     * {@link State8} class test.
     */
    @Test
    public void processSemicolonAsTextTest() {
        String csv = "a;";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.COMMA);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a;", list.get(0).get(0));
    }

    /**
     * {@link State8} class test.
     */
    @Test
    public void processCrAsSeparatorPartTest() {
        String csv = "a\r\na";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.CRLF);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("a", list.get(1).get(0));
    }

    /**
     * {@link State8} class test.
     */
    @Test
    public void processCrAsSeparatorTest() {
        String csv = "a\ra";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.CR);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("a", list.get(1).get(0));
    }

    /**
     * {@link State8} class test.
     */
    @Test
    public void processCrAsTextTest() {
        String csv = "a\ra";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.LF);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a\ra", list.get(0).get(0));
    }

    /**
     * {@link State8} class test.
     */
    @Test
    public void processLfAsSeparatorTest() {
        String csv = "a\na";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.LF);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("a", list.get(1).get(0));
    }

    /**
     * {@link State8} class test.
     */
    @Test
    public void processLfAsTextTest() {
        String csv = "a\na";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.CR);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a\na", list.get(0).get(0));
    }

    /**
     * {@link State8} class test.
     */
    @Test
    public void processQuotFailTest() {
        try {
            String csv = "a\"\"";
            CsvParser.parse(csv);
            Assert.fail("Parse csv fail");
        } catch (CsvParseException ex) {
            Assert.assertEquals("Wrong symbol obtained: '\"' (34). Last symbols: \"a\"\".", ex.getMessage());
        }
    }

    /**
     * {@link State8} class test.
     */
    @Test
    public void processDefaultTest() {
        String csv = "aa";
        List<List<String>> list = CsvParser.parse(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("aa", list.get(0).get(0));
    }

}
