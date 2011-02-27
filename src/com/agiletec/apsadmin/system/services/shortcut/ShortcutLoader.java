/*
*
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
* This file is part of jAPS software.
* jAPS is a free software; 
* you can redistribute it and/or modify it
* under the terms of the GNU General Public License (GPL) as published by the Free Software Foundation; version 2.
* 
* See the file License for the specific language governing permissions   
* and limitations under the License
* 
* 
* 
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
*/
package com.agiletec.apsadmin.system.services.shortcut;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.util.FileTextReader;
import com.agiletec.apsadmin.system.services.shortcut.model.MenuSection;
import com.agiletec.apsadmin.system.services.shortcut.model.Shortcut;

/**
 * Shortcut Loader Class.
 * @author E.Santoboni
 */
public class ShortcutLoader {
	
	protected ShortcutLoader(String definitionConfigPath, ServletContext servletContext) throws ApsSystemException {
		this.setDefinitionConfig(definitionConfigPath);
		this.setServletContext(servletContext);
		this.setSectionMenus(new HashMap<String, MenuSection>());
		this.setShortcuts(new HashMap<String, Shortcut>());
		try {
			List<String> shortcutDefs = this.loadShortcutDefinitions();
			this.loadShortcutObjects(shortcutDefs);
			this.completeLoading();
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "ShortcutLoader", "Error loading Shortcut definitions");
			throw new ApsSystemException("Error loading Shortcut definitions", t);
		}
	}
	
	private List<String> loadShortcutDefinitions() throws Throwable {
		List<String> filenames = new ArrayList<String>();
		try {
			StringTokenizer tokenizer = new StringTokenizer(this.getDefinitionConfig(), ",");
			while (tokenizer.hasMoreTokens()) {
				String currentFilename = tokenizer.nextToken().trim();
				int index = currentFilename.indexOf(AXTER);
				if (-1 == index) {
					filenames.add(currentFilename);
				} else {
					List<String> confFiles = new ArrayList<String>();
					String rootInspectionDir = currentFilename.substring(0, index);
					this.inspectResources(currentFilename, rootInspectionDir, confFiles);
					filenames.addAll(confFiles);
				}
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "loadShortcutDefinitions", "Error loading Shortcut definitions");
			throw new ApsSystemException("Error loading Shortcut definitions", t);
		}
		return filenames;
	}
	
	@SuppressWarnings("unchecked")
	private void inspectResources(String currentFileName, String rootInspectionDir, List<String> confFiles) throws Throwable {
		Set<String> resourcesPath = this.getServletContext().getResourcePaths(rootInspectionDir);
		if (null == resourcesPath) return;
		Iterator<String> it = resourcesPath.iterator();
		while (it.hasNext()) {
			String current = it.next();
			if (!current.endsWith("/") && this.isConfResource(current, currentFileName)){
				confFiles.add(current);
			} else {
				this.inspectResources(currentFileName, current, confFiles);
			}
		}		
	}
	
	private boolean isConfResource(String current, String currentFilename) {
		String regExp = currentFilename.replaceAll(AXTER_REG_EXP, REG_EXP);
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(current);
		return m.matches();
	}
	
	private void loadShortcutObjects(List<String> shortcutDefs) throws Exception {
		Logger log = ApsSystemUtils.getLogger();
		if (null == shortcutDefs) {
			log.severe("No any shortcut definition");
			return;
		}
		ShortcutDefDOM dom = null;
		for (int i = 0; i < shortcutDefs.size(); i++) {
			String definitionPath = shortcutDefs.get(i);
			InputStream is = null;
			try {
				is = this.getServletContext().getResourceAsStream(definitionPath);
				String xml = FileTextReader.getText(is);
				dom = new ShortcutDefDOM(xml, definitionPath);
				this.getManuSections().putAll(dom.getSectionMenus());
				this.getShortcuts().putAll(dom.getShortcuts());
				log.info("Loaded Shortcut definition by file " + definitionPath);
			} catch (Throwable t) {
				ApsSystemUtils.logThrowable(t, this, "loadShortcuts", "Error loading Shortcut definition by file " + definitionPath);
			} finally {
				if (null != is) {
					is.close();
				}
			}
		}
	}
	
	private void completeLoading() {
		Iterator<Shortcut> shorCutIter = this.getShortcuts().values().iterator();
		while (shorCutIter.hasNext()) {
			Shortcut shortcut = shorCutIter.next();
			String menuSectionCode = shortcut.getMenuSectionCode();
			MenuSection section = this.getManuSections().get(menuSectionCode);
			shortcut.setMenuSection(section);
		}
	}
	
	protected Map<String, Shortcut> getShortcuts() {
		return _shortcuts;
	}
	private void setShortcuts(Map<String, Shortcut> shortcuts) {
		this._shortcuts = shortcuts;
	}
	
	protected Map<String, MenuSection> getManuSections() {
		return _sectionMenus;
	}
	private void setSectionMenus(Map<String, MenuSection> sectionMenus) {
		this._sectionMenus = sectionMenus;
	}
	
	protected ServletContext getServletContext() {
		return this._servletContext;
	}
	private void setServletContext(ServletContext servletContext) {
		this._servletContext = servletContext;
	}
	
	protected String getDefinitionConfig() {
		return _definitionConfig;
	}
	private void setDefinitionConfig(String definitionConfig) {
		this._definitionConfig = definitionConfig;
	}
	
	private Map<String, Shortcut> _shortcuts;
	private Map<String, MenuSection> _sectionMenus;
	
	private ServletContext _servletContext;
	private String _definitionConfig;
	
	private final static String REG_EXP = "[\\\\w,\\\\-,_]*";
	private final static String AXTER = "**";
	private final static String AXTER_REG_EXP = "\\*\\*";
	
}