package sr.frostybee.attraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * FXML controller class for the FX Apps Gallery's main stage.
 *
 * @author frostybee
 */
public class FXMLGravitationController {

    @FXML
    private Pane simulationPane;
    @FXML
    private BorderPane mainContainer;

    @FXML
    private Button btnStart;
    @FXML
    private Button btnStop;
    @FXML
    private Button btnReset;
    private static Random random = new Random();
    private List<RigidBody> rigidBodies = new ArrayList<>();
    private AnimationTimer animationLoop;

    @FXML
    public void initialize() {
        initUiControls();
    }

    private void initUiControls() {
        btnStart.setOnAction((event) -> {
            startSimulation();
            disableSimulationButtons(true, false, false);
        });
        btnStop.setOnAction((event) -> {
            stopSimulation();
            disableSimulationButtons(false, true, true);
        });
        btnReset.setOnAction((event) -> {
            resetSimulation();
            disableSimulationButtons(false, true, true);
        });
    }

    private void createRigidBodies() {
        rigidBodies.clear();
        // add sprites
        for (int i = 0; i < Settings.MOVER_COUNT; i++) {
            // random location
            double x = random.nextDouble() * simulationPane.getWidth();
            double y = random.nextDouble() * simulationPane.getHeight();

            // create sprite data
            Point2D location = new Point2D(x, y);
            Point2D velocity = new Point2D(0, 0);
            Point2D acceleration = new Point2D(0, 0);
            double mass = random.nextDouble() * 10 + 10;

            // create sprite and add to layer
            RigidBody mover = new RigidBody(location, velocity, acceleration, mass);

            // register sprite
            rigidBodies.add(mover);

            // add this node to layer
            simulationPane.getChildren().add(mover);
        }
    }

    private void initAnimationLoop() {

        // start game
        animationLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 25000000) {
                    lastUpdate = now;
                    // force: attraction
                    for (RigidBody m1 : rigidBodies) {
                        for (RigidBody m2 : rigidBodies) {

                            if (m1 == m2) {
                                continue;
                            }
                            // calculate attraction
                            Point2D force = m1.attract(m2);
                            // apply attraction
                            m2.applyForce(force);

                        }
                    }
                    // move
                    rigidBodies.forEach(RigidBody::move);
                    // update in fx scene                    
                    rigidBodies.forEach(RigidBody::display);
                }
            }
        };
        animationLoop.start();
    }

    void stopSimulation() {
        // Stop the animation timer upon closing this window. 
        if (animationLoop != null) {
            animationLoop.stop();
        }
    }

    public void startSimulation() {
        stopSimulation();
        if (rigidBodies.isEmpty()) {
            createRigidBodies();
            initAnimationLoop();
        }
        if (animationLoop != null) {
            animationLoop.start();
        }
    }

    private void resetSimulation() {

        disableSimulationButtons(false, true, true);
    }

    private void disableSimulationButtons(boolean start, boolean stop, boolean reset) {
        btnStart.setDisable(start);
        btnStop.setDisable(stop);
        btnReset.setDisable(reset);
    }
}
