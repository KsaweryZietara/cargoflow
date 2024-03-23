package pl.polsl.cargoflow.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pl.polsl.cargoflow.model.City;
import pl.polsl.cargoflow.model.Route;
import pl.polsl.cargoflow.model.dto.RouteResponse;
import pl.polsl.cargoflow.model.dto.CreateRoute;
import pl.polsl.cargoflow.repo.CityRepo;
import pl.polsl.cargoflow.repo.RouteRepo;

@Service
public class RouteService {

    private final RouteRepo routeRepo;
    private final CityRepo cityRepo;

    public RouteService(RouteRepo routeRepo, CityRepo cityRepo) {
        this.routeRepo = routeRepo;
        this.cityRepo = cityRepo;
    }

    public RouteResponse getById(Long id) {
        Route route = routeRepo.findById(id).orElseThrow(() -> 
            new RuntimeException("Route with id " + id + " not found")
        );
        return new RouteResponse(route);
    }

    public List<RouteResponse> getAll() {
        return routeRepo.findAll()
                .stream()
                .map(RouteResponse::new)
                .collect(Collectors.toList());
    }

    public RouteResponse save(CreateRoute createRoute) {
        City departureCity = cityRepo.findById(createRoute.getDepartureCityId()).orElseThrow(() -> 
            new RuntimeException("City with id " + createRoute.getDepartureCityId() + " not found")
        );
        City arrivalCity = cityRepo.findById(createRoute.getArrivalCityId()).orElseThrow(() -> 
            new RuntimeException("City with id " + createRoute.getArrivalCityId() + " not found")
        );
        Route route = routeRepo.save(new Route(createRoute, departureCity, arrivalCity));
        return new RouteResponse(route);
    }

    public RouteResponse update(Long id, CreateRoute createRoute) {
        City departureCity = cityRepo.findById(createRoute.getDepartureCityId()).orElseThrow(() -> 
            new RuntimeException("City with id " + createRoute.getDepartureCityId() + " not found")
        );
        City arrivalCity = cityRepo.findById(createRoute.getArrivalCityId()).orElseThrow(() -> 
            new RuntimeException("City with id " + createRoute.getArrivalCityId() + " not found")
        );
        return routeRepo.findById(id)
                .map(route -> {
                    route.setDistance(createRoute.getDistance());
                    route.setDepartureCity(departureCity);
                    route.setArrivalCity(arrivalCity);
                    return routeRepo.save(route);
                })
                .map(RouteResponse::new)
                .orElseThrow(() ->
                    new RuntimeException("Route with id " + id + " not found")
                );
    }

    public void delete(Long id) {
        routeRepo.deleteById(id);
    }
}
