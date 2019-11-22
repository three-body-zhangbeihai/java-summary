package com.cwy.reflect;

public class Car {
    private int price;
    private String name;
    public Car(){};
    public Car(int price, String name){
        this.price = price;
        this.name = name;
    }

    public int getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String run(String name){
        System.out.println(name);
        return "run";
    }
    private String stop(String name){
        System.out.println(name);
        return "stop";
    }

    @Override
    public String toString() {
        return "Car{" +
                "price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
