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
 * Tests for {@link CsvParserConfiguration}.
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
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void defaultValuesTest() {
        CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
        Assertions.assertThat(csvParserConfiguration.isCommaSeparator()).isFalse();
        Assertions.assertThat(csvParserConfiguration.isSemicolonSeparator()).isFalse();
        Assertions.assertThat(csvParserConfiguration.isCrSeparator()).isFalse();
        Assertions.assertThat(csvParserConfiguration.isLfSeparator()).isFalse();
        Assertions.assertThat(csvParserConfiguration.isCrLfSeparator()).isFalse();
        Assertions.assertThat(csvParserConfiguration.isColumnCountCheckEnabled()).isFalse();
        Assertions.assertThat(csvParserConfiguration.isSkipEmptyRowsEnabled()).isFalse();
        Assertions.assertThat(csvParserConfiguration.getMaxColumnLength()).isEqualTo(0);
        Assertions.assertThat(csvParserConfiguration.isMaxColumnLengthCheckEnabled()).isFalse();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void copyOfTest() {
        CsvParserConfiguration csvParserConfiguration1 = new CsvParserConfiguration();
        csvParserConfiguration1.setCommaSeparator(true);
        csvParserConfiguration1.setSemicolonSeparator(true);
        csvParserConfiguration1.setCrSeparator(true);
        csvParserConfiguration1.setLfSeparator(true);
        csvParserConfiguration1.setCrLfSeparator(true);
        csvParserConfiguration1.setColumnCountCheckEnabled(true);
        csvParserConfiguration1.setSkipEmptyRowsEnabled(true);
        csvParserConfiguration1.setMaxColumnLength(1);
        csvParserConfiguration1.setMaxColumnLengthCheckEnabled(true);
        CsvParserConfiguration csvParserConfigurationCopy1 = csvParserConfiguration1.copyOf();
        Assertions.assertThat(csvParserConfigurationCopy1.isCommaSeparator()).isTrue();
        Assertions.assertThat(csvParserConfigurationCopy1.isSemicolonSeparator()).isTrue();
        Assertions.assertThat(csvParserConfigurationCopy1.isCrSeparator()).isTrue();
        Assertions.assertThat(csvParserConfigurationCopy1.isLfSeparator()).isTrue();
        Assertions.assertThat(csvParserConfigurationCopy1.isCrLfSeparator()).isTrue();
        Assertions.assertThat(csvParserConfigurationCopy1.isColumnCountCheckEnabled()).isTrue();
        Assertions.assertThat(csvParserConfigurationCopy1.isSkipEmptyRowsEnabled()).isTrue();
        Assertions.assertThat(csvParserConfigurationCopy1.getMaxColumnLength()).isEqualTo(1);
        Assertions.assertThat(csvParserConfigurationCopy1.isMaxColumnLengthCheckEnabled()).isTrue();

        CsvParserConfiguration csvParserConfiguration2 = new CsvParserConfiguration();
        csvParserConfiguration2.setCommaSeparator(false);
        csvParserConfiguration2.setSemicolonSeparator(false);
        csvParserConfiguration2.setCrSeparator(false);
        csvParserConfiguration2.setLfSeparator(false);
        csvParserConfiguration2.setCrLfSeparator(false);
        csvParserConfiguration2.setColumnCountCheckEnabled(false);
        csvParserConfiguration2.setSkipEmptyRowsEnabled(false);
        csvParserConfiguration2.setMaxColumnLength(-1);
        csvParserConfiguration2.setMaxColumnLengthCheckEnabled(false);
        CsvParserConfiguration csvParserConfigurationCopy2 = csvParserConfiguration2.copyOf();
        Assertions.assertThat(csvParserConfigurationCopy2.isCommaSeparator()).isFalse();
        Assertions.assertThat(csvParserConfigurationCopy2.isSemicolonSeparator()).isFalse();
        Assertions.assertThat(csvParserConfigurationCopy2.isCrSeparator()).isFalse();
        Assertions.assertThat(csvParserConfigurationCopy2.isLfSeparator()).isFalse();
        Assertions.assertThat(csvParserConfigurationCopy2.isCrLfSeparator()).isFalse();
        Assertions.assertThat(csvParserConfigurationCopy2.isColumnCountCheckEnabled()).isFalse();
        Assertions.assertThat(csvParserConfigurationCopy2.isSkipEmptyRowsEnabled()).isFalse();
        Assertions.assertThat(csvParserConfigurationCopy2.getMaxColumnLength()).isEqualTo(-1);
        Assertions.assertThat(csvParserConfigurationCopy2.isMaxColumnLengthCheckEnabled()).isFalse();
    }

    /**
     * {@link CsvParserConfiguration} class test.
     */
    @Test
    public void validateTest() {
        CsvParserConfiguration csvParserConfiguration01 = new CsvParserConfiguration();
        try {
            csvParserConfiguration01.validate();
            Assertions.fail("CsvParserConfiguration test fail");
        } catch (WrongColumnSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No column separator is specified.");
        }

        CsvParserConfiguration csvParserConfiguration02 = new CsvParserConfiguration();
        csvParserConfiguration02.setCommaSeparator(true);
        csvParserConfiguration02.setSemicolonSeparator(true);
        try {
            csvParserConfiguration02.validate();
            Assertions.fail("CsvParserConfiguration test fail");
        } catch (WrongRowSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No row separator is specified.");
        }

        CsvParserConfiguration csvParserConfiguration03 = new CsvParserConfiguration();
        csvParserConfiguration03.setCrSeparator(true);
        csvParserConfiguration03.setLfSeparator(true);
        csvParserConfiguration03.setCrLfSeparator(true);
        try {
            csvParserConfiguration03.validate();
            Assertions.fail("CsvParserConfiguration test fail");
        } catch (WrongColumnSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No column separator is specified.");
        }

        CsvParserConfiguration csvParserConfiguration04 = new CsvParserConfiguration();
        csvParserConfiguration04.setCommaSeparator(true);
        csvParserConfiguration04.setSemicolonSeparator(true);
        csvParserConfiguration04.setCrSeparator(true);
        csvParserConfiguration04.setLfSeparator(true);
        csvParserConfiguration04.setCrLfSeparator(true);
        csvParserConfiguration04.validate();

        CsvParserConfiguration csvParserConfiguration05 = new CsvParserConfiguration();
        csvParserConfiguration05.setCommaSeparator(true);
        csvParserConfiguration05.setSemicolonSeparator(false);
        csvParserConfiguration05.setCrSeparator(true);
        csvParserConfiguration05.setLfSeparator(true);
        csvParserConfiguration05.setCrLfSeparator(true);
        csvParserConfiguration05.validate();

        CsvParserConfiguration csvParserConfiguration06 = new CsvParserConfiguration();
        csvParserConfiguration06.setCommaSeparator(false);
        csvParserConfiguration06.setSemicolonSeparator(true);
        csvParserConfiguration06.setCrSeparator(true);
        csvParserConfiguration06.setLfSeparator(true);
        csvParserConfiguration06.setCrLfSeparator(true);
        csvParserConfiguration06.validate();

        CsvParserConfiguration csvParserConfiguration07 = new CsvParserConfiguration();
        csvParserConfiguration07.setCommaSeparator(false);
        csvParserConfiguration07.setSemicolonSeparator(false);
        csvParserConfiguration07.setCrSeparator(true);
        csvParserConfiguration07.setLfSeparator(true);
        csvParserConfiguration07.setCrLfSeparator(true);
        try {
            csvParserConfiguration07.validate();
            Assertions.fail("CsvParserConfiguration test fail");
        } catch (WrongColumnSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No column separator is specified.");
        }

        CsvParserConfiguration csvParserConfiguration08 = new CsvParserConfiguration();
        csvParserConfiguration08.setCommaSeparator(true);
        csvParserConfiguration08.setSemicolonSeparator(true);
        csvParserConfiguration08.setCrSeparator(true);
        csvParserConfiguration08.setLfSeparator(true);
        csvParserConfiguration08.setCrLfSeparator(false);
        csvParserConfiguration08.validate();

        CsvParserConfiguration csvParserConfiguration09 = new CsvParserConfiguration();
        csvParserConfiguration09.setCommaSeparator(true);
        csvParserConfiguration09.setSemicolonSeparator(true);
        csvParserConfiguration09.setCrSeparator(true);
        csvParserConfiguration09.setLfSeparator(false);
        csvParserConfiguration09.setCrLfSeparator(true);
        csvParserConfiguration09.validate();

        CsvParserConfiguration csvParserConfiguration10 = new CsvParserConfiguration();
        csvParserConfiguration10.setCommaSeparator(true);
        csvParserConfiguration10.setSemicolonSeparator(true);
        csvParserConfiguration10.setCrSeparator(false);
        csvParserConfiguration10.setLfSeparator(true);
        csvParserConfiguration10.setCrLfSeparator(true);
        csvParserConfiguration10.validate();

        CsvParserConfiguration csvParserConfiguration11 = new CsvParserConfiguration();
        csvParserConfiguration11.setCommaSeparator(true);
        csvParserConfiguration11.setSemicolonSeparator(true);
        csvParserConfiguration11.setCrSeparator(true);
        csvParserConfiguration11.setLfSeparator(false);
        csvParserConfiguration11.setCrLfSeparator(false);
        csvParserConfiguration11.validate();

        CsvParserConfiguration csvParserConfiguration12 = new CsvParserConfiguration();
        csvParserConfiguration12.setCommaSeparator(true);
        csvParserConfiguration12.setSemicolonSeparator(true);
        csvParserConfiguration12.setCrSeparator(false);
        csvParserConfiguration12.setLfSeparator(true);
        csvParserConfiguration12.setCrLfSeparator(false);
        csvParserConfiguration12.validate();

        CsvParserConfiguration csvParserConfiguration13 = new CsvParserConfiguration();
        csvParserConfiguration13.setCommaSeparator(true);
        csvParserConfiguration13.setSemicolonSeparator(true);
        csvParserConfiguration13.setCrSeparator(false);
        csvParserConfiguration13.setLfSeparator(false);
        csvParserConfiguration13.setCrLfSeparator(true);
        csvParserConfiguration13.validate();

        CsvParserConfiguration csvParserConfiguration14 = new CsvParserConfiguration();
        csvParserConfiguration14.setCommaSeparator(true);
        csvParserConfiguration14.setSemicolonSeparator(true);
        csvParserConfiguration14.setCrSeparator(false);
        csvParserConfiguration14.setLfSeparator(false);
        csvParserConfiguration14.setCrLfSeparator(false);
        try {
            csvParserConfiguration14.validate();
            Assertions.fail("CsvParserConfiguration test fail");
        } catch (WrongRowSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No row separator is specified.");
        }
    }

    /**
     * {@link CsvParserConfiguration} class test.
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
        Assertions.assertThat(csvParserConfiguration.getMaxColumnLength()).isEqualTo(0);
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
