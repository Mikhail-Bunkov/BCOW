package server;

import java.sql.*;

public class SQLHandler {
    private static Connection connection;
    private static PreparedStatement getNickname;
    private static PreparedStatement psRegistration;
    private static PreparedStatement psChangeNickname;

    public static boolean connect(){
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            prepareAllStatements();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    private static void prepareAllStatements() throws SQLException {
        getNickname = connection.prepareStatement("SELECT nickname FROM users WHERE login = ? AND password = ?;");
        psRegistration = connection.prepareStatement("INSERT INTO users(login, password, nickname) VALUES (? ,? ,? );");
        psChangeNickname = connection.prepareStatement("UPDATE user SET nickname = ? WHERE nickname = ?;");
    }
    public static void disconnect(){
            try {
                getNickname.close();
                psRegistration.close();
                psChangeNickname.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }
    public static String getNicknameByLoginAndPassword(String login, String password){
        String nickname = null;
        try{
            getNickname.setString(1, login);
            getNickname.setString(2, password);
            ResultSet result = getNickname.executeQuery();
            if(result.next()){
                nickname = result.getString(1);
            }
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nickname;
    }
    public static boolean registration(String login, String password, String nickname){
        try{
        psRegistration.setString(1,login);
        psRegistration.setString(2,password);
        psRegistration.setString(3,nickname);
        psRegistration.executeUpdate();
        return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean changeNickname(String oldNickname, String newNickname){
        try{
            psChangeNickname.setString(1,newNickname);
            psChangeNickname.setString(2,oldNickname);
            psChangeNickname.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
