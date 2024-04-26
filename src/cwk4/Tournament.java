package cwk4;

import java.util.*;
import java.io.*;

import cwk4.Champion;

import cwk4.ChallengeType;

/**
 * This interface specifies the behaviour expected from CARE
 * as required for 5COM2007 Cwk 4
 *
 * @author
 */

public class Tournament implements CARE {

    private String vizier;
    private int Treasury;
    private boolean defeated = false;
    private List<Champion> champions;
    private List<Champion> viziersChampions = new ArrayList<>(List.of());
    private List<Challenge> challenges;

//**************** CARE ************************** 

    /**
     * Constructor requires the name of the vizier
     *
     * @param viz the name of the vizier
     */
    public Tournament(String viz) {
        vizier = viz;
        Treasury = 1000;
//        viziersChampions = null;
        setupChampions();
        setupChallenges();
    }

    /**
     * Constructor requires the name of the vizier and the
     * name of the file storing challenges
     *
     * @param viz      the name of the vizier
     * @param filename name of file storing challenges
     */
    public Tournament(String viz, String filename)  //Task 3.5
    {


        setupChampions();
        readChallenges(filename);
    }


    /**
     * Returns a String representation of the state of the game,
     * including the name of the vizier, state of the treasury,
     * whether defeated or not, and the champions currently in the
     * team,(or, "No champions" if team is empty)
     *
     * @return a String representation of the state of the game,
     * including the name of the vizier, state of the treasury,
     * whether defeated or not, and the champions currently in the
     * team,(or, "No champions" if team is empty)
     **/
    public String toString() {
        String s = "\nVizier: " + vizier + "\n";
        s += "Treasury: " + Treasury + "\n";
        s += "Defeated: " + (isDefeated() ? "Yes" : "No") + "\n";
        s += "Champions in Team: \n";
        if (getTeam() == "No Champions in Vizier's team") {
            s += "No champions entered";
        } else {
            s += getTeam();
        }

        return s;
    }


    /**
     * returns true if Treasury <=0 and the vizier's team has no
     * champions which can be retired.
     *
     * @returns true if Treasury <=0 and the vizier's team has no
     * champions which can be retired.
     */
    public boolean isDefeated() {
//        if ((Treasury <= 0) && ())

        return false;
    }

    /**
     * returns the amount of money in the Treasury
     *
     * @returns the amount of money in the Treasury
     */
    public int getMoney() {
        return 0;
    }


    /**
     * Returns a String representation of all champions in the reserves
     *
     * @return a String representation of all champions in the reserves
     **/
    public String getReserve() {
        String s = "************ Champions available in reserves********\n";
        for (Champion champion : champions) {
            if (champion.isInReserve()) {
                s += champion + "\n";
                s += "******************************\n";
            }
        }

        return s;

    }


    /**
     * Returns details of the champion with the given name.
     * Champion names are unique.
     *
     * @return details of the champion with the given name
     **/
    public String getChampionDetails(String nme) {
// Convert the input parameter to lowercase
        String lowercaseName = nme.toLowerCase();

        for (Champion champion : champions) {
            // Convert the champion name to lowercase for case-insensitive comparison
            String championName = champion.getName().toLowerCase();

            // Check if the lowercase champion name matches the lowercase input parameter
            if (championName.equals(lowercaseName)) {
                return champion.toString();
            }
        }
        return "\nNo such champion";
    }

    /**
     * returns whether champion is in reserve
     *
     * @param nme champion's name
     * @return true if champion in reserve, false otherwise
     */
    public boolean isInReserve(String nme) {
        return (false);
    }

    // ***************** Team champions ************************   

    /**
     * Allows a champion to be entered for the vizier's team, if there
     * is enough money in the Treasury for the entry fee.The champion's
     * state is set to "active"
     * 0 if champion is entered in the vizier's team,
     * 1 if champion is not in reserve,
     * 2 if not enough money in the treasury,
     * -1 if there is no such champion
     *
     * @param nme represents the name of the champion
     * @return as shown above
     **/
    public int enterChampion(String nme) {

        // Convert the input parameter to lowercase

        String lowercaseName = nme.toLowerCase();


        for (Champion champion : champions) {

            // Convert the champion name to lowercase for case-insensitive comparison

            String championName = champion.getName().toLowerCase();


            // Check if the lowercase champion name matches the lowercase input parameter

            if (championName.equals(lowercaseName)) {

                // Check if the champion is in the reserve

                if (champion.isInReserve()) {

                    // Check if there is enough money in the treasury for the entry fee

                    if (Treasury >= champion.getEntryFee()) {

                        // Deduct the entry fee from the treasury

                        Treasury -= champion.getEntryFee();

                        // Set the champion as active

                        champion.setAsEntered();


                        // Add the champion to the vizier's team if it's not already in the team

                        if (!viziersChampions.contains(champion)) {

                            viziersChampions.add(champion);
                            champion.setAsEntered();

                        }


                        return 0; // Champion entered successfully

                    } else {

                        return 2; // Not enough money in the treasury

                    }

                } else {

                    return 1; // Champion not in reserve

                }

            }

        }

        return -1; // No such champion
    }

    /**
     * Returns true if the champion with the name is in
     * the vizier's team, false otherwise.
     *
     * @param nme is the name of the champion
     * @return returns true if the champion with the name
     * is in the vizier's team, false otherwise.
     **/
    public boolean isInViziersTeam(String nme) {
        return false;
    }

    /**
     * Removes a champion from the team back to the reserves (if they are in the team)
     * Pre-condition: isChampion()
     * 0 - if champion is retired to reserves
     * 1 - if champion not retired because disqualified
     * 2 - if champion not retired because not in team
     * -1 - if no such champion
     *
     * @param nme is the name of the champion
     * @return as shown above
     **/
    public int retireChampion(String nme) {
        return -1;
    }


    /**
     * Returns a String representation of the champions in the vizier's team
     * or the message "No champions entered"
     *
     * @return a String representation of the champions in the vizier's team
     **/
    public String getTeam() {
        String s = "************ Vizier's Team of champions********\n";

        if (viziersChampions.isEmpty()) {
            s = "No Champions in Vizier's team";
        } else {
            // Iterate through champions in the team and append their details
            for (Champion champion : champions) {
                if (champion.isEntered()) {
                    s += champion.getName() + "\n";
                }
            }
        }

        return s;
    }

    /**
     * Returns a String representation of the disquakified champions in the vizier's team
     * or the message "No disqualified champions "
     *
     * @return a String representation of the disqualified champions in the vizier's team
     **/
    public String getDisqualified() {
        String s = "************ Vizier's Disqualified champions********";


        return s;
    }

//**********************Challenges************************* 

    /**
     * returns true if the number represents a challenge
     *
     * @param num is the  number of the challenge
     * @return true if the  number represents a challenge
     **/
    public boolean isChallenge(int num) {
        return (false);
    }

    /**
     * Provides a String representation of an challenge given by
     * the challenge number
     *
     * @param num the number of the challenge
     * @return returns a String representation of a challenge given by
     * the challenge number
     **/
    public String getChallenge(int num) {
        for (Challenge challenge : challenges) {

            if (challenge.getNumber() == num) {

                return challenge.toString();

            }
        }

        return "\nNo such challenge";
    }

    /**
     * Provides a String representation of all challenges
     *
     * @return returns a String representation of all challenges
     **/
    public String getAllChallenges() {
        String s = "\n************ All Challenges ************\n";
        for (Challenge challenge : challenges) {
            s += challenge + "\n";
            s += "\n************************\n";
        }
        return s;
    }


    /**
     * Retrieves the challenge represented by the challenge
     * number.Finds a champion from the team who can meet the
     * challenge. The results of meeting a challenge will be
     * one of the following:
     * 0 - challenge won by champion, add reward to the treasury,
     * 1 - challenge lost on skills  - deduct reward from
     * treasury and record champion as "disqualified"
     * 2 - challenge lost as no suitable champion is  available, deduct
     * the reward from treasury
     * 3 - If a challenge is lost and vizier completely defeated (no money and
     * no champions to withdraw)
     * -1 - no such challenge
     *
     * @param chalNo is the number of the challenge
     * @return an int showing the result(as above) of fighting the challenge
     */
    public int meetChallenge(int chalNo) {
        if (chalNo < 1 || chalNo > challenges.size()) {
            return -1;
        } // no such challenge

        Challenge challenge = retreiveChallenge(chalNo);

        List<Champion> eligibleChampions = new ArrayList<>();


        for (Champion champion : viziersChampions) {

            if (champion.getChampionType().toString() == challenge.getType().toString()) {

                eligibleChampions.add(champion);

            }

        }


        if (eligibleChampions.isEmpty()) {

            return 2; // no suitable champion is available

        }


        Collections.sort(eligibleChampions, (c1, c2) -> Integer.compare(c2.getSkillLevel(), c1.getSkillLevel()));

        Champion selectedChampion = eligibleChampions.get(0);


        int outcome = 0; // challenge won by champion

        if (Math.random() < 0.5) {

            outcome = 1; // challenge lost on skills

            selectedChampion.setChampionState(ChampionState.DISQUALIFIED);

        }


// Check if Treasury value is sufficient before deducting or adding the reward

        if (outcome == 1 || isDefeated()) {

            if (Treasury >= challenge.getReward()) {

                Treasury -= challenge.getReward();

            } else {

                System.out.println("Insufficient Treasury value to meet the challenge.");

                return -2; // not enough money in the treasury

            }

        } else {

            Treasury += challenge.getReward();

        }

        return outcome;
    }

    private Challenge retreiveChallenge(int chalNo) {
        for (Challenge challenge : challenges){
            if (challenge.getNumber() == chalNo){
                return challenge;
            }
        }
        return null;
    }


    //****************** private methods for Task 3 functionality*******************
    //*******************************************************************************
    private void setupChampions() {
        champions = List.of(

                new Champion("Ganfrank", 7, true, 400, "transmutation", "sword", false, ChampionType.WIZARD),

                new Champion("Rudolf", 6, true, 400, "invisibility", "sword", false, ChampionType.WIZARD),

                new Champion("Elblond", 1, true, 150, "invisibility", "sword", false, ChampionType.WARRIOR),

                new Champion("Flimsi", 2, true, 200, "invisibility", "bow", false, ChampionType.WARRIOR),

                new Champion("Drabina", 7, true, 500, "invisibility", "bow", false, ChampionType.DRAGON),

                new Champion("Golum", 7, true, 500, "invisibility", "bow", true, ChampionType.DRAGON),

                new Champion("Argon", 9, true, 900, "invisibility", "mace", true, ChampionType.WARRIOR),

                new Champion("Neon", 2, false, 300, "translocation", "mace", true, ChampionType.WIZARD),

                new Champion("Xenon", 7, false, 500, "translocation", "mace", true, ChampionType.DRAGON),

                new Champion("Atlanta", 5, false, 500, "translocation", "bow", true, ChampionType.WARRIOR),

                new Champion("Krypton", 8, false, 300, "fireballs", "bow", true, ChampionType.WIZARD),

                new Champion("Hedwig", 1, true, 400, "flying", "bow", true, ChampionType.WIZARD)

        );

    }

    private void setupChallenges() {
        challenges = List.of(

                new Challenge(1, ChallengeType.MAGIC, "Borg", 3, 100),

                new Challenge(2, ChallengeType.FIGHT, "Huns", 3, 120),

                new Challenge(3, ChallengeType.MYSTERY, "Ferengi", 3, 150),

                new Challenge(4, ChallengeType.MAGIC, "Vandal", 9, 200),

                new Challenge(5, ChallengeType.MYSTERY, "Borg", 7, 90),

                new Challenge(6, ChallengeType.FIGHT, "Goth", 8, 45),

                new Challenge(7, ChallengeType.MAGIC, "Frank", 10, 200),

                new Challenge(8, ChallengeType.FIGHT, "Sith", 10, 170),

                new Challenge(9, ChallengeType.MYSTERY, "Cardashian", 9, 300),

                new Challenge(10, ChallengeType.FIGHT, "Jute", 2, 300),

                new Challenge(11, ChallengeType.MAGIC, "Celt", 2, 250),

                new Challenge(12, ChallengeType.MYSTERY, "Celt", 1, 250)

        );
    }

    // Possible useful private methods
//     private Challenge getAChallenge(int no)
//     {
//         
//         return null;
//     }
//    
//     private Champion getChampionForChallenge(Challenge chal)
//     {
//         
//         return null;
//     }

    //*******************************************************************************
    //*******************************************************************************

    /************************ Task 3.5 ************************************************/

    // ***************   file write/read  *********************

    /**
     * reads challenges from a comma-separated textfile and stores in the game
     *
     * @param filename of the comma-separated textfile storing information about challenges
     */
    public void readChallenges(String filename) {

    }

    /**
     * reads all information about the game from the specified file
     * and returns a CARE reference to a Tournament object, or null
     *
     * @param fname name of file storing the game
     * @return the game (as a Tournament object)
     */
    public Tournament loadGame(String fname) {   // uses object serialisation
        Tournament yyy = null;

        return yyy;
    }

    /**
     * Writes whole game to the specified file
     *
     * @param fname name of file storing requests
     */
    public void saveGame(String fname) {
        // uses object serialisation

    }


}



