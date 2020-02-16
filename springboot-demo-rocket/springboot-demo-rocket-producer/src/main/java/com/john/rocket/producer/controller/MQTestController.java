package com.john.rocket.producer.controller;

import com.john.rocket.producer.service.MQSenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@Api(tags = "test")
public class MQTestController {

    @Autowired
    private MQSenderService mqSenderService;

    @PostMapping("/mq")
    @ApiOperation("test")
    public String test() {
        mqSenderService.sendMessage();
        return "success";
    }

}
