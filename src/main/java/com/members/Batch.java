package com.members;

import java.util.ArrayList;
import java.util.List;

public class Batch {

	private int id;
	private String batchName;
	private String time;
	private List<Participant> participants;
	
	public Batch(String batchName, String time) {
		this.batchName = batchName;
		this.time = time;
		this.participants = new ArrayList<Participant>();
	}

    public Batch() {
		// Default constructor
        this.participants = new ArrayList<Participant>();
	}
	
    public void addParticipant(Participant participant) {
        participants.add(participant);
        participant.setBatch(this);
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Batch [id=" + id + ", batchName=" + batchName + ", time=" + time + ", participants=" + participants + "]";
    }
}
