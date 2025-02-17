public class NToN {
    public static void main(String[] args) {
        TicketSystem ticketSystem = new TicketSystem();
        Schalter s1 = new Schalter("1", ticketSystem);
        Schalter s2 = new Schalter("2", ticketSystem);
        Thread schalter1 = new Thread(s1);
        Thread schalter2 = new Thread(s2);

        schalter1.start();
        schalter2.start();

        Thread kunde1 = new Thread(new Kunde("falsche Farbe", "X", ticketSystem, 1000));
        Thread kunde2 = new Thread(new Kunde("falsche Größe", "Y", ticketSystem, 1000));
        Thread kunde3 = new Thread(new Kunde("falsche Form", "Z", ticketSystem, 1000));
        Thread kunde4 = new Thread(new Kunde("falsches Material", "A", ticketSystem, 1000));
        Thread kunde5 = new Thread(new Kunde("falsche Marke", "B", ticketSystem, 1000));

        kunde1.start();
        kunde2.start();
        kunde3.start();
        kunde4.start();
        kunde5.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ticketSystem.sendShutdown();
        ticketSystem.sendShutdown();
    }
}
