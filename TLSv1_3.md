# 🔐 Spring Boot HTTPS Setup (TLS 1.3 + RSA-4096 + AES-256)

This guide configures a **Spring Boot** application to run securely over **HTTPS** using a **self-signed certificate** generated with **OpenSSL**.  
It prioritizes **maximum encryption strength** — not performance.

---

## 🧱 Overview

| Setting | Value |
|----------|--------|
| Protocol | **TLS 1.3** |
| Key Algorithm | **RSA 4096-bit** |
| Cipher | **AES-256-GCM** |
| Signature Algorithm | **SHA-512** |
| Keystore Format | **PKCS#12 (.p12)** |
| Purpose | Development / internal secure testing |

---

## 🪄 1. Generate Certificates with OpenSSL

Run all commands from your terminal (Linux, macOS, or WSL).

### 🔹 Step 1 — Generate a Private Key (AES-256 Encrypted)

```bash
openssl genpkey -algorithm RSA -out server.key -aes256 -pkeyopt rsa_keygen_bits:4096
```
You’ll be prompted for a passphrase — remember it for later.

### 🔹Step 2 — Generate a Certificate Signing Request (CSR)

```bash
openssl req -new -key server.key -out server.csr -sha512
```
#### Provide the required information:

Country Name (2 letter code) [AU]: IN

State or Province Name (full name) [Some-State]: Telangana

Locality Name (eg, city) []: Hyderabad

Organization Name (eg, company) []: MedTimes

Organizational Unit Name (eg, section) []: Backend

Common Name (e.g., your server's hostname) []: localhost

Email Address []: admin@medtimes.app

### 🔹 Step 3 — Create a Self-Signed Certificate (Valid 10 Years)
```bash
openssl x509 -req -days 3650 -in server.csr -signkey server.key -out server.crt -sha512
```
This produces a self-signed certificate (`server.crt`) valid for 10 years.

### 🔹 Step 4 — Convert to PKCS#12 Keystore for Spring Boot
```bash
openssl pkcs12 -export \
  -in server.crt \
  -inkey server.key \
  -out keystore.p12 \
  -name "a2z-ssl" \
  -CAfile server.crt \
  -caname root \
  -passout pass:changeit
```
#### ✅ Output: keystore.p12 — the file Spring Boot actually uses.

## 📁 2. Place the Keystore in Your Project
Move the generated file into your Spring Boot resources directory: `src/main/resources/keystore.p12`

## ⚙️ 3. Configure Spring Boot for HTTPS (TLS 1.3)

Edit src/main/resources/application.yml and add:

```code 
server:
  port: 8443
  ssl:
    enabled: true
    key-store: classpath:keystore.p12
    key-store-password: changeit
    key-store-type: PKCS12
    key-alias: a2z-ssl
```

