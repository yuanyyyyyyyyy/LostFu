package com.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;


//@SpringBootApplication
@MapperScan("com.demo.Mapper")
@SpringBootApplication(scanBasePackages={"com.demo.Controller","com.demo.Service","com.demo.Config","com.demo.websocket"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
//        ConfigurableApplicationContext application = SpringApplication.run(DemoApplication.class, args);
//        Environment env = application.getEnvironment();
//        String host = InetAddress.getLocalHost().getHostAddress();
//        String port = env.getProperty("server.port");
//        System.out.println("[----------------------------------------------------------]");
//        System.out.println("聊天室启动成功！点击进入:\t http://" + host + ":" + port);
//        System.out.println("[----------------------------------------------------------");
//        WebSocketServer.inst().run(53134);
    }

}
