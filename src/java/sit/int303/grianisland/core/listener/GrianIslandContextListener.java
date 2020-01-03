/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.core.listener;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import sit.int303.grianisland.datasource.GrainIslandConnectionFactory;
import sit.int303.grianisland.game.model.MapManager;
import sit.int303.grianisland.util.StatusMessage;

/**
 * Web application lifecycle listener.
 * @author Vable
 */
public class GrianIslandContextListener implements ServletContextListener, ServletContextAttributeListener {

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        try
        {
            MapManager maps = new MapManager();
            StatusMessage status=maps.loadMap();
            if(status.isError())
            {
                sce.getServletContext().setAttribute("error", status.getMessage());
            }
            else
            {
                sce.getServletContext().setAttribute("maps", maps);
                return;
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            GrainIslandConnectionFactory.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(GrianIslandContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
        
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
       
    }
}
