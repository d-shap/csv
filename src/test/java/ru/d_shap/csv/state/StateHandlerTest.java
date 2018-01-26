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

import java.util.List;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.csv.CsvParseException;
import ru.d_shap.csv.WrongColumnCountException;
import ru.d_shap.csv.WrongColumnLengthException;
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
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
        Assertions.assertThat(stateHandler.isCommaSeparator()).isFalse();
        Assertions.assertThat(stateHandler.isSemicolonSeparator()).isFalse();
        Assertions.assertThat(stateHandler.isCrSeparator()).isFalse();
        Assertions.assertThat(stateHandler.isLfSeparator()).isFalse();
        Assertions.assertThat(stateHandler.isCrLfSeparator()).isFalse();
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isNotNull();
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isEmpty();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void isCommaSeparatorTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        StateHandlerConfiguration stateHandlerConfiguration1 = new StateHandlerConfiguration();
        stateHandlerConfiguration1.setCommaSeparator(false);
        StateHandler stateHandler1 = new StateHandler(listEventHandler, stateHandlerConfiguration1);
        Assertions.assertThat(stateHandler1.isCommaSeparator()).isFalse();

        StateHandlerConfiguration stateHandlerConfiguration2 = new StateHandlerConfiguration();
        stateHandlerConfiguration2.setCommaSeparator(true);
        StateHandler stateHandler2 = new StateHandler(listEventHandler, stateHandlerConfiguration2);
        Assertions.assertThat(stateHandler2.isCommaSeparator()).isTrue();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void isSemicolonSeparatorTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        StateHandlerConfiguration stateHandlerConfiguration1 = new StateHandlerConfiguration();
        stateHandlerConfiguration1.setSemicolonSeparator(false);
        StateHandler stateHandler1 = new StateHandler(listEventHandler, stateHandlerConfiguration1);
        Assertions.assertThat(stateHandler1.isSemicolonSeparator()).isFalse();

        StateHandlerConfiguration stateHandlerConfiguration2 = new StateHandlerConfiguration();
        stateHandlerConfiguration2.setSemicolonSeparator(true);
        StateHandler stateHandler2 = new StateHandler(listEventHandler, stateHandlerConfiguration2);
        Assertions.assertThat(stateHandler2.isSemicolonSeparator()).isTrue();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void isCrSeparatorTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        StateHandlerConfiguration stateHandlerConfiguration1 = new StateHandlerConfiguration();
        stateHandlerConfiguration1.setCrSeparator(false);
        StateHandler stateHandler1 = new StateHandler(listEventHandler, stateHandlerConfiguration1);
        Assertions.assertThat(stateHandler1.isCrSeparator()).isFalse();

        StateHandlerConfiguration stateHandlerConfiguration2 = new StateHandlerConfiguration();
        stateHandlerConfiguration2.setCrSeparator(true);
        StateHandler stateHandler2 = new StateHandler(listEventHandler, stateHandlerConfiguration2);
        Assertions.assertThat(stateHandler2.isCrSeparator()).isTrue();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void isLfSeparatorTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        StateHandlerConfiguration stateHandlerConfiguration1 = new StateHandlerConfiguration();
        stateHandlerConfiguration1.setLfSeparator(false);
        StateHandler stateHandler1 = new StateHandler(listEventHandler, stateHandlerConfiguration1);
        Assertions.assertThat(stateHandler1.isLfSeparator()).isFalse();

        StateHandlerConfiguration stateHandlerConfiguration2 = new StateHandlerConfiguration();
        stateHandlerConfiguration2.setLfSeparator(true);
        StateHandler stateHandler2 = new StateHandler(listEventHandler, stateHandlerConfiguration2);
        Assertions.assertThat(stateHandler2.isLfSeparator()).isTrue();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void isCrLfSeparatorTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        StateHandlerConfiguration stateHandlerConfiguration1 = new StateHandlerConfiguration();
        stateHandlerConfiguration1.setCrLfSeparator(false);
        StateHandler stateHandler1 = new StateHandler(listEventHandler, stateHandlerConfiguration1);
        Assertions.assertThat(stateHandler1.isCrLfSeparator()).isFalse();

        StateHandlerConfiguration stateHandlerConfiguration2 = new StateHandlerConfiguration();
        stateHandlerConfiguration2.setCrLfSeparator(true);
        StateHandler stateHandler2 = new StateHandler(listEventHandler, stateHandlerConfiguration2);
        Assertions.assertThat(stateHandler2.isCrLfSeparator()).isTrue();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushLastProcessedCharacterTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);

        stateHandler.pushLastProcessedCharacter('a');
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isNotNull();
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isEqualTo("a");

        stateHandler.pushLastProcessedCharacter('b');
        stateHandler.pushLastProcessedCharacter('c');
        stateHandler.pushLastProcessedCharacter('d');
        stateHandler.pushLastProcessedCharacter('e');
        stateHandler.pushLastProcessedCharacter('f');
        stateHandler.pushLastProcessedCharacter('g');
        stateHandler.pushLastProcessedCharacter('h');
        stateHandler.pushLastProcessedCharacter('i');
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isNotNull();
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isEqualTo("abcdefghi");

        stateHandler.pushLastProcessedCharacter('j');
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isNotNull();
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isEqualTo("abcdefghij");

        stateHandler.pushLastProcessedCharacter('1');
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isNotNull();
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isEqualTo("abcdefghij1");

        stateHandler.pushLastProcessedCharacter('2');
        stateHandler.pushLastProcessedCharacter('3');
        stateHandler.pushLastProcessedCharacter('4');
        stateHandler.pushLastProcessedCharacter('5');
        stateHandler.pushLastProcessedCharacter('6');
        stateHandler.pushLastProcessedCharacter('7');
        stateHandler.pushLastProcessedCharacter('8');
        stateHandler.pushLastProcessedCharacter('9');
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isNotNull();
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isEqualTo("abcdefghij123456789");

        stateHandler.pushLastProcessedCharacter('0');
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isNotNull();
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isEqualTo("abcdefghij1234567890");

        stateHandler.pushLastProcessedCharacter('a');
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isNotNull();
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isEqualTo("abcdefghij1234567890a");

        stateHandler.pushLastProcessedCharacter('b');
        stateHandler.pushLastProcessedCharacter('c');
        stateHandler.pushLastProcessedCharacter('d');
        stateHandler.pushLastProcessedCharacter('e');
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isNotNull();
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isEqualTo("abcdefghij1234567890abcde");

        stateHandler.pushLastProcessedCharacter('f');
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isNotNull();
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isEqualTo("bcdefghij1234567890abcdef");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushLastProcessedCharacterIgnoreEndOfInputTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);

        stateHandler.pushLastProcessedCharacter('a');
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isNotNull();
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isEqualTo("a");

        stateHandler.pushLastProcessedCharacter(SpecialCharacter.END_OF_INPUT);
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isNotNull();
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isEqualTo("a");

        stateHandler.pushLastProcessedCharacter('b');
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isNotNull();
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isEqualTo("ab");

        stateHandler.pushLastProcessedCharacter(SpecialCharacter.END_OF_INPUT);
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isNotNull();
        Assertions.assertThat(stateHandler.getLastProcessedCharacters()).isEqualTo("ab");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void createCsvParseExceptionTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
        stateHandler.pushLastProcessedCharacter('a');
        stateHandler.pushLastProcessedCharacter('b');
        stateHandler.pushLastProcessedCharacter('c');

        CsvParseException csvParseException1 = stateHandler.createCsvParseException('x');
        Assertions.assertThat(csvParseException1).hasMessage("Wrong character obtained: 'x' (120). Last characters: \"abc\".");

        CsvParseException csvParseException2 = stateHandler.createCsvParseException('y');
        Assertions.assertThat(csvParseException2).hasMessage("Wrong character obtained: 'y' (121). Last characters: \"abc\".");

        CsvParseException csvParseException3 = stateHandler.createCsvParseException(SpecialCharacter.END_OF_INPUT);
        Assertions.assertThat(csvParseException3).hasMessage("End of input obtained. Last characters: \"abc\".");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushCharacterTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
        stateHandler.pushCharacter('a');
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).isEmpty();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
        stateHandler.pushCharacter('a');
        stateHandler.pushColumn();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).isEmpty();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushRowTest() {
        ListEventHandler listEventHandler1 = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration1 = new StateHandlerConfiguration();
        StateHandler stateHandler1 = new StateHandler(listEventHandler1, stateHandlerConfiguration1);
        stateHandler1.pushCharacter('a');
        stateHandler1.pushColumn();
        stateHandler1.pushRow();
        List<List<String>> list1 = listEventHandler1.getCsv();
        Assertions.assertThat(list1).isNotNull();
        Assertions.assertThat(list1).hasSize(1);
        Assertions.assertThat(list1.get(0)).containsExactlyInOrder("a");

        ListEventHandler listEventHandler2 = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration2 = new StateHandlerConfiguration();
        StateHandler stateHandler2 = new StateHandler(listEventHandler2, stateHandlerConfiguration2);
        stateHandler2.pushCharacter('b');
        stateHandler2.pushColumn();
        stateHandler2.pushCharacter('c');
        stateHandler2.pushColumn();
        stateHandler2.pushRow();
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
        StateHandlerConfiguration stateHandlerConfiguration1 = new StateHandlerConfiguration();
        StateHandler stateHandler1 = new StateHandler(listEventHandler1, stateHandlerConfiguration1);
        stateHandler1.pushColumn();
        stateHandler1.pushRow();
        List<List<String>> list1 = listEventHandler1.getCsv();
        Assertions.assertThat(list1).isNotNull();
        Assertions.assertThat(list1).hasSize(1);
        Assertions.assertThat(list1.get(0)).containsExactlyInOrder("");

        ListEventHandler listEventHandler2 = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration2 = new StateHandlerConfiguration();
        StateHandler stateHandler2 = new StateHandler(listEventHandler2, stateHandlerConfiguration2);
        stateHandler2.pushCharacter('a');
        stateHandler2.pushColumn();
        stateHandler2.pushColumn();
        stateHandler2.pushCharacter('b');
        stateHandler2.pushColumn();
        stateHandler2.pushRow();
        List<List<String>> list2 = listEventHandler2.getCsv();
        Assertions.assertThat(list2).isNotNull();
        Assertions.assertThat(list2).hasSize(1);
        Assertions.assertThat(list2.get(0)).containsExactlyInOrder("a", "", "b");

        ListEventHandler listEventHandler3 = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration3 = new StateHandlerConfiguration();
        StateHandler stateHandler3 = new StateHandler(listEventHandler3, stateHandlerConfiguration3);
        stateHandler3.pushCharacter('a');
        stateHandler3.pushColumn();
        stateHandler3.pushColumn();
        stateHandler3.pushRow();
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
        StateHandlerConfiguration stateHandlerConfiguration1 = new StateHandlerConfiguration();
        StateHandler stateHandler1 = new StateHandler(listEventHandler1, stateHandlerConfiguration1);
        stateHandler1.pushRow();
        List<List<String>> list1 = listEventHandler1.getCsv();
        Assertions.assertThat(list1).isNotNull();
        Assertions.assertThat(list1).hasSize(1);
        Assertions.assertThat(list1.get(0)).containsExactlyInOrder();

        ListEventHandler listEventHandler2 = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration2 = new StateHandlerConfiguration();
        stateHandlerConfiguration2.setColumnCountCheckEnabled(false);
        StateHandler stateHandler2 = new StateHandler(listEventHandler2, stateHandlerConfiguration2);
        stateHandler2.pushCharacter('a');
        stateHandler2.pushColumn();
        stateHandler2.pushRow();
        stateHandler2.pushRow();
        stateHandler2.pushRow();
        stateHandler2.pushCharacter('b');
        stateHandler2.pushColumn();
        stateHandler2.pushRow();
        List<List<String>> list2 = listEventHandler2.getCsv();
        Assertions.assertThat(list2).isNotNull();
        Assertions.assertThat(list2).hasSize(4);
        Assertions.assertThat(list2.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(list2.get(1)).containsExactlyInOrder();
        Assertions.assertThat(list2.get(2)).containsExactlyInOrder();
        Assertions.assertThat(list2.get(3)).containsExactlyInOrder("b");

        ListEventHandler listEventHandler3 = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration3 = new StateHandlerConfiguration();
        stateHandlerConfiguration3.setColumnCountCheckEnabled(false);
        StateHandler stateHandler3 = new StateHandler(listEventHandler3, stateHandlerConfiguration3);
        stateHandler3.pushCharacter('a');
        stateHandler3.pushColumn();
        stateHandler3.pushRow();
        stateHandler3.pushRow();
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
    public void putMultipleCharactersInColumnTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
        stateHandler.pushCharacter('a');
        stateHandler.pushColumn();
        stateHandler.pushCharacter('b');
        stateHandler.pushCharacter('c');
        stateHandler.pushCharacter('d');
        stateHandler.pushCharacter('e');
        stateHandler.pushColumn();
        stateHandler.pushColumn();
        stateHandler.pushRow();
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
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        stateHandlerConfiguration.setColumnCountCheckEnabled(false);
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
        stateHandler.pushCharacter('a');
        stateHandler.pushRow();
        stateHandler.pushCharacter('b');
        stateHandler.pushCharacter('c');
        stateHandler.pushCharacter('d');
        stateHandler.pushCharacter('e');
        stateHandler.pushRow();
        stateHandler.pushCharacter('f');
        stateHandler.pushColumn();
        stateHandler.pushRow();
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
    @Test(expected = WrongColumnCountException.class)
    public void checkRectangularFailTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        stateHandlerConfiguration.setColumnCountCheckEnabled(true);
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
        stateHandler.pushCharacter('a');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        stateHandler.pushCharacter('b');
        stateHandler.pushColumn();
        stateHandler.pushCharacter('c');
        stateHandler.pushColumn();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void notReusableTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);

        stateHandler.pushCharacter('a');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        stateHandler.pushCharacter('b');
        stateHandler.pushCharacter('c');
        stateHandler.pushCharacter('d');
        stateHandler.pushCharacter('e');
        List<List<String>> list1 = listEventHandler.getCsv();
        Assertions.assertThat(list1).isNotNull();
        Assertions.assertThat(list1).hasSize(1);
        Assertions.assertThat(list1.get(0)).containsExactlyInOrder("a");

        stateHandler.pushCharacter('a');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        List<List<String>> list2 = listEventHandler.getCsv();
        Assertions.assertThat(list2).isNotNull();
        Assertions.assertThat(list2).hasSize(2);
        Assertions.assertThat(list2.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(list2.get(1)).containsExactlyInOrder("bcdea");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test(expected = WrongColumnCountException.class)
    public void notReusableRectangularFailTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        stateHandlerConfiguration.setColumnCountCheckEnabled(true);
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
        stateHandler.pushCharacter('a');
        stateHandler.pushColumn();
        stateHandler.pushCharacter('b');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        stateHandler.pushCharacter('c');
        stateHandler.pushColumn();
        stateHandler.pushCharacter('d');
        stateHandler.pushColumn();
        stateHandler.pushRow();

        List<List<String>> list = listEventHandler.getCsv();
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("a", "b");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("c", "d");

        stateHandler.pushCharacter('a');
        stateHandler.pushColumn();
        stateHandler.pushRow();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void checkNoColumnRectangularRectangularTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        stateHandlerConfiguration.setColumnCountCheckEnabled(true);
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
        stateHandler.pushRow();
        stateHandler.pushRow();
        stateHandler.pushRow();

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
    @Test(expected = WrongColumnCountException.class)
    public void checkNoColumnRectangularRectangularFailTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        stateHandlerConfiguration.setColumnCountCheckEnabled(true);
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
        stateHandler.pushRow();
        stateHandler.pushColumn();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushCharacterWithNoLengthAndNoCheckRestrictionTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        stateHandlerConfiguration.setColumnCountCheckEnabled(false);
        stateHandlerConfiguration.setMaxColumnLength(-1);
        stateHandlerConfiguration.setMaxColumnLengthCheckEnabled(false);
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
        stateHandler.pushCharacter('a');
        stateHandler.pushCharacter('b');
        stateHandler.pushCharacter('c');
        stateHandler.pushCharacter('d');
        stateHandler.pushColumn();
        stateHandler.pushCharacter('1');
        stateHandler.pushCharacter('2');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        stateHandler.pushCharacter('e');
        stateHandler.pushCharacter('f');
        stateHandler.pushCharacter('g');
        stateHandler.pushCharacter('h');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        List<List<String>> list = listEventHandler.getCsv();
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("abcd", "12");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("efgh");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushCharacterWithNoLengthAndCheckRestrictionTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        stateHandlerConfiguration.setColumnCountCheckEnabled(false);
        stateHandlerConfiguration.setMaxColumnLength(-1);
        stateHandlerConfiguration.setMaxColumnLengthCheckEnabled(true);
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
        stateHandler.pushCharacter('a');
        stateHandler.pushCharacter('b');
        stateHandler.pushCharacter('c');
        stateHandler.pushCharacter('d');
        stateHandler.pushColumn();
        stateHandler.pushCharacter('1');
        stateHandler.pushCharacter('2');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        stateHandler.pushCharacter('e');
        stateHandler.pushCharacter('f');
        stateHandler.pushCharacter('g');
        stateHandler.pushCharacter('h');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        List<List<String>> list = listEventHandler.getCsv();
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("abcd", "12");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("efgh");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushCharacterEmptyWithNoCheckRestrictionTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        stateHandlerConfiguration.setColumnCountCheckEnabled(false);
        stateHandlerConfiguration.setMaxColumnLength(0);
        stateHandlerConfiguration.setMaxColumnLengthCheckEnabled(false);
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
        stateHandler.pushCharacter('a');
        stateHandler.pushCharacter('b');
        stateHandler.pushCharacter('c');
        stateHandler.pushCharacter('d');
        stateHandler.pushColumn();
        stateHandler.pushCharacter('1');
        stateHandler.pushCharacter('2');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        stateHandler.pushCharacter('e');
        stateHandler.pushCharacter('f');
        stateHandler.pushCharacter('g');
        stateHandler.pushCharacter('h');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        List<List<String>> list = listEventHandler.getCsv();
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("", "");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushCharacterEmptyWithCheckRestrictionTest() {
        try {
            ListEventHandler listEventHandler = new ListEventHandler();
            StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
            stateHandlerConfiguration.setMaxColumnLength(0);
            stateHandlerConfiguration.setMaxColumnLengthCheckEnabled(true);
            StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
            stateHandler.pushLastProcessedCharacter('a');
            stateHandler.pushCharacter('a');
            Assertions.fail("StateHandler test fail");
        } catch (WrongColumnLengthException ex) {
            Assertions.assertThat(ex).hasMessage("Maximum column value length exceeded. Last characters: \"a\".");
        }
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushCharacterWithLengthAndNoCheckRestrictionTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
        stateHandlerConfiguration.setColumnCountCheckEnabled(false);
        stateHandlerConfiguration.setMaxColumnLength(3);
        stateHandlerConfiguration.setMaxColumnLengthCheckEnabled(false);
        StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
        stateHandler.pushCharacter('a');
        stateHandler.pushCharacter('b');
        stateHandler.pushCharacter('c');
        stateHandler.pushCharacter('d');
        stateHandler.pushColumn();
        stateHandler.pushCharacter('1');
        stateHandler.pushCharacter('2');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        stateHandler.pushCharacter('e');
        stateHandler.pushCharacter('f');
        stateHandler.pushCharacter('g');
        stateHandler.pushCharacter('h');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        List<List<String>> list = listEventHandler.getCsv();
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0)).containsExactlyInOrder("abc", "12");
        Assertions.assertThat(list.get(1)).containsExactlyInOrder("efg");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushCharacterWithLengthAndCheckRestrictionTest() {
        try {
            ListEventHandler listEventHandler = new ListEventHandler();
            StateHandlerConfiguration stateHandlerConfiguration = new StateHandlerConfiguration();
            stateHandlerConfiguration.setMaxColumnLength(3);
            stateHandlerConfiguration.setMaxColumnLengthCheckEnabled(true);
            StateHandler stateHandler = new StateHandler(listEventHandler, stateHandlerConfiguration);
            stateHandler.pushLastProcessedCharacter('a');
            stateHandler.pushCharacter('a');
            stateHandler.pushLastProcessedCharacter('b');
            stateHandler.pushCharacter('b');
            stateHandler.pushLastProcessedCharacter('c');
            stateHandler.pushCharacter('c');
            stateHandler.pushLastProcessedCharacter('d');
            stateHandler.pushCharacter('d');
            Assertions.fail("StateHandler test fail");
        } catch (WrongColumnLengthException ex) {
            Assertions.assertThat(ex).hasMessage("Maximum column value length exceeded. Last characters: \"abcd\".");
        }
    }

}
