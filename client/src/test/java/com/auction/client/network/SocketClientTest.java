package com.auction.client.network;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import static org.junit.jupiter.api.Assertions.*;

class SocketClientTest {
    private ServerSocket dummyServer;
    private int dynamicTestPort;

    @BeforeEach
    void setUp() throws IOException {
        dummyServer = new ServerSocket(0);
        dynamicTestPort = dummyServer.getLocalPort();
    }

    @AfterEach
    void tearDown() throws IOException {
        if (dummyServer != null && !dummyServer.isClosed()) {
            dummyServer.close();
        }
    }

    @Test
    void testClientConnection_Success() {
        // test Client tạo luồng Socket kết nối thành công tới IP/Port giả lập đang mở
        assertDoesNotThrow(() -> {
            Thread serverThread = new Thread(() -> {
                try (Socket acceptedSocket = dummyServer.accept()) {
                } catch (IOException ignored) {}
            });
            serverThread.start();

            Socket clientSocket = new Socket("127.0.0.1", dynamicTestPort);

            assertTrue(clientSocket.isConnected(), "Lỗi: Client Socket chưa được kết nối thành công!");

            clientSocket.close();
            serverThread.join(1000);
        });
    }

    @Test
    void testClientConnection_ServerOffline_ShouldFail() {
        // Sử dụng một IP không tồn tại (IP ma) để chắc chắn Server luôn offline
        String nonExistentHost = "192.0.2.1";
        int anyPort = 12345;

        assertThrows(IOException.class, () -> {
            Socket socket = new Socket();
            socket.connect(new java.net.InetSocketAddress(nonExistentHost, anyPort), 1000);
            socket.close();
        }, "Lỗi: Server offline hoàn toàn nhưng Client không quăng lỗi IOException!");
    }
}