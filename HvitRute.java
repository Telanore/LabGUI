class HvitRute extends Rute {

  @Override
  public void gaa(Rute kommerFraRute, String utvei){

    utvei += hentPosisjon() + " -> ";

    for(Rute n: naboer){
      //sjekker om naboer er null, eller ruten vi kom fra
      //om ikke, kaller paa gaa()
      if(n != null && n != kommerFraRute){
        n.gaa(this, utvei);

      }
    }
  }


  public char tilTegn(){
    return '.';
  }

  //hjelpemetode for aa sjekke om denne hvite ruten er en mulig aapning
  public boolean erAapning(){
    return nord == null || sor == null || ost == null || vest == null;
  }

}
