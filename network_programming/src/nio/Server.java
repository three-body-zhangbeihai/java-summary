package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server implements Runnable{
    //1. 多路复用器Selector（管理所有的Channel）
    private Selector selector;
    //2. 建立读取缓冲区
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    //3. 建立写缓冲区
    //private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
    public Server(int port){
        try {
            //1. 打开多路复用器(调用自身静态方法)
            this.selector = Selector.open();
            //2. 打开服务器通道
            ServerSocketChannel ssc = ServerSocketChannel.open();
            //3. 设置服务器通道为非阻塞模式
            ssc.configureBlocking(false);
            //4. 绑定地址
            ssc.bind(new InetSocketAddress(port));
            //5. 把服务器通道注册到多路复用器上，并且监听阻塞事件
            ssc.register(this.selector,SelectionKey.OP_ACCEPT);

            System.out.println("服务器已启动，端口是:" + port + ",等待客户端连接...");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //将服务端作为一个线程启动。会执行run方法。
        new Thread(new Server(8888)).start();
    }

    @Override
    public void run() {
        while (true){
            try {
                //1. 必须要让多路复用器开始监听
                this.selector.select();
                //2. 返回多路复用器已经选择的结果集
                //当客户端的Channel注册到多路复用器上时，会有key注册在上面。
                Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
                //3. 进行遍历（遍历Channel的所有key）
                while (keys.hasNext()){
                    //4. 获取一个选择的元素
                    SelectionKey key = keys.next();
                    //5. 直接从容器中移除就可以了
                    keys.remove();

                    //6. 如果是有效的key
                    if(key.isValid()){
                        //7. 如果是阻塞状态
                        //一开始只有服务端的ServerSocketChannel是阻塞的，那么会执行accept方法
                        if(key.isAcceptable()){
                            this.accept(key);
                        }
                        //8. 如果为可读状态
                        if(key.isReadable()){
                            this.read(key);
                        }
                        //9. 写数据
                        /*if(key.isWritable()){
                            this.write(key);
                        }*/
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //阻塞状态
    private void accept(SelectionKey key) {
        try {
            //1. 获取服务通道
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            //2. 执行阻塞方法
            //阻塞着，如果有客户端Channel连上，就被监听到，获得一个客户端的SocketChannel了。
            SocketChannel sc = ssc.accept();
            //3. 设置阻塞模式
            sc.configureBlocking(false);
            //4. 注册到多路复用器上，并设置读取标识。下一次轮询时就是可读状态了。
            sc.register(this.selector, SelectionKey.OP_READ);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    private void read(SelectionKey key) {
        try {
            //1. 清空缓冲区旧的数据
            this.readBuffer.clear();
            //2. 获取之前注册的SocketChannel对象(通过多路复用器上的key来获取）
            SocketChannel sc = (SocketChannel)key.channel();
            //3. 读取数据
            int count = sc.read(this.readBuffer);
            //4. 如果没有数据
            if(count == -1){
                key.channel().close();
                key.cancel();
                return;
            }

            //5. 有数据，则进行读取，读取之前进行复位方法（把position和limit进行复位）
            this.readBuffer.flip();
            //6. 根据缓冲区的数据长度创建相应大小的byte数组，接收缓冲区的数据
            byte[] bytes = new byte[this.readBuffer.remaining()];
            //7. 接收缓冲区的数据
            this.readBuffer.get(bytes);
            //8. 打印结果
            String body = new String(bytes).trim();
            System.out.println("客户端：" + body);

            //9. 回写给客户端数据，就是write
            //这是双向通信。
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
