package sit.int303.grianisland.puts.model;

import sit.int303.grianisland.util.StatusMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.TreeMap;
import sit.int303.grianisland.datasource.GrainIslandConnectionFactory;
import sit.int303.grianisland.core.model.Player;

/**
 *
 * @author Vable
 */
public class PutsBoard {

    private int userId=-1;

    public PutsBoard()
    {
    }

    public PutsBoard(int userId)
    {
        this.userId=userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public TreeMap<Integer,PutsMessage> getTimeline() throws SQLException, ClassNotFoundException
    {
        return getTimeline(userId);
    }

    public static TreeMap<Integer,PutsMessage> getTimeline(int targetUserId) throws SQLException, ClassNotFoundException
    {
        TreeMap<Integer,PutsMessage> targetPuts = new TreeMap<Integer,PutsMessage>();
        Connection databaseConnection =GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement getTimelineStatement = databaseConnection.prepareStatement("SELECT* FROM puts WHERE userid LIKE ? AND hidden=0 ORDER BY putsid desc");
        if(targetUserId==-1)
        {
            getTimelineStatement.setString(1, "%");
        }
        else
        {
            getTimelineStatement.setInt(1, targetUserId);
        }
        ResultSet resultSet= getTimelineStatement.executeQuery();
        while(resultSet.next())
        {
            PutsMessage puts = new  PutsMessage();
            puts.setPutsId(resultSet.getInt("putsid"));
            puts.setPutsMessage(resultSet.getString("message"));
            puts.setUserId(resultSet.getInt("userid"));
            puts.setDate(new Date(resultSet.getLong("time")));
            targetPuts.put(puts.getPutsId(), puts);
        }
        return targetPuts;
    }

    public static TreeMap<Integer, PutsMessage> getMentioning(int userId) throws SQLException, ClassNotFoundException {
        TreeMap<Integer,PutsMessage> targetPuts = new TreeMap<Integer,PutsMessage>();
        Connection databaseConnection =GrainIslandConnectionFactory.getDatabaseConection();
        String player=Player.getName(userId);
        PreparedStatement getTimelineStatement = databaseConnection.prepareStatement("SELECT* FROM puts WHERE  (message LIKE ? OR message LIKE ? OR message LIKE ? OR message LIKE ?) AND hidden=0 ORDER BY putsid desc");
        getTimelineStatement.setString(1, "% @"+player+"% %");
        getTimelineStatement.setString(2, "@"+player+"% %");
        getTimelineStatement.setString(3, "% @"+player+"%");
        getTimelineStatement.setString(4, "@"+player);
        ResultSet resultSet= getTimelineStatement.executeQuery();
        while(resultSet.next())
        {
            PutsMessage puts = new  PutsMessage();
            puts.setPutsId(resultSet.getInt("putsid"));
            puts.setPutsMessage(resultSet.getString("message"));
            puts.setUserId(resultSet.getInt("userid"));
            puts.setDate(new Date(resultSet.getLong("time")));
            targetPuts.put(puts.getPutsId(), puts);
        }
        return targetPuts;
    }

    public static TreeMap<Integer,PutsMessage> getPublicTimeline() throws SQLException, ClassNotFoundException
    {
        TreeMap<Integer,PutsMessage> publicPuts = getTimeline(-1);
        return publicPuts;
    }

    public StatusMessage puts(String message) throws SQLException, ClassNotFoundException
    {
        PutsMessage putsMessage =new PutsMessage(userId,message);
        StatusMessage error=putsMessage.puts();
        return error;
    }

    public StatusMessage remove(int putsId) throws SQLException, ClassNotFoundException
    {
        StatusMessage error=new StatusMessage(true);
        Connection databaseConnection =GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement removeStatement = databaseConnection.prepareStatement("UPDATE puts SET hidden=1 WHERE putsid=? AND userid=?");
        removeStatement.setInt(1, putsId);
        removeStatement.setInt(2, userId);
        if(removeStatement.executeUpdate()>0)
        {
            error.setError(false);
        }
        else
        {
            error.setMessage("Can not delete!!");
        }
        return error;
    }

    public static TreeMap<Integer,PutsMessage> find(String keyword) throws SQLException, ClassNotFoundException
    {
        TreeMap<Integer,PutsMessage> putsList = new TreeMap<Integer,PutsMessage>();
        Connection databaseConnection =GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement getTimelineStatement = databaseConnection.prepareStatement("SELECT* FROM puts WHERE message LIKE ? AND hidden=0 ORDER BY putsid desc");
        getTimelineStatement.setString(1, "%"+keyword+"%");
        ResultSet resultSet= getTimelineStatement.executeQuery();
        while(resultSet.next())
        {
            PutsMessage puts = new  PutsMessage();
            puts.setPutsId(resultSet.getInt("putsid"));
            puts.setPutsMessage(resultSet.getString("message"));
            puts.setUserId(resultSet.getInt("userid"));
            puts.setDate(new Date(resultSet.getLong("time")));
            putsList.put(puts.getPutsId(),puts);
        }
        return putsList;
    }
}