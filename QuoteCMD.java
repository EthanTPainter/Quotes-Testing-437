/* @author Ethan Painter
 * quoteCMD created as a command line interface for quotesServe
 * Does not communicate with remote server (stand alone interface)
 * All interactions are recorded and displayed using only client side
 * Does not interact with remote server.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//Any imports
public class QuoteCMD{

    //Added "throws IOException" because of Buffered Reader
    public static void main(String[] args) throws IOException
    {
        //Variables
        int loopValue = -1;
        String searchTerm;
        QuoteList searchList;
        Quote searchQuote;
        String[] myLastFiveSearches = {"","","","",""};
        int myLastFiveSearchesCounter = 0;
        QuoteList quoteList;
        //BufferedReader for CMD line input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //Read XML Files
        QuoteSaxParser quoteSaxParser = new QuoteSaxParser("C:/Users/Ethan/Desktop/GMU/2018 Spring Classes/Quotes-Testing-437/quotes.xml");
        quoteList = quoteSaxParser.getQuoteList();
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
            loopValue = Integer.parseInt(br.readLine());
            //Loop through loop with input
            switch(loopValue){
                case 0:
                    //Quit
                    System.out.println("\nQuitting...");
                    break;
                case 1:
                    //Search term(s) in quotes
                    System.out.println("\nEnter term(s) to search for in a quote: ");
                    searchTerm = br.readLine();
                    //Send input to search method
                    searchList = quoteList.search(searchTerm,1);
                    //Nothing found in search list (quotes)
                    if(searchList.getSize() == 0)
                    {
                        System.out.println("Term(s): \"" + searchTerm + "\" not found in current quote list");
                    }
                    //At least one search term found (quotes)
                    else{
                        for(int i = 0; i < searchList.getSize(); i++)
                        {
                            searchQuote = searchList.getQuote(i);
                            System.out.println("Quote:  " + searchQuote.getQuoteText());
                            System.out.println("Author: " + searchQuote.getAuthor() + "\n");
                        }
                    }
                    break;
                case 2:
                    //Search term(s) in authors
                    System.out.println("\nEnter term(s) to search for in an author's name: ");
                    searchTerm = br.readLine();
                    //Send input to search method
                    searchList = quoteList.search(searchTerm, 0);
                    //Nothing found in search list (authors)
                    if(searchList.getSize() == 0)
                    {
                        System.out.println("Terms: \"" + searchTerm + "\" not found in current author list");
                    }
                    //At least one search term found (authors)
                    else{
                        for(int i = 0; i < searchList.getSize(); i++)
                        {
                            searchQuote = searchList.getQuote(i);
                            System.out.println("Quote:  " + searchQuote.getQuoteText());
                            System.out.println("Author: " + searchQuote.getAuthor() + "\n");
                        }
                    }
                    break;
                case 3:
                    //Search term(s) in quotes or authors
                    System.out.println("\nEnter term(s) to search for in an author or quote: ");
                    searchTerm = br.readLine();
                    //Send input to search method
                    searchList = quoteList.search(searchTerm, 2);
                    //Nothing found in search list (quotes or authors)
                    if(searchList.getSize() == 0)
                    {
                        System.out.println("Terms: \"" + searchTerm + "\" not found in current quote or author lists");
                    }
                    //At least one search term found (quotes or authors)
                    else{
                        for(int i = 0; i < searchList.getSize(); i++)
                        {
                            searchQuote = searchList.getQuote(i);
                            System.out.println("Quote:  " + searchQuote.getQuoteText());
                            System.out.println("Author: " + searchQuote.getAuthor() + "\n");
                        }
                    }
                    break;
                case 4:
                    //Generate a random quote
                    System.out.println("\nGenerating a random Quote...");
                    Quote quoteTmp = quoteList.getRandomQuote();
                    System.out.println("Quote:  " + quoteTmp.getQuoteText() + "\nAuthor: " + quoteTmp.getAuthor() + "\n");
                    break;
                case 5:
                    //Print last 5 Search Terms
                    System.out.println("\nPrinting last 5 Search Terms: ");

                    break;
                default:
                    System.out.println("\nError: menu number not detected");
                    System.out.println("Please enter another number within 0 to 5\n");
                    break;
            }
        }
    }
}