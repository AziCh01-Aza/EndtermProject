import java.sql.*;

public class PostgreSQL {

    Connection con = null;
    Statement stmt = null;
    ResultSet res = null;

    public PostgreSQL(){
        connect("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000");
    }
    /*
    create table winners(
	name varchar (12)
    )
    */

    public void connect(String url, String user, String pass) {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Connection Established!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String execSQL(String sql) {
        String ans = "";
        try {

            stmt = con.createStatement();
            String[] temp = sql.split(" ");// type of query for ex. sql = SELECT * FROM employee //
            String type = temp[0].toLowerCase(); // type = select

            switch (type){
                case "select" : {
                    res = stmt.executeQuery(sql);

                    int n = res.getMetaData().getColumnCount(); // amount of columns in one row

                    while(res.next()){
                        for (int i = 1; i <= n; i++) {
                            ans += res.getString(i) + "\t";     // here we save our data retireved from database
                        }
                        ans += "\n";
                    }
                    break;
                }
                case "create" :
                case "delete" :
                case "insert" :
                case "update" :
                case "alter" : {

                    int aff_rows = stmt.executeUpdate(sql);
                    ans += "Rows were affected after query " + aff_rows;
                    break;
                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ans;
    }

    public void disconnect(String url, String user, String pass) {
        try {

            if (con != null)    con.close();
            if (stmt != null)   stmt.close();
            if (res != null)    res.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
