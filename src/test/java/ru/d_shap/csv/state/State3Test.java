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
 * Tests for {@link ru.d_shap.csv.state.State3}.
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
     * {@link ru.d_shap.csv.state.State3} class test.
     */
    @Test
    public void processEndOfInputTest() {
        String csv = "\n\r";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(0, list.get(0).size());
        Assert.assertEquals(0, list.get(1).size());
    }

    /**
     * {@link ru.d_shap.csv.state.State3} class test.
     */
    @Test
    public void processCommaTest() {
        String csv = "\n\r,";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(0, list.get(0).size());
        Assert.assertEquals(0, list.get(1).size());
        Assert.assertEquals(2, list.get(2).size());
        Assert.assertEquals("", list.get(2).get(0));
        Assert.assertEquals("", list.get(2).get(1));
    }

    /**
     * {@link ru.d_shap.csv.state.State3} class test.
     */
    @Test
    public void processSemicolonTest() {
        String csv = "\n\r;";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(0, list.get(0).size());
        Assert.assertEquals(0, list.get(1).size());
        Assert.assertEquals(2, list.get(2).size());
        Assert.assertEquals("", list.get(2).get(0));
        Assert.assertEquals("", list.get(2).get(1));
    }

    /**
     * {@link ru.d_shap.csv.state.State3} class test.
     */
    @Test
    public void processCrTest() {
        String csv = "\n\r\r";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(0, list.get(0).size());
        Assert.assertEquals(0, list.get(1).size());
        Assert.assertEquals(0, list.get(2).size());
    }

    /**
     * {@link ru.d_shap.csv.state.State3} class test.
     */
    @Test
    public void processLfTest() {
        String csv = "\n\r\n";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(0, list.get(0).size());
        Assert.assertEquals(0, list.get(1).size());
    }

    /**
     * {@link ru.d_shap.csv.state.State3} class test.
     */
    @Test
    public void processQuotTest() {
        String csv = "\n\r\"aa\"";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(0, list.get(0).size());
        Assert.assertEquals(0, list.get(1).size());
        Assert.assertEquals(1, list.get(2).size());
        Assert.assertEquals("aa", list.get(2).get(0));
    }

    /**
     * {@link ru.d_shap.csv.state.State3} class test.
     */
    @Test
    public void processDefaultTest() {
        String csv = "\n\raa";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(0, list.get(0).size());
        Assert.assertEquals(0, list.get(1).size());
        Assert.assertEquals(1, list.get(2).size());
        Assert.assertEquals("aa", list.get(2).get(0));
    }

}
