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
package com.agiletec.plugins.jacms.aps.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.RequestContext;
import com.agiletec.aps.system.common.entity.model.EntitySearchFilter;
import com.agiletec.aps.util.ApsWebApplicationUtils;
import com.agiletec.plugins.jacms.aps.system.JacmsSystemConstants;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.IContentListBean;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.IContentListHelper;

/**
 * Loads a list of contents IDs by applying the filters (if any).
 * @author E.Santoboni
 */
public class ContentListTag extends TagSupport implements IContentListBean {
	
	@Override
	public int doStartTag() throws JspException {
		if (!this.isCacheable()) {
			return EVAL_BODY_INCLUDE;
		}
		ServletRequest request =  this.pageContext.getRequest();
		RequestContext reqCtx = (RequestContext) request.getAttribute(RequestContext.REQCTX);
		try {
			IContentListHelper helper = (IContentListHelper) ApsWebApplicationUtils.getBean(JacmsSystemConstants.CONTENT_LIST_HELPER, this.pageContext);
			List<String> contentsId = helper.searchInCache(this.getListName(), reqCtx);
			if (contentsId != null && !contentsId.isEmpty()) {
				this.pageContext.setAttribute(this.getListName(), contentsId);
				this.setListEvaluated(true);
				return SKIP_BODY;
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "doStartTag");
			throw new JspException("Error detected while initialising the tag", t);
		}
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException {
		if (this.isListEvaluated()) {
			this.release();
			return super.doEndTag();
		}
		ServletRequest request =  this.pageContext.getRequest();
		RequestContext reqCtx = (RequestContext) request.getAttribute(RequestContext.REQCTX);
		try {
			IContentListHelper helper = (IContentListHelper) ApsWebApplicationUtils.getBean(JacmsSystemConstants.CONTENT_LIST_HELPER, this.pageContext);
			List<String> contents = helper.getContentsId(this, reqCtx);
			this.pageContext.setAttribute(this.getListName(), contents);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "doEndTag");
			throw new JspException("Error detected while finalising the tag", t);
		}
		this.release();
		return super.doEndTag();
	}
	
	@Override
	public void release() {
		this._listName = null;
		this._contentType = null;
		this._category = null;
		this._filters = new EntitySearchFilter[0];
		this._listEvaluated = false;
		this._cacheable = true;
	}
	
	@Override
	public void addFilter(EntitySearchFilter filter) {
		int len = this._filters.length;
		EntitySearchFilter[] newFilters = new EntitySearchFilter[len + 1];
		for(int i=0; i < len; i++){
			newFilters[i] = this._filters[i];
		}
		newFilters[len] = filter;
		this._filters = newFilters;
	}
	
	@Override
	public EntitySearchFilter[] getFilters() {
		return this._filters;
	}
	
	/**
	 * Get the name of the variable in the page context that holds the list of the IDs found.
	 * @return Returns the name of the list.
	 */
	@Override
	public String getListName() {
		return _listName;
	}

	/**
	 * Set the name of the variable in the page context that holds the list of the IDs found.
	 * @param listName The listName to set.
	 */
	public void setListName(String listName) {
		this._listName = listName;
	}

	/**
	 * Get the code of the content types to search.
	 * @return The code of the content type.
	 */
	@Override
	public String getContentType() {
		return _contentType;
	}
	
	/**
	 * Set the code of the content types to search.
	 * @param contentType The code of the content type.
	 */
	@Override
	public void setContentType(String contentType) {
		this._contentType = contentType;
	}
	
	/**
	 * Return the identifier string of the category of the Content to search.
	 * @return The category code.
	 */
	@Override
	public String getCategory() {
		return _category;
	}
	
	/**
	 * Set the identifier string of the category of the Content to search.
	 * @param category The category code.
	 */
	@Override
	public void setCategory(String category) {
		this._category = category;
	}
	
	/**
	 * Checks if the list if the list has been previously stored in the cache.
	 * @return
	 */
	protected boolean isListEvaluated() {
		return _listEvaluated;
	}
	protected void setListEvaluated(boolean listEvaluated) {
		this._listEvaluated = listEvaluated;
	}
	
	/**
	 * Return true if the system caching must involved in the search process.
	 */
	@Override
	public boolean isCacheable() {
		return _cacheable;
	}
	
	/**
	 * Toggles the system caching usage when retrieving the list.
	 * Admitted values (true|false), default "true".
	 * @param cacheable
	 */
	public void setCacheable(boolean cacheable) {
		this._cacheable = cacheable;
	}
	
	private String _listName;
	private String _contentType;
	private String _category;
	private EntitySearchFilter[] _filters = new EntitySearchFilter[0];
	
	private boolean _cacheable = true;
	
	private boolean _listEvaluated;
	
}