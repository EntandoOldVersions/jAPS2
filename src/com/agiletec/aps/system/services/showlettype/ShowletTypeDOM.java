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
package com.agiletec.aps.system.services.showlettype;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.exception.ApsSystemException;

/**
 * Classe di supporto all'interpretazione dell'XML 
 * che rappresenta la configurazione di un tipo di showlet.
 * @author E.Santoboni
 * <config>
	<parameter name="contentId">
		Content ID
	</parameter>
	<parameter name="modelId">
		Content Model ID
	</parameter>
	<action name="viewerConfig"/>
</config>
 */
public class ShowletTypeDOM {
	
	/**
	 * Costruttore della classe.
	 * @param xmlText La stringa xml da interpretare.
	 * @throws ApsSystemException In caso di errore 
	 * nell'interpretazione dell'xml di configurazione.
	 */
	public ShowletTypeDOM(String xmlText) throws ApsSystemException {
		this.decodeDOM(xmlText);
	}
	
	public ShowletTypeDOM(List<ShowletTypeParameter> parameters, String action) throws ApsSystemException {
		this._doc = new Document();
		Element root = new Element("config");
		if (null != parameters && parameters.size() > 0) {
			for (int i = 0; i < parameters.size(); i++) {
				ShowletTypeParameter parameter = parameters.get(i);
				Element paramElement = new Element(TAB_PARAMETER);
				paramElement.setAttribute("name", parameter.getName());
				if (null != parameter.getDescr()) {
					paramElement.setText(parameter.getDescr());
				}
				root.addContent(paramElement);
			}
		}
		if (null != action) {
			Element actionElement = new Element(TAB_ACTION);
			actionElement.setAttribute("name", action);
			root.addContent(actionElement);
		}
		this._doc.setRootElement(root);
	}
	
	/**
	 * Restituisce la lista (in oggetti ShowletTypeParameter) 
	 * di parametri di configurazione della showlet.
	 * @return La lista dei parametri di configurazione della showlet.
	 */
	public List<ShowletTypeParameter> getParameters() {
		List<ShowletTypeParameter> parameters = null;
		List<Element> paramElements = this._doc.getRootElement().getChildren(TAB_PARAMETER);
		if (null != paramElements && paramElements.size() > 0) {
			parameters = new ArrayList<ShowletTypeParameter>();
			Iterator<Element> paramElementsIter = paramElements.iterator();
			while (paramElementsIter.hasNext()) {
				Element currentElement = paramElementsIter.next();
				ShowletTypeParameter parameter = new ShowletTypeParameter();
				parameter.setName(currentElement.getAttributeValue("name"));
				String text = currentElement.getText();
				if (null != text) {
					parameter.setDescr(text.trim());
				}
				parameters.add(parameter);
			}
		}
		return parameters;
	}
	
	/**
	 * Restituisce la stringa identificatrice l'action specifica per quella showlet.
	 * @return La stringa identificatrice l'action specifica.
	 */
	public String getAction() {
		String action = null;
		Element actionElement = _doc.getRootElement().getChild(TAB_ACTION);
		if (null != actionElement) {
			action = actionElement.getAttributeValue("name");
		}
		return action;
	}
	
	public String getXMLDocument(){
		XMLOutputter out = new XMLOutputter();
		Format format = Format.getPrettyFormat();
		format.setIndent("");
		out.setFormat(format);
		return out.outputString(this._doc);
	}
	
	private void decodeDOM(String xmlText) throws ApsSystemException {
		SAXBuilder builder = new SAXBuilder();
		builder.setValidation(false);
		StringReader reader = new StringReader(xmlText);
		try {
			_doc = builder.build(reader);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "decodeDOM");
			throw new ApsSystemException("Error detected while parsing the XML", t);
		}
	}
	
	private Document _doc;
	private final String TAB_PARAMETER = "parameter";
	private final String TAB_ACTION = "action";

}
