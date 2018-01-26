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

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

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
        Assertions.assertThat(eventHandler.getColumnLengths()).isNotNull();
        Assertions.assertThat(eventHandler.getColumnLengths()).isEmpty();
    }

    /**
     * {@link ColumnLengthEventHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        ColumnLengthEventHandler eventHandler = new ColumnLengthEventHandler();
        eventHandler.pushColumn("a", 1);
        Assertions.assertThat(eventHandler.getColumnLengths()).isNotNull();
        Assertions.assertThat(eventHandler.getColumnLengths()).hasSize(0);
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
        Assertions.assertThat(list1).isNotNull();
        Assertions.assertThat(list1).hasSize(1);
        Assertions.assertThat(list1.get(0)).containsExactlyInOrder(1);

        ColumnLengthEventHandler eventHandler2 = new ColumnLengthEventHandler();
        eventHandler2.pushColumn("b", 1);
        eventHandler2.pushColumn("c", 1);
        eventHandler2.pushRow();
        List<List<Integer>> list2 = eventHandler2.getColumnLengths();
        Assertions.assertThat(list2).isNotNull();
        Assertions.assertThat(list2).hasSize(1);
        Assertions.assertThat(list2.get(0)).containsExactlyInOrder(1, 1);
    }

    /**
     * {@link ColumnLengthEventHandler} class test.
     */
    @Test
    public void pushEmptyRowTest() {
        ColumnLengthEventHandler eventHandler1 = new ColumnLengthEventHandler();
        eventHandler1.pushRow();
        List<List<Integer>> list1 = eventHandler1.getColumnLengths();
        Assertions.assertThat(list1).isNotNull();
        Assertions.assertThat(list1).hasSize(1);
        Assertions.assertThat(list1.get(0)).containsExactlyInOrder();

        ColumnLengthEventHandler eventHandler2 = new ColumnLengthEventHandler();
        eventHandler2.pushColumn("a", 1);
        eventHandler2.pushRow();
        eventHandler2.pushRow();
        eventHandler2.pushRow();
        eventHandler2.pushColumn("b", 1);
        eventHandler2.pushRow();
        List<List<Integer>> list2 = eventHandler2.getColumnLengths();
        Assertions.assertThat(list2).isNotNull();
        Assertions.assertThat(list2).hasSize(4);
        Assertions.assertThat(list2.get(0)).containsExactlyInOrder(1);
        Assertions.assertThat(list2.get(1)).containsExactlyInOrder();
        Assertions.assertThat(list2.get(2)).containsExactlyInOrder();
        Assertions.assertThat(list2.get(3)).containsExactlyInOrder(1);

        ColumnLengthEventHandler eventHandler3 = new ColumnLengthEventHandler();
        eventHandler3.pushColumn("a", 1);
        eventHandler3.pushRow();
        eventHandler3.pushRow();
        List<List<Integer>> list3 = eventHandler3.getColumnLengths();
        Assertions.assertThat(list3).isNotNull();
        Assertions.assertThat(list3).hasSize(2);
        Assertions.assertThat(list3.get(0)).containsExactlyInOrder(1);
        Assertions.assertThat(list3.get(1)).containsExactlyInOrder();
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
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(4);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder(1, 2);
        Assertions.assertThat(list.get(1)).containsExactlyInOrder(1, 2);
        Assertions.assertThat(list.get(2)).containsExactlyInOrder(1, 2);
        Assertions.assertThat(list.get(3)).containsExactlyInOrder(1, 3);
    }

}
