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
package test.com.agiletec.plugins.jacms.aps.system.services.resource.parse;

import test.com.agiletec.aps.BaseTestCase;

import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.plugins.jacms.aps.system.services.resource.parse.ResourceDOM;

/**
 * @version 1.0
 * @author M. Morini
 */
public class TestResourceDOM extends BaseTestCase {
	
    public void testGetXMLDocument() throws ApsSystemException {  
		ResourceDOM resourceDom = new ResourceDOM();
        resourceDom.addCategory("tempcategory");
        int index = resourceDom.getXMLDocument().indexOf("tempcategory");
        assertTrue(index != -1);
    }
    
}
