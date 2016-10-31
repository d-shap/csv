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
package ru.d_shap.csv.handler;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link RestrictedListEventHandler}.
 *
 * @author Dmitry Shapovalov
 */
public final class RestrictedListEventHandlerTest {

    /**
     * Test class constructor.
     */
    public RestrictedListEventHandlerTest() {
        super();
    }

    /**
     * {@link RestrictedListEventHandler} class test.
     */
    @Test
    public void newObjectTest() {
        RestrictedListEventHandler eventHandler = new RestrictedListEventHandler(5);
        Assert.assertEquals(5, eventHandler.getMaxColumnLength());
        Assert.assertFalse(eventHandler.checkMaxColumnLength());
        Assert.assertNotNull(eventHandler.getCsv());
        Assert.assertTrue(eventHandler.getCsv().isEmpty());
    }

    /**
     * {@link RestrictedListEventHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        RestrictedListEventHandler eventHandler1 = new RestrictedListEventHandler(5);
        eventHandler1.pushColumn("a", 1);
        Assert.assertNotNull(eventHandler1.getCsv());
        Assert.assertTrue(eventHandler1.getCsv().isEmpty());

        RestrictedListEventHandler eventHandler2 = new RestrictedListEventHandler(5);
        eventHandler2.pushColumn("b", 1);
        Assert.assertNotNull(eventHandler2.getCsv());
        Assert.assertTrue(eventHandler2.getCsv().isEmpty());

        RestrictedListEventHandler eventHandler3 = new RestrictedListEventHandler(5);
        eventHandler3.pushColumn("c", 1);
        Assert.assertNotNull(eventHandler3.getCsv());
        Assert.assertTrue(eventHandler3.getCsv().isEmpty());
    }

    /**
     * {@link RestrictedListEventHandler} class test.
     */
    @Test
    public void pushRowTest() {
        RestrictedListEventHandler eventHandler1 = new RestrictedListEventHandler(5);
        eventHandler1.pushColumn("a", 1);
        eventHandler1.pushRow();
        List<List<String>> list1 = eventHandler1.getCsv();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(1, list1.get(0).size());
        Assert.assertEquals("a", list1.get(0).get(0));

        RestrictedListEventHandler eventHandler2 = new RestrictedListEventHandler(5);
        eventHandler2.pushColumn("b", 1);
        eventHandler2.pushColumn("c", 1);
        eventHandler2.pushRow();
        List<List<String>> list2 = eventHandler2.getCsv();
        Assert.assertNotNull(list2);
        Assert.assertEquals(1, list2.size());
        Assert.assertEquals(2, list2.get(0).size());
        Assert.assertEquals("b", list2.get(0).get(0));
        Assert.assertEquals("c", list2.get(0).get(1));
    }

    /**
     * {@link RestrictedListEventHandler} class test.
     */
    @Test
    public void pushEmptyRowTest() {
        RestrictedListEventHandler eventHandler1 = new RestrictedListEventHandler(5);
        eventHandler1.pushRow();
        List<List<String>> list1 = eventHandler1.getCsv();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(0, list1.get(0).size());

        RestrictedListEventHandler eventHandler2 = new RestrictedListEventHandler(5);
        eventHandler2.pushColumn("a", 1);
        eventHandler2.pushRow();
        eventHandler2.pushRow();
        eventHandler2.pushRow();
        eventHandler2.pushColumn("b", 1);
        eventHandler2.pushRow();
        List<List<String>> list2 = eventHandler2.getCsv();
        Assert.assertNotNull(list2);
        Assert.assertEquals(4, list2.size());
        Assert.assertEquals(1, list2.get(0).size());
        Assert.assertEquals("a", list2.get(0).get(0));
        Assert.assertEquals(0, list2.get(1).size());
        Assert.assertEquals(0, list2.get(2).size());
        Assert.assertEquals(1, list2.get(3).size());
        Assert.assertEquals("b", list2.get(3).get(0));

        RestrictedListEventHandler eventHandler3 = new RestrictedListEventHandler(5);
        eventHandler3.pushColumn("a", 1);
        eventHandler3.pushRow();
        eventHandler3.pushRow();
        List<List<String>> list3 = eventHandler3.getCsv();
        Assert.assertNotNull(list3);
        Assert.assertEquals(2, list3.size());
        Assert.assertEquals(1, list3.get(0).size());
        Assert.assertEquals("a", list3.get(0).get(0));
        Assert.assertEquals(0, list3.get(1).size());
    }

    /**
     * {@link RestrictedListEventHandler} class test.
     */
    @Test
    public void rectangularTest() {
        RestrictedListEventHandler eventHandler = new RestrictedListEventHandler(5);
        eventHandler.pushColumn("a", 1);
        eventHandler.pushColumn("bc", 2);
        eventHandler.pushRow();
        eventHandler.pushColumn("d", 1);
        eventHandler.pushColumn("ef", 2);
        eventHandler.pushRow();
        eventHandler.pushColumn("g", 1);
        eventHandler.pushColumn("hi", 2);
        eventHandler.pushRow();
        eventHandler.pushColumn("j", 1);
        eventHandler.pushColumn("klm", 3);
        eventHandler.pushRow();
        List<List<String>> list = eventHandler.getCsv();
        Assert.assertNotNull(list);
        Assert.assertEquals(4, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("a", list.get(0).get(0));
        Assert.assertEquals("bc", list.get(0).get(1));
        Assert.assertEquals(2, list.get(1).size());
        Assert.assertEquals("d", list.get(1).get(0));
        Assert.assertEquals("ef", list.get(1).get(1));
        Assert.assertEquals(2, list.get(2).size());
        Assert.assertEquals("g", list.get(2).get(0));
        Assert.assertEquals("hi", list.get(2).get(1));
        Assert.assertEquals(2, list.get(3).size());
        Assert.assertEquals("j", list.get(3).get(0));
        Assert.assertEquals("klm", list.get(3).get(1));
    }

    /**
     * {@link RestrictedListEventHandler} class test.
     */
    @Test
    public void addValueExceedsMaxLengthWithNoMarkTest() {
        RestrictedListEventHandler eventHandler = new RestrictedListEventHandler(5);
        eventHandler.pushColumn("abcde", 7);
        eventHandler.pushColumn("12345", 8);
        eventHandler.pushRow();
        List<List<String>> list = eventHandler.getCsv();
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("abcde", list.get(0).get(0));
        Assert.assertEquals("12345", list.get(0).get(1));
    }

    /**
     * {@link RestrictedListEventHandler} class test.
     */
    @Test
    public void addValueExceedsMaxLengthWithMarkTest() {
        RestrictedListEventHandler eventHandler = new RestrictedListEventHandler(5, "..");
        eventHandler.pushColumn("abcde", 7);
        eventHandler.pushColumn("12345", 8);
        eventHandler.pushRow();
        List<List<String>> list = eventHandler.getCsv();
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("abc..", list.get(0).get(0));
        Assert.assertEquals("123..", list.get(0).get(1));
    }

    /**
     * {@link RestrictedListEventHandler} class test.
     */
    @Test
    public void addValueExceedsMaxLengthWithTooLargeMarkTest() {
        RestrictedListEventHandler eventHandler = new RestrictedListEventHandler(5, "......");
        eventHandler.pushColumn("abcde", 7);
        eventHandler.pushColumn("12345", 8);
        eventHandler.pushRow();
        List<List<String>> list = eventHandler.getCsv();
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals("......", list.get(0).get(0));
        Assert.assertEquals("......", list.get(0).get(1));
    }

}
