// UseCase3InventorySetup.java

import java.util.HashMap;
import java.util.Map;

// Abstract Room class (reuse from UC2)
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

// Centralized inventory management
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

    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
    }
}

// Main class for UC3
public class UseCase3InventorySetup {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - UC3 Inventory\n");

        // Initialize rooms
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoom(singleRoom, 5);
        inventory.registerRoom(doubleRoom, 3);
        inventory.registerRoom(suiteRoom, 2);

        // Display room details
        singleRoom.displayRoomDetails();
        doubleRoom.displayRoomDetails();
        suiteRoom.displayRoomDetails();

        // Display centralized inventory
        inventory.displayInventory();

        // Update availability example
        inventory.updateAvailability("Double Room", 2);
        System.out.println("\nAfter updating Double Room availability:");
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}