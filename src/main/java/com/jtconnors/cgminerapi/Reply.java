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

import javax.json.JsonObject;
import javax.json.JsonValue;

/**
 * Parent class for object map representations of JSON Replies to
 * the cgminer API.  This class is abstract and contains common shared
 * methods and types.  All cgminer JSON replies should be subclassed from
 * this class.
 * <br><br>
 * The specification for the cgminer API can found here:
 * <br><br>
 * https://github.com/ckolivas/cgminer/blob/master/API-README
 * <br><br>
 * This API is specific to cgminer version 4.10.0
 * <br><br>
 * All command responses are prefaced with a STATUS section that is implemented
 * in the {@code ReplySummary.class}.
 * <br><br>
 * Support for the following commands are currently available and
 * implemented in the subclasses listed below:
 * <br><br>
 * SUMMARY command: {@code ReplySummary.class}<br>
 * DEVS command: {@code ReplyDevs.class}<br>
 */
public abstract class Reply {

    protected Reply() {      
    }
    
    /* 
     * All replies contain a Status block and a response block. These
     * strings represent all of attributes required to parse the set of
     * currently supported commanfs.
     */
    
    // Supported Commands
    public static final String SUMMARY_REPLY = "SUMMARY";
    public static final String DEVS_REPLY = "DEVS";
    public static final String STATUS_REPLY = "STATUS";
    
    // Reponse Status attributes
    public static final String STATUS = "STATUS";
    public static final String WHEN = "When";
    public static final String CODE = "Code";
    public static final String MSG = "Msg";
    public static final String DESCRIPTION = "Description";
    
    // SUMMARY command attributes
    public static final String ELAPSED = "Elapsed";
    public static final String MHS_AV = "MHS av";
    public static final String MHS_5S = "MHS 5s";
    public static final String MHS_1M = "MHS 1m";
    public static final String MHS_5M = "MHS 5m";
    public static final String MHS_15M = "MHS 15m";
    public static final String FOUND_BLOCKS = "Found Blocks";
    public static final String GETWORKS = "Getworks";
    public static final String ACCEPTED = "Accepted";
    public static final String REJECTED = "Rejected";
    public static final String HARDWARE_ERRORS = "Hardware Errors";
    public static final String UTILITY = "Utility";
    public static final String DISCARDED = "Discarded";
    public static final String STALE = "Stale";
    public static final String GET_FAILURES = "Get Failures";
    public static final String LOCAL_WORK = "Local Work";
    public static final String REMOTE_FAILURES = "Remote Failures";
    public static final String NETWORK_BLOCKS = "Network Blocks";
    public static final String TOTAL_MH = "Total MH";
    public static final String WORK_UTILITY = "Work Utility";
    public static final String DIFFICULTY_ACCEPTED = "Difficulty Accepted";
    public static final String DIFFICULTY_REJECTED = "Difficulty Rejected";
    public static final String DIFFICULTY_STALE = "Difficulty Stale";
    public static final String BESTSHARE = "Best Share";
    public static final String DEVICE_HARDWARE_PERCENT = "Device Hardware%";
    public static final String DEVICE_REJECTED_PERCENT = "Device Rejected%";
    public static final String POOL_REJECTED_PERCENT = "Pool Rejected%";
    public static final String POOL_STALE_PERCENT = "Pool Stale%";
    public static final String LAST_GETWORK = "Last getwork";
    
    // Additional attributes needed when support added for DEVS command
    public static final String ASC = "ASC";
    public static final String NAME = "Name";
    public static final String ID = "ID";
    public static final String ENABLED = "Enabled";
    public static final String STATUS_DEVS = "Status";
    public static final String TEMPERATURE = "Temperature";
    public static final String LAST_SHARE_POOL = "Last Share Pool";
    public static final String LAST_SHARE_TIME = "Last Share Time";
    public static final String DIFF1_WORK = "Diff1 Work";
    public static final String LAST_SHARE_DIFFICULTY = "Last Share Difficulty";
    public static final String NO_DEVICE = "No Device";
    public static final String LAST_VALID_WORK = "Last Valid Work";
    public static final String DEVICE_ELAPSED = "Device Elapsed";  

    /**
     * Utility method to create a concatenated string of the form "label=value"
     * @param label the left hand side of the '=' string
     * @param value the right hand side of the '=' string
     * @return concatenated string "label=value"
     */
    protected static String createLabelEqualsValueStr(String label,
            Object value) {
        return (label + "=" + value);
    }

    /**
     * Check to see that the value associated with {@code key} can be retrieved
     * from the {@code jsonObject}. If no such key-value pair
     * exists, throw an {@code InvalidReplyException}, otherwise return
     * and do nothing.
     *
     * @param jsonObject the JSON Object to check for the {@code key}
     * @param key the value of the key to check
     * @throws InvalidReplyException if no such key exists
     */
    private static void checkKey(JsonObject jsonObject, String key)
            throws InvalidReplyException {
        JsonValue jsonValue = jsonObject.get(key);
        if (jsonValue == null) {
            throw new InvalidReplyException("Unexpected key: " + key
                    + " in JSON reply");
        }
    } 

    /**
     * Get the String value in the {@code JsonObject} instance associated with
     * the {@code key}, but first check to make sure the key is valid.
     * @param jsonObject the JSON Object containing the {@code key} to retrieve
     * @param key the value of the key to retrieve.
     * @return String value associated with {@code key} in {@code jsonObject}
     * @throws InvalidReplyException id no such key exists
     */
    protected static final String getString(JsonObject jsonObject, String key)
            throws InvalidReplyException {
        checkKey(jsonObject, key);
        return jsonObject.getString(key);
    }
    
    /**
     * Get the Integer value in the {@code JsonObject} instance associated with
     * the {@code key}, but first check to make sure the key is valid.
     * @param jsonObject the JSON Object containing the {@code key} to retrieve
     * @param key the value of the key to check
     * @return Integer value associated with {@code key} in {@code jsonObject}
     * @throws InvalidReplyException if no such key exists 
     */
    protected static Integer getInteger(JsonObject jsonObject, String key) 
            throws InvalidReplyException {
        checkKey(jsonObject, key);
        return jsonObject.getInt(key);
    }
    
    /**
     * Get the Long value in the {@code JsonObject} instance associated with
     * the {@code key}, but first check to make sure the key is valid.
     * @param jsonObject the JSON Object containing the {@code key} to retrieve
     * @param key the value of the key to check
     * @return Long value associated with {@code key} in {@code jsonObject}
     * @throws InvalidReplyException if no such key exists 
     */
    protected static Long getLong(JsonObject jsonObject, String key)
            throws InvalidReplyException {
        checkKey(jsonObject, key);
        return Long.parseLong(jsonObject.get(key).toString());
    }
    
    /**
     * Get the Double value in the {@code JsonObject} instance associated with
     * the {@code key}, but first check to make sure the key is valid.
     * @param jsonObject the JSON Object containing the {@code key} to retrieve
     * @param key the value of the key to check
     * @return Double value associated with {@code key} in {@code jsonObject}
     * @throws InvalidReplyException if no such key exists 
     */
    protected static Double getDouble(JsonObject jsonObject, String key)
            throws InvalidReplyException {
        checkKey(jsonObject, key);
        return Double.parseDouble(jsonObject.get(key).toString());
    }
    
    /**
     * Get the Boolean value in the {@code JsonObject} instance associated with
     * the {@code key}, but first check to make sure the key is valid.
     * @param jsonObject the JSON Object containing the {@code key} to retrieve
     * @param key the value of the key to check
     * @return Boolean value associated with {@code key} in {@code jsonObject}
     * @throws InvalidReplyException if no such key exists 
     */
    protected static Boolean getBoolean(JsonObject jsonObject, String key)
            throws InvalidReplyException {
        checkKey(jsonObject, key);
        return jsonObject.getBoolean(key);
    }
}
