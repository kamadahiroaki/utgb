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
// UTGB Common Project
//
// FASTASequence.java
// Since: Jun 4, 2007
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.format.fasta;

import java.util.ArrayList;

/**
 * A FASTASequence represente a single entry in a FASTA file
 * 
 * @author leo
 * 
 */
public class FASTASequence {
	private String sequence = "";
	private ArrayList<String> description = new ArrayList<String>();
	private String descriptionLine;

	/**
	 * @param _sequence
	 * @param _description
	 */
	public FASTASequence(String rawDescription, String sequence) {
		this.descriptionLine = rawDescription;
		for (String d : rawDescription.trim().split("\\|"))
			description.add(d);

		this.sequence = sequence;
	}

	public FASTASequence() {
	}

	public ArrayList<String> getDescription() {
		return description;
	}

	public String getDescriptionLine() {
		return descriptionLine;
	}

	public int getDescriptionSize() {
		return description.size();
	}

	public String getDescription(int index) {
		return description.get(index);
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public void addDescription(String desc) {
		description.add(desc);
	}

	@Override
	public String toString() {
		return description.toString() + ":" + sequence;
	}

	public String getSequenceName() {
		return CompactFASTA.pickSequenceName(descriptionLine);
	}
}
