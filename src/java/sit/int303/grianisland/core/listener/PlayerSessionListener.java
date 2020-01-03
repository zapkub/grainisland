/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.core.listener;

import java.util.LinkedList;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import sit.int303.grianisland.core.model.Player;

/**
 * Web application lifecycle listener.
 * @author Vable
 */
@WebListener()
public class PlayerSessionListener implements HttpSessionAttributeListener {


    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if(event.getName().equals("player"))
        {
            ServletContext sc = event.getSession().getServletContext();
            LinkedList<Player> onlineList= (LinkedList<Player>)sc.getAttribute("onlinelist");
            if(onlineList==null)
            {
                onlineList = new LinkedList<Player>();
            }
            onlineList.add((Player) event.getValue());
            sc.setAttribute("onlinelist", onlineList);
            System.gc();
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if(event.getName().equals("player"))
        {
            ServletContext sc = event.getSession().getServletContext();
            LinkedList<Player> onlineList= (LinkedList<Player>)sc.getAttribute("onlinelist");
            onlineList.remove((Player) event.getValue());
            sc.setAttribute("onlinelist", onlineList);
            System.gc();
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        System.gc();
    }
}
