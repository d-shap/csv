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
 * Tests for {@link ListEventHandler}.
 *
 * @author Dmitry Shapovalov
 */
public final class ListEventHandlerTest {

    /**
     * Test class constructor.
     */
    public ListEventHandlerTest() {
        super();
    }

    /**
     * {@link ListEventHandler} class test.
     */
    @Test
    public void newObjectTest() {
        ListEventHandler eventHandler = new ListEventHandler();
        Assert.assertTrue(eventHandler.getMaxColumnLength() < 0);
        Assert.assertFalse(eventHandler.checkMaxColumnLength());
        Assert.assertNotNull(eventHandler.getCsv());
        Assert.assertTrue(eventHandler.getCsv().isEmpty());
    }

    /**
     * {@link ListEventHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        ListEventHandler eventHandler1 = new ListEventHandler();
        eventHandler1.pushColumn("a", 1);
        Assert.assertNotNull(eventHandler1.getCsv());
        Assert.assertTrue(eventHandler1.getCsv().isEmpty());

        ListEventHandler eventHandler2 = new ListEventHandler();
        eventHandler2.pushColumn("b", 1);
        Assert.assertNotNull(eventHandler2.getCsv());
        Assert.assertTrue(eventHandler2.getCsv().isEmpty());

        ListEventHandler eventHandler3 = new ListEventHandler();
        eventHandler3.pushColumn("c", 1);
        Assert.assertNotNull(eventHandler3.getCsv());
        Assert.assertTrue(eventHandler3.getCsv().isEmpty());
    }

    /**
     * {@link ListEventHandler} class test.
     */
    @Test
    public void pushRowTest() {
        ListEventHandler eventHandler1 = new ListEventHandler();
        eventHandler1.pushColumn("a", 1);
        eventHandler1.pushRow();
        List<List<String>> list1 = eventHandler1.getCsv();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(1, list1.get(0).size());
        Assert.assertEquals("a", list1.get(0).get(0));

        ListEventHandler eventHandler2 = new ListEventHandler();
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
     * {@link ListEventHandler} class test.
     */
    @Test
    public void pushEmptyRowTest() {
        ListEventHandler eventHandler1 = new ListEventHandler();
        eventHandler1.pushRow();
        List<List<String>> list1 = eventHandler1.getCsv();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(0, list1.get(0).size());

        ListEventHandler eventHandler2 = new ListEventHandler();
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

        ListEventHandler eventHandler3 = new ListEventHandler();
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
     * {@link ListEventHandler} class test.
     */
    @Test
    public void rectangularTest() {
        ListEventHandler eventHandler = new ListEventHandler();
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

}
