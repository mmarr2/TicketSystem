import java.util.concurrent.CountDownLatch;

public class Schalter implements Runnable{
    private String name;
    private CountDownLatch latch = new CountDownLatch(1);
    private TicketSystem system;

    public Schalter(String name, TicketSystem system){
        this.name = name;
        this.system = system;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void run(){
        latch = system.getLatch();
        System.out.println("Schalter " + name + " ist bereit.");
        while(true){
            try {
                Ticket ticket = system.nextTicket();
                if(ticket.getTicketNr() == -1){
                    System.out.println("Schalter " + name + " wird heruntergefahren");
                    return;
                }
                System.out.println("Schalter " + name + ": uebernimmt Ticket #" + ticket.getTicketNr());
                system.addSchalter(ticket.getTicketNr(), this);

                latch.countDown();
                latch = system.getLatch2();
                latch.await();
                System.out.println("Schalter " + name + ": das Anliegen bei #" + ticket.getTicketNr() + " war " + ticket.getAnliegen());
                latch.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
