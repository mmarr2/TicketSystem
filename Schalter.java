import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Schalter implements Runnable {
    private String name;
    private boolean running = true;
    private TicketSystem ticketSystem;
    private CountDownLatch readyLatch;

    public Schalter(String name, TicketSystem ticketSystem) {
        this.name = name;
        this.ticketSystem = ticketSystem;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        System.out.println("Schalter " + name + " ist geöffnet");
        while (running) {
            Ticket ticket;
            try {
                ticket = ticketSystem.next();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (ticket != null) {
                if (ticket.getTicketNr() == -1) {
                    System.out.println("Schalter " + name + " ist geschlossen");
                    return;
                }
                readyLatch = new CountDownLatch(1);
                ticketSystem.setReady(ticket.getTicketNr(), this, readyLatch);
                System.out.println("Schalter " + name + " übernimmt #" + ticket.getTicketNr() + " von " + ticket.getCustomerName());
                try {
                    readyLatch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                try {
                    Thread.sleep(ticket.getTalkingtime());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Kunde " + ticket.getCustomerName() + " hat das Anliegen vollständig vorgetragen " + name);
                System.out.println("Schalter " + name + ": Das Anliegen bei #" + ticket.getTicketNr() + " war '" + ticket.getAnliegen() + "'");
                System.out.println(ticket.getCustomerName() + " geht wieder.");
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public void setReady() {
        if (readyLatch != null) {
            readyLatch.countDown();
        }
    }
}
