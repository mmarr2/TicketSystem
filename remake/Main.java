public class Main {
    public static void main(String[] args) throws InterruptedException {
        TicketSystem system = new TicketSystem();
        Thread t1 = new Thread(new Kunde("K1", "Anliegen 1", 1000, system));
        Thread t2 = new Thread(new Kunde("K2", "Anliegen 2", 2000, system));
        Thread t3 = new Thread(new Kunde("K3", "Anliegen 3", 3000, system));
        Thread t4 = new Thread(new Kunde("K4", "Anliegen 4", 4000, system));
        Thread t5 = new Thread(new Kunde("K5", "Anliegen 5", 5000, system));
        Thread t6 = new Thread(new Schalter("A", system));
        Thread t7 = new Thread(new Schalter("B", system));
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        system.shutdown();
        system.shutdown();
    }
}
