package com.voting.dao;

import com.voting.model.Candidate;
import com.voting.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CandidateDAO {

    public boolean addCandidate(String name) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO candidate(name) VALUES(?)"
            );
            ps.setString(1, name);
            return ps.executeUpdate() > 0;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Candidate> getAllCandidates() {
        ArrayList<Candidate> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM candidate");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Candidate c = new Candidate();
                c.setCandidateId(rs.getInt("candidate_id"));
                c.setName(rs.getString("name"));
                c.setVotes(rs.getInt("votes"));
                list.add(c);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void vote(int candidateId) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE candidate SET votes = votes + 1 WHERE candidate_id=?"
            );
            ps.setInt(1, candidateId);
            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
