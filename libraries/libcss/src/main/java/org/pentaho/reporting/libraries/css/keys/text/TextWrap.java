/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
*/

package org.pentaho.reporting.libraries.css.keys.text;

import org.pentaho.reporting.libraries.css.values.CSSConstant;

/**
 * Creation-Date: 30.10.2005, 20:11:13
 *
 * @author Thomas Morgner
 */
public class TextWrap {
  public static final CSSConstant NORMAL = new CSSConstant( "normal" );
  public static final CSSConstant UNRESTRICTED = new CSSConstant( "unrestricted" );
  public static final CSSConstant NONE = new CSSConstant( "none" );
  public static final CSSConstant SUPPRESS = new CSSConstant( "suppress" );

  private TextWrap() {
  }
}
