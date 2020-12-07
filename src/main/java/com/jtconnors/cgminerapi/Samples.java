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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.invoke.MethodHandles;
import java.lang.management.ManagementFactory;
import static com.jtconnors.cgminerapi.CgArgs.*;

import javax.json.Json;

/*
 * Sample invocations of the cgminer API.  This must have access to a running cgminer
 * instance.
 */
public class Samples {

    private static final Logger LOGGER = 
            Logger.getLogger("com.jtconnors.cgminerapi");

    private static final String PROGNAME= "samples";
    private static CgArgs cgArgs;

    static {
        cgArgs = new CgArgs(MethodHandles.lookup().lookupClass(), RESOURCE_NAME, PROGNAME);
        cgArgs.addAllowableArg(CGMINERHOST, "jtconnors.com");
        cgArgs.addAllowableArg(CGMINERPORT, "4028");
        cgArgs.addAllowableArg(DEBUGLOG, "false");
    }
    
    private static void printParseReply(List<Reply> parseReply) {
        if (parseReply.size() <= 1) {
            for (Reply reply : parseReply) {
                LOGGER.log(Level.INFO, "Parsed Reply = {0}\n", reply);
            } 
        } else {
            for (int i=0; i<parseReply.size()-1; i++) {
                LOGGER.log(Level.INFO, "Parsed Reply = {0}", parseReply.get(i));
            }
            LOGGER.log(Level.INFO, "Parsed Reply = {0}\n",
                parseReply.get(parseReply.size()-1));
        }      
    }
    
    public static void main(String[] args) throws IOException {
        String cgminerHost;
        int cgminerPort;
        boolean debugLog;
        /*
         * Print out elasped time it took to get to here.  For argument's sake
         * we'll call this the startup time.
         */
        System.err.println("Startup time = " +
                (System.currentTimeMillis() -
                ManagementFactory.getRuntimeMXBean().getStartTime()) + "ms");

        cgArgs.parseArgs(args);
        cgminerHost = cgArgs.getProperty(CGMINERHOST);
        cgminerPort = Integer.parseInt(cgArgs.getProperty(CGMINERPORT));
        debugLog = Boolean.parseBoolean(cgArgs.getProperty(DEBUGLOG));
        if (!debugLog) {
            LOGGER.setLevel(Level.OFF);
        }
        Util.checkHostValidity(cgminerHost);
		APIConnection apiConn = new APIConnection(cgminerHost, cgminerPort); 
        LOGGER.log(Level.INFO, "cgminerHost = {0}", cgminerHost);
        LOGGER.log(Level.INFO, "cgminerPort = {0}", cgminerPort);
        LOGGER.log(Level.INFO, "debugLog = {0}\n", debugLog);
        
        // Issue a SUMMARY command using the Command class methods and
        // equest enum.  Convert to JSON String.
        String jsonString = new Command(Request.SUMMARY, null).toJSONString();
        String replyStr = apiConn.apiCall(jsonString);
        JSONParser parser = new JSONParser(replyStr);
        printParseReply(parser.parseReply());
        
        // Issue another SUMMARY command, this time using the Json class methods
        jsonString = Json.createObjectBuilder()
                .add("command", "summary")
                .build()
                .toString();
        replyStr = apiConn.apiCall(jsonString);
        parser = new JSONParser(replyStr);
        printParseReply(parser.parseReply());        
        
        // Issue a DEVS Command int JSON format
        jsonString = Json.createObjectBuilder()
                .add("command", "devs")
                .build()
                .toString();
        replyStr = apiConn.apiCall(jsonString);
        parser = new JSONParser(replyStr);
        printParseReply(parser.parseReply());
        
        // Issue an ASCDISABLE command on AISC 0
        jsonString = new Command(Request.ASCDISABLE, "0").toJSONString();
        replyStr = apiConn.apiCall(jsonString);
        parser = new JSONParser(replyStr);
        printParseReply(parser.parseReply());
        
        // Issue an ASCENABLE command on AISC 0
        jsonString = new Command(Request.ASCENABLE, "0").toJSONString();
        replyStr = apiConn.apiCall(jsonString);
        parser = new JSONParser(replyStr);
        printParseReply(parser.parseReply());

        LOGGER.log(Level.INFO, "Memory usage = {0}", 
                Runtime.getRuntime().totalMemory() -
                Runtime.getRuntime().freeMemory());
    } 
}

