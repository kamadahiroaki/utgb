/*--------------------------------------------------------------------------
 *  Copyright 2008 utgenome.org
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
// utgb-shell Project
//
// UTGBShellCommandBase.java
// Since: Jan 9, 2008
//
// $URL$ 
// $Author$
//--------------------------------------
package org.utgenome.shell;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.utgenome.config.UTGBConfig;
import org.utgenome.shell.UTGBShell.UTGBShellOption;
import org.xerial.core.XerialException;
import org.xerial.lens.SilkLens;
import org.xerial.util.FileResource;
import org.xerial.util.log.Logger;
import org.xerial.util.opt.Command;
import org.xerial.util.opt.Usage;
import org.xerial.util.text.Template;

/**
 * A base implementation of the UTGBShell's sub commands
 * 
 * @author leo
 * 
 */
@Usage(templatePath = "org/utgenome/shell/help.template")
public abstract class UTGBShellCommand extends UTGBShellCommandBase implements Comparable<Command> {
	private static Logger _logger = Logger.getLogger(UTGBShell.class);
	public static final String APP_FOLDER = "app";
	public static final String SRC_FOLDER = "src/main/java";
	public static final String WEBAPP_FOLDER = "src/main/webapp";
	public static final String EXPLODED_WEBAPP_DIR = "target/utgb";

	@Override
	public abstract String name();

	public void execute(UTGBShellOption opt, String[] args) throws Exception {
		super.globalOption = opt;
		execute(args);
	}

	@Override
	public abstract void execute(String[] args) throws Exception;

	@Override
	public URL getHelpMessageResource() {
		return FileResource.find(this.getClass().getPackage(), String.format("help-%s.txt", name()));
	}

	@Override
	public String getOneLineDescription() {
		return getOneLinerDescription();
	}

	public abstract String getOneLinerDescription();

	@Override
	public Object getOptionHolder() {
		return this;
	};

	public File getProjectRoot() {
		return new File(globalOption.projectDir);
	}

	public boolean isInProjectRoot() {
		return isInProjectRoot(globalOption.projectDir);
	}

	public File getProjectResourcePath(String relativePathFromTheProjectRoot) {
		return new File(globalOption.projectDir, relativePathFromTheProjectRoot);
	}

	public File getConfigFile() {
		String configFileRelativePath = String.format("config/%s.silk", globalOption.environment);
		if (globalOption.projectDir == null)
			return new File(configFileRelativePath);
		else
			return new File(globalOption.projectDir, configFileRelativePath);
	}

	public File getObsolteConfigurationFile() {
		return new File(globalOption.projectDir, "config/track-config.xml");
	}

	private boolean isInProjectRoot(String projectDir) {
		return getConfigFile().exists();
	}

	public void maven(String commandLine) throws UTGBShellException {
		if (globalOption.projectDir != null)
			Maven.runMaven(commandLine, new File(globalOption.projectDir));
		else
			Maven.runMaven(commandLine);
	}

	public void maven(String commandLine, Properties systemProperties) throws UTGBShellException {
		if (globalOption.projectDir != null)
			Maven.runMaven(commandLine, new File(globalOption.projectDir), systemProperties);
		else
			Maven.runMaven(commandLine, null, systemProperties);
	}

	public UTGBConfig loadUTGBConfig() throws UTGBShellException {
		return loadUTGBConfig(globalOption.projectDir);
	}

	private UTGBConfig loadUTGBConfig(String projectDir) throws UTGBShellException {
		if (!isInProjectRoot(projectDir)) {
			throw new UTGBShellException(String.format("Not in the project root folder: configuration file %s not found", getConfigFile()));
		}
		try {
			UTGBConfig config = SilkLens.loadSilk(UTGBConfig.class, getConfigFile().toURI().toURL());
			return config;
		}
		catch (XerialException e) {
			throw new UTGBShellException(String.format("syntax error in config file %s : %s", getConfigFile(), e.getMessage()));
		}
		catch (IOException e) {
			throw new UTGBShellException(String.format("failed to load %s: %s", getConfigFile(), e.getMessage()));
		}
	}

	public int compareTo(Command o) {
		return name().compareTo(o.name());
	}

	public static String getPath(File f) {
		return f.getPath().replaceAll("\\\\", "/");
	}

	public void createContextXML(String contextPath, String projectRoot, boolean reloadable) throws UTGBShellException {
		Properties prop = new Properties();
		prop.put("contextPath", contextPath);
		prop.put("projectRoot", projectRoot);
		prop.put("reloadable", reloadable ? "true" : "false");
		prop.put("environment", globalOption.environment);

		String target = EXPLODED_WEBAPP_DIR + "/META-INF/context.xml";
		createFileFromTemplate(UTGBShellCommand.class, "template/java/context.xml.template", globalOption.projectDir, target, prop, true);
	}

	public void createFileFromTemplate(Class<?> baseClass, String templateFilePath, String relativePathOfTarget, Properties prop) throws UTGBShellException {
		createFileFromTemplate(baseClass, templateFilePath, globalOption.projectDir, relativePathOfTarget, prop, false);
	}

	public static void createFileFromTemplate(Class<?> baseClass, String templateFilePath, String projectFolder, String relativePathOfTarget, Properties prop,
			boolean overWrite) throws UTGBShellException {
		// create parent directories;

		// change the relative path to the absolute path under the project root folder
		File outFile = new File(projectFolder, relativePathOfTarget);

		if (outFile.exists() && !overWrite) {
			_logger.info(getPath(outFile) + " already exists.");
		}
		else {
			try {
				URL url = FileResource.find(baseClass, templateFilePath);
				if (url == null) {
					throw new UTGBShellException(String.format("resource not found %s", templateFilePath));
				}
				Template template;
				template = new Template(url.openStream());
				String fileContent = template.apply(prop);
				outFile.getParentFile().mkdirs();
				// output action class
				FileWriter writer = new FileWriter(outFile);
				writer.append(fileContent);
				writer.close();
				_logger.info("create a file: " + getPath(new File(relativePathOfTarget)));
			}
			catch (IOException e) {
				throw new UTGBShellException(e);
			}
		}
	}

}
