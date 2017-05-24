class Node<T extends Comparable<T>>{

  private Node<T> neste;
  private Node<T> forrige;

  private T data;

  public Node(T data){ //konstruktor
    this.data = data;
  }

  public void settNeste(Node<T> neste){
    this.neste = neste;
  }

  public void settForrige(Node<T> forrige){
    this.forrige = forrige;
  }

  public Node<T> hentNeste(){
    return neste;
  }

  public Node<T> hentForrige(){
    return forrige;
  }

  public T hentData(){
    return data;
  }

  public String toString(){
    return data.toString();
  }

  public void kobleUt(){
    if(forrige != null)// && neste == null){
      forrige.settNeste(neste);

    if(neste != null)
      neste.settForrige(forrige);

		neste = null;
		forrige = null;

  }

  public void settInn(Node<T> nesteNode){
    if(nesteNode != null){

      this.settNeste(nesteNode);
      nesteNode.settForrige(this);

    }

  }


  public void settInnBak(Node<T> forrigeNode){
    if(forrigeNode != null){

      this.settForrige(forrigeNode);
      forrigeNode.settNeste(this);
    }

  }



  public void settInnMellom(Node<T> venstre, Node<T> hoyre){
    if (venstre != null) {
			venstre.settNeste(this);
			this.settForrige(venstre); // alt: forrige = venstre;
		}

		if (hoyre != null) {
			hoyre.settForrige(this);
			this.settNeste(hoyre);
		}
  }

  public int compareTo(Node<T> annetElement){

    return data.compareTo(annetElement.hentData());

  }

}
