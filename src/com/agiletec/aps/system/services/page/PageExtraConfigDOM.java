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
package com.agiletec.aps.system.services.page;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.exception.ApsSystemException;

/**
 * Dom class for parse the xml of extra page config
 * @author E.Santoboni
 */
public class PageExtraConfigDOM {
	
	public void addExtraConfig(Page page, String xml) throws ApsSystemException {
		Document doc = this.decodeDOM(xml);
		this.addExtraConfig(page, doc);
	}
	
	protected void addExtraConfig(Page page, Document doc) {
		Element useExtraTitlesElement = doc.getRootElement().getChild(USE_EXTRA_TITLES_ELEMENT_NAME);
		if (null != useExtraTitlesElement) {
			Boolean value = new Boolean(useExtraTitlesElement.getText());
			page.setUseExtraTitles(value.booleanValue());
		}
		Element extraGroupsElement = doc.getRootElement().getChild(EXTRA_GROUPS_ELEMENT_NAME);
		if (null != extraGroupsElement) {
			List<Element> groupElements = extraGroupsElement.getChildren(EXTRA_GROUP_ELEMENT_NAME);
			for (int i=0; i<groupElements.size(); i++) {
				Element groupElement = groupElements.get(i);
				page.addExtraGroup(groupElement.getAttributeValue(EXTRA_GROUP_NAME_ATTRIBUTE));
			}
		}
	}
	
	private Document decodeDOM(String xml) throws ApsSystemException {
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		builder.setValidation(false);
		StringReader reader = new StringReader(xml);
		try {
			doc = builder.build(reader);
		} catch (Throwable t) {
			ApsSystemUtils.getLogger().severe("Error while parsing: " + t.getMessage());
			throw new ApsSystemException("Error detected while parsing the XML", t);
		}
		return doc;
	}
	
	public String extractXml(IPage page) {
		Document doc = new Document();
		Element elementRoot = new Element("config");
		doc.setRootElement(elementRoot);
		this.fillDocument(doc, page);
		return this.getXMLDocument(doc);
	}
	
	protected void fillDocument(Document doc, IPage page) {
		Set<String> extraGroups = page.getExtraGroups();
		Element useExtraTitlesElement = new Element(USE_EXTRA_TITLES_ELEMENT_NAME);
		useExtraTitlesElement.setText(String.valueOf(page.isUseExtraTitles()));
		doc.getRootElement().addContent(useExtraTitlesElement);
		
		if (null != extraGroups && extraGroups.size() > 0) {
			Element extraGroupsElement = new Element(EXTRA_GROUPS_ELEMENT_NAME);
			doc.getRootElement().addContent(extraGroupsElement);
			Iterator<String> iterator = extraGroups.iterator();
			while (iterator.hasNext()) {
				String group = iterator.next();
				Element extraGroupElement = new Element(EXTRA_GROUP_ELEMENT_NAME);
				extraGroupElement.setAttribute(EXTRA_GROUP_NAME_ATTRIBUTE, group);
				extraGroupsElement.addContent(extraGroupElement);
			}
		}
	}
	
	protected String getXMLDocument(Document doc) {
		XMLOutputter out = new XMLOutputter();
		Format format = Format.getPrettyFormat();
		out.setFormat(format);
		return out.outputString(doc);
	}
	
	private static final String USE_EXTRA_TITLES_ELEMENT_NAME = "useextratitles";
	private static final String EXTRA_GROUPS_ELEMENT_NAME = "extragroups";
	private static final String EXTRA_GROUP_ELEMENT_NAME = "group";
	private static final String EXTRA_GROUP_NAME_ATTRIBUTE = "name";
	
}