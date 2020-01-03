package sit.int303.grianisland.core.model;

import sit.int303.grianisland.datasource.GrainIslandConnectionFactory;
import sit.int303.grianisland.util.StatusMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import sit.int303.grianisland.puts.model.PutsBoard;


/**
 *
 * @author Varavut
 * คลาส Player ใช้สำหรับเก็บค่าต่างๆของ Player และจัดการสิ่งต่างๆ เกี่ยวกับ Player
 */

public class Player{
    private int id;//id ใน DB
    private String name;//ชื่อผู้ใช้
    private String password;//รหัสผ่าน
    private String email;//email
    private Date birthDate;//วันเกิด
    private Double money;//เงินที่มี
    private LinkedList<Integer> quests = new LinkedList<Integer>();//เควสท์ที่ได้รับแล้ว
    private byte clan;//เผ่าที่เลือก
    private byte sex;
    private int map;
    private PutsBoard putsBoard;
    private String avatar;
    private String authString;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAuthString() {
        return authString;
    }

    public void setAuthString(String authString) throws SQLException, ClassNotFoundException {
        Connection conn =GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement setAuth = conn.prepareStatement("UPDATE user SET authString=? WHERE UserId=?");
        setAuth.setString(1, authString);
        setAuth.setInt(2, id);
        setAuth.executeUpdate();
        this.authString = authString;
    }
    
    public StatusMessage loginWithAuthString(String authString) throws SQLException, ClassNotFoundException
    {
        StatusMessage status = new  StatusMessage(true);
        Connection conn =GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement loginAuth = conn.prepareStatement("SELECT * FROM user WHERE authString=?");
        loginAuth.setString(1, authString);
        ResultSet result=loginAuth.executeQuery();
        if(result.next())
        {
            this.birthDate=new Date(result.getLong("birthdate"));
            this.id=result.getInt("UserId");
            this.name = result.getString("name");
            this.sex=result.getByte("sex");
            this.avatar=result.getString("avatar");
            this.role=result.getString("role");
            this.authString=result.getString("authString");
            this.password=result.getString("password");
            loginAuth = conn.prepareStatement("SELECT * FROM player WHERE Userid=?");
            loginAuth.setInt(1, this.id);
            ResultSet result2=loginAuth.executeQuery();
            result2.next();
            this.money = result2.getDouble("money");
            this.clan=result2.getByte("ClanId");
            this.map=result2.getInt("mapId");
            putsBoard=new PutsBoard(id);
            loadQuests();
            status.setError(false);
        }
        else
        {
            status.setMessage("Auth String is Invalid");
        }
        return status;
    }

    public int getMap(){
        return map;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getMoney() throws SQLException, ClassNotFoundException {
         Connection con = GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement ps = con.prepareStatement("SELECT money FROM player WHERE userid=?");
        ps.setInt(1, id);
        ResultSet rs =ps.executeQuery();
        rs.next();
        this.money=rs.getDouble("money");


        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getClan() {
        return clan;
    }

    public StatusMessage setClan(byte clan) throws SQLException, ClassNotFoundException {
        StatusMessage error=new StatusMessage(true);
        Connection databaseConnection=GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement statement = databaseConnection.prepareStatement("UPDATE Player SET ClanId=? WHERE UserId=?");
        statement.setByte(1, clan);
        statement.setInt(2, this.id);
        if(statement.executeUpdate()>0)
        {
            error.setError(false);
            this.clan = clan;
        }
        else
        {
            error.setMessage("can not select clan");
        }
        return error;
    }

    public StatusMessage login() throws SQLException, ClassNotFoundException//ตรวจสอบผู้ใช้เพื่อเข้าระบบ
    {
        StatusMessage error=new StatusMessage(true);
        Connection databaseConnection=GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement loginStatement = databaseConnection.prepareStatement("SELECT * FROM user WHERE email=? AND password=?");
        loginStatement.setString(1, email);
        loginStatement.setString(2, password);
        ResultSet result=loginStatement.executeQuery();
        if(result.next())
        {
            this.birthDate=new Date(result.getLong("birthdate"));
            this.id=result.getInt("UserId");
            this.name = result.getString("name");
            this.sex=result.getByte("sex");
            this.avatar=result.getString("avatar");
            this.role=result.getString("role");
            this.authString=result.getString("authString");
            loginStatement = databaseConnection.prepareStatement("SELECT * FROM player WHERE Userid=?");
            loginStatement.setInt(1, this.id);
            ResultSet result2=loginStatement.executeQuery();
            result2.next();
            this.money = result2.getDouble("money");
            this.clan=result2.getByte("ClanId");
            this.map=result2.getInt("mapId");
            putsBoard=new PutsBoard(id);
            loadQuests();
            error.setError(false);
        }
        else
        {
            error.setMessage("Invalid email or password");
        }
        return error;
    }
    
    public void loadQuests() throws SQLException, ClassNotFoundException
    {
        Connection databaseConnection=GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement loadQuestsStatement = databaseConnection.prepareStatement("SELECT * FROM player_has_quest WHERE User_UserID=?");
        loadQuestsStatement.setInt(1, id);
        ResultSet result=loadQuestsStatement.executeQuery();
        while(result.next())
        {
            quests.add(result.getInt("Quest_QuestID"));
        } 
    }

    public boolean checkPlayer()//ตรวจสอบว่ามีชื่อผู้ใช้นี้แล้วหรือไม่
    {
        boolean isComplete=false;
        return isComplete;
    }

    public StatusMessage register() throws SQLException, ClassNotFoundException//ลงทะเบียนผู้ใช้ใหม่
    {
        StatusMessage error=new StatusMessage(true);
        Connection databaseConnection=GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement registerStatement = databaseConnection.prepareStatement("INSERT INTO user(name,password,email,birthdate,sex) VALUES(?,?,?,?,?)");
        registerStatement.setString(1, name);
        registerStatement.setString(2, password);
        registerStatement.setString(3, email);
        registerStatement.setLong(4,birthDate.getTime());
        registerStatement.setByte(5, sex);
        if(registerStatement.executeUpdate()>0)
        {
            this.map=0;
            this.clan=0;
            PreparedStatement loginStatement = databaseConnection.prepareStatement("SELECT * FROM user WHERE email=? AND password=?");
            loginStatement.setString(1, email);
            loginStatement.setString(2, password);
            ResultSet result=loginStatement.executeQuery();
            result.next();
            this.id=result.getInt("UserId");
            registerStatement = databaseConnection.prepareStatement("INSERT INTO Player(UserId) VALUES(?)");
            registerStatement.setInt(1, this.id);
            if(registerStatement.executeUpdate()>0)
            {
                error.setError(false);
            }
            else
            {
                error.setMessage("ไม่สามารถลงทะเบียนได้");
            }
        }
        else
        {
            error.setMessage("ไม่สามารถลงทะเบียนได้");
        }
        return error;
    }


    public static String getName(int userId) throws SQLException, ClassNotFoundException
    {
        String name = null;
        StatusMessage error=new StatusMessage(true);
        Connection databaseConnection=GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement getuserNameStatement = databaseConnection.prepareStatement("SELECT * FROM user WHERE UserId=?");
        getuserNameStatement.setInt(1,userId);
        ResultSet resultSet=getuserNameStatement.executeQuery();
        if(resultSet.next())
        {
            name=resultSet.getString("name");
        }
        return name;
    }

    public static String getAvatar(int userId) throws SQLException, ClassNotFoundException
    {
        String avatar = null;
        StatusMessage error=new StatusMessage(true);
        Connection databaseConnection=GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement getuserAvatarStatement = databaseConnection.prepareStatement("SELECT * FROM user WHERE userid=?");
        getuserAvatarStatement.setInt(1,userId);
        ResultSet resultSet=getuserAvatarStatement.executeQuery();
        if(resultSet.next())
        {
            avatar=resultSet.getString("avatar");
        }
        return avatar;
    }


     public static String getAvatar(String userId) throws SQLException, ClassNotFoundException
    {
        String avatar = null;
        StatusMessage error=new StatusMessage(true);
        Connection databaseConnection=GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement getuserAvatarStatement = databaseConnection.prepareStatement("SELECT * FROM user WHERE userid=?");
        getuserAvatarStatement.setInt(1,Integer.parseInt(userId));
        ResultSet resultSet=getuserAvatarStatement.executeQuery();
        if(resultSet.next())
        {
            avatar=resultSet.getString("avatar");
        }
        return avatar;
    }

    public boolean changepassword(String oldpassword,String newpassword,String newpassagn) throws SQLException, ClassNotFoundException{
        if(password.equals(oldpassword))
        {
            if(newpassword.equals(newpassagn))
            {
                Connection cnn = GrainIslandConnectionFactory.getDatabaseConection();
                String sql = "UPDATE user set password=?  WHERE email=?";
                PreparedStatement ps = cnn.prepareStatement(sql);
                ps.setString(1,newpassword);
                ps.setString(2,email);
                ps.executeUpdate();
                password=newpassword;
                return true;
            }
            else
            {
                 return false;
            }
        }
        return false;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) throws SQLException, ClassNotFoundException {//เปลี่ยนภาพประจำตัว
        this.avatar = avatar;
        Connection con = GrainIslandConnectionFactory.getDatabaseConection();
        String sql = "UPDATE user set avatar=?  WHERE userid=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,avatar);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    public PutsBoard getPutsBoard() {
        if(putsBoard==null)
            putsBoard=new PutsBoard(id);
        return putsBoard;
    }

    public void setPutsBoard(PutsBoard putsBoard) {
        this.putsBoard = putsBoard;
    }

    public LinkedList<Integer> getQuests() {
        return quests;
    }

    public void setQuests(LinkedList<Integer> quests) {
        this.quests = quests;
    }
    public LinkedList<Player> getFriend() throws SQLException, ClassNotFoundException
    {
        LinkedList<Player> friendList=new LinkedList<Player>();
        Connection con = GrainIslandConnectionFactory.getDatabaseConection();
        String sql = "SELECT * FROM friend JOIN user ON(friend.requestee=user.userid) WHERE friend.isaccept=1 AND friend.requester=? "
                + "UNION "
                + "SELECT * FROM friend JOIN user ON(friend.requester=user.userid) WHERE friend.isaccept=1 AND friend.requestee=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setInt(2, id);
        ResultSet result=ps.executeQuery();
        while(result.next())
        {
            Player p = new Player();
            p.setId(result.getInt("userid"));
            p.setName(result.getString("name"));
            p.setAvatar(result.getString("avatar"));
            friendList.add(p);
        }
        return friendList;
    }

    public LinkedList<Player> getRequestFriend() throws SQLException, ClassNotFoundException
    {
        LinkedList<Player> friendList=new LinkedList<Player>();
        Connection con = GrainIslandConnectionFactory.getDatabaseConection();
        String sql = "SELECT * FROM friend JOIN user ON(friend.requester=user.userid) WHERE friend.requestee=? AND friend.isaccept=0 ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet result=ps.executeQuery();
        while(result.next())
        {
            Player p = new Player();
            p.setId(result.getInt("userid"));
            p.setName(result.getString("name"));
            p.setAvatar(result.getString("avatar"));
            friendList.add(p);
        }
        return friendList;
    }

    public boolean isFriend(int targetId) throws SQLException, ClassNotFoundException
    {
        boolean isFriend=false;
        Player p2 = new Player();
        p2.setId(targetId);
        if(getFriend().contains(p2))
            isFriend=true;
        return isFriend;
    }

    public int inRequestFriend(int targetId) throws SQLException, ClassNotFoundException//เป็นเพื่อน return 0 ร้องขอไป return 1 ต้องตอบรับ return -1 ไม่เป็น return -2 ตัวเอง return 2
    {
        int inRequest=-2;
        if(targetId==id)
            return 2;
        if(!isFriend(targetId))
        {
            Connection con = GrainIslandConnectionFactory.getDatabaseConection();
            String sql = "SELECT * FROM friend JOIN user ON(friend.requestee=user.userid) WHERE friend.isaccept=0 AND friend.requester=? AND friend.requestee=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, targetId);
            ResultSet result=ps.executeQuery();
            if(result.next())
                inRequest= 1;
            else
            {
                ps = con.prepareStatement(sql);
                ps.setInt(1, targetId);
                ps.setInt(2, id);
                result=ps.executeQuery();
                if(result.next())
                    inRequest= -1;
            }
        }
        else
        {
            inRequest= 0;
        }

        return inRequest;
    }
    public boolean acceptFriend(int targetId) throws SQLException, ClassNotFoundException
    {
        boolean complete =false;
        Connection con = GrainIslandConnectionFactory.getDatabaseConection();
        String sql = "UPDATE friend SET isaccept=1 WHERE (requester=? AND requestee=?) OR (requestee=? AND requester=?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setInt(2, targetId);
        ps.setInt(3, id);
        ps.setInt(4, targetId);
        if(ps.executeUpdate()>0)
        {
            complete=true;
        }
        else
        {
            sql = "INSERT INTO friend(requester,requestee) values(?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, targetId);
            if(ps.executeUpdate()>0)
            {
                 complete=true;
            }
        }
        return complete;
    }
    public boolean deleteFriend(int targetId) throws SQLException, ClassNotFoundException
    {
        boolean complete =false;
        Connection con = GrainIslandConnectionFactory.getDatabaseConection();
        String sql = "DELETE FROM friend WHERE (requester=? AND requestee=?) OR (requestee=? AND requester=?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setInt(2, targetId);
        ps.setInt(3, id);
        ps.setInt(4, targetId);
        if(ps.executeUpdate()>0)
        {
            complete=true;
        }
        return complete;
    }

    @Override
    public boolean equals(Object obj) {
        Player p2=(Player)obj;
        if(this.id==p2.getId())
            return true;
        else
            return false;
    }


    public static LinkedList getPlayerlist() throws SQLException, ClassNotFoundException{

        Connection con = GrainIslandConnectionFactory.getDatabaseConection();
            String sql = "SELECT * FROM user";
            LinkedList<Player> plist = new LinkedList<Player>();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet result=ps.executeQuery();

                    while(result.next()){
                        Player tmp = new Player();
                            tmp.setId(result.getInt("userid"));
                            tmp.setName(result.getString("name"));

                        plist.add(tmp);

                    }

            return plist;
    }

    public static LinkedList<Player> findPlayer(String keyword) throws SQLException, ClassNotFoundException{

        Connection con = GrainIslandConnectionFactory.getDatabaseConection();
        String sql = "SELECT * FROM user WHERE name LIKE ?";
        LinkedList<Player> plist = new LinkedList<Player>();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, "%"+keyword+"%");
        ResultSet result=ps.executeQuery();
        while(result.next())
        {
            Player tmp = new Player();
            tmp.setId(result.getInt("userid"));
            tmp.setName(result.getString("name"));
            tmp.setAvatar(result.getString("avatar"));
            plist.add(tmp);
        }
        return plist;
    }

    public static String getNameByid(String id) throws SQLException, ClassNotFoundException{
        
        Connection con = GrainIslandConnectionFactory.getDatabaseConection();
        String sql = "SELECT * FROM user where userid=(?)";
        LinkedList<Player> plist = new LinkedList<Player>();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        ResultSet result=ps.executeQuery();
        result.next();
        return result.getString("name");
    }

    public static String getIdByName(String name) throws SQLException, ClassNotFoundException{

        Connection con = GrainIslandConnectionFactory.getDatabaseConection();
        String sql = "SELECT * FROM user where name=(?)";
        LinkedList<Player> plist = new LinkedList<Player>();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet result=ps.executeQuery();
        result.next();
        return result.getString("id");
    }

    public boolean addQuest(int questId) throws SQLException, ClassNotFoundException
    {
        boolean complete=false;
        Connection con = GrainIslandConnectionFactory.getDatabaseConection();
        String sql = "INSERT INTO player_has_quest(User_UserID,Quest_QuestID) VALUES(?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setInt(2,questId);
        if(ps.executeUpdate()>0)
        {
            complete=true;
        }
        return complete;
    }

    public synchronized boolean removeMoney(int money) throws SQLException, ClassNotFoundException
    {
        boolean complete=false;
        Connection con = GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement ps = con.prepareStatement("SELECT money FROM player WHERE userid=?");
        ps.setInt(1, id);
        ResultSet rs =ps.executeQuery();
        rs.next();
        this.money=rs.getDouble("money");
        if(this.money-money>=0)
        {
           
            ps = con.prepareStatement("UPDATE player SET money=? WHERE userid=?");
            ps.setDouble(1, this.money-money);
            ps.setInt(2, id);
            if(ps.executeUpdate()>0)
            {
                this.money=this.money-money;
                complete=true;
            }
        }
        return complete;
    }
     public synchronized boolean removeMoneyById(int money,String id) throws SQLException, ClassNotFoundException
    {
            
        boolean complete=false;
        Connection con = GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement ps = con.prepareStatement("SELECT money FROM player WHERE userid=?");
        ps.setInt(1, Integer.parseInt(id));
        ResultSet rs =ps.executeQuery();
        rs.next();
        this.money=rs.getDouble("money");
        if(this.money-money>=0)
        {
           
            ps = con.prepareStatement("UPDATE player SET money=? WHERE userid=?");
            ps.setDouble(1, this.money-money);
            ps.setInt(2, Integer.parseInt(id));
            if(ps.executeUpdate()>0)
            {
                this.money=this.money-money;
                complete=true;
            }
        }
        return complete;
    }



    public String getAvatarByName(String name) throws SQLException, ClassNotFoundException{

        Connection con = GrainIslandConnectionFactory.getDatabaseConection();
        String sql = "SELECT * FROM user where name=(?)";
        LinkedList<Player> plist = new LinkedList<Player>();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet result=ps.executeQuery();
        result.next();
        return result.getString("Avatar");
    }
}
