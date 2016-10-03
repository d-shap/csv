// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public final class CsvParserTest {

    public CsvParserTest() {
        super();
    }

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

    @Test(expected = CsvParseException.class)
    public void wrongQuotFailTest() {
        String csv = "aaa,bb,c\"c,dd";
        CsvParser.parseCsv(csv);
    }

    @Test(expected = CsvParseException.class)
    public void notClosedQuotFailTest() {
        String csv = "aaa,bb,\"ccc,dd";
        CsvParser.parseCsv(csv);
    }

    @Test(expected = CsvParseException.class)
    public void notOpenedQuotFailTest() {
        String csv = "aaa,bb,ccc\",dd";
        CsvParser.parseCsv(csv);
    }

    @Test(expected = CsvParseException.class)
    public void notAllValueInQuotFailTest() {
        String csv = "aaa,bb,\"ccc\"cc,dd";
        CsvParser.parseCsv(csv);
    }

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

    @Test
    public void readEmptyCsvTest() {
        String csv = "";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(0, result.size());
    }

    @Test(expected = CsvParseException.class)
    public void unquotedQuotTest() {
        String csv = "one;\"t\"wo\"\nthree;four";
        CsvParser.parseCsv(csv);
    }

}
