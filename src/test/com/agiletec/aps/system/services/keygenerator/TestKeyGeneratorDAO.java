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
package test.com.agiletec.aps.system.services.keygenerator;

import javax.sql.DataSource;

import test.com.agiletec.aps.BaseTestCase;
import test.com.agiletec.aps.services.mock.MockUniqueKeysDAO;

import com.agiletec.aps.system.services.keygenerator.KeyGeneratorDAO;

/**
 * @version 1.0
 * @author M.Diana
 */
public class TestKeyGeneratorDAO extends BaseTestCase {
	
    public void testGetUniqueKey() throws Throwable {
    	DataSource dataSource = (DataSource) this.getApplicationContext().getBean("portDataSource");
		KeyGeneratorDAO keyGeneratorDao = new KeyGeneratorDAO();
		keyGeneratorDao.setDataSource(dataSource);
		MockUniqueKeysDAO mockUniqueKeysDao = new MockUniqueKeysDAO();
		mockUniqueKeysDao.setDataSource(dataSource);
		int key = -1;
		int current = -1;
        try {
            current = mockUniqueKeysDao.getCurrentKey(1);
    		key = keyGeneratorDao.getUniqueKey();
        } catch (Throwable t) {
        	throw t;
        }
		assertEquals(key, current);
	}
    
}
