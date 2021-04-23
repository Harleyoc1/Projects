package com.harleyoconnor.projects.object;

import com.harleyoconnor.serdes.ClassSerDes;
import com.harleyoconnor.serdes.IndexedSerDesable;
import com.harleyoconnor.serdes.SerDes;
import com.harleyoconnor.serdes.field.ForeignField;
import com.harleyoconnor.serdes.field.ImmutableForeignField;
import com.harleyoconnor.serdes.field.PrimaryField;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Harley O'Connor
 */
public final class MeetingRoomBooking extends IndexedSerDesable<MeetingRoomBooking> {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    public static final PrimaryField<MeetingRoomBooking, Integer> PRIMARY_FIELD = createPrimaryField(MeetingRoomBooking.class);
    public static final ForeignField<MeetingRoomBooking, Integer, Employee> EMPLOYEE_FIELD = new ImmutableForeignField<>("employee", MeetingRoomBooking.class,
            Employee.PRIMARY_FIELD, false, false, MeetingRoomBooking::getEmployee);

    public static final SerDes<MeetingRoomBooking, Integer> SER_DES = ClassSerDes.Builder.of(MeetingRoomBooking.class, Integer.class)
            .primaryField(PRIMARY_FIELD).field(EMPLOYEE_FIELD)
            .field("room", MeetingRoom.PRIMARY_FIELD, MeetingRoomBooking::getRoom)
            .field("time", Date.class, MeetingRoomBooking::getTime, MeetingRoomBooking::setTime).build();

    /** The {@link Employee} who has booked the {@link MeetingRoom}. */
    private final Employee employee;
    /** The {@link MeetingRoom} the booking is for. */
    private final MeetingRoom room;
    /** The {@link Date} (and time) of the meeting. */
    private Date time;

    public MeetingRoomBooking(Employee employee, MeetingRoom room) {
        this.employee = employee;
        this.room = room;
    }

    public MeetingRoomBooking(int id, Employee employee, MeetingRoom room) {
        super(id);
        this.employee = employee;
        this.room = room;
    }

    /**
     * Gets the {@link #employee} for this {@link MeetingRoomBooking} object.
     *
     * @return The {@link #employee} for this {@link MeetingRoomBooking} object.
     */
    public Employee getEmployee() {
        return this.employee;
    }

    /**
     * Gets the {@link #room} for this {@link MeetingRoomBooking} object.
     *
     * @return The {@link #room} for this {@link MeetingRoomBooking} object.
     */
    public MeetingRoom getRoom() {
        return this.room;
    }

    /**
     * Gets the {@link #time} for this {@link MeetingRoomBooking} object.
     *
     * @return The {@link #time} for this {@link MeetingRoomBooking} object.
     */
    public Date getTime() {
        return this.time;
    }

    /**
     * Gets the {@link #time} for this {@link MeetingRoomBooking} object,
     * formatted according to {@link #DATE_FORMAT}.
     *
     * @return The {@link #time} for this {@link MeetingRoomBooking} object.
     */
    public String getTimeFormatted() {
        return DATE_FORMAT.format(this.time);
    }

    /**
     * Sets the {@link #time} for this {@link MeetingRoomBooking} object
     * to the given {@code time}.
     *
     * @param time The new {@link Date} object to set.
     * @return This {@link MeetingRoomBooking} object for chaining.
     */
    public MeetingRoomBooking setTime(Date time) {
        this.time = time;
        return this;
    }

    @Override
    public SerDes<MeetingRoomBooking, Integer> getSerDes() {
        return SER_DES;
    }

    @Override
    public PrimaryField<MeetingRoomBooking, Integer> getPrimaryField() {
        return PRIMARY_FIELD;
    }

}
