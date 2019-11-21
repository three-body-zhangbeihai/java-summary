package simplefactory;

//具体产品类
public class benzCar implements Car {
    @Override
    public void drive() {
        System.out.println("驾驶奔驰车......");
    }

}