package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
 
public class TestCode extends LinearOpMode {
    // Declare Motor and Servo Variables
    private DcMotor motorL;
    private DcMotor motorR;
    private DcMotor motorA;
    private DcMotor motorS;
    private Servo servoL;
    private Servo servoF;
    
    
    @Override
    public void runOpMode() {
        //Harware map to config
        motorL = hardwareMap.get(DcMotor.class, "motorL");
        motorR = hardwareMap.get(DcMotor.class, "motorR");
        motorA = hardwareMap.get(DcMotor.class, "motorA");
        motorS = hardwareMap.get(DcMotor.class, "motorS");
        servoL = hardwareMap.get(Servo.class, "servoL");
        servoF = hardwareMap.get(Servo.class, "servoF");
        
        //Declare logistic variables
        double leftstick = 0;
        double rightTrig = 0;
        double leftTrig = 0;
        double tgtpower = 0;
        double tgtpower2 = 0;
        double tgtpower3 = 0;
        boolean isHold = false;
        boolean LetGo = true;
        int dpadCheck = 0;
        int isHoldTimer = 0;
        
        //Wait for start button
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        
        
        
        while (opModeIsActive()){
            
            //Gets input from gamepad and assigns to variables
            leftstick = -this.gamepad1.left_stick_y;
            tgtpower = -this.gamepad1.left_stick_y;
            tgtpower2 = -this.gamepad1.left_stick_y;
            tgtpower3 = -this.gamepad1.right_stick_x;
            leftTrig = this.gamepad2.left_trigger;
            rightTrig = this.gamepad2.right_trigger;
            
            //Math for finding power for dual sticks
            if (leftstick > 0){
                
                if (tgtpower3 < 0) {
                    tgtpower2 = tgtpower2 - Math.abs(tgtpower3 * 2);
                }
                else if (tgtpower3 > 0) {
                    tgtpower = tgtpower - Math.abs(tgtpower3 * 2);
                }
            }
            else if (leftstick < 0){
                
                if (tgtpower3 < 0) {
                    tgtpower2 = tgtpower2 + Math.abs(tgtpower3 * 2);
                }
                else if (tgtpower3 > 0) {
                    tgtpower = tgtpower + Math.abs(tgtpower3 * 2);
                }
            }
            
            else if (leftstick == 0){
                if (tgtpower3 > 0) {
                    tgtpower2 = Math.abs(tgtpower3 * 2);
                }
                
                else if (tgtpower3 < 0) {
                    tgtpower = Math.abs(tgtpower3 * 2);
                }
                
            }
            
            //set target power to motors
            motorL.setPower(-tgtpower);
            motorR.setPower(tgtpower2);
            
            //If triggers are pressed move arm
            if (rightTrig > 0){
                motorA.setPower(1);
                
            }
            else if (leftTrig > 0){
                motorA.setPower(-1);
                
            }
            else if (rightTrig == 0 && leftTrig == 0){
                motorA.setPower(0);
            }
            
            //If Dpad Up/Down Pressed spin shooting wheels
            if (this.gamepad2.dpad_down){
                motorS.setPower(1);
            }
            if (this.gamepad2.dpad_up){
                motorS.setPower(-1);
                
            }
            if (this.gamepad2.dpad_down == false && this.gamepad2.dpad_up == false){
                motorS.setPower(0);
            }
            //Press Letter button move feeding servo
            if (this.gamepad2.a){ 
                servoF.setPosition(.8);
            }
            if (this.gamepad2.b){ 
                servoF.setPosition(.38);
            }
            
            //Press Letter button move feeding servo
            if (this.gamepad2.x){ 
                servoL.setPosition(.38);
            }
            if (this.gamepad2.y){ 
                servoL.setPosition(.88);
            }
            
            //Bumper Press Move Arm motor for slow fall
            if (this.gamepad2.right_bumper){
                motorA.setPower(.75);
            }
            
            //Runs Timer
            if (isHoldTimer >= 60 & isHold == false && LetGo == true){
                isHoldTimer = 0;
                isHold = true;
                LetGo = false;
            }
            
            if (isHoldTimer >= 60 && isHold == true && LetGo == true){
                isHoldTimer = 0;
                isHold = false;
                LetGo = false;
            }
            
            //checks if bumper is pressed to run timer
            if (this.gamepad2.left_bumper){
                isHoldTimer = isHoldTimer + 1;
            }
            else if (!this.gamepad2.left_bumper){
                isHoldTimer = 0;
                LetGo = true;
            }
            
            //If Timer is on run motor to keep in place
            if (isHold){
                motorA.setPower(1);
            }
            
            
            //change telemetry data
            telemetry.addData("Status", "Running");
            telemetry.addData("Is Holding", isHold);
            telemetry.addData("Timer", isHoldTimer);
            telemetry.addData("Servo Position", servoL.getPosition());
            telemetry.addData("Target Power", tgtpower);
            telemetry.addData("Motor Power", motorL.getPower());
            telemetry.addData("Target Power2", tgtpower2);
            telemetry.addData("Motor Power2", motorR.getPower());
            telemetry.addData("Target Power 3", tgtpower3);
            telemetry.update();
        }
    }
}
