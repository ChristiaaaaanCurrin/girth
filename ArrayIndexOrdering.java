import java.util.Comparator;

public class ArrayIndexOrdering implements Comparator<Integer> {
  private int[] sortingKey;
  public ArrayIndexOrdering(int[] sortingKey) {
    this.sortingKey = sortingKey;
  }

  public int[] getSortingKey() {
    return sortingKey;
  }

  public void setSortingKey(int[] key) {
    sortingKey = key;
  }

  @Override
  public int compare(Integer i, Integer j) {
    return Integer.compare(sortingKey[i], sortingKey[j]);
  }
}
