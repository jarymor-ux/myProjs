public class RAM {
    private final String type;
    private final int amountOfRam;
    private final double weight;

    public RAM(String type, int amountOfRam, double weight) {
        this.type = type;
        this.amountOfRam = amountOfRam;
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public int getAmountOfRam() {
        return amountOfRam;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "RAM{" +
                "type='" + type + '\'' +
                ", amountOfRam=" + amountOfRam +
                ", weight=" + weight +
                '}';
    }
}
