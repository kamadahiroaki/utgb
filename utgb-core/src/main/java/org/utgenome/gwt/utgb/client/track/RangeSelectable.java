/*--------------------------------------------------------------------------
 *  Copyright 2007 utgenome.org
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *--------------------------------------------------------------------------*/
//--------------------------------------
// GenomeBrowser Project
//
// RangeSelectable.java
// Since: Jun 13, 2007
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.gwt.utgb.client.track;

import org.utgenome.gwt.utgb.client.track.lib.RulerTrack;
import org.utgenome.gwt.utgb.client.track.lib.SequenceRulerTrack;
import org.utgenome.gwt.utgb.client.ui.AbsoluteFocusPanel;


/**
 * An interface for tracks supporting range selection.
 * See also {@link TrackRangeSelector} to enable mouse click range selection on your tracks.
 * 
 * {@link SequenceRulerTrack} and {@link RulerTrack} are the examples of using 
 * {@link RangeSelectable} interface and {@link TrackRangeSelector}.  
 * 
 * @author leo
 *
 */
public interface RangeSelectable
{
    public void onRangeSelect(int x1OnTrackWindow, int x2OnTrackWindow);
    public AbsoluteFocusPanel getAbsoluteFocusPanel();
    
}




