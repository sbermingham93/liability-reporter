package betting.report.generation.service.service;

import betting.report.generation.service.constants.CurrencyConstants;
import betting.report.generation.service.constants.FileConstants;
import betting.report.generation.service.model.BetSelectionSummaryForCurrency;
import betting.report.generation.service.model.BetSummaryForCurrency;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class OutputServiceTest
{
    private OutputService outputService;
    private List<BetSummaryForCurrency> betSummaryForCurrencies;
    private List<BetSelectionSummaryForCurrency> betSelectionSummaryForCurrencies;

    // Used to verify the console output
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    // Used to verify the CSVs have been created
    private String relativeFilePath;
    private String summaryByCurrencyFileLocation;
    private String summaryBySelectionAndCurrencyFileLocation;

    @BeforeAll
    public void setup() throws Exception
    {
        this.outputService = new OutputService();
        this.setMockData();
        this.initLogs();
        this.relativeFilePath = new File("").getAbsolutePath();
        this.summaryByCurrencyFileLocation = this.relativeFilePath.concat("/files/output/TestLiabilityByCurrency.csv");
        this.summaryBySelectionAndCurrencyFileLocation = this.relativeFilePath.concat("/files/output/TestLiabilityBySelectionAndCurrency.csv");
    }

    @AfterAll
    public void cleanUp()
    {
        this.cleanUpLogs();
        this.deleteTestCsvFiles();
    }

    @Test
    @DisplayName("It should output the liability summary by currency table to the console")
    public void testLiabilitySummaryByCurrencyToConsole()
    {
        this.outputService.summaryLiabilityByCurrencyToConsole(this.betSummaryForCurrencies);
        Assertions.assertTrue(outContent.toString().contains(FileConstants.currencyLiabilityReportHeadings[0]),
                "Expected to see the currency liability report headings in the console.");
    }

    @Test
    @DisplayName("It should output the liability selection summary by currency table to the console")
    public void testLiabilitySelectionSummaryByCurrencyToConsole()
    {
        this.outputService.summaryLiabilityBySelectionByCurrencyToConsole(this.betSelectionSummaryForCurrencies);
        Assertions.assertTrue(outContent.toString().contains(FileConstants.selectionAndCurrencyLiabilityReportHeadings[0]),
                "Expected to see the selection and currency liability report headings in the console.");
    }

    @Test
    @DisplayName("It should output the liability selection summary by currency to csv file")
    public void testLiabilityBySelectionAndCurrencyToCsv()
    {
        this.outputService.summaryLiabilityBySelectionAndCurrencyToCsv(this.betSelectionSummaryForCurrencies, this.summaryBySelectionAndCurrencyFileLocation);

        File csvCreated = new File(this.summaryBySelectionAndCurrencyFileLocation);

        Assertions.assertTrue(csvCreated.exists(),
                "Expected the CSV to be created but the file doesnt exist.");
    }

    @Test
    @DisplayName("It should output the liability summary by currency to csv file")
    public void testLiabilityByCurrencyToCsv()
    {
        this.outputService.summaryLiabilityByCurrencyToCsv(this.betSummaryForCurrencies, this.summaryByCurrencyFileLocation);

        File csvCreated = new File(this.summaryByCurrencyFileLocation);

        Assertions.assertTrue(csvCreated.exists(),
                "Expected the CSV to be created but the file doesnt exist.");
    }

    private void setMockData()
    {
        this.mockBetSummaryForCurrencies();
        this.mockBetSelectionSummaryForCurrencies();
    }

    private void mockBetSummaryForCurrencies()
    {
        this.betSummaryForCurrencies = new ArrayList<>();
        this.betSummaryForCurrencies.add(new BetSummaryForCurrency(CurrencyConstants.EURO, 5, 20.0, 200.0));
        this.betSummaryForCurrencies.add(new BetSummaryForCurrency(CurrencyConstants.GBP, 3, 50.0, 400.0));
    }

    private void mockBetSelectionSummaryForCurrencies()
    {
        this.betSelectionSummaryForCurrencies = new ArrayList<>();
        this.betSelectionSummaryForCurrencies.add(new BetSelectionSummaryForCurrency(CurrencyConstants.EURO, 5, 20.0, 200.0, "selection-1"));
        this.betSelectionSummaryForCurrencies.add(new BetSelectionSummaryForCurrency(CurrencyConstants.EURO, 5, 20.0, 240.0, "selection-2"));
        this.betSelectionSummaryForCurrencies.add(new BetSelectionSummaryForCurrency(CurrencyConstants.GBP, 5, 20.0, 150.0, "selection-1"));
        this.betSelectionSummaryForCurrencies.add(new BetSelectionSummaryForCurrency(CurrencyConstants.GBP, 5, 20.0, 200.0, "selection-2"));
    }

    private void initLogs()
    {
        System.setOut(new PrintStream(outContent));
    }

    private void cleanUpLogs()
    {
        System.setOut(originalOut);
    }

    private void deleteTestCsvFiles()
    {
        try
        {
            File liabilityBySelectionAndCurrencyCsv = new File(this.summaryBySelectionAndCurrencyFileLocation);
            File liabilityByCurrencyCsv = new File(this.summaryByCurrencyFileLocation);

            this.deleteFileIfExists(liabilityBySelectionAndCurrencyCsv);
            this.deleteFileIfExists(liabilityByCurrencyCsv);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void deleteFileIfExists(File file)
    {
        if (file.exists())
        {
            file.delete();
        }
    }
}
