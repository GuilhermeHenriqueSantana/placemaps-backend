package com.esoft.placemaps.helpers;

public class EmailHelper {
    public static Boolean emailValido(String email) {
        if (email.chars().filter(ch -> ch == '@').count() != 1) {
            return false;
        }
        if (email.indexOf("@") < 1) {
            return false;
        }
        if (email.substring(email.indexOf("@")).indexOf(".") < 2) {
            return false;
        }
        return true;
    }
}
