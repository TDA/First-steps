/**
 * Created by schandramouli on 11/29/15.
 */
public class Engine {
    String type;
    int maxHP;

    public Engine(String type, int maxHP) {
        this.type = type;
        this.maxHP = maxHP;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }
}
