/*--------------------------------------------------------------------------
 *  Copyright 2007 utgenome.org
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
// UTGB Common Project
//
// InputForm.java
// Since: 2007/04/13
//
// $Date$
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.gwt.utgb.client.db.datatype;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Composite;

/**
 * {@link InputForm} is an interface of input forms of various data types.
 * 
 * @author leo
 * 
 */
public abstract class InputForm extends Composite {
	public abstract String getUserInput();

	public abstract void setValue(String value);

	public abstract JSONValue getJSONValue();

	public abstract void addKeyPressHandler(KeyPressHandler listener);

	public abstract void addChangeHandler(ChangeHandler listener);
}