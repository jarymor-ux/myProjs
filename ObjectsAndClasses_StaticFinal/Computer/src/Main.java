public class Main {
    public static void main(String[] args) {
        Processor i7_12700 = new Processor(4.7,14,"intel", 30.0);
        RAM kingstonFuryImpact = new RAM("DDR4",16,15.0);
        Drive samsung980Pro = new Drive(DriveTypeEnum.SSD,500,9.0);
        Monitor LG = new Monitor(27.0,MonitorTypeEnum.IPS,5100);
        Keyboard annePro2 = new Keyboard("Mechanical",IsBacklighting.YES,635.0);
        Computer computer = new Computer(i7_12700,kingstonFuryImpact,samsung980Pro,
                LG,annePro2,"DNS", "JUPITER");
        System.out.println(computer);
        System.out.println("Вес компьютера Jupiter старой комплектации = " + computer.getTotalWeight());
        Monitor msi = new Monitor(23.8,MonitorTypeEnum.IPS,2850.0);
        Keyboard zet = new Keyboard("Mechanical", IsBacklighting.YES,715.0);
        computer.setMonitor(msi);
        computer.setKeyboard(zet);
        System.out.println(computer);
        Computer computer1 = new Computer(i7_12700,kingstonFuryImpact,samsung980Pro,LG,annePro2,"MVIDEO","MAG META");
        System.out.println("Вес компьютера Jupiter новой комплектации = " + computer.getTotalWeight());
        System.out.println("Вес компьютера MAG META = " + computer1.getTotalWeight());
    }
}
