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
package ru.d_shap.csv.state;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ru.d_shap.csv.NotRectangularException;
import ru.d_shap.csv.WrongColumnLengthException;
import ru.d_shap.csv.handler.IParserEventHandler;
import ru.d_shap.csv.handler.ListParserEventHandler;

/**
 * Tests for {@link ParserEventHandler}.
 *
 * @author Dmitry Shapovalov
 */
public final class ParserEventHandlerTest {

    /**
     * Test class constructor.
     */
    public ParserEventHandlerTest() {
        super();
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void newObjectTest() {
        ListParserEventHandler listParserEventHandler = new ListParserEventHandler();
        ParserEventHandler parserEventHandler = new ParserEventHandler(listParserEventHandler, false);
        Assert.assertEquals("", parserEventHandler.getLastSymbols());
        Assert.assertNotNull(listParserEventHandler.getCsv());
        Assert.assertTrue(listParserEventHandler.getCsv().isEmpty());
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void addLastSymbolTest() {
        ListParserEventHandler listParserEventHandler = new ListParserEventHandler();
        ParserEventHandler parserEventHandler = new ParserEventHandler(listParserEventHandler, false);

        parserEventHandler.addLastSymbol('a');
        Assert.assertEquals("a", parserEventHandler.getLastSymbols());

        parserEventHandler.addLastSymbol('b');
        parserEventHandler.addLastSymbol('c');
        parserEventHandler.addLastSymbol('d');
        parserEventHandler.addLastSymbol('e');
        parserEventHandler.addLastSymbol('f');
        parserEventHandler.addLastSymbol('g');
        parserEventHandler.addLastSymbol('h');
        parserEventHandler.addLastSymbol('i');
        Assert.assertEquals("abcdefghi", parserEventHandler.getLastSymbols());

        parserEventHandler.addLastSymbol('j');
        Assert.assertEquals("abcdefghij", parserEventHandler.getLastSymbols());

        parserEventHandler.addLastSymbol('1');
        Assert.assertEquals("abcdefghij1", parserEventHandler.getLastSymbols());

        parserEventHandler.addLastSymbol('2');
        parserEventHandler.addLastSymbol('3');
        parserEventHandler.addLastSymbol('4');
        parserEventHandler.addLastSymbol('5');
        parserEventHandler.addLastSymbol('6');
        parserEventHandler.addLastSymbol('7');
        parserEventHandler.addLastSymbol('8');
        parserEventHandler.addLastSymbol('9');
        Assert.assertEquals("abcdefghij123456789", parserEventHandler.getLastSymbols());

        parserEventHandler.addLastSymbol('0');
        Assert.assertEquals("abcdefghij1234567890", parserEventHandler.getLastSymbols());

        parserEventHandler.addLastSymbol('a');
        Assert.assertEquals("abcdefghij1234567890a", parserEventHandler.getLastSymbols());

        parserEventHandler.addLastSymbol('b');
        parserEventHandler.addLastSymbol('c');
        parserEventHandler.addLastSymbol('d');
        parserEventHandler.addLastSymbol('e');
        Assert.assertEquals("abcdefghij1234567890abcde", parserEventHandler.getLastSymbols());

        parserEventHandler.addLastSymbol('f');
        Assert.assertEquals("bcdefghij1234567890abcdef", parserEventHandler.getLastSymbols());
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void pushSymbolTest() {
        ListParserEventHandler listParserEventHandler = new ListParserEventHandler();
        ParserEventHandler parserEventHandler = new ParserEventHandler(listParserEventHandler, false);
        parserEventHandler.pushSymbol('a');
        Assert.assertNotNull(listParserEventHandler.getCsv());
        Assert.assertTrue(listParserEventHandler.getCsv().isEmpty());
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        ListParserEventHandler listParserEventHandler = new ListParserEventHandler();
        ParserEventHandler parserEventHandler = new ParserEventHandler(listParserEventHandler, false);
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        Assert.assertNotNull(listParserEventHandler.getCsv());
        Assert.assertTrue(listParserEventHandler.getCsv().isEmpty());
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void pushRowTest() {
        ListParserEventHandler listParserEventHandler1 = new ListParserEventHandler();
        ParserEventHandler parserEventHandler1 = new ParserEventHandler(listParserEventHandler1, false);
        parserEventHandler1.pushSymbol('a');
        parserEventHandler1.pushColumn();
        parserEventHandler1.pushRow();
        List<List<String>> list1 = listParserEventHandler1.getCsv();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(1, list1.get(0).size());
        Assert.assertEquals("a", list1.get(0).get(0));

        ListParserEventHandler listParserEventHandler2 = new ListParserEventHandler();
        ParserEventHandler parserEventHandler2 = new ParserEventHandler(listParserEventHandler2, false);
        parserEventHandler2.pushSymbol('b');
        parserEventHandler2.pushColumn();
        parserEventHandler2.pushSymbol('c');
        parserEventHandler2.pushColumn();
        parserEventHandler2.pushRow();
        List<List<String>> list2 = listParserEventHandler2.getCsv();
        Assert.assertNotNull(list2);
        Assert.assertEquals(1, list2.size());
        Assert.assertEquals(2, list2.get(0).size());
        Assert.assertEquals("b", list2.get(0).get(0));
        Assert.assertEquals("c", list2.get(0).get(1));
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void pushEmptyColumnTest() {
        ListParserEventHandler listParserEventHandler1 = new ListParserEventHandler();
        ParserEventHandler parserEventHandler1 = new ParserEventHandler(listParserEventHandler1, false);
        parserEventHandler1.pushColumn();
        parserEventHandler1.pushRow();
        List<List<String>> list1 = listParserEventHandler1.getCsv();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(1, list1.get(0).size());
        Assert.assertEquals("", list1.get(0).get(0));

        ListParserEventHandler listParserEventHandler2 = new ListParserEventHandler();
        ParserEventHandler parserEventHandler2 = new ParserEventHandler(listParserEventHandler2, false);
        parserEventHandler2.pushSymbol('a');
        parserEventHandler2.pushColumn();
        parserEventHandler2.pushColumn();
        parserEventHandler2.pushSymbol('b');
        parserEventHandler2.pushColumn();
        parserEventHandler2.pushRow();
        List<List<String>> list2 = listParserEventHandler2.getCsv();
        Assert.assertNotNull(list2);
        Assert.assertEquals(1, list2.size());
        Assert.assertEquals(3, list2.get(0).size());
        Assert.assertEquals("a", list2.get(0).get(0));
        Assert.assertEquals("", list2.get(0).get(1));
        Assert.assertEquals("b", list2.get(0).get(2));

        ListParserEventHandler listParserEventHandler3 = new ListParserEventHandler();
        ParserEventHandler parserEventHandler3 = new ParserEventHandler(listParserEventHandler3, false);
        parserEventHandler3.pushSymbol('a');
        parserEventHandler3.pushColumn();
        parserEventHandler3.pushColumn();
        parserEventHandler3.pushRow();
        List<List<String>> list3 = listParserEventHandler3.getCsv();
        Assert.assertNotNull(list3);
        Assert.assertEquals(1, list3.size());
        Assert.assertEquals(2, list3.get(0).size());
        Assert.assertEquals("a", list3.get(0).get(0));
        Assert.assertEquals("", list3.get(0).get(1));
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void pushEmptyRowTest() {
        ListParserEventHandler listParserEventHandler1 = new ListParserEventHandler();
        ParserEventHandler parserEventHandler1 = new ParserEventHandler(listParserEventHandler1, false);
        parserEventHandler1.pushRow();
        List<List<String>> list1 = listParserEventHandler1.getCsv();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(0, list1.get(0).size());

        ListParserEventHandler listParserEventHandler2 = new ListParserEventHandler();
        ParserEventHandler parserEventHandler2 = new ParserEventHandler(listParserEventHandler2, false);
        parserEventHandler2.pushSymbol('a');
        parserEventHandler2.pushColumn();
        parserEventHandler2.pushRow();
        parserEventHandler2.pushRow();
        parserEventHandler2.pushRow();
        parserEventHandler2.pushSymbol('b');
        parserEventHandler2.pushColumn();
        parserEventHandler2.pushRow();
        List<List<String>> list2 = listParserEventHandler2.getCsv();
        Assert.assertNotNull(list2);
        Assert.assertEquals(4, list2.size());
        Assert.assertEquals(1, list2.get(0).size());
        Assert.assertEquals("a", list2.get(0).get(0));
        Assert.assertEquals(0, list2.get(1).size());
        Assert.assertEquals(0, list2.get(2).size());
        Assert.assertEquals(1, list2.get(3).size());
        Assert.assertEquals("b", list2.get(3).get(0));

        ListParserEventHandler listParserEventHandler3 = new ListParserEventHandler();
        ParserEventHandler parserEventHandler3 = new ParserEventHandler(listParserEventHandler3, false);
        parserEventHandler3.pushSymbol('a');
        parserEventHandler3.pushColumn();
        parserEventHandler3.pushRow();
        parserEventHandler3.pushRow();
        List<List<String>> list3 = listParserEventHandler3.getCsv();
        Assert.assertNotNull(list3);
        Assert.assertEquals(2, list3.size());
        Assert.assertEquals(1, list3.get(0).size());
        Assert.assertEquals("a", list3.get(0).get(0));
        Assert.assertEquals(0, list3.get(1).size());
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void putMultipleSymbolsTest() {
        ListParserEventHandler listParserEventHandler = new ListParserEventHandler();
        ParserEventHandler parserEventHandler = new ParserEventHandler(listParserEventHandler, false);
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushSymbol('d');
        parserEventHandler.pushSymbol('e');
        parserEventHandler.pushColumn();
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list = listParserEventHandler.getCsv();
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(3, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals("bcde", list.get(0).get(1));
        Assert.assertEquals("", list.get(0).get(2));
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void skipPushColumnTest() {
        ListParserEventHandler listParserEventHandler = new ListParserEventHandler();
        ParserEventHandler parserEventHandler = new ParserEventHandler(listParserEventHandler, false);
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushRow();
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushSymbol('d');
        parserEventHandler.pushSymbol('e');
        parserEventHandler.pushRow();
        parserEventHandler.pushSymbol('f');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list = listParserEventHandler.getCsv();
        Assert.assertNotNull(list);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(0, list.get(0).size());
        Assert.assertEquals(0, list.get(1).size());
        Assert.assertEquals(1, list.get(2).size());
        Assert.assertEquals("f", list.get(2).get(0));
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void checkRectangularFailTest() {
        ListParserEventHandler listParserEventHandler = new ListParserEventHandler();
        ParserEventHandler parserEventHandler = new ParserEventHandler(listParserEventHandler, true);
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushColumn();
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void notReusableTest() {
        ListParserEventHandler listParserEventHandler = new ListParserEventHandler();
        ParserEventHandler parserEventHandler = new ParserEventHandler(listParserEventHandler, false);

        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushSymbol('d');
        parserEventHandler.pushSymbol('e');
        List<List<String>> list1 = listParserEventHandler.getCsv();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(1, list1.get(0).size());
        Assert.assertEquals("a", list1.get(0).get(0));

        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list2 = listParserEventHandler.getCsv();
        Assert.assertNotNull(list2);
        Assert.assertEquals(2, list2.size());
        Assert.assertEquals(1, list2.get(0).size());
        Assert.assertEquals("a", list2.get(0).get(0));
        Assert.assertEquals(1, list2.get(1).size());
        Assert.assertEquals("bcdea", list2.get(1).get(0));
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void notReusableRectangularFailTest() {
        ListParserEventHandler listParserEventHandler = new ListParserEventHandler();
        ParserEventHandler parserEventHandler = new ParserEventHandler(listParserEventHandler, true);
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushColumn();
        parserEventHandler.pushSymbol('d');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();

        List<List<String>> list = listParserEventHandler.getCsv();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals("b", list.get(0).get(1));
        Assert.assertEquals(2, list.get(1).size());
        Assert.assertEquals("c", list.get(1).get(0));
        Assert.assertEquals("d", list.get(1).get(1));

        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void pushSymbolWithNoLengthAndNoCheckRestrictionTest() {
        ParserEventHandlerImpl eventHandler = new ParserEventHandlerImpl(-1, false);
        ParserEventHandler parserEventHandler = new ParserEventHandler(eventHandler, false);
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushSymbol('d');
        parserEventHandler.pushColumn();
        parserEventHandler.pushSymbol('1');
        parserEventHandler.pushSymbol('2');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        parserEventHandler.pushSymbol('e');
        parserEventHandler.pushSymbol('f');
        parserEventHandler.pushSymbol('g');
        parserEventHandler.pushSymbol('h');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list = eventHandler.getCsv();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("abcd", list.get(0).get(0));
        Assert.assertEquals("12", list.get(0).get(1));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("efgh", list.get(1).get(0));
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void pushSymbolWithNoLengthAndCheckRestrictionTest() {
        ParserEventHandlerImpl eventHandler = new ParserEventHandlerImpl(-1, true);
        ParserEventHandler parserEventHandler = new ParserEventHandler(eventHandler, false);
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushSymbol('d');
        parserEventHandler.pushColumn();
        parserEventHandler.pushSymbol('1');
        parserEventHandler.pushSymbol('2');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        parserEventHandler.pushSymbol('e');
        parserEventHandler.pushSymbol('f');
        parserEventHandler.pushSymbol('g');
        parserEventHandler.pushSymbol('h');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list = eventHandler.getCsv();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("abcd", list.get(0).get(0));
        Assert.assertEquals("12", list.get(0).get(1));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("efgh", list.get(1).get(0));
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void pushSymbolEmptyWithNoCheckRestrictionTest() {
        ParserEventHandlerImpl eventHandler = new ParserEventHandlerImpl(0, false);
        ParserEventHandler parserEventHandler = new ParserEventHandler(eventHandler, false);
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushSymbol('d');
        parserEventHandler.pushColumn();
        parserEventHandler.pushSymbol('1');
        parserEventHandler.pushSymbol('2');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        parserEventHandler.pushSymbol('e');
        parserEventHandler.pushSymbol('f');
        parserEventHandler.pushSymbol('g');
        parserEventHandler.pushSymbol('h');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list = eventHandler.getCsv();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("", list.get(0).get(0));
        Assert.assertEquals("", list.get(0).get(1));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("", list.get(1).get(0));
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test(expected = WrongColumnLengthException.class)
    public void pushSymbolEmptyWithCheckRestrictionTest() {
        ParserEventHandlerImpl eventHandler = new ParserEventHandlerImpl(0, true);
        ParserEventHandler parserEventHandler = new ParserEventHandler(eventHandler, false);
        parserEventHandler.pushSymbol('a');
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test
    public void pushSymbolWithLengthAndNoCheckRestrictionTest() {
        ParserEventHandlerImpl eventHandler = new ParserEventHandlerImpl(3, false);
        ParserEventHandler parserEventHandler = new ParserEventHandler(eventHandler, false);
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushSymbol('d');
        parserEventHandler.pushColumn();
        parserEventHandler.pushSymbol('1');
        parserEventHandler.pushSymbol('2');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        parserEventHandler.pushSymbol('e');
        parserEventHandler.pushSymbol('f');
        parserEventHandler.pushSymbol('g');
        parserEventHandler.pushSymbol('h');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list = eventHandler.getCsv();
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("abc", list.get(0).get(0));
        Assert.assertEquals("12", list.get(0).get(1));
        Assert.assertEquals(1, list.get(1).size());
        Assert.assertEquals("efg", list.get(1).get(0));
    }

    /**
     * {@link ParserEventHandler} class test.
     */
    @Test(expected = WrongColumnLengthException.class)
    public void pushSymbolWithLengthAndCheckRestrictionTest() {
        ParserEventHandlerImpl eventHandler = new ParserEventHandlerImpl(3, true);
        ParserEventHandler parserEventHandler = new ParserEventHandler(eventHandler, false);
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushSymbol('d');
    }

    /**
     * CSV parser event handler to test column restrictions.
     *
     * @author Dmitry Shapovalov
     */
    private static final class ParserEventHandlerImpl implements IParserEventHandler {

        private final int _maxColumnLength;

        private final boolean _checkMaxColumnLength;

        private final ListParserEventHandler _eventHandler;

        ParserEventHandlerImpl(final int maxColumnLength, final boolean checkMaxColumnLength) {
            super();
            _maxColumnLength = maxColumnLength;
            _checkMaxColumnLength = checkMaxColumnLength;
            _eventHandler = new ListParserEventHandler();
        }

        @Override
        public int getMaxColumnLength() {
            return _maxColumnLength;
        }

        @Override
        public boolean checkMaxColumnLength() {
            return _checkMaxColumnLength;
        }

        @Override
        public void pushColumn(final String column, final int actualLength) {
            _eventHandler.pushColumn(column, actualLength);
        }

        @Override
        public void pushRow() {
            _eventHandler.pushRow();
        }

        List<List<String>> getCsv() {
            return _eventHandler.getCsv();
        }

    }

}
