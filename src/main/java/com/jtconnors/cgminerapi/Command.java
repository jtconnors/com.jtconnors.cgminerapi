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

import java.util.logging.Level;
import java.util.logging.Logger;

public class Command {

    private static final Logger LOGGER = 
            Logger.getLogger("com.jtconnors.cgminerapi");

    private final Request request;
    private final String parameter;

    /**
     * Parse a HTTP query string specific to the cgminer API and return a
     * Command object. Query string take the format:
     *
     * {@code ?command=REQUEST&PARAMETER}
     *
     * where REQUEST is a valid cgminer API request and PARAMETER is an optional
     * parameter (depending on the request).
     *
     * @param queryString the HTTP query string to parse
     * @return Command object representing the parsed query string
     * @throws InvalidQueryStringException if the HTTP query string is not
     * syntactically valid
     */
    public static Command parseQueryString(String queryString) throws
            InvalidQueryStringException {
        LOGGER.log(Level.INFO, "Query str = {0}", queryString);
        if (queryString == null || queryString.equals("")) {
            throw new InvalidQueryStringException("null query string.");
        }

        // Check that query string starts with "command="
        String[] pairs = queryString.split("&|;");
        int commandIdx = pairs[0].indexOf("=");
        if (commandIdx == -1 || 
                !pairs[0].substring(0, commandIdx).equals("command")) {
            throw new InvalidQueryStringException(
                    "\"command=\" expected at start of query string " 
                    + "\"" + queryString + "\"");
        }

        // Request appears to the right of "command=".  Check to see
        // that it's valid and convert string to a Request instance.
        String requestStr
                = pairs[0].substring(commandIdx + 1, pairs[0].length());
        Request request = Request.toRequest(requestStr);
        if (request == null) {
            throw new InvalidQueryStringException(
                    "Invalid request in query string: \"" + requestStr +"\"");
        }

        // Parse optional parameter if required and syntax check for correctness
        // (string after '&' or ';' character)
        String parameter = null;
        if (request.includesParameter()) {
            if (pairs.length < 2) {
                throw new InvalidQueryStringException(
                    "Missing parameter to \"" + requestStr + "\" command"); 
            }
            int parameterIdx = pairs[1].indexOf("=");
            if (parameterIdx == -1 || parameterIdx == pairs[1].length()-1) {
                throw new InvalidQueryStringException(
                    "Missing device id parameter in " + queryString);      
            }
            if (!pairs[1].substring(0, parameterIdx).equals("parameter")) {
                throw new InvalidQueryStringException(
                    "Missing \"parameter\" keyword in " + queryString);     
            }
            parameter = pairs[1].substring(parameterIdx+1, pairs[1].length());
            try {
                Integer.valueOf(parameter);
            } catch (NumberFormatException e) {
                throw new InvalidQueryStringException(
                    "device id parameter is not an integer in " + queryString);  
            }
        }

        Command retVal = new Command(request, parameter);
        LOGGER.log(Level.INFO, "JSON request: {0}", retVal.toJSONString());
        return retVal;
    }

    /**
     * Convert the Command instance into a JSON formated string. A JSON request
     * follows this format:
     *
     * '{"command":"CMD","parameter":"PARAM"}'
     *
     * where CMD is a valid Request and PARAM is an optional parameter (based
     * upon the Request).
     *
     * @return JSON representation of Command
     */
    public String toJSONString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"command\":\"");
        sb.append(request.toRequestString());
        sb.append("\"");
        if (request.includesParameter() && parameter != null) {
            sb.append(",");
            sb.append("\"parameter\":\"");
            sb.append(parameter);
            sb.append("\"");
        }
        sb.append("}");
        return sb.toString();
    }

    public Command(Request request, String parameter) {
        this.request = request;
        this.parameter = parameter;
    }

}
