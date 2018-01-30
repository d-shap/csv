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
 * Tests for {@link CsvPrinterConfiguration}.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvPrinterConfigurationTest extends CsvTest {

    /**
     * Test class constructor.
     */
    public CsvPrinterConfigurationTest() {
        super();
    }

    /**
     * {@link CsvPrinterConfiguration} class test.
     */
    @Test
    public void defaultValuesTest() {
        CsvPrinterConfiguration csvPrinterConfiguration = new CsvPrinterConfiguration();
        Assertions.assertThat(csvPrinterConfiguration.getColumnSeparator()).isNull();
        Assertions.assertThat(csvPrinterConfiguration.isCommaSeparator()).isFalse();
        Assertions.assertThat(csvPrinterConfiguration.isSemicolonSeparator()).isFalse();
        Assertions.assertThat(csvPrinterConfiguration.getRowSeparator()).isNull();
        Assertions.assertThat(csvPrinterConfiguration.isCrSeparator()).isFalse();
        Assertions.assertThat(csvPrinterConfiguration.isLfSeparator()).isFalse();
        Assertions.assertThat(csvPrinterConfiguration.isCrLfSeparator()).isFalse();
        Assertions.assertThat(csvPrinterConfiguration.isColumnCountCheckEnabled()).isFalse();
        Assertions.assertThat(csvPrinterConfiguration.isSkipEmptyRowsEnabled()).isFalse();
        Assertions.assertThat(csvPrinterConfiguration.isEscapeAllSeparatorsEnabled()).isFalse();
    }

    /**
     * {@link CsvPrinterConfiguration} class test.
     */
    @Test
    public void copyOfTest() {
        CsvPrinterConfiguration csvPrinterConfiguration1 = new CsvPrinterConfiguration();
        csvPrinterConfiguration1.setCommaSeparator();
        csvPrinterConfiguration1.setCrSeparator();
        csvPrinterConfiguration1.setColumnCountCheckEnabled(true);
        csvPrinterConfiguration1.setSkipEmptyRowsEnabled(true);
        csvPrinterConfiguration1.setEscapeAllSeparatorsEnabled(true);
        CsvPrinterConfiguration csvPrinterConfigurationCopy1 = csvPrinterConfiguration1.copyOf();
        Assertions.assertThat(csvPrinterConfigurationCopy1.getColumnSeparator()).isEqualTo(",");
        Assertions.assertThat(csvPrinterConfigurationCopy1.isCommaSeparator()).isTrue();
        Assertions.assertThat(csvPrinterConfigurationCopy1.isSemicolonSeparator()).isFalse();
        Assertions.assertThat(csvPrinterConfigurationCopy1.getRowSeparator()).isEqualTo("\r");
        Assertions.assertThat(csvPrinterConfigurationCopy1.isCrSeparator()).isTrue();
        Assertions.assertThat(csvPrinterConfigurationCopy1.isLfSeparator()).isFalse();
        Assertions.assertThat(csvPrinterConfigurationCopy1.isCrLfSeparator()).isFalse();
        Assertions.assertThat(csvPrinterConfigurationCopy1.isColumnCountCheckEnabled()).isTrue();
        Assertions.assertThat(csvPrinterConfigurationCopy1.isSkipEmptyRowsEnabled()).isTrue();
        Assertions.assertThat(csvPrinterConfigurationCopy1.isEscapeAllSeparatorsEnabled()).isTrue();

        CsvPrinterConfiguration csvPrinterConfiguration2 = new CsvPrinterConfiguration();
        csvPrinterConfiguration2.setSemicolonSeparator();
        csvPrinterConfiguration2.setLfSeparator();
        csvPrinterConfiguration2.setColumnCountCheckEnabled(false);
        csvPrinterConfiguration2.setSkipEmptyRowsEnabled(false);
        csvPrinterConfiguration2.setEscapeAllSeparatorsEnabled(false);
        CsvPrinterConfiguration csvPrinterConfigurationCopy2 = csvPrinterConfiguration2.copyOf();
        Assertions.assertThat(csvPrinterConfigurationCopy2.getColumnSeparator()).isEqualTo(";");
        Assertions.assertThat(csvPrinterConfigurationCopy2.isCommaSeparator()).isFalse();
        Assertions.assertThat(csvPrinterConfigurationCopy2.isSemicolonSeparator()).isTrue();
        Assertions.assertThat(csvPrinterConfigurationCopy2.getRowSeparator()).isEqualTo("\n");
        Assertions.assertThat(csvPrinterConfigurationCopy2.isCrSeparator()).isFalse();
        Assertions.assertThat(csvPrinterConfigurationCopy2.isLfSeparator()).isTrue();
        Assertions.assertThat(csvPrinterConfigurationCopy2.isCrLfSeparator()).isFalse();
        Assertions.assertThat(csvPrinterConfigurationCopy2.isColumnCountCheckEnabled()).isFalse();
        Assertions.assertThat(csvPrinterConfigurationCopy2.isSkipEmptyRowsEnabled()).isFalse();
        Assertions.assertThat(csvPrinterConfigurationCopy2.isEscapeAllSeparatorsEnabled()).isFalse();
    }

    /**
     * {@link CsvPrinterConfiguration} class test.
     */
    @Test
    public void validateTest() {
        CsvPrinterConfiguration csvPrinterConfiguration01 = new CsvPrinterConfiguration();
        try {
            csvPrinterConfiguration01.validate();
            Assertions.fail("CsvPrinterConfiguration test fail");
        } catch (WrongColumnSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No column separator is specified");
        }

        CsvPrinterConfiguration csvPrinterConfiguration02 = new CsvPrinterConfiguration();
        csvPrinterConfiguration02.setCommaSeparator();
        try {
            csvPrinterConfiguration02.validate();
            Assertions.fail("CsvPrinterConfiguration test fail");
        } catch (WrongRowSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No row separator is specified");
        }

        CsvPrinterConfiguration csvPrinterConfiguration03 = new CsvPrinterConfiguration();
        csvPrinterConfiguration03.setCrSeparator();
        try {
            csvPrinterConfiguration03.validate();
            Assertions.fail("CsvPrinterConfiguration test fail");
        } catch (WrongColumnSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No column separator is specified");
        }

        CsvPrinterConfiguration csvPrinterConfiguration04 = new CsvPrinterConfiguration();
        csvPrinterConfiguration04.setCommaSeparator();
        csvPrinterConfiguration04.setCrSeparator();
        csvPrinterConfiguration04.validate();

        CsvPrinterConfiguration csvPrinterConfiguration05 = new CsvPrinterConfiguration();
        csvPrinterConfiguration05.setSemicolonSeparator();
        csvPrinterConfiguration05.setCrSeparator();
        csvPrinterConfiguration05.validate();

        CsvPrinterConfiguration csvPrinterConfiguration06 = new CsvPrinterConfiguration();
        csvPrinterConfiguration06.setCommaSeparator();
        csvPrinterConfiguration06.setLfSeparator();
        csvPrinterConfiguration06.validate();

        CsvPrinterConfiguration csvPrinterConfiguration07 = new CsvPrinterConfiguration();
        csvPrinterConfiguration07.setSemicolonSeparator();
        csvPrinterConfiguration07.setLfSeparator();
        csvPrinterConfiguration07.validate();

        CsvPrinterConfiguration csvPrinterConfiguration08 = new CsvPrinterConfiguration();
        csvPrinterConfiguration08.setCommaSeparator();
        csvPrinterConfiguration08.setCrLfSeparator();
        csvPrinterConfiguration08.validate();

        CsvPrinterConfiguration csvPrinterConfiguration09 = new CsvPrinterConfiguration();
        csvPrinterConfiguration09.setSemicolonSeparator();
        csvPrinterConfiguration09.setCrLfSeparator();
        csvPrinterConfiguration09.validate();
    }

    /**
     * {@link CsvPrinterConfiguration} class test.
     */
    @Test
    public void commaSeparatorTest() {
        CsvPrinterConfiguration csvPrinterConfiguration = new CsvPrinterConfiguration();
        Assertions.assertThat(csvPrinterConfiguration.getColumnSeparator()).isNull();
        Assertions.assertThat(csvPrinterConfiguration.isCommaSeparator()).isFalse();
        csvPrinterConfiguration.setCommaSeparator();
        Assertions.assertThat(csvPrinterConfiguration.getColumnSeparator()).isEqualTo(",");
        Assertions.assertThat(csvPrinterConfiguration.isCommaSeparator()).isTrue();
        csvPrinterConfiguration.setSemicolonSeparator();
        Assertions.assertThat(csvPrinterConfiguration.getColumnSeparator()).isEqualTo(";");
        Assertions.assertThat(csvPrinterConfiguration.isCommaSeparator()).isFalse();
    }

    /**
     * {@link CsvPrinterConfiguration} class test.
     */
    @Test
    public void semicolonSeparatorTest() {
        CsvPrinterConfiguration csvPrinterConfiguration = new CsvPrinterConfiguration();
        Assertions.assertThat(csvPrinterConfiguration.getColumnSeparator()).isNull();
        Assertions.assertThat(csvPrinterConfiguration.isSemicolonSeparator()).isFalse();
        csvPrinterConfiguration.setSemicolonSeparator();
        Assertions.assertThat(csvPrinterConfiguration.getColumnSeparator()).isEqualTo(";");
        Assertions.assertThat(csvPrinterConfiguration.isSemicolonSeparator()).isTrue();
        csvPrinterConfiguration.setCommaSeparator();
        Assertions.assertThat(csvPrinterConfiguration.getColumnSeparator()).isEqualTo(",");
        Assertions.assertThat(csvPrinterConfiguration.isSemicolonSeparator()).isFalse();
    }

    /**
     * {@link CsvPrinterConfiguration} class test.
     */
    @Test
    public void crSeparatorTest() {
        CsvPrinterConfiguration csvPrinterConfiguration = new CsvPrinterConfiguration();
        Assertions.assertThat(csvPrinterConfiguration.getRowSeparator()).isNull();
        Assertions.assertThat(csvPrinterConfiguration.isCrSeparator()).isFalse();
        csvPrinterConfiguration.setCrSeparator();
        Assertions.assertThat(csvPrinterConfiguration.getRowSeparator()).isEqualTo("\r");
        Assertions.assertThat(csvPrinterConfiguration.isCrSeparator()).isTrue();
        csvPrinterConfiguration.setLfSeparator();
        Assertions.assertThat(csvPrinterConfiguration.getRowSeparator()).isEqualTo("\n");
        Assertions.assertThat(csvPrinterConfiguration.isCrSeparator()).isFalse();
        csvPrinterConfiguration.setCrLfSeparator();
        Assertions.assertThat(csvPrinterConfiguration.getRowSeparator()).isEqualTo("\r\n");
        Assertions.assertThat(csvPrinterConfiguration.isCrSeparator()).isFalse();
    }

    /**
     * {@link CsvPrinterConfiguration} class test.
     */
    @Test
    public void lfSeparatorTest() {
        CsvPrinterConfiguration csvPrinterConfiguration = new CsvPrinterConfiguration();
        Assertions.assertThat(csvPrinterConfiguration.getRowSeparator()).isNull();
        Assertions.assertThat(csvPrinterConfiguration.isLfSeparator()).isFalse();
        csvPrinterConfiguration.setLfSeparator();
        Assertions.assertThat(csvPrinterConfiguration.getRowSeparator()).isEqualTo("\n");
        Assertions.assertThat(csvPrinterConfiguration.isLfSeparator()).isTrue();
        csvPrinterConfiguration.setCrSeparator();
        Assertions.assertThat(csvPrinterConfiguration.getRowSeparator()).isEqualTo("\r");
        Assertions.assertThat(csvPrinterConfiguration.isLfSeparator()).isFalse();
        csvPrinterConfiguration.setCrLfSeparator();
        Assertions.assertThat(csvPrinterConfiguration.getRowSeparator()).isEqualTo("\r\n");
        Assertions.assertThat(csvPrinterConfiguration.isLfSeparator()).isFalse();
    }

    /**
     * {@link CsvPrinterConfiguration} class test.
     */
    @Test
    public void crLfSeparatorTest() {
        CsvPrinterConfiguration csvPrinterConfiguration = new CsvPrinterConfiguration();
        Assertions.assertThat(csvPrinterConfiguration.getRowSeparator()).isNull();
        Assertions.assertThat(csvPrinterConfiguration.isCrLfSeparator()).isFalse();
        csvPrinterConfiguration.setCrLfSeparator();
        Assertions.assertThat(csvPrinterConfiguration.getRowSeparator()).isEqualTo("\r\n");
        Assertions.assertThat(csvPrinterConfiguration.isCrLfSeparator()).isTrue();
        csvPrinterConfiguration.setCrSeparator();
        Assertions.assertThat(csvPrinterConfiguration.getRowSeparator()).isEqualTo("\r");
        Assertions.assertThat(csvPrinterConfiguration.isCrLfSeparator()).isFalse();
        csvPrinterConfiguration.setLfSeparator();
        Assertions.assertThat(csvPrinterConfiguration.getRowSeparator()).isEqualTo("\n");
        Assertions.assertThat(csvPrinterConfiguration.isCrLfSeparator()).isFalse();
    }

    /**
     * {@link CsvPrinterConfiguration} class test.
     */
    @Test
    public void columnCountCheckEnabledTest() {
        CsvPrinterConfiguration csvPrinterConfiguration = new CsvPrinterConfiguration();
        Assertions.assertThat(csvPrinterConfiguration.isColumnCountCheckEnabled()).isFalse();
        csvPrinterConfiguration.setColumnCountCheckEnabled(false);
        Assertions.assertThat(csvPrinterConfiguration.isColumnCountCheckEnabled()).isFalse();
        csvPrinterConfiguration.setColumnCountCheckEnabled(true);
        Assertions.assertThat(csvPrinterConfiguration.isColumnCountCheckEnabled()).isTrue();
    }

    /**
     * {@link CsvPrinterConfiguration} class test.
     */
    @Test
    public void skipEmptyRowsEnabledTest() {
        CsvPrinterConfiguration csvPrinterConfiguration = new CsvPrinterConfiguration();
        Assertions.assertThat(csvPrinterConfiguration.isSkipEmptyRowsEnabled()).isFalse();
        csvPrinterConfiguration.setSkipEmptyRowsEnabled(false);
        Assertions.assertThat(csvPrinterConfiguration.isSkipEmptyRowsEnabled()).isFalse();
        csvPrinterConfiguration.setSkipEmptyRowsEnabled(true);
        Assertions.assertThat(csvPrinterConfiguration.isSkipEmptyRowsEnabled()).isTrue();
    }

    /**
     * {@link CsvPrinterConfiguration} class test.
     */
    @Test
    public void escapeAllSeparatorsEnabledTest() {
        CsvPrinterConfiguration csvPrinterConfiguration = new CsvPrinterConfiguration();
        Assertions.assertThat(csvPrinterConfiguration.isEscapeAllSeparatorsEnabled()).isFalse();
        csvPrinterConfiguration.setEscapeAllSeparatorsEnabled(false);
        Assertions.assertThat(csvPrinterConfiguration.isEscapeAllSeparatorsEnabled()).isFalse();
        csvPrinterConfiguration.setEscapeAllSeparatorsEnabled(true);
        Assertions.assertThat(csvPrinterConfiguration.isEscapeAllSeparatorsEnabled()).isTrue();
    }

    /**
     * {@link CsvPrinterConfiguration} class test.
     */
    @Test
    public void hasSpecialCharactersTest() {
        CsvPrinterConfiguration csvPrinterConfiguration01 = new CsvPrinterConfiguration();
        Assertions.assertThat(csvPrinterConfiguration01.hasSpecialCharacters("value")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration01.hasSpecialCharacters(" , ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration01.hasSpecialCharacters(" ; ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration01.hasSpecialCharacters(" \r ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration01.hasSpecialCharacters(" \n ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration01.hasSpecialCharacters(" \r\n ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration01.hasSpecialCharacters(" \" ")).isTrue();

        CsvPrinterConfiguration csvPrinterConfiguration02 = new CsvPrinterConfiguration();
        csvPrinterConfiguration02.setCommaSeparator();
        Assertions.assertThat(csvPrinterConfiguration02.hasSpecialCharacters("value")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration02.hasSpecialCharacters(" , ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration02.hasSpecialCharacters(" ; ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration02.hasSpecialCharacters(" \r ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration02.hasSpecialCharacters(" \n ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration02.hasSpecialCharacters(" \r\n ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration02.hasSpecialCharacters(" \" ")).isTrue();

        CsvPrinterConfiguration csvPrinterConfiguration03 = new CsvPrinterConfiguration();
        csvPrinterConfiguration03.setSemicolonSeparator();
        Assertions.assertThat(csvPrinterConfiguration03.hasSpecialCharacters("value")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration03.hasSpecialCharacters(" , ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration03.hasSpecialCharacters(" ; ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration03.hasSpecialCharacters(" \r ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration03.hasSpecialCharacters(" \n ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration03.hasSpecialCharacters(" \r\n ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration03.hasSpecialCharacters(" \" ")).isTrue();

        CsvPrinterConfiguration csvPrinterConfiguration04 = new CsvPrinterConfiguration();
        csvPrinterConfiguration04.setCrSeparator();
        Assertions.assertThat(csvPrinterConfiguration04.hasSpecialCharacters("value")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration04.hasSpecialCharacters(" , ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration04.hasSpecialCharacters(" ; ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration04.hasSpecialCharacters(" \r ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration04.hasSpecialCharacters(" \n ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration04.hasSpecialCharacters(" \r\n ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration04.hasSpecialCharacters(" \" ")).isTrue();

        CsvPrinterConfiguration csvPrinterConfiguration05 = new CsvPrinterConfiguration();
        csvPrinterConfiguration05.setLfSeparator();
        Assertions.assertThat(csvPrinterConfiguration05.hasSpecialCharacters("value")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration05.hasSpecialCharacters(" , ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration05.hasSpecialCharacters(" ; ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration05.hasSpecialCharacters(" \r ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration05.hasSpecialCharacters(" \n ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration05.hasSpecialCharacters(" \r\n ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration05.hasSpecialCharacters(" \" ")).isTrue();

        CsvPrinterConfiguration csvPrinterConfiguration06 = new CsvPrinterConfiguration();
        csvPrinterConfiguration06.setCrLfSeparator();
        Assertions.assertThat(csvPrinterConfiguration06.hasSpecialCharacters("value")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration06.hasSpecialCharacters(" , ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration06.hasSpecialCharacters(" ; ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration06.hasSpecialCharacters(" \r ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration06.hasSpecialCharacters(" \n ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration06.hasSpecialCharacters(" \r\n ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration06.hasSpecialCharacters(" \" ")).isTrue();

        CsvPrinterConfiguration csvPrinterConfiguration07 = new CsvPrinterConfiguration();
        csvPrinterConfiguration07.setCommaSeparator();
        csvPrinterConfiguration07.setCrSeparator();
        Assertions.assertThat(csvPrinterConfiguration07.hasSpecialCharacters("value")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration07.hasSpecialCharacters(" , ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration07.hasSpecialCharacters(" ; ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration07.hasSpecialCharacters(" \r ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration07.hasSpecialCharacters(" \n ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration07.hasSpecialCharacters(" \r\n ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration07.hasSpecialCharacters(" \" ")).isTrue();

        CsvPrinterConfiguration csvPrinterConfiguration08 = new CsvPrinterConfiguration();
        csvPrinterConfiguration08.setSemicolonSeparator();
        csvPrinterConfiguration08.setCrSeparator();
        Assertions.assertThat(csvPrinterConfiguration08.hasSpecialCharacters("value")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration08.hasSpecialCharacters(" , ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration08.hasSpecialCharacters(" ; ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration08.hasSpecialCharacters(" \r ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration08.hasSpecialCharacters(" \n ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration08.hasSpecialCharacters(" \r\n ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration08.hasSpecialCharacters(" \" ")).isTrue();

        CsvPrinterConfiguration csvPrinterConfiguration09 = new CsvPrinterConfiguration();
        csvPrinterConfiguration09.setCommaSeparator();
        csvPrinterConfiguration09.setLfSeparator();
        Assertions.assertThat(csvPrinterConfiguration09.hasSpecialCharacters("value")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration09.hasSpecialCharacters(" , ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration09.hasSpecialCharacters(" ; ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration09.hasSpecialCharacters(" \r ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration09.hasSpecialCharacters(" \n ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration09.hasSpecialCharacters(" \r\n ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration09.hasSpecialCharacters(" \" ")).isTrue();

        CsvPrinterConfiguration csvPrinterConfiguration10 = new CsvPrinterConfiguration();
        csvPrinterConfiguration10.setSemicolonSeparator();
        csvPrinterConfiguration10.setLfSeparator();
        Assertions.assertThat(csvPrinterConfiguration10.hasSpecialCharacters("value")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration10.hasSpecialCharacters(" , ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration10.hasSpecialCharacters(" ; ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration10.hasSpecialCharacters(" \r ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration10.hasSpecialCharacters(" \n ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration10.hasSpecialCharacters(" \r\n ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration10.hasSpecialCharacters(" \" ")).isTrue();

        CsvPrinterConfiguration csvPrinterConfiguration11 = new CsvPrinterConfiguration();
        csvPrinterConfiguration11.setCommaSeparator();
        csvPrinterConfiguration11.setCrLfSeparator();
        Assertions.assertThat(csvPrinterConfiguration11.hasSpecialCharacters("value")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration11.hasSpecialCharacters(" , ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration11.hasSpecialCharacters(" ; ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration11.hasSpecialCharacters(" \r ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration11.hasSpecialCharacters(" \n ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration11.hasSpecialCharacters(" \r\n ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration11.hasSpecialCharacters(" \" ")).isTrue();

        CsvPrinterConfiguration csvPrinterConfiguration12 = new CsvPrinterConfiguration();
        csvPrinterConfiguration12.setSemicolonSeparator();
        csvPrinterConfiguration12.setCrLfSeparator();
        Assertions.assertThat(csvPrinterConfiguration12.hasSpecialCharacters("value")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration12.hasSpecialCharacters(" , ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration12.hasSpecialCharacters(" ; ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration12.hasSpecialCharacters(" \r ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration12.hasSpecialCharacters(" \n ")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration12.hasSpecialCharacters(" \r\n ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration12.hasSpecialCharacters(" \" ")).isTrue();

        CsvPrinterConfiguration csvPrinterConfiguration13 = new CsvPrinterConfiguration();
        csvPrinterConfiguration13.setEscapeAllSeparatorsEnabled(true);
        Assertions.assertThat(csvPrinterConfiguration13.hasSpecialCharacters("value")).isFalse();
        Assertions.assertThat(csvPrinterConfiguration13.hasSpecialCharacters(" , ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration13.hasSpecialCharacters(" ; ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration13.hasSpecialCharacters(" \r ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration13.hasSpecialCharacters(" \n ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration13.hasSpecialCharacters(" \r\n ")).isTrue();
        Assertions.assertThat(csvPrinterConfiguration13.hasSpecialCharacters(" \" ")).isTrue();
    }

}
