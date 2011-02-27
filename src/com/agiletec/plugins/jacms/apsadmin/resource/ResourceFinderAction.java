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
package com.agiletec.plugins.jacms.apsadmin.resource;

import java.util.List;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.plugins.jacms.apsadmin.util.ResourceIconUtil;

/**
 * Classe Action delegata alla gestione delle operazioni di ricerca risorse.
 * @author E.Santoboni
 */
public class ResourceFinderAction extends AbstractResourceAction implements IResourceFinderAction {
	
	@Override
	public List<String> getResources() throws Throwable {
		List<String> resources = null;
		try {
			resources = this.getResourceActionHelper().searchResources(this.getResourceTypeCode(), 
					this.getText(), this.getOwnerGroupName(), this.getFileName(), this.getCategoryCode(), this.getCurrentUser());
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "getResources");
			throw t;
		}
		return resources;
	}
	
	public String getIconFile(String fileName) {
		return this.getResourceIconUtil().getIconFile(fileName);
	}
	
	public String getText() {
		return _text;
	}
	public void setText(String text) {
		this._text = text;
	}
	
	public String getFileName() {
		return _fileName;
	}
	public void setFileName(String fileName) {
		this._fileName = fileName;
	}
	
	public String getOwnerGroupName() {
		return _ownerGroupName;
	}
	public void setOwnerGroupName(String ownerGroupName) {
		this._ownerGroupName = ownerGroupName;
	}
	
	public String getCategoryCode() {
		if (this._categoryCode != null && this.getCategoryManager().getRoot().getCode().equals(this._categoryCode)) {
			this._categoryCode = null;
		}
		return _categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this._categoryCode = categoryCode;
	}
	
	protected ResourceIconUtil getResourceIconUtil() {
		return _resourceIconUtil;
	}
	public void setResourceIconUtil(ResourceIconUtil resourceIconUtil) {
		this._resourceIconUtil = resourceIconUtil;
	}
	
	private String _text;
	private String _fileName;
	private String _ownerGroupName;
	private String _categoryCode;
	
	private ResourceIconUtil _resourceIconUtil;
	
}