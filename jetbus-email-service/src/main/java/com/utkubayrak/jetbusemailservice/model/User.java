package com.utkubayrak.jetbusemailservice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class User implements Serializable {
    private String email;
    private String verificationCode;
    private String resetCode;
}
