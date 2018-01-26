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
// along with this program. If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.csv;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link CsvPrinter}.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvPrinterTest {

    /**
     * Test class constructor.
     */
    public CsvPrinterTest() {
        super();
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addIntColumnTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        builder.addColumn(10);
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isEqualTo("10\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addLongColumnTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        builder.addColumn(Long.MAX_VALUE);
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("9223372036854775807\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addFloatColumnTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        builder.addColumn(1.1f);
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1.1\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addDoubleColumnTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        builder.addColumn(2.2);
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("2.2\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addBooleanColumnTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        builder.addColumn(true);
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("true\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addStringColumnTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        builder.addColumn("aaa");
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("aaa\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addNullStringColumnTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        String str = null;
        builder.addColumn(str);
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addStringWithSpecialsColumnTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        builder.addColumn("a\"a,a;a\ra\na");
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("\"a\"\"a,a;a\ra\na\"\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addObjectColumnTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        builder.addColumn(new StringBuilder().append("aaa"));
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("aaa\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addObjectStringColumnTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        Object obj = "aaa";
        builder.addColumn(obj);
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("aaa\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addNullObjectColumnTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        Object obj = null;
        builder.addColumn(obj);
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addMultipleColumnsTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        builder.addColumn(1);
        builder.addColumn(true);
        builder.addColumn("aaa");
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1,true,aaa\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addMultipleColumnsWithSpecialsTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        builder.addColumn("a\"a");
        builder.addRow();
        builder.addColumn("a,a");
        builder.addRow();
        builder.addColumn("a;a");
        builder.addRow();
        builder.addColumn("a\ra");
        builder.addRow();
        builder.addColumn("a\na");
        builder.addRow();
        builder.addColumn("a\"a,a;a\ra\na");
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("\"a\"\"a\"\r\n\"a,a\"\r\n\"a;a\"\r\n\"a\ra\"\r\n\"a\na\"\r\n\"a\"\"a,a;a\ra\na\"\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addRowTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        builder.addColumn(1);
        builder.addColumn(2);
        builder.addRow();
        builder.addColumn(3);
        builder.addColumn(4);
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1,2\r\n3,4\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addEmptyRowTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        builder.addColumn(1);
        builder.addColumn(true);
        builder.addRow();
        builder.addRow();
        builder.addRow();
        builder.addColumn(2);
        builder.addColumn(false);
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1,true\r\n\r\n\r\n2,false\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void tableCsvTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
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
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1,true\r\n2.2,aaa,\"a;a;a\",\r\nfalse\r\n4,10.01\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void chainedCallTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build();
        builder = builder.addColumn(1);
        builder = builder.addColumn(true);
        builder = builder.addRow();
        builder = builder.addColumn(2.2f);
        builder = builder.addColumn("aaa");
        builder = builder.addColumn(new StringBuilder("a;a;a"));
        builder = builder.addColumn("");
        builder = builder.addRow();
        builder = builder.addColumn(false);
        builder = builder.addRow();
        builder = builder.addColumn(4L);
        builder = builder.addColumn(10.01);
        builder = builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1,true\r\n2.2,aaa,\"a;a;a\",\r\nfalse\r\n4,10.01\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void checkRectangularTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build();
        builder.addColumn("1");
        builder.addColumn("2");
        builder.addRow();
        builder.addColumn("3");
        builder.addColumn("4");
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1,2\r\n3,4\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test(expected = WrongColumnCountException.class)
    public void checkRectangularFailTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build();
        builder.addColumn("1");
        builder.addColumn("2");
        builder.addRow();
        builder.addColumn("3");
        builder.addColumn("4");
        builder.addColumn("5");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void changeColumnSeparatorTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setSemicolonSeparator().build();
        builder.addColumn(1);
        builder.addColumn(true);
        builder.addRow();
        builder.addColumn(2);
        builder.addColumn(false);
        builder.addColumn("");
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1;true\r\n2;false;\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test(expected = WrongColumnCountException.class)
    public void changeColumnSeparatorCheckRectangularFailTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setColumnCountCheckEnabled(true).build();
        builder.addColumn("1");
        builder.addColumn("2");
        builder.addRow();
        builder.addColumn("3");
        builder.addColumn("4");
        builder.addColumn("5");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void changeRowSeparatorTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setLfSeparator().build();
        builder.addColumn(1);
        builder.addColumn(true);
        builder.addRow();
        builder.addColumn(2);
        builder.addColumn(false);
        builder.addColumn("");
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1,true\n2,false,\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test(expected = WrongColumnCountException.class)
    public void changeRowSeparatorCheckRectangularFailTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setLfSeparator().setColumnCountCheckEnabled(true).build();
        builder.addColumn("1");
        builder.addColumn("2");
        builder.addRow();
        builder.addColumn("3");
        builder.addColumn("4");
        builder.addColumn("5");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void changeSeparatorsTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setLfSeparator().build();
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
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1;true\n2.2;aaa;\"a;a;a\";\nfalse\n4;10.01\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test(expected = WrongColumnCountException.class)
    public void changeSeparatorsCheckRectangularFailTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setLfSeparator().setColumnCountCheckEnabled(true).build();
        builder.addColumn("1");
        builder.addColumn("2");
        builder.addRow();
        builder.addColumn("3");
        builder.addColumn("4");
        builder.addColumn("5");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void writerTest() {
        StringWriter writer = new StringWriter();
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build(writer);
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
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1,true\r\n2.2,aaa,\"a;a;a\",\r\nfalse\r\n4,10.01\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void writerCheckRectangularTest() {
        StringWriter writer = new StringWriter();
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build(writer);
        builder.addColumn("1");
        builder.addColumn("2");
        builder.addRow();
        builder.addColumn("3");
        builder.addColumn("4");
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1,2\r\n3,4\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test(expected = WrongColumnCountException.class)
    public void writerCheckRectangularFailTest() {
        StringWriter writer = new StringWriter();
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build(writer);
        builder.addColumn("1");
        builder.addColumn("2");
        builder.addRow();
        builder.addColumn("3");
        builder.addColumn("4");
        builder.addColumn("5");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void writerChangeColumnSeparatorTest() {
        StringWriter writer = new StringWriter();
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setSemicolonSeparator().build(writer);
        builder.addColumn(1);
        builder.addColumn(true);
        builder.addRow();
        builder.addColumn(2);
        builder.addColumn(false);
        builder.addColumn("");
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1;true\r\n2;false;\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test(expected = WrongColumnCountException.class)
    public void writerChangeColumnSeparatorCheckRectangularFailTest() {
        StringWriter writer = new StringWriter();
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setColumnCountCheckEnabled(true).build(writer);
        builder.addColumn("1");
        builder.addColumn("2");
        builder.addRow();
        builder.addColumn("3");
        builder.addColumn("4");
        builder.addColumn("5");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void writerChangeRowSeparatorTest() {
        StringWriter writer = new StringWriter();
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setLfSeparator().build(writer);
        builder.addColumn(1);
        builder.addColumn(true);
        builder.addRow();
        builder.addColumn(2);
        builder.addColumn(false);
        builder.addColumn("");
        builder.addRow();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1,true\n2,false,\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test(expected = WrongColumnCountException.class)
    public void writerChangeRowSeparatorCheckRectangularFailTest() {
        StringWriter writer = new StringWriter();
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setLfSeparator().setColumnCountCheckEnabled(true).build(writer);
        builder.addColumn("1");
        builder.addColumn("2");
        builder.addRow();
        builder.addColumn("3");
        builder.addColumn("4");
        builder.addColumn("5");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void writerChangeSeparatorsTest() {
        StringWriter writer = new StringWriter();
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setLfSeparator().build(writer);

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
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1;true\n2.2;aaa;\"a;a;a\";\nfalse\n4;10.01\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test(expected = WrongColumnCountException.class)
    public void writerChangeSeparatorsCheckRectangularFailTest() {
        StringWriter writer = new StringWriter();
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setLfSeparator().setColumnCountCheckEnabled(true).build(writer);
        builder.addColumn("1");
        builder.addColumn("2");
        builder.addRow();
        builder.addColumn("3");
        builder.addColumn("4");
        builder.addColumn("5");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test(expected = CsvIOException.class)
    public void writeColumnExceptionFailTest() {
        ErrorWriter writer = new ErrorWriter();
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build(writer);
        builder.addColumn("value");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test(expected = CsvIOException.class)
    public void writeRowExceptionFailTest() {
        ErrorWriter writer = new ErrorWriter();
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build(writer);
        builder.addRow();
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void getCsvForStreamTest() {
        ErrorWriter writer = new ErrorWriter();
        CsvPrinter builder = CsvPrinterBuilder.getInstance().build(writer);
        Assertions.assertThat(builder.getCsv()).isNull();
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void notRectangularExceptionMessageTest() {
        try {
            CsvPrinter builder = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build();
            builder.addColumn("1");
            builder.addColumn("2");
            builder.addColumn("3");
            builder.addRow();
            builder.addColumn("4");
            builder.addColumn("5");
            builder.addColumn("6");
            builder.addColumn("7");
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
        try {
            CsvPrinter builder = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build();
            builder.addColumn("1");
            builder.addColumn("2");
            builder.addColumn("3");
            builder.addRow();
            builder.addColumn("4");
            builder.addColumn("5");
            builder.addRow();
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void restoreFromNotRectangularExceptionTest() {
        CsvPrinter builder = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build();
        builder.addColumn(1);
        builder.addColumn(true);
        builder.addRow();
        builder.addColumn(2.2f);
        builder.addColumn("aaa");
        try {
            builder.addColumn("a;a;a");
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
        builder.addRow();
        builder.addColumn(5);
        try {
            builder.addRow();
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
        builder.addColumn(5L);
        builder.addRow();

        String csv = builder.getCsv();
        Assertions.assertThat(builder.getCsv()).isNotNull();
        Assertions.assertThat(builder.getCsv()).isEqualTo("1,true\r\n2.2,aaa\r\n5,5\r\n");
    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class ErrorWriter extends Writer {

        ErrorWriter() {
            super();
        }

        @Override
        public void write(final char[] cbuf, final int off, final int len) throws IOException {
            throw new IOException("ERROR");
        }

        @Override
        public void flush() throws IOException {
            // Ignore
        }

        @Override
        public void close() {
            // Ignore
        }

    }

}
