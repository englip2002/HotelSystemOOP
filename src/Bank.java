public class Bank {
    private String bankName;
    private String pacNumber;

    //----------------Constructors-------------------
    public Bank(String bankName){
        this.bankName = bankName;
    }

    //-------------------Getters----------------------
    public String getBankName() {
        return bankName;
    }

    public String getPacNumber() {
        return pacNumber;
    }

    //-------------------Setters----------------------
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setPacNumber(String pacNumber) {
        this.pacNumber = pacNumber;
    }

    //--------------------Methods----------------------
    public void generatePACNumber() {
        int random = (int)(Math.random() * 999999);
        this.pacNumber = String.format("%06d", random);
    }

    public boolean validatePACNumber(String pacNumber) {
        return this.pacNumber.equals(pacNumber);
    }
}
