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
package com.agiletec.aps.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.common.IManager;

/**
 * Classe di utilità.
 * @author E.Santoboni
 */
public class ApsWebApplicationUtils {
	
	/**
	 * Restituisce un servizio di sistema.
	 * @param serviceName Il nome del servizio richiesto.
	 * @param request La request.
	 * @return Il servizio richiesto.
	 * @deprecated use getBean
	 */
	public static AbstractService getService(String serviceName, HttpServletRequest request) {
		WebApplicationContext wac = getWebApplicationContext(request);
		return getService(serviceName, wac);
	}
	
	/**
	 * Restituisce un servizio di sistema.
	 * Il seguente metodo è in uso ai tag jsp del sistema.
	 * @param serviceName Il nome del servizio richiesto.
	 * @param pageContext Il Contesto di pagina,
	 * @return Il servizio richiesto.
	 * @deprecated use getBean
	 */
	public static AbstractService getService(String serviceName, PageContext pageContext) {
		WebApplicationContext wac = getWebApplicationContext(pageContext.getServletContext());
		return getService(serviceName, wac);
	}
	
	/**
	 * Restituisce un bean di sistema.
	 * Il seguente metodo è in uso ai tag jsp del sistema.
	 * @param beanName Il nome del servizio richiesto.
	 * @param request La request.
	 * @return Il servizio richiesto.
	 */
	public static Object getBean(String beanName, HttpServletRequest request) {
		WebApplicationContext wac = getWebApplicationContext(request);
		return wac.getBean(beanName);
	}
	
	/**
	 * Restituisce un bean di sistema.
	 * Il seguente metodo è in uso ai tag jsp del sistema.
	 * @param beanName Il nome del servizio richiesto.
	 * @param pageContext Il Contesto di pagina,
	 * @return Il servizio richiesto.
	 */
	public static Object getBean(String beanName, PageContext pageContext) {
		WebApplicationContext wac = getWebApplicationContext(pageContext.getServletContext());
		return wac.getBean(beanName);
	}
	
	/**
	 * Restituisce il WebApplicationContext del sistema.
	 * @param request La request.
	 * @return Il WebApplicationContext del sistema.
	 */
	public static WebApplicationContext getWebApplicationContext(HttpServletRequest request) {
		ServletContext svCtx = request.getSession().getServletContext();
		WebApplicationContext wac = getWebApplicationContext(svCtx);
		return wac;
	}
	
	private static WebApplicationContext getWebApplicationContext(ServletContext svCtx) {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(svCtx);
		return wac;
	}
	
	private static AbstractService getService(String serviceName, WebApplicationContext wac) {
		return (AbstractService) wac.getBean(serviceName);
	}
	
	/**
	 * Esegue il refresh del sistema.
	 * @param request La request.
	 * @throws Throwable In caso di errori in fase di aggiornamento del sistema.
	 */
	public static void executeSystemRefresh(HttpServletRequest request) throws Throwable {
		IManager configManager = (IManager) getBean(SystemConstants.BASE_CONFIG_MANAGER, request);
		configManager.refresh();
		WebApplicationContext wac = getWebApplicationContext(request);
		String[] defNames = wac.getBeanNamesForType(IManager.class);
		for (int i=0; i<defNames.length; i++) {
			Object bean = null;
			try {
				bean = wac.getBean(defNames[i]);
			} catch (Throwable t) {
				ApsSystemUtils.logThrowable(t, ApsWebApplicationUtils.class, "executeSystemRefresh");
				bean = null;
			}
			if (bean != null) {
				((IManager) bean).refresh();
			}
		}
	}
	
}
