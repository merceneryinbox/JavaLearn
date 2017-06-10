/**
 * Created by mercenery on 12.05.2017.
 */
public class Roles {
    private static int Rid;
    private static boolean Rread = false;
    private static boolean Rwrite = false;;
    private static boolean Rupd = false;;
    private static boolean Rrevoke = false;;
    private static String Rmisc = null;

    public Roles(int rolesNameOfClmnID, String rolesNameOfClmnNamess,
                 boolean  rolesNameOfClmnCanRead, boolean  rolesNameOfClmnCanWrite,
                 boolean  rolesNameOfClmnCanUpd, boolean  rolesNameOfClmnCanRevoke,
                 String rolesNameOfClmnMisc) {
        this.Rid = rolesNameOfClmnID;
        this.Rread = rolesNameOfClmnCanRead;
        this.Rwrite = rolesNameOfClmnCanWrite;
        this.Rupd = rolesNameOfClmnCanUpd;
        this.Rrevoke = rolesNameOfClmnCanRevoke;
        this.Rmisc= rolesNameOfClmnMisc;
    }

    public static int getRid() {
        return Rid;
    }

    public static boolean isRread() {
        return Rread;
    }

    public static boolean isRwrite() {
        return Rwrite;
    }

    public static boolean isRupd() {
        return Rupd;
    }

    public static boolean isRrevoke() {
        return Rrevoke;
    }

    public static String getRmisc() {
        return Rmisc;
    }
}
