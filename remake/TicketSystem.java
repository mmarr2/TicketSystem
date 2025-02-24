import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class TicketSystem {
    private BlockingQueue<Ticket> tickets = new LinkedBlockingQueue<>();
    private int ticketNr = 0;
    private CountDownLatch latch = new CountDownLatch(1);
    private CountDownLatch latch2 = new CountDownLatch(1);
    private Map<Integer, Schalter> ticketSchalter = new HashMap<>();

    public synchronized void press(Kunde kunde) throws InterruptedException {
        kunde.setTicketNr(ticketNr);
        tickets.put(new Ticket(kunde, ticketNr++));
    }

    public Ticket nextTicket() throws InterruptedException {
        return tickets.take();
    }


    public CountDownLatch getLatch(){
        return latch;
    }

    public void setLatch(CountDownLatch latch){
        this.latch = latch;
    }

    public CountDownLatch getLatch2(){
        return latch2;
    }

    public void addSchalter(int ticketNr, Schalter schalter){
        ticketSchalter.put(ticketNr, schalter);
    }

    public synchronized Schalter getSchalter(int ticketNr){
        while(ticketSchalter.get(ticketNr) == null){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return ticketSchalter.get(ticketNr);
    }

    public synchronized void shutdown() {
        try{
            tickets.put(new Ticket(null, -1));
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }

    }

}
