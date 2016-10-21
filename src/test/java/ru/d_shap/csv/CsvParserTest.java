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
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.csv;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link CsvParser}.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvParserTest {

    /**
     * Test class constructor.
     */
    public CsvParserTest() {
        super();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readColumnsWithSemicolonsTest() {
        String csv = "true;1.0.1;5";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(3, result.get(0).size());
        Assert.assertEquals("true", result.get(0).get(0));
        Assert.assertEquals("1.0.1", result.get(0).get(1));
        Assert.assertEquals("5", result.get(0).get(2));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readColumnsWithCommasTest() {
        String csv = "true,1.0.1,5";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(3, result.get(0).size());
        Assert.assertEquals("true", result.get(0).get(0));
        Assert.assertEquals("1.0.1", result.get(0).get(1));
        Assert.assertEquals("5", result.get(0).get(2));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readColumnsWithSpecialsTest() {
        String csv = "true,\"aa,bb;\r\na\",\"5\"";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(3, result.get(0).size());
        Assert.assertEquals("true", result.get(0).get(0));
        Assert.assertEquals("aa,bb;\r\na", result.get(0).get(1));
        Assert.assertEquals("5", result.get(0).get(2));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readEmptyColumnsTest() {
        String csv = ";a;;b;;;c;";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(8, result.get(0).size());
        Assert.assertEquals("", result.get(0).get(0));
        Assert.assertEquals("a", result.get(0).get(1));
        Assert.assertEquals("", result.get(0).get(2));
        Assert.assertEquals("b", result.get(0).get(3));
        Assert.assertEquals("", result.get(0).get(4));
        Assert.assertEquals("", result.get(0).get(5));
        Assert.assertEquals("c", result.get(0).get(6));
        Assert.assertEquals("", result.get(0).get(7));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readEmptySpecialColumnsTest() {
        String csv = "\"\",\"\",,\"\"";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(4, result.get(0).size());
        Assert.assertEquals("", result.get(0).get(0));
        Assert.assertEquals("", result.get(0).get(1));
        Assert.assertEquals("", result.get(0).get(2));
        Assert.assertEquals("", result.get(0).get(3));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readColumnsWithQuotsTest() {
        String csv = "\"a\"\"b\",ab;\"\"\"a\",\"b\"\"\"";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(4, result.get(0).size());
        Assert.assertEquals("a\"b", result.get(0).get(0));
        Assert.assertEquals("ab", result.get(0).get(1));
        Assert.assertEquals("\"a", result.get(0).get(2));
        Assert.assertEquals("b\"", result.get(0).get(3));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvParseException.class)
    public void wrongQuotFailTest() {
        String csv = "aaa,bb,c\"c,dd";
        CsvParser.parseCsv(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvParseException.class)
    public void notClosedQuotFailTest() {
        String csv = "aaa,bb,\"ccc,dd";
        CsvParser.parseCsv(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvParseException.class)
    public void notOpenedQuotFailTest() {
        String csv = "aaa,bb,ccc\",dd";
        CsvParser.parseCsv(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvParseException.class)
    public void notAllColumnInQuotFailTest() {
        String csv = "aaa,bb,\"ccc\"cc,dd";
        CsvParser.parseCsv(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readRowsWithCrTest() {
        String csv = "a\rb\rc";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(1, result.get(0).size());
        Assert.assertEquals("a", result.get(0).get(0));
        Assert.assertEquals(1, result.get(1).size());
        Assert.assertEquals("b", result.get(1).get(0));
        Assert.assertEquals(1, result.get(2).size());
        Assert.assertEquals("c", result.get(2).get(0));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readRowsWithLfTest() {
        String csv = "a\nb\nc";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(1, result.get(0).size());
        Assert.assertEquals("a", result.get(0).get(0));
        Assert.assertEquals(1, result.get(1).size());
        Assert.assertEquals("b", result.get(1).get(0));
        Assert.assertEquals(1, result.get(2).size());
        Assert.assertEquals("c", result.get(2).get(0));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readRowsWithCrLfTest() {
        String csv = "a\r\nb\r\nc";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(1, result.get(0).size());
        Assert.assertEquals("a", result.get(0).get(0));
        Assert.assertEquals(1, result.get(1).size());
        Assert.assertEquals("b", result.get(1).get(0));
        Assert.assertEquals(1, result.get(2).size());
        Assert.assertEquals("c", result.get(2).get(0));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readEmptyRowsTest() {
        String csv = "\naaa\n\nbbb\n\n";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(5, result.size());
        Assert.assertEquals(0, result.get(0).size());
        Assert.assertEquals(1, result.get(1).size());
        Assert.assertEquals("aaa", result.get(1).get(0));
        Assert.assertEquals(0, result.get(2).size());
        Assert.assertEquals(1, result.get(3).size());
        Assert.assertEquals("bbb", result.get(3).get(0));
        Assert.assertEquals(0, result.get(4).size());
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readRowsWithSpecialsTest() {
        String csv = "\",\"\r\n\"\"\"\"\r\n\"aaa;bbb\"\r\n";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(1, result.get(0).size());
        Assert.assertEquals(",", result.get(0).get(0));
        Assert.assertEquals(1, result.get(1).size());
        Assert.assertEquals("\"", result.get(1).get(0));
        Assert.assertEquals(1, result.get(2).size());
        Assert.assertEquals("aaa;bbb", result.get(2).get(0));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readTableTest() {
        String csv = "a,,false\nb,true,\n\n,c,d\n";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(4, result.size());
        Assert.assertEquals(3, result.get(0).size());
        Assert.assertEquals("a", result.get(0).get(0));
        Assert.assertEquals("", result.get(0).get(1));
        Assert.assertEquals("false", result.get(0).get(2));
        Assert.assertEquals(3, result.get(1).size());
        Assert.assertEquals("b", result.get(1).get(0));
        Assert.assertEquals("true", result.get(1).get(1));
        Assert.assertEquals("", result.get(1).get(2));
        Assert.assertEquals(0, result.get(2).size());
        Assert.assertEquals(3, result.get(3).size());
        Assert.assertEquals("", result.get(3).get(0));
        Assert.assertEquals("c", result.get(3).get(1));
        Assert.assertEquals("d", result.get(3).get(2));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readEmptyCsvTest() {
        String csv = "";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(0, result.size());
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvParseException.class)
    public void unquotedQuotFailTest() {
        String csv = "one;\"t\"wo\"\nthree;four";
        CsvParser.parseCsv(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvFromReaderTest() {
        String csv = ";,\n,aaa,bbb,\"a\"\"b\";\r";
        Reader reader = new StringReader(csv);
        List<List<String>> result = CsvParser.parseCsv(reader);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(3, result.get(0).size());
        Assert.assertEquals("", result.get(0).get(0));
        Assert.assertEquals("", result.get(0).get(1));
        Assert.assertEquals("", result.get(0).get(2));
        Assert.assertEquals(5, result.get(1).size());
        Assert.assertEquals("", result.get(1).get(0));
        Assert.assertEquals("aaa", result.get(1).get(1));
        Assert.assertEquals("bbb", result.get(1).get(2));
        Assert.assertEquals("a\"b", result.get(1).get(3));
        Assert.assertEquals("", result.get(1).get(4));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void checkRectangularTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        List<List<String>> result = CsvParser.parseCsv(csv, true);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(3, result.get(0).size());
        Assert.assertEquals("1", result.get(0).get(0));
        Assert.assertEquals("2", result.get(0).get(1));
        Assert.assertEquals("3", result.get(0).get(2));
        Assert.assertEquals(3, result.get(1).size());
        Assert.assertEquals("4", result.get(1).get(0));
        Assert.assertEquals("5", result.get(1).get(1));
        Assert.assertEquals("6", result.get(1).get(2));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void checkRectangularFailTest() {
        String csv = "1,2\r\n3,4,5\r\n";
        CsvParser.parseCsv(csv, true);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvFromReaderCheckRectangularTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        Reader reader = new StringReader(csv);
        List<List<String>> result = CsvParser.parseCsv(reader, true);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(3, result.get(0).size());
        Assert.assertEquals("1", result.get(0).get(0));
        Assert.assertEquals("2", result.get(0).get(1));
        Assert.assertEquals("3", result.get(0).get(2));
        Assert.assertEquals(3, result.get(1).size());
        Assert.assertEquals("4", result.get(1).get(0));
        Assert.assertEquals("5", result.get(1).get(1));
        Assert.assertEquals("6", result.get(1).get(2));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void parseCsvFromReaderCheckRectangularFailTest() {
        String csv = "1,2\r\n3,4,5\r\n";
        Reader reader = new StringReader(csv);
        CsvParser.parseCsv(reader, true);
    }

}
