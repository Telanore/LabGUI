class Aapning extends HvitRute {

  //Fungerer som basissteg - naar den rekursive funksjonen kommer til en Aapningsrute, slutter den aa kalle og returnerer
  @Override
  public void gaa(Rute kommerFraRute, String utvei){
    utvei += hentPosisjon(); //legger til posisjon i utvei

    losning.settInn(utvei);
  }
}
