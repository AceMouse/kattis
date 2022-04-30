public class IntArrayQueue {
    private int[] arr;
    private int first = 0, last = 0, size = 0;


    public IntArrayQueue(int initialSize){
        arr = new int[1<<Integer.highestOneBit(initialSize)];
    }
    public IntArrayQueue(){
        arr = new int[1024];
    }
    public void clear(){
        first = last = size = 0;
    }
    public void enqueue(int i){
        size++;
        arr[last] = i;
        last = (last +1) & (arr.length-1);
        if (size == arr.length)
            resize();
    }

    public int dequeue(){
        size--;
        return arr[first++& (arr.length-1)];
    }

    public boolean isEmpty(){
        return size == 0;
    }

    private void resize(){
        int[] oldArr = arr;
        arr = new int[oldArr.length*2];
        for (int i = 0; i < oldArr.length; i++) {
            arr[i] = oldArr[(first+i) & (oldArr.length-1)];
        }
        first = 0;
        last = oldArr.length;
    }
}
