package com.utkubayrak.jetbusservice.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class ExceptionMessages{

    public static final String CITY_NOT_FOUND = "Şehir bulunamadı";
    public static final String AIRPORT_NOT_FOUND = "Havaalanı bulunamadı";
    public static final String FLIGHT_NOT_FOUND = "Uçuş bulunamadı";
    public static final String DEPARTURE_ARRIVAL_TIME_NOT_SET = "Kalkış ve varış saatleri hesaplanmalıdır.";
    public static final String TICKET_LIMIT_EXCEEDED = "Fazla bilet alamazsınız!";

}
