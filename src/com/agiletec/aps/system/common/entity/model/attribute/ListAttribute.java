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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

/**
 * This class represents the Attribute of type "Multi-language List", composed by several
 * homogeneous attributes; there is a list for every language in the system.
 * @author M.Diana
 */
public class ListAttribute extends AbstractListAttribute {
	
	/**
	 * Initialize the data structure.
	 */
	public ListAttribute() {
		this._listMap = new HashMap<String, List<AttributeInterface>>();
	}
	
	/**
	 * Add a new empty attribute to the list in the specified language.
	 * @param langCode The code of the language.
	 * @return The attribute added to the list, ready to be populated with
	 * the data.
	 */
	public AttributeInterface addAttribute(String langCode) {
		AttributeInterface newAttr = (AttributeInterface) this.getNestedAttributeType().getAttributePrototype();
		newAttr.setDefaultLangCode(this.getDefaultLangCode());
		newAttr.setParentEntity(this.getParentEntity());
		List<AttributeInterface> attrList = this.getAttributeList(langCode);
		attrList.add(newAttr);
		return newAttr;
	}
	
	/**
	 * Return the list of attributes of the desired language.
	 * @param langCode The language code.
	 * @return A list of homogeneous attributes.
	 */
	public List<AttributeInterface> getAttributeList(String langCode) {
		List<AttributeInterface> attrList = (List<AttributeInterface>) _listMap.get(langCode);
		if (attrList == null) {
			attrList = new ArrayList<AttributeInterface>();
			this._listMap.put(langCode, attrList);
		}
		return attrList;
	}
	
	/**Return the list of attributes in the current rendering language.
	 * @return A list of homogeneous attributes.
	 */
	@Override
	public Object getRenderingAttributes() {
		List<AttributeInterface> attrList = this.getAttributeList(this.getRenderingLang());
		return attrList;
	}
	
	/**
	 * Remove from the list one of the attributes of the given language.
	 * @param langCode The code of the language of the list where to delete the attribute from.
	 * @param index The index of the attribute in the list.
	 */
	public void removeAttribute(String langCode, int index) {
		List<AttributeInterface> attrList = this.getAttributeList(langCode);
		attrList.remove(index);
		if (attrList.isEmpty()) {
			this._listMap.remove(langCode);
		}
	}
	
	/**
	 * @see com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface#setDefaultLangCode(java.lang.String)
	 */
	@Override
	public void setDefaultLangCode(String langCode){
		super.setDefaultLangCode(langCode);
		Iterator<List<AttributeInterface>> values = this._listMap.values().iterator();
		while (values.hasNext()) {
			List<AttributeInterface> elementList = values.next();
			Iterator<AttributeInterface> attributes = elementList.iterator();
			while (attributes.hasNext()) {
				AttributeInterface attribute = attributes.next();
				attribute.setDefaultLangCode(langCode);
			}
		}
	}
	
	/**
	 * @see com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface#getJDOMElement()
	 */
	@Override
	public Element getJDOMElement() {
		Element listElement = new Element("list");
		listElement.setAttribute("attributetype", this.getType());
		listElement.setAttribute("name", this.getName());
		listElement.setAttribute("nestedtype",this.getNestedAttributeTypeCode());
		Iterator<String> langIter = _listMap.keySet().iterator();
		while (langIter.hasNext()) {
			String langCode = langIter.next();
			Element listLangElement = new Element("listlang");
			if (null != langCode) {
				listLangElement.setAttribute("lang", langCode);
				List<AttributeInterface> attributeList = this.getAttributeList(langCode);
				Iterator<AttributeInterface> attributeListIter = attributeList.iterator();
				while (attributeListIter.hasNext()) {
					AttributeInterface attribute = attributeListIter.next();
					Element attributeElement = attribute.getJDOMElement();
					listLangElement.addContent(attributeElement);
				}
				listElement.addContent(listLangElement);
			}
		}
		return listElement;
	}
	
	/**
	 * Return a Map containing all the localized versions of the associated list.
	 * @return A map indexed by the language code.
	 */
	public Map<String, List<AttributeInterface>> getAttributeListMap() {
		return _listMap;
	}
	
	@Override
	public List<AttributeInterface> getAttributes() {
		List<AttributeInterface> attributes = new ArrayList<AttributeInterface>();
		Iterator<List<AttributeInterface>> values = this.getAttributeListMap().values().iterator();
		while (values.hasNext()) {
			attributes.addAll(values.next());
		}
		return attributes;
	}
	
	@Override
	public Object getValue() {
		return this.getAttributeListMap();
	}
	
	private Map<String, List<AttributeInterface>> _listMap;

}
