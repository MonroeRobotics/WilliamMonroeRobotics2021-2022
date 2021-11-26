package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp
// Wheel Movement
public class DriveProgram extends LinearOpMode{
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;
    private DcMotor intakeMotor = null;
    // todo: write your code here
    
    
    public void runOpMode(){
        double motorPower = 1;
        double leftstickX;
        double leftstickY;
        double rightstickX;
        double rightstickY;
        double direction;
        double magnitude;
        double fRight;
        double bRight;
        double bLeft;
        double fLeft;
        double fLeftBRight;
        double turn;
        
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        intakeMotor  = hardwareMap.get(DcMotor.class, "intakeMotor");
        
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()){
            
            leftstickX = this.gamepad1.left_stick_x;
            leftstickY = -this.gamepad1.left_stick_y;
            
            turn = -this.gamepad1.right_stick_x;
            
            direction = Math.atan2(leftstickY, leftstickX);
            magnitude = Math.sqrt(Math.pow(leftstickX, 2) + Math.pow(leftstickY, 2)) * 1.5;
            
            fRight = (Math.sin(direction - 1.0/4.0 * Math.PI) * magnitude + turn);
            bLeft = (-Math.sin(direction - 1.0/4.0 * Math.PI) * magnitude + turn);
            bRight = (Math.sin(direction + 1.0/4.0 * Math.PI) * magnitude + turn);
            fLeft = (-Math.sin(direction + 1.0/4.0 * Math.PI) * magnitude + turn);
            
            if (fRight > 1 || fRight < -1){
                fLeft = (fLeft / Math.abs(fRight));
                fRight = (fRight / Math.abs(fRight));
                bRight = (bRight / Math.abs(fRight));
                bLeft = (bLeft / Math.abs(fRight));
            }
        
            if (fLeft > 1 || fLeft < -1){
                fLeft = (fLeft / Math.abs(fLeft));
                fRight = (fRight / Math.abs(fLeft));
                bLeft = (bLeft / Math.abs(fLeft));
                bRight = (bRight / Math.abs(fLeft));
            }
            
            intakeMotor.setPower(0);
            
            if (this.gamepad1.x){
                
                intakeMotor.setPower(motorPower);
            }
            if (this.gamepad1.y){
                intakeMotor.setPower(-motorPower);
            }

            telemetry.update();
            
            backLeft.setPower(bLeft);
            frontRight.setPower(fRight);
            backRight.setPower(bRight);
            frontLeft.setPower(fLeft);
            
            telemetry.addData("Status", "Initialized");
            telemetry.addData("leftX", leftstickX);
            telemetry.addData("leftY", leftstickY);
            telemetry.addData("Direction", direction);
            telemetry.addData("magnitude", magnitude);
            telemetry.addData("fRight", fRight);
            telemetry.addData("bLeft", bLeft);
            telemetry.update();
            
        }
    }
    
}
