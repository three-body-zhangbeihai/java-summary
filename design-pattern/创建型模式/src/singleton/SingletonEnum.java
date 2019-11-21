package singleton;
public enum SingletonEnum {
    INSTANCE01, INSTANCE02;// 定义枚举的两个类型
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}


 class TestEnum {

    public static void main(String[] args) {
        SingletonEnum instance01=SingletonEnum.INSTANCE01;
        instance01.setName("aaa");
        System.out.println(instance01.getName());

        SingletonEnum instance02=SingletonEnum.INSTANCE01;
        System.out.println(instance02.getName());

        SingletonEnum instance03=SingletonEnum.INSTANCE02;
        instance03.setName("ccc");
        System.out.println(instance03.getName());

        SingletonEnum instance04=SingletonEnum.INSTANCE02;
        instance04.setName("ddd");
        System.out.println(instance04.getName());
        System.out.println(instance03.hashCode()+"\t"+instance04.hashCode());
        System.out.println(instance03==instance04);

    }
}