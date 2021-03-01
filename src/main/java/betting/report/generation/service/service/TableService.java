package betting.report.generation.service.service;

import betting.report.generation.service.constants.FileConstants;
import betting.report.generation.service.model.BetSelectionSummaryForCurrency;
import betting.report.generation.service.model.BetSummaryForCurrency;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TableService
{
    public static void outputCurrencyLiabilityTable(List<BetSummaryForCurrency> betSummaryForCurrencies)
    {
        tableWithLines(formatTableDataForLiabilityByCurrency(betSummaryForCurrencies));
    }

    public static void outputSelectionCurrencyLiabilityTable(List<BetSelectionSummaryForCurrency> betSelectionSummaryForCurrencies)
    {
        tableWithLines(formatTableDataForSelectionLiabilityByCurrency(betSelectionSummaryForCurrencies));
    }

    /**
     * Please note code for formatting the table taken from (some slight modifications for use cases)
     * https://itsallbinary.com/java-printing-to-console-in-table-format-simple-code-with-flexible-width-left-align-header-separator-line/
     */
    private static void tableWithLines(String[][] table)
    {
        /*
         * leftJustifiedRows - If true, it will add "-" as a flag to format string to
         * make it left justified. Otherwise right justified.
         */
        boolean leftJustifiedRows = true;

        /*
         * Calculate appropriate Length of each column by looking at width of data in
         * each column.
         *
         * Map columnLengths is <column_number, column_length>
         */
        Map<Integer, Integer> columnLengths = new HashMap<>();
        Arrays.stream(table)
                .forEach(a -> Stream.iterate(0, (i -> i < a.length), (i -> ++i))
                                .forEach(i -> {
            columnLengths.putIfAbsent(i, 0);
            if (columnLengths.get(i) < a[i].length())
            {
                columnLengths.put(i, a[i].length());
            }
        }));

        /*
         * Prepare format String
         */
        final StringBuilder formatString = new StringBuilder("");
        String flag = leftJustifiedRows ? "-" : "";
        columnLengths.entrySet().stream().forEach(
                e -> formatString.append("| %" + flag + e.getValue() + "s ")
        );
        formatString.append("|\n");

        /*
         * Prepare line for top, bottom & below header row.
         */
        String line = columnLengths.entrySet().stream().reduce("", (ln, b) -> {
            String templn = "+-";
            templn = templn + Stream.iterate(0, (i -> i < b.getValue()), (i -> ++i)).reduce("", (ln1, b1) -> ln1 + "-",
                    (a1, b1) -> a1 + b1);
            templn = templn + "-";
            return ln + templn;
        }, (a, b) -> a + b);
        line = line + "+\n";

        /*
         * Print table
         */
        System.out.print(line);
        Arrays.stream(table).limit(1).forEach(a -> System.out.printf(formatString.toString(), (Object[]) a));
        System.out.print(line);

        Stream.iterate(1, (i -> i < table.length), (i -> ++i))
                .forEach(a -> System.out.printf(formatString.toString(), (Object[]) table[a]));
        System.out.print(line);
    }

    /**
     * Generate a 2d String array, to be passed into the table
     * Each row within the table is a BetSelectionSummaryForCurrency record
     * Each column of the table (apart from the headers) correspond to fields on the BetSelectionSummaryForCurrency record.
     *
     * The first row is for the headings/title of each column
     */
    private static String[][] formatTableDataForSelectionLiabilityByCurrency(List<BetSelectionSummaryForCurrency> betSelectionSummaryForCurrencies)
    {
        Integer rows = betSelectionSummaryForCurrencies.size() + 1;
        String[] headings = FileConstants.selectionAndCurrencyLiabilityReportHeadings;
        Integer cols = headings.length;
        String[][] tableDataForSelectionLiabilityByCurrency = new String[rows][cols];

        // Set the headings row
        tableDataForSelectionLiabilityByCurrency[0] = headings;

        // Go from the first row onwards as header is already set
        for(Integer row = 1; row < rows; row++)
        {
            // row starting at 1, but list index from 0 hence (row -1) below
            BetSelectionSummaryForCurrency betSelectionSummaryForCurrency = betSelectionSummaryForCurrencies.get(row - 1);

            for(Integer col = 0; col < cols; col++)
            {
                switch(col)
                {
                    case 0:
                        tableDataForSelectionLiabilityByCurrency[row][col] = betSelectionSummaryForCurrency.getSelectionName();
                        break;
                    case 1:
                        tableDataForSelectionLiabilityByCurrency[row][col] = betSelectionSummaryForCurrency.getCurrency();
                        break;
                    case 2:
                        tableDataForSelectionLiabilityByCurrency[row][col] = betSelectionSummaryForCurrency.getNumberOfBets().toString();
                        break;
                    case 3:
                        tableDataForSelectionLiabilityByCurrency[row][col] = betSelectionSummaryForCurrency.getTotalStakesWithCurrencySymbol();
                        break;
                    case 4:
                        tableDataForSelectionLiabilityByCurrency[row][col] = betSelectionSummaryForCurrency.getTotalLiabilityWithCurrencySymbol();
                        break;
                    default:
                }
            }
        }

        return tableDataForSelectionLiabilityByCurrency;
    }

    /**
     * Generate a 2d String array, to be passed into the table
     * Each row within the table is a BetSummaryForCurrency record
     * Each column of the table (apart from the headers) correspond to fields on the BetSelectionSummaryForCurrency record.
     *
     * The first row is for the headings/title of each column
     */
    private static String[][] formatTableDataForLiabilityByCurrency(List<BetSummaryForCurrency> betSelectionSummaryForCurrencies)
    {
        // add a row for the headers
        Integer rows = betSelectionSummaryForCurrencies.size() + 1;
        String[] headings = FileConstants.currencyLiabilityReportHeadings;

        Integer cols = headings.length;

        // create the 2d array
        String[][] tableDataForLiabilityByCurrency = new String[rows][cols];

        // set the header
        tableDataForLiabilityByCurrency[0] = headings;

        for(Integer row = 1; row < rows; row++)
        {
            BetSummaryForCurrency betSummaryForCurrency = betSelectionSummaryForCurrencies.get(row - 1);

            for(Integer col = 0; col < cols; col++)
            {
                switch(col)
                {
                    case 0:
                        tableDataForLiabilityByCurrency[row][col] = betSummaryForCurrency.getCurrency();
                        break;
                    case 1:
                        tableDataForLiabilityByCurrency[row][col] = betSummaryForCurrency.getNumberOfBets().toString();
                    case 2:
                        tableDataForLiabilityByCurrency[row][col] = betSummaryForCurrency.getTotalStakesWithCurrencySymbol();
                    case 3:
                        tableDataForLiabilityByCurrency[row][col] = betSummaryForCurrency.getTotalLiabilityWithCurrencySymbol();
                    default:
                }
            }
        }

        return tableDataForLiabilityByCurrency;
    }
}
