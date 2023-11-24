package sr.frostybee.attraction;


public class Settings {

	public static double SCENE_WIDTH = 1280;
	public static double SCENE_HEIGHT = 720;

	public static int MOVER_COUNT = 500;

	public static double MOVER_MAX_SPEED = 20;

	// ensure that attraction is applied with at least min and max
	// we don't want it to be too weak or too strong
	public static double ATTRACTION_DISTANCE_MIN = 5;
	public static double ATTRACTION_DISTANCE_MAX = 25.0;

	// Univeral Gravitational Constant; real world: 6.67428E10-11;
	public static double GRAVITATIONAL_CONSTANT = 0.004;

}
