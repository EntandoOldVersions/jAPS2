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
package com.agiletec.aps.system.services.controller.control;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.RequestContext;
import com.agiletec.aps.system.services.controller.ControllerManager;
import com.agiletec.aps.system.services.url.IURLManager;
import com.agiletec.aps.system.services.url.PageURL;

/**
 * Classe di utilità che implementa un metodo per impostare una redirezione ed
 * un metodo per recuperare un parametro singolo dall'HttpServletRequest.
 * @author 
 */
public abstract class AbstractControlService implements ControlServiceInterface {
	
	/**
	 * Imposta i parametri di una redirezione.
	 * @param redirDestPage Il codice della pagina su cui si vuole redirezionare.
	 * @param reqCtx Il contesto di richiesta.
	 * @return L'indicativo del tipo di redirezione in uscita del controlService. 
	 * Può essere una delle costanti definite in ControllerManager.
	 */
	protected int redirect(String redirDestPage, RequestContext reqCtx) {
		int retStatus;
		try {
			String redirPar = this.getParameter(RequestContext.PAR_REDIRECT_FLAG, reqCtx);
			if (redirPar == null || "".equals(redirPar)) {
				PageURL url = this.getUrlManager().createURL(reqCtx);
				url.setPageCode(redirDestPage);
				url.addParam(RequestContext.PAR_REDIRECT_FLAG, "1");
				String redirUrl = url.getURL();
				if (_log.isLoggable(Level.FINEST)) {
					_log.finest("Redirecting to " + redirUrl);
				}
				reqCtx.clearError();
				reqCtx.addExtraParam(RequestContext.EXTRAPAR_REDIRECT_URL, redirUrl);
				retStatus = ControllerManager.REDIRECT;
			} else {
				reqCtx.setHTTPError(HttpServletResponse.SC_BAD_REQUEST);
				retStatus = ControllerManager.ERROR;
			}
		} catch (Throwable t) {
			retStatus = ControllerManager.SYS_ERROR;
			reqCtx.setHTTPError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ApsSystemUtils.logThrowable(t, this, "redirect", "Error on creation redirect to page " + redirDestPage);
		}
		return retStatus;
	}
	
	/**
	 * Recupera un parametro della richiesta.
	 * @param name Il nome del parametro.
	 * @param reqCtx Il contesto di richiesta.
	 * @return Il valore del parametro
	 */
	protected String getParameter(String name, RequestContext reqCtx){
		String param = reqCtx.getRequest().getParameter(name);
		return param;
	}
	
	protected IURLManager getUrlManager() {
		return _urlManager;
	}
	public void setUrlManager(IURLManager urlManager) {
		this._urlManager = urlManager;
	}
	
	/**
	 * Il log di sistema.
	 */
	protected Logger _log = ApsSystemUtils.getLogger();
	
	/**
	 * Riferimento all'Url Manager.
	 */
	private IURLManager _urlManager;
	
}