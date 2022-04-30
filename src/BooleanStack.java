public class BooleanStack {
    private boolean[] stack;
    private int stackPointer = 0;
    private int stackSize;
    public BooleanStack(int initialSize){
        stack = new boolean[stackSize = initialSize];
    }
    public boolean pop() {
        return stack[--stackPointer];
    }
    public void push(boolean item) {
        if (stackPointer == stackSize){
            boolean[] newStack = new boolean[stackSize + stackSize];
            System.arraycopy(stack,0,newStack,0,stackSize);
            stackSize += stackSize;
            stack = newStack;
        }
        stack[stackPointer++] = item;
    }
    public boolean isEmpty(){
        return stackPointer==0;
    }
}
