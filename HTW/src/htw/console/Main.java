package htw.console;

import htw.HtwMessageReceiver;
import htw.HuntTheWumpus;
import htw.factory.HtwFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static htw.HuntTheWumpus.Direction.*;

public class Main implements HtwMessageReceiver {
  private static HuntTheWumpus game;
  private static final List<String> caverns = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    game = HtwFactory.makeGame("htw.game.HuntTheWumpusGame", new Main());
    createMap();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    game.makeRestCommand().execute();
    while (true) {
      System.out.println(game.getPlayerCavern());
      HuntTheWumpus.Command c = game.makeRestCommand();
      System.out.println(">");
      String command = br.readLine();
      if (command.equalsIgnoreCase("e"))
        c = game.makeMoveCommand(EAST);
      else if (command.equalsIgnoreCase("w"))
        c = game.makeMoveCommand(WEST);
      else if (command.equalsIgnoreCase("n"))
        c = game.makeMoveCommand(NORTH);
      else if (command.equalsIgnoreCase("s"))
        c = game.makeMoveCommand(SOUTH);
      else if (command.equalsIgnoreCase("r"))
        c = game.makeRestCommand();
      else if (command.equalsIgnoreCase("sw"))
        c = game.makeShootCommand(WEST);
      else if (command.equalsIgnoreCase("se"))
        c = game.makeShootCommand(EAST);
      else if (command.equalsIgnoreCase("sn"))
        c = game.makeShootCommand(NORTH);
      else if (command.equalsIgnoreCase("ss"))
        c = game.makeShootCommand(SOUTH);
      else if (command.equalsIgnoreCase("q"))
        return;

      c.execute();
    }
  }

  private static void createMap() {
    int ncaverns = (int) (Math.random() * 50.0 + 10.0);
    while (ncaverns-- > 0)
      caverns.add("cavern " + ncaverns);

    for (String cavern : caverns) {
      maybeConnectCavern(cavern, NORTH);
      maybeConnectCavern(cavern, SOUTH);
      maybeConnectCavern(cavern, EAST);
      maybeConnectCavern(cavern, WEST);
    }

    String playerCavern = anyCavern();
    game.setPlayerCavern(playerCavern);
    game.setWumpusCavern(anyOther(playerCavern));
    game.addBatCavern(anyOther(playerCavern));
    game.addBatCavern(anyOther(playerCavern));
    game.addBatCavern(anyOther(playerCavern));

    game.addPitCavern(anyOther(playerCavern));
    game.addPitCavern(anyOther(playerCavern));
    game.addPitCavern(anyOther(playerCavern));

    game.setQuiver(5);
  }

  private static void maybeConnectCavern(String cavern, HuntTheWumpus.Direction direction) {
    if (Math.random() > .2)
      game.connectCavern(cavern, anyOther(cavern), direction);
  }

  private static String anyOther(String cavern) {
    String otherCavern = cavern;
    while (cavern.equals(otherCavern)) {
      otherCavern = anyCavern();
    }
    return otherCavern;
  }

  private static String anyCavern() {
    return caverns.get((int) (Math.random() * caverns.size()));
  }

  public void noPassage() {
    System.out.println("No Passage.");
  }

  public void hearBats() {
    System.out.println("You hear chirping.");
  }

  public void hearPit() {
    System.out.println("You hear wind.");
  }

  public void smellWumpus() {
    System.out.println("There is a terrible smell.");
  }

  public void passage(HuntTheWumpus.Direction direction) {
    System.out.println("You can go " + direction.name());
  }

  public void noArrows() {
    System.out.println("You have no arrows.");
  }

  public void arrowShot() {
    System.out.println("Thwang!");
  }

  public void playerShootsSelfInBack() {
    System.out.println("You shot yourself in the back.");
    System.exit(0);
  }

  public void playerKillsWumpus() {
    System.out.println("You killed the Wumpus.");
    System.exit(0);
  }


  public void playerShootsWall() {
    System.out.println("You shot the wall and killed yourself.");
    System.exit(0);
  }

  public void arrowsFound(Integer arrowsFound) {
    System.out.println("You found " + arrowsFound + " arrow" + (arrowsFound == 1 ? "" : "s") + ".");
  }

  public void fellInPit() {
    System.out.println("You fell in a pit.");
    System.exit(0);
  }

  public void playerMovesToWumpus() {
    System.out.println("You walked into the waiting arms of the Wumpus.");
    System.exit(0);
  }

  public void wumpusMovesToPlayer() {
    System.out.println("The Wumpus has found you.");
    System.exit(0);
  }

  public void batsTransport() {
    System.out.println("Some bats carried you away.");
  }
}
