/* @author: Ethan Painter
 * Created Date: 2/1/17
 * Config: Java 1.8
 *
 * quoteCMD created as a command line interface for quotesServe
 * Does not communicate with remote server (stand alone interface)
 * All interactions are recorded and displayed using only client side
 */

//Any imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QuoteCMD{

    //Variables
    private String searchTerm;
    private String[] myLastFiveSearches = {"","","","",""};
    private int myLastFiveSearchesCounter = 0;
    private QuoteList searchList;
    private Quote searchQuote;
    //BufferedReader for CMD line input
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    //Read XML Files
    QuoteSaxParser quoteSaxParser = new QuoteSaxParser("C:\\Users\\EthanPC\\Desktop\\GMU\\Quotes-Testing-437\\quotes.xml");
    QuoteList quoteList = quoteSaxParser.getQuoteList();

    //Getter for quoteList
    public QuoteList getQuoteList() {
        return quoteList;
    }

    //Closes buffered reader br when quitting
    public void closeBufferedReader() throws IOException {
        br.close();
    }

    //Added "throws IOException" because of Buffered Reader
    public static void main(String[] args) throws IOException
    {
        //Make QuoteCMD runner to use methods included
        QuoteCMD runner = new QuoteCMD();
        int loopValue = -1;
        while(loopValue != 0)
        {
            //BufferedReader for CMD line input
            BufferedReader sample = new BufferedReader(new InputStreamReader(System.in));
            //Search Functionality (Current existing quotes)
            //Search by Quote, Author, or both
            //Show Recent Searches (Just User Searches)
            System.out.println("MENU");
            System.out.println("0) Quit");
            System.out.println("1) Search For Existing Quote");
            System.out.println("2) Search For Existing Author");
            System.out.println("3) Search For Either Existing Quote or Author");
            System.out.println("4) Add quote to current quote list");
            System.out.println("5) Generate a Random Quote");
            System.out.println("6) Print last 5 Search Terms");
            loopValue = Integer.parseInt(sample.readLine());
            //Loop through loop with input
            switch(loopValue){
                case 0:
                    //Quit & Close Buffered Readers
                    sample.close();
                    runner.closeBufferedReader();
                    System.out.println("\nQuitting...");
                    break;
                case 1:
                    //Mode = 1
                    runner.searchQuotes(1);
                    break;
                case 2:
                    //Mode = 0
                    runner.searchQuotes(0);
                    break;
                case 3:
                    //Mode = 2
                    runner.searchQuotes(2);
                    break;
                case 4:
                    if (addQuote(runner.getQuoteList())){
                        System.out.println("\nSuccessfully Added new Quote!\n");
                    }
                    else{
                        System.out.println("\nError: Quote text provided is empty (No quote provided)\n");
                    }
                    break;
                case 5:
                    //Generate a random quote
                    runner.generateRandomQuote();
                    break;
                case 6:
                    //Print last 5 Search Terms
                    runner.printLastFiveSearchTerms();
                    break;
                default:
                    System.out.println("\nError: menu number not detected");
                    System.out.println("Please enter another number within 0 to 5\n");
                    break;
            }
        }
    }

    public void searchQuotes(int mode) throws IOException
    {
        //Search term(s) in quotes
        System.out.println("\nEnter term(s) to search for in a quote: ");
        searchTerm = br.readLine();
        //If room in list, update it
        if(myLastFiveSearchesCounter < 5)
        {
            myLastFiveSearches[myLastFiveSearchesCounter] = searchTerm;
            myLastFiveSearchesCounter++;
        }
        //Max Counter Reached
        else{
            //Remove Earliest indexed term to make room for the new term
            for(int i = 0; i < 4; i++)
            {
                myLastFiveSearches[i] = myLastFiveSearches[i+1];
            }
            myLastFiveSearches[myLastFiveSearchesCounter-1] = searchTerm;
        }
        //Send input to search method
        searchList = quoteList.search(searchTerm, mode);
        //Nothing found in search list (quotes)
        if(searchList.getSize() == 0)
        {
            System.out.println("\nTerm(s): \"" + searchTerm + "\" not found in current quote list\n");
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
    }

    public static boolean addQuote(QuoteList list) throws IOException {
        String newQuoteText;
        String newAuthor;
        Quote newQuote;
        BufferedReader newBR = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\nPrepare to add Quote");
        //Get input for quoteText and author from user
        System.out.print("\nQuote: ");
        newQuoteText = newBR.readLine();
        System.out.print("Author: ");
        newAuthor = newBR.readLine();
        //If quote is empty, return an error message and return to menu
        if(newQuoteText.equals("")){
            return false;
        }
        //If author is empty, make a author anonymous (refers to Constructor I designed in assignment #2)
        if(newAuthor.equals("")){
            newQuote = new Quote(newQuoteText);
        }
        else{
            newQuote = new Quote(newAuthor, newQuoteText);
        }
        //Add quote to the current quote list (permanent)
        list.setQuote(newQuote);
        //After session closes, quote is lost/removed. Only xml data is saved
        //Would have to add to xml data file to make permanent
        
        return true;
    }

    public void generateRandomQuote()
    {
        System.out.println("\nGenerating a random Quote...");
        Quote quoteTmp = quoteList.getRandomQuote();
        System.out.println("Quote:  " + quoteTmp.getQuoteText() + "\nAuthor: " + quoteTmp.getAuthor() + "\n");
    }

    public void printLastFiveSearchTerms()
    {
        System.out.println("\nPrinting last 5 Search Terms: ");
        for(int i = 0; i < myLastFiveSearchesCounter; i++)
        {
            System.out.println(i+1 +") " + myLastFiveSearches[i]);
        }
        System.out.println();
    }
}