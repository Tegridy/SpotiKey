import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewController;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        ViewController viewController = new ViewController();
        viewController.showStage();
    }

}
