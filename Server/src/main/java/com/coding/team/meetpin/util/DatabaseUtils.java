package com.coding.team.meetpin.util;

/**
 * Description of class:
 * <p>
 * Created on 20 Jun 2018 (01:10)
 *
 * @author dawid
 */
public class DatabaseUtils {
    public static String getUsernameFromEmail(String email) {
        return email.split("@")[0];
    }
}