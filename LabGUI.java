
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Background;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.ArrayList;

public class LabGUI extends Application {

  private Stage vindu;
  private HBox hbox;
  private VBox root;
  private Labyrint lab;
  private Liste<String> losninger;
  private RuteBoks[][] ruter;
  Label antallLosninger;

  @Override
  public void start(Stage vindu){
    this.vindu = vindu;

    hbox = lagHBox();
    root = new VBox(hbox);

    Scene scene = new Scene(root);

    vindu.setScene(scene);
    vindu.setTitle("Labyrint");
    vindu.show();

  }


  /*Oppretter de tre objektene i vinduet naar det forst aapner: Velg fil knapp,
  et tekstfelt hvor filnavnet/path legges inn, og en Last inn knapp som leser
  den valgte filen og oppretter selve labyrinten i vinduet*/


  private HBox lagHBox(){
    //tekstfelt hvor filens path ligger
    TextField filFelt = new TextField();
    filFelt.setText("2.in");
    //velg fil knapp - anonym klasse handterer selve ActionEventen
    Button velgFil = new Button("Velg fil...");

    velgFil.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent e){
        FileChooser fc = new FileChooser();
        fc.setTitle("Velg labyrintfil");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Tekstfiler", "*.in", "*.txt");
        fc.getExtensionFilters().add(filter);
        File valgtFil = fc.showOpenDialog(vindu);

        if(valgtFil != null){
          filFelt.setText(valgtFil.getPath());
        }
      }
    });


    Button lastInn = new Button("Last inn");

    lastInn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e){
        antallLosninger = new Label("");

        try{

          //henter fil path fra tekstfeltet
          File labyrintFil = new File(filFelt.getText());
          //lager labyrint fra statisk fabrikkmetode i Labyrint
          lab = Labyrint.lesFraFil(labyrintFil);
          //oppretter et gridpane som skal holde rutene
          GridPane grid = new GridPane();
          ruter = new RuteBoks[lab.hentKolonner()][lab.hentRader()];
          RuteBoks rute;
          for(int k = 0; k < ruter.length; k++){
            for(int r = 0; r < ruter[k].length; r++){
              if(lab.hentArray()[k][r] instanceof HvitRute){
                rute = new RuteBoks(30, 30, Color.WHITE, lab.hentArray()[k][r]);
                ruter[k][r] = rute;
              }else{
                rute = new RuteBoks(30, 30, Color.BLACK, lab.hentArray()[k][r]);
                ruter[k][r] = rute;
              }
              grid.add(rute, k, r);
            }
          }
          grid.setAlignment(Pos.CENTER);
          root.getChildren().add(grid);
          root.getChildren().add(antallLosninger);
          vindu.sizeToScene();

        }catch (Exception f){}
      }
    });

    return new HBox(50, velgFil, filFelt, lastInn);
  }


  public void fargUtvei(ArrayList<boolean[][]> utveier){
    for(boolean[][] b: utveier){
      for(int r = 0; r < b.length; r++){
        for(int k = 0; k < b[r].length; k++){
          if(b[r][k]){
            ruter[r][k].setFill(Color.GREEN);
          }else{
            if(ruter[r][k].hentRute() instanceof HvitRute){
              ruter[r][k].setFill(Color.WHITE);
            }else{
              ruter[r][k].setFill(Color.BLACK);
            }
          }
        }
      }
    }
    antallLosninger.setText("Antall losninger: " + utveier.size());

  }



  private class RuteBoks extends Rectangle {
    private Rute rute;

    //custom rectangle klasse, for aa faa tilgang til Rute i konstruktor
    public RuteBoks(int bredde, int hoyde, Paint farge, Rute rute){
      super(bredde, hoyde, farge);
      this.rute = rute;

      setOnMouseClicked(new EventHandler<MouseEvent>(){

        ArrayList<boolean[][]> utveier = new ArrayList<boolean[][]>();

        @Override
        public void handle(MouseEvent e){
          System.out.println(rute.hentPosisjon());
          //lagrer losninger i liste
          losninger = lab.finnUtveiFra(rute.hentKolonne(), rute.hentRad());
          for(String s: losninger){
            utveier.add(lab.losningStringTilTabell(s, lab.hentKolonner(), lab.hentRader()));
          }
          fargUtvei(utveier);
        }

      });
    }

    public Rute hentRute(){
      return rute;
    }

  }

}
