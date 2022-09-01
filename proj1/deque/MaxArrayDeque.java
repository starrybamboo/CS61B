package deque;


import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>  {
    Comparator<T> c;
    public MaxArrayDeque(Comparator<T> c){
        this.c = c;
    }

    public T max(){
        return max(this.c);
    }

    public T max(Comparator<T> thing) {
        if (isEmpty()){
            return null;
        }
        T elementMax = get(0);

        for(int i = 0; i < size(); i++){
            if (thing.compare(get(i), elementMax) > 0){
                elementMax = get(i);
            }
        }
        return elementMax;
    }

}

