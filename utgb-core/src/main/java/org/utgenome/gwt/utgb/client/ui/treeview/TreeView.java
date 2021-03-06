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
// TreeView.java
// Since: 2007/05/28
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.gwt.utgb.client.ui.treeview;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TreeView extends Composite 
{
	VerticalPanel _displayPanel = new VerticalPanel();
	TreePanel _rootPanel = new TreePanel();
	
	public TreeView()
	{
		initWidget(_displayPanel);
	}
	
	public TreePanel getRoot()
	{
		return _rootPanel;
	}

	public void layout() {
		
		_displayPanel.add(new Label("root"));
		_rootPanel.layout(_displayPanel);
		
	}
	
	
}

