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

import java.io.StringReader;
import java.util.List;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.csv.handler.AbstractListEventHandler;
import ru.d_shap.csv.handler.ListEventHandler;
import ru.d_shap.csv.handler.NoopEventHandler;

/**
 * Tests for {@link CsvParserBuilder}.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvParserBuilderTest extends CsvTest {

    /**
     * Test class constructor.
     */
    public CsvParserBuilderTest() {
        super();
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void setFormatDefaultTest() {
        List<List<String>> result11 = CsvParserBuilder.getInstance().setCommaSeparator(false).setSemicolonSeparator(true).setFormat(CsvFormat.DEFAULT).parse("a,b;c");
        Assertions.assertThat(result11).hasSize(1);
        Assertions.assertThat(result11.get(0)).containsExactlyInOrder("a", "b", "c");

        List<List<String>> result12 = CsvParserBuilder.getInstance().setCommaSeparator(true).setSemicolonSeparator(false).setFormat(CsvFormat.DEFAULT).parse("a,b;c");
        Assertions.assertThat(result12).hasSize(1);
        Assertions.assertThat(result12.get(0)).containsExactlyInOrder("a", "b", "c");

        List<List<String>> result21 = CsvParserBuilder.getInstance().setCrSeparator(false).setLfSeparator(true).setCrLfSeparator(true).setFormat(CsvFormat.DEFAULT).parse("a\rb\nc\r\nd");
        Assertions.assertThat(result21).hasSize(3);
        Assertions.assertThat(result21.get(0)).containsExactlyInOrder("a\rb");
        Assertions.assertThat(result21.get(1)).containsExactlyInOrder("c");
        Assertions.assertThat(result21.get(2)).containsExactlyInOrder("d");

        List<List<String>> result22 = CsvParserBuilder.getInstance().setCrSeparator(true).setLfSeparator(false).setCrLfSeparator(false).setFormat(CsvFormat.DEFAULT).parse("a\rb\nc\r\nd");
        Assertions.assertThat(result22).hasSize(3);
        Assertions.assertThat(result22.get(0)).containsExactlyInOrder("a\rb");
        Assertions.assertThat(result22.get(1)).containsExactlyInOrder("c");
        Assertions.assertThat(result22.get(2)).containsExactlyInOrder("d");

        List<List<String>> result31 = CsvParserBuilder.getInstance().setColumnCountCheckEnabled(false).setFormat(CsvFormat.DEFAULT).parse("a,b,c\r\nd,e\r\n");
        Assertions.assertThat(result31).hasSize(2);
        Assertions.assertThat(result31.get(0)).containsExactlyInOrder("a", "b", "c");
        Assertions.assertThat(result31.get(1)).containsExactlyInOrder("d", "e");

        List<List<String>> result32 = CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).setFormat(CsvFormat.DEFAULT).parse("a,b,c\r\nd,e\r\n");
        Assertions.assertThat(result32).hasSize(2);
        Assertions.assertThat(result32.get(0)).containsExactlyInOrder("a", "b", "c");
        Assertions.assertThat(result32.get(1)).containsExactlyInOrder("d", "e");

        List<List<String>> result41 = CsvParserBuilder.getInstance().setSkipEmptyRowsEnabled(false).setFormat(CsvFormat.DEFAULT).parse("a\r\n\r\nb\r\n\r\nc");
        Assertions.assertThat(result41).hasSize(5);
        Assertions.assertThat(result41.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result41.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result41.get(2)).containsExactlyInOrder("b");
        Assertions.assertThat(result41.get(3)).containsExactlyInOrder();
        Assertions.assertThat(result41.get(4)).containsExactlyInOrder("c");

        List<List<String>> result42 = CsvParserBuilder.getInstance().setSkipEmptyRowsEnabled(true).setFormat(CsvFormat.DEFAULT).parse("a\r\n\r\nb\r\n\r\nc");
        Assertions.assertThat(result42).hasSize(5);
        Assertions.assertThat(result42.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result42.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result42.get(2)).containsExactlyInOrder("b");
        Assertions.assertThat(result42.get(3)).containsExactlyInOrder();
        Assertions.assertThat(result42.get(4)).containsExactlyInOrder("c");

        UnrestrictedListEventHandler eventHandler51 = new UnrestrictedListEventHandler();
        CsvParserBuilder.getInstance().setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).setFormat(CsvFormat.DEFAULT).parse("1234567890", eventHandler51);
        List<List<String>> result51 = eventHandler51.getCsv();
        Assertions.assertThat(result51).hasSize(1);
        Assertions.assertThat(result51.get(0)).containsExactlyInOrder("1234567890");

        UnrestrictedListEventHandler eventHandler52 = new UnrestrictedListEventHandler();
        CsvParserBuilder.getInstance().setMaxColumnLength(0).setMaxColumnLengthCheckEnabled(true).setFormat(CsvFormat.DEFAULT).parse("1234567890", eventHandler52);
        List<List<String>> result52 = eventHandler52.getCsv();
        Assertions.assertThat(result52).hasSize(1);
        Assertions.assertThat(result52.get(0)).containsExactlyInOrder("1234567890");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void setFormatRfc4180Test() {
        List<List<String>> result11 = CsvParserBuilder.getInstance().setCommaSeparator(false).setSemicolonSeparator(true).setFormat(CsvFormat.RFC4180).parse("a,b;c");
        Assertions.assertThat(result11).hasSize(1);
        Assertions.assertThat(result11.get(0)).containsExactlyInOrder("a", "b;c");

        List<List<String>> result12 = CsvParserBuilder.getInstance().setCommaSeparator(true).setSemicolonSeparator(false).setFormat(CsvFormat.RFC4180).parse("a,b;c");
        Assertions.assertThat(result12).hasSize(1);
        Assertions.assertThat(result12.get(0)).containsExactlyInOrder("a", "b;c");

        List<List<String>> result21 = CsvParserBuilder.getInstance().setCrSeparator(false).setLfSeparator(true).setCrLfSeparator(true).setFormat(CsvFormat.RFC4180).parse("a\rb\nc\r\nd");
        Assertions.assertThat(result21).hasSize(2);
        Assertions.assertThat(result21.get(0)).containsExactlyInOrder("a\rb\nc");
        Assertions.assertThat(result21.get(1)).containsExactlyInOrder("d");

        List<List<String>> result22 = CsvParserBuilder.getInstance().setCrSeparator(true).setLfSeparator(false).setCrLfSeparator(false).setFormat(CsvFormat.RFC4180).parse("a\rb\nc\r\nd");
        Assertions.assertThat(result22).hasSize(2);
        Assertions.assertThat(result22.get(0)).containsExactlyInOrder("a\rb\nc");
        Assertions.assertThat(result22.get(1)).containsExactlyInOrder("d");

        try {
            CsvParserBuilder.getInstance().setColumnCountCheckEnabled(false).setFormat(CsvFormat.RFC4180).parse("a,b,c\r\nd,e\r\n");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a,b,c\\r\\nd,e\\r\\n\".");
        }

        try {
            CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).setFormat(CsvFormat.RFC4180).parse("a,b,c\r\nd,e\r\n");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a,b,c\\r\\nd,e\\r\\n\".");
        }

        try {
            CsvParserBuilder.getInstance().setSkipEmptyRowsEnabled(false).setFormat(CsvFormat.RFC4180).parse("a\r\n\r\nb\r\n\r\nc");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a\\r\\n\\r\\n\".");
        }

        try {
            CsvParserBuilder.getInstance().setSkipEmptyRowsEnabled(true).setFormat(CsvFormat.RFC4180).parse("a\r\n\r\nb\r\n\r\nc");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a\\r\\n\\r\\n\".");
        }

        UnrestrictedListEventHandler eventHandler51 = new UnrestrictedListEventHandler();
        CsvParserBuilder.getInstance().setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).setFormat(CsvFormat.RFC4180).parse("1234567890", eventHandler51);
        List<List<String>> result51 = eventHandler51.getCsv();
        Assertions.assertThat(result51).hasSize(1);
        Assertions.assertThat(result51.get(0)).containsExactlyInOrder("1234567890");

        UnrestrictedListEventHandler eventHandler52 = new UnrestrictedListEventHandler();
        CsvParserBuilder.getInstance().setMaxColumnLength(0).setMaxColumnLengthCheckEnabled(true).setFormat(CsvFormat.RFC4180).parse("1234567890", eventHandler52);
        List<List<String>> result52 = eventHandler52.getCsv();
        Assertions.assertThat(result52).hasSize(1);
        Assertions.assertThat(result52.get(0)).containsExactlyInOrder("1234567890");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void setFormatExcelCommaTest() {
        List<List<String>> result11 = CsvParserBuilder.getInstance().setCommaSeparator(false).setSemicolonSeparator(true).setFormat(CsvFormat.EXCEL_COMMA).parse("a,b;c");
        Assertions.assertThat(result11).hasSize(1);
        Assertions.assertThat(result11.get(0)).containsExactlyInOrder("a", "b;c");

        List<List<String>> result12 = CsvParserBuilder.getInstance().setCommaSeparator(true).setSemicolonSeparator(false).setFormat(CsvFormat.EXCEL_COMMA).parse("a,b;c");
        Assertions.assertThat(result12).hasSize(1);
        Assertions.assertThat(result12.get(0)).containsExactlyInOrder("a", "b;c");

        List<List<String>> result21 = CsvParserBuilder.getInstance().setCrSeparator(false).setLfSeparator(true).setCrLfSeparator(true).setFormat(CsvFormat.EXCEL_COMMA).parse("a\rb\nc\r\nd");
        Assertions.assertThat(result21).hasSize(2);
        Assertions.assertThat(result21.get(0)).containsExactlyInOrder("a\rb\nc");
        Assertions.assertThat(result21.get(1)).containsExactlyInOrder("d");

        List<List<String>> result22 = CsvParserBuilder.getInstance().setCrSeparator(true).setLfSeparator(false).setCrLfSeparator(false).setFormat(CsvFormat.EXCEL_COMMA).parse("a\rb\nc\r\nd");
        Assertions.assertThat(result22).hasSize(2);
        Assertions.assertThat(result22.get(0)).containsExactlyInOrder("a\rb\nc");
        Assertions.assertThat(result22.get(1)).containsExactlyInOrder("d");

        try {
            CsvParserBuilder.getInstance().setColumnCountCheckEnabled(false).setFormat(CsvFormat.EXCEL_COMMA).parse("a,b,c\r\nd,e\r\n");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a,b,c\\r\\nd,e\\r\\n\".");
        }

        try {
            CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).setFormat(CsvFormat.EXCEL_COMMA).parse("a,b,c\r\nd,e\r\n");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a,b,c\\r\\nd,e\\r\\n\".");
        }

        try {
            CsvParserBuilder.getInstance().setSkipEmptyRowsEnabled(false).setFormat(CsvFormat.EXCEL_COMMA).parse("a\r\n\r\nb\r\n\r\nc");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a\\r\\n\\r\\n\".");
        }

        try {
            CsvParserBuilder.getInstance().setSkipEmptyRowsEnabled(true).setFormat(CsvFormat.EXCEL_COMMA).parse("a\r\n\r\nb\r\n\r\nc");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a\\r\\n\\r\\n\".");
        }

        UnrestrictedListEventHandler eventHandler51 = new UnrestrictedListEventHandler();
        CsvParserBuilder.getInstance().setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).setFormat(CsvFormat.EXCEL_COMMA).parse("1234567890", eventHandler51);
        List<List<String>> result51 = eventHandler51.getCsv();
        Assertions.assertThat(result51).hasSize(1);
        Assertions.assertThat(result51.get(0)).containsExactlyInOrder("1234567890");

        UnrestrictedListEventHandler eventHandler52 = new UnrestrictedListEventHandler();
        CsvParserBuilder.getInstance().setMaxColumnLength(0).setMaxColumnLengthCheckEnabled(true).setFormat(CsvFormat.EXCEL_COMMA).parse("1234567890", eventHandler52);
        List<List<String>> result52 = eventHandler52.getCsv();
        Assertions.assertThat(result52).hasSize(1);
        Assertions.assertThat(result52.get(0)).containsExactlyInOrder("1234567890");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void setFormatExcelSemicolonTest() {
        List<List<String>> result11 = CsvParserBuilder.getInstance().setCommaSeparator(false).setSemicolonSeparator(true).setFormat(CsvFormat.EXCEL_SEMICOLON).parse("a,b;c");
        Assertions.assertThat(result11).hasSize(1);
        Assertions.assertThat(result11.get(0)).containsExactlyInOrder("a,b", "c");

        List<List<String>> result12 = CsvParserBuilder.getInstance().setCommaSeparator(true).setSemicolonSeparator(false).setFormat(CsvFormat.EXCEL_SEMICOLON).parse("a,b;c");
        Assertions.assertThat(result12).hasSize(1);
        Assertions.assertThat(result12.get(0)).containsExactlyInOrder("a,b", "c");

        List<List<String>> result21 = CsvParserBuilder.getInstance().setCrSeparator(false).setLfSeparator(true).setCrLfSeparator(true).setFormat(CsvFormat.EXCEL_SEMICOLON).parse("a\rb\nc\r\nd");
        Assertions.assertThat(result21).hasSize(2);
        Assertions.assertThat(result21.get(0)).containsExactlyInOrder("a\rb\nc");
        Assertions.assertThat(result21.get(1)).containsExactlyInOrder("d");

        List<List<String>> result22 = CsvParserBuilder.getInstance().setCrSeparator(true).setLfSeparator(false).setCrLfSeparator(false).setFormat(CsvFormat.EXCEL_SEMICOLON).parse("a\rb\nc\r\nd");
        Assertions.assertThat(result22).hasSize(2);
        Assertions.assertThat(result22.get(0)).containsExactlyInOrder("a\rb\nc");
        Assertions.assertThat(result22.get(1)).containsExactlyInOrder("d");

        try {
            CsvParserBuilder.getInstance().setColumnCountCheckEnabled(false).setFormat(CsvFormat.EXCEL_SEMICOLON).parse("a;b;c\r\nd;e\r\n");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a;b;c\\r\\nd;e\\r\\n\".");
        }

        try {
            CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).setFormat(CsvFormat.EXCEL_SEMICOLON).parse("a;b;c\r\nd;e\r\n");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a;b;c\\r\\nd;e\\r\\n\".");
        }

        try {
            CsvParserBuilder.getInstance().setSkipEmptyRowsEnabled(false).setFormat(CsvFormat.EXCEL_SEMICOLON).parse("a\r\n\r\nb\r\n\r\nc");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a\\r\\n\\r\\n\".");
        }

        try {
            CsvParserBuilder.getInstance().setSkipEmptyRowsEnabled(true).setFormat(CsvFormat.EXCEL_SEMICOLON).parse("a\r\n\r\nb\r\n\r\nc");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a\\r\\n\\r\\n\".");
        }

        UnrestrictedListEventHandler eventHandler51 = new UnrestrictedListEventHandler();
        CsvParserBuilder.getInstance().setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).setFormat(CsvFormat.EXCEL_SEMICOLON).parse("1234567890", eventHandler51);
        List<List<String>> result51 = eventHandler51.getCsv();
        Assertions.assertThat(result51).hasSize(1);
        Assertions.assertThat(result51.get(0)).containsExactlyInOrder("1234567890");

        UnrestrictedListEventHandler eventHandler52 = new UnrestrictedListEventHandler();
        CsvParserBuilder.getInstance().setMaxColumnLength(0).setMaxColumnLengthCheckEnabled(true).setFormat(CsvFormat.EXCEL_SEMICOLON).parse("1234567890", eventHandler52);
        List<List<String>> result52 = eventHandler52.getCsv();
        Assertions.assertThat(result52).hasSize(1);
        Assertions.assertThat(result52.get(0)).containsExactlyInOrder("1234567890");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void commaSeparatorTest() {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance();
        csvParserBuilder.setSemicolonSeparator(true);
        csvParserBuilder.setCrSeparator(true);
        csvParserBuilder.setLfSeparator(true);
        csvParserBuilder.setCrLfSeparator(true);

        csvParserBuilder = csvParserBuilder.setCommaSeparator(true);
        List<List<String>> result1 = csvParserBuilder.parse("a,b");
        Assertions.assertThat(result1).hasSize(1);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a", "b");

        csvParserBuilder = csvParserBuilder.setCommaSeparator(false);
        List<List<String>> result2 = csvParserBuilder.parse("a,b");
        Assertions.assertThat(result2).hasSize(1);
        Assertions.assertThat(result2.get(0)).containsExactlyInOrder("a,b");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void semicolonSeparatorTest() {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance();
        csvParserBuilder.setCommaSeparator(true);
        csvParserBuilder.setCrSeparator(true);
        csvParserBuilder.setLfSeparator(true);
        csvParserBuilder.setCrLfSeparator(true);

        csvParserBuilder = csvParserBuilder.setSemicolonSeparator(true);
        List<List<String>> result1 = csvParserBuilder.parse("a;b");
        Assertions.assertThat(result1).hasSize(1);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a", "b");

        csvParserBuilder = csvParserBuilder.setSemicolonSeparator(false);
        List<List<String>> result2 = csvParserBuilder.parse("a;b");
        Assertions.assertThat(result2).hasSize(1);
        Assertions.assertThat(result2.get(0)).containsExactlyInOrder("a;b");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void crSeparatorTest() {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance();
        csvParserBuilder.setCommaSeparator(true);
        csvParserBuilder.setSemicolonSeparator(true);
        csvParserBuilder.setLfSeparator(true);
        csvParserBuilder.setCrLfSeparator(true);

        csvParserBuilder = csvParserBuilder.setCrSeparator(true);
        List<List<String>> result1 = csvParserBuilder.parse("a\rb");
        Assertions.assertThat(result1).hasSize(2);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result1.get(1)).containsExactlyInOrder("b");

        csvParserBuilder = csvParserBuilder.setCrSeparator(false);
        List<List<String>> result2 = csvParserBuilder.parse("a\rb");
        Assertions.assertThat(result2).hasSize(1);
        Assertions.assertThat(result2.get(0)).containsExactlyInOrder("a\rb");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void lfSeparatorTest() {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance();
        csvParserBuilder.setCommaSeparator(true);
        csvParserBuilder.setSemicolonSeparator(true);
        csvParserBuilder.setCrSeparator(true);
        csvParserBuilder.setCrLfSeparator(true);

        csvParserBuilder = csvParserBuilder.setLfSeparator(true);
        List<List<String>> result1 = csvParserBuilder.parse("a\nb");
        Assertions.assertThat(result1).hasSize(2);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result1.get(1)).containsExactlyInOrder("b");

        csvParserBuilder = csvParserBuilder.setLfSeparator(false);
        List<List<String>> result2 = csvParserBuilder.parse("a\nb");
        Assertions.assertThat(result2).hasSize(1);
        Assertions.assertThat(result2.get(0)).containsExactlyInOrder("a\nb");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void crLfSeparatorTest() {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance();
        csvParserBuilder.setCommaSeparator(true);
        csvParserBuilder.setSemicolonSeparator(true);
        csvParserBuilder.setCrSeparator(true);
        csvParserBuilder.setLfSeparator(true);

        csvParserBuilder = csvParserBuilder.setCrLfSeparator(true);
        List<List<String>> result1 = csvParserBuilder.parse("a\r\nb");
        Assertions.assertThat(result1).hasSize(2);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result1.get(1)).containsExactlyInOrder("b");

        csvParserBuilder = csvParserBuilder.setCrLfSeparator(false);
        List<List<String>> result2 = csvParserBuilder.parse("a\r\nb");
        Assertions.assertThat(result2).hasSize(3);
        Assertions.assertThat(result2.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result2.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result2.get(2)).containsExactlyInOrder("b");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void columnCountCheckEnabledTest() {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance();

        try {
            csvParserBuilder = csvParserBuilder.setColumnCountCheckEnabled(true);
            csvParserBuilder.parse("a,b\nc,d\ne,f,g");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a,b\\nc,d\\ne,f,g\".");
        }

        csvParserBuilder = csvParserBuilder.setColumnCountCheckEnabled(false);
        List<List<String>> result2 = csvParserBuilder.parse("a,b\nc,d\ne,f,g");
        Assertions.assertThat(result2).hasSize(3);
        Assertions.assertThat(result2.get(0)).containsExactlyInOrder("a", "b");
        Assertions.assertThat(result2.get(1)).containsExactlyInOrder("c", "d");
        Assertions.assertThat(result2.get(2)).containsExactlyInOrder("e", "f", "g");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void skipEmptyRowsEnabledTest() {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance();
        csvParserBuilder.setColumnCountCheckEnabled(false);

        csvParserBuilder = csvParserBuilder.setSkipEmptyRowsEnabled(true);
        List<List<String>> result1 = csvParserBuilder.parse("a\nb\n\nc\n\n");
        Assertions.assertThat(result1).hasSize(3);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result1.get(1)).containsExactlyInOrder("b");
        Assertions.assertThat(result1.get(2)).containsExactlyInOrder("c");

        csvParserBuilder = csvParserBuilder.setSkipEmptyRowsEnabled(false);
        List<List<String>> result2 = csvParserBuilder.parse("a\nb\n\nc\n\n");
        Assertions.assertThat(result2).hasSize(5);
        Assertions.assertThat(result2.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result2.get(1)).containsExactlyInOrder("b");
        Assertions.assertThat(result2.get(2)).containsExactlyInOrder();
        Assertions.assertThat(result2.get(3)).containsExactlyInOrder("c");
        Assertions.assertThat(result2.get(4)).containsExactlyInOrder();
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void maxColumnLengthTest() {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance();
        csvParserBuilder.setMaxColumnLengthCheckEnabled(true);

        try {
            csvParserBuilder = csvParserBuilder.setMaxColumnLength(3);
            csvParserBuilder.parse("abc,12345", new NoopEventHandler());
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnLengthException ex) {
            Assertions.assertThat(ex).hasMessage("Maximum column value length exceeded. Last characters: \"abc,1234\".");
        }

        csvParserBuilder = csvParserBuilder.setMaxColumnLength(5);
        csvParserBuilder.parse("abc,12345", new NoopEventHandler());
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void maxColumnLengthCheckEnabledTest() {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance();
        csvParserBuilder.setMaxColumnLength(3);

        try {
            csvParserBuilder = csvParserBuilder.setMaxColumnLengthCheckEnabled(true);
            csvParserBuilder.parse("abc,12345", new NoopEventHandler());
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnLengthException ex) {
            Assertions.assertThat(ex).hasMessage("Maximum column value length exceeded. Last characters: \"abc,1234\".");
        }

        csvParserBuilder = csvParserBuilder.setMaxColumnLengthCheckEnabled(false);
        csvParserBuilder.parse("abc,12345", new NoopEventHandler());
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void parseCharSequenceTest() {
        List<List<String>> result = CsvParserBuilder.getInstance().parse("a,b");
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("a", "b");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void parseCharSequenceWithCsvEventHandlerTest() {
        ListEventHandler eventHandler = new ListEventHandler();
        CsvParserBuilder.getInstance().parse("a,b", eventHandler);
        Assertions.assertThat(eventHandler.getCsv()).hasSize(1);
        Assertions.assertThat(eventHandler.getCsv().get(0)).containsExactlyInOrder("a", "b");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void parseReaderTest() {
        List<List<String>> result = CsvParserBuilder.getInstance().parse(new StringReader("a,b"));
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("a", "b");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void parseReaderWithCsvEventHandlerTest() {
        ListEventHandler eventHandler = new ListEventHandler();
        CsvParserBuilder.getInstance().parse(new StringReader("a,b"), eventHandler);
        Assertions.assertThat(eventHandler.getCsv()).hasSize(1);
        Assertions.assertThat(eventHandler.getCsv().get(0)).containsExactlyInOrder("a", "b");
    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class UnrestrictedListEventHandler extends AbstractListEventHandler {

        /**
         * Create a new object.
         */
        UnrestrictedListEventHandler() {
            super();
        }

        @Override
        protected void doPushColumn(final String column, final int actualLength) {
            addColumnToCurrentRow(column);
        }

    }

}
