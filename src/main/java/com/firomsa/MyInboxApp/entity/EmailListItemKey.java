package com.firomsa.MyInboxApp.entity;

import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class EmailListItemKey {

    @PrimaryKeyColumn(name = "user_Id" , ordinal = 0 , type = PrimaryKeyType.PARTITIONED)
    private String id;

    @PrimaryKeyColumn(name = "label" , ordinal = 1 , type = PrimaryKeyType.PARTITIONED)
    private String label;

    @PrimaryKeyColumn(name = "created_time_uuid" , ordinal = 2 , type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private UUID timeUuid;

    public EmailListItemKey(String id, String label, UUID timeUuid) {
        this.id = id;
        this.label = label;
        this.timeUuid = timeUuid;
    }
    
    public EmailListItemKey() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public UUID getTimeUuid() {
        return timeUuid;
    }

    public void setTimeUuid(UUID timeUuid) {
        this.timeUuid = timeUuid;
    }
    
}
