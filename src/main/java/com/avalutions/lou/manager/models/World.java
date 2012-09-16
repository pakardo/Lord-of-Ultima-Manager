package com.avalutions.lou.manager.models;

import com.avalutions.lou.manager.net.commands.Poll;
import com.avalutions.lou.manager.net.commands.responses.PollResponse;
import com.avalutions.lou.manager.net.commands.responses.poll.*;

/**
 * Created with IntelliJ IDEA.
 * User: benny
 * Date: 9/2/12
 * Time: 10:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class World {
    private String id;
    private Player player;
    private Alliance alliance;
    private Long currentCityId;
    private City currentCity;
    private QuestProgress quests;
    private Mail mailbox;

    public World() {
    }

    public void changeCity(long cityId) {
        this.currentCityId = cityId;
        update();
    }

    public Long getCurrentCityId() {
        return currentCityId;
    }

    public enum WorldChange {Player, City, Quest, Journal, Alliance, Mailbox, Chat}

    private void onWorldChanged(WorldChange whatChanged) {
        if (handler != null) {
            handler.onWorldChanged(whatChanged);
        }
    }

    public void setWorldChangedHandler(WorldChangedHandler handler) {
        this.handler = handler;
    }

    private WorldChangedHandler handler;

    public interface WorldChangedHandler {
        public void onWorldChanged(WorldChange whatChanged);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        onWorldChanged(WorldChange.Player);
    }

    public Alliance getAlliance() {
        return alliance;
    }

    public void setAlliance(Alliance alliance) {
        this.alliance = alliance;
        onWorldChanged(WorldChange.Alliance);
    }

    public QuestProgress getQuests() {
        return quests;
    }

    public void setQuests(QuestProgress quests) {
        this.quests = quests;
        onWorldChanged(WorldChange.Quest);
    }

    public Mail getMailbox() {
        return mailbox;
    }

    public void setMailbox(Mail mailbox) {
        this.mailbox = mailbox;
        onWorldChanged(WorldChange.Mailbox);
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
        onWorldChanged(WorldChange.City);
    }

    public synchronized void update() {
        Poll poll = new Poll();
        PollResponse response = poll.run();

        if (response.player != null) {
            setPlayer(response.player);
        }
        if (response.alliance != null) {
            setAlliance(response.alliance);
        }
        if (response.chats != null) {
            setMailbox(response.mail);
        }
        if (response.city != null) {
            setCurrentCity(response.city);
        }
        if (response.mail != null) {
            setMailbox(response.mail);
        }
        if (response.quest != null) {
            setQuests(response.quest);
        }
    }
}
