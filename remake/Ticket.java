public class Ticket {
    private int ticketNr;
    private Kunde kunde;

    public Ticket(Kunde kunde, int ticketNr){
        this.kunde = kunde;
        this.ticketNr = ticketNr;
    }

    public int getTicketNr() {
        return ticketNr;
    }

    public void setTicketNr(int ticketNr) {
        this.ticketNr = ticketNr;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public String getName(){
        return kunde.getName();
    }

    public String getAnliegen(){
        return kunde.getAnliegen();
    }

    public int getTalkingTime(){
        return kunde.getTalkingTime();
    }
}
