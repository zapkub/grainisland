/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.system.tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import sit.int303.grianisland.game.model.MapManager;

/**
 *
 * @author Varavut
 */
public class GetMapName extends SimpleTagSupport {
    private int mapId;
    private Object mapmanager;

    /**
     * Called by the container to invoke this tag. 
     * The implementation of this method is provided by the tag library developer,
     * and handles all tag processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        try {
            JspWriter out = getJspContext().getOut();
            MapManager maps = (MapManager) mapmanager;
            out.print(maps.getMap(mapId).getMapName());
        } catch (Exception ex) {
            
        }
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public void setMapmanager(Object mapmanager) {
        this.mapmanager = mapmanager;
    }

}
