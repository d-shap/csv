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
public final class CsvParserConfigurationTest extends CsvTest {

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
        CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(csvParserConfiguration.isCommaSeparator()).isFalse();
        csvParserConfiguration.setCommaSeparator(false);
        Assertions.assertThat(csvParserConfiguration.isCommaSeparator()).isFalse();
        csvParserConfiguration.setCommaSeparator(true);
        Assertions.assertThat(csvParserConfiguration.isCommaSeparator()).isTrue();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void semicolonSeparatorTest() {
        CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(csvParserConfiguration.isSemicolonSeparator()).isFalse();
        csvParserConfiguration.setSemicolonSeparator(false);
        Assertions.assertThat(csvParserConfiguration.isSemicolonSeparator()).isFalse();
        csvParserConfiguration.setSemicolonSeparator(true);
        Assertions.assertThat(csvParserConfiguration.isSemicolonSeparator()).isTrue();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void crSeparatorTest() {
        CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(csvParserConfiguration.isCrSeparator()).isFalse();
        csvParserConfiguration.setCrSeparator(false);
        Assertions.assertThat(csvParserConfiguration.isCrSeparator()).isFalse();
        csvParserConfiguration.setCrSeparator(true);
        Assertions.assertThat(csvParserConfiguration.isCrSeparator()).isTrue();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void lfSeparatorTest() {
        CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(csvParserConfiguration.isLfSeparator()).isFalse();
        csvParserConfiguration.setLfSeparator(false);
        Assertions.assertThat(csvParserConfiguration.isLfSeparator()).isFalse();
        csvParserConfiguration.setLfSeparator(true);
        Assertions.assertThat(csvParserConfiguration.isLfSeparator()).isTrue();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void crLfSeparatorTest() {
        CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(csvParserConfiguration.isCrLfSeparator()).isFalse();
        csvParserConfiguration.setCrLfSeparator(false);
        Assertions.assertThat(csvParserConfiguration.isCrLfSeparator()).isFalse();
        csvParserConfiguration.setCrLfSeparator(true);
        Assertions.assertThat(csvParserConfiguration.isCrLfSeparator()).isTrue();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void columnCountCheckEnabledTest() {
        CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(csvParserConfiguration.isColumnCountCheckEnabled()).isFalse();
        csvParserConfiguration.setColumnCountCheckEnabled(false);
        Assertions.assertThat(csvParserConfiguration.isColumnCountCheckEnabled()).isFalse();
        csvParserConfiguration.setColumnCountCheckEnabled(true);
        Assertions.assertThat(csvParserConfiguration.isColumnCountCheckEnabled()).isTrue();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void skipEmptyRowsEnabledTest() {
        CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(csvParserConfiguration.isSkipEmptyRowsEnabled()).isFalse();
        csvParserConfiguration.setSkipEmptyRowsEnabled(false);
        Assertions.assertThat(csvParserConfiguration.isSkipEmptyRowsEnabled()).isFalse();
        csvParserConfiguration.setSkipEmptyRowsEnabled(true);
        Assertions.assertThat(csvParserConfiguration.isSkipEmptyRowsEnabled()).isTrue();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void maxColumnLengthTest() {
        CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(csvParserConfiguration.getMaxColumnLength()).isLessThan(0);
        csvParserConfiguration.setMaxColumnLength(1);
        Assertions.assertThat(csvParserConfiguration.getMaxColumnLength()).isEqualTo(1);
        csvParserConfiguration.setMaxColumnLength(2);
        Assertions.assertThat(csvParserConfiguration.getMaxColumnLength()).isEqualTo(2);
        csvParserConfiguration.setMaxColumnLength(-1);
        Assertions.assertThat(csvParserConfiguration.getMaxColumnLength()).isEqualTo(-1);
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void maxColumnLengthCheckEnabledTest() {
        CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(csvParserConfiguration.isMaxColumnLengthCheckEnabled()).isFalse();
        csvParserConfiguration.setMaxColumnLengthCheckEnabled(false);
        Assertions.assertThat(csvParserConfiguration.isMaxColumnLengthCheckEnabled()).isFalse();
        csvParserConfiguration.setMaxColumnLengthCheckEnabled(true);
        Assertions.assertThat(csvParserConfiguration.isMaxColumnLengthCheckEnabled()).isTrue();
    }

}
