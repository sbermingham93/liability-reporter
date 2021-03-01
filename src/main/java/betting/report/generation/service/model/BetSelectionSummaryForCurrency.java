package betting.report.generation.service.model;

public class BetSelectionSummaryForCurrency extends BetSummaryForCurrency implements Comparable<BetSelectionSummaryForCurrency>
{
    private String selectionName;

    public BetSelectionSummaryForCurrency(Bet bet)
    {
        super(bet);

        this.selectionName = bet.getSelectionName();
    }

    public BetSelectionSummaryForCurrency(String currency, Integer numberOfBets, Double totalStakes, Double totalLiability, String selectionName)
    {
        super(currency, numberOfBets, totalStakes, totalLiability);

        this.selectionName = selectionName;
    }

    public String getSelectionName() {
        return selectionName;
    }

    @Override
    public int compareTo(BetSelectionSummaryForCurrency betSelectionSummaryForCurrency) {
        Integer currencyComparison = betSelectionSummaryForCurrency.getCurrency().compareTo(this.getCurrency());

        return (currencyComparison != 0) ?
                currencyComparison :
                betSelectionSummaryForCurrency.getTotalLiability().compareTo(this.getTotalLiability());
    }
}
