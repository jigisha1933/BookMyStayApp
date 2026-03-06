// UseCase4RoomSearch.java

import java.util.HashMap;
import java.util.Map;

// Abstract Room class (reuse from UC2/UC3)
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
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Number of Beds: " + numberOfBeds);
        System.out.println("Size: " + size + " sqm");
        System.out.println("Price per night: $" + price);
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

// Centralized inventory
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void registerRoom(Room room, int count) {
        inventory.put(room.getRoomType(), count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        // return a copy to prevent accidental modification
        return new HashMap<>(inventory);
    }
}

// Room search service (read-only access)
class RoomSearchService {
    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void displayAvailableRooms(Room[] rooms) {
        System.out.println("Available Rooms for Guests:\n");
        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());
            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// Main class for UC4
public class UseCase4RoomSearch {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - UC4 Room Search\n");

        // Initialize rooms
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoom(rooms[0], 5);
        inventory.registerRoom(rooms[1], 0); // Double Room is unavailable
        inventory.registerRoom(rooms[2], 2);

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Display available rooms (read-only)
        searchService.displayAvailableRooms(rooms);

        System.out.println("Application terminated.");
    }
}