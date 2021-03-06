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
// CIGARString.java
// Since: Mar 15, 2010
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.gwt.utgb.client.bio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.utgenome.gwt.utgb.client.UTGBClientErrorCode;
import org.utgenome.gwt.utgb.client.UTGBClientException;

/**
 * CIGAR string (in SAM format) management utility
 * 
 * @author leo
 * 
 */
public class CIGAR implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum Type implements Serializable {
		Matches("M"), Insertions("I"), Deletions("D"), SkippedRegion("N"), SoftClip("S"), HardClip("H"), Padding("P");
		public final String shortName;

		private Type(String shortName) {
			this.shortName = shortName;
		}

		public static Type convert(char c) throws UTGBClientException {
			switch (c) {
			case 'M':
				return Type.Matches;
			case 'I':
				return Type.Insertions;
			case 'D':
				return Type.Deletions;
			case 'N':
				return Type.SkippedRegion;
			case 'S':
				return Type.SoftClip;
			case 'H':
				return Type.HardClip;
			case 'P':
				return Type.Padding;
			default:
				throw new UTGBClientException(UTGBClientErrorCode.PARSE_ERROR, "unknown CIGAR type: " + c);
			}
		}

		@Override
		public String toString() {
			return shortName;
		}

	}

	public static class Element implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public Type type = Type.Matches;
		public int length;

		public Element() {

		}

		public Element(Type type, int length) {
			this.type = type;
			this.length = length;
		}

		@Override
		public String toString() {
			return length + ":" + type;
		}

	}

	private final ArrayList<Element> cigar;

	/**
	 * Creates an empty CIGAR
	 */
	public CIGAR() {
		cigar = new ArrayList<Element>();
	}

	public CIGAR(String cigarString) throws UTGBClientException {
		this.cigar = parse(cigarString);
	}

	private CIGAR(ArrayList<Element> cigar) {
		this.cigar = cigar;
	}

	public void add(int length, Type type) {
		cigar.add(new Element(type, length));
	}

	/**
	 * Return the number of CIGAR elements
	 * 
	 * @return
	 */
	public int size() {
		return cigar.size();
	}

	public Element get(int index) {
		return cigar.get(index);
	}

	public List<Element> element() {
		return cigar;
	}

	public String toCIGARString() {
		StringBuilder buf = new StringBuilder();
		for (Element each : cigar) {
			buf.append(each.length + each.type.shortName);
		}
		return buf.toString();
	}

	@Override
	public String toString() {
		return toCIGARString();
	}

	private static CIGAR parseCIGAR(String cigar) throws UTGBClientException {
		return new CIGAR(parse(cigar));
	}

	private static ArrayList<Element> parse(String cigarString) throws UTGBClientException {

		ArrayList<Element> result = new ArrayList<Element>();
		int startIndexOfNumber = 0;
		for (int cursor = 0; cursor < cigarString.length(); cursor++) {
			char c = cigarString.charAt(cursor);
			if (c >= '0' && c <= '9')
				continue;
			else {
				if (startIndexOfNumber == cursor)
					break; // not a CIGAR string, ignoring the error
				int len = Integer.parseInt(cigarString.substring(startIndexOfNumber, cursor));
				Type t = Type.convert(cigarString.charAt(cursor));
				result.add(new Element(t, len));

				startIndexOfNumber = cursor + 1;
			}
		}

		return result;
	}

}
