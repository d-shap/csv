// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv.state;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ru.d_shap.csv.CsvParseException;
import ru.d_shap.csv.CsvParser;

/**
 * Tests for {@link ru.d_shap.csv.state.State7}.
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
     * {@link ru.d_shap.csv.state.State7} class test.
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
     * {@link ru.d_shap.csv.state.State7} class test.
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
     * {@link ru.d_shap.csv.state.State7} class test.
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
     * {@link ru.d_shap.csv.state.State7} class test.
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
     * {@link ru.d_shap.csv.state.State7} class test.
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
     * {@link ru.d_shap.csv.state.State7} class test.
     */
    @Test(expected = CsvParseException.class)
    public void processQuotTest() {
        String csv = "a\"\"";
        CsvParser.parseCsv(csv);
    }

    /**
     * {@link ru.d_shap.csv.state.State7} class test.
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
