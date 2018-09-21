package Casino;

import java.util.ArrayList;

public class Games {
  Tourmanents tourmanents;
  ArrayList<Player> playerArrayList;
  static String[] typeOfGames = new String[]{"Poker", "Blackjack", "Roulette"};
  String game;

  public Games(Tourmanents tourmanents, ArrayList<Player> playerArrayList, String game) {
    this.tourmanents = tourmanents;
    this.playerArrayList = playerArrayList;
    this.game = game;
  }
}
