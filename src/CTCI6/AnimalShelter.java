package CTCI6;

import java.util.LinkedList;

/**
 * Created by schandramouli on 3/21/16.
 */
public class AnimalShelter {
    LinkedList<Object> ll = new LinkedList<>();
    public Object dequeueAny() {
        if (!ll.isEmpty()) {
            return ll.removeLast();
        } else {
            return null;
        }
    }

    public Object dequeueCat() {
        Object x = this.dequeueAny();
        LinkedList<Object> temp = new LinkedList<>();
        while (x != null &&  !x.getClass().equals("Cat")) {
            temp.add(x);
            x = this.dequeueAny();
        }
        while (!temp.isEmpty()) {
            this.enqueue(temp.removeLast());
        }
        return x;
    }

    public void enqueue(Object o) {
        ll.addLast(o);
    }


    @Override
    public String toString() {
        return "AnimalShelter{" +
                "ll=" + ll +
                '}';
    }

    public static void main(String[] args) {
        AnimalShelter animalShelter = new AnimalShelter();
        Cat c = new Cat("SSS");
        Dog d = new Dog("DOOD");
        animalShelter.enqueue(c);
        animalShelter.enqueue(d);
        System.out.println(animalShelter);
        System.out.println(animalShelter.dequeueCat());
    }

}
