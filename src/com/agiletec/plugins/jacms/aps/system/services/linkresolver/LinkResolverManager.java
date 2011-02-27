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
package com.agiletec.plugins.jacms.aps.system.services.linkresolver;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.RequestContext;
import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.services.authorization.IAuthorizationManager;
import com.agiletec.aps.system.services.group.Group;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.aps.system.services.page.IPageManager;
import com.agiletec.aps.system.services.url.IURLManager;
import com.agiletec.aps.system.services.url.PageURL;
import com.agiletec.aps.system.services.user.UserDetails;
import com.agiletec.plugins.jacms.aps.system.services.content.IContentManager;
import com.agiletec.plugins.jacms.aps.system.services.content.model.SymbolicLink;
import com.agiletec.plugins.jacms.aps.system.services.contentpagemapper.IContentPageMapperManager;

/**
 * Servizio gestore della risoluzione dei link sinbolici.
 * Scopo di questa classe è l'individuazione in un testo di stringhe rappresentanti
 * link simbolici, e la loro traduzione e sostituzione nel testo con i 
 * corrispondenti URL.
 * @author 
 */
public class LinkResolverManager extends AbstractService implements ILinkResolverManager {
	
	@Override
	public void init() throws Exception {
		ApsSystemUtils.getLogger().config(this.getClass().getName() + ": inizializzato " );
	}
	
	/**
	 * Sotituisce nel testo i link simbolici con URL reali.
	 * @param text Il testo che può contenere link simbolici.
	 * @param reqCtx Il contesto di richiesta
	 * @return Il testo in cui i link simbolici sono sostituiti con URL reali.
	 */
	@Override
	public String resolveLinks(String text, RequestContext reqCtx) {
		StringBuffer resolvedText = new StringBuffer();
		int postfixLen = SymbolicLink.SYMBOLIC_DEST_POSTFIX.length();
		int end = 0;
		int parsed = 0;
		int start = text.indexOf(SymbolicLink.SYMBOLIC_DEST_PREFIX);
		while(start >= 0){
			end = text.indexOf(SymbolicLink.SYMBOLIC_DEST_POSTFIX, start);
			if(end >= 0) {
				end = end + postfixLen;
				String symbolicString = text.substring(start, end);
				String url = this.convertToURL(symbolicString, reqCtx);
				if(url != null){
					String invariantText = text.substring(parsed, start);
					resolvedText.append(invariantText);
					resolvedText.append(url);
					parsed = end;
				} else {
					end = start + 1;
				}
				start = text.indexOf(SymbolicLink.SYMBOLIC_DEST_PREFIX, end);
			} else {
				start = -1; //uscita
			}
		}
		String residualText = text.substring(parsed);
		resolvedText.append(residualText);
		return resolvedText.toString();
	}
	
	protected String convertToURL(String symbolicString, RequestContext reqCtx){
		String url = null;
		SymbolicLink link = new SymbolicLink();
		if (link.setSymbolicDestination(symbolicString)) {	    
			if (link.getDestType() == SymbolicLink.URL_TYPE) {
				url = link.getUrlDest();
			} else if (link.getDestType() == SymbolicLink.PAGE_TYPE) {
				PageURL pageUrl = this.getUrlManager().createURL(reqCtx);
				pageUrl.setPageCode(link.getPageDest());
				url = pageUrl.getURL();
			} else if (link.getDestType() == SymbolicLink.CONTENT_ON_PAGE_TYPE) {
				PageURL pageUrl = this.getUrlManager().createURL(reqCtx);
				pageUrl.setPageCode(link.getPageDest());
				pageUrl.addParam(SystemConstants.K_CONTENT_ID_PARAM, link.getContentDest());
				url = pageUrl.getURL();
			} else if (link.getDestType() == SymbolicLink.CONTENT_TYPE) {
				PageURL pageUrl = this.getUrlManager().createURL(reqCtx);
				String contentId = link.getContentDest();
				String pageCode = this.getContentPageMapperManager().getPageCode(contentId);
				boolean forwardToDefaultPage = this.isCurrentUserAllowed(reqCtx, pageCode);
				if (forwardToDefaultPage) {
					String viewPageCode = this.getContentManager().getViewPage(contentId);	
					pageUrl.setPageCode(viewPageCode);
					pageUrl.addParam(SystemConstants.K_CONTENT_ID_PARAM, contentId);
				} else {
					pageUrl.setPageCode(pageCode);
				}
				url = pageUrl.getURL();
			}
		}
		return url;
	}
	
	/**
	 * Verifica se l'utente corrente è autorizzato 
	 * all'accesso alla pagina specificata.
	 * @param reqCtx Il contesto della richiesta.
	 * @param pageCode Il codice della pagina.
	 * @return true se l'utente corrente è abilitato all'accesso 
	 * alla pagina specificata, false in caso contrario..
	 */
	protected boolean isCurrentUserAllowed(RequestContext reqCtx, String pageCode) {
		UserDetails currentUser = (UserDetails) reqCtx.getRequest().getSession().getAttribute(SystemConstants.SESSIONPARAM_CURRENT_USER);
		IAuthorizationManager authManager = (IAuthorizationManager) this.getService(SystemConstants.AUTHORIZATION_SERVICE);
		boolean isUserAllowed = true;
		if (pageCode != null) {
			IPage forwardPage = this.getPageManager().getPage(pageCode);
			if (forwardPage != null) {
				String pageGroup = forwardPage.getGroup();
				if (Group.FREE_GROUP_NAME.equals(pageGroup) 
						|| authManager.isAuthOnGroup(currentUser, pageGroup) 
						|| authManager.isAuthOnGroup(currentUser, Group.ADMINS_GROUP_NAME)) {
					isUserAllowed = false;
				}
			}
		}
		return isUserAllowed;
	}
	
	protected IContentManager getContentManager() {
		return _contentManager;
	}
	public void setContentManager(IContentManager contentManager) {
		this._contentManager = contentManager;
	}
	
	protected IContentPageMapperManager getContentPageMapperManager() {
		return _contentPageMapperManager;
	}
	public void setContentPageMapperManager(IContentPageMapperManager contentPageMapperManager) {
		this._contentPageMapperManager = contentPageMapperManager;
	}
	
	protected IPageManager getPageManager() {
		return _pageManager;
	}
	public void setPageManager(IPageManager pageManager) {
		this._pageManager = pageManager;
	}
	
	protected IURLManager getUrlManager() {
		return _urlManager;
	}
	public void setUrlManager(IURLManager urlManager) {
		this._urlManager = urlManager;
	}
	
	private IPageManager _pageManager;
	private IContentManager _contentManager;
	private IContentPageMapperManager _contentPageMapperManager;
	private IURLManager _urlManager;
	
}