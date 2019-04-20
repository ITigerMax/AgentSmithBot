package tk.itiger.utils;


/**
 * Class contains general app properties.
 */
public class GeneralAppProperties {

    /**
     * In JDA case guild means a server.
     * GUILD_ID contains the unique identifier that are corresponding to server's id.
     */
    private static final String GUILD_ID;

    /**
     * TOKEN contain the unique identifier, which allows discord's bot-entity to be tied to JDA object.
     * @see tk.itiger.bot.AgentSmithBot
     */
    private static final String TOKEN;

    static {
        GUILD_ID = AppPropertiesHolder.getGuildId();
        TOKEN = AppPropertiesHolder.getToken();
    }

    public static String getGuildId() {
        return GUILD_ID;
    }

    public static String getToken() {
        return TOKEN;
    }
}
