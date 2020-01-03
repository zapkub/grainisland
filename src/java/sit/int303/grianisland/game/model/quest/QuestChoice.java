/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.game.model.quest;

/**
 *
 * @author Vable
 */
public class QuestChoice {
    private int choiceId;
    private String choiceMessage;
    private int nextMessage;

    public int getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public String getChoiceMessage() {
        return choiceMessage;
    }

    public void setChoiceMessage(String choiceMessage) {
        this.choiceMessage = choiceMessage;
    }

    public int getNextMessage() {
        return nextMessage;
    }

    public void setNextMessage(int nextMessage) {
        this.nextMessage = nextMessage;
    }

}
