package tk.itiger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.itiger.bot.AgentSmithBot;
import tk.itiger.listeners.BotControlCommandsListener;
import tk.itiger.listeners.GuildNewMembersFinder;

import javax.security.auth.login.LoginException;

public class BotContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotContainer.class);

    public static void main(String[] args) throws LoginException, InterruptedException {
        LOGGER.info("Starting...");
        new AgentSmithBot(
                new GuildNewMembersFinder(),
                new BotControlCommandsListener()
        );

    }
}
