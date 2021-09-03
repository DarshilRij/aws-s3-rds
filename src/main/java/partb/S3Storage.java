package partb;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;
import com.amazonaws.services.s3.model.SetPublicAccessBlockRequest;

import java.io.File;

public class S3Storage {


    String bucket1 = "first-bucket-darshil";
    String bucket2 = "second-bucket-darshil";
    String filePath = "D://Dalhousie Projects/5410 Serverless Data Processing/Assignments/Assignment 1/A1 code/src/main/java/Darshilkumar.txt";
    String key = "Darshilkumar.txt";

    public AmazonS3 getS3CLient() {
//        AmazonS3 s3client = AmazonS3ClientBuilder.standard().build();
        AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
        System.out.println("S3 Client setup done");
        return s3Client;
    }

    public String createBucket(AmazonS3 s3Client) {
        String bucketName = "second-bucket-darshil";
        s3Client.createBucket(bucketName);
        return bucketName + " Bucket Created Successfully";
    }

    public String fileTransfer(AmazonS3 s3Client) {

        s3Client.putObject(bucket1, key, new File(filePath));
        return "File uploaded to S3 successfully";
    }

    public String changeAccessPermission(AmazonS3 s3Client) {
        String bucket = "first-bucket-darshil";
        s3Client.setPublicAccessBlock(new SetPublicAccessBlockRequest()
                .withBucketName(bucket)
                .withPublicAccessBlockConfiguration(new PublicAccessBlockConfiguration()
                        .withBlockPublicAcls(true)
                        .withIgnorePublicAcls(true)
                        .withBlockPublicPolicy(true)
                        .withRestrictPublicBuckets(true)));

        return "Access Permission Set Successful";
    }

    public String moveFileToOtherBucket(AmazonS3 s3Client) {
        //Move file from one bucket to another
        s3Client.copyObject(bucket1, key, bucket2, key);

        return "File Moved to Other Bucket";
    }

}
