package pl.polsl.cargoflow.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pl.polsl.cargoflow.model.City;
import pl.polsl.cargoflow.model.dto.CityResponse;
import pl.polsl.cargoflow.model.dto.CityRequest;
import pl.polsl.cargoflow.repo.CityRepo;

@Service
public class CityService {

    private final CityRepo cityRepo;

    public CityService(CityRepo cityRepo) {
        this.cityRepo = cityRepo;
    }

    public CityResponse getById(Long id) {
        City city = cityRepo.findById(id).orElseThrow(() -> 
            new RuntimeException("City with id " + id + " not found")
        );
        return new CityResponse(city);
    }

    public List<CityResponse> getAll() {
        return cityRepo.findAll()
                .stream()
                .map(CityResponse::new)
                .collect(Collectors.toList());
    }

    public CityResponse save(CityRequest cityrRequest) {
        City city = cityRepo.save(new City(cityrRequest));
        return new CityResponse(city);
    }

    public CityResponse update(Long id, CityRequest cityRequest) {
        return cityRepo.findById(id)
                .map(city -> {
                    city.setName(cityRequest.getName());
                    return cityRepo.save(city);
                })
                .map(CityResponse::new)
                .orElseThrow(() ->
                    new RuntimeException("City with id " + id + " not found")
                );
    }

    public void delete(Long id) {
        cityRepo.deleteById(id);
    }
}
