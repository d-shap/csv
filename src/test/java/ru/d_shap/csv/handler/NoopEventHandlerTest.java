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

import ru.d_shap.csv.CsvTest;

/**
 * Tests for {@link NoopEventHandler}.
 *
 * @author Dmitry Shapovalov
 */
public final class NoopEventHandlerTest extends CsvTest {

    /**
     * Test class constructor.
     */
    public NoopEventHandlerTest() {
        super();
    }

    /**
     * {@link NoopEventHandler} class test.
     */
    @Test
    public void pushColumnTest() {
        NoopEventHandler eventHandler = new NoopEventHandler();

        eventHandler.pushColumn("a", 1);

        eventHandler.pushColumn("bb", 2);
        eventHandler.pushColumn("ccc", 3);
    }

    /**
     * {@link NoopEventHandler} class test.
     */
    @Test
    public void pushColumnAndRowTest() {
        NoopEventHandler eventHandler = new NoopEventHandler();

        eventHandler.pushColumn("a", 1);
        eventHandler.pushRow();

        eventHandler.pushColumn("bb", 2);
        eventHandler.pushColumn("ccc", 3);
        eventHandler.pushRow();
    }

}
