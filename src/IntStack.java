public class IntStack {
    private int[] stack;
    private int stackPointer = 0;
    private int stackSize;
    public IntStack(int initialSize){
        stack = new int[stackSize = initialSize];
    }
    public int pop() {
        return stack[--stackPointer];
    }
    public int peek() {
        return stack[stackPointer-1];
    }
    public int push(int item) {
        if (stackPointer == stackSize){
            int[] newStack = new int[stackSize + stackSize];
            System.arraycopy(stack,0,newStack,0,stackSize);
            stackSize += stackSize;
            stack = newStack;
        }
        return stack[stackPointer++] = item;
    }
    public boolean isEmpty(){
        return stackPointer==0;
    }

    public int size() {
        return stackPointer;
    }
}