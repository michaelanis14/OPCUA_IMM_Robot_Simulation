package config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Timing configuration for state machine transitions.
 *
 * @author Michael Bishara
 */
public class TimingConfig {

    @JsonProperty("molding-machine")
    private MoldingMachineTiming moldingMachine;

    @JsonProperty("robot")
    private RobotTiming robot;

    public MoldingMachineTiming getMoldingMachine() {
        return moldingMachine;
    }

    public void setMoldingMachine(MoldingMachineTiming moldingMachine) {
        this.moldingMachine = moldingMachine;
    }

    public RobotTiming getRobot() {
        return robot;
    }

    public void setRobot(RobotTiming robot) {
        this.robot = robot;
    }

    public void validate() {
        if (moldingMachine != null) {
            moldingMachine.validate();
        }
        if (robot != null) {
            robot.validate();
        }
    }

    /**
     * Molding machine timing configuration
     */
    public static class MoldingMachineTiming {
        @JsonProperty("closing-duration")
        private int closingDuration;

        @JsonProperty("molding-duration")
        private int moldingDuration;

        @JsonProperty("opening-duration")
        private int openingDuration;

        @JsonProperty("robot-wait-timeout")
        private int robotWaitTimeout;

        public int getClosingDuration() {
            return closingDuration;
        }

        public void setClosingDuration(int closingDuration) {
            this.closingDuration = closingDuration;
        }

        public int getMoldingDuration() {
            return moldingDuration;
        }

        public void setMoldingDuration(int moldingDuration) {
            this.moldingDuration = moldingDuration;
        }

        public int getOpeningDuration() {
            return openingDuration;
        }

        public void setOpeningDuration(int openingDuration) {
            this.openingDuration = openingDuration;
        }

        public int getRobotWaitTimeout() {
            return robotWaitTimeout;
        }

        public void setRobotWaitTimeout(int robotWaitTimeout) {
            this.robotWaitTimeout = robotWaitTimeout;
        }

        public void validate() {
            if (closingDuration <= 0 || moldingDuration <= 0 || openingDuration <= 0) {
                throw new IllegalStateException("All duration values must be positive");
            }
            if (robotWaitTimeout <= 0) {
                throw new IllegalStateException("Robot wait timeout must be positive");
            }
        }
    }

    /**
     * Robot timing configuration
     */
    public static class RobotTiming {
        @JsonProperty("move-in-duration")
        private int moveInDuration;

        @JsonProperty("grab-duration")
        private int grabDuration;

        @JsonProperty("move-out-duration")
        private int moveOutDuration;

        @JsonProperty("release-duration")
        private int releaseDuration;

        @JsonProperty("ready-delay")
        private int readyDelay;

        public int getMoveInDuration() {
            return moveInDuration;
        }

        public void setMoveInDuration(int moveInDuration) {
            this.moveInDuration = moveInDuration;
        }

        public int getGrabDuration() {
            return grabDuration;
        }

        public void setGrabDuration(int grabDuration) {
            this.grabDuration = grabDuration;
        }

        public int getMoveOutDuration() {
            return moveOutDuration;
        }

        public void setMoveOutDuration(int moveOutDuration) {
            this.moveOutDuration = moveOutDuration;
        }

        public int getReleaseDuration() {
            return releaseDuration;
        }

        public void setReleaseDuration(int releaseDuration) {
            this.releaseDuration = releaseDuration;
        }

        public int getReadyDelay() {
            return readyDelay;
        }

        public void setReadyDelay(int readyDelay) {
            this.readyDelay = readyDelay;
        }

        public void validate() {
            if (moveInDuration <= 0 || grabDuration <= 0 || moveOutDuration <= 0 || releaseDuration <= 0) {
                throw new IllegalStateException("All duration values must be positive");
            }
            if (readyDelay < 0) {
                throw new IllegalStateException("Ready delay cannot be negative");
            }
        }
    }
}
