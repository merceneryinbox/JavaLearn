import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by mercenery on 12.05.2017.
 */
public class RolesFromChat {
    private static Connection connection_r;
    private static PreparedStatement preparedStatement_r;
    private static ResultSet resultSetRoles;
    private static ResultSetMetaData resultSetMetaDataRoles;
    private static Properties dbRolesConSet = new Properties();

    private static List<Roles> roles = new ArrayList<>();

    static {

        try {
            dbRolesConSet.loadFromXML(new FileInputStream("configsDB_Roles.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class.forName(dbRolesConSet.getProperty("dbdriver"));
            System.out.println("Driver for table  \"roles\"  loaded!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection_r = DriverManager.getConnection(dbRolesConSet.getProperty("dburl"),
                    dbRolesConSet.getProperty("dbuser"),
                    dbRolesConSet.getProperty("dbpassword"));
            System.out.println("Connection with  \"roles\"  established ! ");

            preparedStatement_r = connection_r.prepareStatement("select * from roles;");
            System.out.println("Statement for   \"roles\"  created ! ");

            resultSetRoles = preparedStatement_r.executeQuery();
            System.out.println("ResultSet of \"roles\" got !");

            resultSetMetaDataRoles = preparedStatement_r.getMetaData();
            System.out.println("Metadata of \"roles\"  got !");

            System.out.println();

            int roleNumOfClmns = resultSetMetaDataRoles.getColumnCount();
            System.out.println("Number Roles of Columns  \"roles\"  -  got ! = " + roleNumOfClmns + ".");
            System.out.println("");

// user data variables declaration
            int rolesNumOfClmns = resultSetMetaDataRoles.getColumnCount();
            System.out.println("Number of  \"roles\"  columns got!");
            System.out.println();

            int rolesNameOfClmnID;
            String rolesNameOfClmnNamess;
            boolean rolesNameOfClmnCanRead;
            boolean rolesNameOfClmnCanWrite;
            boolean rolesNameOfClmnCanUpd;
            boolean rolesNameOfClmnCanRevoke;
            String rolesNameOfClmnMisc;

// user data variables initialization & base of users of class - User population
            int i = 0;
            while (resultSetRoles.next()) {
                rolesNameOfClmnID = Integer.parseInt(resultSetRoles.getString(1));
                rolesNameOfClmnNamess = resultSetRoles.getString(2);
                rolesNameOfClmnCanRead = Boolean.parseBoolean(resultSetRoles.getString(3));
                rolesNameOfClmnCanWrite = Boolean.parseBoolean(resultSetRoles.getString(4));
                rolesNameOfClmnCanUpd = Boolean.parseBoolean(resultSetRoles.getString(5));
                rolesNameOfClmnCanRevoke = Boolean.parseBoolean(resultSetRoles.getString(6));
                rolesNameOfClmnMisc = resultSetRoles.getString(7);
                Roles temp_r = new Roles(rolesNameOfClmnID, rolesNameOfClmnNamess, rolesNameOfClmnCanRead,
                        rolesNameOfClmnCanWrite, rolesNameOfClmnCanUpd, rolesNameOfClmnCanRevoke, rolesNameOfClmnMisc);

                System.out.println(rolesNameOfClmnNamess);

                roles.add(i,temp_r);
                i++;
            }

            System.out.println("Roles database had been full filled!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllRoles() {
        for (Roles rl :
                roles) {
            System.out.println(
                    rl.getRid() + " - |"
                            + rl.isRread() + " - |"
                            + rl.isRwrite() + " - |"
                            + rl.isRupd() + " - |"
                            + rl.isRrevoke() + " - |"
                            + rl.getRmisc());
        }
    }
}
