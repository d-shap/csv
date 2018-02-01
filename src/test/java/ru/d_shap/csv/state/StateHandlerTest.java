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

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.csv.CsvParseException;
import ru.d_shap.csv.CsvParserConfiguration;
import ru.d_shap.csv.CsvTest;
import ru.d_shap.csv.WrongColumnCountException;
import ru.d_shap.csv.WrongColumnLengthException;
import ru.d_shap.csv.WrongColumnSeparatorException;
import ru.d_shap.csv.WrongRowSeparatorException;
import ru.d_shap.csv.handler.ListEventHandler;

/**
 * Tests for {@link StateHandler}.
 *
 * @author Dmitry Shapovalov
 */
public final class StateHandlerTest extends CsvTest {

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
    public void validateTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        try {
            CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
            csvParserConfiguration.setCommaSeparator(false);
            csvParserConfiguration.setSemicolonSeparator(false);
            StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);
            Assertions.fail("StateHandler test fail");
        } catch (WrongColumnSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No column separator is specified.");
        }

        try {
            CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
            csvParserConfiguration.setCrSeparator(false);
            csvParserConfiguration.setLfSeparator(false);
            csvParserConfiguration.setCrLfSeparator(false);
            StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);
            Assertions.fail("StateHandler test fail");
        } catch (WrongRowSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No row separator is specified.");
        }
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void isCommaSeparatorTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        CsvParserConfiguration csvParserConfiguration1 = createCsvParserConfiguration();
        csvParserConfiguration1.setCommaSeparator(false);
        StateHandler stateHandler1 = new StateHandler(listEventHandler, csvParserConfiguration1);
        Assertions.assertThat(stateHandler1.isCommaSeparator()).isFalse();

        CsvParserConfiguration csvParserConfiguration2 = createCsvParserConfiguration();
        csvParserConfiguration2.setCommaSeparator(true);
        StateHandler stateHandler2 = new StateHandler(listEventHandler, csvParserConfiguration2);
        Assertions.assertThat(stateHandler2.isCommaSeparator()).isTrue();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void isSemicolonSeparatorTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        CsvParserConfiguration csvParserConfiguration1 = createCsvParserConfiguration();
        csvParserConfiguration1.setSemicolonSeparator(false);
        StateHandler stateHandler1 = new StateHandler(listEventHandler, csvParserConfiguration1);
        Assertions.assertThat(stateHandler1.isSemicolonSeparator()).isFalse();

        CsvParserConfiguration csvParserConfiguration2 = createCsvParserConfiguration();
        csvParserConfiguration2.setSemicolonSeparator(true);
        StateHandler stateHandler2 = new StateHandler(listEventHandler, csvParserConfiguration2);
        Assertions.assertThat(stateHandler2.isSemicolonSeparator()).isTrue();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void isCrSeparatorTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        CsvParserConfiguration csvParserConfiguration1 = createCsvParserConfiguration();
        csvParserConfiguration1.setCrSeparator(false);
        StateHandler stateHandler1 = new StateHandler(listEventHandler, csvParserConfiguration1);
        Assertions.assertThat(stateHandler1.isCrSeparator()).isFalse();

        CsvParserConfiguration csvParserConfiguration2 = createCsvParserConfiguration();
        csvParserConfiguration2.setCrSeparator(true);
        StateHandler stateHandler2 = new StateHandler(listEventHandler, csvParserConfiguration2);
        Assertions.assertThat(stateHandler2.isCrSeparator()).isTrue();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void isLfSeparatorTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        CsvParserConfiguration csvParserConfiguration1 = createCsvParserConfiguration();
        csvParserConfiguration1.setLfSeparator(false);
        StateHandler stateHandler1 = new StateHandler(listEventHandler, csvParserConfiguration1);
        Assertions.assertThat(stateHandler1.isLfSeparator()).isFalse();

        CsvParserConfiguration csvParserConfiguration2 = createCsvParserConfiguration();
        csvParserConfiguration2.setLfSeparator(true);
        StateHandler stateHandler2 = new StateHandler(listEventHandler, csvParserConfiguration2);
        Assertions.assertThat(stateHandler2.isLfSeparator()).isTrue();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void isCrLfSeparatorTest() {
        ListEventHandler listEventHandler = new ListEventHandler();

        CsvParserConfiguration csvParserConfiguration1 = createCsvParserConfiguration();
        csvParserConfiguration1.setCrLfSeparator(false);
        StateHandler stateHandler1 = new StateHandler(listEventHandler, csvParserConfiguration1);
        Assertions.assertThat(stateHandler1.isCrLfSeparator()).isFalse();

        CsvParserConfiguration csvParserConfiguration2 = createCsvParserConfiguration();
        csvParserConfiguration2.setCrLfSeparator(true);
        StateHandler stateHandler2 = new StateHandler(listEventHandler, csvParserConfiguration2);
        Assertions.assertThat(stateHandler2.isCrLfSeparator()).isTrue();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void getLastProcessedCharactersTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);

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
    public void getLastProcessedEndOfInputCharactersTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);

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
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);
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
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);

        stateHandler.pushCharacter('a');
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).isEmpty();

        stateHandler.pushCharacter('b');
        stateHandler.pushCharacter('c');
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).isEmpty();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);
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
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);

        stateHandler.pushCharacter('a');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(1);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("a");

        stateHandler.pushCharacter('b');
        stateHandler.pushColumn();
        stateHandler.pushCharacter('c');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(2);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder("b", "c");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushEmptyColumnTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);

        stateHandler.pushColumn();
        stateHandler.pushRow();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(1);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("");

        stateHandler.pushCharacter('a');
        stateHandler.pushColumn();
        stateHandler.pushColumn();
        stateHandler.pushCharacter('b');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(2);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("");
        Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder("a", "", "b");

        stateHandler.pushCharacter('a');
        stateHandler.pushColumn();
        stateHandler.pushColumn();
        stateHandler.pushRow();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(3);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("");
        Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder("a", "", "b");
        Assertions.assertThat(listEventHandler.getCsv().get(2)).containsExactlyInOrder("a", "");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushColumnWithColumnCountCheckTest() {
        try {
            ListEventHandler listEventHandler = new ListEventHandler();
            CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
            csvParserConfiguration.setColumnCountCheckEnabled(true);
            StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);

            stateHandler.pushCharacter('a');
            stateHandler.pushColumn();
            stateHandler.pushRow();
            stateHandler.pushCharacter('b');
            stateHandler.pushColumn();
            stateHandler.pushRow();
            stateHandler.pushCharacter('c');
            stateHandler.pushColumn();
            stateHandler.pushCharacter('d');
            stateHandler.pushColumn();
            Assertions.fail("StateHandler test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"\".");
        }
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushColumnWithColumnCountCheckNotReusableTest() {
        try {
            ListEventHandler listEventHandler = new ListEventHandler();
            CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
            csvParserConfiguration.setColumnCountCheckEnabled(true);
            StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);

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

            Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
            Assertions.assertThat(listEventHandler.getCsv()).hasSize(2);
            Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("a", "b");
            Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder("c", "d");

            stateHandler.pushCharacter('a');
            stateHandler.pushColumn();
            stateHandler.pushCharacter('b');
            stateHandler.pushColumn();
            stateHandler.pushCharacter('c');
            stateHandler.pushColumn();
            Assertions.fail("StateHandler test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"\".");
        }
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushRowWithNoPushColumnTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);
        stateHandler.pushRow();
        stateHandler.pushColumn();
        stateHandler.pushRow();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(2);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder();
        Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder("");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushRowWithNoPushColumnWithColumnCountCheckTest() {
        try {
            ListEventHandler listEventHandler = new ListEventHandler();
            CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
            csvParserConfiguration.setColumnCountCheckEnabled(true);
            StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);
            stateHandler.pushRow();
            stateHandler.pushColumn();
            Assertions.fail("StateHandler test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"\".");
        }
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushEmptyRowTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);

        stateHandler.pushRow();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(1);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder();

        stateHandler.pushCharacter('a');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        stateHandler.pushRow();
        stateHandler.pushRow();
        stateHandler.pushCharacter('b');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(5);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder();
        Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder("a");
        Assertions.assertThat(listEventHandler.getCsv().get(2)).containsExactlyInOrder();
        Assertions.assertThat(listEventHandler.getCsv().get(3)).containsExactlyInOrder();
        Assertions.assertThat(listEventHandler.getCsv().get(4)).containsExactlyInOrder("b");

        stateHandler.pushCharacter('c');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        stateHandler.pushRow();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(7);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder();
        Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder("a");
        Assertions.assertThat(listEventHandler.getCsv().get(2)).containsExactlyInOrder();
        Assertions.assertThat(listEventHandler.getCsv().get(3)).containsExactlyInOrder();
        Assertions.assertThat(listEventHandler.getCsv().get(4)).containsExactlyInOrder("b");
        Assertions.assertThat(listEventHandler.getCsv().get(5)).containsExactlyInOrder("c");
        Assertions.assertThat(listEventHandler.getCsv().get(6)).containsExactlyInOrder();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushEmptyRowWithSkipEmptyRowsEnabledTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        csvParserConfiguration.setSkipEmptyRowsEnabled(true);
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);

        stateHandler.pushRow();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(0);

        stateHandler.pushCharacter('a');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        stateHandler.pushRow();
        stateHandler.pushRow();
        stateHandler.pushCharacter('b');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(2);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder("b");

        stateHandler.pushCharacter('c');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        stateHandler.pushRow();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(3);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder("b");
        Assertions.assertThat(listEventHandler.getCsv().get(2)).containsExactlyInOrder("c");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushRowWithColumnCountCheckTest() {
        try {
            ListEventHandler listEventHandler = new ListEventHandler();
            CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
            csvParserConfiguration.setColumnCountCheckEnabled(true);
            StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);

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
            stateHandler.pushCharacter('e');
            stateHandler.pushColumn();
            stateHandler.pushRow();
            Assertions.fail("StateHandler test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"\".");
        }
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushRowWithColumnCountCheckNotReusableTest() {
        try {
            ListEventHandler listEventHandler = new ListEventHandler();
            CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
            csvParserConfiguration.setColumnCountCheckEnabled(true);
            StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);

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

            Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
            Assertions.assertThat(listEventHandler.getCsv()).hasSize(2);
            Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("a", "b");
            Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder("c", "d");

            stateHandler.pushCharacter('e');
            stateHandler.pushColumn();
            stateHandler.pushRow();
            Assertions.fail("StateHandler test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"\".");
        }
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushRowWithNoColumnsWithColumnCountCheckTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        csvParserConfiguration.setColumnCountCheckEnabled(true);
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);
        stateHandler.pushRow();
        stateHandler.pushRow();
        stateHandler.pushRow();

        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(3);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder();
        Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder();
        Assertions.assertThat(listEventHandler.getCsv().get(2)).containsExactlyInOrder();
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void notReusableTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);

        stateHandler.pushCharacter('a');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        stateHandler.pushCharacter('b');
        stateHandler.pushCharacter('c');
        stateHandler.pushCharacter('d');
        stateHandler.pushCharacter('e');
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(1);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("a");

        stateHandler.pushCharacter('a');
        stateHandler.pushColumn();
        stateHandler.pushRow();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(2);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder("bcdea");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void putMultipleCharactersInColumnTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);
        stateHandler.pushCharacter('a');
        stateHandler.pushColumn();
        stateHandler.pushCharacter('b');
        stateHandler.pushCharacter('c');
        stateHandler.pushCharacter('d');
        stateHandler.pushCharacter('e');
        stateHandler.pushColumn();
        stateHandler.pushColumn();
        stateHandler.pushRow();
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(1);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("a", "bcde", "");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushCharacterWithNoPushColumnTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        csvParserConfiguration.setColumnCountCheckEnabled(false);
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);
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
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(3);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder();
        Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder();
        Assertions.assertThat(listEventHandler.getCsv().get(2)).containsExactlyInOrder("f");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushCharacterWithNoColumnLengthAndWithNoColumnLengthCheckTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        csvParserConfiguration.setMaxColumnLength(-1);
        csvParserConfiguration.setMaxColumnLengthCheckEnabled(false);
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);
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
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(2);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("abcd", "12");
        Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder("efgh");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushCharacterWithNoColumnLengthAndWithColumnLengthCheckTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        csvParserConfiguration.setMaxColumnLength(-1);
        csvParserConfiguration.setMaxColumnLengthCheckEnabled(true);
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);
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
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(2);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("abcd", "12");
        Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder("efgh");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushCharacterWithColumnLength0AndWithNoColumnLengthCheckTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        csvParserConfiguration.setMaxColumnLength(0);
        csvParserConfiguration.setMaxColumnLengthCheckEnabled(false);
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);
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
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(2);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("", "");
        Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder("");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushCharacterWithColumnLength0AndWithColumnLengthCheckTest() {
        try {
            ListEventHandler listEventHandler = new ListEventHandler();
            CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
            csvParserConfiguration.setMaxColumnLength(0);
            csvParserConfiguration.setMaxColumnLengthCheckEnabled(true);
            StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);
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
    public void pushCharacterWithColumnLength3AndWithNoColumnLengthCheckTest() {
        ListEventHandler listEventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
        csvParserConfiguration.setMaxColumnLength(3);
        csvParserConfiguration.setMaxColumnLengthCheckEnabled(false);
        StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);
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
        Assertions.assertThat(listEventHandler.getCsv()).isNotNull();
        Assertions.assertThat(listEventHandler.getCsv()).hasSize(2);
        Assertions.assertThat(listEventHandler.getCsv().get(0)).containsExactlyInOrder("abc", "12");
        Assertions.assertThat(listEventHandler.getCsv().get(1)).containsExactlyInOrder("efg");
    }

    /**
     * {@link StateHandler} class test.
     */
    @Test
    public void pushCharacterWithColumnLength3AndWithColumnLengthCheckTest() {
        try {
            ListEventHandler listEventHandler = new ListEventHandler();
            CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();
            csvParserConfiguration.setMaxColumnLength(3);
            csvParserConfiguration.setMaxColumnLengthCheckEnabled(true);
            StateHandler stateHandler = new StateHandler(listEventHandler, csvParserConfiguration);
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
