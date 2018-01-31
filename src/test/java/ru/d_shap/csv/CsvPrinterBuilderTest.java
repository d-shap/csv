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

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link CsvPrinterBuilder}.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvPrinterBuilderTest extends CsvTest {

    /**
     * Test class constructor.
     */
    public CsvPrinterBuilderTest() {
        super();
    }

    /**
     * {@link CsvPrinterBuilder} class test.
     */
    @Test
    public void setFormatDefaultTest() {
        CsvPrinter csvPrinter11 = CsvPrinterBuilder.getInstance().setCommaSeparator().setCrLfSeparator().setFormat(CsvFormat.DEFAULT).build();
        csvPrinter11.addColumn("a").addColumn("b").addRow();
        csvPrinter11.addColumn("c").addColumn("d").addRow();
        Assertions.assertThat(csvPrinter11.getCsv()).isEqualTo("a,b\r\nc,d\r\n");

        CsvPrinter csvPrinter12 = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setLfSeparator().setFormat(CsvFormat.DEFAULT).build();
        csvPrinter12.addColumn("a").addColumn("b").addRow();
        csvPrinter12.addColumn("c").addColumn("d").addRow();
        Assertions.assertThat(csvPrinter12.getCsv()).isEqualTo("a,b\r\nc,d\r\n");

        CsvPrinter csvPrinter21 = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(false).setFormat(CsvFormat.DEFAULT).build();
        csvPrinter21.addColumn("a").addColumn("b").addColumn("c").addRow();
        csvPrinter21.addColumn("d").addColumn("e").addRow();
        Assertions.assertThat(csvPrinter21.getCsv()).isEqualTo("a,b,c\r\nd,e\r\n");

        CsvPrinter csvPrinter22 = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).setFormat(CsvFormat.DEFAULT).build();
        csvPrinter22.addColumn("a").addColumn("b").addColumn("c").addRow();
        csvPrinter22.addColumn("d").addColumn("e").addRow();
        Assertions.assertThat(csvPrinter22.getCsv()).isEqualTo("a,b,c\r\nd,e\r\n");

        CsvPrinter csvPrinter31 = CsvPrinterBuilder.getInstance().setSkipEmptyRowsEnabled(false).setFormat(CsvFormat.DEFAULT).build();
        csvPrinter31.addColumn("a").addRow();
        csvPrinter31.addRow();
        csvPrinter31.addColumn("b").addRow();
        csvPrinter31.addRow();
        csvPrinter31.addColumn("c").addRow();
        Assertions.assertThat(csvPrinter31.getCsv()).isEqualTo("a\r\n\r\nb\r\n\r\nc\r\n");

        CsvPrinter csvPrinter32 = CsvPrinterBuilder.getInstance().setSkipEmptyRowsEnabled(true).setFormat(CsvFormat.DEFAULT).build();
        csvPrinter32.addColumn("a").addRow();
        csvPrinter32.addRow();
        csvPrinter32.addColumn("b").addRow();
        csvPrinter32.addRow();
        csvPrinter32.addColumn("c").addRow();
        Assertions.assertThat(csvPrinter32.getCsv()).isEqualTo("a\r\n\r\nb\r\n\r\nc\r\n");

        CsvPrinter csvPrinter41 = CsvPrinterBuilder.getInstance().setEscapeAllSeparatorsEnabled(true).setFormat(CsvFormat.DEFAULT).build();
        csvPrinter41.addColumn(" , ").addRow();
        csvPrinter41.addColumn(" ; ").addRow();
        csvPrinter41.addColumn(" \r ").addRow();
        csvPrinter41.addColumn(" \n ").addRow();
        csvPrinter41.addColumn(" \r\n ").addRow();
        csvPrinter41.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter41.getCsv()).isEqualTo("\" , \"\r\n\" ; \"\r\n\" \r \"\r\n\" \n \"\r\n\" \r\n \"\r\n\" \"\" \"\r\n");

        CsvPrinter csvPrinter42 = CsvPrinterBuilder.getInstance().setEscapeAllSeparatorsEnabled(false).setFormat(CsvFormat.DEFAULT).build();
        csvPrinter42.addColumn(" , ").addRow();
        csvPrinter42.addColumn(" ; ").addRow();
        csvPrinter42.addColumn(" \r ").addRow();
        csvPrinter42.addColumn(" \n ").addRow();
        csvPrinter42.addColumn(" \r\n ").addRow();
        csvPrinter42.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter42.getCsv()).isEqualTo("\" , \"\r\n\" ; \"\r\n\" \r \"\r\n\" \n \"\r\n\" \r\n \"\r\n\" \"\" \"\r\n");
    }

    /**
     * {@link CsvPrinterBuilder} class test.
     */
    @Test
    public void setFormatRfc4180Test() {
        CsvPrinter csvPrinter11 = CsvPrinterBuilder.getInstance().setCommaSeparator().setCrLfSeparator().setFormat(CsvFormat.RFC4180).build();
        csvPrinter11.addColumn("a").addColumn("b").addRow();
        csvPrinter11.addColumn("c").addColumn("d").addRow();
        Assertions.assertThat(csvPrinter11.getCsv()).isEqualTo("a,b\r\nc,d\r\n");

        CsvPrinter csvPrinter12 = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setLfSeparator().setFormat(CsvFormat.RFC4180).build();
        csvPrinter12.addColumn("a").addColumn("b").addRow();
        csvPrinter12.addColumn("c").addColumn("d").addRow();
        Assertions.assertThat(csvPrinter12.getCsv()).isEqualTo("a,b\r\nc,d\r\n");

        try {
            CsvPrinter csvPrinter21 = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).setFormat(CsvFormat.RFC4180).build();
            csvPrinter21.addColumn("a").addColumn("b").addColumn("c").addRow();
            csvPrinter21.addColumn("d").addColumn("e").addRow();
            Assertions.fail("CsvPrinterBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }

        try {
            CsvPrinter csvPrinter22 = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(false).setFormat(CsvFormat.RFC4180).build();
            csvPrinter22.addColumn("a").addColumn("b").addColumn("c").addRow();
            csvPrinter22.addColumn("d").addColumn("e").addRow();
            Assertions.fail("CsvPrinterBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }

        try {
            CsvPrinter csvPrinter31 = CsvPrinterBuilder.getInstance().setSkipEmptyRowsEnabled(false).setFormat(CsvFormat.RFC4180).build();
            csvPrinter31.addColumn("a").addRow();
            csvPrinter31.addRow();
            Assertions.fail("CsvPrinterBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }

        try {
            CsvPrinter csvPrinter32 = CsvPrinterBuilder.getInstance().setSkipEmptyRowsEnabled(true).setFormat(CsvFormat.RFC4180).build();
            csvPrinter32.addColumn("a").addRow();
            csvPrinter32.addRow();
            Assertions.fail("CsvPrinterBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }

        CsvPrinter csvPrinter41 = CsvPrinterBuilder.getInstance().setEscapeAllSeparatorsEnabled(false).setFormat(CsvFormat.RFC4180).build();
        csvPrinter41.addColumn(" , ").addRow();
        csvPrinter41.addColumn(" ; ").addRow();
        csvPrinter41.addColumn(" \r ").addRow();
        csvPrinter41.addColumn(" \n ").addRow();
        csvPrinter41.addColumn(" \r\n ").addRow();
        csvPrinter41.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter41.getCsv()).isEqualTo("\" , \"\r\n ; \r\n \r \r\n \n \r\n\" \r\n \"\r\n\" \"\" \"\r\n");

        CsvPrinter csvPrinter42 = CsvPrinterBuilder.getInstance().setEscapeAllSeparatorsEnabled(true).setFormat(CsvFormat.RFC4180).build();
        csvPrinter42.addColumn(" , ").addRow();
        csvPrinter42.addColumn(" ; ").addRow();
        csvPrinter42.addColumn(" \r ").addRow();
        csvPrinter42.addColumn(" \n ").addRow();
        csvPrinter42.addColumn(" \r\n ").addRow();
        csvPrinter42.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter42.getCsv()).isEqualTo("\" , \"\r\n ; \r\n \r \r\n \n \r\n\" \r\n \"\r\n\" \"\" \"\r\n");
    }

    /**
     * {@link CsvPrinterBuilder} class test.
     */
    @Test
    public void setFormatExcelCommaTest() {
        CsvPrinter csvPrinter11 = CsvPrinterBuilder.getInstance().setCommaSeparator().setCrLfSeparator().setFormat(CsvFormat.EXCEL_COMMA).build();
        csvPrinter11.addColumn("a").addColumn("b").addRow();
        csvPrinter11.addColumn("c").addColumn("d").addRow();
        Assertions.assertThat(csvPrinter11.getCsv()).isEqualTo("a,b\r\nc,d\r\n");

        CsvPrinter csvPrinter12 = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setLfSeparator().setFormat(CsvFormat.EXCEL_COMMA).build();
        csvPrinter12.addColumn("a").addColumn("b").addRow();
        csvPrinter12.addColumn("c").addColumn("d").addRow();
        Assertions.assertThat(csvPrinter12.getCsv()).isEqualTo("a,b\r\nc,d\r\n");

        try {
            CsvPrinter csvPrinter21 = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).setFormat(CsvFormat.EXCEL_COMMA).build();
            csvPrinter21.addColumn("a").addColumn("b").addColumn("c").addRow();
            csvPrinter21.addColumn("d").addColumn("e").addRow();
            Assertions.fail("CsvPrinterBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }

        try {
            CsvPrinter csvPrinter22 = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(false).setFormat(CsvFormat.EXCEL_COMMA).build();
            csvPrinter22.addColumn("a").addColumn("b").addColumn("c").addRow();
            csvPrinter22.addColumn("d").addColumn("e").addRow();
            Assertions.fail("CsvPrinterBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }

        try {
            CsvPrinter csvPrinter31 = CsvPrinterBuilder.getInstance().setSkipEmptyRowsEnabled(false).setFormat(CsvFormat.EXCEL_COMMA).build();
            csvPrinter31.addColumn("a").addRow();
            csvPrinter31.addRow();
            Assertions.fail("CsvPrinterBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }

        try {
            CsvPrinter csvPrinter32 = CsvPrinterBuilder.getInstance().setSkipEmptyRowsEnabled(true).setFormat(CsvFormat.EXCEL_COMMA).build();
            csvPrinter32.addColumn("a").addRow();
            csvPrinter32.addRow();
            Assertions.fail("CsvPrinterBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }

        CsvPrinter csvPrinter41 = CsvPrinterBuilder.getInstance().setEscapeAllSeparatorsEnabled(false).setFormat(CsvFormat.EXCEL_COMMA).build();
        csvPrinter41.addColumn(" , ").addRow();
        csvPrinter41.addColumn(" ; ").addRow();
        csvPrinter41.addColumn(" \r ").addRow();
        csvPrinter41.addColumn(" \n ").addRow();
        csvPrinter41.addColumn(" \r\n ").addRow();
        csvPrinter41.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter41.getCsv()).isEqualTo("\" , \"\r\n ; \r\n \r \r\n \n \r\n\" \r\n \"\r\n\" \"\" \"\r\n");

        CsvPrinter csvPrinter42 = CsvPrinterBuilder.getInstance().setEscapeAllSeparatorsEnabled(true).setFormat(CsvFormat.EXCEL_COMMA).build();
        csvPrinter42.addColumn(" , ").addRow();
        csvPrinter42.addColumn(" ; ").addRow();
        csvPrinter42.addColumn(" \r ").addRow();
        csvPrinter42.addColumn(" \n ").addRow();
        csvPrinter42.addColumn(" \r\n ").addRow();
        csvPrinter42.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter42.getCsv()).isEqualTo("\" , \"\r\n ; \r\n \r \r\n \n \r\n\" \r\n \"\r\n\" \"\" \"\r\n");
    }

    /**
     * {@link CsvPrinterBuilder} class test.
     */
    @Test
    public void setFormatExcelSemicolonTest() {
        CsvPrinter csvPrinter11 = CsvPrinterBuilder.getInstance().setSemicolonSeparator().setCrLfSeparator().setFormat(CsvFormat.EXCEL_SEMICOLON).build();
        csvPrinter11.addColumn("a").addColumn("b").addRow();
        csvPrinter11.addColumn("c").addColumn("d").addRow();
        Assertions.assertThat(csvPrinter11.getCsv()).isEqualTo("a;b\r\nc;d\r\n");

        CsvPrinter csvPrinter12 = CsvPrinterBuilder.getInstance().setCommaSeparator().setLfSeparator().setFormat(CsvFormat.EXCEL_SEMICOLON).build();
        csvPrinter12.addColumn("a").addColumn("b").addRow();
        csvPrinter12.addColumn("c").addColumn("d").addRow();
        Assertions.assertThat(csvPrinter12.getCsv()).isEqualTo("a;b\r\nc;d\r\n");

        try {
            CsvPrinter csvPrinter21 = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(true).setFormat(CsvFormat.EXCEL_SEMICOLON).build();
            csvPrinter21.addColumn("a").addColumn("b").addColumn("c").addRow();
            csvPrinter21.addColumn("d").addColumn("e").addRow();
            Assertions.fail("CsvPrinterBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }

        try {
            CsvPrinter csvPrinter22 = CsvPrinterBuilder.getInstance().setColumnCountCheckEnabled(false).setFormat(CsvFormat.EXCEL_SEMICOLON).build();
            csvPrinter22.addColumn("a").addColumn("b").addColumn("c").addRow();
            csvPrinter22.addColumn("d").addColumn("e").addRow();
            Assertions.fail("CsvPrinterBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }

        try {
            CsvPrinter csvPrinter31 = CsvPrinterBuilder.getInstance().setSkipEmptyRowsEnabled(false).setFormat(CsvFormat.EXCEL_SEMICOLON).build();
            csvPrinter31.addColumn("a").addRow();
            csvPrinter31.addRow();
            Assertions.fail("CsvPrinterBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }

        try {
            CsvPrinter csvPrinter32 = CsvPrinterBuilder.getInstance().setSkipEmptyRowsEnabled(true).setFormat(CsvFormat.EXCEL_SEMICOLON).build();
            csvPrinter32.addColumn("a").addRow();
            csvPrinter32.addRow();
            Assertions.fail("CsvPrinterBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }

        CsvPrinter csvPrinter41 = CsvPrinterBuilder.getInstance().setEscapeAllSeparatorsEnabled(false).setFormat(CsvFormat.EXCEL_SEMICOLON).build();
        csvPrinter41.addColumn(" , ").addRow();
        csvPrinter41.addColumn(" ; ").addRow();
        csvPrinter41.addColumn(" \r ").addRow();
        csvPrinter41.addColumn(" \n ").addRow();
        csvPrinter41.addColumn(" \r\n ").addRow();
        csvPrinter41.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter41.getCsv()).isEqualTo(" , \r\n\" ; \"\r\n \r \r\n \n \r\n\" \r\n \"\r\n\" \"\" \"\r\n");

        CsvPrinter csvPrinter42 = CsvPrinterBuilder.getInstance().setEscapeAllSeparatorsEnabled(true).setFormat(CsvFormat.EXCEL_SEMICOLON).build();
        csvPrinter42.addColumn(" , ").addRow();
        csvPrinter42.addColumn(" ; ").addRow();
        csvPrinter42.addColumn(" \r ").addRow();
        csvPrinter42.addColumn(" \n ").addRow();
        csvPrinter42.addColumn(" \r\n ").addRow();
        csvPrinter42.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter42.getCsv()).isEqualTo(" , \r\n\" ; \"\r\n \r \r\n \n \r\n\" \r\n \"\r\n\" \"\" \"\r\n");
    }

    /**
     * {@link CsvPrinterBuilder} class test.
     */
    @Test
    public void commaSeparatorTest() {
        CsvPrinterBuilder csvPrinterBuilder = CsvPrinterBuilder.getInstance();
        csvPrinterBuilder = csvPrinterBuilder.setCommaSeparator();
        csvPrinterBuilder.setCrLfSeparator();
        CsvPrinter csvPrinter = csvPrinterBuilder.build();
        csvPrinter.addColumn("a").addColumn("b").addRow();
        csvPrinter.addColumn("c").addColumn("d").addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("a,b\r\nc,d\r\n");
    }

    /**
     * {@link CsvPrinterBuilder} class test.
     */
    @Test
    public void semicolonSeparatorTest() {
        CsvPrinterBuilder csvPrinterBuilder = CsvPrinterBuilder.getInstance();
        csvPrinterBuilder = csvPrinterBuilder.setSemicolonSeparator();
        csvPrinterBuilder.setCrLfSeparator();
        CsvPrinter csvPrinter = csvPrinterBuilder.build();
        csvPrinter.addColumn("a").addColumn("b").addRow();
        csvPrinter.addColumn("c").addColumn("d").addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("a;b\r\nc;d\r\n");
    }

    /**
     * {@link CsvPrinterBuilder} class test.
     */
    @Test
    public void crSeparatorTest() {
        CsvPrinterBuilder csvPrinterBuilder = CsvPrinterBuilder.getInstance();
        csvPrinterBuilder.setCommaSeparator();
        csvPrinterBuilder = csvPrinterBuilder.setCrSeparator();
        CsvPrinter csvPrinter = csvPrinterBuilder.build();
        csvPrinter.addColumn("a").addColumn("b").addRow();
        csvPrinter.addColumn("c").addColumn("d").addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("a,b\rc,d\r");
    }

    /**
     * {@link CsvPrinterBuilder} class test.
     */
    @Test
    public void lfSeparatorTest() {
        CsvPrinterBuilder csvPrinterBuilder = CsvPrinterBuilder.getInstance();
        csvPrinterBuilder.setCommaSeparator();
        csvPrinterBuilder = csvPrinterBuilder.setLfSeparator();
        CsvPrinter csvPrinter = csvPrinterBuilder.build();
        csvPrinter.addColumn("a").addColumn("b").addRow();
        csvPrinter.addColumn("c").addColumn("d").addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("a,b\nc,d\n");
    }

    /**
     * {@link CsvPrinterBuilder} class test.
     */
    @Test
    public void crLfSeparatorTest() {
        CsvPrinterBuilder csvPrinterBuilder = CsvPrinterBuilder.getInstance();
        csvPrinterBuilder.setCommaSeparator();
        csvPrinterBuilder = csvPrinterBuilder.setCrLfSeparator();
        CsvPrinter csvPrinter = csvPrinterBuilder.build();
        csvPrinter.addColumn("a").addColumn("b").addRow();
        csvPrinter.addColumn("c").addColumn("d").addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("a,b\r\nc,d\r\n");
    }

    /**
     * {@link CsvPrinterBuilder} class test.
     */
    @Test
    public void columnCountCheckEnabledTest() {
        CsvPrinterBuilder csvPrinterBuilder = CsvPrinterBuilder.getInstance();

        try {
            csvPrinterBuilder = csvPrinterBuilder.setColumnCountCheckEnabled(true);
            CsvPrinter csvPrinter1 = csvPrinterBuilder.build();
            csvPrinter1.addColumn("a").addColumn("b").addRow();
            csvPrinter1.addColumn("c").addColumn("d").addColumn("e");
            Assertions.fail("CsvPrinterBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count.");
        }

        csvPrinterBuilder = csvPrinterBuilder.setColumnCountCheckEnabled(false);
        CsvPrinter csvPrinter2 = csvPrinterBuilder.build();
        csvPrinter2.addColumn("a").addColumn("b").addRow();
        csvPrinter2.addColumn("c").addColumn("d").addColumn("e").addRow();
        Assertions.assertThat(csvPrinter2.getCsv()).isEqualTo("a,b\r\nc,d,e\r\n");
    }

    /**
     * {@link CsvPrinterBuilder} class test.
     */
    @Test
    public void skipEmptyRowsEnabledTest() {
        CsvPrinterBuilder csvPrinterBuilder = CsvPrinterBuilder.getInstance();
        csvPrinterBuilder.setColumnCountCheckEnabled(false);

        csvPrinterBuilder = csvPrinterBuilder.setSkipEmptyRowsEnabled(true);
        CsvPrinter csvPrinter1 = csvPrinterBuilder.build();
        csvPrinter1.addColumn("a").addRow();
        csvPrinter1.addRow();
        csvPrinter1.addColumn("b").addRow();
        csvPrinter1.addRow();
        csvPrinter1.addColumn("c").addRow();
        Assertions.assertThat(csvPrinter1.getCsv()).isEqualTo("a\r\nb\r\nc\r\n");

        csvPrinterBuilder = csvPrinterBuilder.setSkipEmptyRowsEnabled(false);
        CsvPrinter csvPrinter2 = csvPrinterBuilder.build();
        csvPrinter2.addColumn("a").addRow();
        csvPrinter2.addRow();
        csvPrinter2.addColumn("b").addRow();
        csvPrinter2.addRow();
        csvPrinter2.addColumn("c").addRow();
        Assertions.assertThat(csvPrinter2.getCsv()).isEqualTo("a\r\n\r\nb\r\n\r\nc\r\n");
    }

    /**
     * {@link CsvPrinterBuilder} class test.
     */
    @Test
    public void escapeAllSeparatorsEnabledTest() {
        CsvPrinterBuilder csvPrinterBuilder = CsvPrinterBuilder.getInstance();
        csvPrinterBuilder.setCommaSeparator();
        csvPrinterBuilder.setCrLfSeparator();

        csvPrinterBuilder = csvPrinterBuilder.setEscapeAllSeparatorsEnabled(true);
        CsvPrinter csvPrinter1 = csvPrinterBuilder.build();
        csvPrinter1.addColumn(" , ").addRow();
        csvPrinter1.addColumn(" ; ").addRow();
        csvPrinter1.addColumn(" \r ").addRow();
        csvPrinter1.addColumn(" \n ").addRow();
        csvPrinter1.addColumn(" \r\n ").addRow();
        csvPrinter1.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter1.getCsv()).isEqualTo("\" , \"\r\n\" ; \"\r\n\" \r \"\r\n\" \n \"\r\n\" \r\n \"\r\n\" \"\" \"\r\n");

        csvPrinterBuilder = csvPrinterBuilder.setEscapeAllSeparatorsEnabled(false);
        CsvPrinter csvPrinter2 = csvPrinterBuilder.build();
        csvPrinter2.addColumn(" , ").addRow();
        csvPrinter2.addColumn(" ; ").addRow();
        csvPrinter2.addColumn(" \r ").addRow();
        csvPrinter2.addColumn(" \n ").addRow();
        csvPrinter2.addColumn(" \r\n ").addRow();
        csvPrinter2.addColumn(" \" ").addRow();
        Assertions.assertThat(csvPrinter2.getCsv()).isEqualTo("\" , \"\r\n ; \r\n \r \r\n \n \r\n\" \r\n \"\r\n\" \"\" \"\r\n");
    }

    /**
     * {@link CsvPrinterBuilder} class test.
     */
    @Test
    public void buildTest() {
        CsvPrinterBuilder csvPrinterBuilder = CsvPrinterBuilder.getInstance();
        csvPrinterBuilder.setCommaSeparator();
        csvPrinterBuilder.setCrLfSeparator();
        CsvPrinter csvPrinter = csvPrinterBuilder.build();
        csvPrinter.addColumn("1234567890").addRow();
        Assertions.assertThat(csvPrinter.getCsv()).isNotNull();
        Assertions.assertThat(csvPrinter.getCsv()).isEqualTo("1234567890\r\n");
    }

    /**
     * {@link CsvPrinterBuilder} class test.
     */
    @Test
    public void buildWithWriterTest() {
        CsvPrinterBuilder csvPrinterBuilder = CsvPrinterBuilder.getInstance();
        csvPrinterBuilder.setCommaSeparator();
        csvPrinterBuilder.setCrLfSeparator();

        StringWriter writer1 = new StringWriter();
        try (CsvPrinter csvPrinter1 = csvPrinterBuilder.build(writer1)) {
            csvPrinter1.addColumn("1234567890").addRow();
            Assertions.assertThat(csvPrinter1.getCsv()).isNotNull();
            Assertions.assertThat(csvPrinter1.getCsv()).isEqualTo("1234567890\r\n");
        }
        Assertions.assertThat(writer1.getBuffer()).isEqualTo("1234567890\r\n");

        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        try (CsvPrinter csvPrinter2 = csvPrinterBuilder.build(new OutputStreamWriter(baos2))) {
            csvPrinter2.addColumn("1234567890").addRow();
            Assertions.assertThat(csvPrinter2.getCsv()).isNull();
        }
        Assertions.assertThat(baos2.toByteArray()).containsExactlyInOrder(49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 13, 10);
    }

}
