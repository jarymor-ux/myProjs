public class Monitor {
    private final double diagonal;
    private final MonitorTypeEnum monitorType;
    private final double weight;

    public Monitor(double diagonal, MonitorTypeEnum monitorType, double weight) {
        this.diagonal = diagonal;
        this.monitorType = monitorType;
        this.weight = weight;
    }

    public double getDiagonal() {
        return diagonal;
    }

    public MonitorTypeEnum getMonitorType() {
        return monitorType;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Monitor{" +
                "diagonal=" + diagonal +
                ", monitorType=" + monitorType +
                ", weight=" + weight +
                '}';
    }
}
