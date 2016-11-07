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

import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals("Wrong symbol obtained: 'a' (97). Last symbols: \"bvda\".", exception1.getMessage());

        CsvParseException exception2 = new CsvParseException('D', "ret5fD");
        Assert.assertEquals("Wrong symbol obtained: 'D' (68). Last symbols: \"ret5fD\".", exception2.getMessage());

        CsvParseException exception3 = new CsvParseException(AbstractState.END_OF_INPUT, "xyz");
        Assert.assertEquals("End of input obtained. Last symbols: \"xyz\".", exception3.getMessage());
    }

}
