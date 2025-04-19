# 🕵️ Steganography Web App

A Java-based web application that lets you **hide and reveal secret messages inside PNG images** using LSB (Least Significant Bit) steganography.

Built with:
- ✅ Java 17
- ✅ Spring Boot
- ✅ HTML/CSS (Dark Mode)
- ✅ Vanilla JavaScript

---

## 🚀 Features

- Hide text messages inside images (encode)
- Reveal hidden messages from images (decode)
- Styled dark UI with custom file upload buttons
- Real-time feedback with loading and success alerts
- Download stego image for sharing or storage
- Mobile-friendly responsive layout

---

## 🧠 How to Use

### 🔐 Hide a Secret Message (Encode)

1. In the **encoder section**:
   - Click **“📁 Select Image”** and upload a PNG image.
   - Enter your secret message in the text area.
   - Click **“Hide Message”**.
2. The app will generate a **stego image** with the hidden message.
3. Download the image using the **“Download Image”** button.

### 🔍 Reveal a Hidden Message (Decode)

1. In the **decoder section**:
   - Click **“📁 Select Stego Image”** and upload the PNG image that contains a message.
   - Click **“Decode Message”**.
2. The hidden message (if present) will be displayed below.

---

### 📌 Notes

- Only `.png` images are supported (to avoid compression issues).
- Your message is embedded into the **blue channel** using **LSB encoding**.
- Message ends when a `00000000` byte (null terminator) is detected.
- Ensure the image has enough pixels to hold your full message.

---

## 🧪 Run the Project Locally

```bash
# Clone the repository
git clone https://github.com/your-username/steganography-app.git
cd steganography-app

# Run the Spring Boot app
./mvnw spring-boot:run
