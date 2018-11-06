package org.irruptiondays.genealogy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.irruptiondays.genealogy.ui.UIContent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * mvn spring-boot:run
 */
@SpringBootApplication
public class GenprojApplication extends Application {

    public static void main(String[] args) {
        SpringApplication.run(GenprojApplication.class, args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(UIContent.createContent()));

        primaryStage.show();

    }
}
