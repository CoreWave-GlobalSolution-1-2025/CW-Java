package com.corewave.models;

import java.util.Objects;

public class _BaseEntity {

    private int id;
    private String name;
    private boolean deleted = false;

    public Object updateAttibutes(Object nObj) {
        return nObj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public _BaseEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "_BaseEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deleted=" + deleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        _BaseEntity that = (_BaseEntity) o;
        return getId() == that.getId() && isDeleted() == that.isDeleted() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), isDeleted());
    }

}
