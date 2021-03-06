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
package test.com.agiletec.aps.util;

import test.com.agiletec.aps.BaseTestCase;

import com.agiletec.aps.util.HtmlHandler;

/**
 * @version 1.0
 * @author W.Ambu
 */
public class TestHtmlHandler extends BaseTestCase {
	
    public void testGetParsedText() {
        String textToParse = "<title> This is the<b>first</b></title><body><b>this is</b>the next</body>";
        HtmlHandler htmlHandler = new HtmlHandler();
        String resultText = htmlHandler.getParsedText(textToParse);
        assertEquals("  This is the first    this is the next ", resultText);
    }
    
}

