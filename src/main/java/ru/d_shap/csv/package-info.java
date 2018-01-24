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
 * CSV is a comma-separated values. It is a list of rows, separated with CRLF characters. Each row
 * is a list of columns, separated with commas. CSV ends with CRLF, but it is not mandatory.
 * Sometimes semicolon is used instead of comma. And sometimes LF is used instead of CRLF. If
 * column value contains comma, semicolon, CR, LF or double quotes, then the whole column is
 * enclosed in double quotes. Double quotes are escaped with double quotes. CSV can have
 * the same number of columns in each row, but sometimes it is not so.
 * </p>
 * <p>
 * CSV is fully described in RFC 4180.
 * </p>
 * <p>
 * To create CSV {@link ru.d_shap.csv.CsvPrinter} object should be used:
 * </p>
 * <pre>{@code
 * CsvPrinter printer = CsvPrinterBuilder.getInstance().build();
 * printer.addColumn(1);
 * printer.addColumn(true);
 * printer.addRow();
 * printer.addColumn(2.2f);
 * printer.addColumn("value");
 * printer.addRow();
 * printer.addColumn("v;a;l;u;e");
 * printer.addColumn("");
 * printer.addRow();
 * printer.addColumn(4L);
 * printer.addColumn('Z');
 * printer.addRow();
 * printer.addColumn(new Object());
 * printer.addColumn(new Object());
 * printer.addRow();
 * ...
 * String csv = printer.getCsv();
 * }</pre>
 * <p>
 * In this example CSV is written to the StringWriter. This could be a memory consuming operation.
 * </p>
 * <p>
 * {@link ru.d_shap.csv.CsvPrinter} object can write CSV directly to the output stream. Next example
 * shows, how {@link ru.d_shap.csv.CsvPrinter} object writes CSV to the file:
 * </p>
 * <pre>{@code
 * try (FileOutputStream stream = new FileOutputStream("someFile.csv");
 *      OutputStreamWriter writer = new OutputStreamWriter(stream);
 *      CsvPrinter printer = CsvPrinterBuilder.getInstance().build(writer)) {
 *     printer.addColumn("value1");
 *     printer.addColumn("value2");
 *     printer.addRow();
 *     ....
 * }
 * }</pre>
 * <p>
 * {@link ru.d_shap.csv.CsvPrinter} object has a number of settings, for example whether to skip empty
 * lines or not, whether to check that each row has the same number of columns or not, and so on.
 * This settings are specified in {@link ru.d_shap.csv.CsvPrinterBuilder} object, that is used to create
 * {@link ru.d_shap.csv.CsvPrinter} object.
 * </p>
 * <p>
 * To parse CSV {@link ru.d_shap.csv.CsvParser} object should be used:
 * </p>
 * <pre>{@code
 * String csv = "value1,,false\r\nvalue2,true,\r\n,value3,value3\r\n";
 * List<List<String>> result = CsvParserBuilder.getInstance().build().parse(csv);
 * }</pre>
 * <p>
 * CSV parser is a push parser. CSV parser reads characters one by one and pushs events (columns and
 * rows) to the {@link ru.d_shap.csv.handler.CsvEventHandler} object. The {@link ru.d_shap.csv.handler.CsvEventHandler}
 * object defines, what to do with pushed columns and rows, for example to count them, or to store
 * them in memory, etc.
 * </p>
 * <p>
 * Some predefined {@link ru.d_shap.csv.handler.CsvEventHandler} objects can be used.
 * </p>
 * <p>
 * Next example shows, how to check if CSV is valid:
 * </p>
 * <pre>{@code
 * String csv = "1,2,3\r\n4,5,6\r\n";
 * CsvParserBuilder.getInstance().build().parse(csv, new NoopEventHandler());
 * }</pre>
 * <p>
 * Next example shows, how to check if CSV is valid and each row has the same number of columns:
 * </p>
 * <pre>{@code
 * String csv = "1,2,3\r\n4,5,6\r\n";
 * CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).build().parse(csv, new NoopEventHandler());
 * }</pre>
 * <p>
 * Next example shows, how to define column and row count of CSV, that has the same number of columns in each row:
 * </p>
 * <pre>{@code
 * String csv = "1,2,3\r\n4,5,6\r\n";
 * DimensionEventHandler eventHandler = new DimensionEventHandler();
 * CsvParserBuilder.getInstance().build().parse(csv, eventHandler);
 * System.out.println("Row count: " + eventHandler.getRowCount());
 * System.out.println("Column count: " + eventHandler.getColumnCount());
 * }</pre>
 * <p>
 * The default {@link ru.d_shap.csv.handler.CsvEventHandler} is {@link ru.d_shap.csv.handler.ListEventHandler}.
 * This handler stores CSV in memory as List of rows, each row is a list of columns. This handler could be a memory
 * consuming one.
 * </p>
 * <p>
 * By default, {@link ru.d_shap.csv.CsvParser} treats commas and semicolons as column separators, LF and
 * CRLF as row separators. Some editors can use semicolon as separator, but not comma. And this editors do NOT
 * enclose column values in double quots if column value contains comma. For example, this editors can produce a
 * CSV like this: <b>&quot;value;value_in_the same_column&quot;;abc,123</b>. There are two columns in this CSV. The
 * first one is enclosed in double quotes and contains semicolon. The second one is NOT enclosed in double quotes and
 * contains comma, that is NOT column separator. {@link ru.d_shap.csv.CsvParserBuilder} has a number of
 * settings that can be used to parse CSV like this.
 * </p>
 */
package ru.d_shap.csv;
