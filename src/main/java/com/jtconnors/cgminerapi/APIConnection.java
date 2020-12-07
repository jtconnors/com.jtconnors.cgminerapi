/*
 * Copyright (c) 2020, Jim Connors
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *   * Neither the name of this project nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.jtconnors.cgminerapi;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements socket connection to/from a running cgminer instance.
 * The specification for the cgminer API can found here:
 * 
 * https://github.com/ckolivas/cgminer/blob/master/API-README
 * 
 * This API is specific to cgminer version 4.10.0
 * 
 * @author jtconnor
 */
public class APIConnection {
    
    public static final String API_VERSION = "4.10.0";

    private static final Logger LOGGER = 
            Logger.getLogger("com.jtconnors.cgminerapi");

    private final InetAddress ipAddr;
    private final int port;

    private static final int MAX_BYTES = 65535;

    /**
     * Perform an API call to a running cgminer instance.
     * @param jsonCmd JSON String representing the command.
     * @return a JSON string representing the response to this call.
     * @throws IOException if an error occurs when operating on the socket
     * connection
     */
    public String apiCall(String jsonCmd) throws IOException {
        LOGGER.log(Level.INFO, "command = {0}", jsonCmd);
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[MAX_BYTES];
        int len;
        try (final Socket socket = new Socket(ipAddr, port)) {
            PrintStream ps = new PrintStream(socket.getOutputStream());
            ps.print(jsonCmd);
            ps.flush();  
            
            InputStreamReader isr = 
                    new InputStreamReader(socket.getInputStream());
            while (true) {
                len = isr.read(buf, 0, MAX_BYTES);
                if (len < 1) {
                    break;
                }
                /*
                 * Check for a null terminating character and eliminate it.
                 * This isn't C. HTTP clients don't like text strings with 
                 * characters.
                 */
                if (buf[len - 1] == '\0') {
                    sb.append(buf, 0, len-1);
                    break;
                } else {
                    sb.append(buf, 0, len);    
                }
            }
        } catch (IOException e) {
           throw new IOException(e.getMessage() + " " + ipAddr + ":" + port, e);    
        }
        LOGGER.log(Level.INFO, "reply = {0}", sb);
        return sb.toString();
    }    

    /**
     * Creates an APIConnection
     * @param ipAddrStr the IP Address or hostname of the host
     * @param port the port number 
     * @throws UnknownHostException if the IP address of {@code ipAddrStr}
     * cannot be determined.
     */
    public APIConnection(String ipAddrStr, int port) throws
            UnknownHostException {
        this.ipAddr = InetAddress.getByName(ipAddrStr);
        this.port = port;
    }
}
