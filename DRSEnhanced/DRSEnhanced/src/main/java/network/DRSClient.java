
package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class DRSClient {

    private final String host;
    private final int port;

    public DRSClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String sendRequest(String request)
            throws IOException {

        try (Socket socket = new Socket()) {

            socket.connect(
                new InetSocketAddress(host, port),
                5000
            );

            socket.setSoTimeout(5000);

            try (
                PrintWriter out = new PrintWriter(
                    socket.getOutputStream(),
                    true,
                    StandardCharsets.UTF_8
                );

                BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                        socket.getInputStream(),
                        StandardCharsets.UTF_8
                    )
                )
            ) {

                out.println(request);

                String response = in.readLine();

                if (response == null) {
                    throw new IOException(
                        "No response received from server."
                    );
                }

                return response;
            }
        }
    }
}
