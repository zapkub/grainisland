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
import sit.int303.grianisland.core.model.Player;
import sit.int303.grianisland.datasource.GrainIslandConnectionFactory;

/**
 *
 * @author Zapdos
 */
public class CardBattle {

        int userid;
        int cardid;
        int opponentid;
        int battleid;
     public CardBattle(){
     super();
     }
    public int getBattleid() {
        return battleid;
    }

    public void setBattleid(int battleid) {
        this.battleid = battleid;
    }

    public int getOpponentid() {
        return opponentid;
    }

    public void setOpponentid(int opponentid) {
        this.opponentid = opponentid;
    }

        public CardBattle(int userid,int cardid,int opponentid){
            this.userid=userid;
            this.cardid=cardid;
            this.opponentid=opponentid;
        }


        public CardBattle(String BattleId) throws SQLException, ClassNotFoundException{
             Connection con = GrainIslandConnectionFactory.getDatabaseConection();
                     PreparedStatement ps = con.prepareStatement("SELECT * FROM Battle where battleID =(?)");
                        ps.setInt(1,Integer.parseInt(BattleId));
                        ResultSet set  = ps.executeQuery();
                            while(set.next()){
                                this.battleid = set.getInt("BattleId");
                                this.userid = set.getInt("userid");
                                this.opponentid = set.getInt("opponentid");
                                this.cardid = set.getInt("cardid");
                            }
        }

    public int getCardid() {
        return cardid;
    }

    public void setCardid(int cardid) {
        this.cardid = cardid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }


    public void addBattle() throws SQLException, ClassNotFoundException{


        Connection con = GrainIslandConnectionFactory.getDatabaseConection();
            String sql = "SELECT * FROM user";
            LinkedList<Player> plist = new LinkedList<Player>();
            PreparedStatement addBattle;
         addBattle=con.prepareStatement("INSERT INTO Battle(userId,cardId,opponentId) values(?,?,?)");
            addBattle.setInt(1,userid);
            addBattle.setInt(2,cardid);
            addBattle.setInt(3, opponentid);
            addBattle.executeUpdate();
    }

    public LinkedList<String> getBattle(int userid) throws SQLException, ClassNotFoundException{
                     Connection con = GrainIslandConnectionFactory.getDatabaseConection();
                     PreparedStatement ps = con.prepareStatement("SELECT battleID FROM `grainisland`.`battle` where opponentID = (?)");
                       ps.setInt(1,userid);
                        LinkedList<String> List= new LinkedList<String>();

                           ResultSet set  = ps.executeQuery();
                            while(set.next()){
                                List.add(set.getString("battleID"));
                            }
            return List;
    }

    public CardBattle getBattleObject(String battleID) throws SQLException, ClassNotFoundException{
    CardBattle cb = new CardBattle();
        Connection con = GrainIslandConnectionFactory.getDatabaseConection();
                     PreparedStatement ps = con.prepareStatement("SELECT * FROM Battle where battleID =(?)");
                        ps.setInt(1,Integer.parseInt(battleID));
                        ResultSet set  = ps.executeQuery();
                            while(set.next()){
                                cb.setCardid(set.getInt("cardid"));

                               cb.setOpponentid(set.getInt("opponentid"));
                            }

            return cb;
    }

    public String getUserIdByBattleId(String Bid) throws SQLException, ClassNotFoundException{
             Connection con = GrainIslandConnectionFactory.getDatabaseConection();
             String userId = "unknow";
                     PreparedStatement ps = con.prepareStatement("SELECT * FROM Battle where battleID =(?)");
                        ps.setInt(1,Integer.parseInt(Bid));
                        ResultSet set  = ps.executeQuery();
                            while(set.next()){
                                userId = set.getString("userID");
                            }
        return userId;
    }
    public String getOpponentIdByBattleId(){


        
        return null;}

    public String getCardIdByBattleId(){
    return null;
    }


    public void dropBattle(String Bid) throws SQLException, ClassNotFoundException{
         Connection con = GrainIslandConnectionFactory.getDatabaseConection();
           
                     PreparedStatement ps = con.prepareStatement("DELETE FROM Battle where battleID =(?)");
                        ps.setInt(1,Integer.parseInt(Bid));
                        ps.executeUpdate();
    }
}
