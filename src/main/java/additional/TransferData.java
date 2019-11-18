package additional;

public class TransferData {


    private double amount;
    private String cardNum;
    private String periodicity;


    public TransferData(String cardNum, String periodicity, double amount) {
        this.cardNum = cardNum;
        this.periodicity = periodicity;
        this.amount = amount;
    }


    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }
}
