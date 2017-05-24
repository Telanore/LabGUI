import java.util.Iterator;

public class Stabel<T extends Comparable<T>> extends Lenkeliste<T> {

  @Override
  public void settInn(T element){

    if(forste != null){

      nyNode = new Node<T>(element);

      nyNode.settInn(forste);
      forste = nyNode;

    }

    super.settInn(element);

  }

}
