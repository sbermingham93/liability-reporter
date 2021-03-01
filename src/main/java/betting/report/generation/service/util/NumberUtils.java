package betting.report.generation.service.util;

public class NumberUtils
{
    /**
     * Takes in a double amount, desired decimal places
     * returns the rounded double value
     */
    public static Double roundToNPlaces(Double value, Integer places)
    {
        Double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
