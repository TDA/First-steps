import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThroneInheritance {
    private final TreeNode root;

    class TreeNode {
        List<TreeNode> children;
        TreeNode parent;
        String name;

        public <E> TreeNode(String kingName, ArrayList<TreeNode> es) {
            children = es;
            name = kingName;
            parent = null;
        }
    }

    Map<String, TreeNode> parentMap = new HashMap<>();

    public ThroneInheritance(String kingName) {
        this.root = new TreeNode(kingName, new ArrayList<>());
        parentMap.put(kingName, root);
    }

    public void birth(String parentName, String childName) {
        TreeNode parent = findNode(parentName);
        TreeNode child = new TreeNode(childName, new ArrayList<>());
        parent.children.add(child);
        parentMap.put(childName, child);
    }

    private TreeNode findNode(String parentName) {
        return parentMap.get(parentName);
    }

    public void death(String name) {
        TreeNode node = findNode(name);
        node.name = "dead";
    }

    public List<String> getInheritanceOrder() {
        ArrayList<String> strings = new ArrayList<>();
        preorder(this.root, strings);
        return strings.stream().filter(s -> !s.equals("dead")).collect(Collectors.toList());
    }

    private void preorder(TreeNode root, ArrayList<String> strings) {
        strings.add(root.name);
        for (TreeNode child : root.children) {
            preorder(child, strings);
        }
    }

    public static void main(String[] args) {
        ThroneInheritance obj = new ThroneInheritance("king");
        obj.birth("king","alice");
        obj.birth("king","bob");
        obj.birth("alice", "duke");
        obj.death("alice");
        List<String> param_3 = obj.getInheritanceOrder();
        System.out.println(param_3);

    }
}