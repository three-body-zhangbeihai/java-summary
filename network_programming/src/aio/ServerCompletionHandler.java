package aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {

    // 业务处理逻辑，当请求成功后，监听成功，应该做什么的方法
    @Override
    public void completed(AsynchronousSocketChannel asc, Server attachment) {
        //当有下一个客户端接入的时候，直接调用Server的accept方法，这样反复执行下去，保证多个客户端都可以阻塞。
        //类似递归
        attachment.assc.accept(attachment,this);
        //读取客户端的请求
        read(asc);
    }

    //异常处理逻辑
    @Override
    public void failed(Throwable exc, Server attachment) {
        exc.printStackTrace();
    }

    /**
     * 业务处理逻辑，当请求到来后，监听成功，应该做什么
     * 一定要实现的逻辑：为下一次客户端请求开启监听。accept方法调用
     * 无论BIO、NIO、AIO中，一旦连接建立，两端是平等的。
     * @param asc
     */
    private void read(final AsynchronousSocketChannel asc) {
        //读取数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        /**
         * 异步读操作，read(Buffer destination, A attachment, CompletionHandler<Integer, ? super A> handler)
         * destination：目的地，是处理客户端传递数据的中转缓存。
         * attachment：处理客户端传递数据的对象
         * handler：处理逻辑
         */
        asc.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            /**
             * 业务逻辑，读取客户端传输数据。
             * @param resultSize
             * @param attachment
             */
            @Override
            public void completed(Integer resultSize, ByteBuffer attachment) {
                //进行读取之后，重置标识位
                attachment.flip();
                //获取读的字节数
                System.out.println("接受到客户端的数据长度为:" + resultSize);

                //获取读取的数据
                String resultData = new String(attachment.array()).trim();
                System.out.println("收到客户端的数据信息为: " + resultData);
                String response = "服务器响应：收到了你的信息了..";
                write(asc, response);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });
    }

    private void write(AsynchronousSocketChannel asc, String response) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(response.getBytes());
            buffer.flip();
            asc.write(buffer).get();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
    }


}
