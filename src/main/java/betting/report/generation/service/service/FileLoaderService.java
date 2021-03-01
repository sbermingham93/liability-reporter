package betting.report.generation.service.service;

import betting.report.generation.service.constants.BetConstants;
import betting.report.generation.service.constants.FileConstants;
import betting.report.generation.service.model.Bet;
import betting.report.generation.service.model.IFileLoaderService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.inject.Singleton;
import java.io.*;
import java.util.*;

/**
 * Service class for all logic related to Loading in and Parsing Files
 * The class finds out which files it needs to read in from FilesToReportOn config object
 */

@Singleton
public class FileLoaderService implements IFileLoaderService
{
    private String relativePath;

    public FileLoaderService()
    {
        this.relativePath = new File("").getAbsolutePath();
    }

    /**
     * Go to each of the file locations and read in those files
     * We parse from the row of the csv file to instance of the Bet class
     * We use a Map to keep track of the Bets that were in each of the file locations
     */
    public Optional<List<Bet>> getBetsFromFileLocation(String fileType)
    {
        switch(fileType)
        {
            case FileConstants.json:
                return this.readBetsFromJsonFile(this.relativePath.concat("/files/input/book.json"));
            case FileConstants.csv:
                return this.readAndFormatBettingCsvIntoBets(this.relativePath.concat("/files/input/book.csv"));
            default:
                return Optional.empty();
        }
    }

    private Optional<List<Bet>> readBetsFromJsonFile(String fileLocation)
    {
        JSONParser jsonParser = new JSONParser();

        try
        {
            FileReader reader = new FileReader(fileLocation);
            Object obj = jsonParser.parse(reader);

            JSONArray bets = (JSONArray) obj;

            List<Bet> betsFromJson = new ArrayList<>();
            bets.forEach( jsonBet ->
                    parseBetObject( (JSONObject) jsonBet ).ifPresent(
                            bet -> betsFromJson.add(bet)
                    )
            );

            return Optional.of(betsFromJson);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private Optional<Bet> parseBetObject(JSONObject bet)
    {
        try
        {
            Bet betFromJson = this.createBetFromJsonObject(bet);

            return Optional.of(betFromJson);
        }
        catch (Exception e)
        {
            System.out.println("There was an exception reading in the bet");
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Go to the file location
     * Using a BR go through each of the lines of the CSV (first is the headings)
     * Create an instance of the Bet for each line (not heading) and add to List to return
     */
    private Optional<List<Bet>> readAndFormatBettingCsvIntoBets(String fileLocation)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(fileLocation)))
        {
            List<Bet> betsInFile = new ArrayList<>();
            String headingLine = br.readLine();

            // remove any spaces as space affects the map keys
            String[] headings = headingLine.replace(" ", "").split(",");
            String row;

            while ((row = br.readLine()) != null)
            {
                String[] rowValues = row.split(",");

                if(rowValues.length == headings.length)
                {
                    this.createBetFromCSVValues(headings, rowValues).ifPresent(bet->betsInFile.add(bet));
                }
                else
                {
                    System.out.println("There were a different number of headings and values, no not creating Bet from values.");
                }
            }
            return Optional.of(betsInFile);
        }
        catch (Exception e)
        {
            System.out.println("Error when reading and formatting the values from the CSV. ");
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Returns a bet unless there's an issue with the incoming CSV values
     */
    private Optional<Bet> createBetFromCSVValues(String[] fieldNames, String[] values)
    {
        try
        {
            Bet bet = new Bet(this.getValuesByColumnName(fieldNames, values));

            return Optional.of(bet);
        }
        catch(Exception e)
        {
            System.out.println("There was an error while creating a Bet instance from the CSV values.");
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private Map<String, String> getValuesByColumnName(String[] fieldNames, String[] values)
    {
        Map<String, String> valuesByColumnName = new HashMap<>();
        for (Integer i = 0; i < fieldNames.length; i++)
        {
            valuesByColumnName.put(fieldNames[i], values[i]);
        }

        return valuesByColumnName;
    }

    // Clashes with the other Bet constructor accepting a map...
    private Bet createBetFromJsonObject(JSONObject jsonBet)
    {
        Bet bet = new Bet();
        bet.setBetTimestamp((String) jsonBet.get(BetConstants.betTimestamp));
        bet.setBetId((String) jsonBet.get(BetConstants.betId));
        bet.setCurrency((String) jsonBet.get(BetConstants.currency));
        bet.setPrice((Double) jsonBet.get(BetConstants.price));
        bet.setStake((Double) jsonBet.get(BetConstants.stake));
        bet.setSelectionName((String) jsonBet.get(BetConstants.selectionName));
        bet.setSelectionId((String) jsonBet.get(BetConstants.selectionId));

        return bet;
    }
}
