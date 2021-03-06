//--------------------------------------
//
// UsingTemplate.java
// Since: 2012/03/02
//
//--------------------------------------
package org.utgenome.gwt.utgb.server.app.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.utgenome.gwt.utgb.server.UTGBMaster;
import org.utgenome.gwt.utgb.server.WebTrackBase;
import org.xerial.util.log.Logger;

/**
 * Web action: UsingTemplate
 * 
 */
public class UsingTemplate extends WebTrackBase {
	private static final long serialVersionUID = 1L;
	private static Logger _logger = Logger.getLogger(UsingTemplate.class);

	/**
	 * Describe your web action parameters here. Public fields in this class will be set using the web request query
	 * parameters before calling handle().
	 */

	/**
	 * Predefined coordinate parameters for GenomeTrack. Uncomment the following lines if you want to receive these
	 * parameter values.
	 */
	// public String species;   /* human, mouse, etc. */
	// public String revision;  /* hg19, mm9 ... */
	// public String name;	    /* chr1, chr2, ... */
	// public int start;       /* start position on the genome */
	// public int end;         /* end position on the genome (inclusive) */ 
	// public int width;        /* track pixel width */

	/**
	 * Use dbGroup, dbName parameters to specify database contents to be accessed
	 */
	// public String dbGroup;   /* database group */
	// public String dbName;    /* database name in the group */ 

	public String name = "World";

	public UsingTemplate() {
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// write your own code to generate an web page here.
		response.setContentType("text/html");

		try {
			VelocityEngine ve = new VelocityEngine();
			ve.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, UTGBMaster.getProjectRootFolder() + "/src/main/template");
			ve.init();

			VelocityContext context = new VelocityContext();
			context.put("world", name);

			Template template = ve.getTemplate("template.vm");
			template.merge(context, response.getWriter());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
