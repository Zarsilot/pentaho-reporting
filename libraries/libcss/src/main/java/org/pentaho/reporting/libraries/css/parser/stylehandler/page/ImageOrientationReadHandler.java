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

package org.pentaho.reporting.libraries.css.parser.stylehandler.page;

import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.parser.CSSValueReadHandler;
import org.pentaho.reporting.libraries.css.values.CSSAutoValue;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.w3c.css.sac.LexicalUnit;

/**
 * Creation-Date: 08.12.2005, 17:29:41
 *
 * @author Thomas Morgner
 */
public class ImageOrientationReadHandler implements CSSValueReadHandler {
  public ImageOrientationReadHandler() {
  }

  public CSSValue createValue( StyleKey name, LexicalUnit value ) {
    if ( value.getLexicalUnitType() == LexicalUnit.SAC_IDENT ) {
      String ident = value.getStringValue();
      if ( ident.equalsIgnoreCase( "auto" ) ) {
        return CSSAutoValue.getInstance();
      }
      return null;
    }
    if ( value.getLexicalUnitType() != LexicalUnit.SAC_DEGREE ) {
      return null;
    }
    return CSSNumericValue.createValue( CSSNumericType.DEG, value.getFloatValue() );
  }
}
