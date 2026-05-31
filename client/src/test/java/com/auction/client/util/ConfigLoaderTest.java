package com.auction.client.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {

    @Test
    void testServerSocketHostConfig() {
        //Test đọc cấu hình Socket Host thực tế trên Railway
        String socketHost = ConfigLoader.get("server.socket.host");

        assertNotNull(socketHost, "Lỗi: Không tìm thấy key 'server.socket.host' trong file config!");
        assertEquals("kodama.proxy.rlwy.net", socketHost, "Lỗi: Giá trị Socket Host cấu hình sai!");

        System.out.println("-> Socket Host chính xác: " + socketHost);
    }

    @Test
    void testServerSocketPortConfig() {
        //Test đọc cấu hình Socket Port thực tế trên Railway
        int socketPort = ConfigLoader.getInt("server.socket.port");

        assertEquals(13556, socketPort, "Lỗi: Cổng kết nối Socket Port không khớp với file cấu hình!");
        assertTrue(socketPort > 0 && socketPort <= 65535, "Lỗi: Giá trị Port không hợp lệ!");

        System.out.println("-> Socket Port chính xác: " + socketPort);
    }

    @Test
    void testServerHttpUrlConfig() {
        //Test đọc cấu hình HTTP URL thực tế trên Railway
        String httpUrl = ConfigLoader.get("server.http.url");

        assertNotNull(httpUrl, "Lỗi: Không tìm thấy key 'server.http.url' trong file config!");
        assertTrue(httpUrl.startsWith("https://"), "Lỗi: API URL phải sử dụng giao thức bảo mật HTTPS!");
        assertEquals("https://auctionsystem-production-7bbb.up.railway.app", httpUrl);

        System.out.println("-> Server HTTP URL chính xác: " + httpUrl);
    }
}