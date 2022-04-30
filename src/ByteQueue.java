public class ByteQueue {
    private byte[] queue;
    private int initialSize;
    private int enqueuePointer = 0, dequeuePointer = 0;
    public ByteQueue(int initialSize) {
        queue = new byte[this.initialSize = initialSize];
    }
    public void enqueue(byte item){
        queue[enqueuePointer++] = item;
    }
    public byte dequeue(){
        return queue[dequeuePointer++];
    }
    public void reset(){
        enqueuePointer = 0;
        dequeuePointer = 0;
    }
}
