package com.utkubayrak.jetbususerservice.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {
    public static final String USER_NOT_FOUND = "Kullanıcı bulunamadı.";
    public static final String USER_NOT_FOUND_OR_PASSWORD_IS_WRONG = "Email veya şifre yanlış.";
    public static final String USER_ALREADY_DEFINED = "Bu e-mail kullanılmaktadır.";
    public static final String USER_EMAIL_CAN_NOT_BE_EMPTY = "Email alanı boş olamaz.";
    public static final String ROLE_NOT_FOUND = "role bulunamadı";
    public static final String ACCOUNT_ALREADY_VERIFIED="Hesap zaten onaylanmış.";
    public static final String USER_NOT_VERIFIED="Kullanıcı onaylanmamış.";
    public static final String VERIFICATION_CODE_EXPIRED="Doğrulama kodu geçersiz. Lütfen yeni kod isteyin.";
    public static final String INVALID_VERIFICATION_CODE="Doğrulama kodu yanlış.";
    public static final String RESET_CODE_EXPIRED="Parola sıfırlama kodu geçersiz lütfen yeni kod isteyin.";
    public static final String INVALID_RESET_CODE="Parola sıfırlama kodu yanlış.";

}
