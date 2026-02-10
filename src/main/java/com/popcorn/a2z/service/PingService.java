package com.popcorn.a2z.service;

import java.net.SocketException;
import java.util.List;

public interface PingService {
    /**
     * Retrieves all non-loopback, site-local IP addresses assigned to the current host machine.
     * <p>
     * This method scans all available network interfaces and collects IP addresses that match:
     * <ul>
     *   <li>Not a loopback address (e.g., 127.0.0.1)</li>
     *   <li>A site-local address (private IP ranges, e.g., 192.168.x.x, 10.x.x.x, 172.16.x.x - 172.31.x.x)</li>
     * </ul>
     * <p>
     * Useful in containerized or multi-network environments where the host may have
     * multiple network interfaces and IP addresses (e.g., Docker bridge, VPN, Wi-Fi, LAN).
     *
     * @return a list of IP addresses found for the host (may contain multiple entries)
     * @throws SocketException if an I/O error occurs while accessing network interfaces
     */
    List<String> getHostIPAddresses() throws SocketException;
}
