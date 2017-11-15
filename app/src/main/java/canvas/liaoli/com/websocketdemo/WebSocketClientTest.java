package canvas.liaoli.com.websocketdemo;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class WebSocketClientTest {


    private static String hostName = "localhost";

    private static int port = 58545;
    private static WebSocket mWebSocket;

    public static void main(String[] args) {
        connectServer();
    }

    private static void connectServer() {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        //构造request对象
        Request request = new Request.Builder()
                .url("ws://" + hostName + ":" + port + "/")
                .build();
//new 一个websocket调用对象并建立连接

        mWebSocket = client.newWebSocket(request, new WebSocketListener() {
            public int Normal_closure_status = 1000;
            public WebSocket webSocket;

            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                this.webSocket = webSocket;
                //打印一些内容
                System.out.println("client onOpen");
                System.out.println("client request header:" + response.request().headers());
                System.out.println("client response header:" + response.headers());
                System.out.println("client response:" + response);

                webSocket.send("hello server");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);

                //打印一些内容
                System.out.println("client onMessage :" + text);

                if("hello client".equals(text)){
                    webSocket.send("I,m a client ,I got you hello!!");
                    webSocket.close(Normal_closure_status,"Normal_closure_status");
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                System.out.println("client onMessage :" + bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                System.out.println("onClosing: code = " + code + ",reason = " + reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                System.out.println("onClosed: code = " + code + ",reason = " + reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                    System.out.println("onFailure: t = " + t );
            }
        });
    }


}
