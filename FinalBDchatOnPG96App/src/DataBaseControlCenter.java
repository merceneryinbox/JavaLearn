import javax.management.relation.Role;
import java.sql.*;

/**
 * Created by mercenery on 11.05.2017.
 */
public class DataBaseControlCenter {

    public static void main(String[] args) throws SQLException {
        UsersFromChat userss = new UsersFromChat();
        userss.getAllUsers();

        System.out.println();

        RolesFromChat rolles = new RolesFromChat();
        rolles.getAllRoles();
    }
}

