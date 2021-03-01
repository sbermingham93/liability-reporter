package betting.report.generation.service.service;

import betting.report.generation.service.model.Bet;
import betting.report.generation.service.model.BetSelectionSummaryForCurrency;
import betting.report.generation.service.model.BetSummaryForCurrency;
import betting.report.generation.service.model.IReportingService;

import javax.inject.Singleton;
import java.util.*;

/**
 * Service class for all logic related to Preparing of Data for reports
 * Basically takes in a list of bets and produces data that is more useable in the reports
 * This data could easily be used to write to CSV or any other output method.
 * 1. Report which aggregates all input bets by each currency
 * 2. Report which aggregates all input bets by selection and currency
 */
@Singleton
public class ReportingService implements IReportingService
{
    private Map<String, BetSummaryForCurrency> betLiabilityByCurrencyMap;
    private Map<String, Map<String, BetSelectionSummaryForCurrency>> betLiabilityBySelectionByCurrencyMap;

    public List<BetSummaryForCurrency> generateBetLiabilityByCurrency(List<Bet> bets)
    {
        return this.getLiabilityByCurrencyForBets(bets);
    }

    public List<BetSelectionSummaryForCurrency> generateBetSelectionSummaryForCurrency(List<Bet> bets)
    {
        List<BetSelectionSummaryForCurrency> betSelectionSummaryForCurrencies = this.getLiabilityBySelectionByCurrencyForBets(bets);
        Collections.sort(betSelectionSummaryForCurrencies);

        return betSelectionSummaryForCurrencies;
    }

    private List<BetSelectionSummaryForCurrency> getLiabilityBySelectionByCurrencyForBets(List<Bet> bets)
    {
        this.initialiseTheLiabilitiesBySelectionByCurrencyMap();

        this.addBetsIntoLiabilitiesBySelectionByCurrencyMap(bets);

        return this.getListOfBetSelectionSummaryForCurrenciesFromMap();
    }

    private List<BetSelectionSummaryForCurrency> getListOfBetSelectionSummaryForCurrenciesFromMap()
    {
        List<BetSelectionSummaryForCurrency> betSelectionSummaryForCurrencyList = new ArrayList<>();

        for(String selectionId: this.betLiabilityBySelectionByCurrencyMap.keySet())
        {
            for(String currency: this.betLiabilityBySelectionByCurrencyMap.get(selectionId).keySet())
            {
                betSelectionSummaryForCurrencyList.add(
                        this.betLiabilityBySelectionByCurrencyMap.get(selectionId)
                                .get(currency)
                );
            }
        }

        return betSelectionSummaryForCurrencyList;
    }

    private void addBetsIntoLiabilitiesBySelectionByCurrencyMap(List<Bet> bets)
    {
        for(Bet bet: bets)
        {
            this.addBetIntoLiabilitiesBySelectionByCurrencyMap(bet);
        }
    }

    private void addBetIntoLiabilitiesBySelectionByCurrencyMap(Bet bet)
    {
        if (bet.isValidBetForLiabilityBySelectionByCurrencyReport())
        {
            this.handleAddingValidBetToLiabilityBySelectionByCurrencyMap(bet);
        }
        else
        {
            System.out.println("Not a valid bet for the liabilities by selection by currency." + bet.toString());
        }
    }

    private void handleAddingValidBetToLiabilityBySelectionByCurrencyMap(Bet bet)
    {
        if (this.betSelectionIdIsRecognisedInMap(bet))
        {
            this.addBetForRecognisedSelectionIdToMap(bet);
        }
        else
        {
            this.addBetForUnrecognisedSelectionIdToMap(bet);
        }
    }

    private void addBetForRecognisedSelectionIdToMap(Bet bet)
    {
        if(this.betSelectionIdAndCurrencyAreRecognisedInMap(bet))
        {
            this.addBetForRecognisedSelectionIdAndCurrency(bet);
        }
        else
        {
            this.addBetForRecognisedSelectionIdButNewCurrency(bet);
        }
    }

    private void addBetForRecognisedSelectionIdButNewCurrency(Bet bet)
    {
        this.betLiabilityBySelectionByCurrencyMap.get(bet.getSelectionId())
                .put(bet.getCurrency(), new BetSelectionSummaryForCurrency(bet));
    }

    private void addBetForRecognisedSelectionIdAndCurrency(Bet bet)
    {
        this.betLiabilityBySelectionByCurrencyMap.get(bet.getSelectionId())
                .get(bet.getCurrency()).updateValuesWithNewBet(bet);
    }

    private Boolean betSelectionIdAndCurrencyAreRecognisedInMap(Bet bet)
    {
        return this.betLiabilityBySelectionByCurrencyMap.get(bet.getSelectionId())
                .containsKey(bet.getCurrency());
    }

    private Boolean betSelectionIdIsRecognisedInMap(Bet bet)
    {
        return this.betLiabilityBySelectionByCurrencyMap.containsKey(bet.getSelectionId());
    }

    private void addBetForUnrecognisedSelectionIdToMap(Bet bet)
    {
        Map<String, BetSelectionSummaryForCurrency> betSelectionSummaryForCurrencyByCurrency = new HashMap<>();
        betSelectionSummaryForCurrencyByCurrency.put(bet.getCurrency(), new BetSelectionSummaryForCurrency(bet));
        this.betLiabilityBySelectionByCurrencyMap.put(bet.getSelectionId(), betSelectionSummaryForCurrencyByCurrency);
    }

    private List<BetSummaryForCurrency> getLiabilityByCurrencyForBets(List<Bet> bets)
    {
        this.initialiseTheLiabilitiesByCurrencyMap();

        this.addBetsIntoLiabilitiesByCurrencyMap(bets);

        return this.getBetSummariesForCurrenciesFromMap();
    }

    private List<BetSummaryForCurrency> getBetSummariesForCurrenciesFromMap()
    {
        List<BetSummaryForCurrency> betSummaryForCurrencies = new ArrayList<>();
        for (BetSummaryForCurrency betSummaryForCurrency: this.betLiabilityByCurrencyMap.values())
        {
            betSummaryForCurrencies.add(betSummaryForCurrency);
        }

        return betSummaryForCurrencies;
    }

    private void addBetsIntoLiabilitiesByCurrencyMap(List<Bet> bets)
    {
        for(Bet bet: bets)
        {
            this.addBetIntoLiabilitiesByCurrencyMap(bet);
        }
    }

    private void addBetIntoLiabilitiesByCurrencyMap(Bet bet)
    {
        if (bet.isValidBetForLiabilityByCurrencyReport())
        {
            if (this.isRecognisedCurrencyInMap(bet))
            {
                this.updateLiabilityForKnownCurrencyWithBet(bet);
            }
            else
            {
                this.addLiabilityForNewCurrencyWithBet(bet);
            }
        }
        else
        {
            System.out.println("Not a valid bet for the liability by currency report. " + bet.toString());
        }
    }

    private void updateLiabilityForKnownCurrencyWithBet(Bet bet)
    {
        this.betLiabilityByCurrencyMap.get(bet.getCurrency())
                .updateValuesWithNewBet(bet);
    }

    private void addLiabilityForNewCurrencyWithBet(Bet bet)
    {
        this.betLiabilityByCurrencyMap.put(
                bet.getCurrency(), new BetSummaryForCurrency(bet)
        );
    }

    private Boolean isRecognisedCurrencyInMap(Bet bet)
    {
        return this.betLiabilityByCurrencyMap.containsKey(
                bet.getCurrency()
        );
    }

    private void initialiseTheLiabilitiesByCurrencyMap()
    {
        this.betLiabilityByCurrencyMap = new HashMap<>();
    }

    private void initialiseTheLiabilitiesBySelectionByCurrencyMap()
    {
        this.betLiabilityBySelectionByCurrencyMap = new HashMap<>();
    }
}
