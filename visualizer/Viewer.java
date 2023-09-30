package visualizer;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

public class Viewer extends JFrame {

    private static final int OFF_X = 16;
    private static final int OFF_Y = 39;

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private static final int CELL_S = 80;

    private GroupLayout layout = new GroupLayout(getContentPane());

    private String title = "Optimal Garden Visualizer";
    private String icoPath = "res/logo.png";

    private Garden garden = new Garden();

    private JToggleButton[][] gridToggles = new JToggleButton[Garden.GARDEN_SIZE][Garden.GARDEN_SIZE];

    public Viewer() {
        // Open the window
        init();
        EventQueue.invokeLater(() -> {
            setLocationRelativeTo(null);
            setVisible(true);
        });
    }

    private void init() {
        // Window
        setLayout(layout);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH + OFF_X, HEIGHT + OFF_Y));
        setTitle(title);
        setIconImage(new ImageIcon(icoPath).getImage());

        // Garden Grid
        for (int x = 0; x < Garden.GARDEN_SIZE; x++)
            for (int y = 0; y < Garden.GARDEN_SIZE; y++) {
                JToggleButton jT = new JToggleButton();
                int pX = CELL_S * x + CELL_S / 2;
                int pY = CELL_S * (y + 1);
                jT.setLocation(pX, pY);
                jT.setSize(CELL_S, CELL_S);
                jT.addMouseListener(new GridSelectListener(x, y));
                gridToggles[x][y] = jT;
                add(gridToggles[x][y]);
            }

        // Plant Selector
        for (int i = 0; i < PlantType.values().length; i++) {
            JButton jB = new JButton();
            jB.setLocation(WIDTH - CELL_S, (CELL_S / 2) * (i + 2));
            jB.setSize(CELL_S / 2, CELL_S / 2);
            Plant p = new Plant(PlantType.values()[i]);
            jB.setIcon(new ImageIcon(p.getSymbol()));
            jB.addMouseListener(new PlantSelectListener(p.type));
            add(jB);

            JLabel jL = new JLabel(PlantType.values()[i].name());
            jL.setLocation(WIDTH - CELL_S * 2, (CELL_S / 2) * (i + 2));
            jL.setSize(CELL_S, CELL_S / 2);
            add(jL);
        }
    }

    private void refresh() {
        garden.validate();
        for (int x = 0; x < Garden.GARDEN_SIZE; x++)
            for (int y = 0; y < Garden.GARDEN_SIZE; y++)
                try {
                    gridToggles[x][y].setIcon(new ImageIcon(garden.get(x, y).getSymbol()));
                } catch (NullPointerException ex) {
                    gridToggles[x][y].setIcon(null);
                }
    }

    private class GridSelectListener extends MouseAdapter {

        int x, y;

        GridSelectListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.isShiftDown()) {
                JToggleButton j = gridToggles[x][y];
                j.setSelected(false);
                j.setIcon(null);
                garden.set(x, y, null);
                refresh();
            }
        }
    }

    private class PlantSelectListener extends MouseAdapter {

        PlantType type;

        PlantSelectListener(PlantType type) {
            this.type = type;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            for (int x = 0; x < Garden.GARDEN_SIZE; x++)
                for (int y = 0; y < Garden.GARDEN_SIZE; y++)
                    if (gridToggles[x][y].isSelected()) {
                        garden.set(x, y, new Plant(type));
                        gridToggles[x][y].setSelected(false);
                    }
            refresh();
        }
    }
}
