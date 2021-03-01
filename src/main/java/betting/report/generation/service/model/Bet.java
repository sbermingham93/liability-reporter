package betting.report.generation.service.model;

import betting.report.generation.service.constants.BetConstants;

import java.util.Map;

public class Bet
{
     private String betId;
     private String betTimestamp;
     private String selectionId;
     private String selectionName;
     private Double stake;
     private Double price;
     private String currency;

     public Bet(
             String betId, String betTimestamp, String selectionId,
             String selectionName, Double stake, Double price, String currency
     )
     {
          this.betId = betId;
          this.betTimestamp = betTimestamp;
          this.selectionId = selectionId;
          this.selectionName = selectionName;
          this.stake = stake;
          this.price = price;
          this.currency = currency;
     }

     public Bet(Map<String, String> bet)
     {
          this.betTimestamp = bet.get(BetConstants.betTimestamp);
          this.betId = bet.get(BetConstants.betId);
          this.currency = bet.get(BetConstants.currency);
          this.price = Double.parseDouble(bet.get(BetConstants.price));
          this.stake = Double.parseDouble(bet.get(BetConstants.stake));
          this.selectionName = bet.get(BetConstants.selectionName);
          this.selectionId = bet.get(BetConstants.selectionId);
     }

     public Bet()
     {}

     public String getBetId() {
          return betId;
     }

     public void setBetId(String betId) {
          this.betId = betId;
     }

     public String getBetTimestamp() {
          return betTimestamp;
     }

     public void setBetTimestamp(String betTimestamp) {
          this.betTimestamp = betTimestamp;
     }

     public String getSelectionId() {
          return selectionId;
     }

     public void setSelectionId(String selectionId) {
          this.selectionId = selectionId;
     }

     public String getSelectionName() {
          return selectionName;
     }

     public void setSelectionName(String selectionName) {
          this.selectionName = selectionName;
     }

     public Double getStake() {
          return stake;
     }

     public void setStake(Double stake) {
          this.stake = stake;
     }

     public Double getPrice() {
          return price;
     }

     public void setPrice(Double price) {
          this.price = price;
     }

     public String getCurrency() {
          return currency;
     }

     public void setCurrency(String currency) {
          this.currency = currency;
     }

     public Double getTotalLiability()
     {
          return this.price * this.stake;
     }

     public Boolean hasValidCurrency()
     {
          return !this.currency.isEmpty();
     }

     public Boolean hasValidSelectionName()
     {
          return !this.selectionName.isEmpty();
     }

     private Boolean hasValidStakeAndPrice()
     {
          return this.stake > 0 && this.price > 0;
     }

     public Boolean isValidBetForLiabilityBySelectionByCurrencyReport()
     {
          return this.hasValidSelectionName() && this.hasValidCurrency() && this.hasValidStakeAndPrice();
     }

     public Boolean isValidBetForLiabilityByCurrencyReport()
     {
          return this.hasValidCurrency() && this.hasValidStakeAndPrice();
     }
}
