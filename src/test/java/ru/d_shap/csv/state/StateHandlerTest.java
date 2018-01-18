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
package ru.d_shap.csv.state;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.csv.ColumnSeparators;
import ru.d_shap.csv.NotRectangularException;
import ru.d_shap.csv.RowSeparators;
import ru.d_shap.csv.WrongColumnLengthException;
import ru.d_shap.csv.handler.IParserEventHandler;
import ru.d_shap.csv.handler.ListEventHandler;

/**
 * Tests for {@link StateHandler}.
 *
 * @author Dmitry Shapovalov
 */
public final class StateHandlerTest {

    /**
     * Test class constructor.
     */
    public StateHandlerTest() {
        super();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void newObjectTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandler parserEventHandler = new StateHandler(listEventHandler, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isNotNull();
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isEmpty();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).isEmpty();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void isCommaSeparatorTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        Set<ColumnSeparators> columnSeparators1 = new HashSet<>();
        StateHandler parserEventHandler1 = new StateHandler(listEventHandler, false, columnSeparators1, new HashSet<RowSeparators>());
        Assertions.assertThat(parserEventHandler1.isCommaSeparator()).isFalse();

        Set<ColumnSeparators> columnSeparators2 = new HashSet<>();
        columnSeparators2.add(ColumnSeparators.COMMA);
        StateHandler parserEventHandler2 = new StateHandler(listEventHandler, false, columnSeparators2, new HashSet<RowSeparators>());
        Assertions.assertThat(parserEventHandler2.isCommaSeparator()).isTrue();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void isSemicolonSeparatorTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        Set<ColumnSeparators> columnSeparators1 = new HashSet<>();
        StateHandler parserEventHandler1 = new StateHandler(listEventHandler, false, columnSeparators1, new HashSet<RowSeparators>());
        Assertions.assertThat(parserEventHandler1.isSemicolonSeparator()).isFalse();

        Set<ColumnSeparators> columnSeparators2 = new HashSet<>();
        columnSeparators2.add(ColumnSeparators.SEMICOLON);
        StateHandler parserEventHandler2 = new StateHandler(listEventHandler, false, columnSeparators2, new HashSet<RowSeparators>());
        Assertions.assertThat(parserEventHandler2.isSemicolonSeparator()).isTrue();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void isCrSeparatorTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        Set<RowSeparators> rowSeparators1 = new HashSet<>();
        StateHandler parserEventHandler1 = new StateHandler(listEventHandler, false, new HashSet<ColumnSeparators>(), rowSeparators1);
        Assertions.assertThat(parserEventHandler1.isCrSeparator()).isFalse();

        Set<RowSeparators> rowSeparators2 = new HashSet<>();
        rowSeparators2.add(RowSeparators.CR);
        StateHandler parserEventHandler2 = new StateHandler(listEventHandler, false, new HashSet<ColumnSeparators>(), rowSeparators2);
        Assertions.assertThat(parserEventHandler2.isCrSeparator()).isTrue();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void isLfSeparatorTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        Set<RowSeparators> rowSeparators1 = new HashSet<>();
        StateHandler parserEventHandler1 = new StateHandler(listEventHandler, false, new HashSet<ColumnSeparators>(), rowSeparators1);
        Assertions.assertThat(parserEventHandler1.isLfSeparator()).isFalse();

        Set<RowSeparators> rowSeparators2 = new HashSet<>();
        rowSeparators2.add(RowSeparators.LF);
        StateHandler parserEventHandler2 = new StateHandler(listEventHandler, false, new HashSet<ColumnSeparators>(), rowSeparators2);
        Assertions.assertThat(parserEventHandler2.isLfSeparator()).isTrue();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void isCrLfSeparatorTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        Set<RowSeparators> rowSeparators1 = new HashSet<>();
        StateHandler parserEventHandler1 = new StateHandler(listEventHandler, false, new HashSet<ColumnSeparators>(), rowSeparators1);
        Assertions.assertThat(parserEventHandler1.isCrLfSeparator()).isFalse();

        Set<RowSeparators> rowSeparators2 = new HashSet<>();
        rowSeparators2.add(RowSeparators.CRLF);
        StateHandler parserEventHandler2 = new StateHandler(listEventHandler, false, new HashSet<ColumnSeparators>(), rowSeparators2);
        Assertions.assertThat(parserEventHandler2.isCrLfSeparator()).isTrue();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void addLastSymbolTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandler parserEventHandler = new StateHandler(listEventHandler, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());

        parserEventHandler.addLastSymbol('a');
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isNotNull();
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isEqualTo("a");

        parserEventHandler.addLastSymbol('b');
        parserEventHandler.addLastSymbol('c');
        parserEventHandler.addLastSymbol('d');
        parserEventHandler.addLastSymbol('e');
        parserEventHandler.addLastSymbol('f');
        parserEventHandler.addLastSymbol('g');
        parserEventHandler.addLastSymbol('h');
        parserEventHandler.addLastSymbol('i');
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isNotNull();
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isEqualTo("abcdefghi");

        parserEventHandler.addLastSymbol('j');
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isNotNull();
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isEqualTo("abcdefghij");

        parserEventHandler.addLastSymbol('1');
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isNotNull();
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isEqualTo("abcdefghij1");

        parserEventHandler.addLastSymbol('2');
        parserEventHandler.addLastSymbol('3');
        parserEventHandler.addLastSymbol('4');
        parserEventHandler.addLastSymbol('5');
        parserEventHandler.addLastSymbol('6');
        parserEventHandler.addLastSymbol('7');
        parserEventHandler.addLastSymbol('8');
        parserEventHandler.addLastSymbol('9');
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isNotNull();
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isEqualTo("abcdefghij123456789");

        parserEventHandler.addLastSymbol('0');
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isNotNull();
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isEqualTo("abcdefghij1234567890");

        parserEventHandler.addLastSymbol('a');
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isNotNull();
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isEqualTo("abcdefghij1234567890a");

        parserEventHandler.addLastSymbol('b');
        parserEventHandler.addLastSymbol('c');
        parserEventHandler.addLastSymbol('d');
        parserEventHandler.addLastSymbol('e');
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isNotNull();
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isEqualTo("abcdefghij1234567890abcde");

        parserEventHandler.addLastSymbol('f');
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isNotNull();
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isEqualTo("bcdefghij1234567890abcdef");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void addLastSymbolIgnoreEndOfInputTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandler parserEventHandler = new StateHandler(listEventHandler, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());

        parserEventHandler.addLastSymbol('a');
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isNotNull();
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isEqualTo("a");

        parserEventHandler.addLastSymbol(State.END_OF_INPUT);
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isNotNull();
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isEqualTo("a");

        parserEventHandler.addLastSymbol('b');
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isNotNull();
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isEqualTo("ab");

        parserEventHandler.addLastSymbol(State.END_OF_INPUT);
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isNotNull();
        Assertions.assertThat(parserEventHandler.getLastSymbols()).isEqualTo("ab");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushSymbolTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandler parserEventHandler = new StateHandler(listEventHandler, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
        parserEventHandler.pushSymbol('a');
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).isEmpty();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandler parserEventHandler = new StateHandler(listEventHandler, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).isEmpty();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushRowTest() {
        ListEventHandler listEventHandler1 = new ListEventHandler();
        StateHandler parserEventHandler1 = new StateHandler(listEventHandler1, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
        parserEventHandler1.pushSymbol('a');
        parserEventHandler1.pushColumn();
        parserEventHandler1.pushRow();
        List<List<String>> list1 = listEventHandler1.getCsv();
        Assertions.assertThat(list1).isNotNull();
        Assertions.assertThat(list1).hasSize(1);
        Assertions.assertThat(list1.get(0)).containsExactlyInOrder("a");

        ListEventHandler listEventHandler2 = new ListEventHandler();
        StateHandler parserEventHandler2 = new StateHandler(listEventHandler2, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
        parserEventHandler2.pushSymbol('b');
        parserEventHandler2.pushColumn();
        parserEventHandler2.pushSymbol('c');
        parserEventHandler2.pushColumn();
        parserEventHandler2.pushRow();
        List<List<String>> list2 = listEventHandler2.getCsv();
        Assertions.assertThat(list2).isNotNull();
        Assertions.assertThat(list2).hasSize(1);
        Assertions.assertThat(list2.get(0)).containsExactlyInOrder("b", "c");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushEmptyColumnTest() {
        ListEventHandler listEventHandler1 = new ListEventHandler();
        StateHandler parserEventHandler1 = new StateHandler(listEventHandler1, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
        parserEventHandler1.pushColumn();
        parserEventHandler1.pushRow();
        List<List<String>> list1 = listEventHandler1.getCsv();
        Assertions.assertThat(list1).isNotNull();
        Assertions.assertThat(list1).hasSize(1);
        Assertions.assertThat(list1.get(0)).containsExactlyInOrder("");

        ListEventHandler listEventHandler2 = new ListEventHandler();
        StateHandler parserEventHandler2 = new StateHandler(listEventHandler2, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
        parserEventHandler2.pushSymbol('a');
        parserEventHandler2.pushColumn();
        parserEventHandler2.pushColumn();
        parserEventHandler2.pushSymbol('b');
        parserEventHandler2.pushColumn();
        parserEventHandler2.pushRow();
        List<List<String>> list2 = listEventHandler2.getCsv();
        Assertions.assertThat(list2).isNotNull();
        Assertions.assertThat(list2).hasSize(1);
        Assertions.assertThat(list2.get(0)).containsExactlyInOrder("a", "", "b");

        ListEventHandler listEventHandler3 = new ListEventHandler();
        StateHandler parserEventHandler3 = new StateHandler(listEventHandler3, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
        parserEventHandler3.pushSymbol('a');
        parserEventHandler3.pushColumn();
        parserEventHandler3.pushColumn();
        parserEventHandler3.pushRow();
        List<List<String>> list3 = listEventHandler3.getCsv();
        Assertions.assertThat(list3).isNotNull();
        Assertions.assertThat(list3).hasSize(1);
        Assertions.assertThat(list3.get(0)).containsExactlyInOrder("a", "");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushEmptyRowTest() {
        ListEventHandler listEventHandler1 = new ListEventHandler();
        StateHandler parserEventHandler1 = new StateHandler(listEventHandler1, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
        parserEventHandler1.pushRow();
        List<List<String>> list1 = listEventHandler1.getCsv();
        Assertions.assertThat(list1).isNotNull();
        Assertions.assertThat(list1).hasSize(1);
        Assertions.assertThat(list1.get(0)).containsExactlyInOrder();

        ListEventHandler listEventHandler2 = new ListEventHandler();
        StateHandler parserEventHandler2 = new StateHandler(listEventHandler2, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
        parserEventHandler2.pushSymbol('a');
        parserEventHandler2.pushColumn();
        parserEventHandler2.pushRow();
        parserEventHandler2.pushRow();
        parserEventHandler2.pushRow();
        parserEventHandler2.pushSymbol('b');
        parserEventHandler2.pushColumn();
        parserEventHandler2.pushRow();
        List<List<String>> list2 = listEventHandler2.getCsv();
        Assertions.assertThat(list2).isNotNull();
        Assertions.assertThat(list2).hasSize(4);
        Assertions.assertThat(list2.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(list2.get(1)).containsExactlyInOrder();
        Assertions.assertThat(list2.get(2)).containsExactlyInOrder();
        Assertions.assertThat(list2.get(3)).containsExactlyInOrder("b");

        ListEventHandler listEventHandler3 = new ListEventHandler();
        StateHandler parserEventHandler3 = new StateHandler(listEventHandler3, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
        parserEventHandler3.pushSymbol('a');
        parserEventHandler3.pushColumn();
        parserEventHandler3.pushRow();
        parserEventHandler3.pushRow();
        List<List<String>> list3 = listEventHandler3.getCsv();
        Assertions.assertThat(list3).isNotNull();
        Assertions.assertThat(list3).hasSize(2);
        Assertions.assertThat(list3.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(list3.get(1)).containsExactlyInOrder();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void putMultipleSymbolsTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandler parserEventHandler = new StateHandler(listEventHandler, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushSymbol('d');
        parserEventHandler.pushSymbol('e');
        parserEventHandler.pushColumn();
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list = listEventHandler.getCsv();
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("a", "bcde", "");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void skipPushColumnTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandler parserEventHandler = new StateHandler(listEventHandler, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
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
        List<List<String>> list = listEventHandler.getCsv();
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder();
        Assertions.assertThat(list.get(2)).containsExactlyInOrder("f");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void checkRectangularFailTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandler parserEventHandler = new StateHandler(listEventHandler, true, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushColumn();
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushColumn();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void notReusableTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandler parserEventHandler = new StateHandler(listEventHandler, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());

        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        parserEventHandler.pushSymbol('b');
        parserEventHandler.pushSymbol('c');
        parserEventHandler.pushSymbol('d');
        parserEventHandler.pushSymbol('e');
        List<List<String>> list1 = listEventHandler.getCsv();
        Assertions.assertThat(list1).isNotNull();
        Assertions.assertThat(list1).hasSize(1);
        Assertions.assertThat(list1.get(0)).containsExactlyInOrder("a");

        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
        List<List<String>> list2 = listEventHandler.getCsv();
        Assertions.assertThat(list2).isNotNull();
        Assertions.assertThat(list2).hasSize(2);
        Assertions.assertThat(list2.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(list2.get(1)).containsExactlyInOrder("bcdea");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void notReusableRectangularFailTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandler parserEventHandler = new StateHandler(listEventHandler, true, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
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

        List<List<String>> list = listEventHandler.getCsv();
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("a", "b");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("c", "d");

        parserEventHandler.pushSymbol('a');
        parserEventHandler.pushColumn();
        parserEventHandler.pushRow();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void checkNoColumnRectangularRectangularTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandler parserEventHandler = new StateHandler(listEventHandler, true, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
        parserEventHandler.pushRow();
        parserEventHandler.pushRow();
        parserEventHandler.pushRow();

        List<List<String>> list = listEventHandler.getCsv();
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(3);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder();
        Assertions.assertThat(list.get(1)).containsExactlyInOrder();
        Assertions.assertThat(list.get(2)).containsExactlyInOrder();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test(expected = NotRectangularException.class)
    public void checkNoColumnRectangularRectangularFailTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandler parserEventHandler = new StateHandler(listEventHandler, true, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
        parserEventHandler.pushRow();
        parserEventHandler.pushColumn();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushSymbolWithNoLengthAndNoCheckRestrictionTest() {
        ParserEventHandlerImpl eventHandler = new ParserEventHandlerImpl(-1, false);
        StateHandler parserEventHandler = new StateHandler(eventHandler, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
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
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("abcd", "12");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("efgh");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushSymbolWithNoLengthAndCheckRestrictionTest() {
        ParserEventHandlerImpl eventHandler = new ParserEventHandlerImpl(-1, true);
        StateHandler parserEventHandler = new StateHandler(eventHandler, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
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
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("abcd", "12");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("efgh");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushSymbolEmptyWithNoCheckRestrictionTest() {
        ParserEventHandlerImpl eventHandler = new ParserEventHandlerImpl(0, false);
        StateHandler parserEventHandler = new StateHandler(eventHandler, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
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
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushSymbolEmptyWithCheckRestrictionTest() {
        try {
            ParserEventHandlerImpl eventHandler = new ParserEventHandlerImpl(0, true);
            StateHandler parserEventHandler = new StateHandler(eventHandler, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
            parserEventHandler.addLastSymbol('a');
            parserEventHandler.pushSymbol('a');
            Assertions.fail("StateHandler test fail");
        } catch (WrongColumnLengthException ex) {
            Assertions.assertThat(ex).hasMessage("Maximum column length exceeded. Last symbols: \"a\".");
        }
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushSymbolWithLengthAndNoCheckRestrictionTest() {
        ParserEventHandlerImpl eventHandler = new ParserEventHandlerImpl(3, false);
        StateHandler parserEventHandler = new StateHandler(eventHandler, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
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
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("abc", "12");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("efg");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushSymbolWithLengthAndCheckRestrictionTest() {
        try {
            ParserEventHandlerImpl eventHandler = new ParserEventHandlerImpl(3, true);
            StateHandler parserEventHandler = new StateHandler(eventHandler, false, new HashSet<ColumnSeparators>(), new HashSet<RowSeparators>());
            parserEventHandler.addLastSymbol('a');
            parserEventHandler.pushSymbol('a');
            parserEventHandler.addLastSymbol('b');
            parserEventHandler.pushSymbol('b');
            parserEventHandler.addLastSymbol('c');
            parserEventHandler.pushSymbol('c');
            parserEventHandler.addLastSymbol('d');
            parserEventHandler.pushSymbol('d');
            Assertions.fail("StateHandler test fail");
        } catch (WrongColumnLengthException ex) {
            Assertions.assertThat(ex).hasMessage("Maximum column length exceeded. Last symbols: \"abcd\".");
        }
    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class ParserEventHandlerImpl implements IParserEventHandler {

        private final int _maxColumnLength;

        private final boolean _checkMaxColumnLength;

        private final ListEventHandler _eventHandler;

        ParserEventHandlerImpl(final int maxColumnLength, final boolean checkMaxColumnLength) {
            super();
            _maxColumnLength = maxColumnLength;
            _checkMaxColumnLength = checkMaxColumnLength;
            _eventHandler = new ListEventHandler();
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
