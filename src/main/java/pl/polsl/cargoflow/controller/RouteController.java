package pl.polsl.cargoflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.cargoflow.model.dto.CreateRoute;
import pl.polsl.cargoflow.model.dto.RouteResponse;
import pl.polsl.cargoflow.service.RouteService;
import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteResponse> getRouteById(@PathVariable Long id) {
        RouteResponse routeResponse = routeService.getById(id);
        return ResponseEntity.ok(routeResponse);
    }

    @GetMapping
    public ResponseEntity<List<RouteResponse>> getAllRoutes() {
        List<RouteResponse> routeResponses = routeService.getAll();
        return ResponseEntity.ok(routeResponses);
    }

    @PostMapping
    public ResponseEntity<RouteResponse> createRoute(@RequestBody CreateRoute createRoute) {
        RouteResponse routeResponse = routeService.save(createRoute);
        return ResponseEntity.status(HttpStatus.CREATED).body(routeResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteResponse> updateRoute(@PathVariable Long id, @RequestBody CreateRoute createRoute) {
        RouteResponse routeResponse = routeService.update(id, createRoute);
        return ResponseEntity.ok(routeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
