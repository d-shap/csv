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
package ru.d_shap.csv.handler;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link ListParserEventHandler}.
 *
 * @author Dmitry Shapovalov
 */
public final class ListParserEventHandlerTest {

    /**
     * Test class constructor.
     */
    public ListParserEventHandlerTest() {
        super();
    }

    /**
     * {@link ListParserEventHandler} class test.
     */
    @Test
    public void newObjectTest() {
        ListParserEventHandler eventHandler = new ListParserEventHandler();
        Assert.assertNotNull(eventHandler.getCsv());
        Assert.assertTrue(eventHandler.getCsv().isEmpty());
    }

    /**
     * {@link ListParserEventHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        ListParserEventHandler eventHandler = new ListParserEventHandler();

        eventHandler.pushColumn("a");
        Assert.assertNotNull(eventHandler.getCsv());
        Assert.assertTrue(eventHandler.getCsv().isEmpty());

        eventHandler.pushColumn("b");
        Assert.assertNotNull(eventHandler.getCsv());
        Assert.assertTrue(eventHandler.getCsv().isEmpty());

        eventHandler.pushColumn("c");
        Assert.assertNotNull(eventHandler.getCsv());
        Assert.assertTrue(eventHandler.getCsv().isEmpty());
    }

    /**
     * {@link ListParserEventHandler} class test.
     */
    @Test
    public void pushRowTest() {
        ListParserEventHandler eventHandler = new ListParserEventHandler();

        eventHandler.pushColumn("a");
        eventHandler.pushRow();
        List<List<String>> list1 = eventHandler.getCsv();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(1, list1.get(0).size());
        Assert.assertEquals("a", list1.get(0).get(0));

        eventHandler.pushColumn("b");
        eventHandler.pushColumn("c");
        eventHandler.pushRow();
        List<List<String>> list2 = eventHandler.getCsv();
        Assert.assertNotNull(list2);
        Assert.assertEquals(1, list2.size());
        Assert.assertEquals(2, list2.get(0).size());
        Assert.assertEquals("b", list2.get(0).get(0));
        Assert.assertEquals("c", list2.get(0).get(1));
    }

    /**
     * {@link ListParserEventHandler} class test.
     */
    @Test
    public void pushEmptyRowTest() {
        ListParserEventHandler eventHandler = new ListParserEventHandler();

        eventHandler.pushRow();
        List<List<String>> list1 = eventHandler.getCsv();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(0, list1.get(0).size());

        eventHandler.pushColumn("a");
        eventHandler.pushRow();
        eventHandler.pushRow();
        eventHandler.pushRow();
        eventHandler.pushColumn("b");
        eventHandler.pushRow();
        List<List<String>> list2 = eventHandler.getCsv();
        Assert.assertNotNull(list2);
        Assert.assertEquals(4, list2.size());
        Assert.assertEquals(1, list2.get(0).size());
        Assert.assertEquals("a", list2.get(0).get(0));
        Assert.assertEquals(0, list2.get(1).size());
        Assert.assertEquals(0, list2.get(2).size());
        Assert.assertEquals(1, list2.get(3).size());
        Assert.assertEquals("b", list2.get(3).get(0));

        eventHandler.pushColumn("a");
        eventHandler.pushRow();
        eventHandler.pushRow();
        List<List<String>> list3 = eventHandler.getCsv();
        Assert.assertNotNull(list3);
        Assert.assertEquals(2, list3.size());
        Assert.assertEquals(1, list3.get(0).size());
        Assert.assertEquals("a", list3.get(0).get(0));
        Assert.assertEquals(0, list3.get(1).size());
    }

    /**
     * {@link ListParserEventHandler} class test.
     */
    @Test
    public void reusableTest() {
        ListParserEventHandler eventHandler = new ListParserEventHandler();

        eventHandler.pushColumn("a");
        eventHandler.pushColumn("bc");
        eventHandler.pushRow();
        List<List<String>> list1 = eventHandler.getCsv();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(2, list1.get(0).size());
        Assert.assertEquals("a", list1.get(0).get(0));
        Assert.assertEquals("bc", list1.get(0).get(1));

        eventHandler.pushColumn("a");
        eventHandler.pushRow();
        eventHandler.pushColumn("bc");
        eventHandler.pushRow();
        List<List<String>> list2 = eventHandler.getCsv();
        Assert.assertNotNull(list2);
        Assert.assertEquals(2, list2.size());
        Assert.assertEquals(1, list2.get(0).size());
        Assert.assertEquals("a", list2.get(0).get(0));
        Assert.assertEquals(1, list2.get(1).size());
        Assert.assertEquals("bc", list2.get(1).get(0));
    }

}
