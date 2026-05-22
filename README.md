# NHIMS Core TestNG — Automation Framework

Dự án kiểm thử tự động hóa (Automation Testing) được xây dựng dựa trên sự kết hợp giữa **Java**, **Selenium WebDriver**, **TestNG** và quản lý phụ thuộc bằng **Maven**. Framework áp dụng mô hình thiết kế **Page Object Model (POM)** kết hợp các thư viện tùy biến (Custom Controls & Utilities) nhằm tối ưu hiệu năng và độ ổn định khi chạy song song (parallel).

---

## 🛠️ Công nghệ sử dụng (Tech Stack)

| Thành phần | Phiên bản | Mục đích |
|:---|:---:|:---|
| Java | 11+ | Ngôn ngữ lập trình |
| Selenium WebDriver | 4.18.1 | Điều khiển trình duyệt |
| TestNG | 7.7.1 | Framework kiểm thử & song song |
| Allure Report | 2.25.0 | Báo cáo HTML đẹp & chi tiết |
| SLF4J + Logback | 2.0.7 / 1.4.8 | Ghi log bất đồng bộ, an toàn đa luồng |
| Monte Screen Recorder | 0.7.7.0 | Ghi video quá trình chạy test |
| Maven | 3.x | Quản lý dự án & build |

---

## 📂 Cấu trúc dự án (Project Structure)

```text
nhims-core-testng/
├── src/
│   ├── main/
│   │   ├── java/com/nhims/
│   │   │   ├── browsers/      # BrowserExtensions, Navigation, Browsers
│   │   │   ├── constants/     # Configs, FileConst, JavaScript, TimeConst
│   │   │   ├── controls/      # Control (stateless), Actions, BaseControl, Keyboards
│   │   │   ├── drivers/       # BrowserFactory, DriverController, DriverExtensions
│   │   │   └── utils/         # Logger (SLF4J), RecordVideo, HFile, HDate, HFolder, Convert
│   │   └── resources/
│   │       └── logback.xml    # Cấu hình logging (Console + RollingFile)
│   └── test/
│       ├── java/com/nhims/
│       │   ├── data/          # Hằng số dữ liệu kiểm thử (FlyMeeConst)
│       │   ├── listeners/     # TestListener — lifecycle hooks (screenshot, video, driver)
│       │   ├── pages/         # Page Objects: BasePage, GeneralPage, SearchPage, ProductDetailPage
│       │   └── scripts/       # Test scripts: TestExample
│       └── resources/settings/
│           ├── configs.properties     # Cấu hình chính (driver, environment, video, capture...)
│           ├── staging.properties     # URL môi trường Staging
│           ├── production.properties  # URL môi trường Production
│           └── nightlight.properties  # URL môi trường Nightlight
├── testng.xml                 # Cấu hình bộ kiểm thử (parallel, thread-count)
├── pom.xml                    # Maven dependencies & plugins
└── README.md
```

---

## 🚀 Tính năng nổi bật

1. **Thread-safe Parallel Execution:** Toàn bộ cơ sở hạ tầng sử dụng `ThreadLocal` cho Driver, ScreenRecorder và tìm kiếm phần tử Stateless — hỗ trợ chạy song song thực sự mà không bị Race Condition.
2. **BrowserFactory:** Hỗ trợ Chrome, Firefox, Edge chỉ qua thay đổi `driver=` trong file cấu hình.
3. **Explicit Wait:** Lớp `Control` sử dụng `WebDriverWait` + `ExpectedConditions` thay vì `Thread.sleep`.
4. **SLF4J + Logback:** Ghi log bất đồng bộ, cuộn file theo ngày (30 ngày), định dạng chuẩn.
5. **Allure Report:** Sinh báo cáo HTML chi tiết sau mỗi lần chạy `mvn site`.
6. **Auto Screenshot & Video:** Chụp màn hình và ghi video tự động theo cấu hình, mỗi luồng quản lý video riêng.
7. **Multi-environment:** Hỗ trợ Staging, Production, Nightlight — chuyển môi trường qua `environment=` trong configs.

---

## 💻 Hướng dẫn cài đặt & chạy kiểm thử

### Yêu cầu hệ thống
- **Java JDK 11+** đã cài đặt và cấu hình `JAVA_HOME`.
- **Maven 3.6+** đã cài đặt.
- Trình duyệt tương ứng đã cài đặt (Chrome, Firefox hoặc Edge).

### Cấu hình trước khi chạy

Mở file `src/test/resources/settings/configs.properties` và điều chỉnh:

```properties
# Môi trường: staging | production | nightlight
environment = staging

# Trình duyệt: chrome | firefox | edge
driver = chrome

# Chụp màn hình khi test kết thúc: true | false
capture = true

# Ghi video màn hình: true | false (chỉ hỗ trợ Windows)
video = false

# Ghi log ra file: true | false
logger = true
```

### Các lệnh chạy chính

```bash
# Biên dịch dự án
mvn clean compile

# Biên dịch bao gồm cả test sources
mvn clean test-compile

# Chạy toàn bộ test suite (dùng testng.xml)
mvn test

# Chạy một test class cụ thể
mvn test -Dtest=TestExample

# Sinh Allure HTML Report (sau khi mvn test)
mvn allure:serve
```

### Chạy song song (Parallel)

Mặc định `testng.xml` cấu hình `parallel="methods"` với 4 threads. Để điều chỉnh:

```xml
<!-- testng.xml -->
<suite name="NHIMS Test Suite" parallel="methods" thread-count="4">
```

Các giá trị `parallel`: `methods` | `classes` | `tests` | `none`

---

## ⚙️ Cấu hình nâng cao

### Thêm môi trường mới
1. Tạo file `src/test/resources/settings/<tenmoi>.properties` với `applicationUrl = <url>`.
2. Thêm giá trị enum trong `Configs.Environment`.
3. Thêm điều kiện trong `HFile.getConfigEnvironment()`.

### Thêm trình duyệt mới
Mở `BrowserFactory.java` và thêm case mới:
```java
case "safari":
    return new SafariDriver();
```

### Thêm Page Object mới
```java
public class MyNewPage extends BasePage {
    private static final Control myElement = new Control("//div[@id='my-element']");

    public static void doSomething() {
        myElement.get().click();
    }
}
```

---

## 📊 Báo cáo kiểm thử (Allure Report)

```bash
# Sau khi mvn test, chạy lệnh này để xem báo cáo trên browser:
mvn allure:serve

# Hoặc sinh ra thư mục báo cáo tĩnh:
mvn allure:report
# → Mở target/site/allure-maven-plugin/index.html
```
