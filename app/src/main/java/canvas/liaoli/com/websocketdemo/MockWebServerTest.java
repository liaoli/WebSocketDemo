package canvas.liaoli.com.websocketdemo;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.ByteString;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class MockWebServerTest {

    private final MockWebServer mockWebServer = new MockWebServer();
    private final ExecutorService writeExecutor = Executors.newSingleThreadExecutor();



    public static void main(String[] args){

        new MockWebServerTest().startServer();

    }

    private  void startServer() {

        String hostName = mockWebServer.getHostName();
        int port = mockWebServer.getPort();

        System.out.println("hostName:" + hostName);
        System.out.println("port:" + port);

        mockWebServer.enqueue(new MockResponse().withWebSocketUpgrade(new WebSocketListener() {

            WebSocket webSocket = null;
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);

                this.webSocket = webSocket;
                //打印一些内容
                System.out.println("server onOpen");
                System.out.println("server request header:" + response.request().headers());
                System.out.println("server response header:" + response.headers());
                System.out.println("server response:" + response);


            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                System.out.println("onMessage: " + text);
                if("hello server".equals(text)){
                    webSocket.send("hello client");
                }else if("I,m a client ,I got you hello!!".equals(text)){

                }
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                System.out.println("onMessage: " + bytes);
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
                try {
                    System.out.println("onFailure: t = " + t + ",response = " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

}
