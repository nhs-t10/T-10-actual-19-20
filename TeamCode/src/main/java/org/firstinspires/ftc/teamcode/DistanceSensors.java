package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DistanceSensor;
        import com.qualcomm.robotcore.util.ElapsedTime;
        import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.hardware.CRServo;
        import com.qualcomm.robotcore.hardware.ColorSensor;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.Servo;
        import com.qualcomm.robotcore.hardware.TouchSensor;
        import com.qualcomm.robotcore.hardware.VoltageSensor;
        import com.qualcomm.robotcore.hardware.DistanceSensor;


@TeleOp(name = "Distance Sensors")
public class DistanceSensors extends OpMode{
    public static DcMotor frontLeft, frontRight, backLeft, backRight, intakeOne, intakeTwo, lift;
    public static CRServo rotateGrabber;
    public static Servo platform,grabber;
    public static VoltageSensor voltageSensor;
    // Initialize hardware devices and their zero behavior
    public static TouchSensor front1, front2;
    public static ColorSensor color;
    public static DistanceSensor distance;
    public Library.DRIVING mode;

    public void init(){
        hardwareInit();
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void loop(){
        /*
        //Intake for blocks | gamepad 1
        boolean a = gamepad1.a;
        //Output for blocks | gamepad 1
        boolean b = gamepad1.b;
        //Intake for blocks | gamepad 2
        boolean a2 = gamepad2.a;
        //Output for blocks | gamepad 2
        boolean b2 = gamepad2.b;
        */

        //Stone gripping | both gamepads
        boolean x = gamepad1.x;
        boolean x2 = gamepad2.x;

        //Hook control to grab foundation | both gamepads
        boolean y = gamepad1.y;
        boolean y2 = gamepad2.y;

        //Lift controls | Both gamepads
        boolean liftUp = gamepad1.right_bumper;
        boolean liftDown = gamepad1.left_bumper;
        boolean liftUp2 = gamepad2.right_bumper;
        boolean liftDown2 = gamepad2.left_bumper;
        //boolean skystone = gamepad1.dpad_up;

        //Movement inputs
        float linear = gamepad1.left_stick_y; //Forward and back
        float side = gamepad1.left_stick_x; //Right and left
        float rotation = gamepad1.right_stick_x; //Rotating in place

        //If controller two gives any commands (true) than the robot will use those inputs
        //Otherwise, it will use the inputs of controller one

        /*if( a2 || b2 ){
            intake(a2, b2);
        }else{
            intake(a, b);
        }*/

                if( x ){
                    if(distance.getDistance(DistanceUnit.CM) >= 20){
                        drive(.25f,0,0);
                    }else{
                        drive(0,0,0);
                    }
                }

        //        if( y2 ){
        //            gripFoundation(true);
        //        }else{
        //            gripFoundation(y);
        //        }

        //        if( liftUp2 || liftDown2 ){
        //            liftGivenControllerValues(liftUp2, liftDown2);
        //        }else{
        //            liftGivenControllerValues(liftUp, liftDown);
        //        }

        /*(if (grabberRight2 != 0 || grabberLeft2 != 0)
        	gRotate(grabberLeft2, grabberRight2);

           else
        	gRotate(grabberLeft, grabberRight);*/

        if( gamepad1.right_stick_button ){
            mode = mode.getNext();
        }

        drive(linear, rotation, side); // fast driving

        telemetry.addData("Values: ", linear + "\n " + rotation + "\n " + side);
        telemetry.addData("Distance: ", distance.getDistance(DistanceUnit.CM));
    }
    public void hardwareInit(){
        frontLeft = hardwareMap.dcMotor.get("m0");
        frontRight = hardwareMap.dcMotor.get("m1");
        backLeft = hardwareMap.dcMotor.get("m2");
        backRight = hardwareMap.dcMotor.get("m3");

        //        lift = hardwareMap.dcMotor.get("l0");
        //        intakeOne = hardwareMap.dcMotor.get("l1");
        //        intakeTwo = hardwareMap.dcMotor.get("l2");

        //        platform = hardwareMap.servo.get("s0");
        //        grabber = hardwareMap.servo.get("s1");
        //        rotateGrabber = hardwareMap.crservo.get("s2");

        //        color = hardwareMap.colorSensor.get("color1");
        distance = hardwareMap.get(DistanceSensor.class, "distance1");
        //        front1 = hardwareMap.touchSensor.get("touch1");
        //        front2 = hardwareMap.touchSensor.get("touch2");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //liftGivenControllerValues.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //liftGivenControllerValues.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //        intakeOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //        intakeTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        mode = Library.DRIVING.Fast;
    }
    public static void drive( float l, float r, float s ){
        float[] sums = new float[4];
        float[] forwardMultiplier = { -1f, 1f, -1f, 1f };
        float[] rotationalMultiplier = { 1f, 1f, 1f, 1f };
        float[] horizontalMultiplier = { 1f, 1f, -1f, -1f };

        for( int i = 0; i < 4; i++ ){
            sums[i] += forwardMultiplier[i] * l + rotationalMultiplier[i] * r + horizontalMultiplier[i] * s;
        }

        for( int i = 0; i < 4; i++ ){
            if( sums[i] > .9 ){
                sums[i] = .9f;
            }
        }

        frontLeft.setPower(sums[0]);
        frontRight.setPower(sums[1]);
        backLeft.setPower(sums[2]);
        backRight.setPower(sums[3]);
        /* telemetry.addData("Front Left", sums[0]);
        telemetry.addData("Front Right", sums[1]);
        telemetry.addData("Back Left", sums[2]);
        telemetry.addData("Back Right", sums[3]); */
    }

}