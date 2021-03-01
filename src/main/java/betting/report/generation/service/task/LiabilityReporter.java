package betting.report.generation.service.task;

import betting.report.generation.service.constants.FileConstants;
import betting.report.generation.service.model.Bet;
import betting.report.generation.service.model.BetSelectionSummaryForCurrency;
import betting.report.generation.service.model.BetSummaryForCurrency;
import betting.report.generation.service.model.ReportOutputConfiguration;
import betting.report.generation.service.service.FileLoaderService;
import betting.report.generation.service.service.OutputService;
import betting.report.generation.service.service.ReportingService;

import java.io.File;
import java.util.*;

public class LiabilityReporter
{
    private Map<String, String> userInputsByName;
    private ReportingService reportingService;
    private FileLoaderService fileLoaderService;
    private OutputService outputService;
    private Optional<List<Bet>> bettingData;
    private Optional<List<BetSummaryForCurrency>> liabilitySummaryByCurrencies;
    private Optional <List<BetSelectionSummaryForCurrency>> liabilitySummaryBySelectionAndCurrencies;

    public LiabilityReporter()
    {
        this.fileLoaderService = new FileLoaderService();
        this.reportingService = new ReportingService();
        this.outputService = new OutputService();
    }

    public void handleReporting()
    {
        this.getUserInputsForFileInputsAndOutputs();
        this.readInTheBettingDataAndOutputTheReports();
    }

    private void readInTheBettingDataAndOutputTheReports()
    {
        this.readInTheBettingData();
        this.formatTheBettingData();
        this.outputTheReports();
    }

    private void readInTheBettingData()
    {
        this.bettingData = this.fileLoaderService.getBetsFromFileLocation(this.userInputsByName.get(FileConstants.inputType));
    }

    private void formatTheBettingData()
    {
        if (this.bettingData.isPresent())
        {
            List<Bet> bets = this.bettingData.get();

            this.liabilitySummaryByCurrencies = Optional.of(this.reportingService.generateBetLiabilityByCurrency(bets));
            this.liabilitySummaryBySelectionAndCurrencies = Optional.of(this.reportingService.generateBetSelectionSummaryForCurrency(bets));
        }
        else
        {
            System.out.println("There was an issue retrieving the bets from the file location. Sorry!");

            this.liabilitySummaryByCurrencies = Optional.empty();
            this.liabilitySummaryBySelectionAndCurrencies = Optional.empty();
        }
    }

    private void outputTheReports()
    {
        if (this.hasValidLiabilitySummaryDataForReports())
        {
            this.handleOutputtingTheReports();
        }
        else
        {
            System.out.println("There was an issue aggregating the betting data. Please try again...");
        }
    }

    private void handleOutputtingTheReports()
    {
        String baseFilePath = new File("").getAbsolutePath();
        String liabilityByCurrencyCsvPath = baseFilePath.concat(FileConstants.liabilityByCurrencyCsvOutputLocation);
        String liabilityBySelectionAndCurrencyCsvPath = baseFilePath.concat(FileConstants.liabilityBySelectionAndCurrencyCsvOutputLocation);

        this.outputService.outputLiabilityReports(
                new ReportOutputConfiguration(this.userInputsByName.get(FileConstants.outputType), liabilityByCurrencyCsvPath, liabilityBySelectionAndCurrencyCsvPath),
                this.liabilitySummaryByCurrencies.get(),
                this.liabilitySummaryBySelectionAndCurrencies.get()
        );
    }

    private Boolean hasValidLiabilitySummaryDataForReports()
    {
        return this.liabilitySummaryBySelectionAndCurrencies.isPresent() && this.liabilitySummaryByCurrencies.isPresent();
    }

    private void getUserInputsForFileInputsAndOutputs()
    {
        this.welcomeTheUser();
        this.setDefaultUserInputsByName();
        this.readInUserInputsFromScanner();
    }

    private void welcomeTheUser()
    {
        System.out.println("----- Welcome to the Value Finder -----");
    }

    private void readInUserInputsFromScanner()
    {
        try
        {
            Scanner scan = new Scanner(System.in);
            System.out.println("Please enter the type of data to report on [json, csv] (default csv)");
            String inputType = scan.nextLine();
            this.handleAddingUserRequestedInputType(inputType);

            System.out.println("Please enter the output format you would like: [console, csv] (default console)");
            String outputType = scan.nextLine();
            this.handleAddingUserRequestedOutputType(outputType);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void handleAddingUserRequestedInputType(String inputType)
    {
        if (this.isValidInputType(inputType))
        {
            this.userInputsByName.put(FileConstants.inputType, inputType);
        }
        else
        {
            System.out.println("Not a recognised input file type, going with the default of " + FileConstants.defualtInputType);
        }
    }

    private void handleAddingUserRequestedOutputType(String outputType)
    {
        if (this.isValidOutputType(outputType))
        {
            this.userInputsByName.put(FileConstants.outputType, outputType);
        }
        else
        {
            System.out.println("Not a recognised output file type, going with the default of " + FileConstants.defualtOutputType);
        }
    }

    private void setDefaultUserInputsByName()
    {
        this.userInputsByName = new HashMap<>();
        this.userInputsByName.put(FileConstants.inputType, FileConstants.csv);
        this.userInputsByName.put(FileConstants.outputType, FileConstants.console);
    }

    private Boolean isValidInputType(String type)
    {
        return FileConstants.allowedInputFileTypes.contains(type.trim().toLowerCase());
    }

    private Boolean isValidOutputType(String type)
    {
        return FileConstants.allowedOutputFileTypes.contains(type.trim().toLowerCase());
    }
}
