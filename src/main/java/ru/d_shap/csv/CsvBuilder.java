// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class CsvBuilder {

    private static final List<String> SPECIAL;

    static {
        SPECIAL = new LinkedList<>();
        SPECIAL.add("\"");
        SPECIAL.add(ValueSeparators.COMMA.getValue());
        SPECIAL.add(ValueSeparators.SEMICOLON.getValue());
        SPECIAL.add(RowSeparators.CR.getValue());
        SPECIAL.add(RowSeparators.LF.getValue());
    }

    private final String _valueSeparator;

    private final String _rowSeparator;

    private List<List<String>> _rows;

    private List<String> _currentRow;

    public CsvBuilder() {
        this(ValueSeparators.SEMICOLON, RowSeparators.CRLF);
    }

    public CsvBuilder(final ValueSeparators valueSeparator, final RowSeparators rowSeparator) {
        super();
        _valueSeparator = valueSeparator.getValue();
        _rowSeparator = rowSeparator.getValue();
        _rows = null;
        _currentRow = null;
    }

    private void setRows() {
        if (_rows == null) {
            _rows = new ArrayList<>();
        }
    }

    private void setCurrentRow() {
        if (_currentRow == null) {
            _currentRow = new ArrayList<>();
        }
    }

    public CsvBuilder addColumn(final int value) {
        setCurrentRow();
        _currentRow.add(String.valueOf(value));
        return this;
    }

    public CsvBuilder addColumn(final long value) {
        setCurrentRow();
        _currentRow.add(String.valueOf(value));
        return this;
    }

    public CsvBuilder addColumn(final float value) {
        setCurrentRow();
        _currentRow.add(String.valueOf(value));
        return this;
    }

    public CsvBuilder addColumn(final double value) {
        setCurrentRow();
        _currentRow.add(String.valueOf(value));
        return this;
    }

    public CsvBuilder addColumn(final boolean value) {
        setCurrentRow();
        _currentRow.add(String.valueOf(value));
        return this;
    }

    public CsvBuilder addColumn(final String value) {
        setCurrentRow();
        _currentRow.add(getValueForCsv(value));
        return this;
    }

    private String getValueForCsv(final String value) {
        if (value == null) {
            return "";
        }
        boolean hasSpecial = false;
        for (int i = 0; i < SPECIAL.size(); i++) {
            if (value.contains(SPECIAL.get(i))) {
                hasSpecial = true;
            }
        }

        if (hasSpecial) {
            return "\"" + value.replaceAll("\"", "\"\"") + "\"";
        } else {
            return value;
        }
    }

    public CsvBuilder addRow() {
        setRows();
        setCurrentRow();
        _rows.add(_currentRow);
        _currentRow = null;
        return this;
    }

    public String getCsv() {
        setRows();
        String csv = convertToScv(_rows);
        _rows = null;
        _currentRow = null;
        return csv;
    }

    private String convertToScv(final List<List<String>> rows) {
        StringBuilder result = new StringBuilder();

        for (List<String> row : rows) {
            int size = row.size();
            int sizeM1 = size - 1;
            for (int i = 0; i <= size; i++) {
                if (i == size) {
                    result.append(_rowSeparator);
                } else {
                    String value = row.get(i);
                    result.append(value);
                    if (i != sizeM1) {
                        result.append(_valueSeparator);
                    }
                }
            }
        }

        return result.toString();
    }

}
