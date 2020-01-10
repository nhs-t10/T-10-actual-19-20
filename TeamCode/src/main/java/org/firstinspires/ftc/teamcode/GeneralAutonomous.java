package org.firstinspires.ftc.teamcode;

public abstract class GeneralAutonomous extends Library
{
    private static final float STONE_HEIGHT_WITHOUT_NUBS = 101.6f;
    private static final float STONE_NUB_HEIGHT = 25.4f;
    private static final float STONE_LENGTH = 203.2f;

    private static final float Starting_To_Quarry_Distance = 0;
    private static final float Quarry_To_Foundation_Side_Distance = 0;

    private static int numOfStones;
    private static int scalar;

    public GeneralAutonomous(int numOfStones, int scalar)
    {
        this.numOfStones = numOfStones;
        this.scalar = scalar;
    }

    public static int getQuarryConfiguration()
    {
        for (int stone = 0; stone < 3; stone++)
        {
            if(isSkystoneVisible())
                return stone;

            strafeForEncoders(getEncoderValue(), getEncoderValue(), STONE_LENGTH);
        }

        return 3;
    }

    public static void driveToQuarry()
    {
        driveForEncoders(getEncoderValue(), getEncoderValue(), Starting_To_Quarry_Distance);
    }

    public static void pickUpStone()
    {
        gripStone(true);
        moveLiftToPosition(10);
        driveForEncoders(getEncoderValue(), getEncoderValue(), -Starting_To_Quarry_Distance);
    }

    public static void moveToFoundation()
    {
        strafeForEncoders(getEncoderValue(), getEncoderValue(), Quarry_To_Foundation_Side_Distance);
        moveLiftToPosition(numOfStones * (STONE_HEIGHT_WITHOUT_NUBS + STONE_NUB_HEIGHT));
        driveForEncoders(getEncoderValue(), getEncoderValue(), Starting_To_Quarry_Distance);
    }

    public static void dragPlatform()
    {
        gripFoundation(true);
        driveForEncoders(getEncoderValue(), getEncoderValue(), -Starting_To_Quarry_Distance);

        gripFoundation(false);
    }

    public static void placeStone()
    {
        moveLiftToPosition(-STONE_NUB_HEIGHT - 10);
        gripStone(false);

        moveLiftToPosition(STONE_NUB_HEIGHT + 10);
        strafeForEncoders(getEncoderValue(), getEncoderValue(), 100);

        moveLiftToPosition(numOfStones * -(STONE_HEIGHT_WITHOUT_NUBS + STONE_NUB_HEIGHT));
    }

    public static void moveToBridge()
    {
        while (!isUnderBridge())
            drive(0, .5f, 0);

        drive(0, 0, 0);
    }

    public abstract void reachStartingLocation();

    public static void main()
    {
        int pos = getQuarryConfiguration();
        driveToQuarry();
        pickUpStone();

        moveToFoundation();
        dragPlatform();
        placeStone();
    }
}