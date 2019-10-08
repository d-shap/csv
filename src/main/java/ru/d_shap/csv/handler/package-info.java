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
/**
 * <p>
 * CSV parser event handlers.
 * </p>
 * <p>
 * CSV parser is a push parser. CSV parser reads characters one by one and pushs events (columns and
 * rows) to the {@link ru.d_shap.csv.handler.CsvEventHandler} object. The {@link ru.d_shap.csv.handler.CsvEventHandler}
 * object defines, what to do with pushed columns and rows - to count them, to store them in memory, and so on.
 * </p>
 * <p>
 * If the {@link ru.d_shap.csv.handler.CsvEventHandler} object also implements {@link ru.d_shap.csv.handler.CsvConfigurable}
 * interface, then the {@link ru.d_shap.csv.handler.CsvEventHandler} object can modify CSV parser behaviour.
 * For example add restriction to the column value length, add restriction to the column count, and so on.
 * </p>
 * <p>
 * CSV parser behaviour is specified in the {@link ru.d_shap.csv.CsvParserBuilder} object. And then
 * this behaviour is altered by the {@link ru.d_shap.csv.handler.CsvEventHandler} object. There is
 * no way to cancel {@link ru.d_shap.csv.handler.CsvEventHandler} modification in CSV parser behaviour.
 * In this case another {@link ru.d_shap.csv.handler.CsvEventHandler} should be used.
 * </p>
 */
package ru.d_shap.csv.handler;
