package Spring.AOP;

import org.junit.Test;

public class SimpleAOPTest {
    @Test
    public void getProxy() {
        MethodInvocation logTask = () -> System.out.println("Log task start.");
        HelloService helloService = new HelloServiceImpl();

        Advice beforeAdvice = new BeforeAdvice(helloService, logTask);

        HelloService helloServiceImplProxy = (HelloService) SimpleAOP.getProxy(helloService, beforeAdvice);
        helloServiceImplProxy.sayHelloWorld();
    }
}
