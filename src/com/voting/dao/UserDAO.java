package com.voting.dao;

import com.voting.model.User;
import com.voting.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // Register a new voter
    public boolean register(User user) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO voter(name, username, password) VALUES(?,?,?)"
            );
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            int i = ps.executeUpdate();
            return i > 0;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User login(String username, String password) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM voter WHERE username=? AND password=?"
            );
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                User user = new User();
                user.setVoterId(rs.getInt("voter_id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setHasVoted(rs.getBoolean("has_voted"));
                return user;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setVoted(int voterId) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE voter SET has_voted = true WHERE voter_id=?"
            );
            ps.setInt(1, voterId);
            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
