public class ByteStack {
    private byte[] stack;
    private int stackPointer = 0;
    private int stackSize;
    public ByteStack(int initialSize){
        stack = new byte[stackSize = initialSize];
    }
    public byte pop() {
        return stack[--stackPointer];
    }
    public byte get(int i){
        return stack[i];
    }
    public void push(byte item) {
        if (stackPointer == stackSize){
            byte[] newStack = new byte[stackSize + stackSize];
            System.arraycopy(stack,0,newStack,0,stackSize);
            stackSize += stackSize;
            stack = newStack;
        }
        stack[stackPointer++] = item;
    }
    public boolean isEmpty(){
        return stackPointer==0;
    }
    public void reset(){
        stackPointer = 0;
        stackSize = 0;
    }
    public int size(){
        return stackPointer;
    }
}
