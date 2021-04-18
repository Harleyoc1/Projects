package com.harleyoconnor.projects.object;

import com.harleyoconnor.serdes.ClassSerDes;
import com.harleyoconnor.serdes.IndexedSerDesable;
import com.harleyoconnor.serdes.SerDes;
import com.harleyoconnor.serdes.field.PrimaryField;

/**
 * @author Harley O'Connor
 */
public final class MeetingRoom extends IndexedSerDesable<MeetingRoom> {

    public static final PrimaryField<MeetingRoom, Integer> PRIMARY_FIELD = createPrimaryField(MeetingRoom.class);

    public static final SerDes<MeetingRoom, Integer> SER_DES = ClassSerDes.Builder.of(MeetingRoom.class, Integer.class)
            .primaryField(PRIMARY_FIELD)
            .uniqueField("name", String.class, MeetingRoom::getName, MeetingRoom::setName)
            .field("capacity", Integer.class, MeetingRoom::getCapacity, MeetingRoom::setCapacity)
            .field("wheelchair_access", Boolean.class, MeetingRoom::hasWheelchairAccess, MeetingRoom::setWheelchairAccess).build();

    private String name;
    /** This represents the maximum amount of people in the room. */
    private int capacity;
    private boolean wheelchairAccess;

    public MeetingRoom() {
        this.name = "Room " + id;
    }

    public MeetingRoom(int id) {
        super(id);
    }

    /**
     * Gets the {@link #name} for this {@link MeetingRoom} object.
     *
     * @return The {@link #name} for this {@link MeetingRoom} object.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the {@link #name} for this {@link MeetingRoom} object
     * to the given {@code name}.
     *
     * @param name The new {@link String} object to set.
     * @return This {@link MeetingRoom} object for chaining.
     */
    public MeetingRoom setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets the {@link #capacity} for this {@link MeetingRoom} object.
     *
     * @return The {@link #capacity} for this {@link MeetingRoom} object.
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Sets the {@link #capacity} for this {@link MeetingRoom} object
     * to the given {@code capacity}.
     *
     * @param capacity The new {@code int} to set.
     * @return This {@link MeetingRoom} object for chaining.
     */
    public MeetingRoom setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    /**
     * Gets the {@link #wheelchairAccess} for this {@link MeetingRoom} object.
     *
     * @return The {@link #wheelchairAccess} for this {@link MeetingRoom} object.
     */
    public boolean hasWheelchairAccess() {
        return this.wheelchairAccess;
    }

    /**
     * Sets the {@link #wheelchairAccess} for this {@link MeetingRoom} object
     * to the given {@code wheelchairAccess}.
     *
     * @param wheelchairAccess The new {@code boolean} to set.
     * @return This {@link MeetingRoom} object for chaining.
     */
    public MeetingRoom setWheelchairAccess(boolean wheelchairAccess) {
        this.wheelchairAccess = wheelchairAccess;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The {@link #SER_DES} for {@link MeetingRoom}s.
     */
    @Override
    public SerDes<MeetingRoom, Integer> getSerDes() {
        return SER_DES;
    }

    /**
     * {@inheritDoc}
     *
     * @return The {@link #PRIMARY_FIELD} for {@link MeetingRoom}s.
     */
    @Override
    public PrimaryField<MeetingRoom, Integer> getPrimaryField() {
        return PRIMARY_FIELD;
    }

}
