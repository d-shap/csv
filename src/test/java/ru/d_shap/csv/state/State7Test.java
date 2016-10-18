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
package ru.d_shap.csv.state;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ru.d_shap.csv.CsvParseException;
import ru.d_shap.csv.CsvParser;

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
        String csv = "a";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processCommaTest() {
        String csv = "a,";
        List<List<String>> list = CsvParser.parseCsv(csv);
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
    public void processSemicolonTest() {
        String csv = "a;";
        List<List<String>> list = CsvParser.parseCsv(csv);
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
    public void processCrTest() {
        String csv = "a\r";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));

    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processLfTest() {
        String csv = "a\n";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
    }

    /**
     * {@link State7} class test.
     */
    @Test(expected = CsvParseException.class)
    public void processQuotFailTest() {
        String csv = "a\"\"";
        CsvParser.parseCsv(csv);
    }

    /**
     * {@link State7} class test.
     */
    @Test
    public void processDefaultTest() {
        String csv = "aa";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("aa", list.get(0).get(0));
    }

}
