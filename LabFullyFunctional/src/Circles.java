import java.util.stream.Stream;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A lab exercise to introduce Java 8 lambdas and streams.
 * @author Your name here
 */
public class Circles extends Application {

    public static final int ROWS      = 4;
    public static final int COLS      = 5;
    public static final int CELL_SIZE = 100;

    private VBox   root;
    private Pane   canvas;
    private Button starter;

    private int row = 0;
    private int col = 0;

    @Override
    public void start(Stage primaryStage) {
        root = new VBox();
        canvas = new Pane();
        starter = new Button("Circles");

        root.setAlignment(Pos.CENTER);
        canvas.setPrefSize(COLS * CELL_SIZE, ROWS * CELL_SIZE);

        addButtonHandler();  // You must write

        root.getChildren().addAll(canvas, starter);

        primaryStage.setTitle("Java 8 Lab Exercise");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * This method adds the handler to the button that gives
     * this application its behavior.
     */
    private void addButtonHandler() {
        // You must write
        starter.setOnAction(e -> {
            canvas.getChildren().clear();
            addAllRowsToCanvas(makeAllRows());
        });
    }

    private void addAllRowsToCanvas(Stream<Stream<Circle>> stream) {
        row = 0;
        stream.forEach(r -> { addRowToCanvas(r); row++; });
    }

    private void addRowToCanvas(Stream<Circle> stream) {
        col = 0;
        stream.forEach(c -> { addToCanvas(c); col++; });
    }

    private Stream<Stream<Circle>> makeAllRows() {
        return Stream.generate(this::makeRow).limit(ROWS);
    }

    private Stream<Circle> makeRow() {
        return Stream.generate(() -> new Circle(CELL_SIZE * (col + .5), CELL_SIZE * (row + .5), CELL_SIZE / 4.0)).limit(COLS);
    }

    private void addToCanvas(Circle circle) {
        circle.setFill(new Color(Math.random(), Math.random(), Math.random(), 1.0));
        double fromX = CELL_SIZE * (COLS - .5), fromY = CELL_SIZE * (ROWS - .5);
        double toX = circle.getCenterX(), toY = circle.getCenterY();
        circle.setCenterX(fromX);
        circle.setCenterY(fromY);
        canvas.getChildren().add(circle);
        TranslateTransition tt = new TranslateTransition(new Duration(500));
        tt.setNode(circle);
        tt.setByX(toX - fromX);
        tt.setByY(toY - fromY);
        tt.play();
        ScaleTransition st = new ScaleTransition(new Duration((Math.random() + .2) * 1000));
        st.setNode(circle);
        double scale = Math.random() * 4 - 1;
        st.setByX(scale);
        st.setByY(scale);
        st.setCycleCount(Animation.INDEFINITE);
        st.setAutoReverse(true);
        st.play();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
