package pl.polsl.cargoflow.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import pl.polsl.cargoflow.model.dto.CityRequest;
import pl.polsl.cargoflow.model.dto.CityResponse;
import pl.polsl.cargoflow.service.AuthService;
import pl.polsl.cargoflow.service.CityService;

@RestController
@RequestMapping("/cities")
@SecurityRequirement(name = "basicAuth")
public class CityController {

    private final CityService cityService;
    private final AuthService authService;

    public CityController(CityService cityService, AuthService authService) {
        this.cityService = cityService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        authService.authenticate(authHeader);
        CityResponse cityResponse = cityService.getById(id);
        return ResponseEntity.ok(cityResponse);
    }

    @GetMapping
    public ResponseEntity<List<CityResponse>> getAllCities(@RequestHeader("Authorization") String authHeader) {
        authService.authenticate(authHeader);
        List<CityResponse> cityResponses = cityService.getAll();
        return ResponseEntity.ok(cityResponses);
    }

    @PostMapping
    public ResponseEntity<CityResponse> createCity(@RequestHeader("Authorization") String authHeader, @RequestBody CityRequest cityRequest) {
        authService.authenticate(authHeader);
        CityResponse cityResponse = cityService.save(cityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cityResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCity(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody CityRequest cityRequest) {
        authService.authenticate(authHeader);
        CityResponse cityResponse = cityService.update(id, cityRequest);
        return ResponseEntity.ok(cityResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        authService.authenticate(authHeader);
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
