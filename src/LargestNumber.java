/**
 * Created by schandramouli on 5/30/17.
 */

import java.util.ArrayList;
import java.util.HashMap;

class maxheap{
    int Capacity;
    int Size;
    int[] elts;
    maxheap(int Cap){
        Capacity=Cap;
        Size=0;
        elts=new int[Cap+1];
        elts[0]=Integer.MAX_VALUE;
    }
    void add(int x, maxheap h){
        int i;
        for(i=++h.Size;h.elts[i/2]<x;i/=2)
            h.elts[i]=h.elts[i/2];
        h.elts[i]=x;
    }
    int deletemax(maxheap h){
        int i, child, maxelt, lastelt;
        if(h.Size==0)
            return (Integer) null;
        maxelt=h.elts[1];
        lastelt=h.elts[h.Size--];
        for(i=1;i*2<=h.Size;i=child){
            child=i*2;
            if(child!=h.Size && h.elts[child+1]>h.elts[child])
                child++;
            if(lastelt<h.elts[child])
                h.elts[i]=h.elts[child];
            else
                break;
        }
        h.elts[i]=lastelt;
        return maxelt;
    }
}
