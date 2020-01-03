/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.puts.model;
import sit.int303.grianisland.util.StatusMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import sit.int303.grianisland.datasource.GrainIslandConnectionFactory;

/**
 *
 * @author Vable
 */
public class PutsMessage implements Comparable<PutsMessage>
{

    private int userId;
    private int putsId;
    private String putsMessage;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPutsMessage() {
        return putsMessage;
    }

    public void setPutsMessage(String putsMessage) {
        this.putsMessage = putsMessage;
    }

    public int getPutsId() {
        return putsId;
    }

    public void setPutsId(int putsId) {
        this.putsId = putsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public PutsMessage()
    {
        date = new Date();
        userId=-1;
        putsMessage="";
    }

    public PutsMessage(int userId,String putsMessage)
    {
        date = new Date();
        this.userId=userId;
        this.putsMessage=putsMessage;
    }

    @Override
    public int compareTo(PutsMessage putsMessage2) {
        return putsMessage2.getPutsId()-putsId;
    }

    public StatusMessage puts() throws SQLException, ClassNotFoundException
    {
        StatusMessage error = new StatusMessage(true);
        Connection databaseCOnnection =GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement putsStatement = databaseCOnnection.prepareStatement("INSERT INTO Puts(time,message,userid) VALUES(?,?,?)");
        putsStatement.setLong(1, date.getTime());
        putsStatement.setString(2,putsMessage);
        putsStatement.setInt(3, userId);
        if(putsStatement.executeUpdate()>0)
        {
            error.setError(false);
        }
        else
        {
            error.setMessage("can not puts!!");
        }
        return error;
    }
}
