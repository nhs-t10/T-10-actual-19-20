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
public class DistanceSensors extends Library{
    public Library.DRIVING mode;
    boolean xToggle = false;
    boolean aToggle = false;
    boolean bToggle = false;

    public void init(){
        hardwareInit();
        driveInit();
    }

    public void loop(){
        //Stone gripping | both gamepads
        boolean x = gamepad1.x;
        boolean x2 = gamepad2.x;
        boolean b = gamepad1.b;
        boolean a = gamepad1.a;

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

        if( x ){
            if(!xToggle){
                xToggle = true;
            }else{
                xToggle = false;
            }
        }

        if(xToggle){
            if(distance.getDistance(DistanceUnit.CM) >= 20){
                drive(.25f,0,0);
            }else{
                drive(0,0,0);
            }
        }

        if(aToggle){
            if(distance.getDistance(DistanceUnit.CM) >= 20){
                drive(.25f,.05f,0);
            }else{
                drive(0,0,0);
            }
        }
        if( a ){
            if(!aToggle){
                aToggle = true;
            }else{
                aToggle = false;
            }
        }

        if(bToggle){
            if(distance.getDistance(DistanceUnit.CM) >= 20){
                drive(.25f,-.05f,0);
            }else{
                drive(0,0,0);
            }
        }
        if( b ){
            if(!bToggle){
                bToggle = true;
            }else{
                bToggle = false;
            }
        }

        if( gamepad1.right_stick_button ){
            mode = mode.getNext();
        }

        if(!aToggle && !bToggle && !xToggle){
            drive(linear, rotation, side); // fast driving
        }

        telemetry.addData("Values: ", linear + "\n " + rotation + "\n " + side);
        telemetry.addData("Distance: ", distance.getDistance(DistanceUnit.CM));
        telemetry.addData("A: ", aToggle);
        telemetry.addData("B: ", bToggle);
        telemetry.addData("X: ", xToggle);
    }
}