/* @author Ethan Painter
 * quoteCMD created as a command line interface for quotesServe
 * Does not communicate with remote server (stand alone interface)
 * All interactions are recorded and displayed using only client side
 * Does not interact with remote server.
 */

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//Any imports
public class QuoteCMD{

    public static void main(String[] args)
    {
        //Variables
        int loopValue = -1;
        String searchTerm = new String();
        String[] myLastFiveSearches = {"","","","",""};
        ArrayList<String> quotes = new ArrayList<String>();
        ArrayList<String> authors = new ArrayList<String>();
        Random randomGen = new Random(915079);
        QuoteList quoteList;
        //Scanner for CMD line input
        Scanner sc = new Scanner(System.in);
        //Read XML Files
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler(){
                boolean quote = false;
                boolean author = false;

                public void startElement(String uri, String localName, String qName, Attributes atts)
                        throws SAXException
                {
                    //Debugging for start element ("<" + term + ">)
                    //System.out.println("Start Element: "+  qName);

                    if(qName.equals("quote-text")) {
                        quote = true;
                    }
                    else if(qName.equals("author")) {
                        author = true;
                    }
                }

                public void endElement(String uri, String localName, String qName)
                        throws SAXException
                {
                    //Debugging for end element ("</" + term + ">")
                    //System.out.println("End Element: " + qName);
                }

                public void characters(char chars[], int start, int length)
                    throws SAXException
                {
                    String result = new String(chars, start, length);
                    if(quote){
                        System.out.println("Quote: " + result);
                        quotes.add(result);
                        quote = false;
                    }
                    else if(author){
                        System.out.println("Author: " + new String(chars, start, length));
                        authors.add(result);
                        author = false;
                    }
                }
            };
            // Location of Quotes.xml file locally
            //saxParser.parse("C:/Users/Ethan/Desktop/GMU/2018 Spring Classes/Quotes-Testing-437/quotes.xml", handler);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        QuoteSaxParser quoteSaxParser = new QuoteSaxParser("C:/Users/Ethan/Desktop/GMU/2018 Spring Classes/Quotes-Testing-437/quotes.xml");
        quoteList = quoteSaxParser.getQuoteList();

        System.out.println("\nQuotes.size: " + quoteList.getSize());
        System.out.println("Authors.size: " + quoteList.getSize());

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
                    //Quit
                    System.out.println("\nQuitting...");
                    break;
                case 1:
                    //Term(s) in quote
                    System.out.println("\nEnter term(s) to search for in a quote: ");
                    QuoteList newList = new QuoteList();
                    break;
                case 2:
                    //Term(s) in author
                    System.out.println("\nEnter term(s) to search for in an author's name: ");

                    break;
                case 3:
                    //Term(s) in quote/author
                    System.out.println("\nEnter term(s) to search for in an author or quote: ");

                    break;
                case 4:
                    //Generate a random quote
                    System.out.println("\nGenerating a random Quote...");
                    Quote quoteTmp = quoteList.getRandomQuote();
                    System.out.println(quoteTmp.getQuoteText() + "\n\t-" + quoteTmp.getAuthor() + "\n");
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