package betting.report.generation.service.util;

import betting.report.generation.service.constants.CurrencyConstants;

public class CurrencyUtils
{
    /**
     * Takes in the currency as string and the double amount
     * Returns the currencies symbol and the rounded amount concatenated
     * (assuming regognised currency gbp, eur)
     */
    public static String formatCurrencyFromDouble(String currency, Double value)
    {
        if (currency.toLowerCase().contains(CurrencyConstants.GBP))
        {
            return CurrencyConstants.GBP_SYMBOL + NumberUtils.roundToNPlaces(value, 3);
        }
        else if (currency.toLowerCase().contains(CurrencyConstants.EURO))
        {
            return CurrencyConstants.EURO_SYMBOL + NumberUtils.roundToNPlaces(value, 3);
        }
        else
        {
            System.out.println("Not a recognised currency, just returning value");
            return value.toString();
        }
    }
}
