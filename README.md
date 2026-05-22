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

## 💻 Hướng dẫn cài đặt & chạy kiểm thử cho người mới bắt đầu

Để bắt đầu chạy dự án kiểm thử này, bạn cần thực hiện theo các bước chi tiết dưới đây:

### 📑 Bước 1: Chuẩn bị môi trường hệ thống

#### 1. Cài đặt Java Development Kit (JDK 11)
Framework được xây dựng tối ưu trên **Java 11**.
- **Tải về:** Bạn có thể tải JDK 11 từ [Eclipse Temurin](https://adoptium.net/temurin/releases/?version=11) hoặc [Azul Zulu](https://www.azul.com/downloads/?package=jdk#zulu).
- **Cấu hình biến môi trường (Windows):**
  1. Mở *Environment Variables* (Biến môi trường) trên Windows.
  2. Tạo một biến hệ thống mới tên là `JAVA_HOME` với giá trị là đường dẫn đến thư mục cài đặt JDK (Ví dụ: `C:\Program Files\Eclipse Adoptium\jdk-11.x.x`).
  3. Tìm biến `Path` trong danh sách, chọn *Edit*, nhấn *New* và thêm dòng sau: `%JAVA_HOME%\bin`.
- **Kiểm tra cài đặt:** Mở Terminal/CMD và gõ lệnh:
  ```bash
  java -version
  ```
  *(Kết quả hiển thị phiên bản Java 11.x là thành công)*

#### 2. Cài đặt Apache Maven (Quản lý dự án & Build)
- **Tải về:** Tải bản ZIP của Maven từ [trang chủ Apache Maven](https://maven.apache.org/download.cgi).
- **Cấu hình biến môi trường (Windows):**
  1. Giải nén file ZIP vừa tải vào một thư mục cố định (Ví dụ: `C:\maven`).
  2. Tạo biến hệ thống mới tên là `MAVEN_HOME` với giá trị là thư mục giải nén (`C:\maven`).
  3. Thêm `%MAVEN_HOME%\bin` vào biến hệ thống `Path`.
- **Kiểm tra cài đặt:** Mở Terminal/CMD và gõ lệnh:
  ```bash
  mvn -version
  ```

#### 3. Cài đặt Allure Commandline (Để xem báo cáo kiểm thử)
Để mở được báo cáo HTML Allure trực quan, máy của bạn cần có Allure CLI.
- **Windows (Khuyên dùng Scoop):**
  ```powershell
  scoop install allure
  ```
  *Hoặc cài đặt thủ công:* Tải bản Allure .zip mới nhất từ [GitHub Allure Releases](https://github.com/allure-framework/allure2/releases), giải nén và thêm đường dẫn thư mục `bin` vào biến môi trường `Path`.
- **macOS (Dùng Homebrew):**
  ```bash
  brew install allure
  ```
- **Kiểm tra cài đặt:**
  ```bash
  allure --version
  ```

---

### 📥 Bước 2: Clone dự án và Mở trên IDE

1. **Tải mã nguồn dự án** về máy tính của bạn.
2. **Mở trên IntelliJ IDEA (Khuyên dùng):**
   - Chọn **Open** -> Chọn thư mục dự án chứa file `pom.xml`.
   - IDE sẽ tự động nhận diện dự án Maven và bắt đầu tải các thư viện cần thiết. Quá trình tải có thể mất vài phút ở lần đầu tiên.
   - Đảm bảo thiết lập cấu hình SDK của dự án là Java 11 (Vào *File* -> *Project Structure* -> *Project* -> chọn SDK Java 11).

---

### ⚙️ Bước 3: Cấu hình tham số kiểm thử

Mở file [configs.properties](file:///c:/Users/nhan.vuong/Desktop/Course/example/nhims-core/nhims-core-testng/src/test/resources/settings/configs.properties) để điều chỉnh các thiết lập mong muốn:

```properties
# 1. Chọn môi trường chạy: staging | production | nightlight
environment = staging

# 2. Chọn trình duyệt: chrome | firefox | edge
driver = chrome

# 3. Tự động chụp ảnh màn hình khi kết thúc testcase: true | false
capture = true

# 4. Tự động quay video màn hình chạy test: true | false (chỉ hỗ trợ Windows)
video = false

# 5. Ghi log hoạt động chi tiết ra console và file: true | false
logger = true
```

*Lưu ý:* URL của từng môi trường tương ứng được định nghĩa bên trong các file [staging.properties](file:///c:/Users/nhan.vuong/Desktop/Course/example/nhims-core/nhims-core-testng/src/test/resources/settings/staging.properties), [production.properties](file:///c:/Users/nhan.vuong/Desktop/Course/example/nhims-core/nhims-core-testng/src/test/resources/settings/production.properties), [nightlight.properties](file:///c:/Users/nhan.vuong/Desktop/Course/example/nhims-core/nhims-core-testng/src/test/resources/settings/nightlight.properties).

---

### 🚀 Bước 4: Chạy kiểm thử (Execution)

Bạn có thể chạy kiểm thử bằng dòng lệnh hoặc trực tiếp trên công cụ lập trình (IDE):

#### Cách 1: Sử dụng Maven dòng lệnh (Terminal/Command Line)
Mở Terminal tại thư mục gốc của dự án và chạy các lệnh tương ứng:

```bash
# Chạy suite kiểm thử Regression (testng-regression.xml)
mvn clean test -DsuiteXmlFile=testng-regression.xml

# Chạy suite kiểm thử Smoke nhanh (testng-smoke.xml)
mvn clean test -DsuiteXmlFile=testng-smoke.xml

# Chạy suite mặc định (testng.xml)
mvn clean test

# Chạy riêng 1 Test Class cụ thể
mvn test -Dtest=TestExample

# Chạy riêng 1 Test Case (Method) cụ thể
mvn test -Dtest=TestExample#testCase001
```

#### Cách 2: Chạy trực tiếp trên IntelliJ IDEA
- Click chuột phải vào file XML bộ kiểm thử (ví dụ [testng-regression.xml](file:///c:/Users/nhan.vuong/Desktop/Course/example/nhims-core/nhims-core-testng/testng-regression.xml)) và chọn **Run**.
- Hoặc mở file [TestExample.java](file:///c:/Users/nhan.vuong/Desktop/Course/example/nhims-core/nhims-core-testng/src/test/java/com/nhims/scripts/TestExample.java) và nhấn vào biểu tượng **nút Run màu xanh** cạnh tên class hoặc method test.

---

### 📊 Bước 5: Đọc báo cáo và kết quả kiểm thử

Sau khi chạy xong, kết quả được xuất ra các thư mục sau:
1. **Logs hoạt động:** Được lưu tại `test-reports/logs/app.log` (ghi nhận chi tiết từng bước click, nhập liệu, chuyển trang, luồng chạy).
2. **Ảnh chụp lỗi (Screenshot):** Tự động lưu khi test case thất bại tại thư mục `test-reports/screenshots/<timestamp>/`.
3. **Video ghi hình:** Được ghi và xuất ra thư mục `test-reports/videos/<timestamp>/` (nếu cấu hình `video=true`).
4. **Báo cáo Allure (HTML Report sinh động):**
   Chạy lệnh sau trên terminal để Allure khởi động Web Server và tự động mở báo cáo trên trình duyệt:
   ```bash
   mvn allure:serve
   ```

---

### 👥 Chạy song song (Parallel execution)

Mặc định, regression suite được thiết lập chạy song song ở cấp độ phương thức (`parallel="methods"`) với 4 luồng chạy đồng thời. Bạn có thể thay đổi số luồng này trong file cấu hình xml:

```xml
<suite name="NHIMS Regression Suite" parallel="methods" thread-count="4">
```
Các chế độ song song hỗ trợ: `methods` (phương thức) | `classes` (lớp kiểm thử) | `tests` (thẻ test) | `none` (chạy tuần tự).

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
