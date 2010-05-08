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
// GenomeBrowser Project
//
// BrowserService.java
// Since: Apr 20, 2007
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.gwt.utgb.client;

import java.util.List;

import org.utgenome.gwt.utgb.client.bean.DatabaseEntry;
import org.utgenome.gwt.utgb.client.bean.track.TrackDescription;
import org.utgenome.gwt.utgb.client.bio.AlignmentResult;
import org.utgenome.gwt.utgb.client.bio.ChrLoc;
import org.utgenome.gwt.utgb.client.bio.ChrRange;
import org.utgenome.gwt.utgb.client.bio.Gene;
import org.utgenome.gwt.utgb.client.bio.Locus;
import org.utgenome.gwt.utgb.client.bio.SAMRead;
import org.utgenome.gwt.utgb.client.bio.WigGraphData;
import org.utgenome.gwt.utgb.client.track.bean.SearchResult;
import org.utgenome.gwt.utgb.client.track.bean.TrackBean;
import org.utgenome.gwt.utgb.client.view.TrackView;

import com.google.gwt.rpc.client.RpcService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service")
public interface BrowserService extends RpcService {

	public TrackView getTrackView(String viewName) throws UTGBClientException;

	public String getHTTPContent(String url);

	public TrackDescription getTrackDescription(String url);

	public String getDatabaseCatalog(String jdbcAddress);

	public String getTableData(String jdbcAddress, String tableName);

	public List<TrackBean> getTrackList(int entriesPerPage, int page);

	public int numHitsOfTracks(String prefix);

	/**
	 */
	public List<TrackBean> getTrackList(String prefix, int entriesPerPage, int page);

	public SearchResult keywordSearch(String species, String revision, String keyword, int entriesPerPage, int page);

	/**
	 * Retrieves the gene list by accessing the specified URI
	 * 
	 * @param serviceURI
	 * @return
	 */
	public List<Gene> getGeneList(String serviceURI);

	public AlignmentResult getAlignment(String serviceURI, String target, String sequence);

	public ChrRange getChrRegion(String species, String revision);

	public List<Locus> getLocusList(String dbGroup, String dbName, ChrLoc location);

	public List<String> getChildDBGroups(String parentDBGroup);

	public List<String> getDBNames(String dbGroup);

	public List<DatabaseEntry> getDBEntry(String dbGroup);

	public List<WigGraphData> getWigDataList(String fileName, int windowWidth, ChrLoc location);

	public List<SAMRead> getSAMReadList(String readFileName, String refSeqFileName);

	public List<Gene> getBEDEntryList(String bedPath, ChrLoc location);

	public List<SAMRead> querySAMReadList(String bamFileName, String indexFileName, String refSeqFileName, String rname, int start, int end);

	public String getRefSeq(String refSeqFileName, String rname, int start, int end);
}
