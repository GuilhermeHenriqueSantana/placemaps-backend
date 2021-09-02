package com.esoft.placemaps.helpers;

public class SenhaHelper {
    public static Boolean senhaSegura(String senha) {
        if (senha.length() < 6) return false;

        Boolean achouNumero = false;
        Boolean achouMaiuscula = false;
        Boolean achouMinuscula = false;
        Boolean achouSimbolo = false;

        for (char c : senha.toCharArray()) {
            if (c >= '0' && c <= '9') {
                achouNumero = true;
            } else if (c >= 'A' && c <= 'Z') {
                achouMaiuscula = true;
            } else if (c >= 'a' && c <= 'z') {
                achouMinuscula = true;
            } else {
                achouSimbolo = true;
            }
        }
        return achouNumero && achouMaiuscula && achouMinuscula && achouSimbolo;
    }
}
