package com.koh.thrift.swift;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftField.Requiredness;
import com.facebook.swift.codec.ThriftStruct;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;

@ThriftStruct("SubData")
@AllArgsConstructor
public final class SubData {
    public SubData() {
    }

    private int id;

    @ThriftField(value = 1, name = "id", requiredness = Requiredness.NONE)
    public int getId() {
        return id;
    }

    @ThriftField
    public void setId(final int id) {
        this.id = id;
    }

    private String name;

    @ThriftField(value = 2, name = "name", requiredness = Requiredness.NONE)
    public String getName() {
        return name;
    }

    @ThriftField
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubData other = (SubData) o;

        return
                Objects.equals(id, other.id) &&
                        Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(new Object[]{
                id,
                name
        });
    }
}
