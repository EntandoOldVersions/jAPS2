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
package com.agiletec.plugins.jacms.aps.system.services.content.showlet;

import com.agiletec.aps.system.common.entity.model.EntitySearchFilter;

/**
 * Il bean detentore dei parametri di ricerca di liste di contenuti.
 * @author E.Santoboni
 */
public interface IContentListBean {
	
	/**
	 * Restituisce il nome identificativo della lista.
	 * @return Returns Il nome identificativo della lista.
	 */
	public String getListName();
	
	/**
	 * Restituisce il codice dei tipi di contenuto da cercare.
	 * @return Il codice dei tipi di contenuto da cercare.
	 */
	public String getContentType();
	
	/**
	 * Setta il codice dei tipi di contenuto da cercare.
	 * @param contentType Il codice dei tipi di contenuto da cercare.
	 */
	public void setContentType(String contentType);
	
	/**
	 * Restituisce la categoria dei contenuto da cercare.
	 * @return La categoria dei contenuto da cercare.
	 */
	public String getCategory();
	
	/**
	 * Setta la categoria dei contenuto da cercare.
	 * @param category La categoria dei contenuto da cercare.
	 */
	public void setCategory(String category);
	
	/**
	 * Aggiunge un filtro in coda alla lista di filtri definita nel bean.
	 * @param filter Il filtro da aggiungere.
	 */
	public void addFilter(EntitySearchFilter filter);
	
	/**
	 * Restituisce la lista di filtri definita nel bean.
	 * @return La lista di filtri definita nel bean.
	 */
	public EntitySearchFilter[] getFilters();
	
	/**
	 * Indica se nel recupero della lista deve essere utilizzata la cache di sistema.
	 * @return True se deve essere utilizzata la chache di sistema, false in caso contrario.
	 */
	public boolean isCacheable();
	
}