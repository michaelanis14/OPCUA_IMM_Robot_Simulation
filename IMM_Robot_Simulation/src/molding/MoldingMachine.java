package molding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static molding.MoldingTrigger.*;

/**
 * State machine model for the Injection Molding Machine.
 * Manages state transitions and lifecycle of the molding process.
 *
 * THREAD SAFETY: This class uses instance-based state (not static) and
 * synchronized methods to ensure thread-safe state transitions.
 *
 * @author Michael Bishara
 */
public class MoldingMachine {

	private static final Logger logger = LoggerFactory.getLogger(MoldingMachine.class);

	// Instance-based state (NOT static) to prevent race conditions between instances
	private volatile int currentState;

	// Lock object for synchronized state transitions
	private final Object stateLock = new Object();

	public MoldingMachine() {
		this.currentState = CLOSING.ordinal();
		logger.info("MoldingMachine initialized in CLOSING state");
	}

	/**
	 * Fires a state transition trigger.
	 * Thread-safe: uses synchronized block to prevent race conditions.
	 *
	 * @param trigger the trigger to fire
	 */
	public void fireTrigger(MoldingTrigger trigger) {
		synchronized (stateLock) {
			String previousState = getCurrentStateName();
			logger.debug("Firing trigger: {} from state: {}", trigger, previousState);

			switch (trigger) {
			case CLOSING:
				currentState = CLOSING.ordinal();
				logger.info("State transition: {} -> CLOSING", previousState);
				closeMold();
				break;
			case MOLDING:
				currentState = MOLDING.ordinal();
				logger.info("State transition: {} -> MOLDING", previousState);
				injectMold();
				break;
			case OPENING:
				currentState = OPENING.ordinal();
				logger.info("State transition: {} -> OPENING", previousState);
				openMold();
				break;
			case ROBOT:
				currentState = ROBOT.ordinal();
				logger.info("State transition: {} -> ROBOT (waiting for robot)", previousState);
				waitForRobot();
				break;
			case STOP:
				currentState = STOP.ordinal();
				logger.warn("State transition: {} -> STOP", previousState);
				break;
			case SUSPEND:
				currentState = SUSPEND.ordinal();
				logger.info("State transition: {} -> SUSPEND", previousState);
				break;
			case RESUME:
				currentState = RESUME.ordinal();
				logger.info("State transition: {} -> RESUME", previousState);
				break;
			default:
				logger.error("Unknown trigger received: {}", trigger);
				break;
			}
		}
	}

	public MoldingTrigger[] getAllTriggers() {
		return Arrays.stream(MoldingTrigger.values()).filter(t -> !t.equals(DONE)).toArray(MoldingTrigger[]::new);
	}

	public int getCurrentState() {
		return currentState;
	}

	/**
	 * Gets the current state name as a string.
	 *
	 * @return current state name
	 */
	public String getCurrentStateName() {
		try {
			return MoldingTrigger.values()[currentState].name();
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.error("Invalid state index: {}", currentState);
			return "UNKNOWN";
		}
	}

	public String injectMold() {
		logger.debug("Executing injectMold operation");
		return "injectMold";
	}

	public String openMold() {
		logger.debug("Executing openMold operation");
		return "openMold";
	}

	public String closeMold() {
		logger.debug("Executing closeMold operation");
		return "closeMold";
	}

	public String waitForRobot() {
		logger.debug("Executing waitForRobot operation - waiting for robot to complete");
		return "waitForRobot";
	}

}
