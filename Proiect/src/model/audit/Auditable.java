package model.audit;

import org.joda.time.DateTime;

import java.util.UUID;

public abstract class Auditable {

    private String uuid;
    private String createdBy;
    private DateTime createdDate;
    private boolean logicallyDeleted;
    private DateTime deletedDate;
    private DateTime editedDate;

    public static final String SYSTEM = "SYSTEM";


    public Auditable(String uuid, String createdBy, DateTime createdDate, boolean logicallyDeleted, DateTime deletedDate, DateTime editedDate) {
        this.uuid = uuid;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.logicallyDeleted = logicallyDeleted;
        this.deletedDate = deletedDate;
        this.editedDate = editedDate;
    }

    public Auditable() {
        this.uuid = UUID.randomUUID().toString();
        this.createdBy = SYSTEM;
        this.createdDate = DateTime.now();
        this.logicallyDeleted = false;
        this.deletedDate = null;
        this.editedDate = null;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
        StringBuilder builder = new StringBuilder();
        builder.append(createdDate.toString()).append(",");
        builder.append(createdBy).append(",");
        builder.append(editedDate!=null?editedDate.toString():"null").append(",");
        builder.append(deletedDate!=null?deletedDate.toString():"null").append(",");
        builder.append(isLogicallyDeleted()?"Yes":"No");
        return builder.toString();
    }

    public String toStringNice() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\n   createdBy='").append(createdBy).append('\'');
        sb.append("\n   createdDate=").append(createdDate);
        sb.append("\n   logicallyDeleted=").append(logicallyDeleted);
        sb.append("\n   deletedDate=").append(deletedDate);
        sb.append("\n   editedDate=").append(editedDate);
        return sb.toString();
    }
}
