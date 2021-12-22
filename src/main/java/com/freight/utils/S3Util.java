package com.freight.utils;


import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.regions.ServiceAbbreviations;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/7/5
 */
@Component
public class S3Util {
    public static AWSCredentials awsCredentials;
    @Value("${AWS.Access}")
    public  String access_key ;
    @Value("${AWS.Secret}")
    private  String secret_key;
    @Value("${AWS.regionName}")
    private  String regionName;
    private AmazonS3 conn;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式

    /**
     * 创建连接，连接S3服务器
     */
    public void creatConnect() {
        //if (awsCredentials == null)
        {
            awsCredentials = new BasicAWSCredentials(access_key, secret_key);
            ClientConfiguration clientConfig = new ClientConfiguration();
            clientConfig.setProtocol(Protocol.HTTP);
            //conn = new AmazonS3Client(awsCredentials, clientConfig);

            // 这里获取配置文件中的region name的设置
            //  如果获取不到, 强烈建议获取当前系统所在region
            conn = new AmazonS3Client(awsCredentials, clientConfig);
            Region region = Region.getRegion(Regions.fromName(regionName));
            conn.setRegion(region);
            final String serviceEndpoint = region.getServiceEndpoint(ServiceAbbreviations.S3);
            // 关键是下面这一行, 在除了中国外的其他region, 这行代码不用写
            //conn.setEndpoint(serviceEndpoint);
            //System.out.println("setting s3 region: " + region + ", : " + serviceEndpoint);
        }

    }

    public AmazonS3 getConnect() {
        return conn;
    }

    /**
     * 获取该连接下所有的容器信息
     * @return
     */
    public List<Bucket> getBuckets() {
        List<Bucket> buckets = conn.listBuckets();
        return buckets;
    }

    public Bucket getBuketsByname(String bucketName) {
        Bucket resultBucket = null;
        if (bucketName.isEmpty()) {
            return null;
        }
        List<Bucket> buckets = conn.listBuckets();
        if(buckets == null){
            return resultBucket;
        }
        for (Bucket bucket : buckets) {
            if (bucketName.equals(bucket.getName())) {
                resultBucket = bucket;
                break;
            }
        }
        return resultBucket;
    }

    /**
     * 新建容器名称
     * @param bucketName
     * @return
     */
    public Bucket creatBucket(String bucketName) {
        if (bucketName.isEmpty()) {
            return null;
        }
        Bucket bucket = conn.createBucket(bucketName);

        return bucket;
    }

    /**
     * 获取该容器下面的所有信息（文件目录集合和文件信息集合）
     * @param bucketName
     * @return
     */
    public ObjectListing getBacketObjects(String bucketName) {
        if (bucketName.isEmpty()) {
            return null;
        }
        ObjectListing objects = conn.listObjects(bucketName);
        return objects;
    }

    /**
     * 获取某个文件（前缀路径）下的所有信息
     * @param bucketName
     * @param prefix
     * @param isDelimiter
     * @return
     */
    public ObjectListing getBacketObjects(String bucketName, String prefix,Boolean isDelimiter ) {
        if ( bucketName == null || bucketName.isEmpty()) {
            return null;
        }
        ListObjectsRequest objectsRequest = new ListObjectsRequest().withBucketName(bucketName);
        if (prefix != null && !prefix.isEmpty()) {
            objectsRequest = objectsRequest.withPrefix(prefix);
        }
        if(isDelimiter){
            objectsRequest = objectsRequest.withDelimiter("/");
        }
        ObjectListing objects = conn.listObjects(objectsRequest);
        return objects;
    }

    /**
     * 获取当前容器下面的目录集合
     * @param objects
     * @return
     */
		  /*  public List<StorageObjectVo> getDirectList(ObjectListing objects) {
		        List<StorageObjectVo> diectList = new ArrayList<StorageObjectVo>();
		        String prefix = objects.getPrefix();
		        do {
		            List<String> commomprefix = objects.getCommonPrefixes();

		            for (String comp : commomprefix) {
		                StorageObjectVo dirStorageObjectVo = new StorageObjectVo();
		                String dirName = comp.substring(prefix == null?0:prefix.length(), comp.length()-1);
		                dirStorageObjectVo.setName(dirName);
		                dirStorageObjectVo.setType("文件夹");
		                diectList.add(dirStorageObjectVo);

		            }
		            objects = conn.listNextBatchOfObjects(objects);
		        } while (objects.isTruncated());
		        return diectList;
		    }  */


    /**
     * 获取当前容器下面的文件集合
     * @param objects
     * @return
     */
		  /*  public List<StorageObjectVo> getFileList(ObjectListing objects) {
		        List<StorageObjectVo> fileList = new ArrayList<StorageObjectVo>();
		        String prefix = objects.getPrefix();
		        do {
		            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
		                System.out.println(objectSummary.getKey() + "\t" + objectSummary.getSize() + "\t" + StringUtils.fromDate(objectSummary.getLastModified()));
		                if(prefix!= null  && objectSummary.getKey().equals(prefix.trim())){
		                    continue;
		                }
		                StorageObjectVo fileStorageObjectVo = new StorageObjectVo();
		                String fileName = objectSummary.getKey().substring(prefix == null?0:prefix.length(), objectSummary.getKey().length());
//		                fileStorageObjectVo.setName(objectSummary.getKey());
		                fileStorageObjectVo.setName(fileName);
		                fileStorageObjectVo.setType("文件");
		                fileStorageObjectVo.setSize(bytes2kb(objectSummary.getSize()));
		                fileStorageObjectVo.setDate(df.format(objectSummary.getLastModified()));
		                fileList.add(fileStorageObjectVo);
		            }
		            objects = conn.listNextBatchOfObjects(objects);
		        } while (objects.isTruncated());
		        return fileList;
		    }

		    public List<StorageObjectVo> getObjectList(String bucketName, String prefix) {
		        if ( bucketName == null && bucketName.isEmpty()) {
		            return null;
		        }
		        ListObjectsRequest objectsRequest = new ListObjectsRequest().withBucketName(bucketName);
		        if (prefix!=null &&  !prefix.isEmpty()) {
		            objectsRequest = objectsRequest.withPrefix(prefix);
		        }
		        objectsRequest = objectsRequest.withDelimiter("/");
		        ObjectListing objects = conn.listObjects(objectsRequest);
		        List<StorageObjectVo> resultList = new ArrayList<StorageObjectVo>();
		        List<StorageObjectVo> dirList = getDirectList(objects);
		        if (dirList != null && dirList.size() > 0) {
		            resultList.addAll(dirList);
		        }
		        List<StorageObjectVo> fileList = getFileList(objects);
		        if (fileList != null && fileList.size() > 0) {
		            resultList.addAll(fileList);
		        }

		        return resultList;
		    }  */
    //创建文件目录
    public  Boolean creatpath(String bucketName,String StorageObjectVoPath,  String folderName){
        if(bucketName == null || folderName == null){
            return  false;
        }
        if(StorageObjectVoPath == null || StorageObjectVoPath.isEmpty()|| "null".equals(StorageObjectVoPath)){
            StorageObjectVoPath ="";
        }

        String key = StorageObjectVoPath + folderName+"/";
        ByteArrayInputStream local = new ByteArrayInputStream("".getBytes());
        PutObjectResult result =   conn.putObject(bucketName, key, local, new ObjectMetadata());
        return  true;

    }

    public Boolean deleteBucket(String bucketName) {
        if (bucketName.isEmpty()) {
            return false;
        }
        Bucket bucket = conn.createBucket(bucketName);
        conn.deleteBucket(bucket.getName());
        return true;
    }

    /**
     *
     * 上传 文件对象到容器
     * @param bucketName
     * @param StorageObjectVoPath
     * @param fileName
     * @param uploadFile
     * @return
     */
    public PutObjectResult creatObject(String bucketName,String StorageObjectVoPath, String fileName, File uploadFile) {
        try {
            if (StorageObjectVoPath == null || StorageObjectVoPath.isEmpty() || "null".equals(StorageObjectVoPath)) {
                StorageObjectVoPath = "";
            }
            if (uploadFile == null) {
                return null;
            }
            String fileAllPath = StorageObjectVoPath + fileName;
//        FileInputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(uploadFile);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        PutObjectResult result = conn.putObject(bucketName, fileAllPath, inputStream, new ObjectMetadata());
            PutObjectResult result = conn.putObject(new PutObjectRequest(bucketName, fileAllPath,
                    uploadFile)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return result;
        }
        catch (Exception e)
        {
            return null;
        }
    }


    public void changeOBjectACL(String bucketName, String ObjectName, CannedAccessControlList aceess) {

        conn.setObjectAcl(bucketName, ObjectName, aceess);

    }


    public ObjectMetadata download(String bucketName, String objectName, File destinationFile) {
        if (bucketName.isEmpty() || objectName.isEmpty()) {
            return null;
        }
        ObjectMetadata result = conn.getObject(new GetObjectRequest(bucketName, objectName), destinationFile);
        return result;
    }
    public S3Object download(String bucketName, String objectName) {
        if (bucketName.isEmpty() || objectName.isEmpty()) {
            return null;
        }
        S3Object  object = conn.getObject(bucketName, objectName);
        return object;
    }

    public Boolean deleteObject(String bucketName, String objectName) {
        if (bucketName.isEmpty() || objectName.isEmpty()) {
            return false;
        }
        conn.deleteObject(bucketName, objectName);
        return true;
    }

    /**生成文件url
     *
     * @param bucketName
     * @param objectName
     * @return
     */
    public String getDownloadUrl(String bucketName, String objectName) {
        if (bucketName.isEmpty() || objectName.isEmpty()) {
            return null;
        }
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName);
        System.out.println(conn.generatePresignedUrl(request));
        return conn.generatePresignedUrl(request).toString();
    }

    /**
     * 移动对象信息到目标容器
     * @param OrgbucketName
     * @param orgKey
     * @param destinationName
     * @param destinationKey
     * @return
     */
    public Boolean moveObject(String OrgbucketName,String orgKey,String destinationName,String destinationKey){
        CopyObjectResult result=conn.copyObject(OrgbucketName, orgKey, destinationName, destinationKey);
        Boolean isDelete=deleteObject(OrgbucketName,orgKey);
        if(result!=null){
            return isDelete;
        }
        return false;
    }

    /**
     * 移动目标文件夹信息到目标容器
     * @param objects
     * @param destinationBucket
     * @return
     */
    public Boolean moveForder(ObjectListing objects,String destinationBucket){
        String bucketName = objects.getBucketName();
        do {

            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                System.out.println(objectSummary.getKey() + "\t" + objectSummary.getSize() + "\t" + StringUtils.fromDate(objectSummary.getLastModified()));
                CopyObjectResult result=conn.copyObject(bucketName, objectSummary.getKey(), destinationBucket,  objectSummary.getKey());
                Boolean isDelete=deleteObject(bucketName, objectSummary.getKey());
            }
            objects = conn.listNextBatchOfObjects(objects);
        } while (objects.isTruncated());
        return  true;
    }

    /**
     * 删除文件夹内容（必须先遍历删除文件夹内的内容）
     * @param objects
     * @return
     */
    public Boolean deleteForder(ObjectListing objects){
        String bucketName = objects.getBucketName();
        do {

            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                System.out.println(objectSummary.getKey() + "\t" + objectSummary.getSize() + "\t" + StringUtils.fromDate(objectSummary.getLastModified()));
                Boolean isDelete=deleteObject(bucketName, objectSummary.getKey());
            }
            objects = conn.listNextBatchOfObjects(objects);
        } while (objects.isTruncated());
        return  true;
    }

    /**
     * 将文件大小格式转为MB格式
     * @param bytes
     * @return
     */
    public static String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        if (returnValue > 1)
            return (returnValue + "MB");
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        return (returnValue + "KB");

    }
}
