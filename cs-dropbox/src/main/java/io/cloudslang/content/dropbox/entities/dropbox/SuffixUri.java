/*
 * (c) Copyright 2017 Hewlett-Packard Enterprise Development Company, L.P.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0 which accompany this distribution.
 *
 * The Apache License is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 */
package io.cloudslang.content.dropbox.entities.dropbox;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Created by TusaM
 * 5/30/2017.
 */
public enum SuffixUri {
    CREATE_FOLDER("CreateFolder", "/create_folder"),
    CREATE_FOLDER_V2("CreateFolder2", "/create_folder_v2"),
    DELETE_FILE_OR_FOLDER("DeleteFileOrFolder", "/delete"),
    DELETE_FILE_OR_FOLDER_V2("DeleteFileOrFolder2", "/delete_v2");

    private final String key;
    private final String value;

    SuffixUri(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getSuffixUri(String key) {
        for (SuffixUri suffixUri : values()) {
            if (suffixUri.getKey().equalsIgnoreCase(key)) {
                return suffixUri.getValue();
            }
        }

        return EMPTY;
    }

    private String getKey() {
        return key;
    }

    private String getValue() {
        return value;
    }
}