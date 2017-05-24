abstract class Rute {

  protected int rad;
  protected int kolonne;

  protected Rute[] naboer = new Rute[4];

  protected Rute nord;
  protected Rute sor;
  protected Rute ost;
  protected Rute vest;

  protected static Liste<String> losning;


  //Fant ut at jeg ikke trengte konstruktor, siden koordinater settes via en metode naar labyrinten opprettes

  public abstract char tilTegn();

  public abstract boolean erAapning();

  public abstract void gaa(Rute kommerFraRute, String utvei);


  public int hentRad(){
    return rad - 1; //minus 1 fordi vi begynner aa telle fra 1, men arrayer teller fra 0
  }


  public int hentKolonne(){
    return kolonne - 1;
  }


  public void settPosisjon(int kolonne, int rad){
    this.kolonne = kolonne;
    this.rad = rad;
  }


  public void settNaboer(Rute nord, Rute sor, Rute ost, Rute vest){
    this.nord = nord;
    naboer[0] = nord;

    this.sor = sor;
    naboer[1] = sor;

    this.ost = ost;
    naboer[2] = ost;

    this.vest = vest;
    naboer[3] = vest;
  }


  public String hentPosisjon(){
    return "(" + kolonne + ", " + rad + ")";
  }


  public Liste<String> finnUtvei(String utvei){
    losning = new Koe<String>();

    if(this instanceof SortRute){
      losning.settInn("Kan ikke starte paa sort rute.");
    }else{
      gaa(this, utvei);
    }

    return losning;
  }


  public boolean erBlindvei(Rute kommerFraRute){

    boolean alleSorte = true;

    for(Rute n: naboer){
      if(n != null && n != kommerFraRute){
        if(n instanceof HvitRute){
          alleSorte = false;
        }
      }
    }
    return alleSorte;
  }


  public Rute hentNord(){
    return nord;
  }

  public Rute hentSor(){
    return sor;
  }

  public Rute hentOst(){  //hehehe hent ost
    return ost;
  }

  public Rute hentVest(){
    return vest;
  }


}
