/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.system.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import sit.int303.grianisland.core.model.Player;

/**
 *
 * @author Vable
 */
public class GetAvatar extends SimpleTagSupport {
    private int userId;

    /**
     * Called by the container to invoke this tag. 
     * The implementation of this method is provided by the tag library developer,
     * and handles all tag processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        try {
            JspWriter out = getJspContext().getOut();
            out.write(Player.getAvatar(userId));
        } catch (Exception ex) {

        }
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
