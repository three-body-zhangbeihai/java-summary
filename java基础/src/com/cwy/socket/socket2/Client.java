package com.cwy.socket.socket2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//字符流的客户端
public class Client {
    public static void main(String[] args) {
        Socket s;
        try {
            //建立连接
            s = new Socket(InetAddress.getLocalHost(), 9999);

            OutputStream os = s.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            InputStream is = s.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            //发送请求
            pw.println("你好，我是客户端，我要A资源");
            pw.flush();

            //接收数据
            String str = br.readLine();
            System.out.println("服务端:" + str);

            //关闭资源
            br.close();
            isr.close();
            is.close();
            pw.close();
            os.close();
            s.close();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
