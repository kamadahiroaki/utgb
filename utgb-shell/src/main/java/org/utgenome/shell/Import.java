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
// utgb-shell Project
//
// Import.java
// Since: Jan 20, 2009
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

import net.sf.samtools.SAMFileHeader;
import net.sf.samtools.SAMFileHeader.SortOrder;
import net.sf.samtools.SAMFileReader;
import net.sf.samtools.SAMFileReader.ValidationStringency;
import net.sf.samtools.SAMFileWriter;
import net.sf.samtools.SAMFileWriterFactory;
import net.sf.samtools.SAMRecord;

import org.utgenome.format.bed.BEDDatabase;
import org.utgenome.format.fasta.FASTADatabase;
import org.utgenome.format.silk.read.ReadDBBuilder;
import org.utgenome.format.wig.WIGDatabaseGenerator;
import org.utgenome.shell.util.ReaderInputStream;
import org.xerial.util.log.Logger;
import org.xerial.util.opt.Argument;
import org.xerial.util.opt.Option;

/**
 * import command
 * 
 * @author leo
 * 
 */
// @Usage(command = "> utgb import", description = "import command")
public class Import extends UTGBShellCommand {

	private static Logger _logger = Logger.getLogger(Import.class);

	public static enum FileType {
		AUTO, READ, BED, SAM, FASTA, WIG, KTAB, UNKNOWN, BAM
	}

	@Option(symbol = "t", longName = "type", description = "specify the input file type: (AUTO, FASTA, READ, BED, WIG)")
	private FileType fileType = FileType.AUTO;

	@Argument(index = 0, required = false)
	private String inputFilePath = null;

	@Option(symbol = "d", description = "output directory. default = db")
	private String outDir = "db";

	@Option(symbol = "o", longName = "output", varName = "DB FILE NAME", description = "output SQLite DB file name")
	private String outputFileName;

	@Option(symbol = "n", description = "do not overwrite existing DB files (default = false)")
	private boolean doNotOverwriteDB = false;

	@Override
	public void execute(String[] args) throws Exception {

		File input = null;

		Reader in = null;
		if (inputFilePath == null) {
			_logger.info("use STDIN for the input");
			in = new InputStreamReader(System.in);
		}
		else {
			_logger.info("input file: " + inputFilePath);
			input = new File(inputFilePath);
			if (!input.exists())
				throw new UTGBShellException("file not found: " + inputFilePath);

			in = new BufferedReader(new FileReader(input));
		}

		if (fileType == FileType.AUTO)
			fileType = detectFileType(inputFilePath);
		_logger.info("file type: " + fileType);

		if (outputFileName == null) {
			// new File("db").mkdirs();

			String inputName = inputFilePath == null ? "out" : inputFilePath;

			if (fileType == FileType.SAM) {
				outputFileName = org.xerial.util.FileType.replaceFileExt(inputName, "bam");
			}
			else {
				outputFileName = String.format("%s.sqlite", inputName);
			}
			int count = 1;
			if (doNotOverwriteDB) {
				while (new File(outputFileName).exists()) {
					if (fileType == FileType.SAM) {
						outputFileName = org.xerial.util.FileType.replaceFileExt(inputName, String.format("%d.bam", count));
					}
					else {
						outputFileName = String.format("%s.%d.sqlite", inputName, count);
					}
					count++;
				}
			}

		}
		_logger.info("output file: " + outputFileName);

		switch (fileType) {
		case READ: {
			ReadDBBuilder builder = new ReadDBBuilder(outputFileName);
			builder.build(in);
			break;
		}
		case BED: {
			BEDDatabase.toSQLiteDB(in, outputFileName);
			break;
		}
		case FASTA:
			if (input != null)
				FASTADatabase.main(new String[] { inputFilePath, "-o", outputFileName });
			else
				FASTADatabase.main(new String[] { "-o", outputFileName });
			break;
		case WIG:
			new WIGDatabaseGenerator().toSQLiteDB(in, outputFileName);
			break;
		case SAM: {
			_logger.info("creating a BAM file from the input SAM.");
			SAMFileReader reader = new SAMFileReader(new ReaderInputStream(in));
			reader.setValidationStringency(ValidationStringency.SILENT);

			String bamOut = outputFileName;
			if (!bamOut.endsWith(".bam"))
				bamOut += ".bam";
			_logger.info("output BAM: " + bamOut);

			SAMFileHeader header = reader.getFileHeader();
			int nRefs = header.getSequenceDictionary().size();
			SortOrder sortOrder = header.getSortOrder();
			boolean sorted = false;
			switch (sortOrder) {
			case coordinate:
				sorted = true;
				break;
			default:
				sorted = false;
				break;
			}

			SAMFileWriterFactory fac = new SAMFileWriterFactory();
			// create .bai (BAM index) file
			fac.setCreateIndex(true);
			header.setSortOrder(SortOrder.coordinate);
			final SAMFileWriter writer = fac.makeBAMWriter(header, sorted, new File(bamOut));
			final Iterator<SAMRecord> iterator = reader.iterator();
			while (iterator.hasNext()) {
				writer.addAlignment(iterator.next());
			}
			reader.close();
			writer.close();

			_logger.info("done.");

		}
			break;
		case UNKNOWN:
		default: {
			_logger.warn("specify the input file type with -t option. Type utgb import --help to see the list of the supported file types");
			break;
		}
		}

	}

	public static FileType detectFileType(String fileName) {
		if (fileName == null)
			return FileType.UNKNOWN;

		if (fileName.endsWith(".fa") || fileName.endsWith(".fasta"))
			return FileType.FASTA;
		else if (fileName.endsWith(".bed"))
			return FileType.BED;
		else if (fileName.endsWith(".wig"))
			return FileType.WIG;
		else if (fileName.endsWith(".sam"))
			return FileType.SAM;
		else if (fileName.endsWith(".bam"))
			return FileType.BAM;
		else if (fileName.endsWith(".ktab"))
			return FileType.KTAB;

		return FileType.AUTO;
	}

	@Override
	public String name() {
		return "import";
	}

	@Override
	public String getOneLinerDescription() {
		return "import a file and create a new database";
	}

}
