package src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean check = true;
        while (check == true) {
        System.out.println("Enter Band (1) or check entered Bands (2)");
            Scanner anObj = new Scanner(System.in);
            Integer choice = anObj.nextInt();
            if (choice == 1) {
                
            } else if (choice == 2) {
                
            } else {
                anObj.close();
                check = false;
            }
        }
    }
}
