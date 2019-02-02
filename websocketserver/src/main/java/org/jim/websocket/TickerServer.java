package org.jim.websocket;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.protocol.AckArgs;
import com.corundumstudio.socketio.protocol.JacksonJsonSupport;
import com.corundumstudio.socketio.protocol.JsonSupport;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class TickerServer implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TickerServer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        SocketIOServer server = new SocketIOServer(config);

        server.addEventListener("subscribe", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String room, AckRequest ackRequest) throws Exception {
                System.out.println("subscribe: " + room);
            }
        });

        server.addEventListener("ticker", TickerObject.class, new DataListener<TickerObject>() {
            @Override
            public void onData(SocketIOClient client, TickerObject data, AckRequest ackRequest) {
                System.out.println("ticker: " + data.toString());

                // broadcast messages to all clients
                server.getBroadcastOperations().sendEvent("ticker", data);
            }
        });

        server.start();


    }

}
