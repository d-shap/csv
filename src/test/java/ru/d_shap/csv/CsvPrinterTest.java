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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link CsvPrinter}.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvPrinterTest extends CsvTest {

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
    public void validateTest() {
        try {
            CsvPrinterConfiguration csvPrinterConfiguration = new CsvPrinterConfiguration();
            new CsvPrinter(new StringWriter(), csvPrinterConfiguration);
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No column separator is specified.");
        }
        try {
            CsvPrinterConfiguration csvPrinterConfiguration = new CsvPrinterConfiguration();
            csvPrinterConfiguration.setCommaSeparator();
            new CsvPrinter(new StringWriter(), csvPrinterConfiguration);
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongRowSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No row separator is specified.");
        }
        try {
            CsvPrinterConfiguration csvPrinterConfiguration = new CsvPrinterConfiguration();
            csvPrinterConfiguration.setCrLfSeparator();
            new CsvPrinter(new StringWriter(), csvPrinterConfiguration);
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No column separator is specified.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addColumnCharTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build();
        csvPrinter.addColumn('a');
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("a\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addColumnIntTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build();
        csvPrinter.addColumn(10);
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("10\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addColumnLongTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build();
        csvPrinter.addColumn(Long.MAX_VALUE);
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("9223372036854775807\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addColumnFloatTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build();
        csvPrinter.addColumn(1.1f);
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1.1\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addColumnDoubleTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build();
        csvPrinter.addColumn(2.2);
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("2.2\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addColumnBooleanTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build();
        csvPrinter.addColumn(true);
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("true\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addColumnStringTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build();
        csvPrinter.addColumn("value");
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("value\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addColumnNullStringTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build();
        csvPrinter.addColumn(null);
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addColumnObjectTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build();
        csvPrinter.addColumn(new StringBuilder().append("value"));
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("value\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addColumnStringObjectTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build();
        csvPrinter.addColumn((Object) "value");
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("value\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addColumnNullObjectTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build();
        csvPrinter.addColumn((Object) null);
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addColumnStringWithSpecialCharactersTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build();
        csvPrinter.addColumn("a\"a,a;a\ra\na");
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("\"a\"\"a,a;a\ra\na\"\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addMultipleColumnsTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build();
        csvPrinter.addColumn(1);
        csvPrinter.addColumn(true);
        csvPrinter.addColumn("value");
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1,true,value\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addMultipleColumnsWithSpecialCharactersTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build();
        csvPrinter.addColumn("a\"a");
        csvPrinter.addRow();
        csvPrinter.addColumn("a,a");
        csvPrinter.addRow();
        csvPrinter.addColumn("a;a");
        csvPrinter.addRow();
        csvPrinter.addColumn("a\ra");
        csvPrinter.addRow();
        csvPrinter.addColumn("a\na");
        csvPrinter.addRow();
        csvPrinter.addColumn("a\"a,a;a\ra\na");
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("\"a\"\"a\"\r\n\"a,a\"\r\n\"a;a\"\r\n\"a\ra\"\r\n\"a\na\"\r\n\"a\"\"a,a;a\ra\na\"\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addRowTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build();
        csvPrinter.addColumn(1);
        csvPrinter.addColumn(2);
        csvPrinter.addRow();
        csvPrinter.addColumn(3);
        csvPrinter.addColumn(4);
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1,2\r\n3,4\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addEmptyRowWithNoSkipEmptyRowsTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setSkipEmptyRowsEnabled(false).build();
        csvPrinter.addColumn(1);
        csvPrinter.addColumn(true);
        csvPrinter.addRow();
        csvPrinter.addRow();
        csvPrinter.addRow();
        csvPrinter.addColumn(2);
        csvPrinter.addColumn(false);
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1,true\r\n\r\n\r\n2,false\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addEmptyRowWithSkipEmptyRowsTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setSkipEmptyRowsEnabled(true).build();
        csvPrinter.addColumn(1);
        csvPrinter.addColumn(true);
        csvPrinter.addRow();
        csvPrinter.addRow();
        csvPrinter.addRow();
        csvPrinter.addColumn(2);
        csvPrinter.addColumn(false);
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1,true\r\n2,false\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvWithNoColumnCountCheckTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(false).build();
        csvPrinter.addColumn(1);
        csvPrinter.addColumn(true);
        csvPrinter.addRow();
        csvPrinter.addColumn(2.2f);
        csvPrinter.addColumn("aaa");
        csvPrinter.addColumn("a;a;a");
        csvPrinter.addColumn("");
        csvPrinter.addRow();
        csvPrinter.addColumn(false);
        csvPrinter.addRow();
        csvPrinter.addColumn(4L);
        csvPrinter.addColumn(10.01);
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1,true\r\n2.2,aaa,\"a;a;a\",\r\nfalse\r\n4,10.01\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvWithColumnCountCheckTest() {
        try {
            CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build();
            csvPrinter.addColumn(1);
            csvPrinter.addColumn(true);
            csvPrinter.addRow();
            csvPrinter.addColumn(2.2f);
            csvPrinter.addColumn("aaa");
            csvPrinter.addColumn("a;a;a");
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
        try {
            CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build();
            csvPrinter.addColumn(1);
            csvPrinter.addColumn(true);
            csvPrinter.addRow();
            csvPrinter.addColumn(2.2f);
            csvPrinter.addRow();
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvWithCommaAndCrAndNoColumnCountCheckTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setCommaSeparator().setCrSeparator().setColumnCountCheckEnabled(false).build();
        csvPrinter.addColumn(1);
        csvPrinter.addColumn(true);
        csvPrinter.addRow();
        csvPrinter.addColumn(2);
        csvPrinter.addColumn(false);
        csvPrinter.addColumn("");
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1,true\r2,false,\r");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvWithCommaAndCrAndColumnCountCheckTest() {
        try {
            CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setCommaSeparator().setCrSeparator().setColumnCountCheckEnabled(true).build();
            csvPrinter.addColumn("1");
            csvPrinter.addColumn("2");
            csvPrinter.addRow();
            csvPrinter.addColumn("3");
            csvPrinter.addColumn("4");
            csvPrinter.addColumn("5");
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvWithSemicolonAndCrAndNoColumnCountCheckTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setCrSeparator().setColumnCountCheckEnabled(false).build();
        csvPrinter.addColumn(1);
        csvPrinter.addColumn(true);
        csvPrinter.addRow();
        csvPrinter.addColumn(2);
        csvPrinter.addColumn(false);
        csvPrinter.addColumn("");
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1;true\r2;false;\r");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvWithSemicolonAndCrAndColumnCountCheckTest() {
        try {
            CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setCrSeparator().setColumnCountCheckEnabled(true).build();
            csvPrinter.addColumn("1");
            csvPrinter.addColumn("2");
            csvPrinter.addRow();
            csvPrinter.addColumn("3");
            csvPrinter.addColumn("4");
            csvPrinter.addColumn("5");
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvWithCommaAndLfAndNoColumnCountCheckTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setCommaSeparator().setLfSeparator().setColumnCountCheckEnabled(false).build();
        csvPrinter.addColumn(1);
        csvPrinter.addColumn(true);
        csvPrinter.addRow();
        csvPrinter.addColumn(2);
        csvPrinter.addColumn(false);
        csvPrinter.addColumn("");
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1,true\n2,false,\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvWithCommaAndLfAndColumnCountCheckTest() {
        try {
            CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setCommaSeparator().setLfSeparator().setColumnCountCheckEnabled(true).build();
            csvPrinter.addColumn("1");
            csvPrinter.addColumn("2");
            csvPrinter.addRow();
            csvPrinter.addColumn("3");
            csvPrinter.addColumn("4");
            csvPrinter.addColumn("5");
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvWithSemicolonAndLfAndNoColumnCountCheckTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setLfSeparator().setColumnCountCheckEnabled(false).build();
        csvPrinter.addColumn(1);
        csvPrinter.addColumn(true);
        csvPrinter.addRow();
        csvPrinter.addColumn(2);
        csvPrinter.addColumn(false);
        csvPrinter.addColumn("");
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1;true\n2;false;\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvWithSemicolonAndLfAndColumnCountCheckTest() {
        try {
            CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setLfSeparator().setColumnCountCheckEnabled(true).build();
            csvPrinter.addColumn("1");
            csvPrinter.addColumn("2");
            csvPrinter.addRow();
            csvPrinter.addColumn("3");
            csvPrinter.addColumn("4");
            csvPrinter.addColumn("5");
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvWithCommaAndCrLfAndNoColumnCountCheckTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setCommaSeparator().setCrLfSeparator().setColumnCountCheckEnabled(false).build();
        csvPrinter.addColumn(1);
        csvPrinter.addColumn(true);
        csvPrinter.addRow();
        csvPrinter.addColumn(2);
        csvPrinter.addColumn(false);
        csvPrinter.addColumn("");
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1,true\r\n2,false,\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvWithCommaAndCrLfAndColumnCountCheckTest() {
        try {
            CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setCommaSeparator().setCrLfSeparator().setColumnCountCheckEnabled(true).build();
            csvPrinter.addColumn("1");
            csvPrinter.addColumn("2");
            csvPrinter.addRow();
            csvPrinter.addColumn("3");
            csvPrinter.addColumn("4");
            csvPrinter.addColumn("5");
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvWithSemicolonAndCrLfAndNoColumnCountCheckTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setCrLfSeparator().setColumnCountCheckEnabled(false).build();
        csvPrinter.addColumn(1);
        csvPrinter.addColumn(true);
        csvPrinter.addRow();
        csvPrinter.addColumn(2);
        csvPrinter.addColumn(false);
        csvPrinter.addColumn("");
        csvPrinter.addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1;true\r\n2;false;\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvWithSemicolonAndCrLfAndColumnCountCheckTest() {
        try {
            CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setCrLfSeparator().setColumnCountCheckEnabled(true).build();
            csvPrinter.addColumn("1");
            csvPrinter.addColumn("2");
            csvPrinter.addRow();
            csvPrinter.addColumn("3");
            csvPrinter.addColumn("4");
            csvPrinter.addColumn("5");
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvToWriterWithNoColumnCountCheckTest() {
        try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(false).build(new StringWriter())) {
            csvPrinter.addColumn(1);
            csvPrinter.addColumn(true);
            csvPrinter.addRow();
            csvPrinter.addColumn(2.2f);
            csvPrinter.addColumn("aaa");
            csvPrinter.addColumn("a;a;a");
            csvPrinter.addColumn("");
            csvPrinter.addRow();
            csvPrinter.addColumn(false);
            csvPrinter.addRow();
            csvPrinter.addColumn(4L);
            csvPrinter.addColumn(10.01);
            csvPrinter.addRow();
            Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
            Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1,true\r\n2.2,aaa,\"a;a;a\",\r\nfalse\r\n4,10.01\r\n");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvToWriterWithColumnCountCheckTest() {
        try {
            try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build(new StringWriter())) {
                csvPrinter.addColumn(1);
                csvPrinter.addColumn(true);
                csvPrinter.addRow();
                csvPrinter.addColumn(2.2f);
                csvPrinter.addColumn("aaa");
                csvPrinter.addColumn("a;a;a");
            }
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
        try {
            try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build(new StringWriter())) {
                csvPrinter.addColumn(1);
                csvPrinter.addColumn(true);
                csvPrinter.addRow();
                csvPrinter.addColumn(2.2f);
                csvPrinter.addRow();
            }
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvToWriterWithCommaAndCrAndNoColumnCountCheckTest() {
        try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setCommaSeparator().setCrSeparator().setColumnCountCheckEnabled(false).build(new StringWriter())) {
            csvPrinter.addColumn(1);
            csvPrinter.addColumn(true);
            csvPrinter.addRow();
            csvPrinter.addColumn(2);
            csvPrinter.addColumn(false);
            csvPrinter.addColumn("");
            csvPrinter.addRow();
            Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
            Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1,true\r2,false,\r");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvToWriterWithCommaAndCrAndColumnCountCheckTest() {
        try {
            try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setCommaSeparator().setCrSeparator().setColumnCountCheckEnabled(true).build(new StringWriter())) {
                csvPrinter.addColumn("1");
                csvPrinter.addColumn("2");
                csvPrinter.addRow();
                csvPrinter.addColumn("3");
                csvPrinter.addColumn("4");
                csvPrinter.addColumn("5");
            }
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvToWriterWithSemicolonAndCrAndNoColumnCountCheckTest() {
        try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setCrSeparator().setColumnCountCheckEnabled(false).build(new StringWriter())) {
            csvPrinter.addColumn(1);
            csvPrinter.addColumn(true);
            csvPrinter.addRow();
            csvPrinter.addColumn(2);
            csvPrinter.addColumn(false);
            csvPrinter.addColumn("");
            csvPrinter.addRow();
            Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
            Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1;true\r2;false;\r");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvToWriterWithSemicolonAndCrAndColumnCountCheckTest() {
        try {
            try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setCrSeparator().setColumnCountCheckEnabled(true).build(new StringWriter())) {
                csvPrinter.addColumn("1");
                csvPrinter.addColumn("2");
                csvPrinter.addRow();
                csvPrinter.addColumn("3");
                csvPrinter.addColumn("4");
                csvPrinter.addColumn("5");
            }
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvToWriterWithCommaAndLfAndNoColumnCountCheckTest() {
        try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setCommaSeparator().setLfSeparator().setColumnCountCheckEnabled(false).build(new StringWriter())) {
            csvPrinter.addColumn(1);
            csvPrinter.addColumn(true);
            csvPrinter.addRow();
            csvPrinter.addColumn(2);
            csvPrinter.addColumn(false);
            csvPrinter.addColumn("");
            csvPrinter.addRow();
            Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
            Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1,true\n2,false,\n");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvToWriterWithCommaAndLfAndColumnCountCheckTest() {
        try {
            try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setCommaSeparator().setLfSeparator().setColumnCountCheckEnabled(true).build(new StringWriter())) {
                csvPrinter.addColumn("1");
                csvPrinter.addColumn("2");
                csvPrinter.addRow();
                csvPrinter.addColumn("3");
                csvPrinter.addColumn("4");
                csvPrinter.addColumn("5");
            }
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvToWriterWithSemicolonAndLfAndNoColumnCountCheckTest() {
        try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setLfSeparator().setColumnCountCheckEnabled(false).build(new StringWriter())) {
            csvPrinter.addColumn(1);
            csvPrinter.addColumn(true);
            csvPrinter.addRow();
            csvPrinter.addColumn(2);
            csvPrinter.addColumn(false);
            csvPrinter.addColumn("");
            csvPrinter.addRow();
            Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
            Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1;true\n2;false;\n");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvToWriterWithSemicolonAndLfAndColumnCountCheckTest() {
        try {
            try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setLfSeparator().setColumnCountCheckEnabled(true).build(new StringWriter())) {
                csvPrinter.addColumn("1");
                csvPrinter.addColumn("2");
                csvPrinter.addRow();
                csvPrinter.addColumn("3");
                csvPrinter.addColumn("4");
                csvPrinter.addColumn("5");
            }
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvToWriterWithCommaAndCrLfAndNoColumnCountCheckTest() {
        try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setCommaSeparator().setCrLfSeparator().setColumnCountCheckEnabled(false).build(new StringWriter())) {
            csvPrinter.addColumn(1);
            csvPrinter.addColumn(true);
            csvPrinter.addRow();
            csvPrinter.addColumn(2);
            csvPrinter.addColumn(false);
            csvPrinter.addColumn("");
            csvPrinter.addRow();
            Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
            Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1,true\r\n2,false,\r\n");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvToWriterWithCommaAndCrLfAndColumnCountCheckTest() {
        try {
            try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setCommaSeparator().setCrLfSeparator().setColumnCountCheckEnabled(true).build(new StringWriter())) {
                csvPrinter.addColumn("1");
                csvPrinter.addColumn("2");
                csvPrinter.addRow();
                csvPrinter.addColumn("3");
                csvPrinter.addColumn("4");
                csvPrinter.addColumn("5");
            }
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvToWriterWithSemicolonAndCrLfAndNoColumnCountCheckTest() {
        try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setCrLfSeparator().setColumnCountCheckEnabled(false).build(new StringWriter())) {
            csvPrinter.addColumn(1);
            csvPrinter.addColumn(true);
            csvPrinter.addRow();
            csvPrinter.addColumn(2);
            csvPrinter.addColumn(false);
            csvPrinter.addColumn("");
            csvPrinter.addRow();
            Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
            Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1;true\r\n2;false;\r\n");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvToWriterWithSemicolonAndCrLfAndColumnCountCheckTest() {
        try {
            try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setCrLfSeparator().setColumnCountCheckEnabled(true).build(new StringWriter())) {
                csvPrinter.addColumn("1");
                csvPrinter.addColumn("2");
                csvPrinter.addRow();
                csvPrinter.addColumn("3");
                csvPrinter.addColumn("4");
                csvPrinter.addColumn("5");
            }
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addSpecialCharactersWithNoEscapeAllSeparatorsEnabledTest() {
        CsvPrinter csvPrinter1 = CsvPrinterBuilder.getInstance().setCommaSeparator().setCrSeparator().setEscapeAllSeparatorsEnabled(false).build();
        csvPrinter1.addColumn(" , ").addRow();
        csvPrinter1.addColumn(" ; ").addRow();
        csvPrinter1.addColumn(" \r ").addRow();
        csvPrinter1.addColumn(" \n ").addRow();
        csvPrinter1.addColumn(" \r\n ").addRow();
        csvPrinter1.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter1.getCsv()).isEqualTo("\" , \"\r ; \r\" \r \"\r \n \r\" \r\n \"\r\" \"\" \"\r");

        CsvPrinter csvPrinter2 = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setCrSeparator().setEscapeAllSeparatorsEnabled(false).build();
        csvPrinter2.addColumn(" , ").addRow();
        csvPrinter2.addColumn(" ; ").addRow();
        csvPrinter2.addColumn(" \r ").addRow();
        csvPrinter2.addColumn(" \n ").addRow();
        csvPrinter2.addColumn(" \r\n ").addRow();
        csvPrinter2.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter2.getCsv()).isEqualTo(" , \r\" ; \"\r\" \r \"\r \n \r\" \r\n \"\r\" \"\" \"\r");

        CsvPrinter csvPrinter3 = CsvPrinterBuilder.getInstance().setCommaSeparator().setLfSeparator().setEscapeAllSeparatorsEnabled(false).build();
        csvPrinter3.addColumn(" , ").addRow();
        csvPrinter3.addColumn(" ; ").addRow();
        csvPrinter3.addColumn(" \r ").addRow();
        csvPrinter3.addColumn(" \n ").addRow();
        csvPrinter3.addColumn(" \r\n ").addRow();
        csvPrinter3.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter3.getCsv()).isEqualTo("\" , \"\n ; \n \r \n\" \n \"\n\" \r\n \"\n\" \"\" \"\n");

        CsvPrinter csvPrinter4 = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setLfSeparator().setEscapeAllSeparatorsEnabled(false).build();
        csvPrinter4.addColumn(" , ").addRow();
        csvPrinter4.addColumn(" ; ").addRow();
        csvPrinter4.addColumn(" \r ").addRow();
        csvPrinter4.addColumn(" \n ").addRow();
        csvPrinter4.addColumn(" \r\n ").addRow();
        csvPrinter4.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter4.getCsv()).isEqualTo(" , \n\" ; \"\n \r \n\" \n \"\n\" \r\n \"\n\" \"\" \"\n");

        CsvPrinter csvPrinter5 = CsvPrinterBuilder.getInstance().setCommaSeparator().setCrLfSeparator().setEscapeAllSeparatorsEnabled(false).build();
        csvPrinter5.addColumn(" , ").addRow();
        csvPrinter5.addColumn(" ; ").addRow();
        csvPrinter5.addColumn(" \r ").addRow();
        csvPrinter5.addColumn(" \n ").addRow();
        csvPrinter5.addColumn(" \r\n ").addRow();
        csvPrinter5.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter5.getCsv()).isEqualTo("\" , \"\r\n ; \r\n \r \r\n \n \r\n\" \r\n \"\r\n\" \"\" \"\r\n");

        CsvPrinter csvPrinter6 = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setCrLfSeparator().setEscapeAllSeparatorsEnabled(false).build();
        csvPrinter6.addColumn(" , ").addRow();
        csvPrinter6.addColumn(" ; ").addRow();
        csvPrinter6.addColumn(" \r ").addRow();
        csvPrinter6.addColumn(" \n ").addRow();
        csvPrinter6.addColumn(" \r\n ").addRow();
        csvPrinter6.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter6.getCsv()).isEqualTo(" , \r\n\" ; \"\r\n \r \r\n \n \r\n\" \r\n \"\r\n\" \"\" \"\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addSpecialCharactersWithEscapeAllSeparatorsEnabledTest() {
        CsvPrinter csvPrinter1 = CsvPrinterBuilder.getInstance().setCommaSeparator().setCrSeparator().setEscapeAllSeparatorsEnabled(true).build();
        csvPrinter1.addColumn(" , ").addRow();
        csvPrinter1.addColumn(" ; ").addRow();
        csvPrinter1.addColumn(" \r ").addRow();
        csvPrinter1.addColumn(" \n ").addRow();
        csvPrinter1.addColumn(" \r\n ").addRow();
        csvPrinter1.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter1.getCsv()).isEqualTo("\" , \"\r\" ; \"\r\" \r \"\r\" \n \"\r\" \r\n \"\r\" \"\" \"\r");

        CsvPrinter csvPrinter2 = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setCrSeparator().setEscapeAllSeparatorsEnabled(true).build();
        csvPrinter2.addColumn(" , ").addRow();
        csvPrinter2.addColumn(" ; ").addRow();
        csvPrinter2.addColumn(" \r ").addRow();
        csvPrinter2.addColumn(" \n ").addRow();
        csvPrinter2.addColumn(" \r\n ").addRow();
        csvPrinter2.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter2.getCsv()).isEqualTo("\" , \"\r\" ; \"\r\" \r \"\r\" \n \"\r\" \r\n \"\r\" \"\" \"\r");

        CsvPrinter csvPrinter3 = CsvPrinterBuilder.getInstance().setCommaSeparator().setLfSeparator().setEscapeAllSeparatorsEnabled(true).build();
        csvPrinter3.addColumn(" , ").addRow();
        csvPrinter3.addColumn(" ; ").addRow();
        csvPrinter3.addColumn(" \r ").addRow();
        csvPrinter3.addColumn(" \n ").addRow();
        csvPrinter3.addColumn(" \r\n ").addRow();
        csvPrinter3.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter3.getCsv()).isEqualTo("\" , \"\n\" ; \"\n\" \r \"\n\" \n \"\n\" \r\n \"\n\" \"\" \"\n");

        CsvPrinter csvPrinter4 = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setLfSeparator().setEscapeAllSeparatorsEnabled(true).build();
        csvPrinter4.addColumn(" , ").addRow();
        csvPrinter4.addColumn(" ; ").addRow();
        csvPrinter4.addColumn(" \r ").addRow();
        csvPrinter4.addColumn(" \n ").addRow();
        csvPrinter4.addColumn(" \r\n ").addRow();
        csvPrinter4.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter4.getCsv()).isEqualTo("\" , \"\n\" ; \"\n\" \r \"\n\" \n \"\n\" \r\n \"\n\" \"\" \"\n");

        CsvPrinter csvPrinter5 = CsvPrinterBuilder.getInstance().setCommaSeparator().setCrLfSeparator().setEscapeAllSeparatorsEnabled(true).build();
        csvPrinter5.addColumn(" , ").addRow();
        csvPrinter5.addColumn(" ; ").addRow();
        csvPrinter5.addColumn(" \r ").addRow();
        csvPrinter5.addColumn(" \n ").addRow();
        csvPrinter5.addColumn(" \r\n ").addRow();
        csvPrinter5.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter5.getCsv()).isEqualTo("\" , \"\r\n\" ; \"\r\n\" \r \"\r\n\" \n \"\r\n\" \r\n \"\r\n\" \"\" \"\r\n");

        CsvPrinter csvPrinter6 = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setCrLfSeparator().setEscapeAllSeparatorsEnabled(true).build();
        csvPrinter6.addColumn(" , ").addRow();
        csvPrinter6.addColumn(" ; ").addRow();
        csvPrinter6.addColumn(" \r ").addRow();
        csvPrinter6.addColumn(" \n ").addRow();
        csvPrinter6.addColumn(" \r\n ").addRow();
        csvPrinter6.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter6.getCsv()).isEqualTo("\" , \"\r\n\" ; \"\r\n\" \r \"\r\n\" \n \"\r\n\" \r\n \"\r\n\" \"\" \"\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void writeColumnToErrorWriterTest() {
        try {
            ErrorOnWriteWriter writer = new ErrorOnWriteWriter();
            CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build(writer);
            csvPrinter.addColumn("value");
            Assertions.fail("CsvPrinter test fail");
        } catch (CsvIOException ex) {
            Assertions.assertThat(ex).hasMessage("ERROR");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void writeRowToErrorWriterTest() {
        try {
            ErrorOnWriteWriter writer = new ErrorOnWriteWriter();
            CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build(writer);
            csvPrinter.addRow();
            Assertions.fail("CsvPrinter test fail");
        } catch (CsvIOException ex) {
            Assertions.assertThat(ex).hasMessage("ERROR");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void getNullCsvForStreamTest() {
        ErrorOnWriteWriter writer = new ErrorOnWriteWriter();
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build(writer);
        Assertions.assertThat(csvPrinter.getCsv()).isNull();
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void restoreFromWrongColumnCountExceptionTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build();
        csvPrinter.addColumn(1);
        csvPrinter.addColumn(true);
        csvPrinter.addRow();
        csvPrinter.addColumn(2.2f);
        csvPrinter.addColumn("aaa");
        try {
            csvPrinter.addColumn("a;a;a");
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
        csvPrinter.addRow();
        csvPrinter.addColumn(5);
        try {
            csvPrinter.addRow();
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
        csvPrinter.addColumn(5L);
        csvPrinter.addRow();

        String csv = csvPrinter.getCsv();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1,true\r\n2.2,aaa\r\n5,5\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addRowWithListAndNoColumnCountCheckTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(false).build();
        csvPrinter.addRow(Arrays.asList("a", "b", "c"));
        csvPrinter.addRow(Arrays.asList("d", "e"));
        csvPrinter.addRow(Arrays.asList("f", "g", "h", "i"));
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("a,b,c\r\nd,e\r\nf,g,h,i\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addRowWithListAndColumnCountCheckTest() {
        try {
            CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build();
            csvPrinter.addRow(Arrays.asList("a", "b", "c"));
            csvPrinter.addRow(Arrays.asList("d", "e"));
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
        try {
            CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build();
            csvPrinter.addRow(Arrays.asList("a", "b", "c"));
            csvPrinter.addRow(Arrays.asList("d", "e", "f", "g"));
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addRowsWithListAndNoColumnCountCheckTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(false).build();
        List<List<String>> rows1 = new ArrayList<>();
        rows1.add(Arrays.asList("a", "b", "c"));
        rows1.add(Arrays.asList("d", "e"));
        csvPrinter.addRows(rows1);
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("a,b,c\r\nd,e\r\n");

        List<List<String>> rows2 = new ArrayList<>();
        rows2.add(Arrays.asList("f", "g", "h", "i"));
        csvPrinter.addRows(rows2);
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("a,b,c\r\nd,e\r\nf,g,h,i\r\n");
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addRowsWithListAndColumnCountCheckTest() {
        try {
            CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build();
            List<List<String>> rows = new ArrayList<>();
            rows.add(Arrays.asList("a", "b", "c"));
            rows.add(Arrays.asList("d", "e"));
            csvPrinter.addRows(rows);
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
        try {
            CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).build();
            List<List<String>> rows = new ArrayList<>();
            rows.add(Arrays.asList("a", "b", "c"));
            rows.add(Arrays.asList("d", "e", "f", "g"));
            csvPrinter.addRows(rows);
            Assertions.fail("CsvPrinter test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void closeWriterTest() {
        IsClosedWriter writer = new IsClosedWriter();
        Assertions.assertThat(writer.isClosed()).isFalse();
        try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build(writer)) {
            csvPrinter.addColumn("1234567890").addRow();
        }
        Assertions.assertThat(writer.isClosed()).isTrue();
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void errorOnCloseWriterTest() {
        try {
            ErrorOnCloseWriter writer = new ErrorOnCloseWriter();
            try (CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().build(writer)) {
                csvPrinter.addColumn("1234567890").addRow();
            }
            Assertions.fail("CsvPrinter test fail");
        } catch (CsvIOException ex) {
            Assertions.assertThat(ex).hasMessage("ERROR");
        }
    }

    /**
     * {@link CsvPrinter} class test.
     */
    @Test
    public void addCsvWithChainedCallTest() {
        CsvPrinter csvPrinter = CsvPrinterBuilder.getInstance().setSkipEmptyRowsEnabled(true).build();
        csvPrinter = csvPrinter.addColumn('z');
        csvPrinter = csvPrinter.addColumn(1);
        csvPrinter = csvPrinter.addColumn(2L);
        csvPrinter = csvPrinter.addColumn(1.1f);
        csvPrinter = csvPrinter.addColumn(2.2);
        csvPrinter = csvPrinter.addColumn(true);
        csvPrinter = csvPrinter.addColumn("value1");
        csvPrinter = csvPrinter.addColumn(new StringBuilder().append("value2"));
        csvPrinter = csvPrinter.addRow();
        csvPrinter = csvPrinter.addRow();
        csvPrinter = csvPrinter.addRow(Arrays.asList("val1", "val2", "val3"));
        List<List<String>> rows = new ArrayList<>();
        rows.add(Arrays.asList("val11", "val12", "val13"));
        rows.add(Arrays.asList("val21", "val22", "val23"));
        rows.add(Arrays.asList("val31", "val32", "val33"));
        csvPrinter = csvPrinter.addRows(rows);
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("z,1,2,1.1,2.2,true,value1,value2\r\nval1,val2,val3\r\nval11,val12,val13\r\nval21,val22,val23\r\nval31,val32,val33\r\n");
    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class ErrorOnWriteWriter extends Writer {

        ErrorOnWriteWriter() {
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
        public void close() throws IOException {
            // Ignore
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class IsClosedWriter extends Writer {

        private boolean _closed;

        IsClosedWriter() {
            super();
            _closed = false;
        }

        @Override
        public void write(final char[] cbuf, final int off, final int len) throws IOException {
            // Ignore
        }

        @Override
        public void flush() throws IOException {
            // Ignore
        }

        @Override
        public void close() throws IOException {
            _closed = true;
        }

        boolean isClosed() {
            return _closed;
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class ErrorOnCloseWriter extends Writer {

        ErrorOnCloseWriter() {
            super();
        }

        @Override
        public void write(final char[] cbuf, final int off, final int len) throws IOException {
            // Ignore
        }

        @Override
        public void flush() throws IOException {
            // Ignore
        }

        @Override
        public void close() throws IOException {
            throw new IOException("ERROR");
        }

    }

}
