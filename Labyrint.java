import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

class Labyrint {

  private final int kolonner;
  private final int rader;

  private boolean altPlassert;

  private Rute nyRute;
  private ArrayList<Rute> ruter = new ArrayList<Rute>(); //for overgang fra array til 2d array

  private Rute[][] labyrint;

  private Liste<String> losning;

  private Labyrint(int kolonner, int rader, ArrayList<Character> filTegn){
    labyrint = new Rute[kolonner][rader];
    this.kolonner = kolonner;
    this.rader = rader;


      for(Character c: filTegn){
        //Skulle gjerne ha satt dem inn i labyrint herfra, men fikk bare ArrayIndexOutOfBounds errors
        if(c == '.'){
          nyRute = new HvitRute();
          ruter.add(nyRute);
        }else if(c == '#'){
          nyRute = new SortRute();
          ruter.add(nyRute);
        }
      }
      while(!altPlassert){
      for(int k = 0; k < labyrint.length; k++){
        for(int r = 0; r < labyrint[k].length; r++){
          labyrint[k][r] = ruter.get((k*rader) + r);
          labyrint[k][r].settPosisjon(k+1, r+1);
        }
      }

      for(int k = 0; k < labyrint.length; k++){
        for(int r = 0; r < labyrint[k].length; r++){
          settNaboer(labyrint[k][r]);
        }
      }

      //etter at naboer er satt inn, sjekker om noen av dem er aapninger
      for(int k = 0; k < labyrint.length; k++){
        for(int r = 0; r < labyrint[k].length; r++){

          if(labyrint[k][r] instanceof Aapning){
            altPlassert = true;
          }

          if(labyrint[k][r].erAapning() && !(labyrint[k][r] instanceof Aapning)){ //metode som sjekker om noen av naboene er null - returnerer false om instanceof SortRute
            altPlassert = false; //maa sette inn alt paa nytt dersom den finner en aapning
            ruter.set((k * rader) + r, new Aapning()); //erstatter hvit rute med aapning i ruter listen

          }

        }
      }
    }

  }


  //leser filen og setter igang konstruktor
  public static Labyrint lesFraFil(File fil) throws FileNotFoundException{

    ArrayList<Character> filTegn = new ArrayList<Character>(); //ArrayList saa labyrinten kan bli saa stor som vi vil
    char[] temp; //temp char array saa jeg kan bruke toCharArray();

    Scanner input = new Scanner(fil);

    String[] tempForTall = input.nextLine().split(" "); //holder paa forste linje saa den kan parses til integers

    while(input.hasNextLine()){
      temp = input.nextLine().toCharArray(); //splitter string til char array

      for(int i = 0; i < temp.length; i++){ // setter inn i dynamisk tabell fra char array
        filTegn.add(temp[i]);
      }
    }

    //parser integers fra string array og sender med char array saa de kan lages til ruter
    Labyrint nyLab = new Labyrint(Integer.parseInt(tempForTall[0]), Integer.parseInt(tempForTall[1]), filTegn);

    return nyLab;
  }


  public Rute[][] hentArray(){
    return labyrint;
  }

  public int hentKolonner(){
    return kolonner;
  }

  public int hentRader(){
    return rader;
  }


  public void settNaboer(Rute mid){ //mid er ruten som tildeles naboer

    Rute nord;
    Rute sor;
    Rute ost;
    Rute vest;

    //sjekker om raden over finnes - om ikke, vil nord vaere null, ellers er nord ruten som er paa raden over, i samme radE
    if((mid.hentRad() - 1) < 0){
      nord = null;
    }else{
      nord = labyrint[mid.hentKolonne()][mid.hentRad() - 1];
    }
    //logikken bak denne metoden har gitt meg vondt i hodet
    if((mid.hentRad() + 1) == rader){
      sor = null;
    }else{
      sor = labyrint[mid.hentKolonne()][mid.hentRad() + 1];
    }

    if((mid.hentKolonne() + 1) == kolonner){
      ost = null;
    }else{
      ost = labyrint[mid.hentKolonne() + 1][mid.hentRad()];
    }

    if((mid.hentKolonne() - 1) < 0){
      vest = null;
    }else{
      vest = labyrint[mid.hentKolonne() - 1][mid.hentRad()];
    }

    mid.settNaboer(nord, sor, ost, vest);

  }


  public Liste<String> finnUtveiFra(int rad, int kol){
    String utvei = "";
    losning = labyrint[kol][rad].finnUtvei(utvei);
    return losning;
  }


  static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
    boolean[][] boolskLosning = new boolean[bredde][hoyde];
    java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
    java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s",""));
    while(m.find()) {
        int x = Integer.parseInt(m.group(1))-1;
        int y = Integer.parseInt(m.group(2))-1;
        boolskLosning[x][y] = true;
    }
    return boolskLosning;
  }


  @Override
  public String toString(){
    String printetLabyrint = "\n"+ kolonner + " " + rader;

    for(int r = 0; r < labyrint.length; r++){
      printetLabyrint += "\n";
      for(int k = 0; k < labyrint[r].length; k++){
        printetLabyrint += labyrint[r][k].tilTegn();
      }
    }
    return printetLabyrint;
  }


  public void settMinimalUtskrift(){
    //wat do?
  }

}
