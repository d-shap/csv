// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
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
