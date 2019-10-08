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
import ru.d_shap.csv.CsvParserConfiguration;
import ru.d_shap.csv.CsvTest;

/**
 * Tests for {@link ListEventHandler}.
 *
 * @author Dmitry Shapovalov
 */
public final class ListEventHandlerTest extends CsvTest {

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
    public void configureTest() {
        ListEventHandler eventHandler = new ListEventHandler();
        CsvParserConfiguration csvParserConfiguration = createCsvParserConfiguration();

        csvParserConfiguration.setCommaSeparator(false);
        csvParserConfiguration.setSemicolonSeparator(false);
        csvParserConfiguration.setCrSeparator(false);
        csvParserConfiguration.setLfSeparator(false);
        csvParserConfiguration.setCrLfSeparator(false);
        csvParserConfiguration.setColumnCountCheckEnabled(false);
        csvParserConfiguration.setSkipEmptyRowsEnabled(false);
        csvParserConfiguration.setMaxColumnLength(0);
        csvParserConfiguration.setMaxColumnLengthCheckEnabled(false);
        eventHandler.configure(csvParserConfiguration);
        Assertions.assertThat(csvParserConfiguration.isCommaSeparator()).isFalse();
        Assertions.assertThat(csvParserConfiguration.isSemicolonSeparator()).isFalse();
        Assertions.assertThat(csvParserConfiguration.isCrSeparator()).isFalse();
        Assertions.assertThat(csvParserConfiguration.isLfSeparator()).isFalse();
        Assertions.assertThat(csvParserConfiguration.isCrLfSeparator()).isFalse();
        Assertions.assertThat(csvParserConfiguration.isColumnCountCheckEnabled()).isFalse();
        Assertions.assertThat(csvParserConfiguration.isSkipEmptyRowsEnabled()).isFalse();
        Assertions.assertThat(csvParserConfiguration.getMaxColumnLength()).isEqualTo(-1);
        Assertions.assertThat(csvParserConfiguration.isMaxColumnLengthCheckEnabled()).isFalse();

        csvParserConfiguration.setCommaSeparator(true);
        csvParserConfiguration.setSemicolonSeparator(true);
        csvParserConfiguration.setCrSeparator(true);
        csvParserConfiguration.setLfSeparator(true);
        csvParserConfiguration.setCrLfSeparator(true);
        csvParserConfiguration.setColumnCountCheckEnabled(true);
        csvParserConfiguration.setSkipEmptyRowsEnabled(true);
        csvParserConfiguration.setMaxColumnLength(1);
        csvParserConfiguration.setMaxColumnLengthCheckEnabled(true);
        eventHandler.configure(csvParserConfiguration);
        Assertions.assertThat(csvParserConfiguration.isCommaSeparator()).isTrue();
        Assertions.assertThat(csvParserConfiguration.isSemicolonSeparator()).isTrue();
        Assertions.assertThat(csvParserConfiguration.isCrSeparator()).isTrue();
        Assertions.assertThat(csvParserConfiguration.isLfSeparator()).isTrue();
        Assertions.assertThat(csvParserConfiguration.isCrLfSeparator()).isTrue();
        Assertions.assertThat(csvParserConfiguration.isColumnCountCheckEnabled()).isTrue();
        Assertions.assertThat(csvParserConfiguration.isSkipEmptyRowsEnabled()).isTrue();
        Assertions.assertThat(csvParserConfiguration.getMaxColumnLength()).isEqualTo(-1);
        Assertions.assertThat(csvParserConfiguration.isMaxColumnLengthCheckEnabled()).isFalse();
    }

    /**
     * {@link ListEventHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        ListEventHandler eventHandler = new ListEventHandler();

        eventHandler.pushColumn("a", 1);
        Assertions.assertThat(eventHandler.getCsv()).isNotNull();
        Assertions.assertThat(eventHandler.getCsv()).hasSize(0);

        eventHandler.pushColumn("bb", 2);
        eventHandler.pushColumn("ccc", 3);
        Assertions.assertThat(eventHandler.getCsv()).isNotNull();
        Assertions.assertThat(eventHandler.getCsv()).hasSize(0);
    }

    /**
     * {@link ListEventHandler} class test.
     */
    @Test
    public void pushColumnAndRowTest() {
        ListEventHandler eventHandler = new ListEventHandler();

        eventHandler.pushColumn("a", 1);
        eventHandler.pushRow();
        Assertions.assertThat(eventHandler.getCsv()).isNotNull();
        Assertions.assertThat(eventHandler.getCsv()).hasSize(1);
        Assertions.assertThat(eventHandler.getCsv().get(0)).containsExactlyInOrder("a");

        eventHandler.pushColumn("bb", 2);
        eventHandler.pushColumn("ccc", 3);
        eventHandler.pushRow();
        Assertions.assertThat(eventHandler.getCsv()).isNotNull();
        Assertions.assertThat(eventHandler.getCsv()).hasSize(2);
        Assertions.assertThat(eventHandler.getCsv().get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(eventHandler.getCsv().get(1)).containsExactlyInOrder("bb", "ccc");
    }

    /**
     * {@link ListEventHandler} class test.
     */
    @Test
    public void pushRowTest() {
        ListEventHandler eventHandler = new ListEventHandler();

        eventHandler.pushRow();
        Assertions.assertThat(eventHandler.getCsv()).isNotNull();
        Assertions.assertThat(eventHandler.getCsv()).hasSize(1);
        Assertions.assertThat(eventHandler.getCsv().get(0)).containsExactlyInOrder();

        eventHandler.pushRow();
        Assertions.assertThat(eventHandler.getCsv()).isNotNull();
        Assertions.assertThat(eventHandler.getCsv()).hasSize(2);
        Assertions.assertThat(eventHandler.getCsv().get(0)).containsExactlyInOrder();
        Assertions.assertThat(eventHandler.getCsv().get(1)).containsExactlyInOrder();

        eventHandler.pushRow();
        Assertions.assertThat(eventHandler.getCsv()).isNotNull();
        Assertions.assertThat(eventHandler.getCsv()).hasSize(3);
        Assertions.assertThat(eventHandler.getCsv().get(0)).containsExactlyInOrder();
        Assertions.assertThat(eventHandler.getCsv().get(1)).containsExactlyInOrder();
        Assertions.assertThat(eventHandler.getCsv().get(2)).containsExactlyInOrder();
    }

    /**
     * {@link ListEventHandler} class test.
     */
    @Test
    public void pushRowsWithSameColumnCountTest() {
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
        eventHandler.pushColumn("kl", 2);
        eventHandler.pushRow();
        Assertions.assertThat(eventHandler.getCsv()).isNotNull();
        Assertions.assertThat(eventHandler.getCsv()).hasSize(4);
        Assertions.assertThat(eventHandler.getCsv().get(0)).containsExactlyInOrder("a", "bc");
        Assertions.assertThat(eventHandler.getCsv().get(1)).containsExactlyInOrder("d", "ef");
        Assertions.assertThat(eventHandler.getCsv().get(2)).containsExactlyInOrder("g", "hi");
        Assertions.assertThat(eventHandler.getCsv().get(3)).containsExactlyInOrder("j", "kl");
    }

    /**
     * {@link ListEventHandler} class test.
     */
    @Test
    public void pushRowsWithDifferentColumnCountTest() {
        ListEventHandler eventHandler = new ListEventHandler();

        eventHandler.pushColumn("a", 1);
        eventHandler.pushColumn("bc", 2);
        eventHandler.pushRow();
        eventHandler.pushColumn("d", 1);
        eventHandler.pushRow();
        eventHandler.pushColumn("e", 1);
        eventHandler.pushColumn("fg", 2);
        eventHandler.pushColumn("hij", 3);
        eventHandler.pushRow();
        eventHandler.pushRow();
        Assertions.assertThat(eventHandler.getCsv()).isNotNull();
        Assertions.assertThat(eventHandler.getCsv()).hasSize(4);
        Assertions.assertThat(eventHandler.getCsv().get(0)).containsExactlyInOrder("a", "bc");
        Assertions.assertThat(eventHandler.getCsv().get(1)).containsExactlyInOrder("d");
        Assertions.assertThat(eventHandler.getCsv().get(2)).containsExactlyInOrder("e", "fg", "hij");
        Assertions.assertThat(eventHandler.getCsv().get(3)).containsExactlyInOrder();
    }

}
