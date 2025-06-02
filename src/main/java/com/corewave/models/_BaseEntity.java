package com.corewave.models;

import org.apache.logging.log4j.LogManager;

import java.util.Objects;

public class _BaseEntity<T> {

    private int id;
    private String name;
    private boolean deleted = false;

    public void updateAttributes(T nObj) {
        for (var field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);

                if (field.getName().equals("id")) {
                    continue;
                }

                var nVal = field.get(nObj);
                if (nVal != null) {
                    field.set(this, nVal);
                }

            } catch (IllegalAccessException e) {
                LogManager.getLogger(_BaseEntity.class).error("Erro ao utilizar método de atualizar.");
            } finally {
                field.setAccessible(false);
            }
        }
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
        if (o == null || getClass() != o.getClass()) return false;
        _BaseEntity<?> that = (_BaseEntity<?>) o;
        return getId() == that.getId() && isDeleted() == that.isDeleted() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), isDeleted());
    }
}
