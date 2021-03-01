package betting.report.generation.service.model;

import betting.report.generation.service.util.CurrencyUtils;

public class BetSummaryForCurrency
{
    private String currency;
    private Integer numberOfBets;
    private Double totalStakes;
    private Double totalLiability;

    public BetSummaryForCurrency(Bet bet)
    {
        this.currency = bet.getCurrency();
        this.numberOfBets = 1;
        this.totalStakes = bet.getStake();
        this.totalLiability = bet.getStake() * bet.getPrice();
    }

    public BetSummaryForCurrency(String currency, Integer numberOfBets, Double totalStakes, Double totalLiability)
    {
        this.currency = currency;
        this.numberOfBets = numberOfBets;
        this.totalStakes = totalStakes;
        this.totalLiability = totalLiability;
    }

    public void updateValuesWithNewBet(Bet bet)
    {
        this.numberOfBets += 1;
        this.totalStakes += bet.getStake();
        this.totalLiability += bet.getStake() * bet.getPrice();
    }

    public String getTotalStakesWithCurrencySymbol()
    {
        return CurrencyUtils.formatCurrencyFromDouble(
                this.getCurrency(),
                this.getTotalStakes()
        );
    }

    public String getTotalLiabilityWithCurrencySymbol()
    {
        return CurrencyUtils.formatCurrencyFromDouble(
                this.getCurrency(),
                this.getTotalLiability()
        );
    }

    public Double getTotalStakes()
    {
        return totalStakes;
    }

    public void setTotalStakes(Double totalStakes)
    {
        this.totalStakes = totalStakes;
    }

    public Double getTotalLiability()
    {
        return totalLiability;
    }

    public void setTotalLiability(Double totalLiability)
    {
        this.totalLiability = totalLiability;
    }


    public Integer getNumberOfBets() {
        return this.numberOfBets;
    }

    public void setNumberOfBets(Integer numberOfBets)
    {
        this.numberOfBets = numberOfBets;
    }

    public String getCurrency()
    {
        return this.currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }
}
