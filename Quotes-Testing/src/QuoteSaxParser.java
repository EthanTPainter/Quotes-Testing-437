//package quotes;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

/**
 * SAX parser
 * @author Mongkoldech Rajapakdee & Jeff Offutt
 *         Date: Nov 2009
 */
public class QuoteSaxParser
{
   private QuoteSaxHandler handler;

   public QuoteSaxParser (String fileName)
   {
      try
      {
         File quoteFile = new File (fileName);

         handler = new QuoteSaxHandler();
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser =  factory.newSAXParser();
         saxParser.parse (quoteFile, handler);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   public QuoteList getQuoteList()
   {
      return handler.getQuoteList();
   }
}