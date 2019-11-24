package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    final static String ADDRESS = "127.0.0.1";
    final static int PORT = 8888;
    public static void main(String[] args) {
        //创建连接的地址
        InetSocketAddress address = new InetSocketAddress(ADDRESS,PORT);

        //声明连接通道
        SocketChannel sc = null;

        //建立缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {
            //打开通道
            sc = SocketChannel.open();
            //进行连接
            sc.connect(address);

            while (true){
                //定义一个字节数组，然后使用系统录入功能
                byte[] bytes = new byte[1024];
                //系统录入
                System.in.read(bytes);

                //把数据放到缓冲区
                buffer.put(bytes);
                //对缓冲区进行复位
                buffer.flip();
                //写出数据
                sc.write(buffer);
                //清空缓冲区
                buffer.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(sc != null){
                try {
                    sc.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
