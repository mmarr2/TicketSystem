import java.util.concurrent.CountDownLatch;

public class Kunde implements Runnable {
    private String anliegen;
    private String name;
    private int ticketNr;
    private TicketSystem system;
    private int talkingtime;
    private CountDownLatch readyLatch;
    private Schalter schalter;

    public Kunde(String anliegen, String name, TicketSystem system, int talkingtime) {
        this.anliegen = anliegen;
        this.name = name;
        this.system = system;
        this.talkingtime = talkingtime;
    }

    @Override
    public void run() {
        System.out.println("Kunde " + name + " kommt mit dem Anliegen '" + anliegen + "'");
        try {
            pressButton();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            readyLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        schalter = system.getSchalter(ticketNr);
        System.out.println("Kunde " + name + " hört auf zu warten und geht zu Schalter " + schalter.getName());
        schalter.setReady();
    }

    public void pressButton() throws InterruptedException {
        readyLatch = new CountDownLatch(1);
        ticketNr = system.press(this, readyLatch);
        System.out.println("Kunde " + name + " hat Ticket #" + ticketNr + " und wartet");
    }

    public String getAnliegen() {
        return anliegen;
    }

    public String getName() {
        return name;
    }

    public int getTicketNr() {
        return ticketNr;
    }

    public int getTalkingtime() {
        return talkingtime;
    }
}

