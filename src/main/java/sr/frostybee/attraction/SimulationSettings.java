package sr.frostybee.attraction;

/**
 * Holds the settings of the gravitation simulation.
 *
 * @author frostybee
 */
public class SimulationSettings {

    private double maxDropSpeed = 2.5;
    // The number of rigid bodies to be generated.
    private int rigidBodiesCount = 300;
    private double bodyMaxSpeed = 20;
    private double minAttractionDistance = 5;
    private double maxAttractionDistance = 25.5;
    // Univeral Gravitational Constant; real world: 6.67428E10-11;
    private double gravitationalConstant = 0.004;

    public double getMaxDropSpeed() {
        return maxDropSpeed;
    }

    public void setMaxDropSpeed(double maxDropSpeed) {
        this.maxDropSpeed = maxDropSpeed;
    }

    public int getRigidBodiesCount() {
        return rigidBodiesCount;
    }

    public void setRigidBodiesCount(int rigidBodiesNbr) {
        this.rigidBodiesCount = rigidBodiesNbr;
    }

    public double getBodyMaxSpeed() {
        return bodyMaxSpeed;
    }

    public void setBodyMaxSpeed(double bodyMaxSpeed) {
        this.bodyMaxSpeed = bodyMaxSpeed;
    }

    public double getMinAttractionDistance() {
        return minAttractionDistance;
    }

    public void setMinAttractionDistance(double minAttractionDistance) {
        this.minAttractionDistance = minAttractionDistance;
    }

    public double getMaxAttractionDistance() {
        return maxAttractionDistance;
    }

    public void setMaxAttractionDistance(double maxAttractionDistance) {
        this.maxAttractionDistance = maxAttractionDistance;
    }

    public double getGravitationalConstant() {
        return gravitationalConstant;
    }    
}
