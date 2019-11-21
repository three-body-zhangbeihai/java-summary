package simplefactory;

//工厂方法类
public class driverFactory {
    //返回类型必须为抽象产品角色
    public static Car dirveCar(String params) throws          Exception{
        //判断逻辑，返回具体的产品角色给Client
        if(params.equals("benzCar")){
            return new benzCar();
        }else if(params.equals("bmwCar")){
            return new bmwCar();
        }else{
            throw new Exception();
        }
    }
}