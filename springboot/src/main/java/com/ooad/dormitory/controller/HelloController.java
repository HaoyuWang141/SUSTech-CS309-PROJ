package com.ooad.dormitory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ooad.dormitory.entity.Hello;
import com.ooad.dormitory.processor.RequestSender;
import com.ooad.dormitory.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/hello")
@CrossOrigin
public class HelloController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RequestSender requestSender;
    @Autowired
    private HelloService helloService;

    @GetMapping("")
    public Map<String, String> hello() {
        Map<String, String> res = Map.of("response", "hello");
        return res;
    }

    @GetMapping("/noRedis")
    public Map<String, String> helloNoRedis() {
        Map<String, String> res = Map.of("response", "hello");
        return res;
    }

    @GetMapping("/redis")
    public Map<String, String> helloRedis() {
        Map<String, String> res = Map.of("response", "helloRedis");
        return res;
    }

    @GetMapping("/insert")
    public String insert() {
        long start = System.currentTimeMillis();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Hello hello = new Hello(null, String.valueOf(random.nextInt(10000)));
            System.out.println(hello.getId() + " " + hello.getVal());
            helloService.save(hello);
        }
        long time = System.currentTimeMillis() - start;
        return "inserted: in " + time + " ms";
    }

    @GetMapping("/getWithRedis")
    public ResponseEntity<?> getWithRedis() {
        long start = System.currentTimeMillis();
        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            int id = random.nextInt(100);
            Hello hello = new Hello(id, (String) redisTemplate.opsForValue().get("id:" + id));
            if (hello.getVal() == null) {
                hello = helloService.getById(id);
                if (hello != null) {
                    redisTemplate.opsForValue().set("id:" + id, hello.getVal());
                }
            }
            if (hello != null) {
                System.out.println("iter: " + i + ", id: " + id + ", val: " + hello.getVal());
            }
            else {
                System.out.println("iter: " + i + ", id: " + id + ", val: null");
            }
        }
        long time = System.currentTimeMillis() - start;
        return ResponseEntity.ok("getWithRedis: in " + time + " ms with 500 iterations");
    }

    @GetMapping("/getWithoutRedis")
    public ResponseEntity<?> getWithoutRedis() {
        long start = System.currentTimeMillis();
        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            int id = random.nextInt(100);
            Hello hello = helloService.getById(id);
            if (hello != null) {
                System.out.println("iter: " + i + ", id: " + id + ", val: " + hello.getVal());
            }
            else {
                System.out.println("iter: " + i + ", id: " + id + ", val: null");
            }
        }
        long time = System.currentTimeMillis() - start;
        return ResponseEntity.ok("getWithoutRedis: in " + time + " ms with 500 iterations");
    }

    @GetMapping("/getWithQueue")
    public ResponseEntity<?> getWithQueue() {
        long start = System.currentTimeMillis();
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int id = random.nextInt(100);
            // 获取请求数据
            Hello hello = helloService.getById(id);

            // 将请求发送到队列
            requestSender.sendRequest("iter: " + i + ", id: " + id + ", val: " + (hello != null ? hello.getVal() : "null"));
        }
        long time = System.currentTimeMillis() - start;
        return ResponseEntity.ok("getWithQueue: in " + time + " ms with 500 iterations");
    }

}
