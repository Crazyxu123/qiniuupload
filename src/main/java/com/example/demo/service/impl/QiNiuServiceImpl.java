package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.config.QiNiuConfig;
import com.example.demo.domain.QiNiuContent;
import com.example.demo.service.QiNiuService;
import com.example.demo.util.FileUtil;
import com.example.demo.util.QiNiuUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Crazyxu
 * @Create 2020-25-2020/9/16-20:25
 */
@Service
public class QiNiuServiceImpl implements QiNiuService {

    @Autowired
    private QiNiuConfig config;

    private final String downloadDirectory = "D:/temp/imges/";

    @Override
    public QiNiuContent upload(MultipartFile file, String userId) {

        QiNiuContent content = new QiNiuContent();
        //七牛配置
        Configuration configuration = new Configuration(QiNiuUtil.getRegion(config.getZone()));
        UploadManager manager =new UploadManager(configuration);
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        String upToken = auth.uploadToken(config.getBucket());
      //  System.out.println(upToken);
        String key =file.getOriginalFilename();

        try {
            Response response = manager.put(file.getBytes(), key, upToken);
            System.out.println(response);
            DefaultPutRet putRet = (DefaultPutRet)JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            content.setKey(putRet.key);
            content.setHashKey(putRet.hash);
            content.setUserId(userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public File download(String key){
        String url = config.getHost()  + "/"+ key;
        String filepath = downloadDirectory+key;

        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        String downloadUrl = auth.privateDownloadUrl(url);
        return download(downloadUrl, filepath);
    }

    private File download(String url,String filepath) {

        OkHttpClient client = new OkHttpClient();
        System.out.println(filepath);
        System.out.println(url);
        Request req = new Request.Builder().url(url).build();
        okhttp3.Response resp = null;
        File imgFile = new File(filepath);          //下载到本地的图片命名
        try {
            resp = client.newCall(req).execute();
            System.out.println(resp.isSuccessful());
            if(resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream is = body.byteStream();
                byte[] data = FileUtil.readInputStream(is);

                File parentFile = imgFile.getParentFile();
                if(!parentFile.exists()){
                    parentFile.mkdirs();
                }
                if(!imgFile.exists()){
                    imgFile.createNewFile();
                }
                FileOutputStream fops = new FileOutputStream(imgFile);
                fops.write(data);
                System.out.println("下载完成");
                fops.close();
            }
            else{
                System.out.println("下载失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unexpected code " + resp);
        }

        return imgFile;
    }

    @Override
    public File download(QiNiuContent content){
        return download(content.getKey());
    }

    @Override
    public void delete(String key) {
        Configuration configuration = new Configuration(QiNiuUtil.getRegion(config.getZone()));
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, configuration);
        try {
            Response response = bucketManager.delete(config.getBucket(), key);
            System.out.println(response.bodyString());

        } catch (QiniuException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(QiNiuContent content) {
        delete(content.getKey());
    }


}
