package org.jim.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TickerTest {

    @Test
    public void testDemo() throws Exception {
        String url = "http://127.0.0.1:9092";

        Socket socket = IO.socket(url);
        System.out.println("===== Instantiated");

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("CONNECTED");

                TickerObject to = new TickerObject();
                to.setSymbol("BTC");
                to.setPrice(3443.5);

                // FIXME: 必须JSONObject,直接字符串不行
                JSONObject obj = new JSONObject(to);
                String json = "{\"symbol\":\"BTC\",\"price\":3443.5}";

                socket.emit("ticker", obj);

            }
        }).on("ticker", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Object obj = args[0];
                JSONObject json = (JSONObject) obj;
                System.out.println(json.toString());
            }
        });
        System.out.println("===== Configured");

        socket.connect();
        System.out.println("===== Connected");

        Thread.sleep(10000L);
    }

    @Test
    public void testJson() throws Exception {
        TickerObject to = new TickerObject();
        to.setSymbol("BTC");
        to.setPrice(3443.5);

        JSONObject obj = new JSONObject(to);
        System.out.println(obj.toString());

        String toJson = "{\"symbol\":\"BTC\",\"price\":3443.5}";
        ObjectMapper om = new ObjectMapper();
        TickerObject too = om.readValue(toJson, TickerObject.class);
        System.out.println(too);
    }
}
