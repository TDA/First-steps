import apple.laf.JRSUIUtils;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.TreeVisitor;
import sun.reflect.generics.tree.Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by schandramouli on 7/19/15.
 */
public class subtree {

    private String data="";
    private List<subtree> children = new ArrayList<>();

    public subtree(){
        // do nothing
    }
    public subtree(String data, List l){
        this.data = data;
        this.children = l;
    }

    public static void printTree(subtree t){
        if(t.data != null)
            System.out.println("---> " + t.data);
        if(t.children != null) {
            for (subtree s : t.children) {
                System.out.print("\t");
                printTree(s);
            }
        }

    }

    public static boolean isSubtree(subtree t1, subtree t2){

        Boolean b = false;
        if(t2 == null)
            return true;

        if(t1 == null)
            return false;

        if(t1.equals(t2)){
            return true;
        }
        else {
            if(t1.children != null) {
                for (subtree child : t1.children) {
                    System.out.print("child: ");
                    printTree(child);

                    System.out.print("t2: ");
                    printTree(t2);
                    b = isSubtree(child, t2);
                    if (b == true)
                        break;
                }
            }
        }
        return b;
    }


    public static void main(String[] args){
        List<subtree> l = new ArrayList<subtree>();
        List<subtree> l2 = new ArrayList<subtree>();
        List<subtree> l3 = new ArrayList<subtree>();
        List<subtree> l4 = new ArrayList<subtree>();

        subtree t6 = new subtree("ace", null);
        subtree t7 = new subtree("adf", null);
        l3.add(t6);
        l4.add(t7);

        subtree t4 = new subtree("ac" , l3);
        subtree t5 = new subtree("ad" , l4);

        l2.add(t4);
        l2.add(t5);


        subtree t2 = new subtree("a" , l2);
        subtree t3 = new subtree("b" , null);

        l.add(t2);
        l.add(t3);
        subtree t1 = new subtree("root", l);
        //printTree(t1);
        //printTree(t2);
        //printTree(t3);
        System.out.println(isSubtree(t3,t6));
    }

}

