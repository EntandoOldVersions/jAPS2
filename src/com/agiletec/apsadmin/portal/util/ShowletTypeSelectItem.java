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
package com.agiletec.apsadmin.portal.util;

import com.agiletec.apsadmin.util.SelectItem;

/**
 * @deprecated from jAPS 2.0 version 2.1 - use {@link SelectItem}
 */
public class ShowletTypeSelectItem extends SelectItem {
	
	public ShowletTypeSelectItem(String key, String value, String optgroup) {
		super(key, value, optgroup);
	}
	
	/**
	 * Return the code of the group owning the showlet type.
	 * The field is null if the showlet type belongs to jAPS Core.
	 * @return The group code.
	 */
	public String getGroupCode() {
		return super.getOptgroup();
	}
	
}