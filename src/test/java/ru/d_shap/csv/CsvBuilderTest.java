// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link CsvBuilder}.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvBuilderTest {

    /**
     * Test class constructor.
     */
    public CsvBuilderTest() {
        super();
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void addIntColumnTest() {
        CsvBuilder builder = new CsvBuilder();
        builder.addColumn(10);
        builder.addRow();
        String csv = builder.getCsv();
        Assert.assertEquals("10\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void addLongColumnTest() {
        CsvBuilder builder = new CsvBuilder();
        builder.addColumn(Long.MAX_VALUE);
        builder.addRow();
        String csv = builder.getCsv();
        Assert.assertEquals("9223372036854775807\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void addFloatColumnTest() {
        CsvBuilder builder = new CsvBuilder();
        builder.addColumn(1.1f);
        builder.addRow();
        String csv = builder.getCsv();
        Assert.assertEquals("1.1\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void addDoubleColumnTest() {
        CsvBuilder builder = new CsvBuilder();
        builder.addColumn(2.2);
        builder.addRow();
        String csv = builder.getCsv();
        Assert.assertEquals("2.2\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void addBooleanColumnTest() {
        CsvBuilder builder = new CsvBuilder();
        builder.addColumn(true);
        builder.addRow();
        String csv = builder.getCsv();
        Assert.assertEquals("true\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void addStringColumnTest() {
        CsvBuilder builder = new CsvBuilder();
        builder.addColumn("aaa");
        builder.addRow();
        String csv = builder.getCsv();
        Assert.assertEquals("aaa\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void addStringWithSpecialsColumnTest() {
        CsvBuilder builder = new CsvBuilder();
        builder.addColumn("a\"a,a;a\ra\na");
        builder.addRow();
        String csv = builder.getCsv();
        Assert.assertEquals("\"a\"\"a,a;a\ra\na\"\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void addMultipleColumnsTest() {
        CsvBuilder builder = new CsvBuilder();
        builder.addColumn(1);
        builder.addColumn(true);
        builder.addColumn("aaa");
        builder.addRow();
        String csv = builder.getCsv();
        Assert.assertEquals("1;true;aaa\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void addMultipleColumnsWithSpecialsTest() {
        CsvBuilder builder = new CsvBuilder();
        builder.addColumn(1);
        builder.addColumn(true);
        builder.addColumn("aaa");
        builder.addColumn("a,a;a");
        builder.addRow();
        String csv = builder.getCsv();
        Assert.assertEquals("1;true;aaa;\"a,a;a\"\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void addRowTest() {
        CsvBuilder builder = new CsvBuilder();
        builder.addColumn(1);
        builder.addColumn(2);
        builder.addRow();
        builder.addColumn(3);
        builder.addColumn(4);
        builder.addRow();
        String csv = builder.getCsv();
        Assert.assertEquals("1;2\r\n3;4\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void getCsvReusableTest() {
        CsvBuilder builder = new CsvBuilder();
        String csv = builder.getCsv();
        Assert.assertEquals("", csv);

        builder.addColumn(1);
        builder.addColumn(2);
        builder.addRow();
        csv = builder.getCsv();
        Assert.assertEquals("1;2\r\n", csv);

        csv = builder.getCsv();
        Assert.assertEquals("", csv);

        builder.addColumn(3);
        builder.addColumn(4);
        builder.addRow();
        csv = builder.getCsv();
        Assert.assertEquals("3;4\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void addEmptyRowTest() {
        CsvBuilder builder = new CsvBuilder();
        builder.addColumn(1);
        builder.addColumn(true);
        builder.addRow();
        builder.addRow();
        builder.addRow();
        builder.addColumn(2);
        builder.addColumn(false);
        builder.addRow();
        String csv = builder.getCsv();
        Assert.assertEquals("1;true\r\n\r\n\r\n2;false\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void tableCsvTest() {
        CsvBuilder builder = new CsvBuilder();
        builder.addColumn(1);
        builder.addColumn(true);
        builder.addRow();
        builder.addColumn(2.2f);
        builder.addColumn("aaa");
        builder.addColumn("a;a;a");
        builder.addColumn("");
        builder.addRow();
        builder.addColumn(false);
        builder.addRow();
        builder.addColumn(4L);
        builder.addColumn(10.01);
        builder.addRow();
        String csv = builder.getCsv();
        Assert.assertEquals("1;true\r\n2.2;aaa;\"a;a;a\";\r\nfalse\r\n4;10.01\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void changeSeparatorsTest() {
        CsvBuilder builder = new CsvBuilder(ColumnSeparators.COMMA, RowSeparators.LF);
        builder.addColumn(1);
        builder.addColumn(true);
        builder.addRow();
        builder.addColumn(2.2f);
        builder.addColumn("aaa");
        builder.addColumn("a;a;a");
        builder.addColumn("");
        builder.addRow();
        builder.addColumn(false);
        builder.addRow();
        builder.addColumn(4L);
        builder.addColumn(10.01);
        builder.addRow();
        String csv = builder.getCsv();
        Assert.assertEquals("1,true\n2.2,aaa,\"a;a;a\",\nfalse\n4,10.01\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void defaultSeparatorsTest() {
        CsvBuilder builder1 = new CsvBuilder(ColumnSeparators.SEMICOLON, RowSeparators.LF);
        builder1.addColumn(1);
        builder1.addColumn(true);
        builder1.addRow();
        builder1.addColumn(2);
        builder1.addColumn(false);
        builder1.addColumn("");
        builder1.addRow();
        String csv1 = builder1.getCsv();
        Assert.assertEquals("1;true\n2;false;\n", csv1);

        CsvBuilder builder2 = new CsvBuilder(ColumnSeparators.COMMA, RowSeparators.CRLF);
        builder2.addColumn(1);
        builder2.addColumn(true);
        builder2.addRow();
        builder2.addColumn(2);
        builder2.addColumn(false);
        builder2.addColumn("");
        builder2.addRow();
        String csv2 = builder2.getCsv();
        Assert.assertEquals("1,true\r\n2,false,\r\n", csv2);
    }

    /**
     * {@link CsvBuilder} class test.
     *
     * @throws IOException IO Exception.
     */
    @Test
    public void writeToTest() throws IOException {
        CsvBuilder builder = new CsvBuilder();
        builder.addColumn("");
        builder.addColumn("");
        builder.addRow();
        builder.addColumn("\n");
        builder.addColumn(",;");
        builder.addColumn(12);
        builder.addColumn("");
        builder.addRow();

        StringWriter writer = new StringWriter();
        builder.writeTo(writer);
        String csv = writer.toString();
        Assert.assertEquals(";\r\n\"\n\";\",;\";12;\r\n", csv);
    }

}
