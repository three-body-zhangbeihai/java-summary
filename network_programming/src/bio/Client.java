package bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @program: network_programming
 * @description:
 * @author: wenyan
 * @create: 2019-11-24 01:05
 **/


public class Client {
    final static String ADDRESS = "127.0.0.1";
    final static int PORT = 8888;

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            //建立连接
            socket = new Socket(ADDRESS,PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);

            //向服务端发送数据
            out.println("你好服务端，我是客户端...");
            out.println("我请求资源A...");

            //接收数据
            String response = in.readLine();
            System.out.println("Server：" + response);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(in != null){
                try {
                    in.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(out != null){
                try{
                    out.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(socket != null){
                try {
                    socket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            socket = null;
        }
    }
}
