package ua.com.mobidev.android.framework.application.controller;

/**
 *
 */
public abstract class AbstractApplicationController {
    private String name;

    public AbstractApplicationController(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Initialize controller here.
     * LOAD internal state from persistent storage
     * @return success flag
     */
    public abstract boolean onStart();

    /**
     * Kind of destroying controller.
     * SAVE internal state from persistent storage
     * @return success flag
     */
    public abstract boolean onPause();

}
