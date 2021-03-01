package betting.report.generation.service.model;

import java.util.List;

public interface IOutputService
{
    void summaryLiabilityByCurrencyToConsole(List<BetSummaryForCurrency> betSummaryForCurrencies);
    void summaryLiabilityBySelectionByCurrencyToConsole(List<BetSelectionSummaryForCurrency> betSelectionSummaryForCurrencies);
}
