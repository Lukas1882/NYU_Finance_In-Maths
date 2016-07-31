/**
 * Created by Luke on 7/31/16.
 */

import java.util.Scanner;;

public class main {
    public static void main(String[] args) {
        appInitial();
        selectApp();
    }

    private static void appInitial()
    {
        // Print the instruction for this program.
        String textStr = "Hi, this is Luke(Yaolong)'s Java Program. Please SELECT the assignment you need to run:\n\n";
        String assignment1 = "1(A) Hello World\n2(A) Manipulating Data\n";
        textStr += assignment1;
        System.out.print(textStr);

    }

    // selection method for user to pick apps to run.
    private static void selectApp() {
        String textStr = "\n\n  Please type the ID to run(1, 2, e.g..).\n\n\n";
        System.out.print(textStr);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        // run the selected apps.
        try {
            int Id = 0;
            Id = Integer.parseInt(input.trim());
            switch (Id) {
                case (1):
                    Summer.HelloWorld.HelloWorld();
                    break;
                case (2):
                    Summer.ManipulatDate.ManipulatDate();
                    break;
                default:
                    System.out.print("Cannot find ID \"" + Id + "\". Please try again.");
            }
        } catch (Exception ex) {
            System.out.print("Cannot find ID \"" + input + "\". Please try again.");
        }
        selectApp();
    }
}
