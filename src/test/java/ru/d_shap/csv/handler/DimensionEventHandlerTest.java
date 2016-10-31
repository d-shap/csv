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

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link DimensionEventHandler}.
 *
 * @author Dmitry Shapovalov
 */
public final class DimensionEventHandlerTest {

    /**
     * Test class constructor.
     */
    public DimensionEventHandlerTest() {
        super();
    }

    /**
     * {@link DimensionEventHandler} class test.
     */
    @Test
    public void newObjectTest() {
        DimensionEventHandler eventHandler = new DimensionEventHandler();
        Assert.assertEquals(0, eventHandler.getMaxColumnLength());
        Assert.assertFalse(eventHandler.checkMaxColumnLength());
        Assert.assertEquals(0, eventHandler.getColumnCount());
        Assert.assertEquals(0, eventHandler.getRowCount());
    }

    /**
     * {@link DimensionEventHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        DimensionEventHandler eventHandler = new DimensionEventHandler();
        eventHandler.pushColumn("a", 1);
        Assert.assertEquals(1, eventHandler.getColumnCount());
        Assert.assertEquals(0, eventHandler.getRowCount());
    }

    /**
     * {@link DimensionEventHandler} class test.
     */
    @Test
    public void pushRowTest() {
        DimensionEventHandler eventHandler = new DimensionEventHandler();
        eventHandler.pushColumn("a", 1);
        eventHandler.pushRow();
        Assert.assertEquals(1, eventHandler.getColumnCount());
        Assert.assertEquals(1, eventHandler.getRowCount());
    }

    /**
     * {@link DimensionEventHandler} class test.
     */
    @Test
    public void skipPushColumnTest() {
        DimensionEventHandler eventHandler = new DimensionEventHandler();
        eventHandler.pushRow();
        Assert.assertEquals(0, eventHandler.getColumnCount());
        Assert.assertEquals(1, eventHandler.getRowCount());
    }

    /**
     * {@link DimensionEventHandler} class test.
     */
    @Test
    public void rectangularTest() {
        DimensionEventHandler eventHandler = new DimensionEventHandler();
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
        eventHandler.pushColumn("kl", 2);
        eventHandler.pushRow();
        Assert.assertEquals(2, eventHandler.getColumnCount());
        Assert.assertEquals(4, eventHandler.getRowCount());
    }

    /**
     * {@link DimensionEventHandler} class test.
     */
    @Test
    public void checkFirstRowColumnTest() {
        DimensionEventHandler eventHandler = new DimensionEventHandler();
        eventHandler.pushColumn("a", 1);
        eventHandler.pushColumn("bc", 2);
        eventHandler.pushRow();
        eventHandler.pushColumn("d", 1);
        eventHandler.pushRow();
        eventHandler.pushColumn("e", 1);
        eventHandler.pushColumn("fg", 2);
        eventHandler.pushColumn("hi", 2);
        eventHandler.pushRow();
        eventHandler.pushRow();
        Assert.assertEquals(2, eventHandler.getColumnCount());
        Assert.assertEquals(4, eventHandler.getRowCount());
    }

}
