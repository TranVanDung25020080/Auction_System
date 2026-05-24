# === BƯỚC 1: MƯỢN MÁY MAVEN ĐỂ BIÊN DỊCH CODE TỪ GITHUB ===
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
# Copy toàn bộ mã nguồn vào máy build
COPY . .
# Biên dịch ra file jar (bỏ qua chạy thử test để build nhanh hơn)
RUN mvn clean package -DskipTests

# === BƯỚC 2: CONTAINER CHẠY CHÍNH (SIÊU NHẸ) ===
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy file jar vừa build xong ở Bước 1 sang máy chạy này
# (Lệnh này sẽ tự động tìm file .jar trong thư mục target vừa build)
COPY --from=build /app/target/*.jar app.jar

# Các biến môi trường mặc định (chỉ dùng làm dự phòng khi test local)
# Khi lên Railway, bạn điền các thông số này vào mục "Variables" trên web nhé

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]