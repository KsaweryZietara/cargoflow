package pl.polsl.cargoflow.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pl.polsl.cargoflow.model.Position;
import pl.polsl.cargoflow.model.dto.PositionResponse;
import pl.polsl.cargoflow.model.dto.PositionRequest;
import pl.polsl.cargoflow.repo.PositionRepo;

@Service
public class PositionService {

    private final PositionRepo positionRepo;

    public PositionService(PositionRepo positionRepo) {
        this.positionRepo = positionRepo;
    }

    public PositionResponse getById(Long id) {
        Position position = positionRepo.findById(id).orElseThrow(() -> 
            new RuntimeException("Position with id " + id + " not found")
        );
        return new PositionResponse(position);
    }

    public List<PositionResponse> getAll() {
        return positionRepo.findAll()
                .stream()
                .map(PositionResponse::new)
                .collect(Collectors.toList());
    }

    public PositionResponse save(PositionRequest positionRequest) {
        Position position = positionRepo.save(new Position(positionRequest));
        return new PositionResponse(position);
    }

    public PositionResponse update(Long id, PositionRequest positionRequest) {
        return positionRepo.findById(id)
                .map(position -> {
                    position.setName(positionRequest.getName());
                    return positionRepo.save(position);
                })
                .map(PositionResponse::new)
                .orElseThrow(() ->
                    new RuntimeException("Position with id " + id + " not found")
                );
    }

    public void delete(Long id) {
        positionRepo.deleteById(id);
    }
}
