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
 * Tests for {@link ru.d_shap.csv.state.State5}.
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
     * {@link ru.d_shap.csv.state.State5} class test.
     */
    @Test(expected = CsvParseException.class)
    public void processEndOfInputTest() {
        String csv = "\"";
        CsvParser.parseCsv(csv);
    }

    /**
     * {@link ru.d_shap.csv.state.State5} class test.
     */
    @Test
    public void processCommaTest() {
        String csv = "\",\"";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals(",", list.get(0).get(0));
    }

    /**
     * {@link ru.d_shap.csv.state.State5} class test.
     */
    @Test
    public void processSemicolonTest() {
        String csv = "\";\"";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals(";", list.get(0).get(0));
    }

    /**
     * {@link ru.d_shap.csv.state.State5} class test.
     */
    @Test
    public void processCrTest() {
        String csv = "\"\r\"";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("\r", list.get(0).get(0));
    }

    /**
     * {@link ru.d_shap.csv.state.State5} class test.
     */
    @Test
    public void processLfTest() {
        String csv = "\"\n\"";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("\n", list.get(0).get(0));
    }

    /**
     * {@link ru.d_shap.csv.state.State5} class test.
     */
    @Test
    public void processQuotTest() {
        String csv = "\"\"\"\"";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("\"", list.get(0).get(0));
    }

    /**
     * {@link ru.d_shap.csv.state.State5} class test.
     */
    @Test
    public void processDefaultTest() {
        String csv = "\"aa\"";
        List<List<String>> list = CsvParser.parseCsv(csv);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).size());
        Assert.assertEquals("aa", list.get(0).get(0));
    }

}
