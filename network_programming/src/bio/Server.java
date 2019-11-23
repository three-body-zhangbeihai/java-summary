package bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: network_programming
 * @description:
 * @author: wenyan
 * @create: 2019-11-24 00:40
 **/


public class Server {

    final static int PORT = 8888;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("服务端启动...");

            //进行阻塞
            Socket socket = serverSocket.accept();

            //新建一个线程执行客户端的任务
            new Thread(new ServerHandler(socket)).start();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
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
