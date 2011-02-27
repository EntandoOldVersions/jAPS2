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

import java.util.List;

import com.agiletec.aps.system.RequestContext;
import com.agiletec.aps.system.common.entity.model.EntitySearchFilter;
import com.agiletec.plugins.jacms.aps.system.services.content.IContentManager;

/**
 * Interfaccia base per l'implementazione del bean helper della showlet di erogatore lista contenuti.
 * La classe è a servizio sia delle funzioni dell'area di amministrazione che di font-end.
 * @author E.Santoboni
 */
public interface IContentListHelper {
	
	/**
	 * Ricerca la lista dei contenuti in base alla posizione della showlet nella pagina.
	 * @param listName Il nome identificativo della lista.
	 * @param reqCtx Il contesto della richiesta.
	 * @return La lista di identificativi di contenuto.
	 * @throws Throwable In caso di errore.
	 */
	public List<String> searchInCache(String listName, RequestContext reqCtx) throws Throwable;
	
	/**
	 * Restituisce la lista di identificativi di contenuto in base ai parametri di ricerca.
	 * I parametri utilizzati per la ricerca, per ciascuno di essi vengono estratti con questo 
	 * ordine di importanza: hanno la precedenza i parametri specificati all'intrno del tag jsp, 
	 * nel caso uno di essi sia nullo esso viene ricercato nei parametri di configurazione 
	 * della showlet.
	 * @param bean Il contenitore delle informazioni base sulla interrogazione da eseguire.
	 * @param reqCtx Il contesto della richiesta.
	 * @return La lista di identificativi di contenuto in base ai parametri di ricerca.
	 * @throws Throwable In caso di errore.
	 */
	public List<String> getContentsId(IContentListBean bean, RequestContext reqCtx) throws Throwable;
	
	/**
	 * Restituisce l'insieme dei filtri in base al parametro di configurazione della showlet detentore dei filtri.
	 * Il parametro è nella forma di:
	 * (key=KEY;value=VALUE;attributeFilter=TRUE|FALSE;start=START;end=END;like=TRUE|FALSE)+..<OTHER_FILTERS>
	 * @param contentType Il tipo di contenuto al quale i filtri vanno applicati.
	 * @param filtersShowletParam Il parametro della showlet nella forma corretta detentore dei filtri.
	 * @param reqCtx Il contesto della richiesta.
	 * @return L'insieme dei filtri dato dall'interpretazione del parametro.
	 */
	public EntitySearchFilter[] getFilters(String contentType, String filtersShowletParam, RequestContext reqCtx);
	
	/**
	 * Costruisce e restituisce un filtro in base ai parametri specificati.
	 * Il metodo è a servizio del sottoTag ContentListFilterTag di ContentListTag.
	 * @param contentType Il Tipo di Contenuto corrispondente al filtro da costruire.
	 * @param bean Il contenitore delle informazioni sul filtro da costruire.
	 * @param reqCtx Il contesto della richiesta corrente.
	 * @return Il nuovo filtro costruito in base ai parametri specificati.
	 */
	public EntitySearchFilter getFilter(String contentType, IContentListFilterBean bean, RequestContext reqCtx);
	
	/**
	 * Restituisce il parametro da inserire nella configurazione della showlet.
	 * Il parametro è nella forma di:
	 * (key=KEY;value=VALUE;attributeFilter=TRUE|FALSE;start=START;end=END;like=TRUE|FALSE)+..<OTHER_FILTERS>
	 * @param filters I filtri tramite il quale ricavare il parametro.
	 * @return Il parametro da inserire nella configurazione della showlet.
	 */
	public String getShowletParam(EntitySearchFilter[] filters);
	
	public static final String[] allowedMetadataFilterKeys = 
		{IContentManager.ENTITY_TYPE_CODE_FILTER_KEY, IContentManager.CONTENT_DESCR_FILTER_KEY, IContentManager.CONTENT_STATUS_FILTER_KEY, 
		IContentManager.CONTENT_CREATION_DATE_FILTER_KEY, IContentManager.CONTENT_MODIFY_DATE_FILTER_KEY, IContentManager.CONTENT_ONLINE_FILTER_KEY};
	
}
