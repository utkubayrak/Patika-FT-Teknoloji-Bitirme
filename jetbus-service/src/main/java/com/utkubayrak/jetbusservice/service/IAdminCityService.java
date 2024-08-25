package com.utkubayrak.jetbusservice.service;


import com.utkubayrak.jetbusservice.dto.request.CityRequest;
import com.utkubayrak.jetbusservice.model.City;

import java.util.List;

public interface IAdminCityService {

    public City createCity(CityRequest cityRequest);
    public void deleteCity(Long cityId);
    public City updateCity(Long cityId, CityRequest cityRequest);
}
