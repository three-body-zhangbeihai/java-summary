package singleton;

public class LazySingleton {
//    private LazySingleton(){
//    }
//    private static volatile LazySingleton instance = null;
//
//    //静态工厂方法
//    public static LazySingleton getInstance(){
//        if(instance == null){
//            //同步块，线程安全的创建实例
//            synchronized (LazySingleton.class){
//                //再次检查实例是否存在，如果不存在才真正的创建实例
//                if(instance == null){
//                    instance = new LazySingleton();
//                }
//            }
//            instance = new LazySingleton();
//        }
//        return instance;
//    }

    private LazySingleton(){}
    /*
        类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
        没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
     */
    private static class SingletonHolder{
        //静态初始化器，由JVM来保证线程安全
        private static LazySingleton instance = new LazySingleton();
    }
    public static LazySingleton getInstance(){
        return SingletonHolder.instance;
    }


}
