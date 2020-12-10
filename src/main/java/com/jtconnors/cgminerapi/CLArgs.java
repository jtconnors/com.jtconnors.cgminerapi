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
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * This class will facilitate enabling program options within the cgminerapi
 * package that can be set in a properties file and optionally overriden by
 * command-line.
 * <br>
 * For an example of a class that extends {@code CLArgs}, see {@link CgArgs}.
 * Furthermore, to see how an implementation of {@code CgArgs}
 * is used in a main program see {@link Samples}.
 */
public abstract class CLArgs {

    public static final String HELP = "help";
    public static final String DEBUGLOG = "debugLog";

    public static final String DASH = "-";
    public static final String DASH_HELP = "-" + HELP;
    public static final String DASH_DEBUGLOG = "-" + DEBUGLOG;

    public static Map<String, String> helpStrMap;

    /*
     * Associate a printable help string with each Command-line option
     */
    static {
        helpStrMap = new HashMap<>();
        helpStrMap.put(DASH_HELP,
            "  -help\n" +
            "\tPrint this screen for command-line argument options and exit");
        helpStrMap.put(DASH_DEBUGLOG,
            "  -debugLog:{true|false} (default false)\n" +
            "\tEnable|Disable debug logging");
    }

    private Properties properties;
    private String progName;
    private Set<String> clArgsSet;

    /**
     * Initializes a newly created {@code CLArgs} instance.  {@code CLArgs}
     * instances include a {@code Properties} object where individual properties
     * are stored in the following format: {@code progName.key=value}.
     * Initial propery values are read from a resource file that is bundled
     * relative to this application's project or jar file.
     * 
     * @param clazz {@code Class} instance of caller of this constructor
     * @param resourceName the name of the {@code properties} resource
     * @param progName Progam name used by {@code properties} to store and
     * retrieve key-value pairs.
     */
    public CLArgs(Class<?> clazz, String resourceName, String progName) {
        this.progName = progName;
        this.clArgsSet = new HashSet<>();
        clArgsSet.add(DASH_HELP);
        clArgsSet.add(DASH_DEBUGLOG);
        properties = new Properties();
        InputStream inputStream = clazz.getResourceAsStream(resourceName);
        if (inputStream != null) {
            try {
                properties.load(clazz.getResourceAsStream(resourceName));
            } catch (IOException e)  {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the value in the {@code CLArgs} instance associated with the 
     * property named {@code key}
     * 
     * @param key the hashtable key 
     * @return the value in the properties list with the specified key value
     */
    public String getProperty(String key) {
        return properties.getProperty(progName + "." + key);
    }

    /**
     * Get the value in the {@code CLArgs} instance associated with the 
     * property named {@code key}.  If it doesn't exist, return
     * the value specified by the {@code defaultValue} argument
     * 
     * @param key the hashtable key
     * @param defaultValue a default value
     * @return the value associated with {@code key}
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(progName + "." + key, defaultValue);
    }

    /**
     * Add the specified argument to the set of allowable command-line arguments
     * for this {@code CLargs} instance and check to see if it contains a
     * property with the specified {@code key} parameter.  If not, add a new
     * property, specified by the {@code key} parameter, with a default value
     * specified by the {@code value} parameter
     * 
     * @param key the hashtable key
     * @param value the default value associated with the key
     */
    public void addAllowableArg(String key, String value) {
        if (getProperty(key) == null) {
            setProperty(key, value);
        }
        clArgsSet.add(DASH + key);
    }

    /**
     * Puts the key-value pair represented by the {@code key} and {@code value}
     * arguments into properties associated with this {@code CLArgs}
     * instance
     * 
     * @param key the hashtable key
     * @param value the value corresponding to key
     */
    public void setProperty(String key, String value) {
        properties.setProperty(progName + "." + key, value);
    }

    /**
     * Check if an argument appears on the list of command-line args
     *
     * @param arg the argument in question
     * @param args the list of command line arguments
     * @return {@code true} if argument appears on the command-line, otherwise
     * {@code false}
     */
    public static boolean isOnCmdLine(String arg, String[] args) {
        for (String cmdLineArg : args) {          
            String[] subarg = cmdLineArg.split(":");
            if (subarg[0].equals(arg)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Return the value associated with an argument contained within a list of
     * command-line arguments ({@code args}).  Command-line arguments for this
     * method are expected to be of the form "-argument:value".  If no such
     * argument exists in the {@code args} list matching this form,
     * return {@code null}, otherwise return the associated value.
     *
     * @param arg the argument in question
     * @param args the list of command line arguments
     * @return the value associated with an argument on the {@code args} list,
     * or {@code null} if no such argument exists in the list
     */
    public static String getArgValue(String arg, String[] args) {
        for (String cmdLineArg : args) {          
            String[] subarg = cmdLineArg.split(":");
            if (subarg[0].equals(arg) && subarg.length > 1) {
                return subarg[1];
            }
        }
        return null;
    }

    /**
     * Process the Command-line arguments and set {@code CLArgs} instance
     * properties accordingly
     * @param args the list cf command-line arguments
     */
    public void parseArgs(String[] args) {
        if (isOnCmdLine(DASH_HELP, args)) {
            System.out.println("Command-line options:");
            for (String option : clArgsSet) {
                System.out.println(helpStrMap.get(option));
            }
            System.exit(0);
        }
        if (isOnCmdLine(CLArgs.DASH_DEBUGLOG, args)) {
            setProperty(DEBUGLOG, getArgValue(DASH_DEBUGLOG, args));    
        }
    }    
}