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
 * Tests for {@link ColumnCountEventHandler}.
 *
 * @author Dmitry Shapovalov
 */
public final class ColumnCountEventHandlerTest {

    /**
     * Test class constructor.
     */
    public ColumnCountEventHandlerTest() {
        super();
    }

    /**
     * {@link ColumnCountEventHandler} class test.
     */
    @Test
    public void newObjectTest() {
        ColumnCountEventHandler eventHandler = new ColumnCountEventHandler();
        Assert.assertEquals(0, eventHandler.getMaxColumnLength());
        Assert.assertFalse(eventHandler.checkMaxColumnLength());
        Assert.assertNotNull(eventHandler.getColumnCounts());
        Assert.assertEquals(0, eventHandler.getColumnCounts().size());
    }

    /**
     * {@link ColumnCountEventHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        ColumnCountEventHandler eventHandler = new ColumnCountEventHandler();
        eventHandler.pushColumn("a", 1);
        Assert.assertNotNull(eventHandler.getColumnCounts());
        Assert.assertEquals(0, eventHandler.getColumnCounts().size());
    }

    /**
     * {@link ColumnCountEventHandler} class test.
     */
    @Test
    public void pushRowTest() {
        ColumnCountEventHandler eventHandler = new ColumnCountEventHandler();
        eventHandler.pushColumn("a", 1);
        eventHandler.pushRow();
        Assert.assertNotNull(eventHandler.getColumnCounts());
        Assert.assertEquals(1, eventHandler.getColumnCounts().size());
        Assert.assertEquals(1, (int) eventHandler.getColumnCounts().get(0));
    }

    /**
     * {@link ColumnCountEventHandler} class test.
     */
    @Test
    public void skipPushColumnTest() {
        ColumnCountEventHandler eventHandler = new ColumnCountEventHandler();
        eventHandler.pushRow();
        Assert.assertNotNull(eventHandler.getColumnCounts());
        Assert.assertEquals(1, eventHandler.getColumnCounts().size());
        Assert.assertEquals(0, (int) eventHandler.getColumnCounts().get(0));
    }

    /**
     * {@link ColumnCountEventHandler} class test.
     */
    @Test
    public void rectangularTest() {
        ColumnCountEventHandler eventHandler = new ColumnCountEventHandler();
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
        Assert.assertNotNull(eventHandler.getColumnCounts());
        Assert.assertEquals(4, eventHandler.getColumnCounts().size());
        Assert.assertEquals(2, (int) eventHandler.getColumnCounts().get(0));
        Assert.assertEquals(2, (int) eventHandler.getColumnCounts().get(1));
        Assert.assertEquals(2, (int) eventHandler.getColumnCounts().get(2));
        Assert.assertEquals(2, (int) eventHandler.getColumnCounts().get(3));
    }

    /**
     * {@link ColumnCountEventHandler} class test.
     */
    @Test
    public void nonRectangularTest() {
        ColumnCountEventHandler eventHandler = new ColumnCountEventHandler();
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
        Assert.assertNotNull(eventHandler.getColumnCounts());
        Assert.assertEquals(4, eventHandler.getColumnCounts().size());
        Assert.assertEquals(2, (int) eventHandler.getColumnCounts().get(0));
        Assert.assertEquals(1, (int) eventHandler.getColumnCounts().get(1));
        Assert.assertEquals(3, (int) eventHandler.getColumnCounts().get(2));
        Assert.assertEquals(0, (int) eventHandler.getColumnCounts().get(3));
    }

}
