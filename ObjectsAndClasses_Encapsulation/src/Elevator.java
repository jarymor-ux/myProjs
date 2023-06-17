public class Elevator {
    private int currentFloor;
    private final int minFloor;
    private final int maxFloor;

    public Elevator(int minFloor, int maxFloor) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.currentFloor = 1;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void moveDown() {
        if (currentFloor > minFloor) {
            currentFloor--;
        } else {
            System.out.println("Лифт уже находится на нижнем этаже");
        }
    }

    public void moveUp() {
        if (currentFloor < maxFloor) {
            currentFloor++;
        } else {
            System.out.println("Лифт уже находится на верхнем этаже");
        }
    }

    public void move(int floor) {
        if (floor >= minFloor && floor <= maxFloor) {
            if (floor > currentFloor) {
                for (int i = currentFloor; i < floor; i++) {
                    moveUp();
                    System.out.println("Текущий этаж: " + getCurrentFloor());
                }
            } else if (floor < currentFloor) {
                for (int i = currentFloor; i > floor; i--) {
                    moveDown();
                    System.out.println("Текущий этаж: " + getCurrentFloor());
                }
            }
        } else {
            System.out.println("Ошибка! Этаж указан неверно.");
        }
    }
}
