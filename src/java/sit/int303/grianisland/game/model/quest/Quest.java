/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.game.model.quest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeMap;
import sit.int303.grianisland.datasource.GrainIslandConnectionFactory;

/**
 *
 * @author Vable
 */
public class Quest implements Comparable<Quest> {
    private int questId;
    private String questName;
    private TreeMap<Integer,QuestMessage> questMessages=new TreeMap<Integer,QuestMessage>();
    private String trueChoiceString=",";
    private int reward;

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public String getTrueChoiceString() {
        return trueChoiceString;
    }

    public void setTrueChoiceString(String trueChoiceString) {
        this.trueChoiceString = trueChoiceString;
    }

    public TreeMap<Integer, QuestMessage> getQuestMessages() {
        return questMessages;
    }

    public void setQuestMessages(TreeMap<Integer, QuestMessage> questMessages) {
        this.questMessages = questMessages;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public int getQuestId() {
        return questId;
    }

    public void setQuestId(int questId) {
        this.questId = questId;
    }

    public QuestMessage getQuestMessage(int questMessageId)
    {
        return questMessages.get(questMessageId);
    }

    public void loadMessages() throws SQLException, ClassNotFoundException {
        Connection conn=GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement loadQuestMessages = conn.prepareStatement("SELECT * FROM QuestMessage WHERE questid=? ORDER BY messageid asc");
        loadQuestMessages.setInt(1, questId);
        ResultSet resultSet=loadQuestMessages.executeQuery();
        while(resultSet.next())
        {
            QuestMessage questMessage = new QuestMessage();
            int questMessageId=resultSet.getInt("messageid");
            questMessage.setQuestMessageId(questMessageId);
            questMessage.setMessage(resultSet.getString("message"));
            boolean isQuestion = resultSet.getBoolean("isquestion");
            questMessage.setQuestion(isQuestion);
            if(isQuestion)
            {
                questMessage.loadChoices();
                questMessage.setTrueChoice(resultSet.getInt("truechoice"));
                trueChoiceString+=resultSet.getInt("truechoice")+",";
            }
            else
            {
                questMessage.setNextMessage(resultSet.getInt("nextmessage"));
            }

            questMessages.put(questMessageId, questMessage);
        }
    }

    @Override
    public int compareTo(Quest o) {
        return this.questId-o.getQuestId();
    }

}
