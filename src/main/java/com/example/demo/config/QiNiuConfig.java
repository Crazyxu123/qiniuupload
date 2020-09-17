package com.example.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;

/**
 * @author Crazyxu
 * @Create 2020-46-2020/9/16-19:46
 */

@Data
@Component
public class QiNiuConfig {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    //七牛存储空间名称
    @Value("${qiniu.bucket}")
    private String bucket;

    //外域名
    @Value("${qiniu.host}")
    private String host;

    //存储所属区域 华南:huanan , 华北:huabei, 华东:huadong,北美:beimei,东南亚:dongnanya
    @Value("${qiniu.zone}")
    private String zone;
}
