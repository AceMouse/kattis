public class StringStack {
    private String[] stack;
    private int stackPointer = 0;
    private int stackSize;
    public StringStack(int initialSize){
        stack = new String[stackSize = initialSize];
    }
    public String pop() {
        return stack[--stackPointer];
    }
    public String peek() {
        return stack[stackPointer-1];
    }
    public String push(String item) {
        if (stackPointer == stackSize){
            String[] newStack = new String[stackSize + stackSize];
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
