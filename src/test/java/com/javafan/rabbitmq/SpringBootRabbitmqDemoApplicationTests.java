package com.javafan.rabbitmq;

import com.javafan.rabbitmq.bean.Book;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SpringBootRabbitmqDemoApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;


    /**
     * 单播
     */
    @Test
    void contextLoads() {
//        message需要自己构造一个
//        rabbitTemplate.send(exchange,routKey,message);
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "这是第一个消息");
        map.put("data", Arrays.asList("helloworld", 123, true));
        //没有配置序列化 默认是Java序列化，可以改为json
        rabbitTemplate.convertAndSend("exchange.direct", "atguigu", map);
        rabbitTemplate.convertAndSend("exchange.direct", "atguigu", new Book("三国", "javafan"));


    }

    /**
     * 广播
     */
    @Test
    public void exchangeFanout() {
        rabbitTemplate.convertAndSend("exchange.fanout", "atguigu", new Book("水浒", "施耐庵"));
    }

    /**
     * 主题模式
     */
    @Test
    public void exchangeTopic() {
        rabbitTemplate.convertAndSend("exchange.topic", "javafan.news", new Book("topic", "topic"));
    }

    /**
     * 接受数据，序列化转为json messageConverter
     */
    @Test
    public void recive() {
        while (1 == 1) {
            Object atguigu = rabbitTemplate.receiveAndConvert("atguigu");
        }
    }

    /**
     * 创建队列
     */
    @Test
    public void amqAdmin() {
        amqpAdmin.declareExchange(new Exchange() {
            @Override
            public String getName() {
                return "amqAdmin";
            }

            @Override
            public String getType() {
                return "fanout";
            }

            @Override
            public boolean isDurable() {
                return true;
            }

            @Override
            public boolean isAutoDelete() {
                return false;
            }

            @Override
            public Map<String, Object> getArguments() {
                return null;
            }

            @Override
            public boolean isDelayed() {
                return false;
            }

            @Override
            public boolean isInternal() {
                return false;
            }

            @Override
            public boolean shouldDeclare() {
                return false;
            }

            @Override
            public Collection<?> getDeclaringAdmins() {
                return null;
            }

            @Override
            public boolean isIgnoreDeclarationExceptions() {
                return false;
            }
        });
    }

    /**
     * 创建队列
     */
    @Test
    public void amqAdminCreateQueues() {
        amqpAdmin.declareQueue(new Queue("javafan", true));
    }

    /**
     * @param destination 目的的 就是对应的队列
     *                    新建绑定
     */
    @Test
    public void amqAdminCreateBindings() {
        Binding binding = new Binding("javafan", Binding.DestinationType.QUEUE, "amqAdmin", "javafan", new HashMap<>());
        amqpAdmin.declareBinding(binding);
    }


}
