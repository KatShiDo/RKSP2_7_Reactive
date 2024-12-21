package mirea.ru.reactive.generated;

import mirea.ru.reactive.entity.Computer;

public class ComputerData {

    private static Computer computer1;
    private static Computer computer2;

    public static void generateComputers() {
        computer1 = new Computer(1L, "i9 9900k", "gtx 1080", 16, 1024);
        computer2 = new Computer(2L, "ryzen 7 5700x", "rx 6900xt", 32, 512);
    }

    public static Computer getComputer1() {
        return computer1;
    }

    public static Computer getComputer2() {
        return computer2;
    }
}
