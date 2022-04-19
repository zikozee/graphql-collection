package com.zikozee.graphql.util;

import org.bouncycastle.crypto.generators.BCrypt;
import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

import java.nio.charset.StandardCharsets;

/**
 * @author: Ezekiel Eromosei
 * @created: 19 April 2022
 */

public class HashUtil {

    private static final String BCRYPT_SALT = "dontDoThisOnProd";

    public static boolean isBcryptMatch(String original, String hashValue){
        return OpenBSDBCrypt.checkPassword(hashValue, original.getBytes(StandardCharsets.UTF_8));
    }

    public static String hashBcrypt(String original){
        return OpenBSDBCrypt.generate(original.getBytes(StandardCharsets.UTF_8),
                BCRYPT_SALT.getBytes(StandardCharsets.UTF_8), 5);
    }
}
