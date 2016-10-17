// CSV-parser converts source stream to rows and columns and vice versa.
// Copyright (C) 2016 Dmitry Shapovalov.
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
package ru.d_shap.csv.state;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ru.d_shap.csv.CsvParser;

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
        String csv = ";";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processCommaTest() {
        String csv = ";,";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(3, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
        Assert.assertEquals("", list.get(0).get(2));
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processSemicolonTest() {
        String csv = ";;";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(3, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
        Assert.assertEquals("", list.get(0).get(2));

    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processCrTest() {
        String csv = ";\ra";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("a", list.get(1).get(0));
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processLfTest() {
        String csv = ";\na";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("a", list.get(1).get(0));
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processQuotTest() {
        String csv = ";\"aa\"";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("aa", list.get(0).get(1));
    }

    /**
     * {@link State1} class test.
     */
    @Test
    public void processDefaultTest() {
        String csv = ";aa";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("aa", list.get(0).get(1));
    }

}
