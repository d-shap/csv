// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv.state;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ru.d_shap.csv.CsvParser;

/**
 * Tests for {@link State0}.
 *
 * @author Dmitry Shapovalov
 */
public final class State0Test {

    /**
     * Test class constructor.
     */
    public State0Test() {
        super();
    }

    /**
     * {@link State0} class test.
     */
    @Test
    public void processEndOfInputTest() {
        String csv = "";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(0, list.size());
    }

    /**
     * {@link State0} class test.
     */
    @Test
    public void processCommaTest() {
        String csv = ",a";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("a", list.get(0).get(1));
    }

    /**
     * {@link State0} class test.
     */
    @Test
    public void processSemicolonTest() {
        String csv = ";a";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("a", list.get(0).get(1));
    }

    /**
     * {@link State0} class test.
     */
    @Test
    public void processCrTest() {
        String csv = "\ra";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(0, list.get(0).size());
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("a", list.get(1).get(0));
    }

    /**
     * {@link State0} class test.
     */
    @Test
    public void processLfTest() {
        String csv = "\na";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(0, list.get(0).size());
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("a", list.get(1).get(0));
    }

    /**
     * {@link State0} class test.
     */
    @Test
    public void processQuotTest() {
        String csv = "\"aa\"";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("aa", list.get(0).get(0));
    }

    /**
     * {@link State0} class test.
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
