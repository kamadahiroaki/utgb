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
// LocusClickHandler.java
// Since: 2009/10/14
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.gwt.utgb.client.canvas;

import org.utgenome.gwt.utgb.client.bio.GenomeRange;

public interface LocusClickHandler {

	public void onClick(int clientX, int clientY, GenomeRange locus);
}
