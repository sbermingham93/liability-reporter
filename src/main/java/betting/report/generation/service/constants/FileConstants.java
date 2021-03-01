package betting.report.generation.service.constants;

import java.util.ArrayList;
import java.util.List;

public class FileConstants
{
    public final static String csv = "csv";
    public final static String json = "json";
    public final static String type = "type";
    public final static String inputType = "inputType";
    public final static String defualtInputType = csv;
    public final static String outputType = "outputType";
    public final static String console = "console";
    public final static String defualtOutputType = console;

    public final static List<String> allowedInputFileTypes = new ArrayList<>(List.of("csv", "json"));
    public final static List<String> allowedOutputFileTypes = new ArrayList<>(List.of("console", "csv", "json"));

    public final static String[] currencyLiabilityReportHeadings = new String[]{"Currency", "No Of Bets", "Total Stakes", "Total Liability"};
    public final static String[] selectionAndCurrencyLiabilityReportHeadings = new String[]{"Selection Name", "Currency", "Num Bets", "Total Stakes", "Total Liability"};

    public final static String liabilityByCurrencyCsvOutputLocation = "/files/output/LiabilityByCurrency.csv";
    public final static String liabilityBySelectionAndCurrencyCsvOutputLocation = "/files/output/LiabilityBySelectionAndCurrency.csv";
}
