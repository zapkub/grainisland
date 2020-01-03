
package sit.int303.grianisland.game.model;

import sit.int303.grianisland.game.model.quest.Quest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeMap;
import sit.int303.grianisland.datasource.GrainIslandConnectionFactory;


public class Npc {
    private int npcId;
    private String npcName;
    private int posX;
    private int posY;
    private TreeMap<Integer,Quest> quests=new TreeMap<Integer,Quest>();

    public TreeMap<Integer, Quest> getQuests() {
        return quests;
    }

    public void setQuests(TreeMap<Integer, Quest> quests) {
        this.quests = quests;
    }

    public Npc() {

    }

    public Npc(int npcId, String npcName, String picture, int posX, int posY) {
        this.npcId = npcId;
        this.npcName = npcName;
        this.posX = posX;
        this.posY = posY;
    }

    public int getNpcId() {
        return npcId;
    }

    public void setNpcId(int npcId) {
        this.npcId = npcId;
    }

    public String getNpcName() {
        return npcName;
    }

    public void setNpcName(String npcName) {
        this.npcName = npcName;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Quest getQuest(int questId)
    {
        return quests.get(questId);
    }

    public void loadQuests() throws SQLException, ClassNotFoundException {
        Connection conn=GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement loadQuests = conn.prepareStatement("SELECT * FROM quest WHERE npcid=?");
        loadQuests.setInt(1, npcId);
        ResultSet resultSet=loadQuests.executeQuery();

        while(resultSet.next())
        {
            Quest quest=new Quest();
            int questid=resultSet.getInt("questid");
            quest.setQuestId(questid);
            quest.setQuestName(resultSet.getString("questname"));
            quest.setReward(resultSet.getInt("reward"));
            quest.loadMessages();
            quests.put(questid, quest);
        }
    }
    public boolean addnpc() throws SQLException, ClassNotFoundException{
        Connection cn = GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement ps = cn.prepareStatement("INSERT INTO NPC(npcname,) values(?,?,?)");
        ps.setString(1, npcName);
        ps.setInt(2, posX);
        ps.setInt(3, posY);
        ps.executeUpdate();
        return true;
    }

}
