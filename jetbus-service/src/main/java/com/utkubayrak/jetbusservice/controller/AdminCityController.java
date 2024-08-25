package com.utkubayrak.jetbusservice.controller;

import com.utkubayrak.jetbusservice.dto.request.CityRequest;
import com.utkubayrak.jetbusservice.model.City;
import com.utkubayrak.jetbusservice.service.IAdminCityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/city")
public class AdminCityController {
    private final IAdminCityService adminCityService;

    @PostMapping("/create-city")
    public ResponseEntity<City> createCity(@RequestBody CityRequest cityRequest){
        City city = adminCityService.createCity(cityRequest);
        return ResponseEntity.ok(city);
    }
    @PutMapping("/update-city/{cityId}")
    public ResponseEntity<City> updateCity(@PathVariable Long cityId, @RequestBody CityRequest cityRequest){
        City city = adminCityService.updateCity(cityId,cityRequest);
        return ResponseEntity.ok(city);
    }
    @DeleteMapping("/delete-city/{cityId}")
    public ResponseEntity<?> deleteCity(@PathVariable Long cityId){
        adminCityService.deleteCity(cityId);
        return ResponseEntity.ok("Başarıyla Silindi");
    }



}
