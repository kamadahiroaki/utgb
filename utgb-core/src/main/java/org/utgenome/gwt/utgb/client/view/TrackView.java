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
// TrackView.java
// Since: 2009/11/27
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.gwt.utgb.client.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class TrackView implements Serializable {

	private static final long serialVersionUID = 1L;

	public String species;
	public String revision;
	public String target;
	public Window window;
	public ArrayList<Track> track = new ArrayList<Track>();

	public TrackView() {
	}

	public static class Track implements Serializable {
		private static final long serialVersionUID = 1L;

		public String name;
		public int height;
		public boolean pack;
		public String javaClass;
		public HashMap<String, String> properties = new HashMap<String, String>();

		public String put(String key, String value) {
			return properties.put(key, value);
		}
	}

	public static class Window implements Serializable {

		private static final long serialVersionUID = 1L;

		public long start;
		public long end;
		public int width;
	}

}