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
package ru.d_shap.csv.state;

/**
 * CSV parser configuration.
 *
 * @author Dmitry Shapovalov
 */
public final class StateHandlerConfiguration {

    private boolean _commaSeparator;

    private boolean _semicolonSeparator;

    private boolean _crSeparator;

    private boolean _lfSeparator;

    private boolean _crLfSeparator;

    private boolean _columnCountCheckEnabled;

    private boolean _skipEmptyRowsEnabled;

    private int _maxColumnLength;

    private boolean _maxColumnLengthCheckEnabled;

    /**
     * Create a new object.
     */
    public StateHandlerConfiguration() {
        super();
        _maxColumnLength = -1;
    }

    /**
     * Create copy of the current object.
     *
     * @return copy of the current object.
     */
    public StateHandlerConfiguration copyOf() {
        StateHandlerConfiguration copy = new StateHandlerConfiguration();
        copy._commaSeparator = _commaSeparator;
        copy._semicolonSeparator = _semicolonSeparator;
        copy._crSeparator = _crSeparator;
        copy._lfSeparator = _lfSeparator;
        copy._crLfSeparator = _crLfSeparator;
        copy._columnCountCheckEnabled = _columnCountCheckEnabled;
        copy._skipEmptyRowsEnabled = _skipEmptyRowsEnabled;
        copy._maxColumnLength = _maxColumnLength;
        copy._maxColumnLengthCheckEnabled = _maxColumnLengthCheckEnabled;
        return copy;
    }

    /**
     * Is comma a column separator.
     *
     * @return true if comma is a column separator.
     */
    public boolean isCommaSeparator() {
        return _commaSeparator;
    }

    /**
     * Set comma as a column separator.
     *
     * @param commaSeparator true if comma is a column separator.
     */
    public void setCommaSeparator(final boolean commaSeparator) {
        _commaSeparator = commaSeparator;
    }

    /**
     * Is semicolon a column separator.
     *
     * @return true if semicolon is a column separator.
     */
    public boolean isSemicolonSeparator() {
        return _semicolonSeparator;
    }

    /**
     * Set semicolon as a column separator.
     *
     * @param semicolonSeparator true if semicolon is a column separator.
     */
    public void setSemicolonSeparator(final boolean semicolonSeparator) {
        _semicolonSeparator = semicolonSeparator;
    }

    /**
     * Is CR a row separator.
     *
     * @return true if CR is a row separator.
     */
    public boolean isCrSeparator() {
        return _crSeparator;
    }

    /**
     * Set CR as a row separator.
     *
     * @param crSeparator true if CR is a row separator.
     */
    public void setCrSeparator(final boolean crSeparator) {
        _crSeparator = crSeparator;
    }

    /**
     * Is LF a row separator.
     *
     * @return true if LF is a row separator.
     */
    public boolean isLfSeparator() {
        return _lfSeparator;
    }

    /**
     * Set LF as a row separator.
     *
     * @param lfSeparator true if LF is a row separator.
     */
    public void setLfSeparator(final boolean lfSeparator) {
        _lfSeparator = lfSeparator;
    }

    /**
     * Is CRLF a row separator.
     *
     * @return true if CRLF is a row separator.
     */
    public boolean isCrLfSeparator() {
        return _crLfSeparator;
    }

    /**
     * Set CRLF as a row separator.
     *
     * @param crLfSeparator true if CRLF is a row separator.
     */
    public void setCrLfSeparator(final boolean crLfSeparator) {
        _crLfSeparator = crLfSeparator;
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
     * Get the maximum length of a column value. If a column value length is greater then the maximum
     * column value length, then either the rest of a column value is skipped, or an exception is thrown.
     *
     * @return the maximum length of a column value, or negative number for no column value length restriction.
     */
    public int getMaxColumnLength() {
        return _maxColumnLength;
    }

    /**
     * Set the maximum length of a column value. If a column value length is greater then the maximum
     * column value length, then either the rest of a column value is skipped, or an exception is thrown.
     *
     * @param maxColumnLength the maximum length of a column value, or negative number for no column value length restriction.
     */
    public void setMaxColumnLength(final int maxColumnLength) {
        _maxColumnLength = maxColumnLength;
    }

    /**
     * Check if an excepton should be thrown if a column value length exceeds the maximum column value length.
     *
     * @return true if an excepton should be thrown.
     */
    public boolean isMaxColumnLengthCheckEnabled() {
        return _maxColumnLengthCheckEnabled;
    }

    /**
     * Specify whether an excepton should be thrown if a column value length exceeds the maximum column value length or not.
     *
     * @param maxColumnLengthCheckEnabled true if an excepton should be thrown.
     */
    public void setMaxColumnLengthCheckEnabled(final boolean maxColumnLengthCheckEnabled) {
        _maxColumnLengthCheckEnabled = maxColumnLengthCheckEnabled;
    }

}
