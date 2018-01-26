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
package ru.d_shap.csv;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link ru.d_shap.csv.CsvParserConfiguration}.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvParserConfigurationTest {

    /**
     * Test class constructor.
     */
    public CsvParserConfigurationTest() {
        super();
    }

    /**
     * {@link ru.d_shap.csv.CsvParserConfiguration} class test.
     */
    @Test
    public void commaSeparatorTest() {
        CsvParserConfiguration stateHandlerConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(stateHandlerConfiguration.isCommaSeparator()).isFalse();
        stateHandlerConfiguration.setCommaSeparator(false);
        Assertions.assertThat(stateHandlerConfiguration.isCommaSeparator()).isFalse();
        stateHandlerConfiguration.setCommaSeparator(true);
        Assertions.assertThat(stateHandlerConfiguration.isCommaSeparator()).isTrue();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void semicolonSeparatorTest() {
        CsvParserConfiguration stateHandlerConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(stateHandlerConfiguration.isSemicolonSeparator()).isFalse();
        stateHandlerConfiguration.setSemicolonSeparator(false);
        Assertions.assertThat(stateHandlerConfiguration.isSemicolonSeparator()).isFalse();
        stateHandlerConfiguration.setSemicolonSeparator(true);
        Assertions.assertThat(stateHandlerConfiguration.isSemicolonSeparator()).isTrue();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void crSeparatorTest() {
        CsvParserConfiguration stateHandlerConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(stateHandlerConfiguration.isCrSeparator()).isFalse();
        stateHandlerConfiguration.setCrSeparator(false);
        Assertions.assertThat(stateHandlerConfiguration.isCrSeparator()).isFalse();
        stateHandlerConfiguration.setCrSeparator(true);
        Assertions.assertThat(stateHandlerConfiguration.isCrSeparator()).isTrue();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void lfSeparatorTest() {
        CsvParserConfiguration stateHandlerConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(stateHandlerConfiguration.isLfSeparator()).isFalse();
        stateHandlerConfiguration.setLfSeparator(false);
        Assertions.assertThat(stateHandlerConfiguration.isLfSeparator()).isFalse();
        stateHandlerConfiguration.setLfSeparator(true);
        Assertions.assertThat(stateHandlerConfiguration.isLfSeparator()).isTrue();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void crLfSeparatorTest() {
        CsvParserConfiguration stateHandlerConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(stateHandlerConfiguration.isCrLfSeparator()).isFalse();
        stateHandlerConfiguration.setCrLfSeparator(false);
        Assertions.assertThat(stateHandlerConfiguration.isCrLfSeparator()).isFalse();
        stateHandlerConfiguration.setCrLfSeparator(true);
        Assertions.assertThat(stateHandlerConfiguration.isCrLfSeparator()).isTrue();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void columnCountCheckEnabledTest() {
        CsvParserConfiguration stateHandlerConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(stateHandlerConfiguration.isColumnCountCheckEnabled()).isFalse();
        stateHandlerConfiguration.setColumnCountCheckEnabled(false);
        Assertions.assertThat(stateHandlerConfiguration.isColumnCountCheckEnabled()).isFalse();
        stateHandlerConfiguration.setColumnCountCheckEnabled(true);
        Assertions.assertThat(stateHandlerConfiguration.isColumnCountCheckEnabled()).isTrue();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void skipEmptyRowsEnabledTest() {
        CsvParserConfiguration stateHandlerConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(stateHandlerConfiguration.isSkipEmptyRowsEnabled()).isFalse();
        stateHandlerConfiguration.setSkipEmptyRowsEnabled(false);
        Assertions.assertThat(stateHandlerConfiguration.isSkipEmptyRowsEnabled()).isFalse();
        stateHandlerConfiguration.setSkipEmptyRowsEnabled(true);
        Assertions.assertThat(stateHandlerConfiguration.isSkipEmptyRowsEnabled()).isTrue();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void maxColumnLengthTest() {
        CsvParserConfiguration stateHandlerConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(stateHandlerConfiguration.getMaxColumnLength()).isLessThan(0);
        stateHandlerConfiguration.setMaxColumnLength(1);
        Assertions.assertThat(stateHandlerConfiguration.getMaxColumnLength()).isEqualTo(1);
        stateHandlerConfiguration.setMaxColumnLength(2);
        Assertions.assertThat(stateHandlerConfiguration.getMaxColumnLength()).isEqualTo(2);
        stateHandlerConfiguration.setMaxColumnLength(-1);
        Assertions.assertThat(stateHandlerConfiguration.getMaxColumnLength()).isEqualTo(-1);
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void maxColumnLengthCheckEnabledTest() {
        CsvParserConfiguration stateHandlerConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(stateHandlerConfiguration.isMaxColumnLengthCheckEnabled()).isFalse();
        stateHandlerConfiguration.setMaxColumnLengthCheckEnabled(false);
        Assertions.assertThat(stateHandlerConfiguration.isMaxColumnLengthCheckEnabled()).isFalse();
        stateHandlerConfiguration.setMaxColumnLengthCheckEnabled(true);
        Assertions.assertThat(stateHandlerConfiguration.isMaxColumnLengthCheckEnabled()).isTrue();
    }

}
