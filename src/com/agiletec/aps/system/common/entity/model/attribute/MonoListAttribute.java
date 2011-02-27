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
import java.util.List;

import org.jdom.Element;

/**
 * This class implements a list of homogeneous attributes. 
 * @author M.Diana
 */
public class MonoListAttribute extends AbstractListAttribute {
	
	/**
	 * Initialize the list of attributes.
	 */
	public MonoListAttribute() {
		this._attributes = new ArrayList<AttributeInterface>();
	}
	
	/**
	 * Add a new empty attribute to the list. The attribute was previously defined
	 * using the 'setNestedAttributeType' method.
	 * @return the empty attribute added to the list ready to be populated with the data.
	 */
	public AttributeInterface addAttribute() {
		AttributeInterface newAttr = (AttributeInterface) this.getNestedAttributeType().getAttributePrototype();
		newAttr.setDefaultLangCode(this.getDefaultLangCode());
		newAttr.setParentEntity(this.getParentEntity());
		this.getAttributes().add(newAttr);
		return newAttr;
	}
	
	/**
	 * Return one of the elements in the list identified by the position.
	 * @param index The index, starting from 0, of the attribute to return.
	 * @return The requested attribute.
	 */
	public AttributeInterface getAttribute(int index) {
		return (AttributeInterface) this.getAttributes().get(index);
	}
	
	/**
	 * Return the list of attributes contained in this object.
	 * @return A list of homogeneous attributes.
	 */
	@Override
	public List<AttributeInterface> getAttributes() {
		return _attributes;
	}
	
	/**
	 * Delete the attribute in list in the desired position
	 * @param index The position index of the attribute to remove.
	 */
	public void removeAttribute(int index) {
		this.getAttributes().remove(index);
	}
	
	/**
	 * Set up the language for the renderization. Note: the attributes in the list 
	 * may support several languages (depending on the attributes themselves)
	 * @param langCode The language code for the rendering process.
	 */
	@Override
	public void setRenderingLang(String langCode){
		super.setRenderingLang(langCode);
		for (int i=0; i<this.getAttributes().size(); i++) {
			AttributeInterface attribute = (AttributeInterface) this.getAttributes().get(i);
			attribute.setRenderingLang(langCode);
		}
	}
	
	/**
	 * Return an Element that represents a list of homogeneous attributes, that may be empty 
	 * The list is the same for all the available languages, but it may contain attributes which,
	 * in turn, my support several languages.
	 * @return The JDOM representing a list of homogeneous attributes, with none or multiple
	 * elements.
	 * 
	 */
	@Override
	public Element getJDOMElement(){
		Element monolistElement = new Element("list");
		monolistElement.setAttribute("attributetype", this.getType());
		monolistElement.setAttribute("name", this.getName());
		monolistElement.setAttribute("nestedtype", this.getNestedAttributeTypeCode());
		for (int i=0; i<this.getAttributes().size(); i++) {
			AttributeInterface attribute = (AttributeInterface) this.getAttributes().get(i);
			Element attributeElement = attribute.getJDOMElement();
			monolistElement.addContent(attributeElement);
		}
		return monolistElement;
	}
	
	@Override
	public Object getRenderingAttributes() {
		if (this.getNestedAttributeType().isSimple()) {
			return this.getAttributes();
		} else {
			List<Object> attributes = new ArrayList<Object>();
			for (int i=0; i<this.getAttributes().size(); i++) {
				AbstractComplexAttribute complexAttr = (AbstractComplexAttribute) this.getAttributes().get(i);
				attributes.add(complexAttr.getRenderingAttributes());
			}
			return attributes;
		}
	}
	
	@Override
	public Object getValue() {
		return this.getAttributes();
	}
	
	private List<AttributeInterface> _attributes;
	
}