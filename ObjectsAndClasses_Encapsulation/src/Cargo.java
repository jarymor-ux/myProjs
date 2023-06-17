 public final class Cargo {
        private final Dimensions dimensions;
        private final double weight;
        private final String deliveryAddress;
        private final boolean isFlippable;
        private final String registrationNumber;
        private final boolean isFragile;

        public Cargo(Dimensions dimensions, double weight, String deliveryAddress, boolean isFlippable, String registrationNumber, boolean isFragile) {
            this.dimensions = dimensions;
            this.weight = weight;
            this.deliveryAddress = deliveryAddress;
            this.isFlippable = isFlippable;
            this.registrationNumber = registrationNumber;
            this.isFragile = isFragile;
        }

        public Dimensions getDimensions() {
            return dimensions;
        }

        public double getWeight() {
            return weight;
        }

        public String getDeliveryAddress() {
            return deliveryAddress;
        }

        public boolean isFlippable() {
            return isFlippable;
        }

        public String getRegistrationNumber() {
            return registrationNumber;
        }

        public boolean isFragile() {
            return isFragile;
        }

        public Cargo withDeliveryAddress(String newAddress) {
            return new Cargo(dimensions, weight, newAddress, isFlippable, registrationNumber, isFragile);
        }

        public Cargo withDimensions(Dimensions newDimensions) {
            return new Cargo(newDimensions, weight, deliveryAddress, isFlippable, registrationNumber, isFragile);
        }

        public Cargo withWeight(double newWeight) {
            return new Cargo(dimensions, newWeight, deliveryAddress, isFlippable, registrationNumber, isFragile);
        }
    }


