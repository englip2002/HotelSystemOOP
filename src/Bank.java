public class Bank {
    private String bankName;
    private String otpNumber;

    //----------------Constructors-------------------
    public Bank(String bankName){
        this.bankName = bankName;
    }

    //-------------------Getters----------------------
    public String getBankName() {
        return bankName;
    }

    public String getOtpNumber() {
        return otpNumber;
    }

    //-------------------Setters----------------------
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setOTPNumber(String pacNumber) {
        this.otpNumber = pacNumber;
    }

    //--------------------Methods----------------------
    public void generateOTPNumber() {
        int random = (int)(Math.random() * 999999);
        this.otpNumber = String.format("%06d", random);
    }

    public boolean validateOTPNumber(String otpNumber) {
        return this.otpNumber.equals(otpNumber);
    }
}
