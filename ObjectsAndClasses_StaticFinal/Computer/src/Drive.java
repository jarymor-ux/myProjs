public class Drive {
    private final DriveTypeEnum type;
    private final int DiskCapacity;
    private final double weight;

    public Drive(DriveTypeEnum type, int diskCapacity, double weight) {
        this.type = type;
        DiskCapacity = diskCapacity;
        this.weight = weight;
    }

    public DriveTypeEnum getType() {
        return type;
    }

    public int getDiskCapacity() {
        return DiskCapacity;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Drive{" +
                "type=" + type +
                ", DiskCapacity=" + DiskCapacity +
                ", weight=" + weight +
                '}';
    }
}
