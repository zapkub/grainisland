/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.system.tag;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import sit.int303.grianisland.core.model.Player;

/**
 *
 * @author Vable
 */
public class inRequestFriend extends SimpleTagSupport {
    private Object player;
    private int targetId;

    /**
     * Called by the container to invoke this tag. 
     * The implementation of this method is provided by the tag library developer,
     * and handles all tag processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        Player p=(Player)player;
        try {
            out.print(p.inRequestFriend(targetId));
        } catch (SQLException ex) {
            Logger.getLogger(inRequestFriend.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(inRequestFriend.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
                Logger.getLogger(inRequestFriend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPlayer(Object player) {
        this.player = player;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

}
