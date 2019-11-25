package aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    //线程池,提高服务端效率
    private ExecutorService executorService;
    //线程组
    private AsynchronousChannelGroup threadGroup;
    //服务端通道
    public AsynchronousServerSocketChannel assc;

    public Server(int port){
        try {
            //创建一个缓存池
            executorService = Executors.newCachedThreadPool();
            //创建线程组
            threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService,1);
            //创建服务器通道
            assc = AsynchronousServerSocketChannel.open(threadGroup);
            //进行绑定监听端口，但是未监听请求
            assc.bind(new InetSocketAddress(port));

            System.out.println("服务器端已启动...端口为:" + port);
            //进行阻塞
            /**
             * accept(T attachment, CompletionHandler<AsynchronousSocketChannel, ? super T>)
             * AIO开发中，监听是一个类似递归的监听操作。每次监听到客户端请求后，都需要处理逻辑开启下一次监听。
             * 下一次监听，需要服务器资源继续支持
             */
            assc.accept(this,new ServerCompletionHandler());

            //一直阻塞，不然服务器停止
            Thread.sleep(Integer.MAX_VALUE);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8888);
    }
}
