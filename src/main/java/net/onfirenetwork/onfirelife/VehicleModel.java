package net.onfirenetwork.onfirelife;

public enum VehicleModel {
    TAXI(Type.GROUND, 2, 100),
    POLICE_CRUISER(Type.GROUND, 3, 100),
    GARBAGE(Type.GROUND, 9, 100),
    AMBULANCE(Type.GROUND, 8, 100),
    AMBULANCE_TRUCK(Type.GROUND, 48, 100),
    PICKUP(Type.GROUND, 7, 100),
    NASCAR(Type.GROUND, 6, 100),
    BELL_HELICOPTER(Type.HELI, 10, 100),
    BELL_HELICOPTER_ONECOLOR(Type.HELI, 20, 100),
    URAL_TRUCK(Type.GROUND, 17, 100),
    URAL_TRUCK_MILITARY(Type.GROUND, 18, 100),
    HUMVEE(Type.GROUND, 21, 100),
    DELIVERY_TRUCK(Type.GROUND, 24, 100),
    LITTLEBIRD(Type.HELI, 26, 100),
    TRUCK(Type.GROUND, 28, 100),
    LAWN_MOWER(Type.GROUND, 32, 100),
    MOTOR_BOAT(Type.BOAT, 33, 100),
    MOTOR_BOAT_CAMO(Type.BOAT, 34, 100),
    LAMBO(Type.GROUND, 37, 100),
    GOLF_CART(Type.GROUND, 38, 100),
    TOW_TRUCK(Type.GROUND, 39, 100),
    CAMPER(Type.GROUND, 42, 100),
    GOLF(Type.GROUND, 43, 100),
    BUGGY(Type.GROUND, 45, 100),
    BUGGY_FLAMES(Type.GROUND, 46, 100),
    SWAT_TRUCK(Type.GROUND, 47, 100),
    TRAILER_TRUCK(Type.GROUND, 49, 100),
    VESPA(Type.GROUND, 51, 100),
    BIKE(Type.GROUND, 52, 100),
    FORKLIFT(Type.GROUND, 55, 100),
    TANK(Type.GROUND, 54, 100),
    SKODA(Type.GROUND, 50, 100),
    BLACKHAWK(Type.HELI, 30, 100),
    RESCUE_BLACKHAWK(Type.HELI, 31, 100),
    RESCUE_HELI(Type.HELI, 41, 100),
    HEAVY_RESCUE_TRUCK(Type.GROUND, 15, 100)
    ;

    private final Type type;
    private final int model;
    private final int tank;

    VehicleModel(Type type, int model, int tank) {
        this.type = type;
        this.model = model;
        this.tank = tank;
    }

    public Type getType() {
        return type;
    }

    public int getModel() {
        return model;
    }

    public int getTank() {
        return tank;
    }

    public enum Type {
        GROUND,
        BOAT,
        HELI
    }

}
