package OpenAI_Interviews;

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
}
