package visualizer;

public class Plant {

    PlantType type;
    boolean alive;

    public Plant(PlantType type) {
        this.type = type;
        this.alive = true;
    }

    public String getSymbol() {
        if (type == PlantType.PINE)
            return "res/tree.jpg";
        return "res/" + type.name().toLowerCase() + (alive ? "" : "_dead") + ".jpg";
    }

    public enum PlantType {

        BEANS,
        BROCCOLI,
        CORN,
        PINE,
        POTATO,
        TOMATO;

        public boolean aids(PlantType targ) {
            switch (this) {
                case BEANS:
                    return targ == CORN || targ == TOMATO;
                case PINE:
                    return targ == BROCCOLI;
                case POTATO:
                    return targ == TOMATO;
                case TOMATO:
                    return targ == POTATO;
                default:
                    return false;
            }
        }

        public boolean kills(PlantType targ) {
            switch (this) {
                case PINE:
                    return targ != BROCCOLI && targ != PINE;
                default:
                    return false;
            }
        }
    }
}
