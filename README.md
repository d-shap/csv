CSV parser
==========
Classes to create CSV and parse CSV.

CSV is a comma-separated values.
It is a list of rows, separated with CRLF symbols.
Each row is a list of columns, separated with commas.
CSV ends with CRLF, but it is not mandatory.
Sometimes semicolon is used instead of comma.
And sometimes LF is used instead of CRLF.
If column value contains comma, semicolon, CR, LF or double quotes, then the whole column is enclosed in double quotes.
Double quotes are escaped with double quotes.
CSV can have the same number of columns in each row, but sometimes it is not so.

CSV is fully described in RFC 4180.

To create CSV CsvBuilder class should be used:
```
CsvBuilder builder = new CsvBuilder();
builder.addColumn(1);
builder.addColumn(true);
builder.addRow();
builder.addColumn(2.2f);
builder.addColumn("value");
builder.addColumn("v;a;l;u;e");
builder.addColumn("");
builder.addRow();
builder.addColumn(false);
builder.addRow();
builder.addColumn(4L);
builder.addColumn(10.01);
builder.addRow();
String csv = builder.getCsv();
```

CsvBuilder can write CSV directly to the output stream.
Next example shows, how CsvBuilder writes CSV to the file:
```
try (FileOutputStream stream = new FileOutputStream("someFile.csv")) {
    OutputStreamWriter writer = new OutputStreamWriter(stream);
    CsvBuilder builder = new CsvBuilder(writer);
    builder.addColumn("value1");
    builder.addColumn("value2");
    builder.addRow();
    ...
}
```

CsvBuilder can optionally check if each row has the same number of columns.

To parse CSV CsvParser class should be used:
```
String csv = "value1,,false\r\nvalue2,true,\r\n\r\n,value3,value3\r\n";
List<List<String>> result = CsvParser.parse(csv);
```

CSV parser is a push parser.
It reads a source symbol by symbol and pushes events to the IParserEventHandler object.
IParserEventHandler defines the result of CSV parser.

Some predefined IParserEventHandler objects can be used.

Next example shows, how to check if CSV is valid:
```
String csv = "1,2,3\r\n4,5,6\r\n";
CsvParser.parse(csv, new NoopEventHandler());
```

Next example shows, how to check if CSV is valid and rectangular (each row has the same number of columns):
```
String csv = "1,2,3\r\n4,5,6\r\n";
CsvParser.parse(csv, new NoopEventHandler(), true);
```

Next example shows, how to define column and row count of rectangular CSV:
```
String csv = "1,2,3\r\n4,5,6\r\n";
DimensionEventHandler eventHandler = new DimensionEventHandler();
CsvParser.parse(csv, eventHandler, true);
System.out.println("Row count: " + eventHandler.getRowCount());
System.out.println("Column count: " + eventHandler.getColumnCount());
```

The default IParserEventHandler is ListEventHandler.
This handler stores CSV in memory as List of rows, each row is a list of columns.
This handler could be a memory consuming handler.

By default, CsvParser treats commas and semicolons as column separators, CR, LF and CRLF as row separators.
Some editors can use semicolon as separator, but not comma.
And this editors do NOT enclose column values in double quots if column value contains comma.
For example, this editors can produce a CSV like this: **"value;value_in_the same_column";abc,123**.
There are two columns in this CSV.
The first one is enclosed in double quotes and contains semicolon.
The second one is NOT enclosed in double quotes and contains comma, that is NOT column separator.
To parse such a CSV overloaded methods with column and row separators can be used.
If methods with NO separators are used, then all separators are assumed.
