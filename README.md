CSV parser
==========
CSV parser converts source stream to rows and columns and vice versa.

CSV is a comma-separated values.
It is a list of rows, separated with CRLF characters.
Each row is a list of columns, separated with commas.
CSV ends with CRLF, but it is not mandatory.
Sometimes semicolon is used instead of comma.
And sometimes LF is used instead of CRLF.
If column value contains comma, semicolon, CR, LF or double quotes, then the whole column is enclosed in double quotes.
Double quotes are escaped with double quotes.
CSV can have the same number of columns in each row, but sometimes it has not.

CSV is fully described in RFC 4180.

To print CSV `CsvPrinter` class should be used:
```
CsvPrinter printer = CsvPrinterBuilder.getInstance().build();
printer.addColumn(1);
printer.addColumn(true);
printer.addRow();
printer.addColumn(2.2f);
printer.addColumn("value");
printer.addRow();
printer.addColumn("v;a;l;u;e");
printer.addColumn("");
printer.addRow();
printer.addColumn(4L);
printer.addColumn('Z');
printer.addRow();
printer.addColumn(new Object());
printer.addColumn(new Object());
printer.addRow();
...
String csv = printer.getCsv();
```

In this example CSV is written to the `StringWriter`.
This could be a memory consuming operation.

`CsvPrinter` object can write CSV directly to the output stream.
Next example shows, how `CsvPrinter` object writes CSV to the file:
```
try (FileOutputStream stream = new FileOutputStream("someFile.csv");
     OutputStreamWriter writer = new OutputStreamWriter(stream);
     CsvPrinter printer = CsvPrinterBuilder.getInstance().build(writer)) {
    printer.addColumn("value1");
    printer.addColumn("value2");
    printer.addRow();
    ....
}
```

A `CsvPrinter` object has a number of settings, for example whether to skip empty lines or not, whether to check that each row has the same number of columns or not, and so on.
This settings are specified in a `CsvPrinterBuilder` object, that is used to create a `CsvPrinter` object.

To parse CSV `CsvParser` class should be used:
```
String csv = "value1,,false\r\nvalue2,true,\r\n,value3,value3\r\n";
List<List<String>> result = CsvParserBuilder.getInstance().parse(csv);
```

CSV parser is a push parser.
CSV parser reads characters one by one and pushs events (columns and rows) to the `CsvEventHandler` object.
The `CsvEventHandler` object defines, what to do with pushed columns and rows - to count them, to store them in memory, and so on.

Some predefined `CsvEventHandler` objects can be used.

Next example shows, how to check if CSV is valid:
```
String csv = "1,2,3\r\n4,5,6\r\n";
CsvParserBuilder.getInstance().parse(csv, new NoopEventHandler());
```

Next example shows, how to check if CSV is valid and each row has the same number of columns:
```
String csv = "1,2,3\r\n4,5,6\r\n";
CsvParserBuilder.getInstance().setColumnCountCheckEnabled(true).parse(csv, new NoopEventHandler());
```

Next example shows, how to define column and row count of CSV, that has the same number of columns in each row:
```
String csv = "1,2,3\r\n4,5,6\r\n";
DimensionEventHandler eventHandler = new DimensionEventHandler();
CsvParserBuilder.getInstance().parse(csv, eventHandler);
System.out.println("Row count: " + eventHandler.getRowCount());
System.out.println("Column count: " + eventHandler.getColumnCount());
```

The default `CsvEventHandler` is a `ListEventHandler`.
This handler stores CSV in memory as list of rows, each row is a list of columns.
This handler could be a memory consuming one.

By default, `CsvParser` object treats commas and semicolons as column separators, LF and CRLF as row separators.

A `CsvParser` object has a number of settings, for example whether to skip empty lines or not, whether to check that each row has the same number of columns or not, and so on.
This settings are specified in a `CsvParserBuilder` object, that is used to create a `CsvParser` object.

This settings can be used in many different situations.

For example, some editors can use semicolon as column separator, but not comma.
This editors do NOT enclose column values in double quots if column value contains commas.
For example, this editors can produce a CSV like this:
```
"value;value_in_the same_column";abc,123
```

There are two columns in this CSV.
The first one is enclosed in double quotes and contains semicolon.
The second one is NOT enclosed in double quotes and contains comma, that is NOT column separator.
The following code is an example of how to deal with CSV like this:
```
String csv = "\"value;value_in_the same_column\";abc,123";
List<List<String>> result = CsvParserBuilder.getInstance().setCommaSeparator(false).setSemicolonSeparator(true).parse(csv);
```

Donation
========
If you find my code useful, you can [![bye me a coffee](donation.png)](https://www.paypal.me/dshapovalov)
