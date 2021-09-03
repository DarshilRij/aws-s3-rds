import com.amazonaws.services.s3.AmazonS3;
import connection.DBConnection;
import connection.IDBConnection;
import partb.S3Storage;
import partc.RDSService;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws IOException, SQLException {

        IDBConnection conObj = new DBConnection();
        Connection connection = null;

//        String responseMessage = "";

        S3Storage s3Storage = new S3Storage();
        AmazonS3 s3Client = s3Storage.getS3CLient();
//        String responseMessage = s3Storage.createBucket(s3Client);
//        System.out.println(responseMessage);

//        responseMessage = s3Storage.fileTransfer(s3Client);
//        System.out.println(responseMessage);

//        responseMessage = s3Storage.changeAccessPermission(s3Client);
//        System.out.println(responseMessage);

//        responseMessage = s3Storage.moveFileToOtherBucket(s3Client);
//        System.out.println(responseMessage);

        RDSService rdsService = new RDSService(conObj);
        rdsService.readLookUpfile(s3Client);
//        rdsService.addUser(2,"xyz");
        rdsService.getUserByID(2);




    }
}
