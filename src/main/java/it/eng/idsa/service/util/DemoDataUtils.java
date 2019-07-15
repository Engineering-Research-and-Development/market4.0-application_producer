package it.eng.idsa.service.util;

/**
 * The constant data utility used in this Demo
 */
public final class DemoDataUtils {
	
	private static final PropertiesConfig CONFIG_PROPERTIES = PropertiesConfig.getInstance();

    public static final int NUMBER_OF_MESSAGES = 3;
    public static final String DESTINATION = "test.queue";

    private static final String BROKER_SETUP = "failover:(";

    private static final String HALF_MINUTE_TIMEOUT = ")?timeout=30000";

    private static final String ENGINEERING_IDSA_MESSAGE = "*** Engineering IDSA using base-connector *** Hello World message %s !";

    public static String buildDummyMessage(int value) {
        return String.format(ENGINEERING_IDSA_MESSAGE, value);
    }

    private static String getBrokerURI(String uri) {
        StringBuffer brokerUrl = new StringBuffer(BROKER_SETUP);
        brokerUrl.append(uri);
        brokerUrl.append(HALF_MINUTE_TIMEOUT);
        return brokerUrl.toString();
    }

    public static String readBrokerURL() {
        String brokerUrl = null;
        brokerUrl = DemoDataUtils.getBrokerURI(CONFIG_PROPERTIES.getProperty("brokerOpenwireUri"));

        return brokerUrl;
    }

}

