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
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance();

        List<List<String>> result1 = csvParserBuilder.parse("a,b;c");
        Assertions.assertThat(result1).hasSize(1);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a", "b", "c");

        List<List<String>> result2 = csvParserBuilder.parse("a\rb\nc\r\nd");
        Assertions.assertThat(result2).hasSize(3);
        Assertions.assertThat(result2.get(0)).containsExactlyInOrder("a\rb");
        Assertions.assertThat(result2.get(1)).containsExactlyInOrder("c");
        Assertions.assertThat(result2.get(2)).containsExactlyInOrder("d");

        List<List<String>> result3 = csvParserBuilder.parse("a,b,c\r\nd,e\r\n");
        Assertions.assertThat(result3).hasSize(2);
        Assertions.assertThat(result3.get(0)).containsExactlyInOrder("a", "b", "c");
        Assertions.assertThat(result3.get(1)).containsExactlyInOrder("d", "e");

        List<List<String>> result4 = csvParserBuilder.parse("a\r\n\r\nb\r\n\r\nc");
        Assertions.assertThat(result4).hasSize(5);
        Assertions.assertThat(result4.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result4.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result4.get(2)).containsExactlyInOrder("b");
        Assertions.assertThat(result4.get(3)).containsExactlyInOrder();
        Assertions.assertThat(result4.get(4)).containsExactlyInOrder("c");

        List<List<String>> result5 = csvParserBuilder.parse("1234567890");
        Assertions.assertThat(result5).hasSize(1);
        Assertions.assertThat(result5.get(0)).containsExactlyInOrder("1234567890");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void setFormatRfc4180Test() {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance().setFormat(CsvFormat.RFC4180);

        List<List<String>> result1 = csvParserBuilder.parse("a,b;c");
        Assertions.assertThat(result1).hasSize(1);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a", "b;c");

        List<List<String>> result2 = csvParserBuilder.parse("a\rb\nc\r\nd");
        Assertions.assertThat(result2).hasSize(2);
        Assertions.assertThat(result2.get(0)).containsExactlyInOrder("a\rb\nc");
        Assertions.assertThat(result2.get(1)).containsExactlyInOrder("d");

        try {
            csvParserBuilder.parse("a,b,c\r\nd,e\r\n");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a,b,c\\r\\nd,e\\r\\n\".");
        }

        try {
            csvParserBuilder.parse("a\r\n\r\nb\r\n\r\nc");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a\\r\\n\\r\\n\".");
        }

        List<List<String>> result5 = csvParserBuilder.parse("1234567890");
        Assertions.assertThat(result5).hasSize(1);
        Assertions.assertThat(result5.get(0)).containsExactlyInOrder("1234567890");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void setFormatExcelCommaTest() {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance().setFormat(CsvFormat.EXCEL_COMMA);

        List<List<String>> result1 = csvParserBuilder.parse("a,b;c");
        Assertions.assertThat(result1).hasSize(1);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a", "b;c");

        List<List<String>> result2 = csvParserBuilder.parse("a\rb\nc\r\nd");
        Assertions.assertThat(result2).hasSize(2);
        Assertions.assertThat(result2.get(0)).containsExactlyInOrder("a\rb\nc");
        Assertions.assertThat(result2.get(1)).containsExactlyInOrder("d");

        try {
            csvParserBuilder.parse("a,b,c\r\nd,e\r\n");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a,b,c\\r\\nd,e\\r\\n\".");
        }

        try {
            csvParserBuilder.parse("a\r\n\r\nb\r\n\r\nc");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a\\r\\n\\r\\n\".");
        }

        List<List<String>> result5 = csvParserBuilder.parse("1234567890");
        Assertions.assertThat(result5).hasSize(1);
        Assertions.assertThat(result5.get(0)).containsExactlyInOrder("1234567890");
    }

    /**
     * {@link CsvParserBuilder} class test.
     */
    @Test
    public void setFormatExcelSemicolonTest() {
        CsvParserBuilder csvParserBuilder = CsvParserBuilder.getInstance().setFormat(CsvFormat.EXCEL_SEMICOLON);

        List<List<String>> result1 = csvParserBuilder.parse("a,b;c");
        Assertions.assertThat(result1).hasSize(1);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a,b", "c");

        List<List<String>> result2 = csvParserBuilder.parse("a\rb\nc\r\nd");
        Assertions.assertThat(result2).hasSize(2);
        Assertions.assertThat(result2.get(0)).containsExactlyInOrder("a\rb\nc");
        Assertions.assertThat(result2.get(1)).containsExactlyInOrder("d");

        try {
            csvParserBuilder.parse("a;b;c\r\nd;e\r\n");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a;b;c\\r\\nd;e\\r\\n\".");
        }

        try {
            csvParserBuilder.parse("a\r\n\r\nb\r\n\r\nc");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a\\r\\n\\r\\n\".");
        }

        List<List<String>> result5 = csvParserBuilder.parse("1234567890");
        Assertions.assertThat(result5).hasSize(1);
        Assertions.assertThat(result5.get(0)).containsExactlyInOrder("1234567890");
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

        csvParserBuilder.setCommaSeparator(true);
        List<List<String>> result1 = csvParserBuilder.parse("a,b");
        Assertions.assertThat(result1).hasSize(1);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a", "b");

        csvParserBuilder.setCommaSeparator(false);
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

        csvParserBuilder.setSemicolonSeparator(true);
        List<List<String>> result1 = csvParserBuilder.parse("a;b");
        Assertions.assertThat(result1).hasSize(1);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a", "b");

        csvParserBuilder.setSemicolonSeparator(false);
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

        csvParserBuilder.setCrSeparator(true);
        List<List<String>> result1 = csvParserBuilder.parse("a\rb");
        Assertions.assertThat(result1).hasSize(2);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result1.get(1)).containsExactlyInOrder("b");

        csvParserBuilder.setCrSeparator(false);
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

        csvParserBuilder.setLfSeparator(true);
        List<List<String>> result1 = csvParserBuilder.parse("a\nb");
        Assertions.assertThat(result1).hasSize(2);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result1.get(1)).containsExactlyInOrder("b");

        csvParserBuilder.setLfSeparator(false);
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

        csvParserBuilder.setCrLfSeparator(true);
        List<List<String>> result1 = csvParserBuilder.parse("a\r\nb");
        Assertions.assertThat(result1).hasSize(2);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result1.get(1)).containsExactlyInOrder("b");

        csvParserBuilder.setCrLfSeparator(false);
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
            csvParserBuilder.setColumnCountCheckEnabled(true);
            csvParserBuilder.parse("a,b\nc,d\ne,f,g");
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"a,b\\nc,d\\ne,f,g\".");
        }

        csvParserBuilder.setColumnCountCheckEnabled(false);
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

        csvParserBuilder.setSkipEmptyRowsEnabled(true);
        List<List<String>> result1 = csvParserBuilder.parse("a\nb\n\nc\n\n");
        Assertions.assertThat(result1).hasSize(3);
        Assertions.assertThat(result1.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result1.get(1)).containsExactlyInOrder("b");
        Assertions.assertThat(result1.get(2)).containsExactlyInOrder("c");

        csvParserBuilder.setSkipEmptyRowsEnabled(false);
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
            csvParserBuilder.setMaxColumnLength(3);
            csvParserBuilder.parse("abc,12345", new NoopEventHandler());
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnLengthException ex) {
            Assertions.assertThat(ex).hasMessage("Maximum column value length exceeded. Last characters: \"abc,1234\".");
        }

        csvParserBuilder.setMaxColumnLength(5);
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
            csvParserBuilder.setMaxColumnLengthCheckEnabled(true);
            csvParserBuilder.parse("abc,12345", new NoopEventHandler());
            Assertions.fail("CsvParserBuilder test fail");
        } catch (WrongColumnLengthException ex) {
            Assertions.assertThat(ex).hasMessage("Maximum column value length exceeded. Last characters: \"abc,1234\".");
        }

        csvParserBuilder.setMaxColumnLengthCheckEnabled(false);
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

}
