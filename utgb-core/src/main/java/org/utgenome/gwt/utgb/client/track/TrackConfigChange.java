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
// TrackConfigChange.java
// Since: Jun 20, 2007
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.gwt.utgb.client.track;

import java.util.HashSet;

/**
 * Change notifier of {@link TrackConfig}
 * @author leo
 *
 */
public interface TrackConfigChange {
	public boolean contains(String configParameterName);
	public boolean containsOneOf(String[] configParameterName);
	
	public String getValue(String configParamName);
	public int getIntValue(String configParamName);
	public float getFloatValue(String configParamName);
	public boolean getBoolValue(String configParamName);
	public HashSet<String> getChangedParamSet();
}




