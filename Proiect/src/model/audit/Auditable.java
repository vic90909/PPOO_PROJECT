package model.audit;

import org.joda.time.DateTime;

public abstract class Auditable {

    private String createdBy;
    private DateTime createdDate;
    private boolean logicallyDeleted;
    private DateTime deletedDate;
    private DateTime editedDate;

    public Auditable(String createdBy, DateTime createdDate, boolean logicallyDeleted, DateTime deletedDate, DateTime editedDate) {
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.logicallyDeleted = logicallyDeleted;
        this.deletedDate = deletedDate;
        this.editedDate = editedDate;
    }

    public Auditable() {

    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isLogicallyDeleted() {
        return logicallyDeleted;
    }

    public void setLogicallyDeleted(boolean logicallyDeleted) {
        this.logicallyDeleted = logicallyDeleted;
    }

    public DateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(DateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    public DateTime getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(DateTime editedDate) {
        this.editedDate = editedDate;
    }

    @Override
    public String toString() {
        return "Auditable{" +
                "createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", logicallyDeleted=" + logicallyDeleted +
                ", deletedDate=" + deletedDate +
                ", editedDate=" + editedDate +
                '}';
    }
}
