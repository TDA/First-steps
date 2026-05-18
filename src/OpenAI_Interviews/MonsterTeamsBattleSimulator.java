package OpenAI_Interviews;

import java.util.ArrayList;
import java.util.List;

import static OpenAI_Interviews.TestHelpers.assertEquals;
import static OpenAI_Interviews.TestHelpers.runTest;

// Monster Teams Battle Simulator: Problem Statement
//
//Two teams of monsters fight until one team is fully defeated. You will design types to represent teams, monsters, and the battle outcome, and implement a battle(team1, team2) function that simulates the fight and returns a winner plus a battle log.
//Interface (implement in your language)
//
//battle(team1, team2) -> Outcome
//
//Types (or equivalents):
//- Monster
//- Team
//- Outcome
//
//Notes:
//
//    Use equivalent types and naming in your language.
//    The exact class/struct definitions are up to you, but they must support the rules and output described below.
//
//Definitions
//Team
//
//    name (string)
//    monsters (ordered list of Monster)
//
//Monster
//
//    name (string)
//    lifePoints (non-negative integer)
//    attack (non-negative integer)
//
//Fight Rules
//
//    Turn order
//        The team passed as the first argument to battle(team1, team2) attacks first.
//        Monsters fight in the same order they appear in each team’s list.
//    One-on-one combat
//        The current monster from Team 1 fights the current monster from Team 2.
//        Attacks alternate turn-by-turn:
//            The attacker subtracts its attack from the defender’s lifePoints.
//            When a defender reaches 0 life points, it is defeated immediately.
//    Replacing defeated monsters
//        When a monster is defeated, the next monster from that same team steps in.
//        The battle continues until one team has no remaining monsters alive.
//    Damage and life points
//        Damage is applied by subtraction.
//        Life points must never become negative in the final state (cap at 0 after damage).
//        Numeric limits: You may assume all values and intermediate computations (including doubled damage in the follow-up) fit within your language’s normal integer range (no overflow). In a real interview, confirm expectations and use wider integer types (e.g., 64-bit) or clamping if needed.
//
//Output
//
//Outcome contains:
//
//    winningTeamName (string)
//    log (ordered list of strings describing events)
//
//The log should include key events, such as:
//
//    Team turn markers (e.g., "Blue turn" if the team name is "Blue", or "Team Blue turn" if the team name is "Team Blue")
//    Attack messages (e.g., "Wolf attacks Bear for 5 damage")
//    Defeat messages (e.g., "Bear is defeated")
//
//Example
//Input teams
//
//Team Blue
//
//    Dog: 3 life points, 2 attack
//    Wolf: 4 life points, 1 attack
//
//Team Red
//
//    Cat: 3 life points, 3 attack
//    Tiger: 4 life points, 5 attack
//
//Sample log (illustrative)
//
//    Team Blue turn
//    Dog attacks Cat for 2 damage
//    Team Red turn
//    Cat attacks Dog for 3 damage
//    Dog is defeated
//    Team Blue turn
//    Wolf attacks Cat for 1 damage
//    Cat is defeated
//    Team Red turn
//    Tiger attacks Wolf for 5 damage
//    Wolf is defeated
//    Team Red wins
//
//Follow-up: Types & Weaknesses (Damage Modifiers)
//
//Extend Monster with:
//
//    type (a specific type; represent as enum/string)
//    weakness (a specific type)
//
//During an attack, compute effective damage:
//
//    If attacker.type == defender.weakness
//        damage is doubled
//    Else if defender.type == attacker.weakness
//        damage is halved (rounded down)
//    Otherwise
//        damage is unchanged
//
//Logging requirement for modifiers
//
//If a modifier affected damage, the attack log must mention it, for example:
//
//    "Dragon (Fire) attacks Troll (Earth) for 10 damage (super effective)"
//    "Troll (Earth) attacks Dragon (Fire) for 2 damage (not very effective)"
//
//Exact phrasing is up to you, but it must clearly indicate advantage/disadvantage when applicable.


public class MonsterTeamsBattleSimulator {
    record Monster(String name, int lifePoints, int attack, String type, String weakness) {
        Monster(String name, int lifePoints, int attack) {
            this(name, lifePoints, attack, null, null);
        }

        Monster withLifePoints(int newLifePoints) {
            return new Monster(name, Math.max(0, newLifePoints), attack, type, weakness);
        }
    }

    record Team(String name, List<Monster> monsters) {
    }

    record Outcome(String winningTeamName, List<String> log) {
    }

    Outcome battle(Team team1, Team team2) {
        List<Monster> team1Monsters = new ArrayList<>(team1.monsters());
        List<Monster> team2Monsters = new ArrayList<>(team2.monsters());
        List<String> log = new ArrayList<>();

        int team1Index = firstAliveIndex(team1Monsters, 0);
        int team2Index = firstAliveIndex(team2Monsters, 0);
        boolean team1Turn = true;

        while (team1Index < team1Monsters.size() && team2Index < team2Monsters.size()) {
            if (team1Turn) {
                log.add(team1.name() + " turn");
                team2Index = attack(team1Monsters, team1Index, team2Monsters, team2Index, log);
            } else {
                log.add(team2.name() + " turn");
                team1Index = attack(team2Monsters, team2Index, team1Monsters, team1Index, log);
            }

            team1Turn = !team1Turn;
        }

        String winningTeam = team1Index < team1Monsters.size() ? team1.name() : team2.name();
        log.add(winningTeam + " wins");
        return new Outcome(winningTeam, log);
    }

    private int attack(
            List<Monster> attackers,
            int attackerIndex,
            List<Monster> defenders,
            int defenderIndex,
            List<String> log
    ) {
        Monster attacker = attackers.get(attackerIndex);
        Monster defender = defenders.get(defenderIndex);
        Damage damage = calculateDamage(attacker, defender);

        log.add(formatAttack(attacker, defender, damage));

        Monster updatedDefender = defender.withLifePoints(defender.lifePoints() - damage.amount());
        defenders.set(defenderIndex, updatedDefender);

        if (updatedDefender.lifePoints() == 0) {
            log.add(updatedDefender.name() + " is defeated");
            return firstAliveIndex(defenders, defenderIndex + 1);
        }

        return defenderIndex;
    }

    private int firstAliveIndex(List<Monster> monsters, int startIndex) {
        int index = startIndex;
        while (index < monsters.size() && monsters.get(index).lifePoints() == 0) {
            index++;
        }
        return index;
    }

    private Damage calculateDamage(Monster attacker, Monster defender) {
        int damage = attacker.attack();
        String modifier = "";

        if (attacker.type() != null && attacker.type().equals(defender.weakness())) {
            damage *= 2;
            modifier = " (super effective)";
        } else if (defender.type() != null && defender.type().equals(attacker.weakness())) {
            damage /= 2;
            modifier = " (not very effective)";
        }

        return new Damage(damage, modifier);
    }

    private String formatAttack(Monster attacker, Monster defender, Damage damage) {
        String attackerName = formatMonsterName(attacker);
        String defenderName = formatMonsterName(defender);
        return attackerName + " attacks " + defenderName + " for " + damage.amount() + " damage" + damage.modifier();
    }

    private String formatMonsterName(Monster monster) {
        if (monster.type() == null || monster.type().isEmpty()) return monster.name();
        return monster.name() + " (" + monster.type() + ")";
    }

    record Damage(int amount, String modifier) {
    }

    public static void main(String[] args) {
        runTest("simulates sample battle", () -> {
            MonsterTeamsBattleSimulator simulator = new MonsterTeamsBattleSimulator();
            Team blue = new Team("Team Blue", List.of(
                    new Monster("Dog", 3, 2),
                    new Monster("Wolf", 4, 1)
            ));
            Team red = new Team("Team Red", List.of(
                    new Monster("Cat", 3, 3),
                    new Monster("Tiger", 4, 5)
            ));

            Outcome outcome = simulator.battle(blue, red);

            assertEquals("Team Red", outcome.winningTeamName());
            assertEquals(List.of(
                    "Team Blue turn",
                    "Dog attacks Cat for 2 damage",
                    "Team Red turn",
                    "Cat attacks Dog for 3 damage",
                    "Dog is defeated",
                    "Team Blue turn",
                    "Wolf attacks Cat for 1 damage",
                    "Cat is defeated",
                    "Team Red turn",
                    "Tiger attacks Wolf for 5 damage",
                    "Wolf is defeated",
                    "Team Red wins"
            ), outcome.log());
        });

        runTest("defeated monster is replaced by next monster immediately", () -> {
            MonsterTeamsBattleSimulator simulator = new MonsterTeamsBattleSimulator();
            Team first = new Team("First", List.of(
                    new Monster("A", 1, 10),
                    new Monster("B", 5, 1)
            ));
            Team second = new Team("Second", List.of(
                    new Monster("C", 2, 1),
                    new Monster("D", 2, 1)
            ));

            Outcome outcome = simulator.battle(first, second);

            assertEquals("First", outcome.winningTeamName());
            assertEquals(true, outcome.log().contains("C is defeated"));
            assertEquals(true, outcome.log().contains("D is defeated"));
        });

        runTest("applies type advantage and weakness modifiers", () -> {
            MonsterTeamsBattleSimulator simulator = new MonsterTeamsBattleSimulator();
            Team fire = new Team("Fire Team", List.of(
                    new Monster("Dragon", 10, 5, "Fire", "Water")
            ));
            Team earth = new Team("Earth Team", List.of(
                    new Monster("Troll", 10, 5, "Earth", "Fire")
            ));

            Outcome outcome = simulator.battle(fire, earth);

            assertEquals("Fire Team", outcome.winningTeamName());
            assertEquals("Dragon (Fire) attacks Troll (Earth) for 10 damage (super effective)", outcome.log().get(1));
            assertEquals("Troll is defeated", outcome.log().get(2));
        });

        runTest("applies not very effective modifier with rounded down damage", () -> {
            MonsterTeamsBattleSimulator simulator = new MonsterTeamsBattleSimulator();
            Team earth = new Team("Earth Team", List.of(
                    new Monster("Troll", 10, 5, "Earth", "Fire")
            ));
            Team fire = new Team("Fire Team", List.of(
                    new Monster("Dragon", 10, 5, "Fire", "Water")
            ));

            Outcome outcome = simulator.battle(earth, fire);

            assertEquals("Fire Team", outcome.winningTeamName());
            assertEquals("Troll (Earth) attacks Dragon (Fire) for 2 damage (not very effective)", outcome.log().get(1));
        });
    }
}
