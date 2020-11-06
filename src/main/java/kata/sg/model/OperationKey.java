package kata.sg.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OperationKey implements Serializable {

    @Column(name = "userId")
    private Long userId;

    @Column(name = "accountId")
    private Long accountId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationKey that = (OperationKey) o;
        return userId.equals(that.userId) &&
                accountId.equals(that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, accountId);
    }
}
