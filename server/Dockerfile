# === BƯỚC 1: MƯỢN MÁY MAVEN ĐỂ BIÊN DỊCH TOÀN BỘ DỰ ÁN ===
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy toàn bộ mã nguồn dự án từ ngoài cùng (bao gồm cả common, server và pom.xml chính)
COPY . .

# Tiến hành build toàn bộ hệ thống (bỏ qua chạy thử test để build nhanh hơn)
RUN mvn clean package -DskipTests

# === BƯỚC 2: CONTAINER CHẠY CHÍNH (SIÊU NHẸ) ===
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Lấy đúng file .jar từ thư mục target của module server sau khi build xong
COPY --from=build /app/server/target/*.jar app.jar

# Lệnh kích hoạt ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]