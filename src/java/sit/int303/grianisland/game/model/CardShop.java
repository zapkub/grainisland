/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.game.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import sit.int303.grianisland.datasource.GrainIslandConnectionFactory;

/**
 *
 * @author Zapdos
 */
public class CardShop {

     LinkedList cardlist = new LinkedList();
     Iterator it = cardlist.iterator();
        public LinkedList<Card> getStorelist() throws SQLException, ClassNotFoundException
        {
            Connection conn  = GrainIslandConnectionFactory.getDatabaseConection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM card WHERE inSale=1");
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next())
            {
                Card card = new Card();
                card.setCardId(resultSet.getInt("cardId"));
                card.setCardName(resultSet.getString("cardName"));
                card.setCardDesc(resultSet.getString("cardDesc"));
                card.setCardPrice(resultSet.getInt("Price"));
                cardlist.add(card);
            }
            return cardlist;
        }

        public boolean buyCard(int cardId,int playerId) throws SQLException, ClassNotFoundException
        {
            boolean complete=false;
            Connection conn = GrainIslandConnectionFactory.getDatabaseConection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM card WHERE CardId=? AND inSale=1");
            ps.setInt(1, cardId);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                CardManager cm =new CardManager(playerId);
                if(cm.addCard(cardId))
                {
                    complete=true;
                }
            }
            return complete;
        }
        
        public boolean sellCard(int CardId,int playerId) throws SQLException, ClassNotFoundException
        {
            boolean complete=false;
            CardManager cm =new CardManager(playerId);
            if(cm.removeCard(CardId))
            {
                complete=true;
            }
            return complete;
        }

}
