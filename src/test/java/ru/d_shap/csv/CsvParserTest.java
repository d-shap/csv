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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

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
     *
     * @throws IllegalAccessException exception in test.
     * @throws InstantiationException exception in test.
     */
    @Test(expected = IllegalAccessException.class)
    public void testConstructorPrivate() throws IllegalAccessException, InstantiationException {
        CsvParser.class.newInstance();
    }

    /**
     * {@link CsvParser} class test.
     *
     * @throws IllegalAccessException    exception in test.
     * @throws InstantiationException    exception in test.
     * @throws InvocationTargetException exception in test.
     */
    @Test
    public void constructorInaccessibilityTest() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Constructor[] ctors = CsvParser.class.getDeclaredConstructors();
        Assert.assertEquals(1, ctors.length);
        Constructor ctor = ctors[0];
        Assert.assertFalse(ctor.isAccessible());
        ctor.setAccessible(true);
        Assert.assertEquals(CsvParser.class, ctor.newInstance().getClass());
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithSemicolonsTest() {
        String csv = "true;1.0.1;5";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(3, result.get(0).size());
        Assert.assertEquals("true", result.get(0).get(0));
        Assert.assertEquals("1.0.1", result.get(0).get(1));
        Assert.assertEquals("5", result.get(0).get(2));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithCommasTest() {
        String csv = "true,1.0.1,5";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(3, result.get(0).size());
        Assert.assertEquals("true", result.get(0).get(0));
        Assert.assertEquals("1.0.1", result.get(0).get(1));
        Assert.assertEquals("5", result.get(0).get(2));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithSpecialsTest() {
        String csv = "true,\"aa,bb;\r\na\",\"5\"";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(3, result.get(0).size());
        Assert.assertEquals("true", result.get(0).get(0));
        Assert.assertEquals("aa,bb;\r\na", result.get(0).get(1));
        Assert.assertEquals("5", result.get(0).get(2));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseEmptyColumnsTest() {
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

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseEmptySpecialColumnsTest() {
        String csv = "\"\",\"\",,\"\"";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(4, result.get(0).size());
        Assert.assertEquals("", result.get(0).get(0));
        Assert.assertEquals("", result.get(0).get(1));
        Assert.assertEquals("", result.get(0).get(2));
        Assert.assertEquals("", result.get(0).get(3));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithQuotsTest() {
        String csv = "\"a\"\"b\",ab;\"\"\"a\",\"b\"\"\"";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(4, result.get(0).size());
        Assert.assertEquals("a\"b", result.get(0).get(0));
        Assert.assertEquals("ab", result.get(0).get(1));
        Assert.assertEquals("\"a", result.get(0).get(2));
        Assert.assertEquals("b\"", result.get(0).get(3));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvParseException.class)
    public void wrongQuotFailTest() {
        String csv = "aaa,bb,c\"c,dd";
        CsvParser.parseCsv(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvParseException.class)
    public void notClosedQuotFailTest() {
        String csv = "aaa,bb,\"ccc,dd";
        CsvParser.parseCsv(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvParseException.class)
    public void notOpenedQuotFailTest() {
        String csv = "aaa,bb,ccc\",dd";
        CsvParser.parseCsv(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvParseException.class)
    public void notAllColumnInQuotFailTest() {
        String csv = "aaa,bb,\"ccc\"cc,dd";
        CsvParser.parseCsv(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseRowsWithCrTest() {
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

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseRowsWithLfTest() {
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

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseRowsWithCrLfTest() {
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

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseEmptyRowsTest() {
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

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseRowsWithSpecialsTest() {
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

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseTableTest() {
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

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseEmptyCsvTest() {
        String csv = "";
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(0, result.size());
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseNullCsvTest() {
        String csv = null;
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(0, result.size());
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCharSequenceCsvTest() {
        StringBuilder csv = new StringBuilder();
        csv.append("a,,false\nb,true,\n\n,c,d\n");
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

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvParseException.class)
    public void unquotedQuotFailTest() {
        String csv = "one;\"t\"wo\"\nthree;four";
        CsvParser.parseCsv(csv);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void checkRectangularTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        List<List<String>> result = CsvParser.parseCsv(csv, true);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(3, result.get(0).size());
        Assert.assertEquals("1", result.get(0).get(0));
        Assert.assertEquals("2", result.get(0).get(1));
        Assert.assertEquals("3", result.get(0).get(2));
        Assert.assertEquals(3, result.get(1).size());
        Assert.assertEquals("4", result.get(1).get(0));
        Assert.assertEquals("5", result.get(1).get(1));
        Assert.assertEquals("6", result.get(1).get(2));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void checkRectangularFailTest() {
        String csv = "1,2\r\n3,4,5\r\n";
        CsvParser.parseCsv(csv, true);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseReaderTest() {
        String csv = ";,\n,aaa,bbb,\"a\"\"b\";\r";
        Reader reader = new StringReader(csv);
        List<List<String>> result = CsvParser.parseCsv(reader);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(3, result.get(0).size());
        Assert.assertEquals("", result.get(0).get(0));
        Assert.assertEquals("", result.get(0).get(1));
        Assert.assertEquals("", result.get(0).get(2));
        Assert.assertEquals(5, result.get(1).size());
        Assert.assertEquals("", result.get(1).get(0));
        Assert.assertEquals("aaa", result.get(1).get(1));
        Assert.assertEquals("bbb", result.get(1).get(2));
        Assert.assertEquals("a\"b", result.get(1).get(3));
        Assert.assertEquals("", result.get(1).get(4));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseNullReaderCsvTest() {
        Reader csv = null;
        List<List<String>> result = CsvParser.parseCsv(csv);
        Assert.assertEquals(0, result.size());
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readerCheckRectangularTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        Reader reader = new StringReader(csv);
        List<List<String>> result = CsvParser.parseCsv(reader, true);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(3, result.get(0).size());
        Assert.assertEquals("1", result.get(0).get(0));
        Assert.assertEquals("2", result.get(0).get(1));
        Assert.assertEquals("3", result.get(0).get(2));
        Assert.assertEquals(3, result.get(1).size());
        Assert.assertEquals("4", result.get(1).get(0));
        Assert.assertEquals("5", result.get(1).get(1));
        Assert.assertEquals("6", result.get(1).get(2));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void readerCheckRectangularFailTest() {
        String csv = "1,2\r\n3,4,5\r\n";
        Reader reader = new StringReader(csv);
        CsvParser.parseCsv(reader, true);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseNullHandlerTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        CsvParser.parseCsv(csv, null, true);
        CsvParser.parseCsv(csv, null, false);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void noopHandlerCheckRectangularTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        CsvParser.parseCsv(csv, new NoopEventHandler(), true);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void noopHandlerCheckRectangularFailTest() {
        String csv = "1,2\r\n3,4,5\r\n";
        CsvParser.parseCsv(csv, new NoopEventHandler(), true);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readerNoopHandlerCheckRectangularTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        Reader reader = new StringReader(csv);
        CsvParser.parseCsv(reader, new NoopEventHandler(), true);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void readerNoopHandlerCheckRectangularFailTest() {
        String csv = "1,2\r\n3,4,5\r\n";
        Reader reader = new StringReader(csv);
        CsvParser.parseCsv(reader, new NoopEventHandler(), true);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void dimensionHandlerTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        DimensionEventHandler eventHandler = new DimensionEventHandler();
        CsvParser.parseCsv(csv, eventHandler);
        Assert.assertEquals(2, eventHandler.getRowCount());
        Assert.assertEquals(3, eventHandler.getColumnCount());
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readerDimensionHandlerTest() {
        String csv = "1,2,3\r\n4,5,6\r\n";
        Reader reader = new StringReader(csv);
        DimensionEventHandler eventHandler = new DimensionEventHandler();
        CsvParser.parseCsv(reader, eventHandler);
        Assert.assertEquals(2, eventHandler.getRowCount());
        Assert.assertEquals(3, eventHandler.getColumnCount());
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void columnCountHandlerTest() {
        String csv = "1,2\r\n3,4,5\r\n6";
        ColumnCountEventHandler eventHandler = new ColumnCountEventHandler();
        CsvParser.parseCsv(csv, eventHandler);
        Assert.assertEquals(3, eventHandler.getColumnCounts().size());
        Assert.assertEquals(2, (int) eventHandler.getColumnCounts().get(0));
        Assert.assertEquals(3, (int) eventHandler.getColumnCounts().get(1));
        Assert.assertEquals(1, (int) eventHandler.getColumnCounts().get(2));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readerColumnCountHandlerTest() {
        String csv = "1,2\r\n3,4,5\r\n6";
        Reader reader = new StringReader(csv);
        ColumnCountEventHandler eventHandler = new ColumnCountEventHandler();
        CsvParser.parseCsv(reader, eventHandler);
        Assert.assertEquals(3, eventHandler.getColumnCounts().size());
        Assert.assertEquals(2, (int) eventHandler.getColumnCounts().get(0));
        Assert.assertEquals(3, (int) eventHandler.getColumnCounts().get(1));
        Assert.assertEquals(1, (int) eventHandler.getColumnCounts().get(2));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void columnLengthHandlerTest() {
        String csv = "aaa,bb\r\ncccc,dddd,eeeee\r\nf";
        ColumnLengthEventHandler eventHandler = new ColumnLengthEventHandler();
        CsvParser.parseCsv(csv, eventHandler);
        Assert.assertEquals(3, eventHandler.getColumnLengths().size());
        Assert.assertEquals(2, eventHandler.getColumnLengths().get(0).size());
        Assert.assertEquals(3, (int) eventHandler.getColumnLengths().get(0).get(0));
        Assert.assertEquals(2, (int) eventHandler.getColumnLengths().get(0).get(1));
        Assert.assertEquals(3, eventHandler.getColumnLengths().get(1).size());
        Assert.assertEquals(4, (int) eventHandler.getColumnLengths().get(1).get(0));
        Assert.assertEquals(4, (int) eventHandler.getColumnLengths().get(1).get(1));
        Assert.assertEquals(5, (int) eventHandler.getColumnLengths().get(1).get(2));
        Assert.assertEquals(1, eventHandler.getColumnLengths().get(2).size());
        Assert.assertEquals(1, (int) eventHandler.getColumnLengths().get(2).get(0));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readerColumnLengthHandlerTest() {
        String csv = "aaa,bb\r\ncccc,dddd,eeeee\r\nf";
        Reader reader = new StringReader(csv);
        ColumnLengthEventHandler eventHandler = new ColumnLengthEventHandler();
        CsvParser.parseCsv(reader, eventHandler);
        Assert.assertEquals(3, eventHandler.getColumnLengths().size());
        Assert.assertEquals(2, eventHandler.getColumnLengths().get(0).size());
        Assert.assertEquals(3, (int) eventHandler.getColumnLengths().get(0).get(0));
        Assert.assertEquals(2, (int) eventHandler.getColumnLengths().get(0).get(1));
        Assert.assertEquals(3, eventHandler.getColumnLengths().get(1).size());
        Assert.assertEquals(4, (int) eventHandler.getColumnLengths().get(1).get(0));
        Assert.assertEquals(4, (int) eventHandler.getColumnLengths().get(1).get(1));
        Assert.assertEquals(5, (int) eventHandler.getColumnLengths().get(1).get(2));
        Assert.assertEquals(1, eventHandler.getColumnLengths().get(2).size());
        Assert.assertEquals(1, (int) eventHandler.getColumnLengths().get(2).get(0));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void restrictedListEventHandlerTest() {
        String csv = "long value 1,long value 2\r\nvalue,val,long value 3\r\nvalue";
        RestrictedListEventHandler eventHandler = new RestrictedListEventHandler(5, "...");
        CsvParser.parseCsv(csv, eventHandler);
        Assert.assertEquals(3, eventHandler.getCsv().size());
        Assert.assertEquals(2, eventHandler.getCsv().get(0).size());
        Assert.assertEquals("lo...", eventHandler.getCsv().get(0).get(0));
        Assert.assertEquals("lo...", eventHandler.getCsv().get(0).get(0));
        Assert.assertEquals(3, eventHandler.getCsv().get(1).size());
        Assert.assertEquals("value", eventHandler.getCsv().get(1).get(0));
        Assert.assertEquals("val", eventHandler.getCsv().get(1).get(1));
        Assert.assertEquals("lo...", eventHandler.getCsv().get(1).get(2));
        Assert.assertEquals(1, eventHandler.getCsv().get(2).size());
        Assert.assertEquals("value", eventHandler.getCsv().get(2).get(0));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void readerRestrictedListEventHandlerTest() {
        String csv = "long value 1,long value 2\r\nvalue,val,long value 3\r\nvalue";
        Reader reader = new StringReader(csv);
        RestrictedListEventHandler eventHandler = new RestrictedListEventHandler(5, "...");
        CsvParser.parseCsv(reader, eventHandler);
        Assert.assertEquals(3, eventHandler.getCsv().size());
        Assert.assertEquals(2, eventHandler.getCsv().get(0).size());
        Assert.assertEquals("lo...", eventHandler.getCsv().get(0).get(0));
        Assert.assertEquals("lo...", eventHandler.getCsv().get(0).get(0));
        Assert.assertEquals(3, eventHandler.getCsv().get(1).size());
        Assert.assertEquals("value", eventHandler.getCsv().get(1).get(0));
        Assert.assertEquals("val", eventHandler.getCsv().get(1).get(1));
        Assert.assertEquals("lo...", eventHandler.getCsv().get(1).get(2));
        Assert.assertEquals(1, eventHandler.getCsv().get(2).size());
        Assert.assertEquals("value", eventHandler.getCsv().get(2).get(0));
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = CsvIOException.class)
    public void parseReaderExceptionFailTest() {
        Reader reader = new ErrorReader();
        CsvParser.parseCsv(reader);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void notRectangularExceptionMessageTest() {
        try {
            String csv = "1,2,3\r\n4,5,6,7";
            CsvParser.parseCsv(csv, new NoopEventHandler(), true);
            Assert.fail("Rectangular check fail");
        } catch (NotRectangularException ex) {
            Assert.assertEquals("CSV is not rectangular. Last symbols: \"1,2,3\\r\\n4,5,6,7\".", ex.getMessage());
        }
        try {
            String csv = "1,2,3\r\n4,5,6,7\r\n";
            CsvParser.parseCsv(csv, new NoopEventHandler(), true);
            Assert.fail("Rectangular check fail");
        } catch (NotRectangularException ex) {
            Assert.assertEquals("CSV is not rectangular. Last symbols: \"1,2,3\\r\\n4,5,6,7\\r\\n\".", ex.getMessage());
        }
        try {
            String csv = "1,2,3\r\n4,5,6,7,8\r\n";
            CsvParser.parseCsv(csv, new NoopEventHandler(), true);
            Assert.fail("Rectangular check fail");
        } catch (NotRectangularException ex) {
            Assert.assertEquals("CSV is not rectangular. Last symbols: \"1,2,3\\r\\n4,5,6,7,\".", ex.getMessage());
        }
        try {
            String csv = "1,2,3\r\n4,5";
            CsvParser.parseCsv(csv, new NoopEventHandler(), true);
            Assert.fail("Rectangular check fail");
        } catch (NotRectangularException ex) {
            Assert.assertEquals("CSV is not rectangular. Last symbols: \"1,2,3\\r\\n4,5\".", ex.getMessage());
        }
        try {
            String csv = "1,2,3\r\n4,5\r\n";
            CsvParser.parseCsv(csv, new NoopEventHandler(), true);
            Assert.fail("Rectangular check fail");
        } catch (NotRectangularException ex) {
            Assert.assertEquals("CSV is not rectangular. Last symbols: \"1,2,3\\r\\n4,5\\r\\n\".", ex.getMessage());
        }
        try {
            String csv = "1,2,3\n4,5\n";
            CsvParser.parseCsv(csv, new NoopEventHandler(), true);
            Assert.fail("Rectangular check fail");
        } catch (NotRectangularException ex) {
            Assert.assertEquals("CSV is not rectangular. Last symbols: \"1,2,3\\n4,5\\n\".", ex.getMessage());
        }
    }

    /**
     * Reader implementation, that throws exceptions.
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
