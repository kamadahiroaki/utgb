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
// KeywordAliasReaderTest.java
// Since: May 20, 2010
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.format.keyword;

import org.junit.Test;
import org.utgenome.format.keyword.GenomeKeywordEntry.KeywordAlias;
import org.xerial.lens.Lens;
import org.xerial.util.FileResource;
import org.xerial.util.log.Logger;

public class KeywordAliasReaderTest {

	private static Logger _logger = Logger.getLogger(KeywordAliasReaderTest.class);

	@Test
	public void read() throws Exception {

		KeywordAliasReader r = new KeywordAliasReader(FileResource.open(KeywordAliasReaderTest.class, "alias-sample.txt"));
		KeywordAlias ka;
		while ((ka = r.next()) != null) {
			_logger.info(Lens.toSilk(ka));
		}
	}

}
