package bio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    final static int PORT = 8888;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("服务端启动...");
            Socket socket = null;
            //启动一个线程池，池的最大容量为50，阻塞队列的最大值为1000
            HandlerExecutorPool executorPool = new HandlerExecutorPool(50, 1000);
            while (true){
                socket = serverSocket.accept();
                //每有一个socket连接，就让线程池去执行
                executorPool.execute(new ServerHandler(socket));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(in != null){
                try {
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(out != null){
                try {
                    out.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(serverSocket != null){
                try {
                    serverSocket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            serverSocket = null;
        }
    }
}
