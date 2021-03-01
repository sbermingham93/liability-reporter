package betting.report.generation.service.model;

public class ReportOutputConfiguration
{
    private String type;
    private String liabilityByCurrencyCsvLocation;
    private String liabilityBySelectionAndCurrencyCsvLocation;

    public ReportOutputConfiguration(String type, String liabilityByCurrencyCsvLocation, String liabilityBySelectionAndCurrencyCsvLocation) {
        this.type = type;
        this.liabilityByCurrencyCsvLocation = liabilityByCurrencyCsvLocation;
        this.liabilityBySelectionAndCurrencyCsvLocation = liabilityBySelectionAndCurrencyCsvLocation;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getLiabilityByCurrencyCsvLocation()
    {
        return liabilityByCurrencyCsvLocation;
    }

    public void setLiabilityByCurrencyCsvLocation(String liabilityByCurrencyCsvLocation)
    {
        this.liabilityByCurrencyCsvLocation = liabilityByCurrencyCsvLocation;
    }

    public String getLiabilityBySelectionAndCurrencyCsvLocation()
    {
        return liabilityBySelectionAndCurrencyCsvLocation;
    }

    public void setLiabilityBySelectionAndCurrencyCsvLocation(String liabilityBySelectionAndCurrencyCsvLocation)
    {
        this.liabilityBySelectionAndCurrencyCsvLocation = liabilityBySelectionAndCurrencyCsvLocation;
    }
}
