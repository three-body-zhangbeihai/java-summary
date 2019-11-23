package com.cwy.socket.socket1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description: 字节流版本的服务端
 */
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);//指定端口
            System.out.println("服务端已建立连接，等待客户端连接");
            //获取建立连接类型的socket
            //accept()阻塞着等待连接
            Socket s = serverSocket.accept();
            InputStream is = s.getInputStream();//获取字节流
            OutputStream os = s.getOutputStream();
            byte[] b = new byte[1024];

            //接收客户端的请求
            int len = is.read(b);
            String str = new String(b, 0, len);
            System.out.println("客户端：" + str);

            //回写客户端
            os.write("这是A资源，给你了".getBytes());

            //关闭资源
            is.close();
            os.close();
            s.close();
            serverSocket.close();
            System.out.println("服务器端已关闭");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
