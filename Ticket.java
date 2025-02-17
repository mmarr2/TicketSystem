public class Ticket {
    private int ticketNr;
    private String customerName;
    private String anliegen;
    private int talkingtime;

    public Ticket(int ticketNr, String customerName, String anliegen, int talkingtime){
        this.ticketNr = ticketNr;
        this.customerName = customerName;
        this.anliegen = anliegen;
        this.talkingtime = talkingtime;
    }

    public int getTicketNr() {
        return ticketNr;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAnliegen(){
        return anliegen;
    }

    public int getTalkingtime() {
        return talkingtime;
    }
}
