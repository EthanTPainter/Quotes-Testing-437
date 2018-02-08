/* @author: Ethan Painter
 * Created Date: 2/1/17
 * Config: Java 1.8
 *
 * quoteCMD created as a command line interface for quotesServe
 * Does not communicate with remote server (stand alone interface)
 * All interactions are recorded and displayed using only client side
 */

//Any imports

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class QuoteCMD{

    //Variables
    private String searchTerm;
    private String[] myLastFiveSearches = {"","","","",""};
    private int myLastFiveSearchesCounter = 0;
    private QuoteList searchList;
    private Quote searchQuote;

    //Temp Variables (only used to store temp quote text and author)
    //This only occurs when adding quotes to XML
    String enteredQuoteText;
    String enteredAuthor;

    //BufferedReader for CMD line input
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    //Quote SAParser to Parse XML Files
    QuoteSaxParser quoteSaxParser = new QuoteSaxParser("C:\\Users\\jedwa\\Documents\\GMU\\2018_spring\\cs437_softwareTesting\\Quotes-Testing-437\\quotes.xml");    //laptop
    QuoteList quoteList = quoteSaxParser.getQuoteList();

    //Getter for quoteList
    public QuoteList getQuoteList() {
        return quoteList;
    }

    //Closes buffered reader br when quitting
    public void closeBufferedReader() throws IOException {
        br.close();
    }

    //Getters and Setters for Entered Quote Text and Author
    //Again, only relevant to use during changing XML (adding quotes)
    public String getEnteredQuoteText(){
        return enteredQuoteText;
    }
    public String getEnteredAuthor() {
        return enteredAuthor;
    }
    public void setQuoteText(String qt){
        enteredQuoteText = qt;
    }
    public void setAuthor(String a){
        enteredAuthor = a;
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
                    String newQuoteText;
                    String newAuthor;
                    BufferedReader newBR = new BufferedReader(new InputStreamReader(System.in));
                    System.out.print("\nPrepare to add Quote");
                    //Get input for quoteText and author from user
                    System.out.print("\nQuote: ");
                    newQuoteText = newBR.readLine();
                    System.out.print("Author: ");
                    newAuthor = newBR.readLine();
                    if (addQuote(runner.getQuoteList(), newQuoteText, newAuthor)){
                        //If quote entered is a valid quote, change XML to add it
                        //Makes added quote permanently part of the Quote List
                        runner.addQuoteToXML(newQuoteText, newAuthor);
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

    //Used to search quote texts, authors, or both depending on passed in mode
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

    //Called whenever to add quotes to the QuoteList
    //DOES NOT ADD THE QUOTE TO THE XML FILE. THIS IS DONE SEPARATELY
    public static boolean addQuote(QuoteList list, String quoteText, String author) throws IOException {
        Quote newQuote;
        //If quote is empty, return an error message and return to menu
        if(quoteText.equals("")){
            return false;
        }
        //If author is empty, make a author anonymous (refers to Constructor I designed in assignment #2)
        if(author.equals("")){
            newQuote = new Quote(quoteText);
        }
        else{
            newQuote = new Quote(author, quoteText);
        }
        //Add quote to the current quote list
        list.setQuote(newQuote);
        return true;
    }

    //Function to add Quote to XML
    public void addQuoteToXML(String quoteText, String author)
    {
        //SAX Parser can't add/modify XML, so instead rely on DOM Parser
        //Create DOM Parser to append XML to current document
        try{
            //Set up DOM Parser
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse("C:\\Users\\jedwa\\Documents\\GMU\\2018_spring\\cs437_softwareTesting\\Quotes-Testing-437\\quotes.xml");          //DESKTOP

            //Get last child node
            Node node = document.getFirstChild(); //quote-list

            //Add New Quote Element to quote XML
            Element quote = document.createElement("quote");
            node.appendChild(quote);

            //Make Quote Text Element
            Element nodeQuoteText = document.createElement("quote-text");
            nodeQuoteText.appendChild(document.createTextNode(quoteText));
            //Append to quote node
            quote.appendChild(nodeQuoteText);

            //Make Author Element
            Element nodeAuthor = document.createElement("author");
            nodeAuthor.appendChild(document.createTextNode(author));
            //Append to quote node
            quote.appendChild(nodeAuthor);

            //Transform the file to append the new quote
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //Add output properties to Transformer to Reformat XML
            //Normally the XML would be added together in a straight line. These output properties will modify the format
            //The Formatting isn't perfect, but most IDEs or tools provide plugins to automatically reformat code
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            //End of Formatting

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("C:\\Users\\jedwa\\Documents\\GMU\\2018_spring\\cs437_softwareTesting\\Quotes-Testing-437\\quotes.xml"));       //DESKTOP

            transformer.transform(source, result);
        }
        catch(ParserConfigurationException | IOException | SAXException | TransformerException e){
            e.printStackTrace();
        }
    }

    //Generates a random quote from Quote List
    public void generateRandomQuote()
    {
        System.out.println("\nGenerating a random Quote...");
        Quote quoteTmp = quoteList.getRandomQuote();
        System.out.println("Quote:  " + quoteTmp.getQuoteText() + "\nAuthor: " + quoteTmp.getAuthor() + "\n");
    }

    //Prints last five search terms to cmd line interface
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