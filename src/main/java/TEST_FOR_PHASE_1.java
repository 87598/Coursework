import java.util.Scanner;

public class TEST_FOR_PHASE_1 {
    public static void main(String[] args){
        insertAPizza();
    }
    private static void insertAPizza() {
        Scanner input = new Scanner(System.in);
        System.out.println("Do you want to make a pizza? (Y/N)");
        String answer = input.nextLine();
        if (answer.equals("Y")){
            System.out.println("How big do you want your pizza? (Large, Medium or Small)");
            String size = input.nextLine();
            System.out.println("How many topping do you want? (1, 2 or 3)");
            int amountTopping = input.nextInt();
            if(amountTopping == 1){
                System.out.println("What topping do you want? ");
                String topping1 = input.nextLine();
                System.out.println("Your pizza size: " + size + "Your toppings: " + topping1);
            }
            else if(amountTopping == 2){
                System.out.println("What topping do you want? ");
                String topping1 = input.nextLine();
                System.out.println("What topping do you want? ");
                String topping2 = input.nextLine();
                System.out.println("Your pizza size: " + size + "Your toppings: " + topping1 + ", " + topping2);
            }
            else if(amountTopping == 3){
                System.out.println("What topping do you want? ");
                String topping1 = input.nextLine();
                System.out.println("What topping do you want? ");
                String topping2 = input.nextLine();
                System.out.println("What topping do you want? ");
                String topping3 = input.nextLine();
                System.out.println("Your pizza size: " + size + "Your toppings: " + topping1 + ", " + topping2 + ", " + topping3);
            }
        }
        else if(answer.equals("N")){
            System.out.println("Okay bye!");
        }

    }
}
