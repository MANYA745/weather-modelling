import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class WeatherModeling extends JPanel {

    private static List<Double> xValues = new ArrayList<>();
    private static List<Double> yValues = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Menu for selecting stages
            System.out.println("\nWeather Modeling Program");
            System.out.println("1. Hardcoded Inputs");
            System.out.println("2. Keyboard Inputs");
            System.out.println("3. Read Inputs from File");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    hardcodedInputs();
                    break;
                case 2:
                    keyboardInputs(scanner);
                    break;
                case 3:
                    readInputsFromFile();
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            // Display the graph after computing y-values
            if (!xValues.isEmpty() && !yValues.isEmpty()) {
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame("Parabola Graph");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.add(new WeatherModeling());
                    frame.setSize(800, 600);
                    frame.setVisible(true);
                });
            }
        }
    }

    // Stage 1: Hardcoded Inputs
    private static void hardcodedInputs() {
        double a = 1.0, b = 2.0, c = 3.0;
        xValues.clear();
        yValues.clear();

        for (double x = -10; x <= 10; x += 0.5) {
            double y = -a * Math.pow(x, 2) + b * x + c;
            xValues.add(x);
            yValues.add(y);
        }
        System.out.println("Graph generated using hardcoded inputs.");
    }

    // Stage 2: Keyboard Inputs
    private static void keyboardInputs(Scanner scanner) {
        System.out.print("Enter coefficient a: ");
        double a = scanner.nextDouble();
        System.out.print("Enter coefficient b: ");
        double b = scanner.nextDouble();
        System.out.print("Enter coefficient c: ");
        double c = scanner.nextDouble();

        xValues.clear();
        yValues.clear();

        for (double x = -10; x <= 10; x += 0.5) {
            double y = -a * Math.pow(x, 2) + b * x + c;
            xValues.add(x);
            yValues.add(y);
        }
        System.out.println("Graph generated using keyboard inputs.");
    }

    // Stage 3: Read Inputs from File
    private static void readInputsFromFile() {
        try {
            Scanner fileReader = new Scanner(new File("input.txt"));

            xValues.clear();
            yValues.clear();

            while (fileReader.hasNextLine()) {
                String[] values = fileReader.nextLine().split(" ");
                if (values.length != 4) {
                    System.out.println("Skipping invalid line: " + String.join(" ", values));
                    continue;
                }

                double a = Double.parseDouble(values[0]);
                double b = Double.parseDouble(values[1]);
                double c = Double.parseDouble(values[2]);
                double x = Double.parseDouble(values[3]);

                double y = -a * Math.pow(x, 2) + b * x + c;
                xValues.add(x);
                yValues.add(y);
            }

            fileReader.close();
            System.out.println("Graph generated using inputs from file.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Override paintComponent to draw the graph
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw axes
        int width = getWidth();
        int height = getHeight();
        int originX = width / 2;
        int originY = height / 2;

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawLine(0, originY, width, originY); // X-axis
        g2d.drawLine(originX, 0, originX, height); // Y-axis

        // Draw parabola
        g2d.setColor(Color.BLUE);

        for (int i = 0; i < xValues.size() - 1; i++) {
            int x1 = (int) (originX + xValues.get(i) * 20);
            int y1 = (int) (originY - yValues.get(i) * 20);
            int x2 = (int) (originX + xValues.get(i + 1) * 20);
            int y2 = (int) (originY - yValues.get(i + 1) * 20);
            g2d.drawLine(x1, y1, x2, y2);
        }
    }
}
