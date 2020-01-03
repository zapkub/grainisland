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
import sit.int303.grianisland.util.StatusMessage;

/**
 *
 * @author Vable
 */
public class MapManager {

    private HashMap<Integer,Map> maps = new HashMap<Integer,Map>();

    public HashMap<Integer, Map> getMaps() {
        return maps;
    }

    public void setMaps(HashMap<Integer, Map> maps) {
        this.maps = maps;
    }

    public Map getMap(int mapId)
    {
        return maps.get(mapId);
    }

    public StatusMessage loadMap() throws SQLException, ClassNotFoundException
    {
        StatusMessage status = new StatusMessage(true,"Can not load Map!!");
        Connection conn=GrainIslandConnectionFactory.getDatabaseConection();
        PreparedStatement loadMapStatement = conn.prepareStatement("SELECT * FROM map");
        ResultSet resultSet=loadMapStatement.executeQuery();
        while(resultSet.next())
        {
            Map map = new Map();
            int mapid=resultSet.getInt("mapid");
            map.setMapId(mapid);
            map.setMapName(resultSet.getString("mapname"));
            //load npc
            map.loadNpcs();
            map.loadBuildings();
            maps.put(mapid, map);
            status.setError(false);
        }
        return status;
    }

}
