package singleton;

public class EagerSingleton {
    private static EagerSingleton instance = new EagerSingleton();

    //构造方法私有化
    private EagerSingleton(){}

    //静态工厂方法
    public static EagerSingleton getInstance(){
        return instance;
    }
}
