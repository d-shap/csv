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

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;

import ru.d_shap.assertions.Assertions;
import ru.d_shap.csv.handler.ColumnCountEventHandler;
import ru.d_shap.csv.handler.ColumnLengthEventHandler;
import ru.d_shap.csv.handler.DimensionEventHandler;
import ru.d_shap.csv.handler.NoopEventHandler;
import ru.d_shap.csv.handler.RestrictedListEventHandler;

/**
 * Tests for {@link CsvParser}.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvParserTest extends CsvTest {

    /**
     * Test class constructor.
     */
    public CsvParserTest() {
        super();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void validateTest() {
        try {
            CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
            new CsvParser(csvParserConfiguration);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No column separator is specified.");
        }
        try {
            CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
            csvParserConfiguration.setCommaSeparator(true);
            new CsvParser(csvParserConfiguration);
            Assertions.fail("CsvParser test fail");
        } catch (WrongRowSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No row separator is specified.");
        }
        try {
            CsvParserConfiguration csvParserConfiguration = new CsvParserConfiguration();
            csvParserConfiguration.setCrLfSeparator(true);
            new CsvParser(csvParserConfiguration);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnSeparatorException ex) {
            Assertions.assertThat(ex).hasMessage("No column separator is specified.");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithSemicolonSeparatorTest() {
        String csv = "true;1.0.1;5";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("true", "1.0.1", "5");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithCommaSeparatorTest() {
        String csv = "true,1.0.1,5";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("true", "1.0.1", "5");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithEmptyValuesTest() {
        String csv = ";a;;b;;;c;";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("", "a", "", "b", "", "", "c", "");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithSpecialCharactersTest() {
        String csv = "true,\"aa,bb;\r\na\",\"5\"";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("true", "aa,bb;\r\na", "5");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithEmptySpecialCharactersTest() {
        String csv = "\"\",\"\",,\"\"";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("", "", "", "");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithQuotsTest() {
        String csv = "\"a\"\"b\",ab;\"\"\"a\",\"b\"\"\"";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("a\"b", "ab", "\"a", "b\"");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithNotClosedQuotTest() {
        try {
            String csv = "aaa,bb,\"ccc,dd";
            createCsvParser(true, true, true, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("End of input obtained. Last characters: \"aaa,bb,\"ccc,dd\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithNotOpenedQuotTest() {
        try {
            String csv = "aaa,bb,ccc\",dd";
            createCsvParser(true, true, true, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: '\"' (34). Last characters: \"aaa,bb,ccc\"\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithOpenQuotInTheMiddleTest() {
        try {
            String csv = "aaa,bb,c\"c,dd";
            createCsvParser(true, true, true, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: '\"' (34). Last characters: \"aaa,bb,c\"\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithCloseQuotInTheMiddleTest() {
        try {
            String csv = "aaa,bb,\"ccc\"cc,dd";
            createCsvParser(true, true, true, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: 'c' (99). Last characters: \"aaa,bb,\"ccc\"c\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseColumnsWithUnquotedQuotTest() {
        try {
            String csv = "one;\"t\"wo\"\nthree;four";
            createCsvParser(true, true, true, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (CsvParseException ex) {
            Assertions.assertThat(ex).hasMessage("Wrong character obtained: 'w' (119). Last characters: \"one;\"t\"w\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseRowsWithCrSeparatorTest() {
        String csv = "a\rb\rc";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("b");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("c");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseRowsWithLfSeparatorTest() {
        String csv = "a\nb\nc";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("b");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("c");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseRowsWithCrLfSeparatorTest() {
        String csv = "a\r\nb\r\nc";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("a");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("b");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("c");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseRowsWithEmptyValuesTest() {
        String csv = "\naaa\n\nbbb\n\n";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder();
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("aaa");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder();
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("bbb");
        Assertions.assertThat(result.get(4)).containsExactlyInOrder();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseRowsWithSpecialCharactersTest() {
        String csv = "\",\"\r\n\"\"\"\"\r\n\"aaa;bbb\"\r\n";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder(",");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("\"");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("aaa;bbb");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithEmptyStringTest() {
        String csv = "";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEmpty();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = NullPointerException.class)
    public void parseCsvWithNullStringTest() {
        createCsvParser(true, true, true, true, true).parse((CharSequence) null);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(true, false, true, false, false).parse(csv);
        Assertions.assertThat(result).hasSize(6);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4;5;6\n\n7", "8;9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("\n");
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("\na", "b", "c", "d");
        Assertions.assertThat(result.get(5)).containsExactlyInOrder("\n");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(true, false, true, false, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(true, false, true, false, false).parse(csv);
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4;5;6\n\n7", "8;9");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("\n");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("\na", "b", "c", "d");
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("\n");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(true, false, true, false, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n7,8;9\\r\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(false, true, true, false, false).parse(csv);
        Assertions.assertThat(result).hasSize(6);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4", "5", "6\n\n7,8", "9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("\n");
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("\na,b,c,d");
        Assertions.assertThat(result.get(5)).containsExactlyInOrder("\n");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(false, true, true, false, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(false, true, true, false, false).parse(csv);
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5", "6\n\n7,8", "9");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("\n");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("\na,b,c,d");
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("\n");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(false, true, true, false, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(true, true, true, false, false).parse(csv);
        Assertions.assertThat(result).hasSize(6);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4", "5", "6\n\n7", "8", "9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("\n");
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("\na", "b", "c", "d");
        Assertions.assertThat(result.get(5)).containsExactlyInOrder("\n");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(true, true, true, false, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(true, true, true, false, false).parse(csv);
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5", "6\n\n7", "8", "9");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("\n");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("\na", "b", "c", "d");
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("\n");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(true, true, true, false, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n7,8;\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(true, false, false, true, false).parse(csv);
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\r\r4;5;6");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("7", "8;9\r");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("\r");
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("a", "b", "c", "d\r");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(true, false, false, true, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(true, false, false, true, false).parse(csv);
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\r\r4;5;6");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("7", "8;9\r");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("\r");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("a", "b", "c", "d\r");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(true, false, false, true, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n7,8;9\\r\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(false, true, false, true, false).parse(csv);
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3\r\r4", "5", "6");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("7,8", "9\r");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("\r");
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("a,b,c,d\r");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(false, true, false, true, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(false, true, false, true, false).parse(csv);
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3\r\r4", "5", "6");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("7,8", "9\r");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("\r");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("a,b,c,d\r");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(false, true, false, true, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n7,8;9\\r\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(true, true, false, true, false).parse(csv);
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\r\r4", "5", "6");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("7", "8", "9\r");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("\r");
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("a", "b", "c", "d\r");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(true, true, false, true, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(true, true, false, true, false).parse(csv);
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\r\r4", "5", "6");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("7", "8", "9\r");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("\r");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("a", "b", "c", "d\r");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(true, true, false, true, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n7,8;9\\r\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(true, false, false, false, true).parse(csv);
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\r\r4;5;6\n\n7", "8;9");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(true, false, false, false, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n7,8;9\\r\\n\\r\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(true, false, false, false, true).parse(csv);
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\r\r4;5;6\n\n7", "8;9");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithColumnCountCheckAndSkipEmptyRows(true, false, false, false, true).parse(csv);
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\r\r4;5;6\n\n7", "8;9");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(false, true, false, false, true).parse(csv);
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3\r\r4", "5", "6\n\n7,8", "9");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("a,b,c,d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(false, true, false, false, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n7,8;9\\r\\n\\r\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(false, true, false, false, true).parse(csv);
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3\r\r4", "5", "6\n\n7,8", "9");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("a,b,c,d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(false, true, false, false, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"4;5;6\\n\\n7,8;9\\r\\n\\r\\na,b,c,d\\r\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(true, true, false, false, true).parse(csv);
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\r\r4", "5", "6\n\n7", "8", "9");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(true, true, false, false, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n7,8;9\\r\\n\\r\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(true, true, false, false, true).parse(csv);
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\r\r4", "5", "6\n\n7", "8", "9");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(true, true, false, false, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"4;5;6\\n\\n7,8;9\\r\\n\\r\\na,b,c,d\\r\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrAndLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(true, false, true, true, false).parse(csv);
        Assertions.assertThat(result).hasSize(10);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4;5;6");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("7", "8;9");
        Assertions.assertThat(result.get(5)).containsExactlyInOrder();
        Assertions.assertThat(result.get(6)).containsExactlyInOrder();
        Assertions.assertThat(result.get(7)).containsExactlyInOrder();
        Assertions.assertThat(result.get(8)).containsExactlyInOrder("a", "b", "c", "d");
        Assertions.assertThat(result.get(9)).containsExactlyInOrder();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrAndLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(true, false, true, true, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrAndLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(true, false, true, true, false).parse(csv);
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4;5;6");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("7", "8;9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrAndLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(true, false, true, true, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrAndLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(false, true, true, true, false).parse(csv);
        Assertions.assertThat(result).hasSize(10);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4", "5", "6");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("7,8", "9");
        Assertions.assertThat(result.get(5)).containsExactlyInOrder();
        Assertions.assertThat(result.get(6)).containsExactlyInOrder();
        Assertions.assertThat(result.get(7)).containsExactlyInOrder();
        Assertions.assertThat(result.get(8)).containsExactlyInOrder("a,b,c,d");
        Assertions.assertThat(result.get(9)).containsExactlyInOrder();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrAndLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(false, true, true, true, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrAndLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(false, true, true, true, false).parse(csv);
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5", "6");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("7,8", "9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("a,b,c,d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrAndLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(false, true, true, true, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrAndLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(true, true, true, true, false).parse(csv);
        Assertions.assertThat(result).hasSize(10);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4", "5", "6");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("7", "8", "9");
        Assertions.assertThat(result.get(5)).containsExactlyInOrder();
        Assertions.assertThat(result.get(6)).containsExactlyInOrder();
        Assertions.assertThat(result.get(7)).containsExactlyInOrder();
        Assertions.assertThat(result.get(8)).containsExactlyInOrder("a", "b", "c", "d");
        Assertions.assertThat(result.get(9)).containsExactlyInOrder();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrAndLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(true, true, true, true, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrAndLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(true, true, true, true, false).parse(csv);
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5", "6");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("7", "8", "9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrAndLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(true, true, true, true, false).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"\\r4;5;6\\n\\n7,8;9\\r\\n\\r\\na,b,c,d\\r\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrAndCrLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(true, false, true, false, true).parse(csv);
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4;5;6\n\n7", "8;9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrAndCrLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(true, false, true, false, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrAndCrLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(true, false, true, false, true).parse(csv);
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4;5;6\n\n7", "8;9");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrAndCrLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(true, false, true, false, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n7,8;9\\r\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrAndCrLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(false, true, true, false, true).parse(csv);
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4", "5", "6\n\n7,8", "9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("a,b,c,d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrAndCrLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(false, true, true, false, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrAndCrLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(false, true, true, false, true).parse(csv);
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5", "6\n\n7,8", "9");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("a,b,c,d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrAndCrLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(false, true, true, false, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrAndCrLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(true, true, true, false, true).parse(csv);
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4", "5", "6\n\n7", "8", "9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrAndCrLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(true, true, true, false, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrAndCrLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(true, true, true, false, true).parse(csv);
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5", "6\n\n7", "8", "9");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrAndCrLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(true, true, true, false, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n7,8;\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndLfAndCrLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(true, false, false, true, true).parse(csv);
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\r\r4;5;6");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("7", "8;9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndLfAndCrLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(true, false, false, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndLfAndCrLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(true, false, false, true, true).parse(csv);
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\r\r4;5;6");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("7", "8;9");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndLfAndCrLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(true, false, false, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n7,8;9\\r\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndLfAndCrLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(false, true, false, true, true).parse(csv);
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3\r\r4", "5", "6");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("7,8", "9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("a,b,c,d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndLfAndCrLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(false, true, false, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndLfAndCrLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(false, true, false, true, true).parse(csv);
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3\r\r4", "5", "6");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("7,8", "9");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("a,b,c,d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndLfAndCrLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(false, true, false, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n7,8;9\\r\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndLfAndCrLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(true, true, false, true, true).parse(csv);
        Assertions.assertThat(result).hasSize(5);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\r\r4", "5", "6");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("7", "8", "9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndLfAndCrLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(true, true, false, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndLfAndCrLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(true, true, false, true, true).parse(csv);
        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\r\r4", "5", "6");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("7", "8", "9");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndLfAndCrLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(true, true, false, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\\n7,8;9\\r\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrAndLfAndCrLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(true, false, true, true, true).parse(csv);
        Assertions.assertThat(result).hasSize(7);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4;5;6");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("7", "8;9");
        Assertions.assertThat(result.get(5)).containsExactlyInOrder();
        Assertions.assertThat(result.get(6)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrAndLfAndCrLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(true, false, true, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrAndLfAndCrLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(true, false, true, true, true).parse(csv);
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4;5;6");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("7", "8;9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndCrAndLfAndCrLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(true, false, true, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;6\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrAndLfAndCrLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(false, true, true, true, true).parse(csv);
        Assertions.assertThat(result).hasSize(7);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4", "5", "6");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("7,8", "9");
        Assertions.assertThat(result.get(5)).containsExactlyInOrder();
        Assertions.assertThat(result.get(6)).containsExactlyInOrder("a,b,c,d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrAndLfAndCrLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(false, true, true, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrAndLfAndCrLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(false, true, true, true, true).parse(csv);
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1,2,3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5", "6");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("7,8", "9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("a,b,c,d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithSemicolonAndCrAndLfAndCrLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(false, true, true, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4;5;\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrAndLfAndCrLfSeparatorsNoColumnCountCheckNoSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).hasSize(7);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4", "5", "6");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder();
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("7", "8", "9");
        Assertions.assertThat(result.get(5)).containsExactlyInOrder();
        Assertions.assertThat(result.get(6)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrAndLfAndCrLfSeparatorsColumnCountCheckNoSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheck(true, true, true, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2,3\\r\\r4\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrAndLfAndCrLfSeparatorsNoColumnCountCheckSkipEmptyRowsTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        List<List<String>> result = createCsvParserWithSkipEmptyRows(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).hasSize(4);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder("4", "5", "6");
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("7", "8", "9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("a", "b", "c", "d");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithCommaAndSemicolonAndCrAndLfAndCrLfSeparatorsColumnCountCheckSkipEmptyRowsTest() {
        try {
            String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
            createCsvParserWithColumnCountCheckAndSkipEmptyRows(true, true, true, true, true).parse(csv);
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"4;5;6\\n\\n7,8;9\\r\\n\\r\\na,b,c,d\\r\\n\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithStringBuilderTest() {
        StringBuilder csv = new StringBuilder();
        csv.append("1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n");
        List<List<String>> result = createCsvParser(true, false, true, false, false).parse(csv);
        Assertions.assertThat(result).hasSize(6);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4;5;6\n\n7", "8;9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("\n");
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("\na", "b", "c", "d");
        Assertions.assertThat(result.get(5)).containsExactlyInOrder("\n");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithReaderTest() {
        String csv = "1,2,3\r\r4;5;6\n\n7,8;9\r\n\r\na,b,c,d\r\n";
        Reader reader = new StringReader(csv);
        List<List<String>> result = createCsvParser(true, false, true, false, false).parse(reader);
        Assertions.assertThat(result).hasSize(6);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3");
        Assertions.assertThat(result.get(1)).containsExactlyInOrder();
        Assertions.assertThat(result.get(2)).containsExactlyInOrder("4;5;6\n\n7", "8;9");
        Assertions.assertThat(result.get(3)).containsExactlyInOrder("\n");
        Assertions.assertThat(result.get(4)).containsExactlyInOrder("\na", "b", "c", "d");
        Assertions.assertThat(result.get(5)).containsExactlyInOrder("\n");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithEmptyReaderTest() {
        String csv = "";
        Reader reader = new StringReader(csv);
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEmpty();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = NullPointerException.class)
    public void parseCsvWithNullReaderTest() {
        createCsvParser(true, true, true, true, true).parse((Reader) null);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithErrorReaderTest() {
        try {
            ErrorOnReadReader reader = new ErrorOnReadReader();
            createCsvParser(true, true, true, true, true).parse(reader);
            Assertions.fail("CsvParser test fail");
        } catch (CsvIOException ex) {
            Assertions.assertThat(ex).hasMessage("ERROR");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithNoopEventHandlerTest() {
        CsvParserBuilder builder = CsvParserBuilder.getInstance();
        builder.setCommaSeparator(true);
        builder.setSemicolonSeparator(true);
        builder.setCrSeparator(true);
        builder.setLfSeparator(true);
        builder.setCrLfSeparator(true);

        String csv = "1,2\r\n3,4,5,abcdefgh\r\n";

        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).parse(csv, new NoopEventHandler());

        try {
            builder.setColumnCountCheckEnabled(true).setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).parse(csv, new NoopEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2\\r\\n3,4,5,\".");
        }

        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(5).setMaxColumnLengthCheckEnabled(false).parse(csv, new NoopEventHandler());

        try {
            builder.setColumnCountCheckEnabled(false).setMaxColumnLength(5).setMaxColumnLengthCheckEnabled(true).parse(csv, new NoopEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnLengthException ex) {
            Assertions.assertThat(ex).hasMessage("Maximum column value length exceeded. Last characters: \"1,2\\r\\n3,4,5,abcdef\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithDimensionEventHandlerTest() {
        CsvParserBuilder builder = CsvParserBuilder.getInstance();
        builder.setCommaSeparator(true);
        builder.setSemicolonSeparator(true);
        builder.setCrSeparator(true);
        builder.setLfSeparator(true);
        builder.setCrLfSeparator(true);

        String csv1 = "1,2\r\n3,4,5,abcdefgh\r\n";

        try {
            builder.setColumnCountCheckEnabled(false).setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).parse(csv1, new DimensionEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2\\r\\n3,4,5,\".");
        }

        try {
            builder.setColumnCountCheckEnabled(true).setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).parse(csv1, new DimensionEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2\\r\\n3,4,5,\".");
        }

        try {
            builder.setColumnCountCheckEnabled(false).setMaxColumnLength(5).setMaxColumnLengthCheckEnabled(false).parse(csv1, new DimensionEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2\\r\\n3,4,5,\".");
        }

        try {
            builder.setColumnCountCheckEnabled(false).setMaxColumnLength(5).setMaxColumnLengthCheckEnabled(true).parse(csv1, new DimensionEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2\\r\\n3,4,5,\".");
        }

        String csv2 = "1,2\r\n3,abcdefgh\r\n";

        DimensionEventHandler eventHandler1 = new DimensionEventHandler();
        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).parse(csv2, eventHandler1);
        Assertions.assertThat(eventHandler1.getRowCount()).isEqualTo(2);
        Assertions.assertThat(eventHandler1.getColumnCount()).isEqualTo(2);

        DimensionEventHandler eventHandler2 = new DimensionEventHandler();
        builder.setColumnCountCheckEnabled(true).setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).parse(csv2, eventHandler2);
        Assertions.assertThat(eventHandler2.getRowCount()).isEqualTo(2);
        Assertions.assertThat(eventHandler2.getColumnCount()).isEqualTo(2);

        DimensionEventHandler eventHandler3 = new DimensionEventHandler();
        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(5).setMaxColumnLengthCheckEnabled(false).parse(csv2, eventHandler3);
        Assertions.assertThat(eventHandler3.getRowCount()).isEqualTo(2);
        Assertions.assertThat(eventHandler3.getColumnCount()).isEqualTo(2);

        DimensionEventHandler eventHandler4 = new DimensionEventHandler();
        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(5).setMaxColumnLengthCheckEnabled(true).parse(csv2, eventHandler4);
        Assertions.assertThat(eventHandler4.getRowCount()).isEqualTo(2);
        Assertions.assertThat(eventHandler4.getColumnCount()).isEqualTo(2);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithColumnCountEventHandlerTest() {
        CsvParserBuilder builder = CsvParserBuilder.getInstance();
        builder.setCommaSeparator(true);
        builder.setSemicolonSeparator(true);
        builder.setCrSeparator(true);
        builder.setLfSeparator(true);
        builder.setCrLfSeparator(true);

        String csv = "1,2\r\n3,4,5,abcdefgh\r\n";

        ColumnCountEventHandler eventHandler1 = new ColumnCountEventHandler();
        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).parse(csv, eventHandler1);
        Assertions.assertThat(eventHandler1.getColumnCounts()).containsExactlyInOrder(2, 4);

        try {
            builder.setColumnCountCheckEnabled(true).setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).parse(csv, new ColumnCountEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2\\r\\n3,4,5,\".");
        }

        ColumnCountEventHandler eventHandler3 = new ColumnCountEventHandler();
        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(5).setMaxColumnLengthCheckEnabled(false).parse(csv, eventHandler3);
        Assertions.assertThat(eventHandler3.getColumnCounts()).containsExactlyInOrder(2, 4);

        ColumnCountEventHandler eventHandler4 = new ColumnCountEventHandler();
        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(5).setMaxColumnLengthCheckEnabled(true).parse(csv, eventHandler4);
        Assertions.assertThat(eventHandler4.getColumnCounts()).containsExactlyInOrder(2, 4);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithColumnLengthEventHandlerTest() {
        CsvParserBuilder builder = CsvParserBuilder.getInstance();
        builder.setCommaSeparator(true);
        builder.setSemicolonSeparator(true);
        builder.setCrSeparator(true);
        builder.setLfSeparator(true);
        builder.setCrLfSeparator(true);

        String csv = "1,2\r\n3,4,5,abcdefgh\r\n";

        ColumnLengthEventHandler eventHandler1 = new ColumnLengthEventHandler();
        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).parse(csv, eventHandler1);
        Assertions.assertThat(eventHandler1.getColumnLengths()).hasSize(2);
        Assertions.assertThat(eventHandler1.getColumnLengths().get(0)).containsExactlyInOrder(1, 1);
        Assertions.assertThat(eventHandler1.getColumnLengths().get(1)).containsExactlyInOrder(1, 1, 1, 8);

        try {
            builder.setColumnCountCheckEnabled(true).setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).parse(csv, new ColumnLengthEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2\\r\\n3,4,5,\".");
        }

        ColumnLengthEventHandler eventHandler3 = new ColumnLengthEventHandler();
        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(5).setMaxColumnLengthCheckEnabled(false).parse(csv, eventHandler3);
        Assertions.assertThat(eventHandler3.getColumnLengths()).hasSize(2);
        Assertions.assertThat(eventHandler3.getColumnLengths().get(0)).containsExactlyInOrder(1, 1);
        Assertions.assertThat(eventHandler3.getColumnLengths().get(1)).containsExactlyInOrder(1, 1, 1, 8);

        ColumnLengthEventHandler eventHandler4 = new ColumnLengthEventHandler();
        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(5).setMaxColumnLengthCheckEnabled(true).parse(csv, eventHandler4);
        Assertions.assertThat(eventHandler4.getColumnLengths()).hasSize(2);
        Assertions.assertThat(eventHandler4.getColumnLengths().get(0)).containsExactlyInOrder(1, 1);
        Assertions.assertThat(eventHandler4.getColumnLengths().get(1)).containsExactlyInOrder(1, 1, 1, 8);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithRestrictedListEventHandlerTest() {
        CsvParserBuilder builder = CsvParserBuilder.getInstance();
        builder.setCommaSeparator(true);
        builder.setSemicolonSeparator(true);
        builder.setCrSeparator(true);
        builder.setLfSeparator(true);
        builder.setCrLfSeparator(true);

        String csv = "1,2\r\n3,4,5,abcdefgh\r\n";

        RestrictedListEventHandler eventHandler1 = new RestrictedListEventHandler(5, "...");
        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).parse(csv, eventHandler1);
        Assertions.assertThat(eventHandler1.getCsv()).hasSize(2);
        Assertions.assertThat(eventHandler1.getCsv().get(0)).containsExactlyInOrder("1", "2");
        Assertions.assertThat(eventHandler1.getCsv().get(1)).containsExactlyInOrder("3", "4", "5", "ab...");

        try {
            builder.setColumnCountCheckEnabled(true).setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).parse(csv, new RestrictedListEventHandler(5, "..."));
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2\\r\\n3,4,5,\".");
        }

        RestrictedListEventHandler eventHandler3 = new RestrictedListEventHandler(5, "...");
        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(5).setMaxColumnLengthCheckEnabled(false).parse(csv, eventHandler3);
        Assertions.assertThat(eventHandler3.getCsv()).hasSize(2);
        Assertions.assertThat(eventHandler3.getCsv().get(0)).containsExactlyInOrder("1", "2");
        Assertions.assertThat(eventHandler3.getCsv().get(1)).containsExactlyInOrder("3", "4", "5", "ab...");

        RestrictedListEventHandler eventHandler4 = new RestrictedListEventHandler(5, "...");
        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(5).setMaxColumnLengthCheckEnabled(true).parse(csv, eventHandler4);
        Assertions.assertThat(eventHandler4.getCsv()).hasSize(2);
        Assertions.assertThat(eventHandler4.getCsv().get(0)).containsExactlyInOrder("1", "2");
        Assertions.assertThat(eventHandler4.getCsv().get(1)).containsExactlyInOrder("3", "4", "5", "ab...");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test(expected = NullPointerException.class)
    public void parseCsvWithNullEventHandlerTest() {
        CsvParserBuilder builder = CsvParserBuilder.getInstance();
        builder.setCommaSeparator(true);
        builder.setSemicolonSeparator(true);
        builder.setCrSeparator(true);
        builder.setLfSeparator(true);
        builder.setCrLfSeparator(true);

        String csv = "1,2\r\n3,4,5,abcdefgh\r\n";

        builder.parse(csv, null);
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithReaderAndNoopEventHandlerTest() {
        CsvParserBuilder builder = CsvParserBuilder.getInstance();
        builder.setCommaSeparator(true);
        builder.setSemicolonSeparator(true);
        builder.setCrSeparator(true);
        builder.setLfSeparator(true);
        builder.setCrLfSeparator(true);

        String csv = "1,2\r\n3,4,5,abcdefgh\r\n";

        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).parse(new StringReader(csv), new NoopEventHandler());

        try {
            builder.setColumnCountCheckEnabled(true).setMaxColumnLength(-1).setMaxColumnLengthCheckEnabled(false).parse(new StringReader(csv), new NoopEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnCountException ex) {
            Assertions.assertThat(ex).hasMessage("CSV has rows with different column count. Last characters: \"1,2\\r\\n3,4,5,\".");
        }

        builder.setColumnCountCheckEnabled(false).setMaxColumnLength(5).setMaxColumnLengthCheckEnabled(false).parse(new StringReader(csv), new NoopEventHandler());

        try {
            builder.setColumnCountCheckEnabled(false).setMaxColumnLength(5).setMaxColumnLengthCheckEnabled(true).parse(new StringReader(csv), new NoopEventHandler());
            Assertions.fail("CsvParser test fail");
        } catch (WrongColumnLengthException ex) {
            Assertions.assertThat(ex).hasMessage("Maximum column value length exceeded. Last characters: \"1,2\\r\\n3,4,5,abcdef\".");
        }
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void parseCsvWithWithZeroCodeByteTest() {
        String csv = "1,2,3\u00004\r\n";
        List<List<String>> result = createCsvParser(true, true, true, true, true).parse(csv);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).containsExactlyInOrder("1", "2", "3\u00004");
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void closeReaderTest() {
        IsClosedReader reader = new IsClosedReader();
        Assertions.assertThat(reader.isClosed()).isFalse();
        CsvParserBuilder.getInstance().parse(reader);
        Assertions.assertThat(reader.isClosed()).isTrue();
    }

    /**
     * {@link CsvParser} class test.
     */
    @Test
    public void errorOnCloseReaderTest() {
        try {
            ErrorOnCloseReader reader = new ErrorOnCloseReader();
            CsvParserBuilder.getInstance().parse(reader);
            Assertions.fail("CsvParser test fail");
        } catch (CsvIOException ex) {
            Assertions.assertThat(ex).hasMessage("ERROR");
        }
    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class ErrorOnReadReader extends Reader {

        ErrorOnReadReader() {
            super();
        }

        @Override
        public int read(final char[] cbuf, final int off, final int len) throws IOException {
            throw new IOException("ERROR");
        }

        @Override
        public void close() throws IOException {
            // Ignore
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class IsClosedReader extends Reader {

        private boolean _closed;

        IsClosedReader() {
            super();
            _closed = false;
        }

        @Override
        public int read(final char[] cbuf, final int off, final int len) throws IOException {
            return -1;
        }

        @Override
        public void close() throws IOException {
            _closed = true;
        }

        boolean isClosed() {
            return _closed;
        }

    }

    /**
     * Test class.
     *
     * @author Dmitry Shapovalov
     */
    private static final class ErrorOnCloseReader extends Reader {

        ErrorOnCloseReader() {
            super();
        }

        @Override
        public int read(final char[] cbuf, final int off, final int len) throws IOException {
            return -1;
        }

        @Override
        public void close() throws IOException {
            throw new IOException("ERROR");
        }

    }

}
