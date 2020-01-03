package sit.int303.grianisland.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Varavut
 * คลาส GrainIslandConnectionFactory ใช้สำหรับการจัดการการเชื่อมต่อกับฐานข้อมูลของตัวเกม
 */
public class GrainIslandConnectionFactory {
//
    private static Connection databaseConection;//ตัวแปร Connection สำหรับเก็บ Connection ที่ใช้ต่อฐานข้อมูล
    private static String connectionString="jdbc:mysql://localhost:3306/grainisland"; //สตริงที่ใช้ต่อ DB
    private static String dbUserName="root"; //username ที่ใช่ต่อ DB
    private static String dbPassword=""; //password ที่ใช่ต่อ DB
    private static String dbDriver="com.mysql.jdbc.Driver"; //Driver ที่ใช่ต่อ DB

    public static Connection getDatabaseConection() throws SQLException, ClassNotFoundException {//method สำหรับขอ COnnection
        //ถ้า Connection ยังไม่ถูกสร้าง หรือ ถูกปิดไปแล้ว
        if(databaseConection==null || databaseConection.isClosed())
        {
            Class.forName(dbDriver); //โหลด Driver
            databaseConection = DriverManager.getConnection(connectionString,dbUserName,dbPassword); //ขอ Connection
        }
        return databaseConection; //ส่ง Connection กลับ
    }
    
    public static void closeConnection() throws SQLException
    {
        //ถ้า Connection ถูกสร้างแล้ว และ ยังไม่ถูกปิด
        if(databaseConection!=null && !databaseConection.isClosed())
        {
            databaseConection.close(); //ปิด Connection
        }
    }

}
