package com.example.demo.service;

import com.example.demo.domain.QiNiuContent;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author Crazyxu
 * @Create 2020-18-2020/9/16-20:18
 */
public interface QiNiuService {
    public QiNiuContent upload(MultipartFile file, String userId);

    public File download(String key);

    public File download(QiNiuContent content);

    public void delete(String key);

    public void delete(QiNiuContent content);
}
