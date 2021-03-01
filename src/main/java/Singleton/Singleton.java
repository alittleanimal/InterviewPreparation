package Singleton;

public class Singleton {
    private Singleton() {}
    private static Singleton instance = null;

    private static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

}
