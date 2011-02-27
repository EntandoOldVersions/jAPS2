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
package com.agiletec.apsadmin.category;

/**
 * Interface defining the actions needed in order to handle categories.  
 * @author E.Santoboni
 */
public interface ICategoryAction {
	
	/**
	 * Create a new category.
	 * @return The result code.
	 */
	public String add();
	
	/**
	 * Edit an existing category.
	 * @return The result code.
	 */
	public String edit();
	
	/**
	 * Start the deletion process of a category.
	 * @return The result code.
	 */
	public String trash();
	
	/**
	 * Delete a category permanently.
	 * @return The result code.
	 */
	public String delete();
	
	/**
	 * Save a category.
	 * @return The result code.
	 */
	public String save();
	
}
