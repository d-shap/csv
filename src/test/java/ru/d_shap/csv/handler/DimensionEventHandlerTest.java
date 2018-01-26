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
 * Tests for {@link DimensionEventHandler}.
 *
 * @author Dmitry Shapovalov
 */
public final class DimensionEventHandlerTest extends CsvTest {

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
    public void configureTest() {
        DimensionEventHandler eventHandler = new DimensionEventHandler();
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
        Assertions.assertThat(csvParserConfiguration.isColumnCountCheckEnabled()).isTrue();
        Assertions.assertThat(csvParserConfiguration.isSkipEmptyRowsEnabled()).isFalse();
        Assertions.assertThat(csvParserConfiguration.getMaxColumnLength()).isEqualTo(0);
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
        Assertions.assertThat(csvParserConfiguration.getMaxColumnLength()).isEqualTo(0);
        Assertions.assertThat(csvParserConfiguration.isMaxColumnLengthCheckEnabled()).isFalse();
    }

    /**
     * {@link DimensionEventHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        DimensionEventHandler eventHandler = new DimensionEventHandler();

        eventHandler.pushColumn("a", 1);
        Assertions.assertThat(eventHandler.getColumnCount()).isEqualTo(1);
        Assertions.assertThat(eventHandler.getRowCount()).isEqualTo(0);

        eventHandler.pushColumn("bb", 2);
        eventHandler.pushColumn("ccc", 3);
        Assertions.assertThat(eventHandler.getColumnCount()).isEqualTo(3);
        Assertions.assertThat(eventHandler.getRowCount()).isEqualTo(0);
    }

    /**
     * {@link DimensionEventHandler} class test.
     */
    @Test
    public void pushColumnAndRowTest() {
        DimensionEventHandler eventHandler = new DimensionEventHandler();

        eventHandler.pushColumn("a", 1);
        eventHandler.pushRow();
        Assertions.assertThat(eventHandler.getColumnCount()).isEqualTo(1);
        Assertions.assertThat(eventHandler.getRowCount()).isEqualTo(1);

        eventHandler.pushColumn("bb", 2);
        eventHandler.pushColumn("ccc", 3);
        eventHandler.pushRow();
        Assertions.assertThat(eventHandler.getColumnCount()).isEqualTo(1);
        Assertions.assertThat(eventHandler.getRowCount()).isEqualTo(2);
    }

    /**
     * {@link DimensionEventHandler} class test.
     */
    @Test
    public void pushRowTest() {
        DimensionEventHandler eventHandler = new DimensionEventHandler();

        eventHandler.pushRow();
        Assertions.assertThat(eventHandler.getColumnCount()).isEqualTo(0);
        Assertions.assertThat(eventHandler.getRowCount()).isEqualTo(1);

        eventHandler.pushRow();
        Assertions.assertThat(eventHandler.getColumnCount()).isEqualTo(0);
        Assertions.assertThat(eventHandler.getRowCount()).isEqualTo(2);

        eventHandler.pushRow();
        Assertions.assertThat(eventHandler.getColumnCount()).isEqualTo(0);
        Assertions.assertThat(eventHandler.getRowCount()).isEqualTo(3);
    }

    /**
     * {@link DimensionEventHandler} class test.
     */
    @Test
    public void pushRowsWithSameColumnCountTest() {
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
        eventHandler.pushColumn("klm", 3);
        eventHandler.pushRow();
        Assertions.assertThat(eventHandler.getColumnCount()).isEqualTo(2);
        Assertions.assertThat(eventHandler.getRowCount()).isEqualTo(4);
    }

    /**
     * {@link DimensionEventHandler} class test.
     */
    @Test
    public void pushRowsWithDifferentColumnCountTest() {
        DimensionEventHandler eventHandler = new DimensionEventHandler();

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
        Assertions.assertThat(eventHandler.getColumnCount()).isEqualTo(2);
        Assertions.assertThat(eventHandler.getRowCount()).isEqualTo(4);
    }

}
