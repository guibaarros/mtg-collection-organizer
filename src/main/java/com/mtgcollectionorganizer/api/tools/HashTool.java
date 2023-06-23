package com.mtgcollectionorganizer.api.tools;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public final class HashTool {
    public static String hashSHA256(final String string) {
        return Hashing.sha256()
                .hashString(string, StandardCharsets.UTF_8)
                .toString();
    }
}
