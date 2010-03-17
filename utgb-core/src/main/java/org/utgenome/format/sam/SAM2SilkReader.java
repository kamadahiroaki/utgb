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
// SAM2SilkReader.java
// Since: Mar 15, 2010
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.format.sam;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.samtools.SAMFileReader;
import net.sf.samtools.SAMRecord;
import net.sf.samtools.SAMRecord.SAMTagAndValue;
import net.sf.samtools.util.CloseableIterator;

import org.apache.tools.ant.util.ReaderInputStream;
import org.xerial.silk.SilkWriter;
import org.xerial.util.log.Logger;

public class SAM2SilkReader extends Reader {

	private static Logger _logger = Logger.getLogger(SAM2SilkReader.class);

	private ExecutorService threadPool;

	private final InputStream input;
	private final PipedWriter pipeOut;
	private final PipedReader pipeIn;

	private boolean hasStarted = false;

	public SAM2SilkReader(InputStream input) throws IOException {
		this.input = input;
		this.pipeOut = new PipedWriter();
		this.pipeIn = new PipedReader(pipeOut);
	}

	public SAM2SilkReader(Reader input) throws IOException {
		this(new ReaderInputStream(input));
	}

	private static class PipeWorker implements Runnable {

		private final SAMFileReader samReader;
		private final PrintWriter out;

		public PipeWorker(InputStream in, PrintWriter out) throws IOException {
			samReader = new SAMFileReader(in);
			this.out = out;
		}

		public void run() {
			if (out == null)
				return;
			SilkWriter w = new SilkWriter(out);
			w.preamble();
			w.preamble("schema record(qname, flag, rname, start, end, mapq, cigar, mrnm, mpos, isize, seq, qual, tag, vtype, tag*)");
			for (CloseableIterator<SAMRecord> it = samReader.iterator(); it.hasNext();) {
				SAMRecord rec = it.next();
				SilkWriter rw = w.node("record");
				rw.leaf("qname", rec.getReadName());
				rw.leaf("flag", rec.getFlags());
				rw.leaf("rname", rec.getReferenceName());
				rw.leaf("start", rec.getAlignmentStart());
				rw.leaf("end", rec.getAlignmentEnd());
				rw.leaf("mapq", rec.getMappingQuality());
				rw.leaf("cigar", rec.getCigarString());
				rw.leaf("mrname", rec.getMateReferenceName());
				rw.leaf("mpos", rec.getMateAlignmentStart());
				rw.leaf("isize", rec.getInferredInsertSize());
				rw.leaf("seq", rec.getReadString());
				rw.leaf("qual", rec.getBaseQualityString());
				SilkWriter tw = rw.node("tag");
				for (SAMTagAndValue each : rec.getAttributes()) {
					tw.leaf(each.tag, each.value);
				}
			}
			out.close();

		}

	}

	public void close() throws IOException {
		pipeIn.close();
		input.close();
	}

	public int read(char[] cbuf, int off, int len) throws IOException {

		if (!hasStarted) {
			threadPool = Executors.newFixedThreadPool(1);
			threadPool.submit(new PipeWorker(input, new PrintWriter(pipeOut)));
			hasStarted = true;
		}

		int ret = pipeIn.read(cbuf, off, len);

		if (ret == -1) {
			threadPool.shutdownNow();
		}
		return ret;
	}

}