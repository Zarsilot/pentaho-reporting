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

package org.pentaho.reporting.designer.core.util.table.filter;

import org.pentaho.reporting.designer.core.util.table.ElementMetaDataTableModel;
import org.pentaho.reporting.designer.core.util.table.GroupingHeader;
import org.pentaho.reporting.designer.core.util.table.GroupingModel;
import org.pentaho.reporting.designer.core.util.table.TableStyle;

import java.beans.PropertyEditor;

public class DefaultMetaDataFilterTableModel
  extends DefaultFilterTableModel implements ElementMetaDataTableModel, GroupingModel {
  private ElementMetaDataTableModel metaParent;
  private GroupingModel groupingBackend;

  public DefaultMetaDataFilterTableModel( final ElementMetaDataTableModel backend, final int filterColumn ) {
    super( backend, filterColumn );
    if ( backend instanceof GroupingModel ) {
      this.groupingBackend = (GroupingModel) backend;
    }
    this.metaParent = backend;
  }

  public String[] getExtraFields( final int row, final int column ) {
    return metaParent.getExtraFields( mapToModel( row ), column );
  }

  public Class getClassForCell( final int row, final int column ) {
    return metaParent.getClassForCell( mapToModel( row ), column );
  }

  public PropertyEditor getEditorForCell( final int row, final int column ) {
    return metaParent.getEditorForCell( mapToModel( row ), column );
  }

  public String getValueRole( final int row, final int column ) {
    return metaParent.getValueRole( mapToModel( row ), column );
  }

  public void setTableStyle( final TableStyle tableStyle ) {
    metaParent.setTableStyle( tableStyle );
    applyFilter();
  }

  public TableStyle getTableStyle() {
    return metaParent.getTableStyle();
  }

  public GroupingHeader getGroupHeader( final int index ) {
    if ( groupingBackend == null ) {
      return null;
    }
    return groupingBackend.getGroupHeader( mapToModel( index ) );
  }

  public boolean isHeaderRow( final int index ) {
    if ( groupingBackend == null ) {
      return false;
    }
    return groupingBackend.isHeaderRow( mapToModel( index ) );
  }
}
