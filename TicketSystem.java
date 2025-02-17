import java.util.*;
import java.util.concurrent.*;

public class TicketSystem {
    private BlockingQueue<Ticket> queue = new LinkedBlockingQueue<>();
    private Map<Integer, Schalter> ticketSchalterMap = new ConcurrentHashMap<>();
    private Map<Integer, CountDownLatch> ticketLatchMap = new ConcurrentHashMap<>();
    private int ticketCounter = 0;

    public synchronized int press(Kunde kunde, CountDownLatch latch) throws InterruptedException {
        int ticketNr = ++ticketCounter;
        Ticket ticket = new Ticket(ticketNr, kunde.getName(), kunde.getAnliegen(), kunde.getTalkingtime());
        queue.put(ticket);
        ticketLatchMap.put(ticketNr, latch);
        return ticketNr;
    }

    public Ticket next() throws InterruptedException {
        return queue.take();
    }

    public void setReady(int ticketNr, Schalter schalter, CountDownLatch latch) {
        ticketSchalterMap.put(ticketNr, schalter);
        CountDownLatch kundeLatch = ticketLatchMap.get(ticketNr);
        if (kundeLatch != null) {
            kundeLatch.countDown();
        }
    }

    public boolean isReady(int ticketNr) {
        return ticketSchalterMap.containsKey(ticketNr);
    }

    public Schalter getSchalter(int ticketNr) {
        return ticketSchalterMap.get(ticketNr);
    }

    public void sendShutdown() {
        try{
            queue.put(new Ticket(-1, "", "", 0));
        }catch (InterruptedException e){
            System.err.print("Shutdown failed");
        }
    }
}
