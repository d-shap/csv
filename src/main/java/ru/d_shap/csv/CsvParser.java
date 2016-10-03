// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class CsvParser {

    private static final char QUOT = '"';

    private static final char COMMA = ',';

    private static final char SEMICOLON = ';';

    private static final char CR = '\r';

    private static final char LF = '\n';

    private CsvParser() {
        super();
    }

    public static List<List<String>> parseCsv(final String csv) {
        List<String> rows = readRows(csv);
        List<List<String>> result = new ArrayList<>(rows.size());
        for (String row : rows) {
            List<String> columns;
            if (row == null || "".equals(row)) {
                columns = new ArrayList<>();
            } else {
                columns = readColumns(row);
            }
            result.add(columns);
        }
        return result;
    }

    private static List<String> readRows(final String csv) {
        List<String> rows = new LinkedList<>();

        String currentCsv = csv;
        boolean isInQuot = false;
        int position = 0;
        for (; position < currentCsv.length(); position++) {
            if (!isInQuot && currentCsv.charAt(position) == QUOT) {
                if (position == 0) {
                    isInQuot = true;
                    continue;
                }
                if (position > 0 && (currentCsv.charAt(position - 1) == COMMA || currentCsv.charAt(position - 1) == SEMICOLON)) {
                    isInQuot = true;
                    continue;
                }
                throw new CsvParseException("Quot is not bounded");
            }
            if (isInQuot && currentCsv.charAt(position) == QUOT && position < currentCsv.length() - 1 && currentCsv.charAt(position + 1) == QUOT) {
                position++;
                continue;
            }
            if (isInQuot && currentCsv.charAt(position) == QUOT) {
                if (position == currentCsv.length() - 1) {
                    isInQuot = false;
                    continue;
                }
                if (position < currentCsv.length() - 2 && currentCsv.charAt(position + 1) == CR && currentCsv.charAt(position + 2) == LF) {
                    isInQuot = false;
                    continue;
                }
                if (position < currentCsv.length() - 1 && (currentCsv.charAt(position + 1) == CR || currentCsv.charAt(position + 1) == LF)) {
                    isInQuot = false;
                    continue;
                }
                if (position < currentCsv.length() - 1 && (currentCsv.charAt(position + 1) == COMMA || currentCsv.charAt(position + 1) == SEMICOLON)) {
                    isInQuot = false;
                    continue;
                }
                throw new CsvParseException("Value is not properly quoted");
            }
            if (!isInQuot && currentCsv.charAt(position) == CR && position < currentCsv.length() - 1 && currentCsv.charAt(position + 1) == LF) {
                continue;
            }
            if (!isInQuot && (currentCsv.charAt(position) == CR || currentCsv.charAt(position) == LF)) {
                String row;
                if (position > 0 && currentCsv.charAt(position - 1) == CR) {
                    row = currentCsv.substring(0, position - 1);
                } else {
                    row = currentCsv.substring(0, position);
                }
                currentCsv = currentCsv.substring(position + 1, currentCsv.length());
                position = -1;
                rows.add(row);
            }
        }

        if (isInQuot) {
            throw new CsvParseException("Value is not properly quoted");
        }

        if (!"".equals(currentCsv)) {
            rows.add(currentCsv);
        }

        return rows;
    }

    private static List<String> readColumns(final String row) {
        List<String> columns = new LinkedList<>();

        String currentRow = row;
        boolean isInQuot = false;
        int position = 0;
        for (; position < currentRow.length(); position++) {
            if (!isInQuot && currentRow.charAt(position) == QUOT) {
                if (position == 0) {
                    isInQuot = true;
                    continue;
                }
                if (position > 0 && (currentRow.charAt(position - 1) == COMMA || currentRow.charAt(position - 1) == SEMICOLON)) {
                    isInQuot = true;
                    continue;
                }
                throw new CsvParseException("Quot is not bounded");
            }
            if (isInQuot && currentRow.charAt(position) == QUOT && position < currentRow.length() - 1 && currentRow.charAt(position + 1) == QUOT) {
                position++;
                continue;
            }
            if (isInQuot && currentRow.charAt(position) == QUOT) {
                if (position == currentRow.length() - 1) {
                    isInQuot = false;
                    continue;
                }
                if (position < currentRow.length() - 1 && (currentRow.charAt(position + 1) == COMMA || currentRow.charAt(position + 1) == SEMICOLON)) {
                    isInQuot = false;
                    continue;
                }
                throw new CsvParseException("Value is not properly quoted");
            }
            if (!isInQuot && (currentRow.charAt(position) == COMMA || currentRow.charAt(position) == SEMICOLON)) {
                String column = currentRow.substring(0, position);
                currentRow = currentRow.substring(position + 1, currentRow.length());
                position = -1;
                columns.add(getValueFromCsv(column));
            }
        }

        if (isInQuot) {
            throw new CsvParseException("Value is not properly quoted");
        }

        columns.add(getValueFromCsv(currentRow));

        return columns;
    }

    private static String getValueFromCsv(final String value) {
        if (value == null) {
            return null;
        }
        String currentValue = value;
        if (currentValue.length() > 0 && currentValue.charAt(0) == QUOT) {
            currentValue = currentValue.substring(1);
        }
        if (currentValue.length() > 0 && currentValue.charAt(currentValue.length() - 1) == QUOT) {
            currentValue = currentValue.substring(0, currentValue.length() - 1);
        }
        currentValue = currentValue.replace("\"\"", "\"");
        return currentValue;
    }

}
