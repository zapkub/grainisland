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
import sit.int303.grianisland.datasource.GrainIslandConnectionFactory;

/**
 * @author Vable
 */
public class QuestMessage implements Comparable<QuestMessage>{
    private int questMessageId;
    private String message;
    private boolean question; 
    private int trueChoice;
    private int nextMessage;
    private HashMap<Integer,QuestChoice> choices=new HashMap<Integer,QuestChoice>();

    public int getNextMessage() {
        return nextMessage;
    }

    public void setNextMessage(int nextMessage) {
        this.nextMessage = nextMessage;
    }
    public HashMap<Integer, QuestChoice> getChoices() {
        return choices;
    }

    public void setChoices(HashMap<Integer, QuestChoice> choices) {
        this.choices = choices;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getQuestMessageId() {
        return questMessageId;
    }

    public void setQuestMessageId(int questMessageId) {
        this.questMessageId = questMessageId;
    }

    public boolean isQuestion() {
        return question;
    }

    public void setQuestion(boolean question) {
        this.question = question;
    }

    public int getTrueChoice() {
        return trueChoice;
    }

    public void setTrueChoice(int trueChoice) {
        this.trueChoice = trueChoice;
    }

    public QuestChoice getQuestChoice(int choiceId)
    {
        return choices.get(choiceId);
    }

    public void loadChoices() throws SQLException, ClassNotFoundException {
        Connection conn=GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement loadQuestChoices = conn.prepareStatement("SELECT * FROM choice WHERE questmessageid=?");
        loadQuestChoices.setInt(1, questMessageId);
        ResultSet resultSet=loadQuestChoices.executeQuery();
        while(resultSet.next())
        {
            QuestChoice choice = new QuestChoice();
            int choiceId=resultSet.getInt("choiceid");
            choice.setChoiceId(choiceId);
            choice.setChoiceMessage(resultSet.getString("choicemessage"));
            choice.setNextMessage(resultSet.getInt("nextmessage"));
            choices.put(choiceId, choice);
        }
    }

    @Override
    public boolean equals(Object obj) {
        QuestMessage message2=(QuestMessage) obj;
        return (this.questMessageId==message2.getQuestMessageId())?true:false;
   }

    @Override
    public int compareTo(QuestMessage o) {
        return this.questMessageId-o.getQuestMessageId();
    }
}
