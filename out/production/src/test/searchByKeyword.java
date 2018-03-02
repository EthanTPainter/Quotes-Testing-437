import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class searchByKeyword {

    private static QuoteList quoteList;
    private static QuoteList keywordResult;

    @BeforeAll
    public static void setUp(){
        //set up quoteLists
        quoteList = new QuoteList();
        keywordResult = new QuoteList();
    }

    @Test
    public void keywordSearchTest_1(){
        String keyword = "technology";

        quoteList.setQuote(new Quote("technology is fun"));
        keywordResult = keywordResult.searchByKeyword("technology is fun", quoteList);
        assertTrue(keywordResult.getQuote(0).getQuoteText().contains(keyword));
    }

    @Test
    public void keywordSearchTest_2()
    {
        String keyword = "technology";

        quoteList.setQuote(new Quote("technology is fun"));
        quoteList.setQuote(new Quote("technology is the devil"));
        keywordResult = keywordResult.searchByKeyword(keyword, quoteList);

        assertTrue(keywordResult.getQuote(0).getQuoteText().contains(keyword));
        assertTrue(keywordResult.getQuote(1).getQuoteText().contains(keyword));
    }
}
