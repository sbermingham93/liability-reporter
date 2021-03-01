package betting.report.generation.service.service;

import betting.report.generation.service.constants.FileConstants;
import betting.report.generation.service.model.BetSelectionSummaryForCurrency;
import betting.report.generation.service.model.BetSummaryForCurrency;
import betting.report.generation.service.model.ReportOutputConfiguration;
import betting.report.generation.service.model.IOutputService;

import javax.inject.Singleton;
import java.util.List;

/**
 * Service class for all logic related to Outputting Of Data
 * Liability reports to CSV and Console
 */

@Singleton
public class OutputService implements IOutputService
{
    private CsvOutputService csvOutputService;

    public OutputService()
    {
        this.csvOutputService = new CsvOutputService();
    }

    public void outputLiabilityReports(ReportOutputConfiguration reportOutputConfiguration, List<BetSummaryForCurrency> betSummaryForCurrencies, List<BetSelectionSummaryForCurrency> betSelectionSummaryForCurrencies)
    {
        switch(reportOutputConfiguration.getType())
        {
            case FileConstants.console:
                this.outputLiabilityReportsToTheConsole(betSummaryForCurrencies, betSelectionSummaryForCurrencies);
                break;
            case FileConstants.csv:
                this.outputLiabilityReportsToCSVFiles(reportOutputConfiguration, betSummaryForCurrencies, betSelectionSummaryForCurrencies);
                break;
            default:
                System.out.println("Not a recognised output file type....");
        }
    }

    private void outputLiabilityReportsToTheConsole(List<BetSummaryForCurrency> betSummaryForCurrencies, List<BetSelectionSummaryForCurrency> betSelectionSummaryForCurrencies)
    {
        this.summaryLiabilityByCurrencyToConsole(betSummaryForCurrencies);
        this.summaryLiabilityBySelectionByCurrencyToConsole(betSelectionSummaryForCurrencies);
    }

    private void outputLiabilityReportsToCSVFiles(ReportOutputConfiguration reportOutputConfiguration, List<BetSummaryForCurrency> betSummaryForCurrencies, List<BetSelectionSummaryForCurrency> betSelectionSummaryForCurrencies)
    {
        this.summaryLiabilityByCurrencyToCsv(betSummaryForCurrencies, reportOutputConfiguration.getLiabilityByCurrencyCsvLocation());
        this.summaryLiabilityBySelectionAndCurrencyToCsv(betSelectionSummaryForCurrencies, reportOutputConfiguration.getLiabilityBySelectionAndCurrencyCsvLocation());

    }

    /**
     * Takes a list of bet summaries for each currency and logs it to the console
     */
    public void summaryLiabilityByCurrencyToConsole(List<BetSummaryForCurrency> betSummaryForCurrencies)
    {
        TableService.outputCurrencyLiabilityTable(betSummaryForCurrencies);
    }

    /**
     * Takes a list of bet summaries for each currency and selection, logs each of them to the console
     */
    public void summaryLiabilityBySelectionByCurrencyToConsole(List<BetSelectionSummaryForCurrency> betSelectionSummaryForCurrencies)
    {
        TableService.outputSelectionCurrencyLiabilityTable(betSelectionSummaryForCurrencies);
    }

    public void summaryLiabilityByCurrencyToCsv(List<BetSummaryForCurrency> betSummaryForCurrencies, String fileLocation)
    {
        this.csvOutputService.writeSummaryByCurrencyReportToCsv(betSummaryForCurrencies, fileLocation);

        System.out.println("The Liability By Currency is available here: " + fileLocation);
    }

    public void summaryLiabilityBySelectionAndCurrencyToCsv(List<BetSelectionSummaryForCurrency> betSelectionSummaryForCurrencies, String fileLocation)
    {
        this.csvOutputService.writeSelectionSummaryByCurrencyReportToCsv(betSelectionSummaryForCurrencies, fileLocation);

        System.out.println("The Liability By Selection And Currency is available here: " + fileLocation);
    }
}
