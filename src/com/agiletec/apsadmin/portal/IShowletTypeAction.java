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
package com.agiletec.apsadmin.portal;

/**
 * @author E.Santoboni
 */
public interface IShowletTypeAction {
	
	/**
	 * Create of new user showlet.
	 * @return The result code.
	 */
	public String newShowlet();
	
	/**
	 * Copy an exist showlet (physic and with parameters) and value the form 
	 * of creation of new user showlet.
	 * @return The result code.
	 */
	public String copyShowlet();
	
	/**
	 * Add a new user showlet type.
	 * @return The result code.
	 */
	public String saveUserShowlet();
	
	/**
	 * Edit the titles of an exist showlet type.
	 * @return The result code.
	 */
	public String editShowletTitles();
	
	/**
	 * Update the titles of an exist showlet type.
	 * @return The result code.
	 */
	public String saveShowletTitles();
	
	/**
	 * Start the deletion operations for the given showlet type.
	 * @return The result code.
	 */
	public String trash();
	
	/**
	 * Delete a showlet type from the system.
	 * @return The result code.
	 */
	public String delete();
	
}