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

import com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface;
import com.agiletec.aps.system.common.entity.model.attribute.BooleanAttribute;
import com.agiletec.apsadmin.system.entity.attribute.AttributeTracer;

/**
 * Manager class for the 'boolean' attributes.
 * @author E.Santoboni
 */
public class BooleanAttributeManager extends AbstractMonoLangAttributeManager {
	
	@Override
	protected Object getValue(AttributeInterface attribute) {
		return ((BooleanAttribute) attribute).getBooleanValue();
	}
	
	@Override
	protected void setValue(AttributeInterface attribute, String value) {
		if (value != null) {
			((BooleanAttribute) attribute).setBooleanValue(new Boolean(value));
		} else {
			((BooleanAttribute) attribute).setBooleanValue(null);
		}
	}
	
	@Override
	protected boolean isValidListElement(AttributeInterface attribute, AttributeTracer tracer) {
		return true;
	}
	
	@Override
	protected boolean isValidMonoListElement(AttributeInterface attribute, AttributeTracer tracer) {
		return true;
	}
	
}