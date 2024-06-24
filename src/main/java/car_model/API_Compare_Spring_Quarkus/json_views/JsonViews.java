package car_model.API_Compare_Spring_Quarkus.json_views;

public class JsonViews {
    public interface BrandMin {}
    public interface Brand extends BrandMin{ }
    public interface BrandPlus extends BrandMin, Brand, Model{ }

    public interface ModelMin {}
    public interface Model extends ModelMin, BrandMin, EngineMin, ColorMin, CarTypeMin {}
    public interface ModelPlus extends Model, CarTypeMin, ColorMin {}

    public interface EngineMin {}
    public interface Engine extends EngineMin, Brand { }
    public interface EnginePlus extends Model {}

    public interface CarTypeMin {}
    public interface CarType extends CarTypeMin {}
    public interface CarTypePlus extends CarType, ModelMin { }

    public interface ColorMin {}
    public interface Color extends ColorMin {}
    public interface ColorPlus extends ColorMin, Color, ModelMin {}
    public interface ColorAdd  {}
}