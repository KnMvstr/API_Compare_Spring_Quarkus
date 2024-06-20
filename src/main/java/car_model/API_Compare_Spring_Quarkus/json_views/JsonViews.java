package car_model.API_Compare_Spring_Quarkus.json_views;

public class JsonViews {

    public interface Brand { }
    public interface BrandPlus extends Brand, Model{ }

    public interface Model extends Brand {}
    public interface ModelPlus extends Model, CarType, Color {}

    public interface Engine extends Brand { }
    public interface EnginePlus extends Model {}

    public interface CarType {}
    public interface CarTypePlus extends CarType, Model { }

    public interface Color {}
    public interface ColorPlus extends Color, Model {}
    public interface ColorAdd  {}
}