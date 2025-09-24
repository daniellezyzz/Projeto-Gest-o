package app.models;

import java.util.regex.Pattern;

public class Validador {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    // Valida se o email segue o padrão básico
    public static boolean validarEmail(String email) {
        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }

    // Valida CPF brasileiro
    public static boolean validarCPF(String cpf) {
        if (cpf == null) return false;

        cpf = cpf.replaceAll("\\D", ""); // Remove caracteres não numéricos

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        try {
            // Primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int digito1 = (soma * 10) % 11;
            if (digito1 == 10) digito1 = 0;
            if (digito1 != Character.getNumericValue(cpf.charAt(9))) return false;

            // Segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int digito2 = (soma * 10) % 11;
            if (digito2 == 10) digito2 = 0;

            return digito2 == Character.getNumericValue(cpf.charAt(10));
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
