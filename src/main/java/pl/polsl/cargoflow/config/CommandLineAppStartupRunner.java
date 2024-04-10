package pl.polsl.cargoflow.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.polsl.cargoflow.model.Position;
import pl.polsl.cargoflow.repo.PositionRepo;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private final PositionRepo positionRepo;

    public CommandLineAppStartupRunner(PositionRepo positionRepo) {
        this.positionRepo = positionRepo;
    }
    
    @Override
    public void run(String...args) throws Exception {
        if (positionRepo.findByName("koordynator").isEmpty()) {
            Position position = new Position();
            position.setName("koordynator");
            positionRepo.save(position);
        }
        if (positionRepo.findByName("kierowca").isEmpty()) {
            Position position = new Position();
            position.setName("kierowca");
            positionRepo.save(position);
        }
        if (positionRepo.findByName("mechanik").isEmpty()) {
            Position position = new Position();
            position.setName("mechanik");
            positionRepo.save(position);
        }
    }
}
