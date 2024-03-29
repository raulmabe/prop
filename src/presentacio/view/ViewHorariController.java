package presentacio.view;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.fxml.FXML;
        import javafx.geometry.Insets;
        import javafx.scene.Node;
        import javafx.scene.control.*;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.*;
        import javafx.scene.paint.Color;
        import presentacio.ControladorPresentacio;

        import java.lang.reflect.Array;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.HashMap;

public class ViewHorariController {

    @FXML
    private Button rest;
    @FXML
    private Button quit;
    @FXML
    private BorderPane borderPaneViewHorari;


    @FXML
    private Label classOrigin;
    @FXML
    private Label classDestination;
    @FXML
    private Button swapButton;

    private boolean classSelected = false;

    private boolean isClass[][][];
    private boolean classAvailable[][];

    private String classes[][][][];

        private ArrayList<String> hores;
    private ArrayList<String> dies;

    private int rowSelected;
    private int colSelected;
    private int indexSelected;

    private int rowClassOrigin;
    private int colClassOrigin;
    private int indexClassOrigin;

    private int rowDest;
    private int colDest;

    final int ROW_HEIGHT = 24;


    private ControladorPresentacio cP;

    public void setMainApp(ControladorPresentacio contPres) {this.cP = contPres;}

    public void setClassOrigin() {
        classOrigin.setText("No sha seleccionat res");
    }


    //hs[dia][hora][aula]
//Podria fer que classe disponible sempre estigui al inici o final


    public void setHorari (String[][][][] hs, ArrayList<String> dies, ArrayList<String> hores, HashMap<String, Color> colorAssig) {
        classes = new String[hs.length][hs[0].length][hs[0][0].length][hs[0][0][0].length];
        this.hores = hores;
        this.dies = dies;
        isClass = new boolean[hs.length][hs[0].length][hs[0][0].length];
        classAvailable = new boolean[hs.length][hs[0].length];

        GridPane horariGrid = new GridPane();
        ColumnConstraints c0 = new ColumnConstraints();
        c0.setPercentWidth(3);
        horariGrid.getColumnConstraints().addAll(c0);
        //horariGrid.setGridLinesVisible(true);
        horariGrid.setPadding(new Insets(10, 10, 10, 10));
        //set days
        for (int i = 1; i <= hs.length; ++i) horariGrid.add(new Label(dies.get(i-1)), i, 0);
        //set hours
        for (int i = 1; i <= hs[0].length; ++i) horariGrid.add(new Label(hores.get(i-1)), 0, i);



        for (int i = 0; i < hs.length; ++i) {
            for (int j = 0; j < hs[0].length; ++j) {
                ListView<Label> listHora = new ListView<>();
                int realPos = 0;
                int numItems = 0;
                for (int k = 0; k < hs[0][0].length; ++k) {
                    if (hs[i][j][k][0] != null) isClass[i][j][k] = true;
                    //Declares button text
                    String text;
                    if (isClass[i][j][k]) {
                        classes[i][j][realPos] = hs[i][j][k];
                        text = hs[i][j][k][0] + " " + hs[i][j][k][1] + " " + hs[i][j][k][2];
                        Label auxL = new Label(text);
                        auxL.setMouseTransparent(false);
                        auxL.setBackground(new Background(new BackgroundFill(colorAssig.get(hs[i][j][k][1]), CornerRadii.EMPTY, Insets.EMPTY)));

                        listHora.getItems().add(auxL);
                        ++realPos;
                        ++numItems;

                    } else {
                        if(!classAvailable[i][j]) {
                            classAvailable[i][j] = true;
                            Label auxL = new Label("Espai disponible");
                            auxL.setMouseTransparent(false);
                            auxL.setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));

                            listHora.getItems().add(auxL);
                            ++realPos;
                            ++numItems;
                        }
                    }



                }

                listHora.setPrefHeight(numItems * ROW_HEIGHT - ROW_HEIGHT + 4);

                listHora.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    System.out.println( "Node: " + listHora + " at row/col" + GridPane.getRowIndex( listHora) + "/" + GridPane.getColumnIndex( listHora));

                    rowSelected = GridPane.getRowIndex(listHora);
                    colSelected = GridPane.getColumnIndex(listHora);
                });

                listHora.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {


                        indexSelected = listHora.getSelectionModel().getSelectedIndex();

                        if (isClass[colSelected-1][rowSelected-1][indexSelected]){

                            System.out.println("label reads " + listHora.getSelectionModel().getSelectedItem());

                            colClassOrigin = colSelected-1;
                            rowClassOrigin = rowSelected-1;
                            indexClassOrigin = indexSelected;

                            System.out.println("colclassOrigin " + colClassOrigin + " rowclassorigin " + (rowSelected-1) + " index " + indexClassOrigin);
                            System.out.println("in class array theres " + classes[colClassOrigin][rowClassOrigin][indexSelected][0]
                                    + " " + classes[colClassOrigin][rowClassOrigin][indexSelected][1]
                                    + " " + classes[colClassOrigin][rowClassOrigin][indexSelected][2]);
                            classOrigin.setText(classes[colClassOrigin][rowClassOrigin][indexClassOrigin][0]
                                    + " " + classes[colClassOrigin][rowClassOrigin][indexClassOrigin][1]
                                    + " " + classes[colClassOrigin][rowClassOrigin][indexClassOrigin][2]);
                            //classOrigin.setText(listHora.getSelectionModel().getSelectedItem().getText());
                        }
                        else {

                            rowDest = rowSelected-1;
                            colDest = colSelected-1;

                            classDestination.setText(dies.get(colDest) + " a les " + hores.get(rowDest));
                        }
                    }
                });
                horariGrid.add(listHora, i+1, j+1);
            }
        }

        ScrollPane horariScroll = new ScrollPane(horariGrid);

        horariScroll.setFitToWidth(true);

        borderPaneViewHorari.setCenter(horariScroll);
    }

    @FXML
    public void initialize() {

        swapButton.setOnAction((event) -> {

            System.out.println("swap at viewhoraricontroller going to swap ");

            for (int i = 0; i < 3; ++i) System.out.print(classes[colClassOrigin][rowClassOrigin][indexClassOrigin][i] + " ");

            System.out.println(" to " + hores.get(rowDest) + " " + dies.get(colDest));

            cP.swap(classes[colClassOrigin][rowClassOrigin][indexClassOrigin], hores.get(rowDest), dies.get(colDest));

        });


        rest.setOnAction((event) -> { 
            cP.showViewRest();
        });

        quit.setOnAction((event) -> {
            System.exit(0);
        });
    }
}
