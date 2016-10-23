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
 * Tests for {@link ColumnLengthEventHandler}.
 *
 * @author Dmitry Shapovalov
 */
public final class ColumnLengthEventHandlerTest {

    /**
     * Test class constructor.
     */
    public ColumnLengthEventHandlerTest() {
        super();
    }

    /**
     * {@link ColumnLengthEventHandler} class test.
     */
    @Test
    public void newObjectTest() {
        ColumnLengthEventHandler eventHandler = new ColumnLengthEventHandler();
        Assert.assertEquals(0, eventHandler.getMaxColumnLength());
        Assert.assertFalse(eventHandler.checkMaxColumnLength());
        Assert.assertNotNull(eventHandler.getColumnLengths());
        Assert.assertTrue(eventHandler.getColumnLengths().isEmpty());
    }

    /**
     * {@link ColumnLengthEventHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        ColumnLengthEventHandler eventHandler1 = new ColumnLengthEventHandler();
        eventHandler1.pushColumn("a", 1);
        Assert.assertNotNull(eventHandler1.getColumnLengths());
        Assert.assertTrue(eventHandler1.getColumnLengths().isEmpty());

        ColumnLengthEventHandler eventHandler2 = new ColumnLengthEventHandler();
        eventHandler2.pushColumn("b", 1);
        Assert.assertNotNull(eventHandler2.getColumnLengths());
        Assert.assertTrue(eventHandler2.getColumnLengths().isEmpty());

        ColumnLengthEventHandler eventHandler3 = new ColumnLengthEventHandler();
        eventHandler3.pushColumn("c", 1);
        Assert.assertNotNull(eventHandler3.getColumnLengths());
        Assert.assertTrue(eventHandler3.getColumnLengths().isEmpty());
    }

    /**
     * {@link ColumnLengthEventHandler} class test.
     */
    @Test
    public void pushRowTest() {
        ColumnLengthEventHandler eventHandler1 = new ColumnLengthEventHandler();
        eventHandler1.pushColumn("a", 1);
        eventHandler1.pushRow();
        List<List<Integer>> list1 = eventHandler1.getColumnLengths();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(1, list1.get(0).size());
        Assert.assertEquals(1, (int) list1.get(0).get(0));

        ColumnLengthEventHandler eventHandler2 = new ColumnLengthEventHandler();
        eventHandler2.pushColumn("b", 1);
        eventHandler2.pushColumn("c", 1);
        eventHandler2.pushRow();
        List<List<Integer>> list2 = eventHandler2.getColumnLengths();
        Assert.assertNotNull(list2);
        Assert.assertEquals(1, list2.size());
        Assert.assertEquals(2, list2.get(0).size());
        Assert.assertEquals(1, (int) list2.get(0).get(0));
        Assert.assertEquals(1, (int) list2.get(0).get(1));
    }

    /**
     * {@link ColumnLengthEventHandler} class test.
     */
    @Test
    public void pushEmptyRowTest() {
        ColumnLengthEventHandler eventHandler1 = new ColumnLengthEventHandler();
        eventHandler1.pushRow();
        List<List<Integer>> list1 = eventHandler1.getColumnLengths();
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(0, list1.get(0).size());

        ColumnLengthEventHandler eventHandler2 = new ColumnLengthEventHandler();
        eventHandler2.pushColumn("a", 1);
        eventHandler2.pushRow();
        eventHandler2.pushRow();
        eventHandler2.pushRow();
        eventHandler2.pushColumn("b", 1);
        eventHandler2.pushRow();
        List<List<Integer>> list2 = eventHandler2.getColumnLengths();
        Assert.assertNotNull(list2);
        Assert.assertEquals(4, list2.size());
        Assert.assertEquals(1, list2.get(0).size());
        Assert.assertEquals(1, (int) list2.get(0).get(0));
        Assert.assertEquals(0, list2.get(1).size());
        Assert.assertEquals(0, list2.get(2).size());
        Assert.assertEquals(1, list2.get(3).size());
        Assert.assertEquals(1, (int) list2.get(3).get(0));

        ColumnLengthEventHandler eventHandler3 = new ColumnLengthEventHandler();
        eventHandler3.pushColumn("a", 1);
        eventHandler3.pushRow();
        eventHandler3.pushRow();
        List<List<Integer>> list3 = eventHandler3.getColumnLengths();
        Assert.assertNotNull(list3);
        Assert.assertEquals(2, list3.size());
        Assert.assertEquals(1, list3.get(0).size());
        Assert.assertEquals(1, (int) list3.get(0).get(0));
        Assert.assertEquals(0, list3.get(1).size());
    }

    /**
     * {@link ColumnLengthEventHandler} class test.
     */
    @Test
    public void rectangularTest() {
        ColumnLengthEventHandler eventHandler = new ColumnLengthEventHandler();
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
        List<List<Integer>> list = eventHandler.getColumnLengths();
        Assert.assertNotNull(list);
        Assert.assertEquals(4, list.size());
        Assert.assertEquals(2, list.get(0).size());
        Assert.assertEquals(1, (int) list.get(0).get(0));
        Assert.assertEquals(2, (int) list.get(0).get(1));
        Assert.assertEquals(2, list.get(1).size());
        Assert.assertEquals(1, (int) list.get(1).get(0));
        Assert.assertEquals(2, (int) list.get(1).get(1));
        Assert.assertEquals(2, list.get(2).size());
        Assert.assertEquals(1, (int) list.get(2).get(0));
        Assert.assertEquals(2, (int) list.get(2).get(1));
        Assert.assertEquals(2, list.get(3).size());
        Assert.assertEquals(1, (int) list.get(3).get(0));
        Assert.assertEquals(3, (int) list.get(3).get(1));
    }

}
