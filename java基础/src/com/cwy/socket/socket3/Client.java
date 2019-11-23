package com.cwy.socket.socket3;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//socket发送文件(带文件名)的客户端
public class Client {
    public static void main(String[] args) {
        try{
            //建立连接
            Socket s = new Socket(InetAddress.getLocalHost(), 8888);

            //输出流
            OutputStream os = s.getOutputStream();
            PrintWriter pw = new PrintWriter(os,true);

            //文件读入
            String fileName = "src/com/cwy/socket/socket3/1.png";
            File f = new File(fileName);
            System.out.println("发送文件，名为:" + fileName);
            FileInputStream fis = new FileInputStream(f);

            //写文件名
            pw.println(f.getName());

            //文件内容发送
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = fis.read(b)) > 0){
                os.write(b,0,len);
            }

            //关闭资源
            pw.close();
            fis.close();
            os.close();
            s.close();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            System.out.println("客户端已关闭");
        }
    }
}
