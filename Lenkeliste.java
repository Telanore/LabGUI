import java.util.Iterator;

abstract class Lenkeliste<T extends Comparable<T>> implements Liste<T>{

  protected Node<T> forste;
  protected Node<T> siste;
  protected Node<T> nyNode;

  protected int listeLengde;


  public Lenkeliste(){
    listeLengde = 0;
  }


  public int storrelse(){
    return listeLengde;
  }


  public boolean erTom(){

    return listeLengde == 0;

  }


  public void settInn(T element){
    if(forste == null){

      forste = new Node<T>(element);
      siste = forste;

    }
    listeLengde++;

  }


  public T fjern(){

    if(forste != null && forste != siste){

      T temp = forste.hentData();
      forste = forste.hentNeste();
      forste.hentForrige().kobleUt(); //henter nye forste's forrige, som er originale forste

      listeLengde--;
      return temp;

    }else if(forste != null && forste == siste){

      T temp = forste.hentData();
      forste.kobleUt();
      forste = null;
      siste = null;

      listeLengde--;
      return temp;

    }else{
      listeLengde--;
      return null;

    }
  }


  public Iterator iterator(){
    return new ListeIterator();
  }



  private class ListeIterator implements Iterator<T>{

      private Node<T> itr;

      public ListeIterator(){
        itr = forste;

      }


      public boolean hasNext(){
        return itr != null;
      }


      public T next(){
        T temp = itr.hentData();
        itr = itr.hentNeste();
        return temp;
      }


      public void remove(){
        throw new UnsupportedOperationException();
      }

  }

}
