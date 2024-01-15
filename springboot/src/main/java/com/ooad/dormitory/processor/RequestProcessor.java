package com.ooad.dormitory.processor;

import com.ooad.dormitory.entity.Dormitory;
import com.ooad.dormitory.entity.Team;
import com.ooad.dormitory.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RequestProcessor {

    private static double dropRate = 0;
    private static double requestRate = 0;

    @Autowired
    private TeamService teamService;

//    @JmsListener(destination = "requestQueue")
//    public void processRequest(String request) {
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 100000000; i++) {
//            int j = i;
//        }
//        long time = System.currentTimeMillis() - start;
//        System.out.println("Processed: " + request + " in " + time + " ms");
//    }

    // 处理选宿舍请求
//    @JmsListener(destination = "requestQueue")
    public void processRequest(String request) {
//        requestRate += 1;
//        Random random = new Random();
//        if (random.nextDouble(1) > dropRate) {
//            String[] requestInfo = request.split(" ");
//            String teamId = requestInfo[0];
//            String dormitoryId = requestInfo[1];
//            String ownerId = requestInfo[2];
//            long start = System.currentTimeMillis();
//            teamService.save(new Team(null, Integer.parseInt(dormitoryId), ownerId, new Dormitory()));
//            long time = System.currentTimeMillis() - start;
//            System.out.println("Processed: " + request + " in " + time + " ms");
//        }
    }

//    @Scheduled(fixedRate = 1000)  // 每秒：更新当前的丢弃率和请求率
    public void updateDropRate() {
//        System.out.println("dropRate: " + dropRate + " requestRate: " + requestRate);
//        dropRate = requestRate == 0 ? 0 : Math.max(0, - 100.0 / requestRate + 0.2);
//        requestRate = 0;
    }
}
