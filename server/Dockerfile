# Sử dụng JDK 21 JRE (chỉ để chạy, dung lượng nhẹ)
FROM eclipse-temurin:21-jre-jammy

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Copy file jar đã build từ máy build vào container
# Lưu ý: Sẽ lấy file jar có kích thước lớn nhất (Fat Jar) trong thư mục target
COPY target/server-*.jar app.jar

# Mở cổng 8000 (hoặc cổng bạn dùng cho socket/http)
EXPOSE 8000

# Cấu hình biến môi trường mặc định cho Database (có thể ghi đè khi chạy docker)
ENV DB_URL=jdbc:mysql://localhost:3306/auction_db
ENV DB_USER=root
ENV DB_PASSWORD=123Dung#

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
