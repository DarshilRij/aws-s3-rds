package partc;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import connection.IDBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RDSService {

    IDBConnection conObj;
    Connection connection = null;
    ResultSet resultSet = null;
    private PreparedStatement statement = null;

    public RDSService(IDBConnection conObj) {
        this.conObj = conObj;
    }

    String bucket1 = "second-bucket-darshil";
    String key = "Lookup5410.txt";
    String[] replaceArray = new String[30];
    String[] alphaArray = new String[30];

    public void readLookUpfile(AmazonS3 s3Client) throws IOException {
        S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucket1, key));
        InputStream objectData = s3Object.getObjectContent();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(objectData));
        String line = null;

        int i = 0;
        while ((line = bufferedReader.readLine()) != null) {
            if (!line.contains("Alphabet")) {
                String[] arrOfStr = line.split("\t");
                alphaArray[i] = arrOfStr[0];
                replaceArray[i] = arrOfStr[1];
                i++;
            }
        }


        s3Object.close();
    }

    public void addUser(int user, String password) throws SQLException {
        String encryptedPassword = "";

        encryptedPassword = getEncryptedPassword(password, encryptedPassword);

        connection = conObj.establishDBConnection();
        String insertUserQuery = "INSERT INTO user(userID,password) VALUES (?,?)";
        statement = connection.prepareStatement(insertUserQuery);
        statement.setInt(1, user);
        statement.setString(2, encryptedPassword);
        statement.executeUpdate();
        connection.close();
    }

    private String getEncryptedPassword(String password, String encryptedPassword) {
        for (int i = 0; i < password.length(); i++) {
            for (int j = 0; j < alphaArray.length; j++) {
                if (alphaArray[j].equals(String.valueOf(password.charAt(i)))) {
                    encryptedPassword = encryptedPassword.concat(replaceArray[j]);
                    break;
                }
            }
        }
        return encryptedPassword;
    }

    public void getUserByID(int id) throws SQLException {
        System.out.println("Getting User...");
        connection = conObj.establishDBConnection();
        String getAllUsersQuery = "SELECT * from user where userID=" + id;
        statement = connection.prepareStatement(getAllUsersQuery);
        try (ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getInt("userID") + "              " + getDecryptPassword(rs.getString("password")));
            }
        }
        connection.close();
    }

    public String getDecryptPassword(String password) {
        String decryptedPassword = "";
        for (int i = 0; i < password.length(); i = i + 2) {
            for (int j = 0; j < alphaArray.length; j++) {
                if (replaceArray[j].equals(String.valueOf(password.substring(i, i + 2)))) {
                    decryptedPassword = decryptedPassword.concat(alphaArray[j]);
                    break;
                }
            }
        }
        return decryptedPassword;
    }


}
