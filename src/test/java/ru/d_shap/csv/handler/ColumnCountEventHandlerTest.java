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

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

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
        Assertions.assertThat(eventHandler.getMaxColumnLength()).isEqualTo(0);
        Assertions.assertThat(eventHandler.checkMaxColumnLength()).isFalse();
        Assertions.assertThat(eventHandler.getColumnCounts()).isNotNull();
        Assertions.assertThat(eventHandler.getColumnCounts()).isEmpty();
    }

    /**
     * {@link ColumnCountEventHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        ColumnCountEventHandler eventHandler = new ColumnCountEventHandler();
        eventHandler.pushColumn("a", 1);
        Assertions.assertThat(eventHandler.getColumnCounts()).isNotNull();
        Assertions.assertThat(eventHandler.getColumnCounts()).containsExactlyInOrder();
    }

    /**
     * {@link ColumnCountEventHandler} class test.
     */
    @Test
    public void pushRowTest() {
        ColumnCountEventHandler eventHandler = new ColumnCountEventHandler();
        eventHandler.pushColumn("a", 1);
        eventHandler.pushRow();
        Assertions.assertThat(eventHandler.getColumnCounts()).isNotNull();
        Assertions.assertThat(eventHandler.getColumnCounts()).containsExactlyInOrder(1);
    }

    /**
     * {@link ColumnCountEventHandler} class test.
     */
    @Test
    public void skipPushColumnTest() {
        ColumnCountEventHandler eventHandler = new ColumnCountEventHandler();
        eventHandler.pushRow();
        Assertions.assertThat(eventHandler.getColumnCounts()).isNotNull();
        Assertions.assertThat(eventHandler.getColumnCounts()).containsExactlyInOrder(0);
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
        Assertions.assertThat(eventHandler.getColumnCounts()).isNotNull();
        Assertions.assertThat(eventHandler.getColumnCounts()).containsExactlyInOrder(2, 2, 2, 2);
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
        Assertions.assertThat(eventHandler.getColumnCounts()).isNotNull();
        Assertions.assertThat(eventHandler.getColumnCounts()).containsExactlyInOrder(2, 1, 3, 0);
    }

}
