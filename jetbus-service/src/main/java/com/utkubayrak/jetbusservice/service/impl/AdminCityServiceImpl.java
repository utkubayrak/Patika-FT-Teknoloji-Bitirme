package com.utkubayrak.jetbusservice.service.impl;

import com.utkubayrak.jetbusservice.dto.request.CityRequest;
import com.utkubayrak.jetbusservice.exception.ExceptionMessages;
import com.utkubayrak.jetbusservice.exception.JetbusException;
import com.utkubayrak.jetbusservice.model.City;
import com.utkubayrak.jetbusservice.repository.CityRepository;
import com.utkubayrak.jetbusservice.service.IAdminCityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class AdminCityServiceImpl implements IAdminCityService {
    private final CityRepository cityRepository;
    private static final Logger logger = LoggerFactory.getLogger(AdminCityServiceImpl.class);

    @Override
    public City createCity(CityRequest cityRequest) {
        logger.info("Creating city with name: {}", cityRequest.getCityName());
        City city = new City();
        city.setCityName(cityRequest.getCityName());
        city.setCountryName(cityRequest.getCountryName());
        cityRepository.save(city);
        logger.info("City created successfully: {}", city);
        return city;
    }

    @Override
    public void deleteCity(Long cityId) {
        logger.info("Deleting city with ID: {}", cityId);
        Optional<City> cityOpt = cityRepository.findById(cityId);
        if (cityOpt.isEmpty()) {
            logger.error("City not found with ID: {}", cityId);
            throw new JetbusException(ExceptionMessages.CITY_NOT_FOUND);
        }
        cityRepository.deleteById(cityId);
        logger.info("City deleted successfully with ID: {}", cityId);
    }

    @Override
    public City updateCity(Long cityId, CityRequest cityRequest) {
        logger.info("Updating city with ID: {}", cityId);
        Optional<City> cityOpt = cityRepository.findById(cityId);
        if (cityOpt.isEmpty()) {
            logger.error("City not found with ID: {}", cityId);
            throw new JetbusException(ExceptionMessages.CITY_NOT_FOUND);
        }
        City city = cityOpt.get();
        city.setCityName(cityRequest.getCityName());
        city.setCountryName(cityRequest.getCountryName());
        cityRepository.save(city);
        logger.info("City updated successfully: {}", city);
        return city;
    }



}
