package tk.itiger.listeners;


import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;



public class GuildNewMembersFinder extends ListenerAdapter {


    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent gmjEvent) {
        User user = gmjEvent.getMember().getUser();
        user.openPrivateChannel().queue((channel) -> {
                    channel.sendMessage("```java\nПриветствую, " + user.getAsTag() + " тебя в Матрице!!!\n" +
                            "Предоставь мне id соего профиля на JavaRush, я гарантирую, что все узнают когда твой уровень повысится." +
                            " Если я тебе понадоблюсь просто напиши мне в личку: #Smith\n```").queue();
                });

        System.out.println(user.getName());
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent gmlEvent) {

       //TODO remove user from DB & clean message history
    }
}
