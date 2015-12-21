package htw.game;

import htw.HuntTheWumpus;

import java.util.*;

class Cavern {
  public static final Cavern NULL = new NullCavern();

  private String name;
  private Map<HuntTheWumpus.Direction, Cavern> connections = new HashMap<>();

  public Cavern(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public boolean isNamed(String name) {
    return this.name.equals(name);
  }

  public Cavern findDestination(HuntTheWumpus.Direction direction) {
    Cavern destination = connections.get(direction);
    return (destination != null) ? destination : new NullCavern();
  }

  public Set<HuntTheWumpus.Direction> availableDirections() {
    return connections.keySet();
  }

  public List<Cavern> connectedCaverns() {
    return new ArrayList<>(connections.values());
  }

  public void addConnection(Cavern to, HuntTheWumpus.Direction direction) {
    connections.put(direction, to);
  }

  public boolean hasConnectionGoing(HuntTheWumpus.Direction direction) {
    return !findDestination(direction).isNull();
  }

  public boolean isNull() {
    return false;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Cavern) {
      Cavern c = (Cavern) obj;
      return getName().equals(c.getName());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }
}
