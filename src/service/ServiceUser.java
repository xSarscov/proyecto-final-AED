package service;

import model.ModelUser;
import connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Random;
import model.ModelLogin;
import model.ModelReset;

public class ServiceUser {

    private final Connection con;

    public ServiceUser() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    public ModelUser login(ModelLogin login) throws SQLException {
        ModelUser data = null;
        String email = login.getEmail();
        String password = login.getPassword();

        String loginQuery = "SELECT TOP 1 UserID, Username, Email FROM usuarios WHERE Email COLLATE Latin1_General_BIN = ? AND [Password] COLLATE Latin1_General_BIN = ? AND [Status] = 'Verified'";

        try (PreparedStatement p = con.prepareStatement(loginQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            p.setString(1, email);
            p.setString(2, password);

            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    int userID = r.getInt(1);
                    String Username = r.getString(2);
                    String Email = r.getString(3);
                    data = new ModelUser(userID, Username, Email, "");
                }
            }

        }

        return data;
    }

    public void insertUser(ModelUser user) throws SQLException {
        String username = user.getUserName();
        String email = user.getEmail();
        String password = user.getPassword();
        String verifyCode = generateVerifyCode();

        String insertQuery = "INSERT INTO usuarios (Username, Email, [Password], VerifyCode) VALUES (?, ?, ?, ?)";

        try (PreparedStatement p = con.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            p.setString(1, username);
            p.setString(2, email);
            p.setString(3, password);
            p.setString(4, verifyCode);

            p.executeUpdate();

            try (ResultSet r = p.getGeneratedKeys()) {
                if (r.next()) {
                    int userID = r.getInt(1);
                    user.setUserID(userID);
                    user.setVerifyCode(verifyCode);
                }
            }

        }
    }

    private String generateVerifyCode() throws SQLException {
        DecimalFormat df = new DecimalFormat("000000");
        Random ran = new Random();
        String code = df.format(ran.nextInt(1000000));  // Aleatorio de 0 a 999999
        while (checkDuplicateCode(code)) {
            code = df.format(ran.nextInt(1000000));
        }
        return code;
    }

    private boolean checkDuplicateCode(String code) throws SQLException {
        boolean duplicate = false;
        String selectQuery = "SELECT TOP 1 UserID FROM usuarios WHERE VerifyCode=?";
        try (PreparedStatement p = con.prepareStatement(selectQuery)) {
            p.setString(1, code);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) { // Usar next() en lugar de first() para revisar el conjunto de resultados
                    duplicate = true;
                }
            }
        }
        return duplicate;
    }

    public boolean checkDuplicateUser(String user) throws SQLException {
        boolean duplicate = false;
        String selectQuery = "SELECT TOP 1 UserID FROM usuarios WHERE Username=? AND [Status]='Verified'";
        try (PreparedStatement p = con.prepareStatement(selectQuery)) {
            p.setString(1, user);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) { // Usar next() en lugar de first() para revisar el conjunto de resultados
                    duplicate = true;
                }
            }
        }
        return duplicate;
    }

    public boolean checkDuplicateEmail(String user) throws SQLException {
        boolean duplicate = false;
        String selectQuery = "SELECT TOP 1 UserID FROM usuarios WHERE Email=? AND [Status]='Verified'";
        try (PreparedStatement p = con.prepareStatement(selectQuery)) {
            p.setString(1, user);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) { // Usar next() en lugar de first() para revisar el conjunto de resultados
                    duplicate = true;
                }
            }
        }
        return duplicate;
    }

    public boolean checkEmail(String email) throws SQLException {
        boolean emailInDB = false;
        String selectQuery = "SELECT TOP 1 Email FROM usuarios WHERE Email=? AND [Status]='Verified'";
        try (PreparedStatement p = con.prepareStatement(selectQuery)) {
            p.setString(1, email);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    emailInDB = true;
                }
            }

        }

        return emailInDB;
    }

    public void doneVerify(int userID) throws SQLException {
        String updateQuery = "UPDATE usuarios SET VerifyCode='', [Status]='Verified' WHERE UserID=?";
        try (PreparedStatement p = con.prepareStatement(updateQuery)) {
            p.setInt(1, userID);
            p.executeUpdate();
        }
    }

    public void doneAuth(int userID) throws SQLException {
        String updateQuery = "UPDATE usuarios SET VerifyCode='' WHERE UserID=?";
        try (PreparedStatement p = con.prepareStatement(updateQuery)) {
            p.setInt(1, userID);
            p.executeUpdate();
        }
    }

    public void doneReset(int userID, String newPassword) throws SQLException {
        String updateQuery = "UPDATE usuarios SET [Password]=? WHERE UserID=?";
        try (PreparedStatement p = con.prepareStatement(updateQuery)) {
            p.setString(1, newPassword);
            p.setInt(2, userID);
            p.executeUpdate();
        }
    }

    public boolean verifyCodeWithUser(int userID, String code) throws SQLException {
        boolean verify = false;
        String selectQuery = "SELECT TOP 1 UserID FROM usuarios WHERE UserID=? AND VerifyCode=?";
        try (PreparedStatement p = con.prepareStatement(selectQuery)) {
            p.setInt(1, userID);
            p.setString(2, code);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) { // Usar next() en lugar de first() para revisar el conjunto de resultados
                    verify = true;
                }
            }
        }
        return verify;
    }

    public String setRecoveryCode(String email) throws SQLException {
        String updateQuery = "UPDATE usuarios SET VerifyCode=? WHERE Email=? AND [Status]='Verified'";
        String verifyCode = generateVerifyCode();

        try (PreparedStatement p = con.prepareStatement(updateQuery)) {
            p.setString(1, verifyCode);
            p.setString(2, email);
            p.executeUpdate();
        }

        return verifyCode;
    }

    public boolean verifyRecoveryCode(String code) throws SQLException {
        boolean verify = false;
        String selectQuery = "SELECT TOP 1 VerifyCode FROM usuarios WHERE VerifyCode=? AND [Status]='Verified'";
        try (PreparedStatement p = con.prepareStatement(selectQuery)) {
            p.setString(1, code);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) { // Usar next() en lugar de first() para revisar el conjunto de resultados
                    verify = true;
                }
            }
        }
        return verify;
    }

    public ModelReset getUserByEmail(String email) throws SQLException {
        ModelReset data = null;
        String selectQuery = "SELECT TOP 1 UserID, Username, Email, VerifyCode FROM usuarios WHERE Email=? AND [Status]='Verified'";

        try (PreparedStatement p = con.prepareStatement(selectQuery)) {
            p.setString(1, email);

            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    int UserID = r.getInt(1);
                    String Username = r.getString(2);
                    String Email = r.getString(3);
                    String VerifyCode = r.getString(4);
                    data = new ModelReset(UserID, Username, Email, VerifyCode);
                }
            }
        }

        return data;
    }

    public boolean checkDuplicatePassword(String password) throws SQLException {
        boolean duplicate = false;
        String selectQuery = "SELECT TOP 1 [Password] FROM usuarios WHERE [Password]=? AND [Status]='Verified'";
        try (PreparedStatement p = con.prepareStatement(selectQuery)) {
            p.setString(1, password);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) { // Usar next() en lugar de first() para revisar el conjunto de resultados
                    duplicate = true;
                }
            }
        }
        return duplicate;
    }

}
