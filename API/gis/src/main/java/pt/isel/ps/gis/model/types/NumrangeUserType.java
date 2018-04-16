package pt.isel.ps.gis.model.types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import pt.isel.ps.gis.model.Numrange;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class NumrangeUserType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class<Numrange> returnedClass() {
        return Numrange.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == null)
            return y == null;
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        final String cellContent = rs.getString(names[0]);
        if (cellContent == null)
            return null;
        String[] split = cellContent.split("[\\[,\\]]");
        try {
            float minimum = Float.parseFloat(split[1]);
            float maximum = Float.parseFloat(split[2]);
            return new Numrange(minimum, maximum);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Failed to parse String to Number: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement ps, Object value, int idx, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            ps.setNull(idx, Types.OTHER);
            return;
        }
        ps.setObject(idx, value.toString(), Types.OTHER);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        // use serialization to create a deep copy
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeObject(value);
            oos.flush();
            try (ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
                 ObjectInputStream ois = new ObjectInputStream(bais)) {

                return ois.readObject();
            }
        } catch (ClassNotFoundException | IOException ex) {
            throw new HibernateException(ex);
        }
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) this.deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return this.deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return this.deepCopy(original);
    }
}
