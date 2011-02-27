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
package com.agiletec.plugins.jacms.apsadmin.resource.helper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.group.Group;
import com.agiletec.aps.system.services.user.UserDetails;
import com.agiletec.plugins.jacms.aps.system.services.resource.model.ResourceInterface;

/**
 * Interfaccia Base per le classi helper della gestione risorse.
 * @author E.Santoboni
 */
public interface IResourceActionHelper {
	
	public Map getReferencingObjects(ResourceInterface resource, HttpServletRequest request) throws ApsSystemException;
	
	public List<Group> getAllowedGroups(UserDetails currentUser);
	
	/**
	 * Restituisce la lista di identificativi risorse caricati in base 
	 * ai parametri immessi (tipo risorsa e categoria).
	 * @param resourceType Il tipo della risorsa.
	 * @param insertedText Testo immesso per la ricerca.
	 * @param categoryCode Il codice della categoria specificata della risorsa.
	 * @param currentUser L'utente corrente.
	 * @return La lista di identificativi delle risorse cercate.
	 * @throws Throwable In caso di errore.
	 */
	public List<String> searchResources(String resourceType, 
			String insertedText, String categoryCode, UserDetails currentUser) throws Throwable;
	
	public List<String> searchResources(String resourceType, String insertedText, String groupName, 
			String insertedFileName, String categoryCode, UserDetails currentUser) throws Throwable;
    
}