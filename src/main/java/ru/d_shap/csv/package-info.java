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
 * Classes to create CSV and parse CSV.
 * </p>
 * <p>
 * CSV is a comma-separated values. It is a list of rows, separated with CRLF symbols. Each row
 * is a list of columns, separated with commas. CSV ends with CRLF, but it is not mandatory.
 * Sometimes semicolon is used instead of comma. And sometimes LF is used instead of CRLF. If
 * column value contains comma, semicolon, CR, LF or double quotes, then the whole column is
 * enclosed in double quotes. Double quotes are escaped with double quotes. CSV can have
 * the same number of columns in each row, but sometimes it is not so.
 * </p>
 * <p>
 * CSV  is fully described in RFC 4180.
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
 * {@link ru.d_shap.csv.CsvBuilder} can write CSV directly to the output stream. Next example shows,
 * how {@link ru.d_shap.csv.CsvBuilder} writes CSV to the file:
 * </p>
 * <pre>{@code
 * try (FileOutputStream stream = new FileOutputStream("someFile.csv")) {
 *     OutputStreamWriter writer = new OutputStreamWriter(stream);
 *     CsvBuilder builder = new CsvBuilder(writer);
 *     builder.addColumn("value1");
 *     builder.addColumn("value2");
 *     builder.addRow();
 *     ...
 * }
 * }</pre>
 * <p>
 * {@link ru.d_shap.csv.CsvBuilder} can optionally check if each row has the same number of columns.
 * </p>
 * <p>
 * To parse CSV {@link ru.d_shap.csv.CsvParser} class should be used:
 * </p>
 * <pre>{@code
 * String csv = "value1,,false\r\nvalue2,true,\r\n\r\n,value3,value3\r\n";
 * List<List<String>> result = CsvParser.parse(csv);
 * }</pre>
 * <p>
 * CSV parser is a push parser. It reads a source symbol by symbol and pushes events to the
 * {@link ru.d_shap.csv.handler.CsvEventHandler} object. {@link ru.d_shap.csv.handler.CsvEventHandler}
 * defines the result of CSV parser.
 * </p>
 * <p>
 * Some predefined {@link ru.d_shap.csv.handler.CsvEventHandler} objects can be used.
 * </p>
 * <p>
 * Next example shows, how to check if CSV is valid:
 * </p>
 * <pre>{@code
 * String csv = "1,2,3\r\n4,5,6\r\n";
 * CsvParser.parse(csv, new NoopEventHandler());
 * }</pre>
 * <p>
 * Next example shows, how to check if CSV is valid and rectangular (each row has the same number of columns):
 * </p>
 * <pre>{@code
 * String csv = "1,2,3\r\n4,5,6\r\n";
 * CsvParser.parse(csv, new NoopEventHandler(), true);
 * }</pre>
 * <p>
 * Next example shows, how to define column and row count of rectangular CSV:
 * </p>
 * <pre>{@code
 * String csv = "1,2,3\r\n4,5,6\r\n";
 * DimensionEventHandler eventHandler = new DimensionEventHandler();
 * CsvParser.parse(csv, eventHandler, true);
 * System.out.println("Row count: " + eventHandler.getRowCount());
 * System.out.println("Column count: " + eventHandler.getColumnCount());
 * }</pre>
 * <p>
 * The default {@link ru.d_shap.csv.handler.CsvEventHandler} is {@link ru.d_shap.csv.handler.ListEventHandler}.
 * This handler stores CSV in memory as List of rows, each row is a list of columns. This handler could be a memory
 * consuming one.
 * </p>
 * <p>
 * By default, {@link ru.d_shap.csv.CsvParser} treats commas and semicolons as column separators, CR, LF and
 * CRLF as row separators. Some editors can use semicolon as separator, but not comma. And this editors do NOT
 * enclose column values in double quots if column value contains comma. For example, this editors can produce a
 * CSV like this: <b>&quot;value;value_in_the same_column&quot;;abc,123</b>. There are two columns in this CSV. The
 * first one is enclosed in double quotes and contains semicolon. The second one is NOT enclosed in double quotes and
 * contains comma, that is NOT column separator. To parse such a CSV overloaded methods with column and row separators
 * can be used. If methods with NO separators are used, then all separators are assumed.
 * </p>
 */
package ru.d_shap.csv;
