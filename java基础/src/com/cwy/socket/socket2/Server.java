package com.cwy.socket.socket2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//字符流版本的server
public class Server {
    public static void main(String[] args) {
        try{
            //建立通信
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("服务器已经建立连接");
            Socket s = serverSocket.accept();

            InputStream is = s.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            OutputStream os = s.getOutputStream();
            PrintWriter pw = new PrintWriter(os);

            //获取服务端请求
            String str = br.readLine();
            System.out.println("客户端：" + str);

            //回写数据
            pw.print("你好，我是服务器，给你A资源");
            pw.flush();

            //关闭资源
            br.close();
            isr.close();
            is.close();
            pw.close();
            os.close();
            s.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
