// UseCase5BookingRequestQueue.java

import java.util.LinkedList;
import java.util.Queue;

// Guest Reservation request
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Reservation [Guest: " + guestName + ", Room Type: " + roomType + "]";
    }
}

// Booking request queue using FIFO
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Added booking request: " + reservation);
    }

    public Reservation processNextRequest() {
        return queue.poll(); // removes and returns the head, or null if empty
    }

    public boolean hasPendingRequests() {
        return !queue.isEmpty();
    }

    public void displayPendingRequests() {
        System.out.println("\nPending Booking Requests:");
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }
}

// Main class for UC5
public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - UC5 Booking Requests\n");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Sample reservations (requests arrive in order)
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Add requests to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display pending requests
        bookingQueue.displayPendingRequests();

        // Process requests one by one
        System.out.println("\nProcessing requests (FIFO order):");
        while (bookingQueue.hasPendingRequests()) {
            Reservation next = bookingQueue.processNextRequest();
            System.out.println("Processing: " + next);
        }

        System.out.println("\nAll requests processed. Queue is now empty.");
        System.out.println("Application terminated.");
    }
}