/* @author Ethan Painter
 * quoteCMD created as a command line interface for quotesServe
 * Does not communicate with remote server (stand alone interface)
 * All interactions are recorded and displayed using only client side
 * Does not interact with remote server.
 */

import java.util.Scanner;

//Any imports
public class QuoteCMD{

    public static void main(String[] args)
    {
        int loopValue = 0;
        String[] myLastFiveSearches = {"","","","",""};

        QuoteList quoteList;
        Scanner sc = new Scanner(System.in);

        while(loopValue != 0)
        {
            //Search Functionality (Current existing quotes)
            //Search by Quote, Author, or both
            //Show Recent Searches (Just User Searches)
            System.out.println("MENU");
            System.out.println("0) Quit");
            System.out.println("1) Search For Existing Quote");
            System.out.println("2) Search For Existing Author");
            System.out.println("3) Search For Either Existing Quote or Author");
            System.out.println("4) Generate a Random Quote");
            System.out.println("5) Print last 5 Search Terms");
            loopValue = sc.nextInt();
            //Loop through loop with input
            switch(loopValue){
                case 0:
                    System.out.println("\nQuitting...");
                    break;
                case 1:
                    System.out.println("\nEnter term(s) to search for in a quote: ");
                    
                    break;
                case 2:
                    System.out.println("\nEnter term(s) to search for in an author's name: ");

                    break;
                case 3:
                    System.out.println("\nEnter term(s) to search for in an author or quote: ");

                    break;
                case 4:
                    System.out.println("\nGenerating a random Quote...");
                    //Generate a random quote
                    break;
                case 5:
                    System.out.println("\nPrinting last 5 Search Terms: ");

                    break;
                default:
                    System.out.println("\nError: menu number not detected");
                    System.out.println("Please enter another number within 0 to 5");
                    break;
            }
        }
    }
}