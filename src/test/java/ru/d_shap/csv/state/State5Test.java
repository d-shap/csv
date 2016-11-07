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
        List<List<String>> list = CsvParser.parse(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processEndOfInputCrTextTest() {
        try {
            String csv = "\"a\"\r";
            CsvParser.parse(csv, RowSeparators.CRLF);
            Assert.fail("Parse csv fail");
        } catch (CsvParseException ex) {
            Assert.assertEquals("End of input obtained. Last symbols: \"\"a\"\\r\".", ex.getMessage());
        }
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processCommaAsSeparatorCrSeparatorTest() {
        String csv = "\"a\"\r,";
        List<List<String>> list = CsvParser.parse(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals(2, list.get(1).size());
        Assert.assertEquals("", list.get(1).get(0));
        Assert.assertEquals("", list.get(1).get(1));
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processCommaAsSeparatorCrTextTest() {
        try {
            String csv = "\"a\"\r,";
            CsvParser.parse(csv, RowSeparators.CRLF);
            Assert.fail("Parse csv fail");
        } catch (CsvParseException ex) {
            Assert.assertEquals("Wrong symbol obtained: ',' (44). Last symbols: \"\"a\"\\r,\".", ex.getMessage());
        }
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processCommaAsTextCrSeparatorTest() {
        String csv = "\"a\"\r,";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.SEMICOLON);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals(",", list.get(1).get(0));
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processCommaAsTextCrTextTest() {
        try {
            String csv = "\"a\"\r,";
            CsvParser.parse(csv, ColumnSeparators.SEMICOLON, RowSeparators.CRLF);
            Assert.fail("Parse csv fail");
        } catch (CsvParseException ex) {
            Assert.assertEquals("Wrong symbol obtained: ',' (44). Last symbols: \"\"a\"\\r,\".", ex.getMessage());
        }
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processSemicolonAsSeparatorCrSeparatorTest() {
        String csv = "\"a\"\r;";
        List<List<String>> list = CsvParser.parse(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals(2, list.get(1).size());
        Assert.assertEquals("", list.get(1).get(0));
        Assert.assertEquals("", list.get(1).get(1));
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processSemicolonAsSeparatorCrTextTest() {
        try {
            String csv = "\"a\"\r;";
            CsvParser.parse(csv, RowSeparators.CRLF);
            Assert.fail("Parse csv fail");
        } catch (CsvParseException ex) {
            Assert.assertEquals("Wrong symbol obtained: ';' (59). Last symbols: \"\"a\"\\r;\".", ex.getMessage());
        }
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processSemicolonAsTextCrSeparatorTest() {
        String csv = "\"a\"\r;";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.COMMA);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals(";", list.get(1).get(0));
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processSemicolonAsTextCrTextTest() {
        try {
            String csv = "\"a\"\r;";
            CsvParser.parse(csv, ColumnSeparators.COMMA, RowSeparators.CRLF);
            Assert.fail("Parse csv fail");
        } catch (CsvParseException ex) {
            Assert.assertEquals("Wrong symbol obtained: ';' (59). Last symbols: \"\"a\"\\r;\".", ex.getMessage());
        }
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processCrAsSeparatorTest() {
        String csv = "\"a\"\r\ra";
        List<List<String>> list = CsvParser.parse(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals(0, list.get(1).size());
        Assert.assertEquals(1, list.get(2).size());
        Assert.assertEquals("a", list.get(2).get(0));
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processCrAsTextTest() {
        try {
            String csv = "\"a\"\r\ra";
            CsvParser.parse(csv, RowSeparators.CRLF);
            Assert.fail("Parse csv fail");
        } catch (CsvParseException ex) {
            Assert.assertEquals("Wrong symbol obtained: '\r' (13). Last symbols: \"\"a\"\\r\\r\".", ex.getMessage());
        }
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processLfTest() {
        String csv = "\"a\"\r\n";
        List<List<String>> list = CsvParser.parse(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processQuotCrSeparatorTest() {
        String csv = "\"a\"\r\"a\"";
        List<List<String>> list = CsvParser.parse(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("a", list.get(1).get(0));
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processQuotCrTextTest() {
        try {
            String csv = "\"a\"\r\"a\"";
            CsvParser.parse(csv, RowSeparators.CRLF);
            Assert.fail("Parse csv fail");
        } catch (CsvParseException ex) {
            Assert.assertEquals("Wrong symbol obtained: '\"' (34). Last symbols: \"\"a\"\\r\"\".", ex.getMessage());
        }
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processDefaultCrSeparatorTest() {
        String csv = "\"a\"\ra";
        List<List<String>> list = CsvParser.parse(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("a", list.get(1).get(0));
    }

    /**
     * {@link State5} class test.
     */
    @Test
    public void processDefaultCrTextTest() {
        try {
            String csv = "\"a\"\ra";
            CsvParser.parse(csv, RowSeparators.CRLF);
            Assert.fail("Parse csv fail");
        } catch (CsvParseException ex) {
            Assert.assertEquals("Wrong symbol obtained: 'a' (97). Last symbols: \"\"a\"\\ra\".", ex.getMessage());
        }
    }

}
