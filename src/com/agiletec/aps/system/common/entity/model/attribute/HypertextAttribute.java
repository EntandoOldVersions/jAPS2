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
package com.agiletec.aps.system.common.entity.model.attribute;

import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;
import org.jdom.CDATA;
import org.jdom.Element;

import com.agiletec.aps.util.HtmlHandler;

/**
 * This class represents a 'Hypertext' Attribute.
 * @author W.Ambu
 */
public class HypertextAttribute extends TextAttribute {
	
	@Override
	public boolean needToConvertSpecialCharacter() {
		return false;
	}
	
	/**
	 * Return the field to index after having eventually removed the HTML tags.
	 * @return The text field to index
	 */
	@Override
	public String getIndexeableFieldValue() {
		HtmlHandler htmlhandler = new HtmlHandler();
		String parsedText = htmlhandler.getParsedText(super.getText());
		return StringEscapeUtils.unescapeHtml(parsedText);
	}
	
	/** 
	 * Return the requested number of characters of the text associated to this attribute, in the current 
	 * language purged by the HTML tags, if any.
	 * @param n The number of characters to return 
	 * @return The string of text with the desired length.
	 */
	public String getHead(int n) {
		HtmlHandler htmlhandler = new HtmlHandler();
		String parsedText = htmlhandler.getParsedText(super.getText());
		String head = parsedText;
		if ( n < parsedText.length()) {
			while ( ( Character.isLetterOrDigit(parsedText.charAt(n)) || ( parsedText.charAt(n) == ';' ) ) && (n < parsedText.length()) ) {
				n++;
			}
			head = parsedText.substring(0, n);
		}
		return head;
	}

	/**
	 * @see com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface#getJDOMElement()
	 */
	@Override
	public Element getJDOMElement() {
		Element attributeElement = new Element("attribute");
		attributeElement.setAttribute("name", this.getName());
		attributeElement.setAttribute("attributetype", this.getType());
		Iterator<String> langIter = this.getTextMap().keySet().iterator();
		while (langIter.hasNext()) {
			String currentLangCode = langIter.next();
			String hypertext = (String) this.getTextMap().get(currentLangCode);
			if (null != hypertext && hypertext.trim().length() > 0) {
				Element hypertextElement = new Element("hypertext");
				hypertextElement.setAttribute("lang", currentLangCode);
				CDATA cdata = new CDATA(hypertext);
				hypertextElement.addContent(cdata);
				attributeElement.addContent(hypertextElement);
			}
		}
		return attributeElement;
	}

	/**
	 * Since this kind of attribute cannot be searchable we have overridden the abstract so to
	 * always return false.
	 * @return Return always false.
	 * @see com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface#isSearcheable()
	 */
	@Override
	public boolean isSearcheable() {
		return false;
	}
	
	@Override
	public boolean isSearchableOptionSupported() {
		return false;
	}
	
}