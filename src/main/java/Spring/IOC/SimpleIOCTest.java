package Spring.IOC;

import org.junit.Test;

public class SimpleIOCTest {
    @Test
    public void getBean() throws Exception {
//        String location = SimpleIOC.class.getClassLoader().getResource("ioc.xml").getFile();
        SimpleIOC bf = new SimpleIOC("/Users/adrian/Documents/IdeaProjects/InterviewPreparation/src/main/java/Spring/IOC/ioc.xml");
        Wheel wheel = (Wheel) bf.getBean("wheel");
        System.out.println(wheel);
        Car car = (Car) bf.getBean("car");
        System.out.println(car);
    }
}
