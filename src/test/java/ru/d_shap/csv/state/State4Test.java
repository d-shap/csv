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
 * Tests for {@link ru.d_shap.csv.state.State4}.
 *
 * @author Dmitry Shapovalov
 */
public final class State4Test {

    /**
     * Test class constructor.
     */
    public State4Test() {
        super();
    }

    /**
     * {@link ru.d_shap.csv.state.State4} class test.
     */
    @Test
    public void processEndOfInputTest() {
        String csv = ",\r";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
    }

    /**
     * {@link ru.d_shap.csv.state.State4} class test.
     */
    @Test
    public void processCommaTest() {
        String csv = ",\r,";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
        Assert.assertEquals(2, list.get(1).size());
        Assert.assertEquals("", list.get(1).get(0));
        Assert.assertEquals("", list.get(1).get(1));
    }

    /**
     * {@link ru.d_shap.csv.state.State4} class test.
     */
    @Test
    public void processSemicolonTest() {
        String csv = ",\r;";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
        Assert.assertEquals(2, list.get(1).size());
        Assert.assertEquals("", list.get(1).get(0));
        Assert.assertEquals("", list.get(1).get(1));
    }

    /**
     * {@link ru.d_shap.csv.state.State4} class test.
     */
    @Test
    public void processCrTest() {
        String csv = ",\r\r";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
        Assert.assertEquals(0, list.get(1).size());
    }

    /**
     * {@link ru.d_shap.csv.state.State4} class test.
     */
    @Test
    public void processLfTest() {
        String csv = ",\r\n";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
    }

    /**
     * {@link ru.d_shap.csv.state.State4} class test.
     */
    @Test
    public void processQuotTest() {
        String csv = ",\r\"aa\"";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("aa", list.get(1).get(0));
    }

    /**
     * {@link ru.d_shap.csv.state.State4} class test.
     */
    @Test
    public void processDefaultTest() {
        String csv = ",\raa";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("aa", list.get(1).get(0));
    }

}
