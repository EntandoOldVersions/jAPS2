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
package test.com.agiletec.plugins.jacms.aps.system.services.dispenser;

import test.com.agiletec.aps.BaseTestCase;

import com.agiletec.aps.system.RequestContext;
import com.agiletec.plugins.jacms.aps.system.JacmsSystemConstants;
import com.agiletec.plugins.jacms.aps.system.services.dispenser.ContentRenderizationInfo;
import com.agiletec.plugins.jacms.aps.system.services.dispenser.IContentDispenser;

/**
 * @author W.Ambu - E.Santoboni
 */
public class TestContentDispenser extends BaseTestCase {
	
	@Override
	protected void setUp() throws Exception {
        super.setUp();
        this.init();
    }
	
    public void testGetRenderedContent_1() throws Throwable {
    	RequestContext reqCtx = this.getRequestContext();
    	
    	ContentRenderizationInfo outputInfo = this._contentDispenser.getRenderizationInfo("ART1", 2, "en", reqCtx);
    	assertEquals(this.replaceNewLine(_attendedEnART1.trim()), this.replaceNewLine(outputInfo.getRenderedContent().trim()));
    	
    	this.setUserOnSession("admin");
    	outputInfo = this._contentDispenser.getRenderizationInfo("ART1", 2, "en", reqCtx);
    	assertEquals(this.replaceNewLine(_attendedEnART1.trim()), this.replaceNewLine(outputInfo.getRenderedContent().trim()));
    	
    	outputInfo = this._contentDispenser.getRenderizationInfo("ART104", 2, "it", reqCtx);
    	assertEquals(this.replaceNewLine(_attendedItART104.trim()), this.replaceNewLine(outputInfo.getRenderedContent().trim()));
    	
    	this.setUserOnSession("editorCoach");
    	outputInfo = this._contentDispenser.getRenderizationInfo("ART104", 2, "it", reqCtx);
    	assertEquals(this.replaceNewLine(_attendedItART104.trim()), this.replaceNewLine(outputInfo.getRenderedContent().trim()));
    	
    	this.setUserOnSession("pageManagerCoach");
    	outputInfo = this._contentDispenser.getRenderizationInfo("ART104", 2, "it", reqCtx);
    	assertEquals(this.replaceNewLine(_attendedItART104.trim()), this.replaceNewLine(outputInfo.getRenderedContent().trim()));
    }
    
    public void testGetRenderedContent_2() throws Throwable {
    	RequestContext reqCtx = this.getRequestContext();
    	this.setUserOnSession("admin");
    	
    	ContentRenderizationInfo outputInfo = this._contentDispenser.getRenderizationInfo("ART120", 2, "it", reqCtx);
		assertEquals(this.replaceNewLine(_attendedItART120.trim()), this.replaceNewLine(outputInfo.getRenderedContent().trim()));
    	
		outputInfo = this._contentDispenser.getRenderizationInfo("ART120", 2, "en", reqCtx);
    	assertEquals(this.replaceNewLine(_attendedEnART120.trim()), this.replaceNewLine(outputInfo.getRenderedContent().trim()));
    	
    	outputInfo = this._contentDispenser.getRenderizationInfo("ART121", 2, "it", reqCtx);
		assertEquals(this.replaceNewLine(_attendedItART121.trim()), this.replaceNewLine(outputInfo.getRenderedContent().trim()));
    	
		outputInfo = this._contentDispenser.getRenderizationInfo("ART121", 2, "en", reqCtx);
    	assertEquals(this.replaceNewLine(_attendedEnART121.trim()), this.replaceNewLine(outputInfo.getRenderedContent().trim()));
    	
    	outputInfo = this._contentDispenser.getRenderizationInfo("ART122", 2, "en", reqCtx);
    	assertEquals(this.replaceNewLine(_attendedEnART122.trim()), this.replaceNewLine(outputInfo.getRenderedContent().trim()));
    }
    
    public void testGetUnauthorizedContent() throws Throwable {
    	RequestContext reqCtx = this.getRequestContext();
    	
    	ContentRenderizationInfo outputInfo = this._contentDispenser.getRenderizationInfo("ART104", 2, "it", reqCtx);
    	assertEquals("Current user 'guest' can't view this content", outputInfo.getRenderedContent().trim());
    	
    	this.setUserOnSession("editorCustomers");
    	outputInfo = this._contentDispenser.getRenderizationInfo("ART104", 2, "it", reqCtx);
    	assertEquals("Current user 'editorCustomers' can't view this content", outputInfo.getRenderedContent().trim());
    	
    	this.setUserOnSession("supervisorCustomers");
    	outputInfo = this._contentDispenser.getRenderizationInfo("ART104", 2, "it", reqCtx);
    	assertEquals("Current user 'supervisorCustomers' can't view this content", outputInfo.getRenderedContent().trim());
    }
    
    public void testGetRenderedContentWithWrongModel() throws Throwable {
    	RequestContext reqCtx = this.getRequestContext();
    	String output = _contentDispenser.getRenderedContent("ART1", 67, "en", reqCtx);
    	assertEquals("Content model 67 undefined", output.trim());
    }
    
    private String replaceNewLine(String input) {
    	input = input.replaceAll("\\n", "");
    	input = input.replaceAll("\\r", "");
        return input;
    }
    
    private void init() throws Exception {
    	try {
    		this._contentDispenser = (IContentDispenser) this.getService(JacmsSystemConstants.CONTENT_DISPENSER_MANAGER);
    	} catch (Throwable t) {
    		throw new Exception(t);
    	}
    }
    
    private IContentDispenser _contentDispenser = null;
    
    private String _attendedEnART1 = 
		"ART1;\n" 
    	+ "Pippo;\n"
    	+ "Paperino;\n"
    	+ "Pluto;\n"
    	+ "The title;\n"
    	+ "Spiderman,http://www.spiderman.org;\n"
    	+ "Image description,/japs/resources/cms/images/lvback_d1.jpg;\n"
    	+ "Mar 10, 2004;";
    
    private String _attendedItART104 = 
		"ART104;\n" 
    	+ "Walter;\n"
    	+ "Marco;\n"
    	+ "Eugenio;\n"
    	+ "William;\n"
    	+ "Titolo Contenuto 2 Coach;\n"
    	+ "Home jAPS,http://www.japsportal.org;\n"
    	+ ",;\n"
    	+ "4-gen-2007;";
    
    private String _attendedItART120 = 
    	"ART120;\n" +
    	"Titolo Contenuto degli &quot;Amministratori&quot;;\n" +
    	"Pagina Iniziale jAPSPortal,http://www.japsportal.org;\n,;\n" +
    	"28-mar-2009;";
    
    private String _attendedEnART120 = 
    	"ART120;\n" +
    	"Title of Administrator's Content;\n" +
    	"jAPSPortal HomePage,http://www.japsportal.org;\n,;\n" +
    	"Mar 28, 2009;";
	
	private String _attendedItART121 = 
    	"ART121;\n" +
    	"Titolo Contenuto degli &quot;Amministratori&quot; 2;\n" +
    	"Pagina Iniziale W3C,http://www.w3.org/;\n,;\n" +
    	"30-mar-2009;";
	
	private String _attendedEnART121 = 
    	"ART121;\n" +
    	"Title of Administrator's Content &lt;2&gt;;\n" +
    	"World Wide Web Consortium - Web Standards,http://www.w3.org/;\n,;\n" +
    	"Mar 30, 2009;";
	
	private String _attendedEnART122 = 
    	"ART122;\n" +
    	"Titolo Contenuto degli &quot;Amministratori&quot; 3;\n,;\n,;\n;";
    
}