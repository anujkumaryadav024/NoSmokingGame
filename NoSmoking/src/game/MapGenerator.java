package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapGenerator {
    int map[][];
    int brickWidth;
    int brickHeight;
    int bricksCount;
    int xOffset;
    int yOffset;

    public MapGenerator() {
        String csvFilePath = "C:\\Users\\anujk\\eclipse-workspace\\NoSmoking\\src\\res\\lungs.csv";
        initializeMap(csvFilePath);
        calculateBrickDimensions();
        setOffsets();
    }

    private void initializeMap(String csvFilePath) {
        int[] dimensions = determineDimensions(csvFilePath);
        map = new int[dimensions[0]][dimensions[1]];
        populateMap(csvFilePath);
    }

    private int[] determineDimensions(String csvFilePath) {
        int rows = 0;
        int cols = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                cols = values.length;
                rows++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new int[]{rows, cols};
    }

    private void populateMap(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            int r = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int c = 0; c < values.length; c++) {
                    map[r][c] = Integer.parseInt(values[c]);
                    if (map[r][c] > 0)
                        bricksCount++;
                }
                r++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void calculateBrickDimensions() {
        brickWidth = 600 / map[0].length;
        brickHeight = 600 / map.length;
    }

    private void setOffsets() {
        xOffset = 120;
        yOffset = 50;
    }

    public void setBrick(int value, int r, int c) {
        map[r][c] = value;
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                if (map[i][j] > 0) {
                    if (map[i][j] == 2) {
                        g.setColor(Color.PINK);
                    } else if (map[i][j] == 1) {
                    	g.setColor(new Color(181, 101, 29));
                    }
                    g.fillRect(j * brickWidth + xOffset, i * brickHeight + yOffset, brickWidth, brickHeight);

                    g.setColor(Color.BLACK);
                    g.setStroke(new BasicStroke(3));
                    g.drawRect(j * brickWidth + xOffset, i * brickHeight + yOffset, brickWidth, brickHeight);
                }
            }
        }
    }
}
