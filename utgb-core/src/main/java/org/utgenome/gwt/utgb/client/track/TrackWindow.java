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
// TrackWindow.java
// Since: Jun 12, 2007
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.gwt.utgb.client.track;

import org.utgenome.gwt.utgb.client.bio.OnGenome;

/**
 * {@link TrackWindow} manages a track-window size and a region on the genome displayed in the window. An instance of
 * TrackWindow is immutable, so you can safely copy a reference of TrackWindow to preserve a current track window state.
 * 
 * @author leo
 * 
 */
public interface TrackWindow {
	/**
	 * calculate the X position (pixel address) in a track window of a given index on genome.
	 * 
	 * @param indexOnGenome
	 * @return relative X position in a window (0 origin).
	 */
	public int convertToPixelX(int indexOnGenome);

	/**
	 * @param xOnWindow
	 *            calculate the genome index of a given x position in the current window
	 * @return genome position
	 */
	public int convertToGenomePosition(int xOnWindow);

	/**
	 * @return pixel length / (genome end - genome start);
	 */
	public double getPixelLengthPerBase();

	/**
	 * @return the window size
	 */
	public int getPixelWidth();

	/**
	 * @return the sequence width
	 */
	public int getSequenceLength();

	/**
	 * @return start position on the genome currently displayed in the window
	 */
	public int getStartOnGenome();

	/**
	 * @return end position on the genome currently displayed in the window
	 */
	public int getEndOnGenome();

	public boolean equals(TrackWindow window);

	public boolean sameRangeWith(TrackWindow window);

	public boolean isReverseStrand();

	public TrackWindow newWindow(int newStartOnGenome, int newEndOnGenome);

	public TrackWindow newPixelWidthWindow(int pixelSize);

	public boolean hasOverlapWith(OnGenome g);

	public boolean hasSameScale(TrackWindow other);

	public TrackWindow mask(TrackWindow mask);
}
