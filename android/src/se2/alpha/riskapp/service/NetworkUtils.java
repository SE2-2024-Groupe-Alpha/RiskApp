package se2.alpha.riskapp.service;

import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;


public class NetworkUtils {

    public static boolean isBackendReachable(String url, int timeout) {
        try {
            URL parsedUrl = new URL(url);
            String host = parsedUrl.getHost();
            int port = parsedUrl.getPort() != -1 ? parsedUrl.getPort() : parsedUrl.getDefaultPort();
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(host, port), timeout);
                return true;
            }
        } catch (IOException e) {
            Log.e("NetworkUtils", "Failed to connect to " + url, e);
            return false;
        }
    }

}

