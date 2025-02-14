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

package org.pentaho.reporting.libraries.css.parser.stylehandler.font;

import org.pentaho.reporting.libraries.css.keys.font.FontStretch;
import org.pentaho.reporting.libraries.css.parser.stylehandler.OneOfConstantsReadHandler;

/**
 * Creation-Date: 28.11.2005, 16:51:33
 *
 * @author Thomas Morgner
 */
public class FontStretchReadHandler extends OneOfConstantsReadHandler {
  public FontStretchReadHandler() {
    super( false );
    addValue( FontStretch.CONDENSED );
    addValue( FontStretch.EXPANDED );
    addValue( FontStretch.EXTRA_CONDENSED );
    addValue( FontStretch.EXTRA_EXPANDED );
    addValue( FontStretch.NORMAL );
    addValue( FontStretch.SEMI_CONDENSED );
    addValue( FontStretch.SEMI_EXPANDED );
    addValue( FontStretch.ULTRA_CONDENSED );
    addValue( FontStretch.ULTRA_EXPANDED );

    addValue( FontStretch.WIDER );
    addValue( FontStretch.NARROWER );
  }
}
