import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by mercenery on 12.05.2017.
 */
public class UsersFromChat {
    private static Connection connection_u;
    private static PreparedStatement preparedStatement_u;
    private static ResultSet resultSetUsers;
    private static ResultSetMetaData resultSetMetaDataUsers;
    private static Properties dbConSet = new Properties();
    private static int userNumOfClmns;

    private static List<Users> users = new ArrayList<>();

    static {

        try {
            dbConSet.loadFromXML(new FileInputStream("configsDB_Users.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class.forName(dbConSet.getProperty("dbdriver"));
            System.out.println("Driver for \"users\" loaded!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection_u = DriverManager.getConnection(dbConSet.getProperty("dburl"),
                    dbConSet.getProperty("dbuser"),
                    dbConSet.getProperty("dbpassword"));
            System.out.println("Connection for \"users\" established ! ");

            preparedStatement_u = connection_u.prepareStatement("select * from users;");
            System.out.println("Statement created ! ");

            resultSetUsers = preparedStatement_u.executeQuery();
            System.out.println("ResultSet for \"users\" got !");

            resultSetMetaDataUsers = preparedStatement_u.getMetaData();
            System.out.println("Metadata for \"users\" got !");

            System.out.println();

            userNumOfClmns = resultSetMetaDataUsers.getColumnCount();
            System.out.println("Number of Columns \"users\"  got ! = " + userNumOfClmns + ".");
            System.out.println("");

// user data variables declaration
            int userNameOfClmnUId;
            String userNameOfClmnLogin;
            String userNameOfClmnPas;

// user column labels data variables declaration + initialization
            String ClmnUId = resultSetMetaDataUsers.getColumnLabel(1);
            String ClmnLogin = resultSetMetaDataUsers.getColumnLabel(2);
            String ClmnPas = resultSetMetaDataUsers.getColumnLabel(3);

// user data variables initialisation & base of users of class - User populating
            int i = 0;
            while (resultSetUsers.next()) {
                userNameOfClmnUId = Integer.parseInt(resultSetUsers.getString(ClmnUId));
                userNameOfClmnLogin = resultSetUsers.getString(ClmnLogin);
                userNameOfClmnPas = resultSetUsers.getString(ClmnPas);
                Users temp_u = new Users(userNameOfClmnUId, userNameOfClmnLogin, userNameOfClmnPas);

                System.out.println(userNameOfClmnLogin);

                users.add(i, temp_u);
                i++;
            }

            System.out.println("User database full filled from \"users\" !");
            System.out.println();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllUsers() {
        System.out.println(users.size());
        for (Users us :
                users) {
            System.out.println("ID = " + us.getUserID() + "| Login = " + us.getUserName() + "| Password = " + us.getUserPassword());
        }
    }

}
