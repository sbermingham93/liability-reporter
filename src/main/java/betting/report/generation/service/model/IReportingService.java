package betting.report.generation.service.model;

import java.util.List;

public interface IReportingService
{
    List<BetSummaryForCurrency> generateBetLiabilityByCurrency(List<Bet> bets);
    List<BetSelectionSummaryForCurrency> generateBetSelectionSummaryForCurrency(List<Bet> bets);
}
