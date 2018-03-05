import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class searchByKeyword {

    private static QuoteList quoteList;
    private static String keyword;
    private static String result;

    @BeforeEach
    public void setUp(){
        //set up quoteLists
        quoteList = new QuoteList();
        result = new String();
        keyword = "technology";
    }

    @Test
    public void keywordSearchTest_1() throws Exception
    {
        quoteList.setQuote(new Quote("technology is fun"));
        String result = quoteList.searchByKeyword("technology is fun", quoteList);

        assertTrue(result.contains(keyword));
        assertTrue(result.equals(quoteList.getQuote(0).toString()+"\n"));
    }

    @Test
    public void keywordSearchTest_2() throws Exception
    {
        quoteList.setQuote(new Quote("technology is fun"));
        quoteList.setQuote(new Quote("technology is the devil"));
        result = quoteList.searchByKeyword(keyword, quoteList);

        assertTrue(result.contains(keyword));
        assertTrue(result.contains(quoteList.getQuote(0).toString()));
        assertTrue(result.contains(quoteList.getQuote(1).toString()));
    }

    @Test
    public void keywordSearchTest_3() throws Exception
    {
        keyword = "";
        quoteList.setQuote(new Quote("technology is fun"));
        quoteList.setQuote(new Quote("technology is the devil"));
        Assertions.assertThrows(NoSuchFieldException.class,()->
                quoteList.searchByKeyword(keyword, quoteList));
    }

    @Test
    public void keywordSearchTest_4() throws Exception
    {
        quoteList.setQuote(new Quote("Knitting is fun"));
        quoteList.setQuote(new Quote("Kitesurfing is the devil"));

        String result = quoteList.searchByKeyword(keyword, quoteList);
        assertTrue(result.equals("No quotes found with keyword: "+keyword));
    }
}
