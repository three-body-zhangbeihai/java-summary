package com.cwy.socket.socket1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @description: 字节流版本的客户端
 */

public class Client {
    public static void main(String[] args) {
        try {
            //指定服务器的地址和端口
            //Socket s = new Socket(InetAddress.getLocalHost(), 8888);
            Socket s = new Socket("127.0.0.1", 8888);
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();

            //发送请求数据
            os.write("你好，服务器，我要A资源".getBytes());
            byte[] b = new byte[1024];
            int len = -1;
            //接收回写数据
            while ((len = is.read(b)) > 0){
                String str = new String(b,0,len);
                System.out.println("服务端的回复:" + str);
            }
            is.close();
            os.close();

            //关闭通信
            s.close();
            System.out.println("客户端已关闭");
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
