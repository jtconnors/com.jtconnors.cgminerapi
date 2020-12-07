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

public enum Request {
    ADDPOOL,
    ASC,
    ASCCOUNT,
    ASCDISABLE,
    ASCENABLE,
    ASCSET,
    CHECK,
    COIN,
    CONFIG,
    DEBUG,
    DEVDETAILS,
    DEVS,
    DISABLEPOOL,
    EDEVS,
    ENABLEPOOL,
    ESTATS,
    FAILOVER_ONLY,
    HOTPLUG,
    LCD,
    LOCKSTATS,
    NOTIFY,
    PGA,
    PGACOUNT,
    PGADISABLE,
    PGAENABLE,
    PGAIDENTIFY,
    PGASET,
    POOLPRIORITY,
    POOLQUOTA,
    POOLS,
    PRIVILEGED,
    QUIT,
    REMOVEPOOL,
    RESTART,
    SAVE,
    SETCONFIG,
    STATS,
    SWITCHPOOL,
    SUMMARY,
    USBSTATS,
    VERSION,
    ZERO;

    /**
     * Determine if the Request can accept a parameter 
     * @return {@code true} if Request can accept a parameter, {@code false}
     * otherwise
     */
    public boolean includesParameter() {
        switch (this) {
            case ADDPOOL:
                return true;
            case ASC:
                return true;
            case ASCCOUNT:
                return false;
            case ASCDISABLE:
                return true;
            case ASCENABLE:
                return true;
            case ASCSET:
                return true;
            case CHECK:
                return true;
            case COIN:
                return false;
            case CONFIG:
                return false;
            case DEBUG:
                return true;
            case DEVDETAILS:
                return false;
            case DEVS:
                return false;
            case DISABLEPOOL:
                return true;
            case EDEVS:
                return true;
            case ENABLEPOOL:
                return true;
            case ESTATS:
                return true;
            case FAILOVER_ONLY:
                return true;
            case HOTPLUG:
                return true;
            case LCD:
                return false;
            case LOCKSTATS:
                return false;
            case NOTIFY:
                return false;
            case PGA:
                return true;
            case PGACOUNT:
                return false;
            case PGADISABLE:
                return true;
            case PGAENABLE:
                return true;
            case PGAIDENTIFY:
                return true;
            case PGASET:
                return true;
            case POOLPRIORITY:
                return true;
            case POOLQUOTA:
                return true;
            case POOLS:
                return false;
            case PRIVILEGED:
                return false;
            case QUIT:
                return false;
            case REMOVEPOOL:
                return true;
            case RESTART:
                return false;
            case SAVE:
                return true;
            case SETCONFIG:
                return true;
            case STATS:
                return false;
            case SWITCHPOOL:
                return true;
            case SUMMARY:
                return false;
            case USBSTATS:
                return false;
            case VERSION:
                return false;
            case ZERO:
                return true;
            default:
                return false;
        }
    }
    
    /**
     * Convert string representation of a request to its corresponding 
     * {@code Request} enum value.  Requests are in lowercase, whereas the
     * enum constants are in UPPERCASE.  Additionally there are a few
     * exceptions (e.g. {"failover-only" = FALIOVER_ONLY}) which need handling
     * @param requestStr string representation of command.
     * @return enum value of the {@code requestStr} if valid, otherwise
     * {@code null}.
     */
    public static Request toRequest(String requestStr) {
        if (requestStr.equals("failover-only")) {
            return FAILOVER_ONLY;
        } else {
            Request retVal = null;
            try {
              retVal = valueOf(requestStr.toUpperCase());
            } catch (IllegalArgumentException e) {/* catch and do nothing */}
            return retVal;
        }
    }

    /**
     * Convert the Request into a valid command string. Request enum constants
     * are represented in UPPERCASE, whereas a valid command string must be in
     * lowercase. Additionally there may be a few exceptions which need to be
     * handled e.g. {@code FALIOVER_ONLY} = "failover-only"
     * @return valid string representation of Request instance
     */
    public String toRequestString() {
        if (this == FAILOVER_ONLY) {
            return "failover-only";
        } else {
            return this.toString().toLowerCase();
        }
    }
}
