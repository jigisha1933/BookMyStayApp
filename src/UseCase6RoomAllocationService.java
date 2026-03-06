// UseCase6RoomAllocationService.java

import java.util.*;

// Room class (reuse)
abstract class Room {
    private int numberOfBeds;
    private double size;
    private double price;

    public Room(int numberOfBeds, double size, double price) {
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    public abstract String getRoomType();

    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType() +
                ", Beds: " + numberOfBeds +
                ", Size: " + size + " sqm" +
                ", Price: $" + price);
    }
}

// Concrete room types
class SingleRoom extends Room {
    public SingleRoom() { super(1, 15.0, 50.0); }
    @Override
    public String getRoomType() { return "Single Room"; }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super(2, 25.0, 80.0); }
    @Override
    public String getRoomType() { return "Double Room"; }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super(4, 50.0, 200.0); }
    @Override
    public String getRoomType() { return "Suite Room"; }
}

// Reservation class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return guestName + " -> " + roomType;
    }
}

// Booking request queue (reuse UC5)
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();
    public void addRequest(Reservation r) { queue.add(r); }
    public Reservation pollRequest() { return queue.poll(); }
    public boolean hasPending() { return !queue.isEmpty(); }
}

// Centralized inventory (reuse UC3)
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();
    public void registerRoom(Room room, int count) { inventory.put(room.getRoomType(), count); }
    public boolean isAvailable(String roomType) { return inventory.getOrDefault(roomType, 0) > 0; }
    public void decrement(String roomType) { inventory.put(roomType, inventory.get(roomType) - 1); }
}

// Allocation service
class RoomAllocationService {
    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRooms; // roomType -> allocated IDs
    private int nextRoomId;

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
        this.nextRoomId = 100; // starting room ID
    }

    public void allocateRoom(Reservation r) {
        String roomType = r.getRoomType();
        if (!inventory.isAvailable(roomType)) {
            System.out.println("No available " + roomType + " for " + r.getGuestName());
            return;
        }
        String roomId = generateRoomId();
        allocatedRooms.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomId);
        inventory.decrement(roomType);
        System.out.println("Confirmed: " + r.getGuestName() +
                " assigned " + roomType + " (Room ID: " + roomId + ")");
    }

    private String generateRoomId() {
        nextRoomId++;
        return "R" + nextRoomId;
    }
}

// Main class
public class UseCase6RoomAllocationService {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - UC6 Room Allocation\n");

        // Initialize rooms
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoom(rooms[0], 2);
        inventory.registerRoom(rooms[1], 1);
        inventory.registerRoom(rooms[2], 1);

        // Initialize booking queue (requests)
        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Suite Room"));
        queue.addRequest(new Reservation("David", "Single Room"));
        queue.addRequest(new Reservation("Eve", "Single Room")); // will fail due to inventory

        // Allocate rooms
        RoomAllocationService allocationService = new RoomAllocationService(inventory);
        while (queue.hasPending()) {
            allocationService.allocateRoom(queue.pollRequest());
        }

        System.out.println("\nAll requests processed. Inventory synchronized. Application terminated.");
    }
}