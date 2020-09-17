package com.example.demo.controller;

import com.example.demo.service.QiNiuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Crazyxu
 * @Create 2020-42-2020/9/16-19:42
 */
@RestController
public class QiNiuController {

    @Autowired
    private QiNiuService qiNiuService;

   @GetMapping("Hello")
   public String hello(){
       return "hello";
   }

    @PostMapping("/upload")
    public ResponseEntity<Object> qiniuUoload(HttpServletRequest request){

        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
        Map<String,String> response = new HashMap<>();
        response.put("123","23");
        Map fileMap = req.getFileMap();
        Set keySet = fileMap.keySet();
        String userId = request.getParameter("userId");
        for (var key:keySet) {
            System.out.println(key);
            MultipartFile file = (MultipartFile)fileMap.get(key);
            qiNiuService.upload(file, userId);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
