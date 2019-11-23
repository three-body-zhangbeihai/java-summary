package bio;

import java.io.*;
import java.net.Socket;

/**
 * @program: network_programming
 * @description:
 * @author: wenyan
 * @create: 2019-11-24 00:57
 **/


public class ServerHandler implements Runnable{

    private Socket socket;
    //从服务端传入socket
    public ServerHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(),true);
            String body = null;
            while (true){
                //接收客户端发送的请求
                body = in.readLine();
                if(body == null){
                    break;
                }
                System.out.println("Client:" + body);
                //响应数据
                out.println("你要的资源A给你了");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭资源
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
                try{
                    socket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            socket = null;
        }
    }
}
