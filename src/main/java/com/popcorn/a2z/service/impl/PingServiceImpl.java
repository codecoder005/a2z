package com.popcorn.a2z.service.impl;

import com.popcorn.a2z.service.PingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Service
@Slf4j
public class PingServiceImpl implements PingService {
    @Override
    public List<String> getHostIPAddresses() throws SocketException {
        List<String> ipAddresses = new ArrayList<>();
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        while (nets.hasMoreElements()) {
            NetworkInterface networkInterface = nets.nextElement();
            Enumeration<InetAddress> addr = networkInterface.getInetAddresses();
            while (addr.hasMoreElements()) {
                InetAddress inetAddress = addr.nextElement();
                if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
                    ipAddresses.add(inetAddress.getHostAddress());
                }
            }
        }
        log.info("IP Addresses: {}", ipAddresses);
        return ipAddresses;
    }
}
