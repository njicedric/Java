package ESPGame;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ESPGame {
    public static void main(String[] args) {
        final String COLOR_FILE = "C:\\Users\\Kingsley Ndi\\Downloads\\Assignment1_st_updated012025 (1)\\Assignment1_st_updated012025\\colors.txt";
        final String RESULTS_FILE = "EspGameResults.txt";
        final int MAX_COLORS = 16;
        final int MAX_GUESSES = 3;

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("CMSC203 Assignment1: Test your ESP skills!");
        System.out.println("Welcome to ESP - extrasensory perception!");

        boolean playAgain = true;
        int totalCorrectGuesses = 0;

        while (playAgain) {
            // Display menu
            System.out.println("\nWould you please choose one of the 4 options from the menu:");
            System.out.println("1. Read and display the first 16 names of colors from a file colors.txt");
            System.out.println("2. Read and display the first 10 names of colors from a file colors.txt");
            System.out.println("3. Read and display the first 5 names of colors from a file colors.txt");
            System.out.println("4. Exit from the program");
            System.out.print("Enter the option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option == 4) {
                System.out.println("Exiting the program.");
                break;
            }

            int numColors = 0;
            switch (option) {
                case 1:
                    numColors = 16;
                    break;
                case 2:
                    numColors = 10;
                    break;
                case 3:
                    numColors = 5;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    continue;
            }

            // Read and display colors
            System.out.println("\nThere are " + numColors + " colors from the file:");
            try {
                Scanner fileScanner = new Scanner(new File(COLOR_FILE));
                for (int i = 1; i <= numColors && fileScanner.hasNextLine(); i++) {
                    String color = fileScanner.nextLine();
                    System.out.println(i + " " + color);
                }
                fileScanner.close();
            } catch (IOException e) {
                System.out.println("Error reading colors file.");
                return;
            }

            // Game logic
            int correctGuesses = 0;
            for (int round = 1; round <= MAX_GUESSES; round++) {
                System.out.println("\nRound " + round);
                int randomIndex = random.nextInt(numColors) + 1;

                String selectedColor = "";
                try {
                    Scanner fileScanner = new Scanner(new File(COLOR_FILE));
                    for (int i = 1; i <= randomIndex && fileScanner.hasNextLine(); i++) {
                        selectedColor = fileScanner.nextLine();
                    }
                    fileScanner.close();
                } catch (IOException e) {
                    System.out.println("Error reading colors file.");
                    return;
                }

                System.out.println("I am thinking of a color.");
                System.out.println("Is it one of the list of colors above?");
                System.out.print("Enter your guess: ");
                String guess = scanner.nextLine();

                if (guess.equalsIgnoreCase(selectedColor)) {
                    correctGuesses++;
                }

                System.out.println("I was thinking of " + selectedColor + ".");
            }

            totalCorrectGuesses += correctGuesses;
            System.out.println("\nGame Over");
            System.out.println("You guessed " + correctGuesses + " out of " + MAX_GUESSES + " colors correctly.");

            // Ask if the user wants to continue
            System.out.print("\nWould you like to continue the game? Type Yes/No: ");
            String continueChoice = scanner.nextLine();
            if (!continueChoice.equalsIgnoreCase("Yes")) {
                playAgain = false;
            }
        }

        // Collect user information
        System.out.print("\nEnter your name: ");
        String name = scanner.nextLine();
        System.out.print("Describe yourself: ");
        String description = scanner.nextLine();
        System.out.print("Due Date (MM/DD/YY): ");
        String dueDate = scanner.nextLine();

        // Write results to file
        String results = "Game Over\n" +
                "You guessed " + totalCorrectGuesses + " out of " + (MAX_GUESSES * (totalCorrectGuesses / MAX_GUESSES)) + " colors correctly.\n" +
                "Due Date: " + dueDate + "\n" +
                "Username: " + name + "\n" +
                "User Description: " + description + "\n" +
                "Date: " + dueDate;

        try (FileWriter writer = new FileWriter(RESULTS_FILE)) {
            writer.write(results);
            System.out.println("\nResults written to " + RESULTS_FILE);
        } catch (IOException e) {
            System.out.println("Error writing results to file.");
        }

        scanner.close();
    }
}