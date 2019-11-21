package simplefactory;

//具体产品类
public class bmwCar implements Car {
    @Override
    public void drive() {
        System.out.println("驾驶宝马车......");
    }
}