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
package test.com.agiletec.apsadmin.common;

import test.com.agiletec.apsadmin.ApsAdminBaseTestCase;

import com.opensymphony.xwork2.Action;

/**
 * @version 1.0
 * @author E.Santoboni
 */
public class TestDispatchForward extends ApsAdminBaseTestCase {
	
	public void testGoOnMainPage() throws Throwable {
    	this.initAction("/do", "main");
    	this.setUserOnSession("admin");
    	String result = super.executeAction();
		assertEquals(Action.SUCCESS, result);
	}
	
    public void testGoOnMainPageWithUserNotAbilitated() throws Throwable {
    	this.initAction("/do", "main");
    	this.setUserOnSession("guest");
    	String result = super.executeAction();
		assertEquals("apslogin", result);
	}
    
    public void testGoOnMainPageWithNullUser() throws Throwable {
    	this.initAction("/do", "main");
    	this.removeUserOnSession();
    	String result = super.executeAction();
		assertEquals("apslogin", result);
	}
    
}
