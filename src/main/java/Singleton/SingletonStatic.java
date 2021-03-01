package Singleton;

public class SingletonStatic {
    private static class LazyHolder {
        private static final SingletonStatic INSTANCE = new SingletonStatic();
    }

    private SingletonStatic() {

    }

    public static SingletonStatic getInstance() {
        return LazyHolder.INSTANCE;
    }
}
