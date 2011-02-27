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
package com.agiletec.aps.system.common.entity.parse;

import java.util.Map;

import com.agiletec.aps.system.common.entity.model.IApsEntity;
import com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface;
import com.agiletec.aps.system.exception.ApsSystemException;

/**
 * Interface of the JDOM classes that parse the XML code defining the Entity Types.
 * @author E.Santoboni
 */
public interface IEntityTypeDOM {
	
	/**
	 * Initialization of the DOM class.
	 * @param xml The configuration XML of the Entity Types available.
	 * @param entityClass The class of the Entity Type.
	 * @param entityDom The DOM class that creates the XML of the entity instances. 
	 * @throws ApsSystemException If errors are detected while parsing the configuration XML.
	 */
	public void initEntityTypeDOM(String xml, Class entityClass, 
			IApsEntityDOM entityDom) throws ApsSystemException;
	
	/**
	 * Prepare the map with the Attribute Types.
	 * The map is indexed by the code of the Attribute type.
	 * The Attributes are utilized (as elementary "bricks") to build the structure
	 * of the Entity Types.
	 * @param attributeTypes The map containing the Attribute Types indexed by the type code. 
	 * 
	 */
	public void setAttributeTypes(Map<String, AttributeInterface> attributeTypes);
	
	public Map<String, AttributeInterface> getAttributeTypes();
	
	/**
	 * Return a map, indexed by code, of the Entity Types prototypes. 
	 * This method must be invoked after the parsing process.
	 * @return A map whose key is the Entity Type code, the value is an entity object.
	 */
	public Map<String, IApsEntity> getEntityTypes();
	
	public String getXml(Map<String, IApsEntity> entityTypes) throws ApsSystemException;
	
}
