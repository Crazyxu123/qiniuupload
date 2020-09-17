package com.example.demo;

import com.example.demo.config.QiNiuConfig;
import com.example.demo.service.QiNiuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private QiNiuConfig config;

    @Autowired
    private QiNiuService service;

    @Test
    void contextLoads() {
        System.out.println(config.getZone());
        service.download("wx15bcb6f7b8910aa9.o6zAJszto_8uXfKOfbiXMsudEXuo.EkZrRNcHagMT88ab6b539854e648907bcc158e5bb44b.jpg");
    }

}
