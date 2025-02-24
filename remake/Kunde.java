import java.util.concurrent.CountDownLatch;

public class Kunde implements Runnable{
    private String name;
    private String anliegen;
    private int ticketNr;
    private int talkingTime;
    private CountDownLatch latch;
    private TicketSystem system;

    public Kunde(String name, String anliegen, int talkingTime, TicketSystem system){
        this.name = name;
        this.anliegen = anliegen;
        this.talkingTime = talkingTime;
        this.system = system;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public int getTalkingTime() {
        return talkingTime;
    }

    public void setTalkingTime(int talkingTime) {
        this.talkingTime = talkingTime;
    }

    public int getTicketNr() {
        return ticketNr;
    }

    public void setTicketNr(int ticketNr) {
        this.ticketNr = ticketNr;
    }

    public String getAnliegen() {
        return anliegen;
    }

    public void setAnliegen(String anliegen) {
        this.anliegen = anliegen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void run(){
        latch = system.getLatch();
        System.out.println("Kunde " + name + " kommt mit dem Anliegen '" + anliegen + "'.");
        try {
            system.press(this);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Kunde " + name + " hat Ticket #" + ticketNr + " und wartet.");
        try {
            latch.await();
            Schalter schalter = system.getSchalter(ticketNr);
            System.out.println("Kunde " + name + " hoert auf zu warten und geht zu Schalter " + schalter.getName() + ".");
            Thread.sleep(talkingTime);
            System.out.println("Kunde " + name + " hat das Anliegen vollstaendig vorgetragen");

            latch = system.getLatch2();
            latch.countDown();
            latch.await();
            System.out.println("Kunde " + name + " geht wieder.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
