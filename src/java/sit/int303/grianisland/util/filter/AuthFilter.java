/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.util.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import sit.int303.grianisland.core.model.Player;

/**
 *
 * @author Vable
 */
public class AuthFilter implements Filter {

    private static final boolean debug = false;

    private FilterConfig filterConfig = null;

    public AuthFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain)
	throws IOException, ServletException
    {
        System.out.println("auth");
        RequestWrapper  wrappedRequest  = new RequestWrapper((HttpServletRequest)request);
        ResponseWrapper wrappedResponse = new ResponseWrapper((HttpServletResponse)response);
	    Player player = (Player) wrappedRequest.getSession().getAttribute("player");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if(player==null)
        {
            throw new ServletException(new Exception("กรุณา Login"));
        }
        else
        {
            chain.doFilter(wrappedRequest, wrappedResponse);
        }
    }

    public FilterConfig getFilterConfig() {
	return (this.filterConfig);
    }


    public void setFilterConfig(FilterConfig filterConfig) {
	this.filterConfig = filterConfig;
    }


    public void destroy() {
    }


    public void init(FilterConfig filterConfig) {
	this.filterConfig = filterConfig;
	if (filterConfig != null) {
	    if (debug) {
		log("AuthFilter: Initializing filter");
	    }
	}
    }


    @Override
    public String toString() {
	if (filterConfig == null) return ("AuthFilter()");
	StringBuffer sb = new StringBuffer("AuthFilter(");
	sb.append(filterConfig);
	sb.append(")");
	return (sb.toString());

    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
	String stackTrace = getStackTrace(t);

	if(stackTrace != null && !stackTrace.equals("")) {
	    try {
		response.setContentType("text/html");
		PrintStream ps = new PrintStream(response.getOutputStream());
		PrintWriter pw = new PrintWriter(ps);
		pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

		// PENDING! Localize this for next official release
		pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
		pw.print(stackTrace);
		pw.print("</pre></body>\n</html>"); //NOI18N
		pw.close();
		ps.close();
		response.getOutputStream().close();
	    }
	    catch(Exception ex) {}
	}
	else {
	    try {
		PrintStream ps = new PrintStream(response.getOutputStream());
		t.printStackTrace(ps);
		ps.close();
		response.getOutputStream().close();
	    }
	    catch(Exception ex) {}
	}
    }

    public static String getStackTrace(Throwable t) {
	String stackTrace = null;
	try {
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    t.printStackTrace(pw);
	    pw.close();
	    sw.close();
	    stackTrace = sw.getBuffer().toString();
	}
	catch(Exception ex) {}
	return stackTrace;
    }

    public void log(String msg) {
	filterConfig.getServletContext().log(msg);
    }

    /**
     * This request wrapper class extends the support class HttpServletRequestWrapper,
     * which implements all the methods in the HttpServletRequest interface, as
     * delegations to the wrapped request.
     * You only need to override the methods that you need to change.
     * You can get access to the wrapped request using the method getRequest()
     */
    class RequestWrapper extends HttpServletRequestWrapper {

	public RequestWrapper(HttpServletRequest request) {
	    super(request);
	}

	// You might, for example, wish to add a setParameter() method. To do this
	// you must also override the getParameter, getParameterValues, getParameterMap,
	// and getParameterNames methods.
	protected Hashtable localParams = null;

	public void setParameter(String name, String []values) {
	    if (debug) System.out.println("AuthFilter::setParameter(" + name + "=" + values + ")" + " localParams = "+ localParams);

	    if (localParams == null) {
		localParams = new Hashtable();
		// Copy the parameters from the underlying request.
		Map wrappedParams = getRequest().getParameterMap();
		Set keySet = wrappedParams.keySet();
		for (Iterator it = keySet.iterator(); it.hasNext(); ) {
		    Object key = it.next();
		    Object value = wrappedParams.get(key);
		    localParams.put(key, value);
		}
	    }
	    localParams.put(name, values);
	}

        @Override
	public String getParameter(String name) {
	    if (debug) System.out.println("AuthFilter::getParameter(" + name + ") localParams = " + localParams);
	    if (localParams == null)
		return getRequest().getParameter(name);
	    Object val = localParams.get(name);
	    if (val instanceof String)
		return (String)val;
	    if (val instanceof String[]) {
		String [] values = (String  []) val;
		return values[0];
	    }
	    return (val==null ? null : val.toString());
	}

        @Override
	public String[] getParameterValues(String name) {
	    if (debug) System.out.println("AuthFilter::getParameterValues(" + name + ") localParams = " + localParams);
	    if (localParams == null)
		return getRequest().getParameterValues(name);
	    return (String[]) localParams.get(name);
	}

        @Override
	public Enumeration getParameterNames() {
	    if (debug) System.out.println("AuthFilter::getParameterNames() localParams = " + localParams);
	    if (localParams == null)
		return getRequest().getParameterNames();
	    return localParams.keys();
	}

        @Override
	public Map getParameterMap() {
	    if (debug) System.out.println("AuthFilter::getParameterMap() localParams = " + localParams);
	    if (localParams == null)
		return getRequest().getParameterMap();
	    return localParams;
	}
    }

    /**
     * This response wrapper class extends the support class HttpServletResponseWrapper,
     * which implements all the methods in the HttpServletResponse interface, as
     * delegations to the wrapped response.
     * You only need to override the methods that you need to change.
     * You can get access to the wrapped response using the method getResponse()
     */
    class ResponseWrapper extends HttpServletResponseWrapper {

	public ResponseWrapper(HttpServletResponse response) {
	    super(response);
	}

	// You might, for example, wish to know what cookies were set on the response
	// as it went throught the filter chain. Since HttpServletRequest doesn't
	// have a get cookies method, we will need to store them locally as they
	// are being set.
	/*
	protected Vector cookies = null;

	// Create a new method that doesn't exist in HttpServletResponse
	public Enumeration getCookies() {
		if (cookies == null)
		    cookies = new Vector();
		return cookies.elements();
	}

	// Override this method from HttpServletResponse to keep track
	// of cookies locally as well as in the wrapped response.
	public void addCookie (Cookie cookie) {
		if (cookies == null)
		    cookies = new Vector();
		cookies.add(cookie);
		((HttpServletResponse)getResponse()).addCookie(cookie);
	}
	*/
    }

}
