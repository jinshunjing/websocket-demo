package org.jim.websocket;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BtcSocketIOTest {

    @Test
    public void testDemo() throws Exception {
        String url = "http://47.106.196.27:4001";
        url = "http://121.196.197.246:3001";

        Socket socket = IO.socket(url);
        System.out.println("===== Instantiated");

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("CONNECTED");

                socket.emit("subscribe", "inv");
                System.out.println("SUBSCRIBED");
            }
        }).on("tx", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Object obj = args[0];
                JSONObject json = (JSONObject) obj;
                System.out.println(json.toString());
                // {"isRBF":false,"txid":"62d7d83df8dbc03f41b1aafe7528c12f380a83e54888eaeba920badb77873238","valueOut":0.02484703,"vout":[{"1PbjLgMTnLELPSnQtLWnZM5kdF8m6K6AGm":2484703}]}
            }
        });
        System.out.println("===== Configured");

        socket.connect();
        System.out.println("===== Connected");

        Thread.sleep(10000L);
    }

}
