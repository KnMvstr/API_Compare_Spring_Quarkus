package car_model.API_Compare_Spring_Quarkus.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String type;


    public NotFoundException(String type) {
        super("Entity not found");
        this.type = type;

    }
}
