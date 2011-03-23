/*--------------------------------------------------------------------------
 *  Copyright 2011 utgenome.org
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
// Convert.java
// Since: 2011/03/22
//
//--------------------------------------
package org.utgenome.core.cui;

import java.net.URL;

import org.xerial.util.log.Logger;

public class Convert extends UTGBCommandBase {

	private static Logger _logger = Logger.getLogger(Convert.class);

	@Override
	public String name() {
		return "convert";
	}

	@Override
	public String getOneLineDescription() {
		return "text format converter";
	}

	@Override
	public Object getOptionHolder() {
		return this;
	}

	@Override
	public URL getHelpMessageResource() {
		return null;
	}

	@Override
	public void execute(String[] args) throws Exception {
		_logger.info("convert");

	}

}
