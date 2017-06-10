/**
 * Created by mercenery on 12.05.2017.
 */
public class Users {
    private static int Uid;
    private static String Uname;
    private static String UPassword;


    public Users(int uId, String uname, String upassword) {
        this.Uid = uId;
        this.Uname = uname;
        this.UPassword = upassword;
    }
    public Integer getUserID(){
        return Uid;
    }public String getUserName(){
        return Uname;
    }public String getUserPassword(){
        return UPassword;
    }

}
