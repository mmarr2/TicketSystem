public class OneToOne {
    public static void main(String[] args) {

        TicketSystem ticketSystem = new TicketSystem();

        Schalter s1 = new Schalter("1", ticketSystem);
        Thread schalter1 = new Thread(s1);
        schalter1.start();


        Thread kunde1 = new Thread(new Kunde("falsche Farbe", "X", ticketSystem, 1000));

        kunde1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ticketSystem.sendShutdown();
    }


}
