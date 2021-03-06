package com.clavardage.core.models;

import com.clavardage.core.database.queries.selects.ConversationMemberSelectQuery;
import com.clavardage.core.database.queries.inserts.NewConversationInsertQuery;
import com.clavardage.core.database.queries.QueryParameters;
import com.clavardage.core.network.models.Address;
import com.clavardage.core.utils.Storable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class Conversation implements Storable {
    private int id = -1;
    private String name = null;
    private final ArrayList<User> participants = new ArrayList<>();
    private History history = null;

    public Conversation(ArrayList<User> users) {
        this.participants.add(0, User.localUser);
        this.participants.addAll(users);
    }

    private static ArrayList<User> castUserToArray(User user) {
        ArrayList<User> tmp = new ArrayList<>();
        tmp.add(user);
        return tmp;
    }

    public Conversation(User user) {
        this(castUserToArray(user));
    }

    public Conversation(int id) {
        this.id = id;
        this.history = new History(id);
        this.participants.add(0, User.localUser);

        ConversationMemberSelectQuery query = new ConversationMemberSelectQuery();
        QueryParameters parameters = new QueryParameters();

        parameters.append(id);
        query.prepare();
        query.setParameters(parameters);
        ResultSet resultSet = query.execute();
        ArrayList<User> remoteParticipants = new ArrayList<>();
        try {
            while(resultSet.next()) {
                remoteParticipants.add(new User(new Address(resultSet.getString("user_id"))));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        query.close();

        this.participants.addAll(remoteParticipants);
    }

    public int getId() { return id; }
    public ArrayList<User> getParticipants() { return participants; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public History getHistory() { return this.history; }

    public void store() {
        NewConversationInsertQuery query = new NewConversationInsertQuery();
        QueryParameters parameters = new QueryParameters();

        parameters.append(this.name);
        parameters.append(this.participants.size());
        query.prepare();
        query.setParameters(parameters);
        this.id = query.execute();
        query.close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        Conversation conversation = (Conversation) o;

        return this.id == conversation.id;
    }
}
