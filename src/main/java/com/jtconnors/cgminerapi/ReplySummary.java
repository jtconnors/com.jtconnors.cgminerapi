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

import java.util.concurrent.TimeUnit;
import javax.json.JsonObject;

public class ReplySummary extends Reply {

    private final Long elapsed;
    private final Double mhsAv;
    private final Double mhs5s;
    private final Double mhs1m;
    private final Double mhs5m;
    private final Double mhs15m;
    private final Integer foundBlocks;
    private final Integer getWorks;
    private final Integer accepted;
    private final Integer rejected;
    private final Integer hardwareErrors;
    private final Double utility;
    private final Integer discarded;
    private final Integer stale;
    private final Integer getFailures;
    private final Integer localWork;
    private final Integer remoteFailures;
    private final Integer networkBlocks;
    private final Double totalMH;
    private final Double workUtility;
    private final Double difficultyAccepted;
    private final Double difficultyRejected;
    private final Double difficultyStale;
    private final Integer bestShare;
    private final Double deviceHardwarePercent;
    private final Double deviceRejectedPercent;
    private final Double poolRejectedPercent;
    private final Double poolStalePercent;
    private final Long lastGetwork;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(createLabelEqualsValueStr(ELAPSED, getElapsed()));
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
        sb.append(createLabelEqualsValueStr(FOUND_BLOCKS, getFoundBlocks()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(GETWORKS, getGetWorks()));
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
        sb.append(createLabelEqualsValueStr(DISCARDED, getDiscarded()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(STALE, getStale()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(GET_FAILURES, getGetFailures()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(LOCAL_WORK, getLocalWork()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(REMOTE_FAILURES,
            getRemoteFailures()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(NETWORK_BLOCKS,
            getNetworkBlocks()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(TOTAL_MH, getTotalMH()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(WORK_UTILITY, getWorkUtility()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(DIFFICULTY_ACCEPTED,
            getDifficultyAccepted()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(DIFFICULTY_REJECTED,
            getDifficultyRejected()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(DIFFICULTY_STALE,
            getDifficultyStale()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(BESTSHARE, getBestShare()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(DEVICE_HARDWARE_PERCENT,
            getDeviceHardwarePercent()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(DEVICE_REJECTED_PERCENT,
            getDeviceRejectedPercent()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(POOL_REJECTED_PERCENT,
            getPoolRejectedPercent()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(POOL_STALE_PERCENT,
            getPoolStalePercent()));
        sb.append(", ");
        sb.append(createLabelEqualsValueStr(LAST_GETWORK, getLastGetwork()));
        return sb.toString();
    }

    public ReplySummary(JsonObject jsonReplyStatus)
            throws InvalidReplyException {
        elapsed = getLong(jsonReplyStatus, ELAPSED);
        mhsAv = getDouble(jsonReplyStatus, MHS_AV);
        mhs5s = getDouble(jsonReplyStatus, MHS_5S);
        mhs1m = getDouble(jsonReplyStatus, MHS_1M);
        mhs5m = getDouble(jsonReplyStatus, MHS_5M);
        mhs15m = getDouble(jsonReplyStatus, MHS_15M);
        foundBlocks = getInteger(jsonReplyStatus, FOUND_BLOCKS);
        getWorks = getInteger(jsonReplyStatus, GETWORKS);
        accepted = getInteger(jsonReplyStatus, ACCEPTED);
        rejected = getInteger(jsonReplyStatus, REJECTED);
        hardwareErrors = getInteger(jsonReplyStatus, HARDWARE_ERRORS);
        utility = getDouble(jsonReplyStatus, UTILITY);
        discarded = getInteger(jsonReplyStatus, DISCARDED);
        stale = getInteger(jsonReplyStatus, STALE);
        getFailures = getInteger(jsonReplyStatus, GET_FAILURES);
        localWork = getInteger(jsonReplyStatus, LOCAL_WORK);
        remoteFailures = getInteger(jsonReplyStatus, REMOTE_FAILURES);
        networkBlocks = getInteger(jsonReplyStatus, NETWORK_BLOCKS);
        totalMH = getDouble(jsonReplyStatus, TOTAL_MH);
        workUtility = getDouble(jsonReplyStatus, WORK_UTILITY);
        difficultyAccepted = getDouble(jsonReplyStatus, DIFFICULTY_ACCEPTED);
        difficultyRejected = getDouble(jsonReplyStatus, DIFFICULTY_REJECTED);
        difficultyStale = getDouble(jsonReplyStatus, DIFFICULTY_STALE);
        bestShare = getInteger(jsonReplyStatus, BESTSHARE);
        deviceHardwarePercent = getDouble(jsonReplyStatus,
            DEVICE_HARDWARE_PERCENT);
        deviceRejectedPercent = getDouble(jsonReplyStatus,
            DEVICE_REJECTED_PERCENT);
        poolRejectedPercent = getDouble(jsonReplyStatus, POOL_REJECTED_PERCENT);
        poolStalePercent = getDouble(jsonReplyStatus, POOL_STALE_PERCENT);
        lastGetwork = getLong(jsonReplyStatus, LAST_GETWORK);
    }
    
    /**
     * Returns an elapsed time string with the format "Days HH:MM:SS"
     * @param l elapsed time in milliseconds
     * @return string with the format "Days HH:MM:SS"
     */
    public static String FormatInterval(final long l)
    {
        long day = TimeUnit.MILLISECONDS.toDays(l);
        long hr = TimeUnit.MILLISECONDS.toHours(
                l - TimeUnit.DAYS.toMillis(day));
        long min = TimeUnit.MILLISECONDS.toMinutes(
                l - TimeUnit.DAYS.toMillis(day) -
                        TimeUnit.HOURS.toMillis(hr));
        long sec = TimeUnit.MILLISECONDS.toSeconds(
                l - TimeUnit.DAYS.toMillis(day) -
                        TimeUnit.HOURS.toMillis(hr) -
                        TimeUnit.MINUTES.toMillis(min));
        return String.format("%4d %02d:%02d:%02d", day, hr, min, sec);
    }

    /**
     * @return the elapsed
     */
    public Long getElapsed() {
        return elapsed;
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
     * @return the foundBlocks
     */
    public Integer getFoundBlocks() {
        return foundBlocks;
    }

    /**
     * @return the getWorks
     */
    public Integer getGetWorks() {
        return getWorks;
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
     * @return the discarded
     */
    public Integer getDiscarded() {
        return discarded;
    }

    /**
     * @return the stale
     */
    public Integer getStale() {
        return stale;
    }

    /**
     * @return the getFailures
     */
    public Integer getGetFailures() {
        return getFailures;
    }

    /**
     * @return the localWork
     */
    public Integer getLocalWork() {
        return localWork;
    }

    /**
     * @return the remoteFailures
     */
    public Integer getRemoteFailures() {
        return remoteFailures;
    }

    /**
     * @return the networkBlocks
     */
    public Integer getNetworkBlocks() {
        return networkBlocks;
    }

    /**
     * @return the totalMH
     */
    public Double getTotalMH() {
        return totalMH;
    }

    /**
     * @return the workUtility
     */
    public Double getWorkUtility() {
        return workUtility;
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
     * @return the difficultyStale
     */
    public Double getDifficultyStale() {
        return difficultyStale;
    }

    /**
     * @return the bestShare
     */
    public Integer getBestShare() {
        return bestShare;
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
     * @return the poolRejectedPercent
     */
    public Double getPoolRejectedPercent() {
        return poolRejectedPercent;
    }

    /**
     * @return the poolStalePercent
     */
    public Double getPoolStalePercent() {
        return poolStalePercent;
    }

    /**
     * @return the lastGetwork
     */
    public Long getLastGetwork() {
        return lastGetwork;
    }
}
