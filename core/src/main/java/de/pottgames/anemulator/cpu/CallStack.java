package de.pottgames.anemulator.cpu;

public class CallStack {
    private static final int       CAPACITY = 10;
    private final CpuInstruction[] stack    = new CpuInstruction[CallStack.CAPACITY];
    private int                    index    = -1;


    public CallStack() {
        for (int i = 0; i < CallStack.CAPACITY; i++) {
            this.stack[i] = new CpuInstruction();
        }
    }


    public void add(String name, int opCode, int pc) {
        this.index = this.incIndex(this.index);
        this.stack[this.index].used = true;
        this.stack[this.index].name = name;
        this.stack[this.index].opCode = opCode;
        this.stack[this.index].pc = pc;
    }


    public void print() {
        if (this.index < 0) {
            System.out.println("Call Stack is empty.");
            return;
        }

        System.out.println("######### CALL STACK #########");
        int printIndex = this.index;
        do {
            final CpuInstruction instruction = this.stack[printIndex];
            if (instruction.used) {
                System.out.println("$" + Integer.toHexString(instruction.pc) + " : " + Integer.toHexString(instruction.opCode) + " : " + instruction.name);
            }
            printIndex = this.decIndex(printIndex);
        } while (printIndex != this.index);
        System.out.println("##############################");
    }


    private int incIndex(int value) {
        value++;
        if (value > CallStack.CAPACITY - 1) {
            value = 0;
        }
        return value;
    }


    private int decIndex(int value) {
        value--;
        if (value < 0) {
            value = CallStack.CAPACITY - 1;
        }
        return value;
    }


    private static class CpuInstruction {
        private boolean used = false;
        private String  name;
        private int     opCode;
        private int     pc;
    }

}
