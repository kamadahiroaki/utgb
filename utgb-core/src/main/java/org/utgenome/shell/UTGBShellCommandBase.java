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
// UTGBShellCommandBase.java
// Since: 2011/03/23
//
//--------------------------------------
package org.utgenome.shell;

import org.utgenome.core.cui.UTGBCommandBase;
import org.utgenome.shell.UTGBShell.UTGBShellOption;
import org.xerial.util.opt.GlobalCommandOption;

public abstract class UTGBShellCommandBase extends UTGBCommandBase {

	protected UTGBShellOption globalOption = new UTGBShellOption();

	@Override
	public void execute(GlobalCommandOption opt, String[] args) throws Exception {
		if (UTGBShellOption.class.isAssignableFrom(opt.getClass())) {
			this.globalOption = UTGBShellOption.class.cast(opt);
		}

		execute(args);
	}

}
