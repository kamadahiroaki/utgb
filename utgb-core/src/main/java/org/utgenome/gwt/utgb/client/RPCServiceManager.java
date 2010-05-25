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
// RPCServiceManager.java
// Since: 2010/05/23
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.gwt.utgb.client;

import com.google.gwt.core.client.GWT;

public class RPCServiceManager {

	private static BrowserServiceAsync _service = null;

	public static void initServices() {
		// set up an access interface to the web service
		_service = (BrowserServiceAsync) GWT.create(BrowserService.class);
	}

	public static BrowserServiceAsync getRPCService() {
		if (_service == null)
			throw new IllegalStateException("BrowserService is not initialized");
		return _service;
	}

}