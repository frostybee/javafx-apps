package com.frostybee.gravityballs;

/**
 *
 * @author frostybee
 */
public class Settings {

    public static double SCENE_WIDTH = 800;
    public static double SCENE_HEIGHT = 600;

    public static int SPRITE_COUNT = 10;

    public static double SPRITE_ACCELERATION_SCALE = 0.5;
    public static double SPRITE_MAX_SPEED = 4;

    public static PVector FORCE_WIND = new PVector(0.04, 0);
    public static PVector FORCE_GRAVITY = new PVector(0, 3);
}
