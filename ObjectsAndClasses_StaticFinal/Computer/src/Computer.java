public  class Computer {
    private Processor processor;
    private RAM ram;
    private Drive drive;
    private Monitor monitor;
    private Keyboard keyboard;
    private final String vendor;
    private final String name;

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public RAM getRam() {
        return ram;
    }

    public void setRam(RAM ram) {
        this.ram = ram;
    }

    public Drive getDrive() {
        return drive;
    }

    public void setDrive(Drive drive) {
        this.drive = drive;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public Computer(Processor processor, RAM ram, Drive drive, Monitor monitor,
                    Keyboard keyboard, String vendor, String name) {
        this.processor = processor;
        this.ram = ram;
        this.drive = drive;
        this.monitor = monitor;
        this.keyboard = keyboard;
        this.vendor = vendor;
        this.name = name;
    }

    public double getTotalWeight(){
        return processor.getWeight() + ram.getWeight() + drive.getWeight() + monitor.getWeight() + keyboard.getWeight();
    }

    @Override
    public String toString() {
        return "Computer{" +
                "processor=" + processor +
                ", ram=" + ram +
                ", drive=" + drive +
                ", monitor=" + monitor +
                ", keyboard=" + keyboard +
                ", vendor='" + vendor + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
