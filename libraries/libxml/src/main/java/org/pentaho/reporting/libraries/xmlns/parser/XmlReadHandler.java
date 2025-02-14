/*
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
* Copyright (c) 2001 - 2013 Object Refinery Ltd, Pentaho Corporation and Contributors..  All rights reserved.
*/

package org.pentaho.reporting.libraries.xmlns.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * A handler for reading an XML element.
 *
 * @author Thomas Morgner
 */
public interface XmlReadHandler {

  /**
   * This method is called at the start of an element.
   *
   * @param uri     the namespace uri.
   * @param tagName the tag name.
   * @param attrs   the attributes.
   * @throws SAXException if there is a parsing error.
   */
  public void startElement( String uri, String tagName, Attributes attrs )
    throws SAXException;

  /**
   * This method is called to process the character data between element tags.
   *
   * @param ch     the character buffer.
   * @param start  the start index.
   * @param length the length.
   * @throws SAXException if there is a parsing error.
   */
  public void characters( char[] ch, int start, int length )
    throws SAXException;

  /**
   * This method is called at the end of an element.
   *
   * @param uri     the namespace uri.
   * @param tagName the tag name.
   * @throws SAXException if there is a parsing error.
   */
  public void endElement( String uri, String tagName )
    throws SAXException;

  /**
   * Returns the object for this element or null, if this element does not create an object.
   *
   * @return the object.
   * @throws SAXException if an parser error occured.
   */
  public Object getObject() throws SAXException;

  /**
   * Initialise.
   *
   * @param rootHandler the root handler.
   * @param uri         the namespace uri.
   * @param tagName     the tag name.
   * @throws SAXException if an parser-error occured.
   */
  public void init( RootXmlReadHandler rootHandler, String uri, String tagName )
    throws SAXException;

}
