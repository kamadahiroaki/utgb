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
// SAMRead.java
// Since: 2010/03/15
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.gwt.utgb.client.bio;

import org.utgenome.gwt.utgb.client.util.Properties;

/**
 * Genome Read data
 * 
 * @author yoshimura
 * 
 */
public class SAMRead extends Interval {
	private static final long serialVersionUID = 1L;

	//schema record(qname, flag, rname, start, end, mapq, cigar, mrnm, mpos, isize, seq, qual, tag*)
	public String qname;
	public int flag;
	public String rname;
	// left-most position on the reference sequence
	//public int start; 
	//public int end;
	public int mapq; // Mapping Quality
	public String cigar;
	public String mrnm; // mate reference name
	public int mStart; // mate start (new parameter!) 
	public int iSize;
	public String seq;
	public String qual;
	public Properties tag;

	public String refSeq;

	public SAMRead() {
		super();
	}

	public SAMRead(int start, int end) {
		super(start, end);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("flag=" + flag);
		sb.append(", mapq=" + mapq);
		sb.append(", cigar=" + cigar);
		sb.append(", iSize=" + iSize);
		sb.append(", qual=\"" + qual + "\"");
		sb.append(", tag=" + tag);

		return sb.toString();
	}

	@Override
	public String getName() {
		return qname;
	}

	public boolean isSense() {
		return (flag & SAMReadFlag.FLAG_STRAND_OF_QUERY) == 0;
	}

	public boolean isAntiSense() {
		return (flag & SAMReadFlag.FLAG_STRAND_OF_QUERY) == 1;
	}

	@Override
	public void accept(OnGenomeDataVisitor visitor) {
		visitor.visitSAMRead(this);
	}

	public boolean isPairedRead() {
		return (flag & SAMReadFlag.FLAG_PAIRED_READ) == 1;
	}

}
