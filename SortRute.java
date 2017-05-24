class SortRute extends Rute {


  @Override
  public void gaa(Rute kommerFraRute, String utvei){
    return; //kan ikke gaa paa sorte ruter
  }


  public char tilTegn(){
    return '#';
  }


  public boolean erAapning(){
    return false; //sorte ruter er aldri aapninger
  }

}
