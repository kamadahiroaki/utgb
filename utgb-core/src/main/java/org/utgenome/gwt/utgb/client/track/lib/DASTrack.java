/*--------------------------------------------------------------------------
 *  Copyright 2009 utgenome.org
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
// utgb-core Project
//
// GenomeTrack.java
// Since: Feb 17, 2009
//
// $URL: http://svn.utgenome.org/utgb/trunk/utgb/utgb-core/src/main/java/org/utgenome/gwt/utgb/client/track/lib/GenomeTrack.java $ 
// $Author: yoshimura $
//--------------------------------------
package org.utgenome.gwt.utgb.client.track.lib;

import org.utgenome.gwt.utgb.client.bio.DASLocation;
import org.utgenome.gwt.utgb.client.bio.GenomeDB;
import org.utgenome.gwt.utgb.client.db.datatype.StringType;
import org.utgenome.gwt.utgb.client.track.Track;
import org.utgenome.gwt.utgb.client.track.TrackConfigChange;
import org.utgenome.gwt.utgb.client.track.TrackFrame;
import org.utgenome.gwt.utgb.client.track.TrackGroup;
import org.utgenome.gwt.utgb.client.util.Properties;

/**
 * DASTrack is for visualizing data that can be located on das data.
 * 
 * @author yoshimura
 * 
 */
public class DASTrack extends ReadTrack {

	private String dasBaseURL = null;
	private String dasType = null;

	public static TrackFactory factory() {
		return new TrackFactory() {
			@Override
			public Track newInstance() {
				return new DASTrack();
			}
		};
	}

	public DASTrack() {
		super("DAS Track", "DAS");
	}

	@Override
	public void setUp(TrackFrame trackFrame, TrackGroup group) {
		super.setUp(trackFrame, group);

		config.addConfigParameter("DAS Data Type", new StringType("dasType"), dasType);
	}

	@Override
	public void onChangeTrackConfig(TrackConfigChange change) {
		super.onChangeTrackConfig(change);

		if (change.contains("dasType")) {
			dasType = change.getValue("dasType");
			refresh();
		}
	}

	@Override
	public GenomeDB getGenomeDB() {
		return new DASLocation(super.getGenomeDB(), dasType);
	}

	@Override
	public void saveProperties(Properties saveData) {
		super.saveProperties(saveData);
		if (dasType != null) {
			saveData.add("dasType", dasType);
		}
	}

	@Override
	public void restoreProperties(Properties properties) {
		super.restoreProperties(properties);
		dasType = properties.get("dasType", dasType);
	}
}
