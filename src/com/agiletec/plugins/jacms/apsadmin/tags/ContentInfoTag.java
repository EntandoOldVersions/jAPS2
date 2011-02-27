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
package com.agiletec.plugins.jacms.apsadmin.tags;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.util.ApsWebApplicationUtils;
import com.agiletec.apsadmin.tags.AbstractObjectInfoTag;
import com.agiletec.plugins.jacms.aps.system.JacmsSystemConstants;
import com.agiletec.plugins.jacms.aps.system.services.content.IContentManager;

/**
 * Returns a content (or one of its property) through the code.
 * You can choose whether to return the entire object (leaving the attribute "property" empty) or a single property.
 * The names of the available property of "Content" (Entity Object): "id", "descr", "typeCode", "typeDescr", 
 * "mainGroup" (code), "groups" (extra group codes), "categories" (list of categories),
 * "attributeMap" (map of attributes indexed by the name), "attributeList" (list of attributes), "status" (code), 
 * "viewPage" (page code), "listModel", "defaultModel", "version", "lastEditor" (username of last editor).
 * The names of the available property of "Content" (Record Object): "id", "typeCode", "descr", "status" (code), "create" (Creation Date), 
 * "modify" (last modify date), "mainGroupCode", "version", "lastEditor" (username of last editor).
 * @author E.Santoboni
 */
public class ContentInfoTag extends AbstractObjectInfoTag {
	
	@Override
	protected Object getMasterObject(String keyValue) throws Throwable {
		try {
			IContentManager contentManager = (IContentManager) ApsWebApplicationUtils.getBean(JacmsSystemConstants.CONTENT_MANAGER, this.pageContext);
			if (this.isRecord()) {
				return contentManager.loadContentVO(keyValue);
			} else {
				return contentManager.loadContent(keyValue, !this.isWorkVersion());
			}
		} catch (Throwable t) {
			String message = "Error extracting content : id '" + keyValue + "' - " +
					"record " + this.isRecord() + "' - work version " + this.isWorkVersion();
			ApsSystemUtils.logThrowable(t, this, "getMasterObject", message);
			throw new ApsSystemException(message, t);
		}
	}
	
	public void setContentId(String contentId) {
		super.setKey(contentId);
	}
	
	/**
	 * Indicates if the record of the entity object must be returned.
	 * @return
	 */
	protected boolean isRecord() {
		return _record;
	}
	/**
	 * Decide if the record of the entity object must be returned.
	 * @param record
	 */
	public void setRecord(boolean record) {
		this._record = record;
	}
	
	/**
	 * Determines the version to be returned: the current version or the published one.
	 * @return The boolean value
	 */
	public boolean isWorkVersion() {
		return _workVersion;
	}
	
	/**
	 * Decides the version to be returned: the current version or the published one.
	 * @param workVersion The boolean value
	 */
	public void setWorkVersion(boolean workVersion) {
		this._workVersion = workVersion;
	}
	
	private boolean _record;
	private boolean _workVersion;
	
}