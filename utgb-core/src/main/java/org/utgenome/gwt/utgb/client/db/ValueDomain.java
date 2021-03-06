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
// ValueDomain.java
// Since: 2007/04/03
//
// $Date$
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.gwt.utgb.client.db;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * A ValueDomain shows a set of possible values that can be used within a DataType in a Relation.
 * 
 * @author leo
 * 
 */
public class ValueDomain implements IsSerializable {
	/**
	 * The following line specifies the element type of the List, that is, org.utgenome.gwt.web.client.db.Value. This
	 * information is requried in the GWT compiler.
	 * 
	 */
	List<Value> valueList = new ArrayList<Value>();

	public ValueDomain() {
	}

	public ValueDomain(List<Value> valueList) {
		this.valueList = valueList;
	}

	public List<Value> getValueList() {
		return valueList;
	}

	public void setValueList(List<Value> valueList) {
		this.valueList = valueList;
	}

	public void addValueList(Value value) {
		valueList.add(value);
	}

	public static ValueDomain createNewValueDomain(String[] value) {
		ValueDomain vd = new ValueDomain();
		if (value == null)
			return vd;
		for (String each : value) {
			vd.addValueList(new Value(each));
		}
		return vd;
	}

}
