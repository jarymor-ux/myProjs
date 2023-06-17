public class Main1 {
    public static void main(String[] args) {
        Dimensions dimensions = new Dimensions(10.0, 5.0, 8.0);


        Cargo cargo = new Cargo(dimensions, 25, "Moscow", true, "RT45Y7U876IO025RU", true);
        System.out.println("Original Cargo: " + cargo);


        Cargo updatedCargo1 = cargo.withDeliveryAddress("Murmansk");
        System.out.println("Updated Cargo 1: " + updatedCargo1);
        System.out.println("Original Cargo after update 1: " + cargo);


        Dimensions newDimensions = new Dimensions(12.0, 6.0, 10.0);
        Cargo updatedCargo2 = cargo.withDimensions(newDimensions);
        System.out.println("Updated Cargo 2: " + updatedCargo2);
        System.out.println("Original Cargo after update 2: " + cargo);


        Cargo updatedCargo3 = cargo.withWeight(20.0);
        System.out.println("Updated Cargo 3: " + updatedCargo3);
        System.out.println("Original Cargo after update 3: " + cargo);
    }
}
