package visualizer;

public class Plant {

    PlantType type;
    boolean alive;

    public Plant(PlantType type) {
        this.type = type;
        this.alive = true;
    }

    public String getSymbol() {
        return "res/" + type.name().toLowerCase() + (alive ? "" : "_dead") + ".png";
    }
}
