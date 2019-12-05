package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

public abstract class Library extends OpMode
{
    // Declare hardware devices
    public static DcMotor frontLeft, frontRight, backLeft, backRight, intakeOne, intakeTwo, lift;
    public static Servo platform, grabber;
    public static CRServo rotateGrabber;

    // Initialize hardware devices and their zero behavior
    public void hardwareInit()
    {
        frontLeft = hardwareMap.dcMotor.get("m0");
        frontRight = hardwareMap.dcMotor.get("m1");
        backLeft = hardwareMap.dcMotor.get("m2");
        backRight = hardwareMap.dcMotor.get("m3");

        lift = hardwareMap.dcMotor.get("l0");
        intakeOne = hardwareMap.dcMotor.get("l1");
        intakeTwo = hardwareMap.dcMotor.get("l2");

        platform = hardwareMap.servo.get("s0");
        grabber = hardwareMap.servo.get("s1");
        rotateGrabber = hardwareMap.crservo.get("s2");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    //Each method below uses inputs to dictate the robot's actions
    //(i.e grip, which determines weather the robot should grab or not)
    public static void intake(boolean a, boolean b)
    {
        double num = 0.0;

        if (a)
            num = .5;

        if (b)
            num = -.5;

        intakeOne.setPower(num);
        intakeTwo.setPower(num);
    }

    public static void grip(boolean x)
    {
        if(x)
            grabber.setPosition(1);

        else
            grabber.setPosition(0);
    }

    public static void platform(boolean y)
    {
        if (y)
            platform.setPosition(1);

        else
            platform.setPosition(0);
    }

    public static void lift(boolean up, boolean down)
    {
        if (up)
            lift.setPower(.5);

        if (down)
            lift.setPower(-.5);

        if (!up && !down)
            lift.setPower(0);
    }

    public static void gRotate(float left, float right)
    {
        if(right > left)
            rotateGrabber.setPower(right);

        else if(left > right)
            rotateGrabber.setPower(-left);

        else
            rotateGrabber.setPower(0);
    }

    //Drive is the central movement and robot handling method of the code
    //Its parameters are l (forward component), r (rotational component), and s (skating component)
    //The method creates a list of the sums of each parameter multiplied by the i index of the
    //forward, rotational and horizontal multiplier arrays
    //Any resulting values above .9 are rounded down to .9 (any higher value might cause the robot
    //to crash) and used to set the power of each of the motors
    public static void drive(float l, float r, float s)
    {
        float[] sums = new float[4];
        float[] forwardMultiplier = {-1f, 1f, -1f, 1f};
        float[] rotationalMultiplier = {1f, 1f, 1f, 1f};
        float[] horizontalMultiplier = {1f, 1f, -1f, -1f};

        for (int i = 0; i < 4; i++)
            sums[i] += forwardMultiplier[i] * l + rotationalMultiplier[i] * r + horizontalMultiplier[i] * s;

        for (int i = 0; i < 4; i++)
            if (sums[i] > .9)
                sums[i] = .9f;

        frontLeft.setPower(sums[0]);
        frontRight.setPower(sums[1]);
        backLeft.setPower(sums[2]);
        backRight.setPower(sums[3]);
    }
}