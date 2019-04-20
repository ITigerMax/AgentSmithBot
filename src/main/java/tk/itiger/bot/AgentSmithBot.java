package tk.itiger.bot;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

import tk.itiger.utils.GeneralAppProperties;

public class AgentSmithBot {

    public AgentSmithBot(ListenerAdapter... adapters) throws LoginException, InterruptedException {
        JDA jda = new JDABuilder(GeneralAppProperties.getToken())
                .addEventListener(adapters)
                .build();
        jda.awaitReady();
    }
}
