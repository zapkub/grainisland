/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.game.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import sit.int303.grianisland.datasource.GrainIslandConnectionFactory;

/**
 *
 * @author Vable
 */
public class Map {
    private int mapId;
    private String mapName;
    private HashMap<Integer,Npc> npcs=new HashMap<Integer,Npc>();
    private HashMap<Integer,Building> buildings=new HashMap<Integer,Building>();

    public HashMap<Integer, Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(HashMap<Integer, Building> buildings) {
        this.buildings = buildings;
    }

    public HashMap<Integer, Npc> getNpcs() {
        return npcs;
    }

    public void setNpcs(HashMap<Integer, Npc> npcs) {
        this.npcs = npcs;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public Npc getNpc(int npcId)
    {
        return npcs.get(npcId);
    }

    public void loadNpcs() throws SQLException, ClassNotFoundException
    {
        Connection conn=GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement loadNpcs = conn.prepareStatement("SELECT * FROM npc WHERE mapid=?");
        loadNpcs.setInt(1, mapId);
        ResultSet resultSet=loadNpcs.executeQuery();
        while(resultSet.next())
        {
            Npc npc = new Npc();
            int npcid=resultSet.getInt("npcid");
            npc.setNpcId(npcid);
            npc.setNpcName(resultSet.getString("npcname"));
            npc.setPosX(resultSet.getInt("posX"));
            npc.setPosY(resultSet.getInt("posY"));
            //load quest
            npc.loadQuests();
            npcs.put( npcid, npc);
        }
    }

    public void loadBuildings() throws SQLException, ClassNotFoundException
    {
        Connection conn=GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement loadBuildings = conn.prepareStatement("SELECT * FROM building WHERE mapid=?");
        loadBuildings.setInt(1, mapId);
        ResultSet resultSet=loadBuildings.executeQuery();
        while(resultSet.next())
        {
            Building building = new Building();
            building.setBuildingId(resultSet.getInt("buildingid"));
            building.setBuildingName(resultSet.getString("buildingName"));
            building.setRole(resultSet.getString("role"));
            building.setPosX(resultSet.getInt("posX"));
            building.setPosY(resultSet.getInt("posY"));
            buildings.put(resultSet.getInt("buildingid"), building);
        }
    }

}
