package pl.polsl.cargoflow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.cargoflow.model.dto.PositionRequest;
import pl.polsl.cargoflow.model.dto.PositionResponse;
import pl.polsl.cargoflow.service.PositionService;
import java.util.List;

@RestController
@RequestMapping("/positions")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionResponse> getPositionById(@PathVariable Long id) {
        PositionResponse positionResponse = positionService.getById(id);
        return ResponseEntity.ok(positionResponse);
    }

    @GetMapping
    public ResponseEntity<List<PositionResponse>> getAllPositions() {
        List<PositionResponse> positionResponses = positionService.getAll();
        return ResponseEntity.ok(positionResponses);
    }

    @PostMapping
    public ResponseEntity<PositionResponse> createPosition(@RequestBody PositionRequest positionRequest) {
        PositionResponse positionResponse = positionService.save(positionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(positionResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PositionResponse> updatePosition(@PathVariable Long id, @RequestBody PositionRequest positionRequest) {
        PositionResponse positionResponse = positionService.update(id, positionRequest);
        return ResponseEntity.ok(positionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        positionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
