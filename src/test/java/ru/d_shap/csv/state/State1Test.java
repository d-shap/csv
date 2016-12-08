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
import ru.d_shap.csv.CsvParser;
import ru.d_shap.csv.RowSeparators;

/**
 * Tests for {@link State1}.
 *
 * @author Dmitry Shapovalov
 */
public final class State1Test {

    /**
     * Test class constructor.
     */
    public State1Test() {
        super();
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processEndOfInputTest() {
        String csv = "";
        List<List<String>> list = CsvParser.parse(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(0, list.size());
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processCommaAsSeparatorTest() {
        String csv = ",a";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.COMMA);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("a", list.get(0).get(1));
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processCommaAsTextTest() {
        String csv = ",a";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.SEMICOLON);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals(",a", list.get(0).get(0));
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processSemicolonAsSeparatorTest() {
        String csv = ";a";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.SEMICOLON);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("a", list.get(0).get(1));
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processSemicoloAsTextTest() {
        String csv = ";a";
        List<List<String>> list = CsvParser.parse(csv, ColumnSeparators.COMMA);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals(";a", list.get(0).get(0));
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processCrAsSeparatorPartTest() {
        String csv = "\r\na";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.CRLF);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(0, list.get(0).size());
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("a", list.get(1).get(0));
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processCrAsSeparatorTest() {
        String csv = "\ra";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.CR);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(0, list.get(0).size());
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("a", list.get(1).get(0));
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processCrAsTextTest() {
        String csv = "\ra";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.LF);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("\ra", list.get(0).get(0));
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processLfAsSeparatorTest() {
        String csv = "\na";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.LF);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(0, list.get(0).size());
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("a", list.get(1).get(0));
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processLfAsTextTest() {
        String csv = "\na";
        List<List<String>> list = CsvParser.parse(csv, RowSeparators.CR);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("\na", list.get(0).get(0));
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processQuotTest() {
        String csv = "\"a\"";
        List<List<String>> list = CsvParser.parse(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processDefaultTest() {
        String csv = "a";
        List<List<String>> list = CsvParser.parse(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
    }

}
