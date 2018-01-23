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

import ru.d_shap.csv.state.StateHandlerConfiguration;

/**
 * CSV parser event handler, that accumulates columns and rows in memory.
 *
 * @author Dmitry Shapovalov
 */
public final class ListEventHandler extends AbstractListEventHandler implements CsvConfigurable {

    /**
     * Create a new object.
     */
    public ListEventHandler() {
        super();
    }

    @Override
    public void configure(final StateHandlerConfiguration stateHandlerConfiguration) {
        stateHandlerConfiguration.setMaxColumnLength(-1);
        stateHandlerConfiguration.setMaxColumnLengthCheckEnabled(false);
    }

    @Override
    protected void doPushColumn(final String column, final int actualLength) {
        addColumnToCurrentRow(column);
    }

}
