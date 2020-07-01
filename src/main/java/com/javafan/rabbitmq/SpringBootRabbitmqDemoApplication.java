package com.javafan.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fanzhiqiang
 * @date 2020/06/30
 * RabbitAutoConfiguration 自动配置
 * - RabbitConnectionFactoryCreator 连接工厂
 * - RabbitTemplateConfiguration 模版
 * - AmqpAdmin 配置生成队列 交换器等等
 */
@SpringBootApplication
@EnableRabbit
public class SpringBootRabbitmqDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRabbitmqDemoApplication.class, args);
    }

}
