package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Selections")

public class AutoSelection extends Library{
    public enum Auto {COLOR, RED, BLUE, VIBE}
    public enum File {BPA, BPP, BDP, BBA, RPA, RPP, RDP}

    private String Color;
    private Auto auto = Auto.COLOR;
    private File file;

    private BluePlatformAuto BPA = new BluePlatformAuto();
    private BluePlatformPark BPP = new BluePlatformPark();
    private BlueDepotPark BDP = new BlueDepotPark();
    private BlueBlockAuto BBA = new BlueBlockAuto();

    private RedPlatformAuto RPA = new RedPlatformAuto();
    private RedPlatformPark RPP = new RedPlatformPark();
    private RedDepotPark RDP = new RedDepotPark();

    public void init(){
    }

    public void init_loop(){

        boolean up = gamepad1.dpad_up;
        boolean down = gamepad1.dpad_down;
        boolean right = gamepad1.dpad_right;
        boolean left = gamepad1.dpad_left;

        switch( auto ){
            case COLOR:{
                telemetry.addLine("UP for BLUE side");
                telemetry.addLine("DOWN for RED side");

                if( up )
                    Color = "red";

                else if( down )
                    Color = "blue";

                if( Color.equals("blue") ){
                    auto = Auto.BLUE;

                    try{
                        Thread.sleep(150);
                    }catch( Exception ie ){
                        System.out.println("Error " + ie);
                    }

                }else if( Color.equals("red") ){
                    auto = Auto.RED;

                    try{
                        Thread.sleep(150);
                    }catch( Exception ie ){
                        System.out.println("Error " + ie);
                    }
                }
                break;
            }

            case BLUE:{
                telemetry.addLine("UP for BLUE PLATFORM AUTO");
                telemetry.addLine("DOWN for BLUE PLATFORM PARK");
                telemetry.addLine("LEFT for BLUE DEPOT PARK");
                telemetry.addLine("RIGHT for BLUE BLOCK AUTO");

                if( up ){
                    file = File.BPA;
                }else if( down ){
                    file = File.BPP;
                }else if( left ){
                    file = File.BDP;
                }else if( right ){
                    file = File.BBA;
                }

                if( file != null ){
                    auto = Auto.VIBE;
                }

                break;
            }

            case RED:{
                telemetry.addLine("UP for RED PLATFORM AUTO");
                telemetry.addLine("DOWN for RED PLATFORM PARK");
                telemetry.addLine("LEFT for RED DEPOT PARK");
                //telemetry.addLine("RIGHT for RED BLOCK AUTO");

                if( up ){
                    file = File.RPA;
                }else if( down ){
                    file = File.RPP;
                }else if( left ){
                    file = File.RDP;
                }

                if( file != null ){
                    auto = Auto.VIBE;
                }
                break;
            }
        }
    }

    public void loop(){
        switch ( file ){
            case BPA:{ BPA.start(); break; }
            case BPP:{ BPP.start(); break; }
            case BDP:{ BDP.start(); break; }
            case BBA:{ BBA.start(); break; }
            case RPA:{ RPA.start(); break; }
            case RPP:{ RPP.start(); break; }
            case RDP:{ RDP.start(); break; }
        }
    }
}