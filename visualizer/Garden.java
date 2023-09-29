package visualizer;

public class Garden {

    public static final int GARDEN_SIZE = 4;

    private Plant[][] gardenGrid = new Plant[GARDEN_SIZE][GARDEN_SIZE];

    public Garden() {
    }

    public Plant get(int x, int y) {
        if (x < 0 || y < 0 || x >= GARDEN_SIZE || y >= GARDEN_SIZE)
            throw new IllegalArgumentException();

        return gardenGrid[x][y];
    }

    public void set(int x, int y, Plant p) {
        if (x < 0 || y < 0 || x >= GARDEN_SIZE || y >= GARDEN_SIZE)
            throw new IllegalArgumentException();

        gardenGrid[x][y] = p;
    }

    public void validate() {
        for (int x = 0; x < GARDEN_SIZE; x++)
            for (int y = 0; y < GARDEN_SIZE; y++)
                if (gardenGrid[x][y] != null) {
                    gardenGrid[x][y].alive = true;
                    Plant[] adjs = new Plant[4];
                    try {
                        adjs[0] = gardenGrid[x + 1][y];
                    } catch (ArrayIndexOutOfBoundsException ex) {
                    }
                    try {
                        adjs[1] = gardenGrid[x - 1][y];
                    } catch (ArrayIndexOutOfBoundsException ex) {
                    }
                    try {
                        adjs[2] = gardenGrid[x][y + 1];
                    } catch (ArrayIndexOutOfBoundsException ex) {
                    }
                    try {
                        adjs[3] = gardenGrid[x][y - 1];
                    } catch (ArrayIndexOutOfBoundsException ex) {
                    }

                    for (Plant p : adjs) {
                        if (p != null) {
                            if (p.type.kills(gardenGrid[x][y].type))
                                gardenGrid[x][y].alive = false;
                            if (p.type.aids(gardenGrid[x][y].type)) {
                                gardenGrid[x][y].alive = true;
                                break;
                            }
                        }
                    }
                }
    }
}
