package factorymethod;

public class Client {
	public static void main(String args[]) {
		try {
			TV tv;
			TVFactory factory;
			factory = new HaierTVFactory();
			tv = factory.produceTV();
			tv.play();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
