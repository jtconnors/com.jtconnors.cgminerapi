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

import java.time.Instant;
import java.time.ZoneId;
import javax.json.JsonObject;

public class ReplyStatus extends Reply {

    private final String status;
    private final Long when;
    private final Integer code;
    private final String msg;
    private final String description;

    private String getStatusStr(String statusLetter) {
        switch (statusLetter) {
            case "E":
                return ("Error");
            case "F":
                return ("Fatal");
            case "I":
                return ("Informational");
            case "S":
                return ("Success");
            case "W":
                return ("Warning");
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(createLabelEqualsValueStr(STATUS, getStatusStr(getStatus())));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(WHEN,
                Instant.ofEpochSecond(getWhen())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime().toString()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(CODE, getCode()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(MSG, getMsg()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(DESCRIPTION, getDescription()));
        return sb.toString();
    }

    public ReplyStatus(JsonObject jsonReplyStatus)
            throws InvalidReplyException {
        status = getString(jsonReplyStatus, STATUS);
        when = getLong(jsonReplyStatus, WHEN);
        code = getInteger(jsonReplyStatus, CODE);
        msg = getString(jsonReplyStatus, MSG);
        description = getString(jsonReplyStatus, DESCRIPTION);
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the when
     */
    public Long getWhen() {
        return when;
    }

    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

}
