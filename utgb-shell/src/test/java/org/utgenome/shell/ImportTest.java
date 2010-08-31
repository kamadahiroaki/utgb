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
// ImportTest.java
// Since: Apr 27, 2009
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.shell;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xerial.util.FileResource;
import org.xerial.util.FileUtil;

public class ImportTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	@Test
	public void importTest() throws Exception {
		UTGBShell.runCommand(new String[] { "import", "f:/cygwin/home/leo/work/t2k/scaffold5001.silk" });
	}

	@Test
	public void auto() throws Exception {

		assertEquals(Import.FileType.WIG, Import.detectFileType("sample.wig"));
		assertEquals(Import.FileType.FASTA, Import.detectFileType("hg19.fa"));
		assertEquals(Import.FileType.FASTA, Import.detectFileType("sample.fasta"));
		assertEquals(Import.FileType.BED, Import.detectFileType("sample.bed"));
		assertEquals(Import.FileType.AUTO, Import.detectFileType("sample.fdasfa.dfa"));
		assertEquals(Import.FileType.SAM, Import.detectFileType("sample.sam"));
	}

	@Test
	public void importSAM() throws Exception {

		File tmpSAM = FileUtil.createTempFile(new File("target"), "sample", ".sam");
		tmpSAM.deleteOnExit();
		File bam = new File(tmpSAM.getPath().replace(".sam", ".bam"));
		File bai = new File(bam.getPath() + ".bai");
		bam.deleteOnExit();
		bai.deleteOnExit();

		FileUtil.copy(FileResource.openByteStream(ImportTest.class, "sample.sam"), tmpSAM);
		// File tmpBAM = FileUtil.createTempFile(new File("target"), "sample", ".bam");
		UTGBShell.runCommand(new String[] { "import", "-w", tmpSAM.getAbsolutePath() });

		assertTrue(bam.exists());
		assertTrue(bai.exists());

	}

	@Test
	public void importPaddedSAMReads() throws Exception {

		File tmpSAM = FileUtil.createTempFile(new File("target"), "test", ".sam");
		tmpSAM.deleteOnExit();
		File bam = new File(tmpSAM.getPath().replace(".sam", ".bam"));
		File bai = new File(bam.getPath() + ".bai");
		bam.deleteOnExit();
		bai.deleteOnExit();

		FileUtil.copy(FileResource.openByteStream(ImportTest.class, "test.sam"), tmpSAM);
		// File tmpBAM = FileUtil.createTempFile(new File("target"), "sample", ".bam");
		UTGBShell.runCommand(new String[] { "import", "-w", tmpSAM.getAbsolutePath() });

		assertTrue(bam.exists());
		assertTrue(bai.exists());

	}

}
