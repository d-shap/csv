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
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * <p>
 * Classes to generate CSV and parse CSV.
 * </p>
 * <p>
 * To create CSV {@link ru.d_shap.csv.CsvBuilder} class should be used:
 * </p>
 * <pre>{@code
 * CsvBuilder builder = new CsvBuilder();
 * builder.addColumn(1);
 * builder.addColumn(true);
 * builder.addRow();
 * builder.addColumn(2.2f);
 * builder.addColumn("value");
 * builder.addColumn("v;a;l;u;e");
 * builder.addColumn("");
 * builder.addRow();
 * builder.addColumn(false);
 * builder.addRow();
 * builder.addColumn(4L);
 * builder.addColumn(10.01);
 * builder.addRow();
 * String csv = builder.getCsv();
 * }</pre>
 * <p>
 * To parse CSV {@link ru.d_shap.csv.CsvParser} class should be used:
 * </p>
 * <pre>{@code
 * String csv = "value1,,false\r\nvalue2,true,\r\n\r\n,value3,value3\r\n";
 * List<List<String>> result = CsvParser.parseCsv(csv);
 * }</pre>
 */
package ru.d_shap.csv;
