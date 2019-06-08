package ares.remoting.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClient {

    private static final Logger logger = LoggerFactory.getLogger(MainClient.class);

    public static void main(String[] args) {

        //引入远程服务
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ares-client.xml");
        //获取远程服务
        final HelloService helloService = (HelloService) context.getBean("remoteHelloService");
        final GoodByeService goodByeService = (GoodByeService) context.getBean("remoteGoodByeService");

//        long count = 1000000000000000000L;
        long count = 5L;

        //调用服务并打印结果
        for (int i = 0; i< count;i++) {
            try {
                String result = helloService.sayHello("wuyangsheng, i=" + i);
                System.out.println(result);
            } catch (Exception e) {
                logger.warn("-------------",e);
            }
        }
        //调用服务2并打印结果
        for (int i = 0; i< count;i++) {
            try {
                String result1 = goodByeService.sayGoodBye("qinqin, i=" + i);
                System.out.println(result1);
            } catch (Exception e) {
                logger.warn("-------------",e);
            }
        }


        //关闭jvm
        System.exit(0);


    }
}
