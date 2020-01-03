/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.util.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import sit.int303.grianisland.core.model.Player;
import sit.int303.grianisland.util.Shuffle;
import sit.int303.grianisland.util.StatusMessage;

/**
 *
 * @author Varavut
 */
public class AuthStringLoginFilter implements Filter {

    private static final boolean debug = false;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured.
    private FilterConfig filterConfig = null;

    public AuthStringLoginFilter() {
    }
public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
	throws IOException, ServletException
    {
        System.out.println("authLogin");
        RequestWrapper  wrappedRequest  = new RequestWrapper((HttpServletRequest)request);
        ResponseWrapper wrappedResponse = new ResponseWrapper((HttpServletResponse)response);
        Player player=(Player) wrappedRequest.getSession(true).getAttribute("player");
        if(player==null)
        {

            Cookie[] cookies=wrappedRequest.getCookies();
            String auth="";
            if(cookies!=null)
            {
                for(Cookie cookie : cookies)
                {
                    if(cookie.getName().equals("auth"))
                    {
                        auth=cookie.getValue();
                        System.out.println(auth);
                        System.out.println(auth);
                    }
                }
                if(!auth.equals(""))
                {
                    try {
                        player = new Player();
                        StatusMessage status=player.loginWithAuthString(auth);
                        if(!status.isError())
                        {
                            wrappedRequest.getSession().setAttribute("player", player);
                            wrappedRequest.getSession().setMaxInactiveInterval(60*60*2);
                            String authString=System.currentTimeMillis()+player.getName();
                            authString=Shuffle.shuffle(authString);
                            player.setAuthString(authString);
                            Cookie authCookie=new Cookie("auth",authString);
                            authCookie.setMaxAge(24*7*7);
                            authCookie.setPath("/");
                            wrappedResponse.addCookie(authCookie);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AuthStringLoginFilter.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(AuthStringLoginFilter.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }
        chain.doFilter(wrappedRequest, wrappedResponse);
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
	return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
	this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
	this.filterConfig = filterConfig;
	if (filterConfig != null) {
	    if (debug) {
		log("AuthStringLoginFilter: Initializing filter");
	    }
	}
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
	if (filterConfig == null) return ("AuthStringLoginFilter()");
	StringBuffer sb = new StringBuffer("AuthStringLoginFilter(");
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
	    if (debug) System.out.println("AuthStringLoginFilter::setParameter(" + name + "=" + values + ")" + " localParams = "+ localParams);

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
	    if (debug) System.out.println("AuthStringLoginFilter::getParameter(" + name + ") localParams = " + localParams);
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
	    if (debug) System.out.println("AuthStringLoginFilter::getParameterValues(" + name + ") localParams = " + localParams);
	    if (localParams == null)
		return getRequest().getParameterValues(name);
	    return (String[]) localParams.get(name);
	}

        @Override
	public Enumeration getParameterNames() {
	    if (debug) System.out.println("AuthStringLoginFilter::getParameterNames() localParams = " + localParams);
	    if (localParams == null)
		return getRequest().getParameterNames();
	    return localParams.keys();
	}

        @Override
	public Map getParameterMap() {
	    if (debug) System.out.println("AuthStringLoginFilter::getParameterMap() localParams = " + localParams);
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
