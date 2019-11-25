package aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

public class Client implements Runnable{
    final static String ADDRESS = "127.0.0.1";
    final static int PORT = 8888;

    private AsynchronousSocketChannel asc;

    public Client() throws IOException {
        asc = AsynchronousSocketChannel.open();
    }

    public void connect(){
        asc.connect(new InetSocketAddress(ADDRESS,PORT));
    }

    public void read(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            //读取
            asc.read(buffer).get();
            buffer.flip();
            byte[] respByte = new byte[buffer.remaining()];
            buffer.get(respByte);
            System.out.println(new String(respByte,"UTF-8"));

        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

    }

    public void write(String request){
        try {
            //写
            asc.write(ByteBuffer.wrap(request.getBytes())).get();
            read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){

        }
    }

    public static void main(String[] args) throws Exception {
        Client c1 = new Client();
        c1.connect();

        Client c2 = new Client();
        c2.connect();

        Client c3 = new Client();
        c3.connect();

        new Thread(c1, "c1").start();
        new Thread(c2, "c2").start();
        new Thread(c3, "c3").start();

        Thread.sleep(1000);

        c1.write("c1 aaa");
        c2.write("c2 bbbb");
        c3.write("c3 ccccc");
    }
}
