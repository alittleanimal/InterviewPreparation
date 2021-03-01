package Singleton;

public class SingletonSecond {
    private SingletonSecond() {}
    private volatile static SingletonSecond instance = null;

    private static SingletonSecond getInstanceSecond() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new SingletonSecond();
                }
            }
        }
        return instance;
    }
}
