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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * This class parses the JSON replies to cgminer API calls.  
 * @author jtconnor
 */
public class JSONParser {

    private final JsonObject jsonReply;

    private ReplyStatus parseReplyStatus() throws InvalidReplyException {
        JsonArray statusArray = jsonReply.getJsonArray(Reply.STATUS_REPLY);
        if (statusArray.size() != 1) {
            throw new InvalidReplyException(
                    "Expected JSON Reply Status Array of size 1, got "
                    + statusArray.size());
        }
        JsonObject jsonStatus = statusArray.getJsonObject(0);
        return new ReplyStatus(jsonStatus);
    }

    private ReplySummary parseReplySummary() throws InvalidReplyException {
        JsonArray summaryArray = jsonReply.getJsonArray(Reply.SUMMARY_REPLY);
        if (summaryArray.size() != 1) {
            throw new InvalidReplyException(
                    "Expected JSON Reply Summary Array of size 1, got "
                    + summaryArray.size());
        }
        JsonObject jsonSummary = summaryArray.getJsonObject(0);
        return new ReplySummary(jsonSummary);
    }

    private List<ReplyDevs> parseReplyDevs() throws InvalidReplyException {
        List<ReplyDevs> devList = new ArrayList<>();
        JsonArray devArray = jsonReply.getJsonArray(Reply.DEVS_REPLY);
        for (int index = 0; index < devArray.size(); index++) {
            devList.add(new ReplyDevs(devArray.getJsonObject(index)));
        }
        return devList;
    }

    private static boolean isCommand(JsonObject jsonObject, String command) {
        return jsonObject.get(command) != null;
    }

    public List<Reply> parseReply() {
        ArrayList<Reply> replyList = new ArrayList<>();
        try {
            replyList.add(parseReplyStatus());
            if (isCommand(jsonReply, Reply.SUMMARY_REPLY)) {
                replyList.add(parseReplySummary());
            } else if (isCommand(jsonReply, Reply.DEVS_REPLY)) {
                for (Reply reply : parseReplyDevs()) {
                    replyList.add(reply);
                }
            }
        } catch (InvalidReplyException ex) {
            Logger.getLogger(
                JSONParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return replyList;
    }

    public JSONParser(String replyStr) {
        try (JsonReader rdr = Json.createReader(new StringReader(replyStr))) {
            jsonReply = rdr.readObject();
        }
    }

}
