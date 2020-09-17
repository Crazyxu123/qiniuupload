package com.example.demo.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Crazyxu
 * @Create 2020-20-2020/9/16-20:20
 */
@Component
@Data
public class QiNiuContent {

    private String userId;

    private String key;

    private String hashKey;
}
