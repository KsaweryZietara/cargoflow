package pl.polsl.cargoflow.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.polsl.cargoflow.model.Position;

@AllArgsConstructor
@Getter
@Setter
public class PositionResponse {
    private Long id;

    private String name;

    public PositionResponse(Position position) {
        this.id = position.getId();
        this.name = position.getName();
    }
}
