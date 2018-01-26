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
package ru.d_shap.csv;

import ru.d_shap.csv.state.SpecialCharacter;

/**
 * CSV printer configuration.
 *
 * @author Dmitry Shapovalov
 */
public final class CsvPrinterConfiguration {

    private static final String COMMA = String.valueOf((char) SpecialCharacter.COMMA);

    private static final String SEMICOLON = String.valueOf((char) SpecialCharacter.SEMICOLON);

    private static final String CR = String.valueOf((char) SpecialCharacter.CR);

    private static final String LF = String.valueOf((char) SpecialCharacter.LF);

    private static final String CRLF = CR + LF;

    private String _columnSeparator;

    private String _rowSeparator;

    private boolean _columnCountCheckEnabled;

    private boolean _skipEmptyRowsEnabled;

    private boolean _escapeAllSeparatorsEnabled;

    CsvPrinterConfiguration() {
        super();
    }

    CsvPrinterConfiguration copyOf() {
        CsvPrinterConfiguration copy = new CsvPrinterConfiguration();
        copy._columnSeparator = _columnSeparator;
        copy._rowSeparator = _rowSeparator;
        copy._columnCountCheckEnabled = _columnCountCheckEnabled;
        copy._skipEmptyRowsEnabled = _skipEmptyRowsEnabled;
        copy._escapeAllSeparatorsEnabled = _escapeAllSeparatorsEnabled;
        return copy;
    }

    /**
     * Validate current CSV printer configuration.
     */
    public void validate() {
        if (!isCommaSeparator() && !isSemicolonSeparator()) {
            throw new WrongColumnSeparatorException();
        }
        if (!isCrSeparator() && !isLfSeparator() && !isCrLfSeparator()) {
            throw new WrongRowSeparatorException();
        }
    }

    /**
     * Get column separator.
     *
     * @return column separator.
     */
    public String getColumnSeparator() {
        return _columnSeparator;
    }

    /**
     * Is comma a column separator.
     *
     * @return true if comma is a column separator.
     */
    public boolean isCommaSeparator() {
        return COMMA.equals(_columnSeparator);
    }

    /**
     * Set comma as a column separator.
     */
    public void setCommaSeparator() {
        _columnSeparator = COMMA;
    }

    /**
     * Is semicolon a column separator.
     *
     * @return true if semicolon is a column separator.
     */
    public boolean isSemicolonSeparator() {
        return SEMICOLON.equals(_columnSeparator);
    }

    /**
     * Set semicolon as a column separator.
     */
    public void setSemicolonSeparator() {
        _columnSeparator = SEMICOLON;
    }

    /**
     * Get row separator.
     *
     * @return row separator.
     */
    public String getRowSeparator() {
        return _rowSeparator;
    }

    /**
     * Is CR a row separator.
     *
     * @return true if CR is a row separator.
     */
    public boolean isCrSeparator() {
        return CR.equals(_rowSeparator);
    }

    /**
     * Set CR as a row separator.
     */
    public void setCrSeparator() {
        _rowSeparator = CR;
    }

    /**
     * Is LF a row separator.
     *
     * @return true if LF is a row separator.
     */
    public boolean isLfSeparator() {
        return LF.equals(_rowSeparator);
    }

    /**
     * Set LF as a row separator.
     */
    public void setLfSeparator() {
        _rowSeparator = LF;
    }

    /**
     * Is CRLF a row separator.
     *
     * @return true if CRLF is a row separator.
     */
    public boolean isCrLfSeparator() {
        return CRLF.equals(_rowSeparator);
    }

    /**
     * Set CRLF as a row separator.
     */
    public void setCrLfSeparator() {
        _rowSeparator = CRLF;
    }

    /**
     * Check if all rows should have the same column count.
     *
     * @return true if all rows should have the same column count.
     */
    public boolean isColumnCountCheckEnabled() {
        return _columnCountCheckEnabled;
    }

    /**
     * Specify whether all rows should have the same column count or not.
     *
     * @param columnCountCheckEnabled true if all rows should have the same column count.
     */
    public void setColumnCountCheckEnabled(final boolean columnCountCheckEnabled) {
        _columnCountCheckEnabled = columnCountCheckEnabled;
    }

    /**
     * Check if all empty rows should be skipped.
     *
     * @return true if all empty rows should be skipped.
     */
    public boolean isSkipEmptyRowsEnabled() {
        return _skipEmptyRowsEnabled;
    }

    /**
     * Specify whether all empty rows should be skipped or not.
     *
     * @param skipEmptyRowsEnabled true if all empty rows should be skipped.
     */
    public void setSkipEmptyRowsEnabled(final boolean skipEmptyRowsEnabled) {
        _skipEmptyRowsEnabled = skipEmptyRowsEnabled;
    }

    /**
     * Check if all column and row separators should be escaped, or only separators specified in this object.
     *
     * @return true if all column and row separators should be escaped, false if only separators specified in this object.
     */
    public boolean isEscapeAllSeparatorsEnabled() {
        return _escapeAllSeparatorsEnabled;
    }

    /**
     * Specify whether all column and row separators should be escaped, or only separators specified in this object.
     *
     * @param escapeAllSeparatorsEnabled true if all column and row separators should be escaped, false if only separators specified in this object.
     */
    public void setEscapeAllSeparatorsEnabled(final boolean escapeAllSeparatorsEnabled) {
        _escapeAllSeparatorsEnabled = escapeAllSeparatorsEnabled;
    }

    boolean hasSpecialCharacters(final String value) {
        if ((_escapeAllSeparatorsEnabled || isCommaSeparator()) && value.indexOf(SpecialCharacter.COMMA) >= 0) {
            return true;
        }
        if ((_escapeAllSeparatorsEnabled || isSemicolonSeparator()) && value.indexOf(SpecialCharacter.SEMICOLON) >= 0) {
            return true;
        }
        if ((_escapeAllSeparatorsEnabled || isCrSeparator()) && value.indexOf(SpecialCharacter.CR) >= 0) {
            return true;
        }
        if ((_escapeAllSeparatorsEnabled || isLfSeparator()) && value.indexOf(SpecialCharacter.LF) >= 0) {
            return true;
        }
        if ((_escapeAllSeparatorsEnabled || isCrLfSeparator()) && value.contains(CRLF)) {
            return true;
        }
        return value.indexOf(SpecialCharacter.QUOT) >= 0;
    }

}
