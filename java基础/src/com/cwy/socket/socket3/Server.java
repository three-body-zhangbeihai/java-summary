package com.cwy.socket.socket3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// socket发送文件的服务端
public class Server {
    public static void main(String[] args) {
        try {
            //建立连接
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("服务端已建立，等待客户端连接...");
            Socket s = serverSocket.accept();

            //输入流
            InputStream is = s.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            //读文件名
            String fileName = br.readLine();
            System.out.println(fileName);
            //修改文件名为2.png
            fileName = "2.png";
            FileOutputStream fos = new FileOutputStream(fileName);

            //读取文件
            byte[] b = new byte[1024];
            int len = -1;
            while (true){
                len = is.read(b);
                if(len == -1){
                    break;
                }
                //将读取客户端的文件读到磁盘中
                fos.write(b,0,len);
            }

            //关闭资源
            fos.close();
            is.close();
            s.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("服务端已关闭");
        }
    }
}
