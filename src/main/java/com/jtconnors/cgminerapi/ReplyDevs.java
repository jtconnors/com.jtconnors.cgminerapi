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

public class ReplyDevs extends Reply {

    private final Integer asc;
    private final String name;
    private final Integer id;
    private final String enabled;
    private final String status;
    private final Double temperature;
    private final Double mhsAv;
    private final Double mhs5s;
    private final Double mhs1m;
    private final Double mhs5m;
    private final Double mhs15m;
    private final Integer accepted;
    private final Integer rejected;
    private final Integer hardwareErrors;
    private final Double utility;
    private final Integer lastSharePool;
    private final Long lastShareTime;
    private final Double totalMh;
    private final Integer diff1Work;
    private final Double difficultyAccepted;
    private final Double difficultyRejected;
    private final Double lastShareDifficulty;
    private final Boolean noDevice;
    private final Long lastValidWork;
    private final Double deviceHardwarePercent;
    private final Double deviceRejectedPercent;
    private final Long deviceElapsed;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(createLabelEqualsValueStr(ASC, getAsc()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(NAME, getName()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(ID, getId()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(ENABLED, getEnabled()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(STATUS_DEVS, getStatus()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(TEMPERATURE, getTemperature()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(MHS_AV, getMhsAv()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(MHS_5S, getMhs5s()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(MHS_1M, getMhs1m()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(MHS_5M, getMhs5m()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(MHS_15M, getMhs15m()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(ACCEPTED, getAccepted()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(REJECTED, getRejected()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(HARDWARE_ERRORS, 
            getHardwareErrors()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(UTILITY, getUtility()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(LAST_SHARE_POOL,
            getLastSharePool()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(LAST_SHARE_TIME,
            getLastShareTime()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(DIFF1_WORK, getDiff1Work()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(DIFFICULTY_ACCEPTED,
            getDifficultyAccepted()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(DIFFICULTY_REJECTED,
            getDifficultyRejected()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(LAST_SHARE_DIFFICULTY,
            getLastShareDifficulty()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(NO_DEVICE, getNoDevice()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(DEVICE_HARDWARE_PERCENT,
            getDeviceHardwarePercent()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(DEVICE_REJECTED_PERCENT,
            getDeviceRejectedPercent()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(DEVICE_ELAPSED,
            getDeviceElapsed()));
        return sb.toString();
    }

    public ReplyDevs(JsonObject jsonReplyStatus)
            throws InvalidReplyException {
        asc = getInteger(jsonReplyStatus, ASC);
        name = getString(jsonReplyStatus, NAME);
        id = getInteger(jsonReplyStatus, ID);
        enabled = getString(jsonReplyStatus, ENABLED);
        status = getString(jsonReplyStatus, STATUS_DEVS);
        temperature = getDouble(jsonReplyStatus, TEMPERATURE);
        mhsAv = getDouble(jsonReplyStatus, MHS_AV);
        mhs5s = getDouble(jsonReplyStatus, MHS_5S);
        mhs1m = getDouble(jsonReplyStatus, MHS_1M);
        mhs5m = getDouble(jsonReplyStatus, MHS_5M);
        mhs15m = getDouble(jsonReplyStatus, MHS_15M);
        accepted = getInteger(jsonReplyStatus, ACCEPTED);
        rejected = getInteger(jsonReplyStatus, REJECTED);
        hardwareErrors = getInteger(jsonReplyStatus, HARDWARE_ERRORS);
        utility = getDouble(jsonReplyStatus, UTILITY);
        lastSharePool = getInteger(jsonReplyStatus, LAST_SHARE_POOL);
        lastShareTime = getLong(jsonReplyStatus, LAST_SHARE_TIME);
        totalMh = getDouble(jsonReplyStatus, TOTAL_MH);
        diff1Work = getInteger(jsonReplyStatus, DIFF1_WORK);
        difficultyAccepted = getDouble(jsonReplyStatus, DIFFICULTY_ACCEPTED);
        difficultyRejected = getDouble(jsonReplyStatus, DIFFICULTY_REJECTED);
        lastShareDifficulty = getDouble(jsonReplyStatus,
            LAST_SHARE_DIFFICULTY);
        noDevice = getBoolean(jsonReplyStatus, NO_DEVICE);
        lastValidWork = getLong(jsonReplyStatus, LAST_VALID_WORK);
        deviceHardwarePercent = getDouble(jsonReplyStatus,
            DEVICE_HARDWARE_PERCENT);
        deviceRejectedPercent = getDouble(jsonReplyStatus,
            DEVICE_REJECTED_PERCENT);
        deviceElapsed = getLong(jsonReplyStatus, DEVICE_ELAPSED);
    }

    /**
     * @return the asc
     */
    public Integer getAsc() {
        return asc;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the enabled
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the temperature
     */
    public Double getTemperature() {
        return temperature;
    }

    /**
     * @return the mhsAv
     */
    public Double getMhsAv() {
        return mhsAv;
    }

    /**
     * @return the mhs5s
     */
    public Double getMhs5s() {
        return mhs5s;
    }

    /**
     * @return the mhs1m
     */
    public Double getMhs1m() {
        return mhs1m;
    }

    /**
     * @return the mhs5m
     */
    public Double getMhs5m() {
        return mhs5m;
    }

    /**
     * @return the mhs15m
     */
    public Double getMhs15m() {
        return mhs15m;
    }

    /**
     * @return the accepted
     */
    public Integer getAccepted() {
        return accepted;
    }

    /**
     * @return the rejected
     */
    public Integer getRejected() {
        return rejected;
    }

    /**
     * @return the hardwareErrors
     */
    public Integer getHardwareErrors() {
        return hardwareErrors;
    }

    /**
     * @return the utility
     */
    public Double getUtility() {
        return utility;
    }

    /**
     * @return the lastSharePool
     */
    public Integer getLastSharePool() {
        return lastSharePool;
    }

    /**
     * @return the lastShareTime
     */
    public Long getLastShareTime() {
        return lastShareTime;
    }

    /**
     * @return the totalMh
     */
    public Double getTotalMh() {
        return totalMh;
    }

    /**
     * @return the diff1Work
     */
    public Integer getDiff1Work() {
        return diff1Work;
    }

    /**
     * @return the difficultyAccepted
     */
    public Double getDifficultyAccepted() {
        return difficultyAccepted;
    }

    /**
     * @return the difficultyRejected
     */
    public Double getDifficultyRejected() {
        return difficultyRejected;
    }

    /**
     * @return the lastShareDifficulty
     */
    public Double getLastShareDifficulty() {
        return lastShareDifficulty;
    }

    /**
     * @return the noDevice
     */
    public Boolean getNoDevice() {
        return noDevice;
    }

    /**
     * @return the lastValidWork
     */
    public Long getLastValidWork() {
        return lastValidWork;
    }

    /**
     * @return the deviceHardwarePercent
     */
    public Double getDeviceHardwarePercent() {
        return deviceHardwarePercent;
    }

    /**
     * @return the deviceRejectedPercent
     */
    public Double getDeviceRejectedPercent() {
        return deviceRejectedPercent;
    }

    /**
     * @return the deviceElapsed
     */
    public Long getDeviceElapsed() {
        return deviceElapsed;
    }
}
