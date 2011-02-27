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
package com.agiletec.apsadmin.system.entity.attribute.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.agiletec.aps.system.common.entity.model.IApsEntity;
import com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface;
import com.agiletec.aps.system.common.entity.model.attribute.MonoListAttribute;
import com.agiletec.apsadmin.system.entity.attribute.AttributeTracer;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Manager class for the 'Monolist' Attributes
 * @author E.Santoboni
 */
public class MonoListAttributeManager extends AbstractAttributeManager {
	
	@Override
	protected void checkAttribute(ActionSupport action, AttributeInterface attribute, AttributeTracer tracer, IApsEntity entity) {
		super.checkAttribute(action, attribute, tracer, entity);
		this.manageMonoListAttribute(true, false, action, attribute, tracer, null, entity);
	}
	
	@Override
	protected void updateAttribute(AttributeInterface attribute, AttributeTracer tracer, HttpServletRequest request) {
		this.manageMonoListAttribute(false, true, null, attribute, tracer, request, null);
	}
	
	private void manageMonoListAttribute(boolean isCheck, boolean isUpdate, ActionSupport action, 
			AttributeInterface attribute, AttributeTracer tracer, HttpServletRequest request, IApsEntity entity) {
		List<AttributeInterface> attributes = ((MonoListAttribute) attribute).getAttributes();
		for (int i=0; i<attributes.size(); i++) {
			AttributeInterface attributeElement = attributes.get(i);
			AttributeTracer elementTracer = (AttributeTracer) tracer.clone();
			elementTracer.setMonoListElement(true);
			elementTracer.setListIndex(i);
			AbstractAttributeManager elementManager = (AbstractAttributeManager) this.getManager(attributeElement.getType());
			if (elementManager != null) {
				if (isCheck && !isUpdate) {
					elementManager.checkAttribute(action, attributeElement, elementTracer, entity);
				}
				if (!isCheck && isUpdate) {
					elementManager.updateAttribute(attributeElement, elementTracer, request);
				}
			}
		}
	}
	
	@Override
	protected int getState(AttributeInterface attribute, AttributeTracer tracer) {
		boolean valued = ((MonoListAttribute) attribute).getAttributes().size()>0;
		if (valued) {
			return VALUED_ATTRIBUTE_STATE;
		} else return EMPTY_ATTRIBUTE_STATE;
	}
	
}