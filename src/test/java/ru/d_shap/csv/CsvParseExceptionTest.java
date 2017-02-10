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
import ru.d_shap.csv.state.AbstractState;

/**
 * Tests for {@link CsvParseException}.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvParseExceptionTest {

    /**
     * Test class constructor.
     */
    public CsvParseExceptionTest() {
        super();
    }

    /**
     * {@link CsvParseException} class test.
     */
    @Test
    public void errorMessageTest() {
        CsvParseException exception1 = new CsvParseException('a', "bvda");
        Assertions.assertThat(exception1).hasMessage("Wrong symbol obtained: 'a' (97). Last symbols: \"bvda\".");

        CsvParseException exception2 = new CsvParseException('D', "ret5fD");
        Assertions.assertThat(exception2).hasMessage("Wrong symbol obtained: 'D' (68). Last symbols: \"ret5fD\".");

        CsvParseException exception3 = new CsvParseException(AbstractState.END_OF_INPUT, "xyz");
        Assertions.assertThat(exception3).hasMessage("End of input obtained. Last symbols: \"xyz\".");
    }

}
