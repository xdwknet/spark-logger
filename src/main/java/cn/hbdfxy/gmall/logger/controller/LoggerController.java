package cn.hbdfxy.gmall.logger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoggerController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @RequestMapping("/applog")
   // public String appLog(@RequestBody JSONObject jsonObject) {
    public String appLog(@RequestBody String logString) {
        //String logJson = jsonObject.toJSONString();
        JSONObject logJson = JSON.parseObject(logString);
       log.info(logString);
        if (logJson.getString("start") != null && logJson.getString("start").length()>0){
            kafkaTemplate.send("gmall_start", logString);
        }else{
            kafkaTemplate.send("gmall_event", logString);
        }
        return "success";
    }
}
