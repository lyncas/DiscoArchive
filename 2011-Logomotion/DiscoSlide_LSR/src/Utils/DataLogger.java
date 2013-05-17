package Utils;

import java.util.TimerTask;

/** -------------------------------------------------------
 * @class DataLogger
 * @purpose Class for logging variables as the robot runs
 * Uses DataLogHelper class to accumulate log entries during execution
 * Data should be written to a file using writeData after the robot is disabled
 * @author Nelson Chen
 * @written Jan 27, 2011
------------------------------------------------------- */
public class DataLogger {

    private final String filename = "DataLog.csv";
    private String header;
    public final double kDefaultPeriod = .5;
    private double m_period = kDefaultPeriod;
    private java.util.Timer m_controlLoop;
    private boolean m_enabled = false;
    protected double entryValues[];
    public static DataLogger dataLogger = new DataLogger();

    private class DataLoggerTask extends TimerTask {

        private DataLogger m_dataLogger;

        public DataLoggerTask(DataLogger dataLogger) {
            if (dataLogger == null) {
                throw new NullPointerException("Given DataLogger was null");
            }
            m_dataLogger = dataLogger;
        }

        public void run() {
            if (m_enabled) {
                m_dataLogger.addEntry(entryValues);
            }
        }
    }

    /**
     * singleton class constructor
     */
    private DataLogger() {
    }

    /**
     * starts scheduled dataLogging task
     */
    public void init() {
        m_controlLoop = new java.util.Timer();
        m_controlLoop.schedule(new DataLoggerTask(this), 0L, (long) (m_period * 1000));
        disable();
    }

    /**
     * disables dataLogger (new entries will not be created)
     */
    public void disable() {
        m_enabled = false;
    }

    /**
     * enables dataLogger
     */
    public void enable() {
        m_enabled = true;
    }

    /**
     * sets DataLogHelper time offset (to account for time spent disabled)
     * so timestamps start closer to 0 instead of 20 or 30 seconds
     * @param offset - time offset
     */
    public void setTimeOffset(double offset) {
        DataLogHelper.setTimeOffset(offset);
    }

    /**
     * current method of passing data to the datalogger; a better solution wanted
     * @param values - array of values to store
     */
    public void setEntryValue(double[] values) {
        entryValues = values;
    }

    /**
     * adds another entry to the datalog; called by DataLoggerTask
     * @param entryData - data to be written
     */
    private synchronized void addEntry(double[] entryData) {
        new DataLogHelper(entryData);
    }

    /**
     * sets file header
     * @param hdr - new header for file
     */
    public void setHeader(String[] hdr) {
        header = "time";
        for (int k = 0; k < hdr.length; k++) {
            header += "," + hdr[k];
        }
    }

    /**
     * writes the contents of dataLog (DataLogHelper stack) to a file
     * @param hdr - header to append to file, elements of array are column labels
     */
    public void writeData(String[] hdr) {
        header = "time";
        for (int k = 0; k < hdr.length; k++) {
            header += "," + hdr[k];
        }
        String[] data = DataLogHelper.getData();
        String[] contents = new String[data.length + 1];
        System.arraycopy(data, 0, contents, 1, data.length);
        contents[0] = header;
        FileIO.writeToFile(filename, contents);
        DiscoUtils.debugPrintln("DataLog written");
    }

    /**
     * writes the contents of dataLog (DataLogHelper stack) to a file
     */
    public void writeData() {
        String[] data = DataLogHelper.getData();
        String[] contents = new String[data.length + 1];
        System.arraycopy(data, 0, contents, 1, data.length);
        contents[0] = header;
        FileIO.writeToFile(filename, contents);
        DiscoUtils.debugPrintln("DataLog written");
    }
}
