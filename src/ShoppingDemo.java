import java.util.Scanner;

public class ShoppingDemo {
    private static final int size = 7;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        double sum = 0;
        double budget = 59.00;
        ItemDesc[] shoppingList = new ItemDesc[size];
        ItemDesc[] sortList = new ItemDesc[size];
        int[] sortPriority = new int[size];

        System.out.println("----------------Please add items to your shop list----------------");
        System.out.println("Note: Price must be set down to penny.");
        System.out.println("Press Enter to start >>");

        /** Read input algorithm:
         * for (0 ~ size)
         *      Take user inputs (name, price, priority, quantity)
         *      if (name is duplicate)
         *          re-input
         *      else
         *          setName()
         *      Increment total price
         * End loop
         * while (sum < 100)
         *      Choose an item to change the price
         *      Compute total price
         * End loop
         */

        for (int i = 0; i < size; i++) {

            shoppingList[i] = new ItemDesc();

            in.nextLine(); //Eat the "\n"

            System.out.println("Enter item " + (i + 1) + "'s name: ");
            String inputName = in.nextLine();
            shoppingList[i].setName(inputName);

            //Check if duplicated entry exists
            for (int j = 0; j < i; j++) {
                if (shoppingList[j].getName().equals(inputName)) {
                    System.out.println("Item already exists. Please enter again: ");
                    inputName = in.nextLine();
                    shoppingList[i].setName(inputName);
                }
            }

            //Input price
            System.out.println("Enter it's unit price: ");
            double inputPrice = in.nextDouble();
            shoppingList[i].setPrice(inputPrice);

            //Input priority
            System.out.println("Enter it's priority: ");
            int inputPriority = in.nextInt();
            shoppingList[i].setPriority(inputPriority);

            //Input quantity
            System.out.println("Enter it's quantity: ");
            int inputQty = in.nextInt();
            shoppingList[i].setQuantity(inputQty);

            sum += inputPrice * inputQty;

            System.out.println("Total Value = $" + String.format("%.2f", sum));
            System.out.println();
        }

        //Check if total price > $100
        while (sum < 100.00) {
            System.out.println("The total value is less than $100. Please change the price of items on your list.");
            System.out.println("Enter a number to change the corresponding item price.");
            for (int i = 0; i < size; i++) {
                System.out.println((i + 1) + " - " + shoppingList[i].getName());
            }

            int index = in.nextInt();

            boolean flag = true;

            while (flag) {
                switch (index) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        System.out.println("The original price of " + shoppingList[index - 1].getName() +
                                " is $" + shoppingList[index - 1].getPrice());
                        System.out.println("Please re-enter a higher price: ");
                        sum -= shoppingList[index - 1].getPrice() * shoppingList[index - 1].getQuantity();
                        shoppingList[index - 1].setPrice(in.nextDouble());
                        sum += shoppingList[index - 1].getPrice() * shoppingList[index - 1].getQuantity();
                        flag = false;
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1-7.");
                        index = in.nextInt();
                }
            }
            System.out.println("Now the total price is $" + String.format("%.2f", sum));
            System.out.println();
        }

        /** Sort by priority algorithm:
         * Create a new array to get priorities from shoppingList
         * Sort the new array
         * If priority in new array == priority in original array, set the item desc of new array
         */
        for (int i = 0; i < size; i++) {
            sortPriority[i] = shoppingList[i].getPriority();
            sortList[i] = new ItemDesc();
        }

        ShoppingDemo.bubbleSort(sortPriority, size);

        System.out.println();
        System.out.println("Your shopping list based on priority:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (sortPriority[i] == shoppingList[j].getPriority()) { //primitive types can use == to compare
                    sortList[i].setName(shoppingList[j].getName());
                    sortList[i].setPrice(shoppingList[j].getPrice());
                    sortList[i].setPriority(shoppingList[j].getPriority());
                    sortList[i].setQuantity(shoppingList[j].getQuantity());
                }
            }
            System.out.println(sortList[i].getPriority() + ", " + sortList[i].getName() +
                    ", $" + sortList[i].getPrice() + ", Qty: " + sortList[i].getQuantity());
        }

        /**Display final list
         * Create a variable (count) to store how many items are purchased
         * for (0 ~ count-1)
         *      print purchased items
         * for (count ~ size)
         *      print unpurchased items
         */
        int count = ShoppingDemo.purchaseItem(sortList, budget);
        //System.out.println("count = " + count);

        System.out.println();
        System.out.println("You have $" + budget + " budget.");
        System.out.println("-----------------------------------");
        System.out.println("Purchased Item Details:");
        for (int i = 0; i < count; i++) {
            System.out.println(sortList[i].getName() +
                    ", $" + sortList[i].getPrice() +
                    ", Qty: " + sortList[i].getQuantity() +
                    ", total: $" + String.format("%.2f", sortList[i].getPrice() * sortList[i].getQuantity()));

            budget -= sortList[i].getPrice() * sortList[i].getQuantity();
        }
        System.out.println("-----------------------------------");
        System.out.println("Unpurchased Item Details:");
        for (int i = count; size - i > 0; i++) {
            System.out.println(sortList[i].getName() +
                    ", $" + sortList[i].getPrice() +
                    ", Qty: " + sortList[i].getQuantity() +
                    ", total: $" + String.format("%.2f", sortList[i].getPrice() * sortList[i].getQuantity()));
        }
        System.out.println("-----------------------------------");
        System.out.println("Remaining budget: \n$" + String.format("%.2f", budget));
    }

    public static void bubbleSort(int[] a, int n) {
        int i, j;

        //After sorting, small number = less priority; large number = higher priority
        for (i = 0; i < n; i++) {
            for (j = 1; j < n-i; j++) {
                if (a[j-1] < a[j]) {
                    int temp;
                    temp = a[j-1];
                    a[j-1] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        return (this == object);
    }

    public static int purchaseItem(ItemDesc [] array, double budget) {
        int i = 0;
        do {
            budget -= array[i].getPrice() * array[i].getQuantity();
            i++;
        } while (budget > 0);
        return i-1;
    }
}
