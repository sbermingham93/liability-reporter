package betting.report.generation.service.service;

import betting.report.generation.service.constants.FileConstants;
import betting.report.generation.service.model.BetSelectionSummaryForCurrency;
import betting.report.generation.service.model.BetSummaryForCurrency;

import javax.inject.Singleton;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;

@Singleton
public class CsvOutputService
{
    public void writeSummaryByCurrencyReportToCsv(List<BetSummaryForCurrency> betSummaryForCurrencies, String destinationFile)
    {
        File csvOutputFile = new File(destinationFile);
        try(PrintWriter printWriter = new PrintWriter(csvOutputFile))
        {
            printWriter.println(this.getSummaryByCurrencyHeadingsForCsv());
            for(BetSummaryForCurrency betSummaryForCurrency: betSummaryForCurrencies)
            {
                printWriter.println(this.convertSummaryByCurrencyToCsvRow(betSummaryForCurrency));
            }
        }
        catch (Exception e)
        {
            System.out.println("There was an error writing the summary by currency report to the CSV file.");
            e.printStackTrace();
        }
    }

    public void writeSelectionSummaryByCurrencyReportToCsv(List<BetSelectionSummaryForCurrency> betSelectionSummaryForCurrencies, String destinationFile)
    {
        File csvOutputFile = new File(destinationFile);
        try(PrintWriter printWriter = new PrintWriter(csvOutputFile))
        {
            printWriter.println(this.getSelectionSummaryByCurrencyHeadingsForCsv());
            for(BetSelectionSummaryForCurrency betSelectionSummaryForCurrency: betSelectionSummaryForCurrencies)
            {
                printWriter.println(this.convertBetSelectionSummaryByCurrencyToCsvRow(betSelectionSummaryForCurrency));
            }
        }
        catch (Exception e)
        {
            System.out.println("There was an error writing the summary by currency report to the CSV file.");
            e.printStackTrace();
        }
    }

    private String getHeadingStringFromArray(String[] headings)
    {
        String headingsString = "";
        for(String heading: headings)
        {
            headingsString += heading + ",";
        }

        return this.removeFinalCommaFromString(headingsString);
    }

    private String getSummaryByCurrencyHeadingsForCsv()
    {
        return this.getHeadingStringFromArray(FileConstants.currencyLiabilityReportHeadings);
    }

    private String getSelectionSummaryByCurrencyHeadingsForCsv()
    {
        return this.getHeadingStringFromArray(FileConstants.selectionAndCurrencyLiabilityReportHeadings);
    }

    private String removeFinalCommaFromString(String text)
    {
        if (!text.isEmpty() && text.charAt(text.length() - 1) == ',')
        {
            text = text.substring(0, text.length() - 1);
        }

        return text;
    }

    private String convertSummaryByCurrencyToCsvRow(BetSummaryForCurrency betSummaryForCurrency)
    {
        return betSummaryForCurrency.getCurrency() + "," + betSummaryForCurrency.getNumberOfBets()
                + "," + betSummaryForCurrency.getTotalStakesWithCurrencySymbol()
                + "," + betSummaryForCurrency.getTotalLiabilityWithCurrencySymbol();
    }

    private String convertBetSelectionSummaryByCurrencyToCsvRow(BetSelectionSummaryForCurrency betSelectionSummaryForCurrency)
    {
        return betSelectionSummaryForCurrency.getSelectionName() + "," + betSelectionSummaryForCurrency.getCurrency()
                + "," + betSelectionSummaryForCurrency.getNumberOfBets() + "," + betSelectionSummaryForCurrency.getTotalStakesWithCurrencySymbol()
                + "," + betSelectionSummaryForCurrency.getTotalLiabilityWithCurrencySymbol();
    }
}
