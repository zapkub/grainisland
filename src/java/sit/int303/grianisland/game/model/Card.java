/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.game.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sit.int303.grianisland.datasource.GrainIslandConnectionFactory;

/**
 *
 * @author Vable
 */
public class Card implements Serializable {
    private int cardId;
    private String cardName;
    private String cardDesc;
    private String cardProperty;
    private int cardAmount;
    private int cardPrice;
    private int ATK;

    public int getATK() {
        return ATK;
    }

    private void setATK(int ATK) {
        this.ATK = ATK;
    }

    public int getDEF() {
        return DEF;
    }

    public void setDEF(int DEF) {
        this.DEF = DEF;
    }
    private int DEF;
    public int getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(int cardAmount) {
        this.cardAmount = cardAmount;
    }

    public int getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(int cardPrice) {
        this.cardPrice = cardPrice;
    }
 /*

    public Card(int cardId, String cardName) {
        this.cardId = cardId;
        this.cardName = cardName;
    }

    public Card(int cardId, String cardName, String cardDesc) {
        this.cardId = cardId;
        this.cardName = cardName;
        this.cardDesc = cardDesc;
    }

    public Card(int cardId, String cardName, String cardDesc, String cardProperty) {
        this.cardId = cardId;
        this.cardName = cardName;
        this.cardDesc = cardDesc;
        this.cardProperty = cardProperty;
    }*/

    public String getCardDesc() {
        return cardDesc;
    }

    public void setCardDesc(String cardDesc) {
        this.cardDesc = cardDesc;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardProperty() {
        return cardProperty;
    }

    public void setCardProperty(String cardProperty) {
        this.cardProperty = cardProperty;
    }
    public static String getCardName(int cardId) throws SQLException, ClassNotFoundException
    {
        String name="";
        Connection conn=GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM card WHERE CardId=?");
        ps.setInt(1, cardId);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
            name=rs.getString("cardName");
        }
        return name;
    }
    public static Card getCard(int cardId) throws SQLException, ClassNotFoundException
    {
        Card card=new Card();
        Connection conn=GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM card WHERE CardId=?");
        ps.setInt(1, cardId);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
            card.setCardId(rs.getInt("cardId"));
            card.setCardName(rs.getString("cardName"));
            card.setCardDesc(rs.getString("cardDesc"));
            card.setCardPrice(rs.getInt("Price"));
                card.setATK(rs.getInt("cardATK"));
                card.setDEF(rs.getInt("cardDEF"));
        }
        return card;
    }

   

}
