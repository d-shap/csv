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
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.csv.handler.ColumnCountEventHandler;
import ru.d_shap.csv.handler.ColumnLengthEventHandler;
import ru.d_shap.csv.handler.DimensionEventHandler;
import ru.d_shap.csv.handler.NoopEventHandler;
import ru.d_shap.csv.handler.RestrictedListEventHandler;

/**
 * Tests for {@link CsvParser}.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvParserTest {

    /**
     * Test class constructor.
     */
    public CsvParserTest() {
        super();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithSemicolonsTest() {
        String csv = "true;1.0.1;5";
        List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("true", "1.0.1", "5");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithCommasTest() {
        String csv = "true,1.0.1,5";
        List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("true", "1.0.1", "5");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithSpecialsTest() {
        String csv = "true,\"aa,bb;\r\na\",\"5\"";
        List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("true", "aa,bb;\r\na", "5");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseEmptyColumnsTest() {
        String csv = ";a;;b;;;c;";
        List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("", "a", "", "b", "", "", "c", "");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseEmptySpecialColumnsTest() {
        String csv = "\"\",\"\",,\"\"";
        List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("", "", "", "");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithQuotsTest() {
        String csv = "\"a\"\"b\",ab;\"\"\"a\",\"b\"\"\"";
        List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("a\"b", "ab", "\"a", "b\"");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvParseException.class)
    public void wrongQuotFailTest() {
        String csv = "aaa,bb,c\"c,dd";
        CsvParserBuilder.getInstance().build().parse(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvParseException.class)
    public void notClosedQuotFailTest() {
        String csv = "aaa,bb,\"ccc,dd";
        CsvParserBuilder.getInstance().build().parse(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvParseException.class)
    public void notOpenedQuotFailTest() {
        String csv = "aaa,bb,ccc\",dd";
        CsvParserBuilder.getInstance().build().parse(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvParseException.class)
    public void notAllColumnInQuotFailTest() {
        String csv = "aaa,bb,\"ccc\"cc,dd";
        CsvParserBuilder.getInstance().build().parse(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseRowsWithCrTest() {
        String csv = "a\rb\rc";
        List<List<String>> result = Builder.createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("b");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("c");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseRowsWithLfTest() {
        String csv = "a\nb\nc";
        List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("b");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("c");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseRowsWithCrLfTest() {
        String csv = "a\r\nb\r\nc";
        List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("b");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("c");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseEmptyRowsTest() {
        String csv = "\naaa\n\nbbb\n\n";
        List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder();
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("aaa");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder();
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("bbb");
        Assertions.assertThat(result.get(4)).containsExactlyInOrder();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseRowsWithSpecialsTest() {
        String csv = "\",\"\r\n\"\"\"\"\r\n\"aaa;bbb\"\r\n";
        List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder(",");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("\"");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("aaa;bbb");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseTableTest() {
        String csv = "a,,false\nb,true,\n\n,c,d\n";
        List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("a", "", "false");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("b", "true", "");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder();
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseEmptyCsvTest() {
        String csv = "";
        List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEmpty();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = NullPointerException.class)
    public void parseNullCsvTest() {
        CsvParserBuilder.getInstance().build().parse((String) null);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseStringBuilderCsvTest() {
        StringBuilder csv = new StringBuilder();
        csv.append("a,,false\nb,true,\n\n,c,d\n");
        List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("a", "", "false");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("b", "true", "");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder();
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvParseException.class)
    public void unquotedQuotFailTest() {
        String csv = "one;\"t\"wo\"\nthree;four";
        CsvParserBuilder.getInstance().build().parse(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void checkRectangularTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        List<List<String>> result = CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5", "6");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = WrongColumnCountException.class)
    public void checkRectangularFailTest() {
        String csv = "1,2\r\n3,4,5\r\n";
        CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderTest() {
        String csv = ";,\n,aaa,bbb,\"a\"\"b\";\r";
        Reader reader = new StringReader(csv);
        List<List<String>> result = Builder.createCsvParser(true, true, true, true, true).parse(reader);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("", "", "");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("", "aaa", "bbb", "a\"b", "");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseEmptyReaderCsvTest() {
        String csv = "";
        Reader reader = new StringReader(csv);
        List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEmpty();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = NullPointerException.class)
    public void parseNullReaderCsvTest() {
        CsvParserBuilder.getInstance().build().parse((Reader) null);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readerCheckRectangularTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        Reader reader = new StringReader(csv);
        List<List<String>> result = CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(reader);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5", "6");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = WrongColumnCountException.class)
    public void readerCheckRectangularFailTest() {
        String csv = "1,2\r\n3,4,5\r\n";
        Reader reader = new StringReader(csv);
        CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(reader);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = NullPointerException.class)
    public void parseNullHandlerTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        CsvParserBuilder.getInstance().build().parse(csv, null);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void noopHandlerCheckRectangularTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(csv, new NoopEventHandler());
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = WrongColumnCountException.class)
    public void noopHandlerCheckRectangularFailTest() {
        String csv = "1,2\r\n3,4,5\r\n";
        CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(csv, new NoopEventHandler());
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readerNoopHandlerCheckRectangularTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        Reader reader = new StringReader(csv);
        CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(reader, new NoopEventHandler());
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = WrongColumnCountException.class)
    public void readerNoopHandlerCheckRectangularFailTest() {
        String csv = "1,2\r\n3,4,5\r\n";
        Reader reader = new StringReader(csv);
        CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(reader, new NoopEventHandler());
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void dimensionHandlerTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        DimensionEventHandler eventHandler = new DimensionEventHandler();
        CsvParserBuilder.getInstance().build().parse(csv, eventHandler);
        Assertions.assertThat(eventHandler.getRowCount()).isEqualTo(2);
        Assertions.assertThat(eventHandler.getColumnCount()).isEqualTo(3);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readerDimensionHandlerTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        Reader reader = new StringReader(csv);
        DimensionEventHandler eventHandler = new DimensionEventHandler();
        CsvParserBuilder.getInstance().build().parse(reader, eventHandler);
        Assertions.assertThat(eventHandler.getRowCount()).isEqualTo(2);
        Assertions.assertThat(eventHandler.getColumnCount()).isEqualTo(3);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void columnCountHandlerTest() {
        String csv = "1,2\r\n3,4,5\r\n6";
        ColumnCountEventHandler eventHandler = new ColumnCountEventHandler();
        CsvParserBuilder.getInstance().build().parse(csv, eventHandler);
        Assertions.assertThat(eventHandler.getColumnCounts()).isNotNull();
        Assertions.assertThat(eventHandler.getColumnCounts()).containsExactlyInOrder(2, 3, 1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readerColumnCountHandlerTest() {
        String csv = "1,2\r\n3,4,5\r\n6";
        Reader reader = new StringReader(csv);
        ColumnCountEventHandler eventHandler = new ColumnCountEventHandler();
        CsvParserBuilder.getInstance().build().parse(reader, eventHandler);
        Assertions.assertThat(eventHandler.getColumnCounts()).isNotNull();
        Assertions.assertThat(eventHandler.getColumnCounts()).containsExactlyInOrder(2, 3, 1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void columnLengthHandlerTest() {
        String csv = "aaa,bb\r\ncccc,dddd,eeeee\r\nf";
        ColumnLengthEventHandler eventHandler = new ColumnLengthEventHandler();
        CsvParserBuilder.getInstance().build().parse(csv, eventHandler);
        Assertions.assertThat(eventHandler.getColumnLengths()).isNotNull();
        Assertions.assertThat(eventHandler.getColumnLengths()).hasSize(3);
        Assertions.assertThat(eventHandler.getColumnLengths().get(0)).containsAllInOrder(3, 2);
        Assertions.assertThat(eventHandler.getColumnLengths().get(1)).containsAllInOrder(4, 4, 5);
        Assertions.assertThat(eventHandler.getColumnLengths().get(2)).containsAllInOrder(1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readerColumnLengthHandlerTest() {
        String csv = "aaa,bb\r\ncccc,dddd,eeeee\r\nf";
        Reader reader = new StringReader(csv);
        ColumnLengthEventHandler eventHandler = new ColumnLengthEventHandler();
        CsvParserBuilder.getInstance().build().parse(reader, eventHandler);
        Assertions.assertThat(eventHandler.getColumnLengths()).isNotNull();
        Assertions.assertThat(eventHandler.getColumnLengths()).hasSize(3);
        Assertions.assertThat(eventHandler.getColumnLengths().get(0)).containsAllInOrder(3, 2);
        Assertions.assertThat(eventHandler.getColumnLengths().get(1)).containsAllInOrder(4, 4, 5);
        Assertions.assertThat(eventHandler.getColumnLengths().get(2)).containsAllInOrder(1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void restrictedListEventHandlerTest() {
        String csv = "long value 1,long value 2\r\nvalue,val,long value 3\r\nvalue";
        RestrictedListEventHandler eventHandler = new RestrictedListEventHandler(5, "...");
        CsvParserBuilder.getInstance().build().parse(csv, eventHandler);
        Assertions.assertThat(eventHandler.getCsv()).isNotNull();
        Assertions.assertThat(eventHandler.getCsv()).hasSize(3);
        Assertions.assertThat(eventHandler.getCsv().get(0)).containsAllInOrder("lo...", "lo...");
        Assertions.assertThat(eventHandler.getCsv().get(1)).containsAllInOrder("value", "val", "lo...");
        Assertions.assertThat(eventHandler.getCsv().get(2)).containsAllInOrder("value");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readerRestrictedListEventHandlerTest() {
        String csv = "long value 1,long value 2\r\nvalue,val,long value 3\r\nvalue";
        Reader reader = new StringReader(csv);
        RestrictedListEventHandler eventHandler = new RestrictedListEventHandler(5, "...");
        CsvParserBuilder.getInstance().build().parse(reader, eventHandler);
        Assertions.assertThat(eventHandler.getCsv()).isNotNull();
        Assertions.assertThat(eventHandler.getCsv()).hasSize(3);
        Assertions.assertThat(eventHandler.getCsv().get(0)).containsAllInOrder("lo...", "lo...");
        Assertions.assertThat(eventHandler.getCsv().get(1)).containsAllInOrder("value", "val", "lo...");
        Assertions.assertThat(eventHandler.getCsv().get(2)).containsAllInOrder("value");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvIOException.class)
    public void parseReaderExceptionFailTest() {
        Reader reader = new ErrorReader();
        CsvParserBuilder.getInstance().build().parse(reader);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void notRectangularExceptionMessageTest() {
        try {
            String csv = "1,2,3\r\n4,5,6,7";
            CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(csv, new NoopEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\n4,5,6,7\".");
        }
        try {
            String csv = "1,2,3\r\n4,5,6,7\r\n";
            CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(csv, new NoopEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\n4,5,6,7\\r\\n\".");
        }
        try {
            String csv = "1,2,3\r\n4,5,6,7,8\r\n";
            CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(csv, new NoopEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\n4,5,6,7,\".");
        }
        try {
            String csv = "1,2,3\r\n4,5";
            CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(csv, new NoopEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\n4,5\".");
        }
        try {
            String csv = "1,2,3\r\n4,5\r\n";
            CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(csv, new NoopEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\n4,5\\r\\n\".");
        }
        try {
            String csv = "1,2,3\n4,5\n";
            CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(csv, new NoopEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\n4,5\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithColumnSeparatorsTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        List<List<String>> result = CsvParserBuilder.getInstance().setCommaSeparator(false).setSemicolonSeparator(true).build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4,5,6");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithRowSeparatorsTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        List<List<String>> result = CsvParserBuilder.getInstance().setCrSeparator(false).setLfSeparator(true).setCrLfSeparator(false).build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\r");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5", "6\r");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithRowSeparators2Test() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        List<List<String>> result = CsvParserBuilder.getInstance().setCrSeparator(true).setLfSeparator(true).setCrLfSeparator(false).build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4", "5", "6");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithColumnAndRowSeparatorsTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        List<List<String>> result = Builder.createCsvParser(false, true, false, true, false).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3\r");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4,5,6\r");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithColumnAndRowSeparators2Test() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        List<List<String>> result = Builder.createCsvParser(false, true, true, true, false).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4,5,6");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithCheckAndColumnSeparatorsTest() {
        String csv = "1,2;3\r\n4;5,6\r\n";
        List<List<String>> result = Builder.createCsvParser(true, false, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2;3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4;5", "6");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithCheckAndRowSeparatorsTest() {
        String csv = "1,2;3\n4;5,6\n";
        List<List<String>> result = Builder.createCsvParser(true, true, true, false, false, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\n4", "5", "6\n");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithCheckAndRowSeparators2Test() {
        String csv = "1,2;3\n4;5,6\n";
        List<List<String>> result = Builder.createCsvParser(true, true, true, true, false, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5", "6");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithCheckColumnAndRowSeparatorsTest() {
        String csv = "1,2;3\n4;5,6\n";
        List<List<String>> result = Builder.createCsvParser(false, true, true, false, false, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2", "3\n4", "5,6\n");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithCheckColumnAndRowSeparators2Test() {
        String csv = "1,2;3\n4;5,6\n";
        List<List<String>> result = Builder.createCsvParser(false, true, true, true, false, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5,6");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithHandlerAndColumnSeparatorsTest() {
        String csv = "1,2;3\n4;5,6;7\n";
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(false, true, true, true, true).parse(csv, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(3, 1);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(1, 3, 1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithHandlerAndRowSeparatorsTest() {
        String csv = "1,2;3\n4;5,6\n\r7\n";
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(true, true, true, false, false).parse(csv, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(1, 1, 3, 1, 2);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(2);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithHandlerAndRowSeparators2Test() {
        String csv = "1,2;3\n4;5,6,7\n";
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(true, true, true, true, false).parse(csv, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(1, 1, 1);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(1, 1, 1, 1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithHandlerColumnAndRowSeparatorsTest() {
        String csv = "1,2;3\n4;5,6\n\r7\n";
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(false, true, true, false, false).parse(csv, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(3, 3, 4);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(2);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithHandlerColumnAndRowSeparators2Test() {
        String csv = "1,2;3\n4;5,6;7\n";
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(false, true, true, true, false).parse(csv, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(3, 1);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(1, 3, 1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithHandlerCheckAndColumnSeparatorsTest() {
        String csv = "1,2;3\n4;5,6\n";
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(true, false, true, true, true, true).parse(csv, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(1, 3);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(3, 1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithHandlerCheckAndRowSeparatorsTest() {
        String csv = "1,2;3\n4;5,6\n";
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(true, true, false, false, true, true).parse(csv, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(1);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(1, 1, 3, 1, 2);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithHandlerCheckAndRowSeparators2Test() {
        String csv = "1,2;3\n4;5,6\n";
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(true, true, false, true, true, true).parse(csv, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(1, 1, 1);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(1, 1, 1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithHandlerCheckColumnAndRowSeparatorsTest() {
        String csv = "1,2;3\n4;5,6\n";
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(true, false, false, false, true, true).parse(csv, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(1);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(1, 7, 2);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithHandlerCheckColumnAndRowSeparators2Test() {
        String csv = "1,2;3\n4;5,6\n";
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(true, false, false, true, true, true).parse(csv, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(1, 3);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(3, 1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithColumnSeparatorsTest() {
        String csv = "1,2,3\r\n4,5,6;7\r\n";
        Reader reader = new StringReader(csv);
        List<List<String>> result = Builder.createCsvParser(false, true, true, true, true).parse(reader);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4,5,6", "7");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithRowSeparatorsTest() {
        String csv = "1,2,3\r\n4,5,6,7\r\n";
        Reader reader = new StringReader(csv);
        List<List<String>> result = Builder.createCsvParser(true, true, false, true, false).parse(reader);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\r");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5", "6", "7\r");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithRowSeparators2Test() {
        String csv = "1,2,3\r\n4,5,6,7\r\n";
        Reader reader = new StringReader(csv);
        List<List<String>> result = Builder.createCsvParser(true, true, true, true, false).parse(reader);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4", "5", "6", "7");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithColumnAndRowSeparatorsTest() {
        String csv = "1,2,3\r\n4,5,6;7\r\n";
        Reader reader = new StringReader(csv);
        List<List<String>> result = Builder.createCsvParser(false, true, false, true, false).parse(reader);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3\r");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4,5,6", "7\r");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithColumnAndRowSeparators2Test() {
        String csv = "1,2,3\r\n4,5,6;7\r\n";
        Reader reader = new StringReader(csv);
        List<List<String>> result = Builder.createCsvParser(false, true, true, true, false).parse(reader);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4,5,6", "7");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithCheckAndColumnSeparatorsTest() {
        String csv = "1,2;3\r\n4;5,6\r\n";
        Reader reader = new StringReader(csv);
        List<List<String>> result = Builder.createCsvParser(true, false, true, true, true, true).parse(reader);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2;3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4;5", "6");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithCheckAndRowSeparatorsTest() {
        String csv = "1,2;3\n4;5,6\n";
        Reader reader = new StringReader(csv);
        List<List<String>> result = Builder.createCsvParser(true, true, true, false, false, true).parse(reader);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\n4", "5", "6\n");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithCheckAndRowSeparators2Test() {
        String csv = "1,2;3\n4;5,6\n";
        Reader reader = new StringReader(csv);
        List<List<String>> result = Builder.createCsvParser(true, true, true, true, false, true).parse(reader);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5", "6");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithCheckColumnAndRowSeparatorsTest() {
        String csv = "1,2;3\n4;5,6\n";
        Reader reader = new StringReader(csv);
        List<List<String>> result = Builder.createCsvParser(false, true, true, false, false, true).parse(reader);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2", "3\n4", "5,6\n");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithCheckColumnAndRowSeparators2Test() {
        String csv = "1,2;3\n4;5,6\n";
        Reader reader = new StringReader(csv);
        List<List<String>> result = Builder.createCsvParser(false, true, true, true, false, true).parse(reader);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5,6");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithHandlerAndColumnSeparatorsTest() {
        String csv = "1,2;3\n4;5,6\n7\n";
        Reader reader = new StringReader(csv);
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(false, true, true, true, true).parse(reader, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(3);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(3, 1);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(1, 3);
        Assertions.assertThat(handler.getColumnLengths().get(2)).containsExactlyInOrder(1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithHandlerAndRowSeparatorsTest() {
        String csv = "1,2;3\n4;5,6\n\r7\r";
        Reader reader = new StringReader(csv);
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(true, true, true, false, false).parse(reader, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(1, 1, 3, 1, 2);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithHandlerAndRowSeparators2Test() {
        String csv = "1,2;3\n4;5,6;7\n";
        Reader reader = new StringReader(csv);
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(true, true, true, true, false).parse(reader, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(1, 1, 1);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(1, 1, 1, 1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithHandlerColumnAndRowSeparatorsTest() {
        String csv = "1,2;3\n4;5,6\n\r7\r";
        Reader reader = new StringReader(csv);
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(false, true, true, false, false).parse(reader, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(3, 3, 4);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithHandlerColumnAndRowSeparators2Test() {
        String csv = "1,2;3\n4;5,6;7\n";
        Reader reader = new StringReader(csv);
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(false, true, true, true, false).parse(reader, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(3, 1);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(1, 3, 1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithHandlerCheckAndColumnSeparatorsTest() {
        String csv = "1,2;3\n4;5,6\n";
        Reader reader = new StringReader(csv);
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(true, false, true, true, true, true).parse(reader, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(1, 3);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(3, 1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithHandlerCheckAndRowSeparatorsTest() {
        String csv = "1,2;3\n4;5,6\n";
        Reader reader = new StringReader(csv);
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(true, true, false, false, true, true).parse(reader, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(1);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(1, 1, 3, 1, 2);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithHandlerCheckAndRowSeparators2Test() {
        String csv = "1,2;3\n4;5,6\n";
        Reader reader = new StringReader(csv);
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(true, true, false, true, true, true).parse(reader, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(1, 1, 1);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(1, 1, 1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithHandlerCheckColumnAndRowSeparatorsTest() {
        String csv = "1,2;3\n4;5,6\n";
        Reader reader = new StringReader(csv);
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(true, false, false, false, true, true).parse(reader, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(1);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(1, 7, 2);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderWithHandlerCheckColumnAndRowSeparators2Test() {
        String csv = "1,2;3\n4;5,6\n";
        Reader reader = new StringReader(csv);
        ColumnLengthEventHandler handler = new ColumnLengthEventHandler();
        Builder.createCsvParser(true, false, false, true, true, true).parse(reader, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(1, 3);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(3, 1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithZeroCodeByteTest() {
        String csv = "1,2,3\u00004\r\n";
        List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\u00004");
    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class ErrorReader extends Reader {

        ErrorReader() {
            super();
        }

        @Override
        public int read(final char[] cbuf, final int off, final int len) throws IOException {
            throw new IOException("ERROR");
        }

        @Override
        public void close() throws IOException {
            // Ignore
        }

    }

}
