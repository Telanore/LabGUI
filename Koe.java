import java.util.Iterator;

public class Koe<T extends Comparable<T>> extends Lenkeliste<T> {

  @Override
  public void settInn(T element){

    if(siste != null){

      nyNode = new Node<T>(element);
      nyNode.settInnBak(siste);
      siste = nyNode;

    }

    super.settInn(element);

  }

}
