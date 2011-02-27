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
package com.agiletec.aps.tags;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.RequestContext;
import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.aps.system.services.page.Showlet;
import com.agiletec.aps.util.ApsProperties;

/**
 * Tag for showlet "InternalServlet".
 * Publish a function erogated throw a internal Servlet; the servlet is invoked by a path specificated 
 * by the tag attribute "actionPath" or by the showlet parameter of the same name.
 * @author M.Casari - E.Santoboni
 */
public class InternalServletTag extends TagSupport {
	
	/**
	 * Internal class that wrappers the response, extending the
	 * javax.servlet.http.HttpServletResponseWrapper class to
	 * define a proprietary output channel.
	 * It is used to retrieve the content response after having
	 * made an 'include' in the RequestDispatcher 
	 */
	public class ResponseWrapper extends HttpServletResponseWrapper {
		
		public ResponseWrapper(HttpServletResponse response) {
			super(response);
			_output = new CharArrayWriter();
		}
		
		@Override
		public PrintWriter getWriter() {
			return new PrintWriter(_output);
		}
		
		@Override
		public void sendRedirect(String path) throws IOException {
			this._redirectPath = path;
		}
		
		@Override
		public void addCookie(Cookie cookie) {
			super.addCookie(cookie);
			this._cookieToAdd = cookie;
		}
		
		protected Cookie getCookieToAdd() {
			return _cookieToAdd;
		}
		
		public boolean isRedirected() {
			return (_redirectPath != null);
		}
		
		public String getRedirectPath() {
			return _redirectPath;
		}
		
		@Override
		public String toString() {
			return _output.toString();
		}
		
		private String _redirectPath;
		private CharArrayWriter _output;
		
		private Cookie _cookieToAdd;
		
	}
	
	/**
	 * Invokes the showlet configured in the current page.
	 * @throws JspException in case of error that occurred in both this method
	 *  or in one of the included JSPs
	 */
	@Override
	public int doEndTag() throws JspException {
		int result = super.doEndTag();
		ServletRequest req =  this.pageContext.getRequest();
		RequestContext reqCtx = (RequestContext) req.getAttribute(RequestContext.REQCTX);
		try {
			IPage page = (IPage) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_PAGE);
			ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse)this.pageContext.getResponse());
			String output = this.buildShowletOutput(page, responseWrapper);
			if (responseWrapper.isRedirected()) {
				String redirect = responseWrapper.getRedirectPath();
				reqCtx.addExtraParam(SystemConstants.EXTRAPAR_EXTERNAL_REDIRECT, redirect);
				result = SKIP_PAGE;
			} else {
				this.pageContext.getOut().print(output);
			}
		} catch (Throwable t) {
			String msg = "Error in showlet preprocessing";
			ApsSystemUtils.logThrowable(t, this, "doEndTag", msg);
			throw new JspException(msg, t);
		}
		return result;
	}
	
	protected String buildShowletOutput(IPage page, ResponseWrapper responseWrapper) throws JspException {
		String output = null;
		ServletRequest req =  this.pageContext.getRequest();
		RequestContext reqCtx = (RequestContext) req.getAttribute(RequestContext.REQCTX);
		try {
			Showlet showlet = (Showlet) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_SHOWLET);
			this.includeShowlet(reqCtx, responseWrapper, showlet);
			if (null != responseWrapper.getCookieToAdd()) {
				reqCtx.getResponse().addCookie(responseWrapper.getCookieToAdd());
			}
			output = responseWrapper.toString();
			responseWrapper.getWriter().close();
		} catch (Throwable t) {
			String msg = "Errore in preelaborazione showlets";
			throw new JspException(msg, t);
		}
		return output;
	}
	
	protected void includeShowlet(RequestContext reqCtx, ResponseWrapper responseWrapper, Showlet showlet) throws ServletException, IOException {
		String actionPath = this.extractIntroActionPath(reqCtx, showlet);
		String requestActionPath = reqCtx.getRequest().getParameter(REQUEST_PARAM_ACTIONPATH);
		String currentFrameActionPath = reqCtx.getRequest().getParameter(REQUEST_PARAM_FRAMEDEST);
		Integer currentFrame = (Integer) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_FRAME);
		if (requestActionPath != null && currentFrameActionPath != null && currentFrame.toString().equals(currentFrameActionPath)) {
			actionPath = requestActionPath;
		}
		HttpServletRequest request = reqCtx.getRequest();
		try {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(actionPath);
			requestDispatcher.include(request, responseWrapper);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "includeShowlet", "Error including showlet");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/aps/jsp/system/internalServlet_error.jsp");
			requestDispatcher.include(request, responseWrapper);
		}
	}
	
	/**
	 * Extract the init Action Path. 
	 * Return the tag attribute (if set), else the showlet parameter.
	 * @param reqCtx The request context
	 * @param showlet The current showlet.
	 * @return The init Action Path
	 */
	protected String extractIntroActionPath(RequestContext reqCtx, Showlet showlet) {
		String actionPath = this.getActionPath();
		if (null == this.getActionPath()) {
			ApsProperties config = showlet.getConfig();
			if (showlet.getType().isLogic()) {
				config = showlet.getType().getConfig();
			}
			if (null != config) {
				actionPath = config.getProperty(CONFIG_PARAM_ACTIONPATH);
			}
		}
		if (null == actionPath || actionPath.trim().length() == 0) {
			IPage page = (IPage) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_PAGE);
			throw new RuntimeException("Null init action path : page " + page.getCode());
		}
		return actionPath;
	}
	
	@Override
	public void release() {
		super.release();
		this.setActionPath(null);
	}
	
	public String getActionPath() {
		return _actionPath;
	}
	public void setActionPath(String actionPath) {
		this._actionPath = actionPath;
	}
	
	private String _actionPath;
	
	public static final String CONFIG_PARAM_ACTIONPATH = "actionPath";
	public static final String REQUEST_PARAM_ACTIONPATH = "internalServletActionPath";
	public static final String REQUEST_PARAM_FRAMEDEST = "internalServletFrameDest";
	
}