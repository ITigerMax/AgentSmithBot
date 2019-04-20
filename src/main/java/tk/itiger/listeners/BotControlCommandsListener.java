package tk.itiger.listeners;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ChoiceFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.util.*;


public class BotControlCommandsListener extends ListenerAdapter implements Runnable{

    private static final Logger LOGGER = LoggerFactory.getLogger(BotControlCommandsListener.class);

    private final String STUB = "В данный момент эта способность находится в разработке ...";
    private final Long BOT_ID = 563811025632100384L;

    private final Map<User, Integer> session;
    private  final Set<User> dialog;

    private PrivateMessageReceivedEvent event;
    private PrivateChannel channel;
    private User user;

    private Map<String, String> commands;

    private String format;
    private String clientMessage;


    boolean hasPermission;



    public BotControlCommandsListener() {
        session = new HashMap<>();
        dialog = new HashSet<>();
        //TODO further convert to enum
        format = "Чтобы %s введи %s";
        commands = new HashMap<>();
        commands.put("передать мне id профиля Java Rush", "#set --id");
        commands.put("я подсказал, как узнать свой id", "#get --id");
        commands.put("удалить мои сообщения", "#delete msg --all");
        commands.put("я оставил тебя в покое","#stop");
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        this.event = event;
        user = event.getAuthor();
        if (user.isBot()) return;
        clientMessage = event.getMessage().getContentDisplay();
        LOGGER.info("clientMessage " + clientMessage);
        channel = event.getChannel();

        if (clientMessage.equalsIgnoreCase("@Agent Smith")) {
            initSession(user);
            showAllCommands();
        } else if(dialog.contains(user)) {
           new Thread(this).start();
        }
    }

    private void showAllCommands(){
        channel.sendMessage("И так, " + user.getName() + ", вот что ты можешь...").queue();
        for (Map.Entry<String, String> command : commands.entrySet()) {
            String result = String.format(format, command.getKey(), command.getValue());
            channel.sendMessage(result).queue();
        }
    }

    private void executeCommand(){

        switch (clientMessage) {
            case "#set --id":
                //TODO remove line
                LOGGER.info("#set --id");
                setJavaRushId();
                break;

            case "#get --id":
                //TODO remove line
                LOGGER.info("#get --id");
                getJavaRushId();
                break;

            case "#delete msg --all":
                //TODO remove line
                LOGGER.info("#delete msg --all");
                deleteAllMessages();
                break;

            case "#stop":
                //TODO remove line
                LOGGER.info("#stop");
                stopCommunication();
                break;

            default:
                rejectRequest();
        }
    }

    private void setJavaRushId(){
        endSession(user);
        channel.sendMessage(STUB).queue();
    }

    private void getJavaRushId() {
        endSession(user);
        channel.sendMessage(STUB).queue();
    }


    private void deleteAllMessages() {
        endSession(user);
        MessageHistory messageHistory = new MessageHistory(channel);
        while (true) {
            List<Message> allMessages = messageHistory.retrievePast(1).complete();
            if (allMessages.size() == 0) break;
            Message message = allMessages.get(0);
            if (event.getJDA().getUserById(BOT_ID).equals(message.getAuthor())){
                message.delete().queue();
            }
        }
    }

    private void stopCommunication(){
        endSession(user);
        channel.sendMessage("Если понадоблюсь, ты знаешь где меня искать. Пока!!!").queue();
        return;
    }

    private void rejectRequest() {
        int attempt = session.get(user);
        session.put(user, ++attempt);
        //TODO remove line
        LOGGER.info("User has " + session.get(user) + " attempt(s).");
        if (attempt == 3 ){
            hasPermission = false;
            session.remove(user);
        }
        //TODO remove line
        LOGGER.info("attempt = " + attempt);
        String rejectMessage = "Команда введана не верно. {0}";
        double[] attempts = {3, 2, 1, 0};
        String[] options = {
                "Осталось три попытки.",
                "Осталось две попытки.",
                "Осталось одна попытка.",
                "Попыток не осталось. Сеанс связи завершен."
        };
        ChoiceFormat choice = new ChoiceFormat(attempts, options);
        Format[] formats = {null, choice};
        MessageFormat messageFormat = new MessageFormat(rejectMessage);
        messageFormat.setFormats(formats);
        Object[] textParts = {options[attempt], attempts};
        channel.sendMessage(messageFormat.format(textParts)).queue();
    }


    @Override
    public void run() {
        executeCommand();
    }

    private void initSession(User user) {
        session.put(user, -1);
        dialog.add(user);
    }

    private void endSession(User user) {
        session.remove(user);
        dialog.remove(user);
    }
}
