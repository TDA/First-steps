public class Person {
    String name = "name";
    public Person(String name){
        this.name = name;
    }
    public void newName(String name){
        this.name = name;
    }
    public void printName(){
        System.out.println(name);
    }
}
