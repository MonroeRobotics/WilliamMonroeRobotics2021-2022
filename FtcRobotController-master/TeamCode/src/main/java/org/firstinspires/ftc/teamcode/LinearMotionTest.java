package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.concurrent.TimeUnit;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class LinearMotionTest extends LinearOpMode{

    // todo: write your code here
    private DcMotor lineMotor;
    private DcMotor lineMotor2;
    private Servo dropServo;
    
    public void runOpMode(){
        
        long lastTestTime = 0;
        long lastTime = 0;
        int stage = 0;
        long time;
        long checkTime;
        long checkTimeEnd = 0;
        double servoPos;
        
        lineMotor = hardwareMap.get(DcMotor.class, "LeverMotor");
        lineMotor2 = hardwareMap.get(DcMotor.class, "backMotor");
        dropServo = hardwareMap.servo.get("dropServo");
        
        servoPos = 0.97;
        
        waitForStart();
        
        while (opModeIsActive()){
            
            if(this.gamepad1.right_trigger > 0.5){
                servoPos = .8;
            }
            
            if(this.gamepad1.left_trigger > 0.5){
                servoPos = .97;
            }
            
            if (this.gamepad1.x && stage == 0){
                time = 1000;
                checkTime =  System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                stage = 1;
            }
            
            if (checkTimeEnd > System.currentTimeMillis() && opModeIsActive() && stage == 1) {
                lineMotor.setPower(1);
                lineMotor2.setPower(-0.5);
                telemetry.addData("Running", "True");
            }
            
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 1){
                time = 4900;
                checkTime =  System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                servoPos = 0.8;
                stage = 2;
            }
            
            if (checkTimeEnd > System.currentTimeMillis() && opModeIsActive() && stage == 2) {
                lineMotor.setPower(1);
                lineMotor2.setPower(-0.5);
                telemetry.addData("Running", "True");
            }
            
             if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 2){
                time = 2000;
                checkTime =  System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                servoPos = 0.0;
                stage = 3;
            }
            
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 3){
                time = 3400;
                checkTime =  System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                servoPos = 0.8;
                sleep(20);
                stage = 4;
            }
            
            if (checkTimeEnd > System.currentTimeMillis() && opModeIsActive() && stage == 4) {
                lineMotor.setPower(-0.9);
                lineMotor2.setPower(1);
                telemetry.addData("Running", "True");
            }
            
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 4){
                time = 600;
                checkTime =  System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                servoPos = 0.97;
                sleep(20);
                stage = 5;
            }
            
            if (checkTimeEnd > System.currentTimeMillis() && opModeIsActive() && stage == 5) {
                lineMotor.setPower(-0.9);
                lineMotor2.setPower(1);
                telemetry.addData("Running", "True");
            }
            
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 5){
                stage = 0;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                telemetry.addData("Running", "False");
            }
            
            
            dropServo.setPosition(servoPos);
            
            
            if (this.gamepad1.dpad_left){
                lineMotor2.setPower(-1);
                telemetry.addData("Running", "True");
            }
            if (this.gamepad1.dpad_right){
                lineMotor2.setPower(1);
                telemetry.addData("Running", "True");
            }
            if (this.gamepad1.right_bumper){
                lineMotor.setPower(1);
                telemetry.addData("Running", "True");
            }
            if (this.gamepad1.left_bumper){
                lineMotor.setPower(-1);
                telemetry.addData("Running", "True");
            }
            if (this.gamepad1.dpad_up){
                lineMotor.setPower(1);
                lineMotor2.setPower(-0.5);
                telemetry.addData("Running", "True");
               
            }
            if (this.gamepad1.dpad_down){
                lineMotor.setPower(-0.9);
                lineMotor2.setPower(1);
                telemetry.addData("Running", "True");
               
            }
            else {
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                telemetry.addData("Running", "False");
                
            }
            telemetry.addData("Stage", stage);
            telemetry.addData("dropServo", dropServo.getPosition());
            telemetry.update();
        }
    }
}