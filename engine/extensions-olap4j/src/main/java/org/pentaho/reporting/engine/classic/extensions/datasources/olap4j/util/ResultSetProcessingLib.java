/*
 * This program is free software; you can redistribute it and/or modify it under the
 *  terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 *  Foundation.
 *
 *  You should have received a copy of the GNU Lesser General Public License along with this
 *  program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *  or from the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.
 *
 *  Copyright (c) 2006 - 2009 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.reporting.engine.classic.extensions.datasources.olap4j.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.olap4j.CellSetAxis;
import org.olap4j.Position;
import org.olap4j.metadata.Member;
import org.pentaho.reporting.engine.classic.core.util.IntList;
import org.pentaho.reporting.engine.classic.extensions.datasources.olap4j.Olap4jUtil;
import org.pentaho.reporting.libraries.base.util.FastStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResultSetProcessingLib {
  private static final Log logger = LogFactory.getLog( ResultSetProcessingLib.class );

  public static int[] computeItemsPerAxis( final List<CellSetAxis> axes ) {
    int[] axesSize = new int[ axes.size() ];
    for ( int axesIndex = 0; axesIndex < axes.size(); axesIndex += 1 ) {
      final CellSetAxis axis = axes.get( axesIndex );
      axesSize[ axesIndex ] = axis.getPositions().size();
    }
    return axesSize;
  }

  public static IntList computeColumnToAxisMapping( final List<CellSetAxis> axes, final int[] axesMembers,
                                                    final int columnCount, final int startAxis ) {
    IntList columnToAxisPosition = new IntList( columnCount );
    for ( int axesIndex = axes.size() - 1; axesIndex >= startAxis; axesIndex -= 1 ) {
      int memberCntAxis = axesMembers[ axesIndex ];
      for ( int x = 0; x < memberCntAxis; x += 1 ) {
        columnToAxisPosition.add( axesIndex );
      }
    }
    return columnToAxisPosition;
  }


  public static int computeMemberCountForAxis( final CellSetAxis axis,
                                               final boolean membersOnAxisSorted ) {
    final List<Position> positions = axis.getPositions();
    final MemberAddingStrategy strategy = membersOnAxisSorted ?
      new SortedMemberAddingStrategy( positions ) :
      new ResultSetOrderMemberAddingStrategy();

    for ( int positionsIndex = 0; positionsIndex < positions.size(); positionsIndex++ ) {
      final List<Member> position = positions.get( positionsIndex ).getMembers();
      for ( int positionIndex = 0; positionIndex < position.size(); positionIndex++ ) {
        Member m = position.get( positionIndex );
        computeDeepColumnNames( m, strategy );
      }
    }

    return strategy.values().size();
  }

  public static int[] computeTotalColumnsPerAxis( final List<CellSetAxis> axes, final int startAxis,
                                                  final boolean membersOnAxisSorted ) {
    final int[] membersPerAxis = new int[ axes.size() ];
    // Axis contains (zero or more) positions, which contains (zero or more) members
    for ( int axesIndex = startAxis; axesIndex < axes.size(); axesIndex++ ) {
      CellSetAxis axis = axes.get( axesIndex );
      membersPerAxis[ axesIndex ] = ResultSetProcessingLib.computeMemberCountForAxis( axis, membersOnAxisSorted );
    }

    return membersPerAxis;
  }


  public static ArrayList<Member> computeColumnToMemberMapping( final List<CellSetAxis> axes,
                                                                final int[] axesMembers,
                                                                final int startAxis,
                                                                final boolean membersOnAxisSorted ) {
    final ArrayList<Member> columnToMemberMapper = new ArrayList<Member>();
    for ( int axesIndex = axes.size() - 1; axesIndex >= startAxis; axesIndex -= 1 ) {
      final CellSetAxis axis = axes.get( axesIndex );
      final List<Position> positions = axis.getPositions();

      final MemberAddingStrategy strategy = membersOnAxisSorted ?
        new SortedMemberAddingStrategy( positions ) :
        new ResultSetOrderMemberAddingStrategy();

      for ( int positionsIndex = 0; positionsIndex < positions.size(); positionsIndex++ ) {
        final List<Member> position = positions.get( positionsIndex ).getMembers();
        for ( int positionIndex = 0; positionIndex < position.size(); positionIndex++ ) {
          Member m = position.get( positionIndex );
          computeDeepColumnNames( m, strategy );
        }
      }

      Collection<Member> columnNamesSet = strategy.values();
      if ( columnNamesSet.size() != axesMembers[ axesIndex ] ) {
        logger.error( "ERROR: Number of names is not equal the pre-counted number." );
      }

      columnToMemberMapper.addAll( columnNamesSet );
    }
    return columnToMemberMapper;
  }


  /**
   * Computes a set of column names starting with the deepest parent up to the member actually found on the axis.
   *
   * @param m
   */
  public static void computeDeepColumnNames( Member m,
                                             final MemberAddingStrategy memberToNameMapping ) {
    final FastStack<Member> memberStack = new FastStack<Member>();
    while ( m != null ) {
      memberStack.push( m );
      m = m.getParentMember();
    }

    while ( memberStack.isEmpty() == false ) {
      Member mx = memberStack.pop();
      memberToNameMapping.add( mx );
    }
  }

  /**
   * Column axis members can be nested (having multiple dimensions or multiple levels of the same dimension) and thus
   * the Member's unique name is not necessarily unique across the whole context (same year mentioned for different
   * product lines, for example). So we need to compute that name recursively.
   *
   * @param p The OLAP position, a list of members uniquely specifying a cell-position.
   * @return the computed name, usually jus a concat of all levels.
   */
  public static String computeUniqueColumnName( final Position p ) {
    final StringBuilder positionName = new StringBuilder( 100 );
    final List<Member> position = p.getMembers();
    for ( int j = 0; j < position.size(); j++ ) {
      if ( j != 0 ) {
        positionName.append( '/' );
      }
      final Member member = position.get( j );
      positionName.append( Olap4jUtil.getUniqueMemberName( member ) );
    }
    return positionName.toString();
  }
}
