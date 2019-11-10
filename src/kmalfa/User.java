package kmalfa;

public class User {

    private String cardNumber;
    private String cardHolderName;
    private int op;
    private int pin;

    public User(String cardHolderName, String cardNumber, int pin, int op) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.pin = pin;
        this.op = op;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getPin() {
        return pin;
    }

    public int getOp() {
        return op;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void destroySession() {
        this.cardNumber = null;
        this.cardHolderName = null;
        this.pin = 0;
        this.op = 0;
    }

}


