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
package test.com.agiletec.plugins.jacms.aps.system.services.content.util;

import test.com.agiletec.aps.BaseTestCase;

import com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface;
import com.agiletec.aps.system.common.entity.model.attribute.MonoTextAttribute;
import com.agiletec.aps.system.common.util.EntityAttributeIterator;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.plugins.jacms.aps.system.services.content.model.Content;

/**
 * @version 1.0
 * @author M. Morini
 */
public class TestContentAttributeIterator extends BaseTestCase {
	
    public void testIterator() throws ApsSystemException {  
		Content content = new Content();
    	AttributeInterface attribute = new MonoTextAttribute();
    	attribute.setName("temp");
    	attribute.setDefaultLangCode("it");
    	attribute.setRenderingLang("it");
    	attribute.setSearcheable(true);
    	attribute.setType("Monotext");        
        content.addAttribute(attribute);
        EntityAttributeIterator attributeIterator = new EntityAttributeIterator(content);
        boolean contains = false;
        while (attributeIterator.hasNext()) {
        	attribute = (AttributeInterface) attributeIterator.next();
			contains = attribute.getName().equals("temp");
		}
        assertTrue(contains);
    }        
}
