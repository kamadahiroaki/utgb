/*--------------------------------------------------------------------------
 *  Copyright 2010 utgenome.org
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
// AXTAlignmentTest.java
// Since: 2010/12/07
//
//--------------------------------------
package org.utgenome.format.axt;

import org.junit.Ignore;
import org.junit.Test;
import org.xerial.util.FileResource;

public class AXTAlignmentTest {

	@Ignore
	@Test
	public void toAXT() throws Exception {

		for (AXTAlignment each : AXTLens.lens(FileResource.open(AXTAlignmentTest.class, "sample.axt"))) {

		}

	}

}
