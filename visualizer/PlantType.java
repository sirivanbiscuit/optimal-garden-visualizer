package visualizer;

public enum PlantType {

    PINE,
    CORN,
    BEANS,
    TOMATOES,
    PEPPERS;

    /*
     * Corn and Beans help each other
     * Tomatoes and Peppers help each other
     */
    public boolean aids(PlantType targ) {
        return switch (this) {
            case CORN -> targ == BEANS;
            case BEANS -> targ == CORN;
            case TOMATOES -> targ == PEPPERS;
            case PEPPERS -> targ == TOMATOES;
            default -> false;
        };
    }

    /*
     * Corn and Tomatoes kill each other
     * Beans and Peppers kill each other
     * Pines kill all other plants
     * Non-pine plants kill themselves
     */
    public boolean kills(PlantType targ) {
        return switch (this) {
            case PINE -> targ != PINE;
            case CORN -> targ == TOMATOES || targ == CORN;
            case BEANS -> targ == PEPPERS || targ == BEANS;
            case TOMATOES -> targ == CORN || targ == TOMATOES;
            case PEPPERS -> targ == BEANS || targ == PEPPERS;
            default -> false;
        };
    }
}
