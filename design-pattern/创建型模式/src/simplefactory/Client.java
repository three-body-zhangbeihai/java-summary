package simplefactory;

// 客户
public class Client {
    public static void main(String[] args) throws Exception {
        //告诉司机(工厂) 开奔驰车
        Car car=driverFactory.dirveCar("benzCar");
        //下命令开车
        car.drive();
    }
}