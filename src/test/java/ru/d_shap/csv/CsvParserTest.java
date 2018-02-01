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
public final class CsvParserTest extends CsvTest {

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
    public void validateTest() {
        try {
            CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
            new CsvParser(csvParserConfiguration);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No column separator is specified.");
        }
        try {
            CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
            csvParserConfiguration.setCommaSeparator(true);
            new CsvParser(csvParserConfiguration);
            Assertions.fail("CsvParser test fail");
        } catch (WrongRowSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No row separator is specified.");
        }
        try {
            CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
            csvParserConfiguration.setCrLfSeparator(true);
            new CsvParser(csvParserConfiguration);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No column separator is specified.");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithSemicolonSeparatorTest() {
        String csv = "true;1.0.1;5";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("true", "1.0.1", "5");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithCommaSeparatorTest() {
        String csv = "true,1.0.1,5";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("true", "1.0.1", "5");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithEmptyValuesTest() {
        String csv = ";a;;b;;;c;";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("", "a", "", "b", "", "", "c", "");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithSpecialCharactersTest() {
        String csv = "true,\"aa,bb;\r\na\",\"5\"";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("true", "aa,bb;\r\na", "5");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithEmptySpecialCharactersTest() {
        String csv = "\"\",\"\",,\"\"";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
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
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("a\"b", "ab", "\"a", "b\"");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithNotClosedQuotTest() {
        try {
            String csv = "aaa,bb,\"ccc,dd";
            createCsvParser(true, true, true, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("End of input obtained. Last characters: \"aaa,bb,\"ccc,dd\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithNotOpenedQuotTest() {
        try {
            String csv = "aaa,bb,ccc\",dd";
            createCsvParser(true, true, true, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: '\"' (34). Last characters: \"aaa,bb,ccc\"\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithOpenQuotInTheMiddleTest() {
        try {
            String csv = "aaa,bb,c\"c,dd";
            createCsvParser(true, true, true, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: '\"' (34). Last characters: \"aaa,bb,c\"\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithCloseQuotInTheMiddleTest() {
        try {
            String csv = "aaa,bb,\"ccc\"cc,dd";
            createCsvParser(true, true, true, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: 'c' (99). Last characters: \"aaa,bb,\"ccc\"c\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseRowsWithCrSeparatorTest() {
        String csv = "a\rb\rc";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
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
    public void parseRowsWithLfSeparatorTest() {
        String csv = "a\nb\nc";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
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
    public void parseRowsWithCrLfSeparatorTest() {
        String csv = "a\r\nb\r\nc";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
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
    public void parseRowsWithEmptyValuesTest() {
        String csv = "\naaa\n\nbbb\n\n";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
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
    public void parseRowsWithSpecialCharactersTest() {
        String csv = "\",\"\r\n\"\"\"\"\r\n\"aaa;bbb\"\r\n";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
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
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
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
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEmpty();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = NullPointerException.class)
    public void parseNullCsvTest() {
        createCsvParser(true, true, true, true, true).parse((String) null);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseStringBuilderCsvTest() {
        StringBuilder csv = new StringBuilder();
        csv.append("a,,false\nb,true,\n\n,c,d\n");
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
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
        createCsvParser(true, true, true, true, true).parse(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void checkColumnCountTest() {
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
    public void checkColumnCountFailTest() {
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
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(reader);
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
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEmpty();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = NullPointerException.class)
    public void parseNullReaderCsvTest() {
        createCsvParser(true, true, true, true, true).parse((Reader) null);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readerCheckColumnCountTest() {
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
    public void readerCheckColumnCountFailTest() {
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
        createCsvParser(true, true, true, true, true).parse(csv, null);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void noopHandlerCheckColumnCountTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(csv, new NoopEventHandler());
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = WrongColumnCountException.class)
    public void noopHandlerCheckColumnCountFailTest() {
        String csv = "1,2\r\n3,4,5\r\n";
        CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(csv, new NoopEventHandler());
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readerNoopHandlerCheckColumnCountTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        Reader reader = new StringReader(csv);
        CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(reader, new NoopEventHandler());
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = WrongColumnCountException.class)
    public void readerNoopHandlerCheckColumnCountFailTest() {
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
        createCsvParser(true, true, true, true, true).parse(csv, eventHandler);
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
        createCsvParser(true, true, true, true, true).parse(reader, eventHandler);
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
        createCsvParser(true, true, true, true, true).parse(csv, eventHandler);
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
        createCsvParser(true, true, true, true, true).parse(reader, eventHandler);
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
        createCsvParser(true, true, true, true, true).parse(csv, eventHandler);
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
        createCsvParser(true, true, true, true, true).parse(reader, eventHandler);
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
        createCsvParser(true, true, true, true, true).parse(csv, eventHandler);
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
        createCsvParser(true, true, true, true, true).parse(reader, eventHandler);
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
        ErrorOnReadReader reader = new ErrorOnReadReader();
        createCsvParser(true, true, true, true, true).parse(reader);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void wrongColumnCountExceptionMessageTest() {
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
        List<List<String>> result = createCsvParser(false, true, false, true, false).parse(csv);
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
        List<List<String>> result = createCsvParser(false, true, true, true, false).parse(csv);
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
        List<List<String>> result = createCsvParserWithColumnCountCheck(true, false, true, true, true).parse(csv);
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
        List<List<String>> result = createCsvParserWithColumnCountCheck(true, true, true, false, false).parse(csv);
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
        List<List<String>> result = createCsvParserWithColumnCountCheck(true, true, true, true, false).parse(csv);
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
        List<List<String>> result = createCsvParserWithColumnCountCheck(false, true, true, false, false).parse(csv);
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
        List<List<String>> result = createCsvParserWithColumnCountCheck(false, true, true, true, false).parse(csv);
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
        createCsvParser(false, true, true, true, true).parse(csv, handler);
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
        createCsvParser(true, true, true, false, false).parse(csv, handler);
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
        createCsvParser(true, true, true, true, false).parse(csv, handler);
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
        createCsvParser(false, true, true, false, false).parse(csv, handler);
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
        createCsvParser(false, true, true, true, false).parse(csv, handler);
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
        createCsvParserWithColumnCountCheck(true, false, true, true, true).parse(csv, handler);
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
        createCsvParserWithColumnCountCheck(true, true, false, false, true).parse(csv, handler);
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
        createCsvParserWithColumnCountCheck(true, true, false, true, true).parse(csv, handler);
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
        createCsvParserWithColumnCountCheck(true, false, false, false, true).parse(csv, handler);
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
        createCsvParserWithColumnCountCheck(true, false, false, true, true).parse(csv, handler);
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
        List<List<String>> result = createCsvParser(false, true, true, true, true).parse(reader);
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
        List<List<String>> result = createCsvParser(true, true, false, true, false).parse(reader);
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
        List<List<String>> result = createCsvParser(true, true, true, true, false).parse(reader);
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
        List<List<String>> result = createCsvParser(false, true, false, true, false).parse(reader);
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
        List<List<String>> result = createCsvParser(false, true, true, true, false).parse(reader);
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
        List<List<String>> result = createCsvParserWithColumnCountCheck(true, false, true, true, true).parse(reader);
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
        List<List<String>> result = createCsvParserWithColumnCountCheck(true, true, true, false, false).parse(reader);
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
        List<List<String>> result = createCsvParserWithColumnCountCheck(true, true, true, true, false).parse(reader);
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
        List<List<String>> result = createCsvParserWithColumnCountCheck(false, true, true, false, false).parse(reader);
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
        List<List<String>> result = createCsvParserWithColumnCountCheck(false, true, true, true, false).parse(reader);
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
        createCsvParser(false, true, true, true, true).parse(reader, handler);
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
        createCsvParser(true, true, true, false, false).parse(reader, handler);
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
        createCsvParser(true, true, true, true, false).parse(reader, handler);
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
        createCsvParser(false, true, true, false, false).parse(reader, handler);
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
        createCsvParser(false, true, true, true, false).parse(reader, handler);
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
        createCsvParserWithColumnCountCheck(true, false, true, true, true).parse(reader, handler);
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
        createCsvParserWithColumnCountCheck(true, true, false, false, true).parse(reader, handler);
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
        createCsvParserWithColumnCountCheck(true, true, false, true, true).parse(reader, handler);
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
        createCsvParserWithColumnCountCheck(true, false, false, false, true).parse(reader, handler);
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
        createCsvParserWithColumnCountCheck(true, false, false, true, true).parse(reader, handler);
        Assertions.assertThat(handler.getColumnLengths()).isNotNull();
        Assertions.assertThat(handler.getColumnLengths()).hasSize(2);
        Assertions.assertThat(handler.getColumnLengths().get(0)).containsExactlyInOrder(1, 3);
        Assertions.assertThat(handler.getColumnLengths().get(1)).containsExactlyInOrder(3, 1);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(true, false, true, false, false).parse(csv);
        Assertions.assertThat(result).hasSize(6);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4;5;6\n\n7", "8;9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("\n");
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("\na", "b", "c", "d");
        Assertions.assertThat(result.get(5)).containsExactlyInOrder("\n");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceWithZeroCodeByteTest() {
        String csv = "1,2,3\u00004\r\n";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\u00004");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void closeReaderTest() {
        IsClosedReader reader = new IsClosedReader();
        Assertions.assertThat(reader.isClosed()).isFalse();
        CsvParserBuilder.getInstance().parse(reader);
        Assertions.assertThat(reader.isClosed()).isTrue();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void errorOnCloseReaderTest() {
        try {
            ErrorOnCloseReader reader = new ErrorOnCloseReader();
            CsvParserBuilder.getInstance().parse(reader);
            Assertions.fail("CsvParser test fail");
        } catch (CsvIOException ex) {
            Assertions.assertThat(ex).hasMessage("ERROR");
        }
    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class ErrorOnReadReader extends Reader {

        ErrorOnReadReader() {
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

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class IsClosedReader extends Reader {

        private boolean _closed;

        IsClosedReader() {
            super();
            _closed = false;
        }

        @Override
        public int read(final char[] cbuf, final int off, final int len) throws IOException {
            return -1;
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
    private static final class ErrorOnCloseReader extends Reader {

        ErrorOnCloseReader() {
            super();
        }

        @Override
        public int read(final char[] cbuf, final int off, final int len) throws IOException {
            return -1;
        }

        @Override
        public void close() throws IOException {
            throw new IOException("ERROR");
        }

    }

}
