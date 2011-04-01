/*--------------------------------------------------------------------------
 *  Copyright 2008 utgenome.org
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
// tss-toolkit Project
//
// AlignmentStat.java
// Since: 2011/02/27
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.core.cui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.samtools.SAMFileReader;
import net.sf.samtools.SAMFileReader.ValidationStringency;
import net.sf.samtools.SAMRecord;
import net.sf.samtools.SAMRecordIterator;

import org.utgenome.shell.UTGBShellException;
import org.xerial.lens.SilkLens;
import org.xerial.util.log.Logger;
import org.xerial.util.opt.Argument;

public class ReadStat extends UTGBCommandBase {

	@Override
	public String name() {
		return "read-stat";
	}

	@Override
	public String getOneLineDescription() {
		return "compute a statistics of the read alignment results";
	}

	@Override
	public Object getOptionHolder() {
		return this;
	}

	@Argument
	private List<String> bamFiles = new ArrayList<String>();

	public static class ReadAlignmentStat {
		public List<String> bamFile = new ArrayList<String>();

		public long numRead = 0;
		public long numMapped = 0;
		public long numUnmapped = 0;
		public long numUnique = 0;
		public long numRepeat = 0;
		public long numAlignedWithSW = 0;
		public long numMateIsUnmapped = 0;

		public ReadAlignmentStat() {
		}

		public ReadAlignmentStat(String bamFile) {
			this.bamFile.add(bamFile);
		}

		public String getMappedRate() {
			return String.format("%.2f%%", (double) numMapped / numRead * 100.0);
		}

		public String getUniquelyMapppedRate() {
			return String.format("%.2f%%", (double) numUnique / numMapped * 100.0);
		}

		public String getRepeatMappedRate() {
			return String.format("%.2f%%", (double) numRepeat / numMapped * 100.0);
		}

		public void add(ReadAlignmentStat other) {
			bamFile.addAll(other.bamFile);
			numRead += other.numRead;
			numMapped += other.numMapped;
			numUnmapped += other.numUnmapped;
			numUnique += other.numUnique;
			numRepeat += other.numRepeat;
			numAlignedWithSW += other.numAlignedWithSW;
			numMateIsUnmapped += other.numMateIsUnmapped;
		}

	}

	private static Logger _logger = Logger.getLogger(ReadStat.class);

	@Override
	public void execute(String[] args) throws Exception {

		SAMFileReader.setDefaultValidationStringency(ValidationStringency.SILENT);
		if (bamFiles.isEmpty())
			throw new UTGBShellException("no input BAM files");

		ReadAlignmentStat totalStat = new ReadAlignmentStat();

		for (String bamFile : bamFiles) {
			ReadAlignmentStat stat = new ReadAlignmentStat(bamFile);

			SAMFileReader in = new SAMFileReader(new File(bamFile), false);
			_logger.info("reading " + bamFile);
			SAMRecordIterator it = in.iterator();

			try {
				for (; it.hasNext();) {
					stat.numRead++;

					if (stat.numRead % 1000000 == 0) {
						_logger.info(String.format("%,d reads processed", stat.numRead));
					}

					SAMRecord read = it.next();
					if (read.getReadUnmappedFlag()) {
						stat.numUnmapped++;
					}
					else {
						stat.numMapped++;
					}

					Object XT = read.getAttribute("XT");
					if (XT != null && Character.class.isAssignableFrom(XT.getClass())) {
						char XTtag = Character.class.cast(XT);
						switch (XTtag) {
						case 'U':
							stat.numUnique++;
							break;
						case 'R':
							stat.numRepeat++;
							break;
						case 'M':
							stat.numAlignedWithSW++;
							break;
						}
					}

					if (read.getReadPairedFlag()) {
						if (read.getMateUnmappedFlag()) {
							stat.numMateIsUnmapped++;
						}
					}
				}
			}
			finally {
				it.close();
				in.close();
			}
			System.out.println(SilkLens.toSilk("read stat", stat));

			totalStat.add(stat);
		}

		// output global stat
		if (bamFiles.size() > 1)
			System.out.println(SilkLens.toSilk("read stat total", totalStat));

	}

}
