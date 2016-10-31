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
 * State machine for CSV parser.
 * </p>
 * <p>
 * CSV parser reads input symbols and defines, whether the symbol is a meaningful symbol or a special symbol.
 * </p>
 * <p>
 * The special symbols are:
 * </p>
 * <ul>
 * <li><b>CR</b>, <b>CL</b> or <b>CRLF</b> - row separator</li>
 * <li><b>Comma</b> or <b>Semicolon</b> - column separator</li>
 * <li><b>Double quotes</b> - column bounds, if column contains other special symbols</li>
 * </ul>
 * <p>
 * After processing input symbol parser generates event and changes its internal state to process next symbol.
 * </p>
 */
package ru.d_shap.csv.state;
