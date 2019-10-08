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

import org.junit.Test;

import ru.d_shap.assertions.Assertions;

/**
 * Tests for {@link CsvIOException}.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvIOExceptionTest extends CsvTest {

    /**
     * Test class constructor.
     */
    public CsvIOExceptionTest() {
        super();
    }

    /**
     * {@link CsvIOException} class test.
     */
    @Test
    public void errorMessageTest() {
        IOException ioException1 = new IOException("IO message");
        CsvIOException exception1 = new CsvIOException(ioException1);
        Assertions.assertThat(exception1).hasMessage("IO message");

        RuntimeException runtimeException = new RuntimeException("Runtime message");
        IOException ioException2 = new IOException(runtimeException);
        CsvIOException exception2 = new CsvIOException(ioException2);
        Assertions.assertThat(exception2).hasMessage("java.lang.RuntimeException: Runtime message");
    }

}
