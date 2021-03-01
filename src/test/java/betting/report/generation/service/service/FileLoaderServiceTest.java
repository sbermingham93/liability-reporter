package betting.report.generation.service.service;

import betting.report.generation.service.constants.FileConstants;
import betting.report.generation.service.model.Bet;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class FileLoaderServiceTest
{
    private FileLoaderService fileLoaderService;

    @BeforeAll
    public void setup() throws Exception
    {
        fileLoaderService = new FileLoaderService();
    }

    @Test
    @DisplayName("It should read in a Json file of bets")
    public void testReadInJsonFile()
    {
        Optional<List<Bet>> bets = fileLoaderService.getBetsFromFileLocation(FileConstants.json);
        Assertions.assertTrue(bets.isPresent(), "Expected there to be Bets returned from the Json file.");
        Assertions.assertTrue(!bets.get().isEmpty(), "Expected there to be Bets returned from the Json file.");
    }

    @Test
    @DisplayName("It should read in a csv file of bets")
    public void testReadInCsvFile()
    {
        Optional<List<Bet>> bets = fileLoaderService.getBetsFromFileLocation(FileConstants.csv);
        Assertions.assertTrue(bets.isPresent(), "Expected there to be Bets returned from the CSV file.");
        Assertions.assertTrue(!bets.get().isEmpty(), "Expected there to be Bets returned from the CSV file.");
    }

    @Test
    @DisplayName("It should not read in an un-recognised file type")
    public void testReadInJsFile()
    {
        Optional<List<Bet>> bets = fileLoaderService.getBetsFromFileLocation("js");
        Assertions.assertTrue(bets.isEmpty(), "Expected no bets to come back for a Javascript file (un-supported)");
    }
}
