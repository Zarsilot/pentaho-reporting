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

package org.pentaho.reporting.designer.core.actions.elements;

import org.pentaho.reporting.designer.core.actions.AbstractElementSelectionAction;
import org.pentaho.reporting.designer.core.actions.ActionMessages;
import org.pentaho.reporting.designer.core.editor.ReportDocumentContext;
import org.pentaho.reporting.designer.core.model.ModelUtility;
import org.pentaho.reporting.designer.core.settings.SettingsListener;
import org.pentaho.reporting.designer.core.settings.WorkspaceSettings;
import org.pentaho.reporting.designer.core.util.IconLoader;
import org.pentaho.reporting.designer.core.util.exceptions.UncaughtExceptionsModel;
import org.pentaho.reporting.designer.core.util.undo.UndoEntry;
import org.pentaho.reporting.engine.classic.core.CrosstabGroup;
import org.pentaho.reporting.engine.classic.core.CrosstabOtherGroup;
import org.pentaho.reporting.engine.classic.core.CrosstabOtherGroupBody;
import org.pentaho.reporting.engine.classic.core.Group;
import org.pentaho.reporting.engine.classic.core.GroupBody;
import org.pentaho.reporting.engine.classic.core.event.ReportModelEvent;
import org.pentaho.reporting.engine.classic.core.filter.types.CrosstabElementType;
import org.pentaho.reporting.engine.classic.core.util.InstanceID;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Inserts a crosstab row group, only appears when inside a crosstab row
 *
 * @author Will Gorman (wgorman@pentaho.com)
 */
public final class InsertCrosstabOtherGroupAction extends AbstractElementSelectionAction implements SettingsListener {
  private static final long serialVersionUID = 8941387470673515186L;

  public InsertCrosstabOtherGroupAction() {
    putValue( Action.NAME, ActionMessages.getString( "InsertCrosstabOtherGroupAction.Text" ) );
    putValue( Action.SHORT_DESCRIPTION, ActionMessages.getString( "InsertCrosstabOtherGroupAction.Description" ) );
    putValue( Action.MNEMONIC_KEY, ActionMessages.getOptionalMnemonic( "InsertCrosstabOtherGroupAction.Mnemonic" ) );
    putValue( Action.ACCELERATOR_KEY,
      ActionMessages.getOptionalKeyStroke( "InsertCrosstabOtherGroupAction.Accelerator" ) );
    putValue( Action.SMALL_ICON, IconLoader.getInstance().getGenericSquare() );

    setVisible( WorkspaceSettings.getInstance().isVisible( CrosstabElementType.INSTANCE.getMetaData() ) );
    WorkspaceSettings.getInstance().addSettingsListener( this );
  }

  protected void selectedElementPropertiesChanged( final ReportModelEvent event ) {
  }

  public void actionPerformed( final ActionEvent e ) {
    final ReportDocumentContext activeContext = getActiveContext();
    if ( activeContext == null ) {
      return;
    }

    final CrosstabOtherGroup newGroup = new CrosstabOtherGroup();
    try {
      Object selectedElement = null;
      if ( getSelectionModel().getSelectionCount() > 0 ) {
        selectedElement = getSelectionModel().getSelectedElement( 0 );
      }
      if ( selectedElement instanceof CrosstabOtherGroup ) {
        // execution order is important here.
        // first unlink the old root-group by setting a new one ...
        final CrosstabOtherGroup selectedGroup = (CrosstabOtherGroup) selectedElement;
        final GroupBody oldGroupBody = selectedGroup.getBody();
        final CrosstabOtherGroupBody newGroupBody = new CrosstabOtherGroupBody( newGroup );
        selectedGroup.setBody( newGroupBody );
        newGroup.setBody( oldGroupBody );

        activeContext.getUndo().addChange( ActionMessages.getString( "InsertCrosstabOtherGroupAction.UndoName" ),
          new InsertGroupBodyOnGroupUndoEntry( selectedGroup.getObjectID(), oldGroupBody, newGroupBody ) );
      }
      if ( selectedElement instanceof CrosstabGroup ) {
        final CrosstabGroup selectedGroup = (CrosstabGroup) selectedElement;
        final GroupBody oldGroupBody = selectedGroup.getBody();
        final CrosstabOtherGroupBody newGroupBody = new CrosstabOtherGroupBody( newGroup );
        selectedGroup.setBody( newGroupBody );
        newGroup.setBody( oldGroupBody );

        activeContext.getUndo().addChange( ActionMessages.getString( "InsertCrosstabOtherGroupAction.UndoName" ),
          new InsertGroupBodyOnGroupUndoEntry( selectedGroup.getObjectID(), oldGroupBody, newGroupBody ) );
      }
    } catch ( Exception ex ) {
      UncaughtExceptionsModel.getInstance().addException( ex );
    }
  }

  protected void updateSelection() {
    if ( isVisible() == false ) {
      setEnabled( false );
      return;
    }

    if ( getSelectionModel() != null && getSelectionModel().getSelectionCount() == 0 ) {
      // there's nothing selected, we can safely add a new group
      // at the report level (AbstractReportDefinition)
      setEnabled( false );
      return;
    }
    if ( isSingleElementSelection() == false ) {
      // there's more than 1 element selected, disable because
      // we can't know where to insert in this case
      setEnabled( false );
      return;
    }

    final Object selectedElement = getSelectionModel().getSelectedElement( 0 );
    if ( selectedElement instanceof CrosstabOtherGroup ) {
      // if the selectedElement is the report-definition or a relational group
      // then we can safely insert to those
      setEnabled( true );
      return;
    }
    if ( selectedElement instanceof CrosstabGroup ) {
      setEnabled( true );
      return;
    }
    setEnabled( false );
  }

  private static class InsertGroupBodyOnGroupUndoEntry implements UndoEntry {
    private static final long serialVersionUID = 6615171451777587555L;

    private InstanceID target;
    private GroupBody newRootGroup;
    private GroupBody oldRootGroup;

    private InsertGroupBodyOnGroupUndoEntry( final InstanceID target,
                                             final GroupBody oldRootGroup,
                                             final GroupBody newRootGroup ) {
      this.target = target;
      this.oldRootGroup = oldRootGroup;
      this.newRootGroup = newRootGroup;
    }

    public void undo( final ReportDocumentContext renderContext ) {
      final Group selectedGroup = (Group)
        ModelUtility.findElementById( renderContext.getReportDefinition(), target );
      selectedGroup.setBody( oldRootGroup );
    }

    public void redo( final ReportDocumentContext renderContext ) {
      final Group selectedGroup = (Group)
        ModelUtility.findElementById( renderContext.getReportDefinition(), target );
      selectedGroup.setBody( newRootGroup );
    }

    public UndoEntry merge( final UndoEntry newEntry ) {
      return null;
    }
  }

  public void settingsChanged() {
    setVisible( WorkspaceSettings.getInstance().isVisible( CrosstabElementType.INSTANCE.getMetaData() ) );
  }
}
