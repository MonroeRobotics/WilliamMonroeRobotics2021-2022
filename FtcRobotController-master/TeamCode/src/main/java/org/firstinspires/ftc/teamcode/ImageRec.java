package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


@TeleOp(name="DriveToPicture", group = "Concept")
@Disabled
public class ImageRec extends LinearOpMode {
        // Adjust these numbers to suit your robot.
        final double DESIRED_DISTANCE = 12.0; //  this is how close the camera should get to the target (inches)
        //  The GAIN constants set the relationship between the measured position error,
        //  and how much power is applied to the drive motors.  Drive = Error * Gain
        //  Make these values smaller for smoother control.
        final double SPEED_GAIN =   0.1 ;   //  Speed Control "Gain". eg: Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
        final double TURN_GAIN  =   0.1 ;   //  Turn Control "Gain".  eg: Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)

        final double MM_PER_INCH = 25.40 ;   //  Metric conversion

        /*
         * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
         * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
         * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
         * web site at https://developer.vuforia.com/license-manager.
         *
         * Vuforia license keys are always 380 characters long, and look as if they contain mostly
         * random data. As an example, here is a example of a fragment of a valid key:
         *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
         * Once you've obtained a license key, copy the string from the Vuforia web site
         * and paste it in to your code on the next line, between the double quotes.
         */

        private static final String VUFORIA_KEY = "AYgFQEn/////AAABmZktkoBTAkVNt+reKSY8LhArtiNgmXoPcLLAfyChoAu8zFN33UltlJ5qSzMikTirl0dLSkgn8Jix4iFSMbc3ArlSBwmNgP5nMSuEGCLvdjgLddPa7eBL7M8CrYcWGwG9E/snaBn5br/bkKQFsfdb334YxxMPn6EDfJplvsX+87KHhIKqKMsAKry4+PegiTwSyzOZapdb+qUmngVHVof2xObB2YI3TOikP2/T7qqToI+RpxVxV1ndg7yUCwTwAM2TjZUgj5N7uNV6dkAtt8+sYjhdXA5ZmsXzUm1a8BatDt8SkI6tXUEDohxLWGlwqdy4hyESnCX/fsglF43c2637MoRsqjjfeI7Nb3OGRMtOiI6A";

        VuforiaLocalizer vuforia    = null;
        OpenGLMatrix targetPose     = null;
        String targetName           = "";

        private DcMotor fRight   = null;
        private DcMotor fLeft  = null;
        private DcMotor bRight  = null;
        private DcMotor bLeft  = null;
        
        @Override
        public void runOpMode()
        {
            /*
             * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
             * To get an on-phone camera preview, use the code below.
             * If no camera preview is desired, use the parameter-less constructor instead (commented out below).
             */

            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
            // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

            parameters.vuforiaLicenseKey = VUFORIA_KEY;

            // Turn off Extended tracking.  Set this true if you want Vuforia to track beyond the target.
            parameters.useExtendedTracking = false;

            // Connect to the camera we are to use.  This name must match what is set up in Robot Configuration
            parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
            this.vuforia = ClassFactory.getInstance().createVuforia(parameters);

            // Load the trackable objects from the Assets file, and give them meaningful names
            VuforiaTrackables targetsFreightFrenzy = this.vuforia.loadTrackablesFromAsset("FreightFrenzy");
            targetsFreightFrenzy.get(0).setName("Blue Storage");
            targetsFreightFrenzy.get(1).setName("Blue Alliance Wall");
            targetsFreightFrenzy.get(2).setName("Red Storage");
            targetsFreightFrenzy.get(3).setName("Red Alliance Wall");

            // Start tracking targets in the background
            targetsFreightFrenzy.activate();

            // Initialize the hardware variables. Note that the strings used here as parameters
            // to 'get' must correspond to the names assigned during the robot configuration
            // step (using the FTC Robot Controller app on the phone).
            fRight  = hardwareMap.get(DcMotor.class, "frontRight");
            fLeft = hardwareMap.get(DcMotor.class, "frontLeft");
            bRight = hardwareMap.get(DcMotor.class, "backRight");
            bLeft = hardwareMap.get(DcMotor.class, "backLeft");

            // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
            // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.

            telemetry.addData(">", "Press Play to start");
            telemetry.update();

            waitForStart();

            boolean targetFound     = false;    // Set to true when a target is detected by Vuforia
            double  targetRange     = 0;        // Distance from camera to target in Inches
            double  targetBearing   = 0;        // Robot Heading, relative to target.  Positive degrees means target is to the right.
            double  drive           = 0;        // Desired forward power (-1 to +1)
            double  turn            = 0;        // Desired turning power (-1 to +1)
            double magnitude = .5;
            double rightDrive = 0;
            double leftDrive = 0;

            while (opModeIsActive())
            {
                // Look for first visible target, and save its pose.
                targetFound = false;
                for (VuforiaTrackable trackable : targetsFreightFrenzy)
                {
                    if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible())
                    {
                        targetPose = ((VuforiaTrackableDefaultListener)trackable.getListener()).getVuforiaCameraFromTarget();

                        // if we have a target, process the "pose" to determine the position of the target relative to the robot.
                        if (targetPose != null)
                        {
                            targetFound = true;
                            targetName  = trackable.getName();
                            VectorF trans = targetPose.getTranslation();

                            // Extract the X & Y components of the offset of the target relative to the robot
                            double targetX = trans.get(0) / MM_PER_INCH; // Image X axis
                            double targetY = trans.get(2) / MM_PER_INCH; // Image Z axis

                            // target range is based on distance from robot position to origin (right triangle).
                            targetRange = Math.hypot(targetX, targetY);

                            // target bearing is based on angle formed between the X axis and the target range line
                            targetBearing = Math.toDegrees(Math.asin(targetX / targetRange));

                            break;  // jump out of target tracking loop if we find a target.
                        }
                    }
                }

                // Tell the driver what we see, and what to do.
                if (targetFound) {
                    telemetry.addData("Target", " %s", targetName);
                    telemetry.addData("Range",  "%5.1f inches", targetRange);
                    telemetry.addData("Bearing","%3.0f degrees", targetBearing);
                } else {
                    telemetry.addData(">","Drive using joystick to find target\n");
                }

                // Drive to target Automatically if Left Bumper is being pressed, AND we have found a target.
                if (targetFound) {

                    // Determine heading and range error so we can use them to control the robot automatically.
                    double  rangeError   = (targetRange - DESIRED_DISTANCE);
                    double  headingError = targetBearing;

                    // Use the speed and turn "gains" to calculate how we want the robot to move.
                    magnitude = rangeError * SPEED_GAIN;
                    turn  = headingError * TURN_GAIN ;

                    telemetry.addData("Auto","Drive %5.2f, Turn %5.2f", drive, turn);
                    if (rangeError > 0){

                        double direction = (headingError + 90) * (Math.PI / 180);
                        
                        fRight.setPower(Math.sin(direction + 1.0/4.0 * Math.PI) * magnitude);
                        bLeft.setPower(-Math.sin(direction + 1.0/4.0 * Math.PI) * magnitude);
                        bRight.setPower(Math.sin(direction - 1.0/4.0 * Math.PI) * magnitude);
                        fLeft.setPower(-Math.sin(direction - 1.0/4.0 * Math.PI) * magnitude);
                    }
                    else if (headingError > 0 ){
                        fLeft.setPower(-turn);
                        bLeft.setPower(-turn);
                        fRight.setPower(turn);
                        bRight.setPower(turn);
                    }
                    else {
                        fRight.setPower(0);
                        bLeft.setPower(0);
                        bRight.setPower(0);
                        fLeft.setPower(0);
                    }
                }
                if (!targetFound){
                    fLeft.setPower(-.3);
                    bLeft.setPower(-.3);
                    fRight.setPower(-.3);
                    bRight.setPower(-.3);
                }
                
                telemetry.update();

                sleep(10);
            }
        }
}
