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
package com.agiletec.plugins.jacms.aps.system.services.dispenser;

import com.agiletec.aps.system.RequestContext;

/**
 * Interfaccia base per i servizio fornitori di contenuti formattati.
 * Le implementazioni potranno o meno implementare meccanismi di caching.
 * @author M.Diana - E.Santoboni
 */
public interface IContentDispenser {
	
	/**
	 * Fornisce un contenuto formattato.
	 * @param contentId Identificatore del contenuto.
	 * @param modelId Identificatore del modello di contenuto.
	 * @param langCode Codice della lingua di renderizzazione richiesta.
	 * @param reqCtx Il contesto della richiesta.
	 * @return Il contenuto formattato, solitamente un frammento di html.
	 */
	public String getRenderedContent(String contentId, long modelId, String langCode, RequestContext reqCtx);
	
	public ContentRenderizationInfo getRenderizationInfo(String contentId, long modelId, String langCode, RequestContext reqCtx);
	
}
