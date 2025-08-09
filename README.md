# Java Port Scanner

A lightweight, multithreaded port scanner written in **Java**.  
This tool allows you to scan a range of ports on a given target (IP or domain) to check which ones are open, with optional banner grabbing for basic service identification.

---

## Features
- **Multithreaded scanning** using a fixed thread pool (100 threads by default) for faster results.
- **Customizable scan range**: Specify starting and ending ports.
- **Domain/IP resolution**: Automatically resolves domain names to IP addresses.
- **Open port detection** with optional banner grabbing.
- **Timeout handling** to skip unresponsive ports quickly.

---

## How It Works
1. **User input**:
   - Target domain name.
   - Start and end port range.
2. The program resolves the target's hostname to its IP address.
3. A thread pool scans ports concurrently to improve speed.
4. For each open port found, the program attempts to retrieve a **banner** (if available) to identify the service.

---

## Example Usage

```bash
Enter target IP or domain: example.com
Enter starting port: 20
Enter ending port: 100

Scanning host: example.com (93.184.216.34)
Port 22 is OPEN
 Banner: SSH-2.0-OpenSSH_8.4
Port 80 is OPEN
 Banner: HTTP/1.1 200 OK
Scan complete.
