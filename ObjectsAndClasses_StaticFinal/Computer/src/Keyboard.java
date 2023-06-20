public class Keyboard {
    private final String keyboardType;
    private final IsBacklighting isBacklighting;
    private final double weight;

    public Keyboard(String keyboardType, IsBacklighting isBacklighting, double weight) {
        this.keyboardType = keyboardType;
        this.isBacklighting = isBacklighting;
        this.weight = weight;
    }

    public String getKeyboardType() {
        return keyboardType;
    }

    public IsBacklighting getIsBacklighting() {
        return isBacklighting;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Keyboard{" +
                "keyboardType='" + keyboardType + '\'' +
                ", isBacklighting=" + isBacklighting +
                ", weight=" + weight +
                '}';
    }
}
