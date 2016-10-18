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
        Assert.assertEquals("1,true,aaa\r\n", csv);
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
        Assert.assertEquals("1,true,aaa,\"a,a;a\"\r\n", csv);
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
        Assert.assertEquals("1,2\r\n3,4\r\n", csv);
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
        Assert.assertEquals("1,2\r\n", csv);

        csv = builder.getCsv();
        Assert.assertEquals("", csv);

        builder.addColumn(3);
        builder.addColumn(4);
        builder.addRow();
        csv = builder.getCsv();
        Assert.assertEquals("3,4\r\n", csv);
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
        Assert.assertEquals("1,true\r\n\r\n\r\n2,false\r\n", csv);
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
        Assert.assertEquals("1,true\r\n2.2,aaa,\"a;a;a\",\r\nfalse\r\n4,10.01\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void changeSeparatorsTest() {
        CsvBuilder builder = new CsvBuilder(ColumnSeparators.SEMICOLON, RowSeparators.LF);
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
        Assert.assertEquals("1;true\n2.2;aaa;\"a;a;a\";\nfalse\n4;10.01\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void changeColumnSeparatorTest() {
        CsvBuilder builder = new CsvBuilder(ColumnSeparators.SEMICOLON);
        builder.addColumn(1);
        builder.addColumn(true);
        builder.addRow();
        builder.addColumn(2);
        builder.addColumn(false);
        builder.addColumn("");
        builder.addRow();
        String csv2 = builder.getCsv();
        Assert.assertEquals("1;true\r\n2;false;\r\n", csv2);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void changeRowSeparatorTest() {
        CsvBuilder builder = new CsvBuilder(RowSeparators.LF);
        builder.addColumn(1);
        builder.addColumn(true);
        builder.addRow();
        builder.addColumn(2);
        builder.addColumn(false);
        builder.addColumn("");
        builder.addRow();
        String csv1 = builder.getCsv();
        Assert.assertEquals("1,true\n2,false,\n", csv1);
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
        Assert.assertEquals(",\r\n\"\n\",\",;\",12,\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void checkRectangularTest() {
        CsvBuilder builder = new CsvBuilder(true);
        builder.addColumn("1");
        builder.addColumn("2");
        builder.addRow();
        builder.addColumn("3");
        builder.addColumn("4");
        builder.addRow();
        String csv = builder.getCsv();
        Assert.assertEquals("1,2\r\n3,4\r\n", csv);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test
    public void checkRectangularReusableTest() {
        CsvBuilder builder = new CsvBuilder(true);
        builder.addColumn(1);
        builder.addColumn(2);
        builder.addColumn(3);
        builder.addRow();
        builder.addColumn(4);
        builder.addColumn(5);
        builder.addColumn(6);
        builder.addRow();
        String csv1 = builder.getCsv();
        Assert.assertEquals("1,2,3\r\n4,5,6\r\n", csv1);

        builder.addColumn(1);
        builder.addRow();
        builder.addColumn(2);
        builder.addRow();
        String csv2 = builder.getCsv();
        Assert.assertEquals("1\r\n2\r\n", csv2);
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void checkRectangularFailTest() {
        CsvBuilder builder = new CsvBuilder(true);
        builder.addColumn("1");
        builder.addColumn("2");
        builder.addRow();
        builder.addColumn("3");
        builder.addColumn("4");
        builder.addColumn("5");
        builder.addRow();
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void changeSeparatorsCheckRectangularFailTest() {
        CsvBuilder builder = new CsvBuilder(ColumnSeparators.SEMICOLON, RowSeparators.LF, true);
        builder.addColumn("1");
        builder.addColumn("2");
        builder.addRow();
        builder.addColumn("3");
        builder.addColumn("4");
        builder.addColumn("5");
        builder.addRow();
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void changeColumnSeparatorCheckRectangularFailTest() {
        CsvBuilder builder = new CsvBuilder(ColumnSeparators.SEMICOLON, true);
        builder.addColumn("1");
        builder.addColumn("2");
        builder.addRow();
        builder.addColumn("3");
        builder.addColumn("4");
        builder.addColumn("5");
        builder.addRow();
    }

    /**
     * {@link CsvBuilder} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void changeRowSeparatorCheckRectangularFailTest() {
        CsvBuilder builder = new CsvBuilder(RowSeparators.LF, true);
        builder.addColumn("1");
        builder.addColumn("2");
        builder.addRow();
        builder.addColumn("3");
        builder.addColumn("4");
        builder.addColumn("5");
        builder.addRow();
    }

}
