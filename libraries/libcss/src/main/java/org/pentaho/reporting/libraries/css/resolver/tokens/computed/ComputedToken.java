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

package org.pentaho.reporting.libraries.css.resolver.tokens.computed;

import org.pentaho.reporting.libraries.css.resolver.tokens.ContentToken;

/**
 * A computed token is a placeholder for content that *must* be resolved during the layouting phase. Usually, this deals
 * with compound counters (which need to be split into resolved single counters).
 *
 * @author Thomas Morgner
 */
public abstract class ComputedToken implements ContentToken {
  protected ComputedToken() {
  }
}
