/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.game.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import sit.int303.grianisland.datasource.GrainIslandConnectionFactory;
import sit.int303.grianisland.core.model.Player;
import sit.int303.grianisland.util.StatusMessage;

/**
 *
 * @author Vable
 * ใช้ในการจัดการการ์ดของผู้เล่นแต่ละคน
 */
public class CardManager {

    int playerid =0;
    LinkedList cards = new LinkedList();
        public CardManager(int id){

            playerid = id;
        }

        public LinkedList<Card> loadCard() throws SQLException, ClassNotFoundException{

            
         Connection conn=GrainIslandConnectionFactory.getDatabaseConection();
         String sql = "SELECT  * FROM player_has_card JOIN card ON (player_has_card.cardId = card.cardId) WHERE   userid = ?; ";
         PreparedStatement loadCardStatement = conn.prepareStatement(sql);
         loadCardStatement.setInt(1, playerid);
         ResultSet resultSet=loadCardStatement.executeQuery();
         while(resultSet.next())
         {
            Card card = new Card();
            card.setCardId(resultSet.getInt("cardId"));
            card.setCardName(resultSet.getString("cardName"));
            card.setCardDesc(resultSet.getString("cardDesc"));
            card.setCardAmount(resultSet.getInt("amount"));
            card.setCardPrice(resultSet.getInt("Price"));
            cards.add(card);
         }
        return cards;

        }
        public boolean addCard(int cardId) throws SQLException, ClassNotFoundException
        {
            boolean complete=false;
            Connection conn=GrainIslandConnectionFactory.getDatabaseConection();
            PreparedStatement addCard = conn.prepareStatement("SELECT * from Player_has_card where userid = (?) and cardid =(?)");
            addCard.setInt(1, playerid);
            addCard.setInt(2, cardId);
            ResultSet rs=addCard.executeQuery();
            if(rs.next())
            {
                addCard=conn.prepareStatement("update Player_has_card set amount=(?) where cardid =(?) and userid = (?)");
                addCard.setInt(1, rs.getInt("amount")+1);
                addCard.setInt(2,cardId);
                addCard.setInt(3,playerid);
                if(addCard.executeUpdate()>0)
                    complete=true;
            }
            else
            {
                addCard=conn.prepareStatement("INSERT INTO Player_has_card(userId,cardId,amount) values(?,?,?)");
                addCard.setInt(1,playerid);
                addCard.setInt(2,cardId);
                addCard.setInt(3,1);
                if(addCard.executeUpdate()>0)
                    complete=true;
            }
            return complete;
        }

        public boolean removeCard(int cardId) throws SQLException, ClassNotFoundException
        {
            boolean complete=false;
            Connection conn=GrainIslandConnectionFactory.getDatabaseConection();
            PreparedStatement addCard = conn.prepareStatement("SELECT * from Player_has_card where userid = (?) and cardid =(?)");
            addCard.setInt(1, playerid);
            addCard.setInt(2, cardId);
            ResultSet rs=addCard.executeQuery();
            if(rs.next())
            {
                if(rs.getInt("amount")>1)
                {
                    addCard=conn.prepareStatement("update Player_has_card set amount=(?) where cardid =(?) and userid = (?)");
                    addCard.setInt(1, rs.getInt("amount")-1);
                    addCard.setInt(2,cardId);
                    addCard.setInt(3,playerid);
                    if(addCard.executeUpdate()>0)
                        complete=true;
                }
                else
                {
                    addCard=conn.prepareStatement("DELETE FROM Player_has_card WHERE userId=? AND cardId=?");
                addCard.setInt(1,playerid);
                addCard.setInt(2,cardId);
                if(addCard.executeUpdate()>0)
                    complete=true;
                }
            }
            return complete;
        }
}
