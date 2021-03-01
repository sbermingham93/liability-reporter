package betting.report.generation.service.service;

import betting.report.generation.service.constants.CurrencyConstants;
import betting.report.generation.service.model.Bet;
import betting.report.generation.service.model.BetSelectionSummaryForCurrency;
import betting.report.generation.service.model.BetSummaryForCurrency;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class ReportingServiceTest
{
    private ReportingService reportingService;
    private List<Bet> mockBets;

    @BeforeAll
    public void setup() throws Exception
    {
        reportingService = new ReportingService();
        this.setMockBets();
    }

    @Test
    @DisplayName("It should generate a List of summary liability by currency")
    public void itShouldGenerateSummaryLiabilityByCurrencyForValidBets()
    {
        List<BetSummaryForCurrency> betSummaryForCurrencyList = reportingService.generateBetLiabilityByCurrency(this.mockBets);
        Assertions.assertTrue(betSummaryForCurrencyList != null, "Expected the bet summary for currency not to be null.");
        Assertions.assertFalse(betSummaryForCurrencyList.isEmpty(), "Expected there to be bet summary for currency records generated as valid bets passed in.");
    }

    @Test
    @DisplayName("It should generate summary liability by currency and selection")
    public void itShouldGenerateSummaryLiabilityByCurrencyAndSelectionForValidBets()
    {
        List<BetSelectionSummaryForCurrency> betSelectionSummaryForCurrencies = reportingService.generateBetSelectionSummaryForCurrency(this.mockBets);
        Assertions.assertTrue(betSelectionSummaryForCurrencies != null, "Expected the bet summary for currency and selection Id not to be null.");
        Assertions.assertFalse(betSelectionSummaryForCurrencies.isEmpty(), "Expected there to be bet summary for currency and selection id records generated as valid bets passed in.");
    }

    @Test
    @DisplayName("It should generate summary liability by currency and selection for empty array of bets")
    public void itShouldGenerateSummaryLiabilityByCurrencyAndSelectionForEmptyBets()
    {
        List<BetSelectionSummaryForCurrency> betSelectionSummaryForCurrencies = reportingService.generateBetSelectionSummaryForCurrency(new ArrayList<>());
        Assertions.assertTrue(betSelectionSummaryForCurrencies.isEmpty(), "Expected there to be no summary information as empty array of bets passed.");
    }

    @Test
    @DisplayName("It should generate summary liability by currency for empty array of bets")
    public void itShouldGenerateSummaryLiabilityByCurrencyForEmptyBets()
    {
        List<BetSummaryForCurrency> betSummaryForCurrencies = reportingService.generateBetLiabilityByCurrency(new ArrayList<>());
        Assertions.assertTrue(betSummaryForCurrencies.isEmpty(), "Expected there to be no summary information as empty array of bets passed.");
    }

    private void setMockBets()
    {
        this.mockBets = new ArrayList<>();

        this.addMockBetsForCurrency(CurrencyConstants.EURO);
        this.addMockBetsForCurrency(CurrencyConstants.GBP);
        this.addMockBetsForCurrencyAndSelectionId(CurrencyConstants.GBP, "Selection-1");
        this.addMockBetsForCurrencyAndSelectionId(CurrencyConstants.EURO, "Selection-2");
    }

    private void addMockBetsForCurrency(String currency)
    {
        for(Integer i = 0; i < 5; i++)
        {
            String betId = "Bet-" + i;
            String betTimestamp = new Date().toInstant().toString();
            String selectionId = "Selection-" + i;
            String selectionName = "Selection "+ i;
            Double price = 2.0 + i;
            Double stake = 2.0 + i;

            this.mockBets.add(new Bet(betId, betTimestamp, selectionId, selectionName, stake, price, currency));
        }
    }

    private void addMockBetsForCurrencyAndSelectionId(String currency, String selectionId)
    {
        for(Integer i = 0; i < 5; i++)
        {
            String betId = "Bet-" + i;
            String betTimestamp = new Date().toInstant().toString();
            String selectionName = "Selection "+ i;
            Double price = 2.0 + i;
            Double stake = 2.0 + i;

            this.mockBets.add(new Bet(betId, betTimestamp, selectionId, selectionName, stake, price, currency));
        }
    }
}
